package dao;

import dto.Board;
import dto.CommentDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BoardDao implements IBoardDao {
    @Override
    public List<Board> getBoardList(Connection conn, int rowPerPage, int beginRow) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Board> list = new ArrayList<>();
        try {
            String sql = "select board_no,board_title,board_writer,create_date,board_read,board_nice FROM board ORDER BY create_date DESC LIMIT ?,?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, beginRow);
            stmt.setInt(2, rowPerPage);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Board board = new Board();
                board.setBoardNo(rs.getInt("board_no"));
                board.setBoardTitle(rs.getString("board_title"));
                board.setBoardWriter(rs.getString("board_writer"));
                board.setCreateDate(rs.getString("create_date"));
                board.setBoardRead(rs.getInt("board_read"));
                board.setBoardNice(rs.getInt("board_nice"));
                list.add(board);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
        return list;
    }

    @Override
    public int getBoardListTotal(Connection conn) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int result = 0;
        try {
            String sql = "select count(*) FROM board";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
        return result;
    }

    @Override
    public int readUpdate(Connection conn, int boardNo) throws Exception {
        int result = 0;
        PreparedStatement stmt = null;
        try {
            String sql = "UPDATE board SET board_read = board_read + 1 WHERE board_no= ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, boardNo);
            result = stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            return result;
        }
    }

    @Override
    public Board selectRead(Connection conn, int boardNo) throws Exception {
        Board board = new Board();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT board_no, board_title,board_contents,board_writer,create_date,board_read,board_nice FROM board WHERE board_no = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, boardNo);
            rs = stmt.executeQuery();
            if (rs.next()) {
                board.setBoardNo(rs.getInt("board_no"));
                board.setBoardTitle(rs.getString("board_title"));
                board.setBoardContent(rs.getString("board_contents"));
                board.setBoardWriter(rs.getString("board_writer"));
                board.setCreateDate(rs.getString("create_date"));
                board.setBoardRead(rs.getInt("board_read"));
                board.setBoardNice(rs.getInt("board_nice"));
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
        return board;
    }

    @Override
    public int goodCheck(Connection conn, int boardNo, String memberId) throws Exception {
        int result = 0;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT COUNT(*) FROM nice WHERE board_no = ? AND member_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, boardNo);
            stmt.setString(2, memberId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            return result;
        }
    }

    @Override
    public void goodInsert(Connection conn, int boardNo, String memberId) throws Exception {
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT INTO nice (board_no,member_id,create_date) VALUES (?,?,now())";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, boardNo);
            stmt.setString(2, memberId);
            stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    @Override
    public void goodDelete(Connection conn, int boardNo, String memberId) throws Exception {
        PreparedStatement stmt = null;
        try {
            String sql = "DELETE FROM nice WHERE board_no = ? AND member_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, boardNo);
            stmt.setString(2, memberId);
            stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    @Override
    public void goodUpdate(Connection conn, int boardNo) throws Exception {
        PreparedStatement stmt = null;
        try {
            String sql = "UPDATE board SET board_nice = (SELECT COUNT(*) FROM nice WHERE board_no = ?) WHERE board_no = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, boardNo);
            stmt.setInt(2, boardNo);
            stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    @Override
    public int insertBoard(Connection conn, Board board) throws Exception {
        System.out.println(board);
        int num = 0;
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT INTO board(board_title,board_contents,board_writer,create_date) VALUES (?,?,?,NOW())";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, board.getBoardTitle());
            stmt.setString(2, board.getBoardContent());
            stmt.setString(3, board.getBoardWriter());
            num = stmt.executeUpdate();
            System.out.println("성공했냐 ? " + num);
        } finally {
            stmt.close();
        }
        return num;
    }

    public int updateBoard(Connection conn, Board board) throws Exception {
        PreparedStatement pstmt = null;
        System.out.println("board = " + board);
        int num = 0;
        try {
            String sql = "UPDATE board SET board_title = ?, board_contents = ? WHERE board_no = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, board.getBoardTitle());
            pstmt.setString(2, board.getBoardContent());
            pstmt.setInt(3, board.getBoardNo());
            num = pstmt.executeUpdate();
        } finally {
            pstmt.close();
        }
        return num;
    }

    public List<CommentDto> getBoardComment(Connection conn, int boardNo, int rowPerPage, int beginRow) throws Exception {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CommentDto> list = new ArrayList<>();
        int num = 0;
        try {
            String sql = "SELECT comment_no,board_no,member_id,comment_content,comment_date FROM comment WHERE board_no = ? ORDER BY comment_no DESC LIMIT ?,?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, boardNo);
            pstmt.setInt(2, beginRow);
            pstmt.setInt(3, rowPerPage);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CommentDto commentDto = new CommentDto();
                commentDto.setCommentNo(rs.getInt("comment_no"));
                commentDto.setBoardNo(rs.getInt("board_no"));
                commentDto.setMemberId(rs.getString("member_id"));
                commentDto.setCommentContent(rs.getString("comment_content"));
                commentDto.setCommentDate(rs.getString("comment_date"));
                list.add(commentDto);
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return list;
    }

    public int cmInsert(Connection conn, int boardNo, String memberId, String commentContent) throws Exception {
        int num = 0;
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT INTO comment(board_no,member_id,comment_content,comment_date) VALUES (?,?,?,NOW())";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, boardNo);
            stmt.setString(2, memberId);
            stmt.setString(3, commentContent);
            num = stmt.executeUpdate();
        } finally {
            stmt.close();
        }
        return num;
    }

    public int getBoardCommentTotal(Connection conn, int boardNo) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int result = 0;
        try {
            String sql = "select count(*) FROM comment WHERE board_no = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, boardNo);
            rs = stmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
        return result;
    }

    public int cmDelete(Connection conn, int commentNo) throws Exception {
        int num = 0;
        PreparedStatement stmt = null;
        try {
            String sql = "DELETE FROM comment WHERE comment_no = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, commentNo);
            num = stmt.executeUpdate();
        } finally {
            stmt.close();
        }
        return num;
    }

    public int cmUpdate(Connection conn, int commentNo, String cmUpdateContent) throws Exception {
        int num = 0;
        PreparedStatement stmt = null;
        try {
            String sql = "UPDATE comment SET comment_content = ? WHERE comment_no = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cmUpdateContent);
            stmt.setInt(2, commentNo);
            num = stmt.executeUpdate();
        } finally {
            stmt.close();
        }
        return num;


    }
}





