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
    <form method="post" action="/board/board-update">
        <div class="mb-3">
            <h2 class="form-label">제목</h2>
            <hr/>
            <input type="hidden" name="boardNo" value="${board.boardNo}">
            <c:if test="${sessionScope.get('loginMember') eq null}">
                ${board.boardTitle}
            </c:if>
            <c:if test="${sessionScope.get('loginMember') ne null}">
                <c:if test="${sessionScope.loginMember.memberId eq board.boardWriter}">
                    <input class="form-control" type="text" name="boardTitle" id="boardTitle"
                           value="${board.boardTitle}">
                </c:if>
                <c:if test="${sessionScope.loginMember.memberId ne board.boardWriter}">
                    ${board.boardTitle}
                </c:if>
            </c:if>
        </div>
        <div class="mb-3">
            <h2 class="form-label">내용</h2>
            <hr/>
            <c:if test="${sessionScope.get('loginMember') eq null}">
                ${board.boardTitle}
            </c:if>
            <c:if test="${sessionScope.get('loginMember') ne null}">
                <c:if test="${sessionScope.loginMember.memberId eq board.boardWriter}">
                    <textarea class="form-control" rows="4" name="boardContent"
                              id="boardContent">${board.boardContent}</textarea>
                </c:if>
                <c:if test="${sessionScope.loginMember.memberId ne board.boardWriter}">
                    ${board.boardContent}
                </c:if>
            </c:if>

        </div>
        <div class="mb-3">
            <h2 for="date" class="form-label">작성일</h2>
            <input type="text" class="form-control" id="date" name="date" value="${board.createDate}" disabled>
        </div>
        <div>
            <a href="/board/board-list" class="btn btn-primary">리스트로</a>
            <c:if test="${sessionScope.get('loginMember') ne null}">
                <c:if test="${sessionScope.loginMember.memberId eq board.boardWriter}">
                    <input type="submit" class="btn btn-primary" value="수정"/>
                </c:if>
                <c:if test="${sessionScope.loginMember.memberId ne board.boardWriter}">
                    <c:if test="${good}">
                        <button type="button" boardNo="${board.boardNo}" id="goodBtn" class="btn btn-danger">좋아요
                        </button>
                    </c:if>
                    <c:if test="${!good}">
                        <button type="button" boardNo="${board.boardNo}" id="goodBtn" class="btn btn-outline-danger">좋아요
                        </button>
                    </c:if>
                </c:if>
            </c:if>
        </div>
    </form>
    <div class="pt-4 border-bottom border-dark">
        <h4 class="fw-bold">댓글</h4>
    </div>
    <%-- 댓글 입력 폼 --%>
    <div class="d-flex align-items-center mt-2">
        <div class="form-floating flex-grow-1 px-2">
            <c:if test="${sessionScope.loginMember ne null}">
            <textarea class="form-control" placeholder="댓글을 작성해주세요" name="commentContent" id="commentContent"
                      style="height: 100px; resize: none;"></textarea>
            </c:if>
            <div class="invalid-feedback">
                1자 이상 입력해주세요
            </div>
            <c:if test="${sessionScope.loginMember eq null}">
                <label for="commentContent">댓글을 작성하려면, 로그인 해주세요</label>
            </c:if>
        </div>
        <c:if test="${sessionScope.loginMember ne null}">
            <a boardNo="${board.boardNo}" memberId="${sessionScope.loginMember.memberId}" id="cmInsertBtn"
               class="btn btn-primary btn-sm">등록</a>
        </c:if>
    </div>
    <%-- 댓글리스트 --%>
    <div id="commentLists" class="container px-5 my-4">
        <c:forEach items="${commentList}" var="dto">
            <%-- 댓글 내용 --%>
            <div class="listForm">
                <h4 class="fw-bold fs-4">${dto.memberId}</h4>
                <div class="lh-sm">${dto.commentContent}</div>
                <div class="d-flex justify-content-end">
                    <div>
                        <c:if test="${sessionScope.loginMember.memberId eq dto.memberId}">
                            <a class='cmUpdateBtnForm btn btn-primary btn-sm'>수정</a>
                            <a boardNo="${board.boardNo}" commentNo="${dto.commentNo}"
                               class='cmDelBtn btn btn-primary btn-sm'>삭제</a>
                        </c:if>
                    </div>
                </div>
                <div class="d-flex justify-content-end">작성일 : ${dto.commentDate}</div>
                <hr/>
            </div>
            <%-- 수정하기 수정 클릭시 요놈 생김 --%>
            <div class="updateForm" style="display: none">
                <div class="form-floating flex-grow-1 px-2">
                    <textarea class="cmUpdateContent form-control"
                              style="height: 100px; resize: none;">${dto.commentContent}</textarea>
                    <div class="invalid-feedback">
                        1자 이상 입력해주세요
                    </div>
                </div>
                <div class="d-flex justify-content-end mt-2">
                    <a boardNo="${board.boardNo}" commentNo="${dto.commentNo}"
                       class='cmUpdateBtn btn btn-primary btn-sm mx-1'>등록</a>
                    <a class='cmUpdateCancel btn btn-primary btn-sm ms-1'>취소</a>
                </div>
                <hr/>
            </div>
        </c:forEach>
    </div>
    <%-- 페이지네이션 --%>
    <div id="paginationBox">
        <ul class="pagination justify-content-center my-2 mb-2">
            <%-- 이전 --%>
            <c:if test="${pageNation.startPage ne 1}">
                <li class="page-item">
                    <a style="cursor: pointer" class="page-link page-info"
                       boardNo="${board.boardNo}"
                       page="${pageNation.startPage-1}">
                        이전 </a>
                </li>
            </c:if>
            <%-- 페이지넘버 --%>
            <c:forEach begin="${pageNation.startPage}" end="${pageNation.endPage}" varStatus="status">
                <c:if test="${pageNation.currentPage eq status.index}">
                    <li class="page-item active">
                        <a class="page-link">${status.index}
                        </a>
                    </li>
                </c:if>
                <c:if test="${pageNation.currentPage ne status.index}">
                    <li class="page-item">
                        <a class="page-link page-info"
                           style="cursor: pointer"
                           boardNo="${board.boardNo}"
                           page="${status.index}">${status.index}
                        </a>
                    </li>
                </c:if>
            </c:forEach>
            <%-- 다음버튼 --%>
            <c:if test="${pageNation.endPage ne pageNation.lastPage}">
                <li class="page-item">
                    <a style="cursor: pointer"
                       class="page-link page-info"
                       boardNo="${board.boardNo}"
                       page="${pageNation.endPage+1}">다음</a>
                </li>
            </c:if>
        </ul>
    </div>
</div>
<script type="text/javascript" src="/resource/js/cm.js"></script>
</body>
<script>
    // 좋아요
    if (document.querySelector('#goodBtn')) {
        document.querySelector('#goodBtn').addEventListener('click', function (ev) {
            let url = '/board/good?boardNo=' + this.getAttribute('boardNo');
            fetch(url, {
                method: 'GET'
            }).then(res => res.text())
                .then(data => {
                    if (data == 'good') {
                        this.className = 'btn btn-danger';
                    } else {
                        this.className = 'btn btn-outline-danger';
                    }
                })
        })
    }
</script>
</html>
