package service;

import dto.Board;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IBoardListService {
  List<Board> getBoardList(int rowPerPage, int beginRow);


   int getBoardListTotal();

    Board getBoardDetail(int boardNo);

    boolean good(int boardNo, String memberId);

    int goodCheck(int boardNo, String memberId);

    int addBoardList(Board board);

}
