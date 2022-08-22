<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>
<div class="col-sm-10">

    <br>
    ${loginMember.memberId} 님 환영합니다!
    <br>
    <a href="/login/logout">로그아웃</a>
    <a href="/board/board-list">게시판</a>
    <a href="/sign/remove-form">회원탈퇴</a>
    <a href="/sign/detail-form">내정보</a>
</body>
</html>
