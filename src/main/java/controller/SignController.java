package controller;

import dto.Member;
import service.MemberService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/sign/*")
public class SignController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uri = request.getRequestURI();
        String context = request.getContextPath();
        String subAddr = uri.substring(context.length());
        MemberService memberService = new MemberService();
        Member member = new Member();

        switch (subAddr) {
            case "/sign/sign-member":
                String memberId = request.getParameter("id");
                String memberPw = request.getParameter("pw");
                String memberName = request.getParameter("name");
                String addr = request.getParameter("addr");
                String detailAddr = request.getParameter("detailAddr");
                member.setMemberId(memberId);
                member.setMemberPw(memberPw);
                member.setMemberName(memberName);
                member.setMemberAddr(addr);
                member.setMemberDetailAddr(detailAddr);

                int row = memberService.insertMember(member);
                System.out.println("row = " + row);
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print("<script>alert('가입성공');location.href='/login/login-form'</script>");
                break;

            //로그인 폼으로
            case "/sign/sign-form":

                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/sign/sign-form.jsp");
                requestDispatcher.forward(request, response);
                break;

            case "/sign/id-check":
                System.out.println("중복체크");
                String id = request.getParameter("id");
                if (memberService.idCheck(id)) {
                    response.getWriter().print("ok");
                } else {
                    response.getWriter().print("fail");
                }
                break;


            //회원탈퇴
            case "/sign/sign-remove":
                HttpSession session = request.getSession();
                memberId = request.getParameter("memberId");
                memberPw = request.getParameter("memberPw");


                memberService = new MemberService();
                member = new Member();

                member.setMemberId(memberId);
                member.setMemberPw(memberPw);

                System.out.println("memberId = " + memberId);
                System.out.println("memberPw = " + memberPw);

                row = memberService.removeMember(member);
                System.out.println("row = " + row);
                session.invalidate();
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print("<script>alert('탈퇴성공');location.href='/login/login-form'</script>");
                break;

            //삭제 폼으로
            case "/sign/remove-form":
                requestDispatcher = request.getRequestDispatcher("/WEB-INF/sign/remove-form.jsp");
                requestDispatcher.forward(request, response);
                break;


            //디테일 폼으로
            case "/sign/detail-form":
                session = request.getSession();
                member = (Member) session.getAttribute("loginMember");
                memberService = new MemberService();
                Member memberDetail = memberService.getMember(member.getMemberId());
                request.setAttribute("member", memberDetail);
                requestDispatcher = request.getRequestDispatcher("/WEB-INF/sign/detail-form.jsp");
                requestDispatcher.forward(request, response);
                break;


            case "/sign/sign-update":
                session = request.getSession();
                memberId = request.getParameter("memberId");
                memberPw = request.getParameter("memberPw");
                memberName = request.getParameter("name");
                addr = request.getParameter("addr");
                detailAddr = request.getParameter("detailAddr");

                System.out.println("memberId = " + memberId);
                System.out.println("memberPw = " + memberPw);
                System.out.println("memberName = " + memberName);
                System.out.println("memberName = " + memberName);
                System.out.println("addr = " + addr);
                System.out.println("detailAddr = " + detailAddr);


        }
    }
}

