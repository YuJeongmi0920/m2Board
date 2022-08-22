package service;

import dao.MemberDao;
import dto.Member;
import util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class MemberService implements IMemberService {

    @Override
    public Member getMemberLogin(String id, String pw) {
        Connection conn = null;
        Member member = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            // executeUpdate() 실행 시 자동 커밋을 막음

            MemberDao memberDao = new MemberDao();
            member = memberDao.selectMemberLogin(conn, id, pw);
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace(); // console에 예외메세지 출력
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return member;
    }

    @Override
    public int insertMember(Member member) {
        System.out.println(member);
        int num = 0;
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false); // executeUpdate()실행시 자동 커밋을 막음

            MemberDao memberDao = new MemberDao();
            num = memberDao.insertMember(conn, member);
            System.out.println("여기오세요?" + num);
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return num;
    }

    public boolean idCheck(String id) {
        Connection conn = null;
        boolean check = true;
        try {
            conn = DBUtil.getConnection();
            // executeUpdate() 실행 시 자동 커밋을 막음
            MemberDao memberDao = new MemberDao();
            String result = memberDao.idCheck(conn, id);
            if (result != null) {
                check = false;
            }
        } catch (Exception e) {
            e.printStackTrace(); // console에 예외메세지 출력
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return check;
    }

    // 탈퇴
    public int removeMember(Member member) {
        System.out.println("삭제 여기까지 오나?" + member);
        int row = 0;
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false); //
            MemberDao memberDao = new MemberDao();
            row = memberDao.deleteMember(conn, member);
            System.out.println("여기까지도 오나?" + row);
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace(); // console에 예외메세지 출력
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return row; // 탈퇴 성공
    }


    public Member getMember(String memberId) {
        System.out.println("회원정보 여기까지 오는지" + memberId);

        Member member = null;
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            MemberDao memberDao = new MemberDao();
            member = memberDao.getMemberDao(conn, memberId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return member;
    }
}

