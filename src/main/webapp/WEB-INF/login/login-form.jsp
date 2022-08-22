<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
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

<div class="d-flex justify-content-center container" style="height: 450px">
    <form id="list" method="post" action="/login"
          class="w-75 border p-3 bg-white shadow rounded align-self-center">
        <div class="d-inline-flex">
            <h1 class="ms-2 fw-bold">로그인</h1>
        </div>
        <div class="mb-3">
            <label for="memberId" class="form-label">아이디</label>
            <input type="text" class="form-control" name="memberId" id="memberId" placeholder="아이디를 입력해주세요">
        </div>
        <div class="mb-3">
            <label for="memberPw" class="form-label">비밀번호</label>
            <input type="password" class="form-control" name="memberPw" id="memberPw"
                   placeholder="비밀번호를 입력해주세요">
        </div>
        <div class="text-center mt-3">
            <button type="submit" id="boardBtn" class="btn btn-danger rounded-0 me-1">고객 로그인</button>
            <a href="/sign/sign-form" class="btn btn-primary">회원가입</a>

        </div>
    </form>
</div>

</form>
</body>
</html>


