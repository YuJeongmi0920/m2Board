package controller;


import com.google.gson.Gson;
import dto.Board;
import dto.CommentDto;
import dto.Member;
import dto.PageNationDto;
import service.BoardListService;
import util.PageNationUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(value = "/board/*")
public class
BoardListController extends HttpServlet {


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String uri = request.getRequestURI();
        String context = request.getContextPath();
        String subAddr = uri.substring(context.length());
        BoardListService boardListService = new BoardListService();
        PageNationDto pageNation;


        int total;
        switch (subAddr) {
            case "/board/board-list":
                String current = request.getParameter("currentPage");


                // 페이지네이션
                total = boardListService.getBoardListTotal();
                pageNation = PageNationUtil.getPageNation(current, total, "/board/board-list", 10);
                request.setAttribute("pageNation", pageNation);


                // 리스트
                List<Board> boardList = boardListService.getBoardList(pageNation.getRowPerPage(), pageNation.getBeginRow());
                System.out.println(boardList);
                request.setAttribute("list", boardList);


                // 포워드
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/board/board-list.jsp");
                requestDispatcher.forward(request, resp);
                break;

            case "/board/board-detail":
                int boardNo = Integer.parseInt(request.getParameter("boardNo"));
                Board board = boardListService.getBoardDetail(boardNo);
                PageNationDto pageNationDto = PageNationUtil.getPageNation(null, boardListService.getBoardCommentTotal(boardNo), "", 5);
                List<CommentDto> commentList = boardListService.getBoardComment(boardNo, pageNationDto.getRowPerPage(), pageNationDto.getBeginRow());
                HttpSession session = request.getSession();
                Member member = (Member) session.getAttribute("loginMember");
                if (member != null) {
                    // 좋아요 체크
                    int num = boardListService.goodCheck(boardNo, member.getMemberId());
                    if (num != 0) {
                        // 좋아요가 있으면
                        request.setAttribute("good", true);
                    }
                }
                request.setAttribute("board", board);
                request.setAttribute("commentList", commentList);
                request.setAttribute("pageNation", pageNationDto);

                // 포워드
                RequestDispatcher requestDispatcher2 = request.getRequestDispatcher("/WEB-INF/board/board-detail.jsp");
                requestDispatcher2.forward(request, resp);
                break;

            case "/board/good":
                session = request.getSession();
                member = (Member) session.getAttribute("loginMember");
                boardNo = Integer.parseInt(request.getParameter("boardNo"));
                PrintWriter writer = resp.getWriter();
                if (boardListService.good(boardNo, member.getMemberId())) {
                    writer.print("good");
                } else {
                    writer.print("goodDel");
                }
                break;


            case "/board/board-insert":
                String boardTitle = request.getParameter("boardTitle");
                String boardContent = request.getParameter("boardContent");
                session = request.getSession();
                Member loginMember = (Member) session.getAttribute("loginMember");
                System.out.println("loginMember = " + loginMember);
                boardListService = new BoardListService();
                board = new Board();

                board.setBoardTitle(boardTitle);
                board.setBoardContent(boardContent);
                board.setBoardWriter(loginMember.getMemberId());
                int row = boardListService.addBoardList(board);
                System.out.println("row : " + row);
                // 포워드
                resp.sendRedirect("/board/board-list");
                break;

            //입력 폼으로
            case "/board/board-insert-form":
                requestDispatcher = request.getRequestDispatcher("/WEB-INF/board/board-insert.jsp");
                requestDispatcher.forward(request, resp);
                break;


            case "/board/board-update":
                boardNo = Integer.parseInt(request.getParameter("boardNo"));
                boardTitle = request.getParameter("boardTitle");
                boardContent = request.getParameter("boardContent");
                System.out.println("boardTitle = " + boardTitle);
                System.out.println("boardContent = " + boardContent);

                session = request.getSession();
                loginMember = (Member) session.getAttribute("loginMember");
                System.out.println("loginMember = " + loginMember);
                boardListService = new BoardListService();
                board = new Board();

                board.setBoardNo(boardNo);
                board.setBoardTitle(boardTitle);
                board.setBoardContent(boardContent);
                int num = boardListService.modifyBoardList(board);
                System.out.println("num = " + num);

                // 포워드
                request.setAttribute("board", board);


                //수정 폼으로
                requestDispatcher = request.getRequestDispatcher("/WEB-INF/board/board-detail.jsp");
                requestDispatcher.forward(request, resp);
                break;

            case "/board/cmInsert":
                Map<String, Object> map = new HashMap<>();
                session = request.getSession();
                loginMember = (Member) session.getAttribute("loginMember");
                String loginId = loginMember.getMemberId();
                String commentContent = request.getParameter("commentContent");
                String memberId = request.getParameter("memberId");
                boardNo = Integer.parseInt(request.getParameter("boardNo"));
                boardListService.cmInsert(boardNo, memberId, commentContent);
                pageNationDto = PageNationUtil.getPageNation(null, boardListService.getBoardCommentTotal(boardNo), "", 5);
                List<CommentDto> boardComment = boardListService.getBoardComment(boardNo, pageNationDto.getRowPerPage(), pageNationDto.getBeginRow());
                map.put("boardNo", boardNo);
                map.put("loginId", loginId);
                map.put("cmList", boardComment);
                map.put("pageNation", pageNationDto);
                resp.setContentType("text/html; charset=UTF-8");
                resp.getWriter().write(new Gson().toJson(map));
                break;
            case "/board/cmList":
                loginId = null;
                boardNo = Integer.parseInt(request.getParameter("boardNo"));
                current = request.getParameter("current");
                map = new HashMap<>();
                session = request.getSession();
                if (session.getAttribute("loginMember") != null) {
                    loginMember = (Member) session.getAttribute("loginMember");
                    loginId = loginMember.getMemberId();
                }
                pageNationDto = PageNationUtil.getPageNation(current, boardListService.getBoardCommentTotal(boardNo), "", 5);
                boardComment = boardListService.getBoardComment(boardNo, pageNationDto.getRowPerPage(), pageNationDto.getBeginRow());
                map.put("boardNo", boardNo);
                map.put("loginId", loginId);
                map.put("cmList", boardComment);
                map.put("pageNation", pageNationDto);
                resp.setContentType("text/html; charset=UTF-8");
                resp.getWriter().write(new Gson().toJson(map));
                break;
            case "/board/cmDelete":
                loginId = null;
                int commentNo = Integer.parseInt(request.getParameter("commentNo"));
                boardNo = Integer.parseInt(request.getParameter("boardNo"));
                current = request.getParameter("current");
                map = new HashMap<>();
                session = request.getSession();
                if (session.getAttribute("loginMember") != null) {
                    loginMember = (Member) session.getAttribute("loginMember");
                    loginId = loginMember.getMemberId();
                }
                boardListService.cmDelete(commentNo);
                pageNationDto = PageNationUtil.getPageNation(current, boardListService.getBoardCommentTotal(boardNo), "", 5);
                boardComment = boardListService.getBoardComment(boardNo, pageNationDto.getRowPerPage(), pageNationDto.getBeginRow());
                if (boardComment.size() == 0) {
                    int check = Integer.parseInt(current) - 1;
                    pageNationDto = PageNationUtil.getPageNation(String.valueOf(check), boardListService.getBoardCommentTotal(boardNo), "", 5);
                    boardComment = boardListService.getBoardComment(boardNo, pageNationDto.getRowPerPage(), pageNationDto.getBeginRow());
                }
                map.put("boardNo", boardNo);
                map.put("loginId", loginId);
                map.put("cmList", boardComment);
                map.put("pageNation", pageNationDto);
                resp.setContentType("text/html; charset=UTF-8");
                resp.getWriter().write(new Gson().toJson(map));
                break;
            case "/board/cmUpdate":
                loginId = null;
                String cmUpdateContent = request.getParameter("cmUpdateContent");
                commentNo = Integer.parseInt(request.getParameter("commentNo"));
                boardNo = Integer.parseInt(request.getParameter("boardNo"));
                current = request.getParameter("current");
                map = new HashMap<>();
                session = request.getSession();
                if (session.getAttribute("loginMember") != null) {
                    loginMember = (Member) session.getAttribute("loginMember");
                    loginId = loginMember.getMemberId();
                }
                boardListService.cmUpdate(commentNo, cmUpdateContent);
                pageNationDto = PageNationUtil.getPageNation(current, boardListService.getBoardCommentTotal(boardNo), "", 5);
                boardComment = boardListService.getBoardComment(boardNo, pageNationDto.getRowPerPage(), pageNationDto.getBeginRow());
                map.put("boardNo", boardNo);
                map.put("loginId", loginId);
                map.put("cmList", boardComment);
                map.put("pageNation", pageNationDto);
                resp.setContentType("text/html; charset=UTF-8");
                resp.getWriter().write(new Gson().toJson(map));
                break;
        }

    }
}

