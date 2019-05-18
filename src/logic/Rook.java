package logic;

import graphic.ChessBoardUnit;

import java.util.ArrayList;

public class Rook extends AbstractPiece {
    public Rook(boolean isWhite) {
        super("Rook", isWhite);
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
                if ((j == y) || (i == x)) {
                    coordinates.add(new Coordinate(i, j));
                }
            }
        }

        boolean flag = false;
        for (int i = x + 1; i < 8; i++) {
            flag = RookBishopRemover(coordinates, new Coordinate(i, y), chessBoard, flag);
        }

        flag = false;
        for (int i = x - 1; i >= 0; i--) {
            flag = RookBishopRemover(coordinates, new Coordinate(i, y), chessBoard, flag);
        }

        flag = false;
        for (int i = y + 1; i < 8; i++) {
            flag = RookBishopRemover(coordinates, new Coordinate(x, i), chessBoard, flag);
        }

        flag = false;
        for (int i = y - 1; i >= 0; i--) {
            flag = RookBishopRemover(coordinates, new Coordinate(x, i), chessBoard, flag);
        }

        return coordinates;
    }
}
