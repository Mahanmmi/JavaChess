package logic;

import graphic.ChessBoardUnit;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class Piece {
    protected String type;
    protected boolean isWhite;
    protected boolean isFirstMove;
    protected ImageIcon imageIcon;

    public ImageIcon getIcon() {
        return imageIcon;
    }

    public Piece(String type, boolean isWhite) {
        this.type = type;
        this.isWhite = isWhite;
        this.isFirstMove = true;
        String imgName;
        if (this.isWhite()) {
            imgName = "White ";
        } else {
            imgName = "Black ";
        }
        if (this.getType().equals("Knight")) {
            imgName += "N";
        } else {
            imgName += this.getType().substring(0, 1);
        }
        imgName += ".png";
        ImageIcon myIcon = new ImageIcon("./ChessPieces/" + imgName);
        imageIcon = new ImageIcon((myIcon.getImage()).getScaledInstance(60, 60, Image.SCALE_AREA_AVERAGING));
    }

    protected static boolean RookBishopRemover(ArrayList<Coordinate> coordinates, Coordinate coordinate, ChessBoardUnit[][] chessBoard, boolean flag) {
        int i = coordinate.getX();
        int j = coordinate.getY();
        if (flag) {
            coordinates.remove(new Coordinate(i, j));
        }
        if (chessBoard[i][j].getPiece() != null) {
            flag = true;
        }
        return flag;
    }

    public abstract ArrayList<Coordinate> getAllMoves(Coordinate coordinate,ChessBoardUnit[][] chessBoard);

    public ArrayList<Coordinate> getMoves(Coordinate coordinate, ChessBoardUnit[][] chessBoard) {
        ArrayList<Coordinate> coordinates = getAllMoves(coordinate,chessBoard);

        for (int i = 0; i < coordinates.size(); i++) {
            Coordinate move = coordinates.get(i);
            int x = move.getX();
            int y = move.getY();
            Piece target = chessBoard[x][y].getPiece();
            if (target != null && target.isWhite == this.isWhite) {
                coordinates.remove(i);
                i--;
            }
        }
        return coordinates;
    }

    public ArrayList<Coordinate> getChecked(Coordinate coordinate, ChessBoardUnit[][] chessBoard){
        return getAllMoves(coordinate,chessBoard);
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public void setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "" +
                "" + type +
                ", isWhite=" + isWhite +
                '}';
    }
}
