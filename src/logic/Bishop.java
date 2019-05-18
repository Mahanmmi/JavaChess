package logic;

import graphic.ChessBoardUnit;

import java.util.ArrayList;

public class Bishop extends Piece {
    public Bishop(boolean isWhite) {
        super("Bishop", isWhite);
    }

    @Override
    public ArrayList<Coordinate> getAllMoves(Coordinate coordinate, ChessBoardUnit[][] chessBoard) {
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        int x = coordinate.getX();
        int y = coordinate.getY();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == x && j == y) {
                    continue;
                }
                if (((i - x) == (j - y) || (i - x) == (y - j))) {
                    coordinates.add(new Coordinate(i, j));
                }
            }
        }

        boolean flag = false;
        for (int i = x + 1, j = y + 1; i < 8 && j < 8; i++, j++) {
            flag = RookBishopRemover(coordinates, new Coordinate(i, j), chessBoard, flag);
        }

        flag = false;
        for (int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
            flag = RookBishopRemover(coordinates, new Coordinate(i, j), chessBoard, flag);
        }

        flag = false;
        for (int i = x - 1, j = y + 1; i >= 0 && j < 8; i--, j++) {
            flag = RookBishopRemover(coordinates, new Coordinate(i, j), chessBoard, flag);
        }


        flag = false;
        for (int i = x + 1, j = y - 1; i < 8 && j >= 0; i++, j--) {
            flag = RookBishopRemover(coordinates, new Coordinate(i, j), chessBoard, flag);
        }

        return coordinates;
    }
}
