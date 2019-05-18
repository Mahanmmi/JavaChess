package logic;

import graphic.ChessBoardUnit;

import java.util.ArrayList;

public class Pawn extends AbstractPiece {
    public Pawn(boolean isWhite) {
        super("Pawn", isWhite);
    }

    @Override
    public ArrayList<Coordinate> getAllMoves(Coordinate coordinate,ChessBoardUnit[][] chessBoard) {
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        int x = coordinate.getX();
        int y = coordinate.getY();
        if (isWhite) {
            if (y + 2 < 8 && isFirstMove) {
                coordinates.add(new Coordinate(x, y + 2));
            }
            if (y + 1 < 8) {
                coordinates.add(new Coordinate(x, y + 1));
                if (x + 1 < 8) {
                    coordinates.add(new Coordinate(x + 1, y + 1));
                }
                if (x - 1 >= 0) {
                    coordinates.add(new Coordinate(x - 1, y + 1));
                }
            }
        } else {
            if (y - 2 >= 0 && isFirstMove) {
                coordinates.add(new Coordinate(x, y - 2));
            }
            if (y - 1 >= 0) {
                coordinates.add(new Coordinate(x, y - 1));
                if (x + 1 < 8) {
                    coordinates.add(new Coordinate(x + 1, y - 1));
                }
                if (x - 1 >= 0) {
                    coordinates.add(new Coordinate(x - 1, y - 1));
                }
            }
        }
        return coordinates;
    }

    @Override
    public ArrayList<Coordinate> getMoves(Coordinate coordinate, ChessBoardUnit[][] chessBoard) {
        ArrayList<Coordinate> coordinates = super.getMoves(coordinate, chessBoard);
        int x = coordinate.getX();
        int y = coordinate.getY();
        if (isWhite) {
            if ((y + 2 < 8 && isFirstMove) && (chessBoard[x][y + 1].getAbstractPiece() != null || chessBoard[x][y + 2].getAbstractPiece() != null)) {
                coordinates.remove(new Coordinate(x, y + 2));
            }
            if (y + 1 < 8) {
                if (chessBoard[x][y + 1].getAbstractPiece() != null) {
                    coordinates.remove(new Coordinate(x, y + 1));
                }
                if (x + 1 < 8 && chessBoard[x + 1][y + 1].getAbstractPiece() == null) {
                    coordinates.remove(new Coordinate(x + 1, y + 1));
                }
                if (x - 1 >= 0 && chessBoard[x - 1][y + 1].getAbstractPiece() == null) {
                    coordinates.remove(new Coordinate(x - 1, y + 1));
                }
            }
        } else {
            if ((y - 2 >= 0 && isFirstMove) && (chessBoard[x][y - 1].getAbstractPiece() != null || chessBoard[x][y - 2].getAbstractPiece() != null)) {
                coordinates.remove(new Coordinate(x, y - 2));
            }
            if (y - 1 >= 0) {
                if (chessBoard[x][y - 1].getAbstractPiece() != null) {
                    coordinates.remove(new Coordinate(x, y - 1));
                }
                if (x + 1 < 8 && chessBoard[x + 1][y - 1].getAbstractPiece() == null) {
                    coordinates.remove(new Coordinate(x + 1, y - 1));
                }
                if (x - 1 >= 0 && chessBoard[x - 1][y - 1].getAbstractPiece() == null) {
                    coordinates.remove(new Coordinate(x - 1, y - 1));
                }
            }
        }
        return coordinates;
    }

    @Override
    public ArrayList<Coordinate> getChecked(Coordinate coordinate, ChessBoardUnit[][] chessBoard) {
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        int x = coordinate.getX();
        int y = coordinate.getY();
        if (isWhite) {
            if (y + 1 < 8) {
                if (x + 1 < 8) {
                    coordinates.add(new Coordinate(x + 1, y + 1));
                }
                if (x - 1 >= 0) {
                    coordinates.add(new Coordinate(x - 1, y + 1));
                }
            }
        } else {
            if (y - 1 >= 0) {
                if (x + 1 < 8) {
                    coordinates.add(new Coordinate(x + 1, y - 1));
                }
                if (x - 1 >= 0) {
                    coordinates.add(new Coordinate(x - 1, y - 1));
                }
            }
        }
        return coordinates;
    }
}
