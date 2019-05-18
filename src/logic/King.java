package logic;

import graphic.ChessBoardUnit;

import java.util.ArrayList;

public class King extends AbstractPiece {
    public King(boolean isWhite) {
        super("King", isWhite);
    }

    @Override
    public ArrayList<Coordinate> getAllMoves(Coordinate coordinate,ChessBoardUnit[][]chessBoard) {
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        int x = coordinate.getX();
        int y = coordinate.getY();


        if (x + 1 < 8 && y + 1 < 8) {
            coordinates.add(new Coordinate(x + 1, y + 1));
        }
        if (x + 1 < 8) {
            coordinates.add(new Coordinate(x + 1, y));
        }
        if (x + 1 < 8 && y - 1 >= 0) {
            coordinates.add(new Coordinate(x + 1, y - 1));
        }
        if (y + 1 < 8) {
            coordinates.add(new Coordinate(x, y + 1));
        }
        if (y - 1 >= 0) {
            coordinates.add(new Coordinate(x, y - 1));
        }
        if (x - 1 >= 0 && y + 1 < 8) {
            coordinates.add(new Coordinate(x - 1, y + 1));
        }
        if (x - 1 >= 0) {
            coordinates.add(new Coordinate(x - 1, y));
        }
        if (x - 1 >= 0 && y - 1 >= 0) {
            coordinates.add(new Coordinate(x - 1, y - 1));
        }
        return coordinates;
    }

    @Override
    public ArrayList<Coordinate> getMoves(Coordinate coordinate, ChessBoardUnit[][] chessBoard) {
        ArrayList<Coordinate> coordinates = super.getMoves(coordinate, chessBoard);
        //Castling
        if (isFirstMove ) {
            if (isWhite) {
                if (!chessBoard[4][0].isWhiteCheck()
                        && !chessBoard[5][0].isWhiteCheck()
                        && chessBoard[5][0].getAbstractPiece() == null
                        && !chessBoard[6][0].isWhiteCheck()
                        && chessBoard[6][0].getAbstractPiece() == null
                        && chessBoard[7][0].getAbstractPiece().isFirstMove()) {
                    coordinates.add(new Coordinate(6,0));
                }
                if (!chessBoard[4][0].isWhiteCheck()
                        && !chessBoard[3][0].isWhiteCheck()
                        && chessBoard[3][0].getAbstractPiece() == null
                        && !chessBoard[2][0].isWhiteCheck()
                        && chessBoard[2][0].getAbstractPiece() == null
                        && !chessBoard[1][0].isWhiteCheck()
                        && chessBoard[1][0].getAbstractPiece() == null
                        && chessBoard[0][0].getAbstractPiece().isFirstMove()){
                    coordinates.add(new Coordinate(1,0));
                }
            } else {
                if (!chessBoard[4][7].isBlackCheck() && !chessBoard[5][7].isBlackCheck()
                        && chessBoard[5][7].getAbstractPiece() == null
                        && !chessBoard[6][7].isBlackCheck()
                        && chessBoard[6][7].getAbstractPiece() == null
                        && chessBoard[7][7].getAbstractPiece().isFirstMove()) {
                    coordinates.add(new Coordinate(6,7));
                }
                if (!chessBoard[4][7].isBlackCheck()
                        && !chessBoard[3][7].isBlackCheck()
                        && chessBoard[3][7].getAbstractPiece() == null
                        && !chessBoard[2][7].isBlackCheck()
                        && chessBoard[2][7].getAbstractPiece() == null
                        && !chessBoard[1][7].isBlackCheck()
                        && chessBoard[1][7].getAbstractPiece() == null
                        && chessBoard[0][7].getAbstractPiece().isFirstMove()){
                    coordinates.add(new Coordinate(1,7));
                }
            }
        }
        for (int i = 0; i < coordinates.size(); i++) {
            int x = coordinates.get(i).getX();
            int y = coordinates.get(i).getY();
            if(isWhite) {
                if (chessBoard[x][y].isWhiteCheck()) {
                    coordinates.remove(i);
                    i--;
                }
            } else {
                if (chessBoard[x][y].isBlackCheck()) {
                    coordinates.remove(i);
                    i--;
                }
            }
        }

        return coordinates;
    }
}
