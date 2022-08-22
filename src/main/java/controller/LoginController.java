package controller;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.Member;
import service.MemberService;


@WebServlet(value = "/login/*")
public class LoginController extends HttpServlet {
    MemberService memberService = new MemberService();

    // login form
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String uri = request.getRequestURI();
        String context = request.getContextPath();
        String subAddr = uri.substring(context.length());
        switch (subAddr) {
            case "/login/login-form":
                HttpSession session = request.getSession();
                if (session.getAttribute("loginMember") != null) { // 로그인 되어 있는 상태
                    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/login/index.jsp");
                    rd.forward(request, response);
                    return;
                }
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/login/login-form.jsp");
                rd.forward(request, response);
                break;
            case "/login/logout":
                System.out.println("로그아웃");
                session = request.getSession();
                session.invalidate();
                rd = request.getRequestDispatcher("/WEB-INF/login/login-form.jsp");
                rd.forward(request, response);
                break;
        }

    }

    // login action
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("loginMember") != null) { // 로그인 되어 있는 상태
            response.sendRedirect(request.getContextPath() + "/login/index");
            return;
        }
        request.setCharacterEncoding("utf-8");
        String id = request.getParameter("memberId");
        String pw = request.getParameter("memberPw");

        // new
        Member member = memberService.getMemberLogin(id, pw);
        if (member.getMemberId() == null) {
            System.out.println("로그인 실패");
            response.sendRedirect("/login/login-form");
            return;
        }
        session.setAttribute("loginMember", member);
        response.sendRedirect("/login/login-form");
    }
}
