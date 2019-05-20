package graphic;

import logic.*;

import javax.swing.*;

import static graphic.GUI.*;

class ChooseUnit extends JButton {
    private Coordinate coordinate;
    private String childText;
    private JFrame chooseWindow;

    ChooseUnit(String text, Coordinate coordinate, JFrame chooseWindow) {
        super(text);
        this.chooseWindow = chooseWindow;
        this.childText = text;
        this.coordinate = coordinate;
        addListener();
    }

    private void addListener() {
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AbstractPiece newAbstractPiece = null;
                switch (childText) {
                    case "Bishop": {
                        newAbstractPiece = new Bishop(chessBoard[coordinate.getX()][coordinate.getY()].getAbstractPiece().isWhite());
                        break;
                    }
                    case "Knight": {
                        newAbstractPiece = new Knight(chessBoard[coordinate.getX()][coordinate.getY()].getAbstractPiece().isWhite());
                        break;
                    }
                    case "Rook": {
                        newAbstractPiece = new Rook(chessBoard[coordinate.getX()][coordinate.getY()].getAbstractPiece().isWhite());
                        break;
                    }
                    case "Queen": {
                        newAbstractPiece = new Queen(chessBoard[coordinate.getX()][coordinate.getY()].getAbstractPiece().isWhite());
                        break;
                    }
                }
                chessBoard[coordinate.getX()][coordinate.getY()].setAbstractPiece(newAbstractPiece);
                chessBoard[coordinate.getX()][coordinate.getY()].setIcon(newAbstractPiece.getIcon());
                changeButtonsState(true);
                chooseWindow.dispose();
                updateBoards();
            }
        });
    }

}
