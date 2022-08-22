<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <!-- Popper JS -->
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <!-- Latest compiled JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<div class="container px-4 px-lg-5 mt-5">
    <form method="post" action="/sign/sign-detail">
        <div class="mb-3">
            <h2 class="form-label">고객아이디</h2>
            <hr/>
            <input class="form-control" type="text" name="memberId" value="${member.memberId}" disabled>
        </div>
        <div class="mb-3">
            <h2 class="form-label">고객주소</h2>
            <hr/>
            <input class="form-control" type="text" name="memberId" value="${member.memberAddr}">
        </div>
        <div class="mb-3">
            <h2 class="form-label">고객상세주소</h2>
            <hr/>
            <input class="form-control" type="text" name="memberId" value="${member.memberDetailAddr}">
        </div>
        <div class="mb-3">
            <h2 class="form-label">고객이름</h2>
            <hr/>
            <input class="form-control" type="text" name="memberId" value="${member.memberName}">
        </div>
        <div>
            <input class="btn btn-primary" type="submit" value="수정">
            <a href="/login/login-form" class="btn btn-primary">뒤로가기</a>
        </div>
    </form>
</div>
</body>
</html>
