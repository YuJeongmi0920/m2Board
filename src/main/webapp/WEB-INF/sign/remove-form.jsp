<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <!-- jQuery library -->
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
    <!-- Popper JS -->
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <!-- Latest compiled JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="d-flex justify-content-center container" style="height: 800px">
    <form action="/sign/sign-remove" method="post">
        <div class="mb-3">
            <label for="memberId" class="form-label">아이디</label>
            <input type="text" class="form-control" name="memberId" id="memberId">
        </div>
        <div class="mb-3">
            <label for="memberPw" class="form-label">비밀번호</label>
            <input type="password" class="form-control" name="memberPw" id="memberPw">
        </div>
        <div>
            <input type="submit" id="removeBtn" class="btn btn-danger rounded-0 me-1" value="탈퇴하기">
        </div>
    </form>
</div>
</body>
</html>
