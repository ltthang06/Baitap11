param(
    [string]$TomcatHome = $env:CATALINA_HOME,
    [int]$Port = 8080,
    [string]$DbUrl = 'jdbc:sqlserver://localhost:1433;databaseName=BookStore_24162119;encrypt=true;trustServerCertificate=true',
    [string]$DbUsername = 'sa',
    [switch]$SkipBuild
)
$ErrorActionPreference = 'Stop'
$projectRoot = Split-Path -Parent $PSScriptRoot
if (-not $TomcatHome) { throw 'Truyền -TomcatHome là thư mục Tomcat 10.1 chứa bin, conf, lib.' }
$TomcatHome = (Resolve-Path -LiteralPath $TomcatHome).Path
if (-not (Test-Path -LiteralPath (Join-Path $TomcatHome 'bin\bootstrap.jar'))) { throw 'TomcatHome không đúng.' }
if ($Port -lt 1024 -or $Port -gt 65535) { throw 'Port phải từ 1024 đến 65535.' }
if ($env:JAVA_HOME -and (Test-Path (Join-Path $env:JAVA_HOME 'bin\java.exe'))) {
    $java = Join-Path $env:JAVA_HOME 'bin\java.exe'
} else { $java = (Get-Command java -ErrorAction Stop).Source }
Push-Location $projectRoot
try {
    if (-not $SkipBuild) {
        & mvn -B clean verify
        if ($LASTEXITCODE -ne 0) { throw 'Build thất bại. Xem lỗi Maven bên trên.' }
    }
    $war = Join-Path $projectRoot 'target\bookstore-24162119.war'
    if (-not (Test-Path $war)) { $war = Join-Path $projectRoot 'dist\bookstore-24162119.war' }
    if (-not (Test-Path $war)) { throw 'Chưa có WAR. Chạy mvn clean verify trước.' }
    $runtime = Join-Path $projectRoot '.runtime\tomcat'
    New-Item -ItemType Directory -Force -Path $runtime,(Join-Path $runtime 'logs'),(Join-Path $runtime 'temp'),(Join-Path $runtime 'webapps'),(Join-Path $runtime 'work') | Out-Null
    Copy-Item -LiteralPath (Join-Path $TomcatHome 'conf') -Destination $runtime -Recurse -Force
    $serverPath = Join-Path $runtime 'conf\server.xml'
    [xml]$server = Get-Content -LiteralPath $serverPath -Raw
    $server.Server.SetAttribute('port', '-1')
    $connector = @($server.Server.Service.Connector) | Where-Object { $_.protocol -eq 'HTTP/1.1' } | Select-Object -First 1
    if (-not $connector) { throw 'Không tìm thấy HTTP Connector trong server.xml.' }
    $connector.SetAttribute('port', [string]$Port)
    $connector.SetAttribute('address', '127.0.0.1')
    $server.Save($serverPath)
    Copy-Item -LiteralPath $war -Destination (Join-Path $runtime 'webapps\bookstore-24162119.war') -Force
    $oldDbUrl = $env:DB_URL; $oldDbUser = $env:DB_USERNAME; $oldDbPassword = $env:DB_PASSWORD
    try {
        $env:DB_URL = $DbUrl
        $env:DB_USERNAME = $DbUsername
        if (-not $env:DB_PASSWORD) {
            $securePassword = Read-Host "Mật khẩu SQL Server cho $DbUsername" -AsSecureString
            $env:DB_PASSWORD = [System.Net.NetworkCredential]::new('', $securePassword).Password
        }
        Write-Host "Mở http://localhost:$Port/bookstore-24162119/home"
        Write-Host 'Nhấn Ctrl+C để dừng Tomcat.'
        $classpath = (Join-Path $TomcatHome 'bin\bootstrap.jar') + ';' + (Join-Path $TomcatHome 'bin\tomcat-juli.jar')
        & $java "-Dcatalina.home=$TomcatHome" "-Dcatalina.base=$runtime" "-Djava.io.tmpdir=$(Join-Path $runtime 'temp')" '-Dfile.encoding=UTF-8' '-cp' $classpath 'org.apache.catalina.startup.Bootstrap' 'start'
    } finally {
        $env:DB_URL = $oldDbUrl; $env:DB_USERNAME = $oldDbUser; $env:DB_PASSWORD = $oldDbPassword
    }
} finally { Pop-Location }
