package graphic;

import logic.*;

import javax.swing.*;

import static graphic.GUI.chessBoard;
import static graphic.GUI.mainFrame;

public class ChooseUnit extends JButton {
    private Coordinate coordinate;
    private String textt;
    private JFrame chooseWindow;
    public ChooseUnit(String text, Coordinate coordinate, JFrame chooseWindow) {
        super(text);
        this.chooseWindow = chooseWindow;
        this.textt = text;
        this.coordinate = coordinate;
        addListener();
    }

    private void addListener(){
        addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent evt){
                AbstractPiece newAbstractPiece = null;
                if(textt.equals("Bishop")){
                    newAbstractPiece = new Bishop(chessBoard[coordinate.getX()][coordinate.getY()].getAbstractPiece().isWhite());
                }
                else if(textt.equals("Knight")){
                    newAbstractPiece = new Knight(chessBoard[coordinate.getX()][coordinate.getY()].getAbstractPiece().isWhite());
                }
                else if(textt.equals("Rook")){
                    newAbstractPiece = new Rook(chessBoard[coordinate.getX()][coordinate.getY()].getAbstractPiece().isWhite());
                }
                else if(textt.equals("Queen")){
                    newAbstractPiece = new Queen(chessBoard[coordinate.getX()][coordinate.getY()].getAbstractPiece().isWhite());
                }
                chessBoard[coordinate.getX()][coordinate.getY()].setAbstractPiece(newAbstractPiece);
                chessBoard[coordinate.getX()][coordinate.getY()].setIcon(newAbstractPiece.getIcon());
                mainFrame.setVisible(true);
                chooseWindow.dispose();
            }
        });
    }

}
