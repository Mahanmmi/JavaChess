package logic;

import graphic.ChessBoardUnit;

import java.util.ArrayList;

public class Knight extends Piece {
    public Knight(boolean isWhite) {
        super("Knight", isWhite);
    }

    @Override
    public ArrayList<Coordinate> getAllMoves(Coordinate coordinate, ChessBoardUnit[][] chessBoard) {
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        int x = coordinate.getX();
        int y = coordinate.getY();

        if (x + 2 < 8 && y + 1 < 8) {
            coordinates.add(new Coordinate(x + 2, y + 1));
        }
        if (x + 1 < 8 && y + 2 < 8) {
            coordinates.add(new Coordinate(x + 1, y + 2));
        }
        if (x + 2 < 8 && y - 1 >= 0) {
            coordinates.add(new Coordinate(x + 2, y - 1));
        }
        if (x - 1 >= 0 && y + 2 < 8) {
            coordinates.add(new Coordinate(x - 1, y + 2));
        }
        if (x - 2 >= 0 && y + 1 < 0) {
            coordinates.add(new Coordinate(x - 2, y + 1));
        }
        if (x + 1 < 8 && y - 2 >= 0) {
            coordinates.add(new Coordinate(x + 1, y - 2));
        }
        if (x - 2 >= 0 && y - 1 >= 0) {
            coordinates.add(new Coordinate(x - 2, y - 1));
        }
        if (x - 1 >= 0 && y - 2 >= 0) {
            coordinates.add(new Coordinate(x - 1, y - 2));
        }

        return coordinates;
    }
}
