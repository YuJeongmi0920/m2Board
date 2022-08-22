package dao;

import dto.Member;

import java.sql.Connection;
import java.sql.SQLException;

public interface IMemberDao {
    Member selectMemberLogin(Connection conn, String id, String pw) throws Exception;

    int insertMember(Connection conn, Member member) throws Exception;
}
