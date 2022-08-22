<%--
  Created by IntelliJ IDEA.
  User: GDJ50
  Date: 2022-08-19
  Time: 오후 3:12
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Insert title here</title>
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
<div class="container">
  <form action="/board/board-update" method="post">

    <div class="mb-3">
      <label for="subject" class="form-label">제목</label>
      <input type="text" class="form-control" id="subject" name="subject">
    </div>
    <div class="mb-3">
      <label for="content" class="form-label">내용</label>
      <textarea class="form-control" name="content" id="content"
                rows="5"></textarea>
    </div>
    <div>
      <input type="submit" class="btn btn-primary" value="수정">
      <a id="deleteBtn" class="btn btn-primary">삭제</a>
    </div>
  </form>
</div>
</body>