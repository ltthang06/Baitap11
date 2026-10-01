<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1">

    <title>Đăng ký - BookStore</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>

<body class="bg-light">

<div class="container py-5">

    <div class="row justify-content-center">

        <div class="col-md-6 col-lg-5">

            <div class="card shadow-sm">

                <div class="card-body p-4">

                    <h2 class="text-center mb-4">
                        Đăng ký
                    </h2>

                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger">
                            ${errorMessage}
                        </div>
                    </c:if>

                    <form method="post"
                          action="${pageContext.request.contextPath}/register">

                        <div class="mb-3">

                            <label class="form-label">
                                Email
                            </label>

                            <input type="email"
                                   name="email"
                                   class="form-control"
                                   required>

                        </div>

                        <div class="mb-3">

                            <label class="form-label">
                                Họ tên
                            </label>

                            <input type="text"
                                   name="fullname"
                                   class="form-control"
                                   required>

                        </div>

                        <div class="mb-3">

                            <label class="form-label">
                                Phone
                            </label>

                            <input type="text"
                                   name="phone"
                                   class="form-control">

                        </div>

                        <div class="mb-3">

                            <label class="form-label">
                                Password
                            </label>

                            <input type="password"
                                   name="password"
                                   class="form-control"
                                   required>

                        </div>

                        <button type="submit"
                                class="btn btn-primary w-100">
                            Đăng ký và gửi OTP
                        </button>

                    </form>

                    <div class="text-center mt-3">

                        Đã có tài khoản?

                        <a href="${pageContext.request.contextPath}/login">
                            Đăng nhập
                        </a>

                    </div>

                </div>

            </div>

        </div>

    </div>

</div>

</body>
</html>