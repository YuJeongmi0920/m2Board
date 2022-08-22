package dao;

import dto.Board;

import java.sql.Connection;

import java.util.List;

public interface IBoardDao {

    List<Board> getBoardList(Connection conn, int rowPerPage, int beginRow) throws Exception;

    int getBoardListTotal(Connection conn) throws Exception;

    int readUpdate(Connection conn, int boardNo) throws Exception;

    Board selectRead(Connection conn, int boardNo) throws Exception;

    int goodCheck(Connection conn, int boardNo, String memberId) throws Exception;

    void goodInsert(Connection conn, int boardNo, String memberId) throws Exception;

    void goodDelete(Connection conn, int boardNo, String memberId) throws Exception;

    void goodUpdate(Connection conn, int boardNo) throws Exception;

    int insertBoard(Connection conn, Board board) throws Exception;
}
