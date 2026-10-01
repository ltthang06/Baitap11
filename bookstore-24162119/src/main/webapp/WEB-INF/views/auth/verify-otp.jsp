<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1">

    <title>Xác thực OTP - BookStore</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>

<body class="bg-light">

<div class="container py-5">

    <div class="row justify-content-center">

        <div class="col-md-5 col-lg-4">

            <div class="card shadow-sm">

                <div class="card-body p-4">

                    <h2 class="text-center mb-3">
                        Xác thực OTP
                    </h2>

                    <p class="text-muted text-center">
                        Nhập mã OTP đã được gửi tới email của bạn.
                    </p>

                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger">
                            ${errorMessage}
                        </div>
                    </c:if>

                    <form method="post"
                          action="${pageContext.request.contextPath}/verify-otp">

                        <div class="mb-3">

                            <label class="form-label">
                                Mã OTP
                            </label>

                            <input type="text"
                                   name="otp"
                                   class="form-control text-center"
                                   maxlength="6"
                                   required>

                        </div>

                        <button type="submit"
                                class="btn btn-primary w-100">
                            Xác thực
                        </button>

                    </form>

                    <div class="text-center mt-3">

                        <a href="${pageContext.request.contextPath}/login">
                            Quay lại đăng nhập
                        </a>

                    </div>

                </div>

            </div>

        </div>

    </div>

</div>

</body>
</html>