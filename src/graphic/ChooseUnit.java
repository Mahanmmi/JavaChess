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
                Piece newPiece = null;
                if(textt.equals("Bishop")){
                    newPiece = new Bishop(chessBoard[coordinate.getX()][coordinate.getY()].getPiece().isWhite());
                }
                else if(textt.equals("Knight")){
                    newPiece = new Knight(chessBoard[coordinate.getX()][coordinate.getY()].getPiece().isWhite());
                }
                else if(textt.equals("Rook")){
                    newPiece = new Rook(chessBoard[coordinate.getX()][coordinate.getY()].getPiece().isWhite());
                }
                else if(textt.equals("Queen")){
                    newPiece = new Queen(chessBoard[coordinate.getX()][coordinate.getY()].getPiece().isWhite());
                }
                chessBoard[coordinate.getX()][coordinate.getY()].setPiece(newPiece);
                chessBoard[coordinate.getX()][coordinate.getY()].setIcon(newPiece.getIcon());
                mainFrame.setVisible(true);
                chooseWindow.dispose();
            }
        });
    }

}
