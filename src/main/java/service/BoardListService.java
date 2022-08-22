package service;

import dao.BoardDao;
import dto.Board;
import dto.CommentDto;
import util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BoardListService implements IBoardListService {

    @Override
    public List<Board> getBoardList(int rowPerPage, int beginRow) {
        Connection conn = null;
        List<Board> list = null;
        try {
            conn = DBUtil.getConnection();
            // executeUpdate() 실행 시 자동 커밋을 막음

            BoardDao boardDao = new BoardDao();
            list = boardDao.getBoardList(conn, rowPerPage, beginRow);

        } catch (Exception e) {
            e.printStackTrace(); // console에 예외메세지 출력
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public int getBoardListTotal() {
        Connection conn = null;
        int result = 0;
        try {
            conn = DBUtil.getConnection();
            // executeUpdate() 실행 시 자동 커밋을 막음
            result = new BoardDao().getBoardListTotal(conn);
        } catch (Exception e) {
            e.printStackTrace(); // console에 예외메세지 출력
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public Board getBoardDetail(int boardNo) {
        Board board = null;
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            BoardDao boardDao = new BoardDao();

            int check = boardDao.readUpdate(conn, boardNo);
            if (check != 0) {
                board = boardDao.selectRead(conn, boardNo);
            }
            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(board);
        }
        return board;

    }

    @Override
    public boolean good(int boardNo, String memberId) {
        Connection conn = null;
        boolean result = true;
        try {
            conn = DBUtil.getConnection();
            BoardDao boardDao = new BoardDao();
            int check = boardDao.goodCheck(conn, boardNo, memberId);
            if (check == 0) {
                // 좋아요가 없으면
                boardDao.goodInsert(conn, boardNo, memberId);
            } else {
                // 좋아요가 있으면
                boardDao.goodDelete(conn, boardNo, memberId);
                result = false;
            }
            boardDao.goodUpdate(conn, boardNo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public int goodCheck(int boardNo, String memberId) {
        Connection conn = null;
        int check = 0;
        try {
            conn = DBUtil.getConnection();
            BoardDao boardDao = new BoardDao();
            check = boardDao.goodCheck(conn, boardNo, memberId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return check;
    }

    @Override
    public int addBoardList(Board board) {
        System.out.println(board);
        int num = 0;
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            BoardDao boardDao = new BoardDao();
            num = boardDao.insertBoard(conn, board);
            System.out.println("여기도 옴 ? " + num);
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception e1) {
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


    public int modifyBoardList(Board board) {
        System.out.println(board);
        Connection conn = null;
        int num = 0;
        try {
            conn = DBUtil.getConnection();
            BoardDao boardDao = new BoardDao();
            num = boardDao.updateBoard(conn, board);
            System.out.println("-------------------- " + num);
            conn.commit();
        } catch (Exception e) {
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

    public List<CommentDto> getBoardComment(int boardNo, int rowPerPage, int beginRow) {
        Connection conn = null;
        List<CommentDto> list = null;
        try {
            conn = DBUtil.getConnection();
            BoardDao boardDao = new BoardDao();
            list = boardDao.getBoardComment(conn, boardNo, rowPerPage, beginRow);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public int getBoardCommentTotal(int boardNo) {
        Connection conn = null;
        int result = 0;
        try {
            conn = DBUtil.getConnection();
            // executeUpdate() 실행 시 자동 커밋을 막음
            result = new BoardDao().getBoardCommentTotal(conn, boardNo);
        } catch (Exception e) {
            e.printStackTrace(); // console에 예외메세지 출력
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public int cmInsert(int boardNo, String memberId, String commentContent) {
        Connection conn = null;
        int result = 0;
        try {
            conn = DBUtil.getConnection();
            BoardDao boardDao = new BoardDao();
            result = boardDao.cmInsert(conn, boardNo, memberId, commentContent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public int cmDelete(int commentNo) {
        Connection conn = null;
        int result = 0;
        try {
            conn = DBUtil.getConnection();
            BoardDao boardDao = new BoardDao();
            result = boardDao.cmDelete(conn, commentNo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public int cmUpdate(int commentNo, String cmUpdateContent) {
        Connection conn = null;
        int result = 0;
        try {
            conn = DBUtil.getConnection();
            BoardDao boardDao = new BoardDao();
            result = boardDao.cmUpdate(conn, commentNo, cmUpdateContent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}


