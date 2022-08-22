package dao;

import dto.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDao implements IMemberDao {

    @Override
    public Member selectMemberLogin(Connection conn, String id, String pw) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Member memberLogin = new Member();
        try {
            String sql = "select member_id from member WHERE  member_id= ? AND member_pw= password(?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.setString(2, pw);
            rs = stmt.executeQuery();
            if (rs.next()) {
                memberLogin.setMemberId(rs.getString("member_id"));
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
        return memberLogin;
    }

    @Override
    public int insertMember(Connection conn, Member member) throws Exception {
        System.out.println("member = " + member);
        //return variable
        PreparedStatement stmt = null;
        int row = 0;
        try {
            String sql = "insert into Member (member_id, member_pw, member_level,member_name,member_address,member_detail_address) value (?,password(?),?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, member.getMemberId());
            stmt.setString(2, member.getMemberPw());
            stmt.setInt(3, member.getMemberLevel());
            stmt.setString(4, member.getMemberName());
            stmt.setString(5, member.getMemberAddr());
            stmt.setString(6, member.getMemberDetailAddr());
            row = stmt.executeUpdate();
            System.out.println("성공 했나유 = " + row);
        } finally {
            if (stmt != null) stmt.close();
        }
        return row;
    }

    public String idCheck(Connection conn, String id) throws Exception {
        //return variable
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String result = null;
        try {
            String sql = "SELECT member_id FROM member WHERE member_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                result = rs.getString("member_id");
            }
        } finally {
            if (stmt != null) stmt.close();
            if (rs != null) rs.close();
        }
        return result;
    }

    public int deleteMember(Connection conn, Member member) throws Exception {
        System.out.println("--------------------------------" + member);
        int row = 0;
        PreparedStatement psmt = null;
        try {
            String sql = "DELETE FROM member WHERE member_id = ? AND member_pw= password(?)";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, member.getMemberId());
            psmt.setString(2, member.getMemberPw());
            psmt.executeUpdate();
        } finally {
            if (psmt != null) {
                psmt.close();
            }
        }
        return row;
    }


    public Member getMemberDao(Connection conn, String memberId) throws Exception {
        int row = 0;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Member member = new Member();
        try {
            String sql = "SELECT member_id,member_address,member_detail_address,member_name FROM member WHERE member_id = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, memberId);
            rs = psmt.executeQuery();
            if (rs.next()) {
                member.setMemberId(rs.getString("member_id"));
                member.setMemberAddr(rs.getString("member_address"));
                member.setMemberDetailAddr(rs.getString("member_detail_address"));
                member.setMemberName(rs.getString("member_name"));
            }
        } finally {
            if (psmt != null) psmt.close();
            if (rs != null) rs.close();
        }
        return member;
    }
}

