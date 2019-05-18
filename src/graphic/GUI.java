package graphic;

import logic.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

class GUI {
    static JFrame mainFrame;
    static JPanel choosePanel;
    static ChessBoardUnit[][] chessBoard = new ChessBoardUnit[8][8];
    static int turn = 0;

    GUI() {
        mainFrame = new JFrame();
        choosePanel = new JPanel();
        chessBoard = new ChessBoardUnit[8][8];


        initGUI();


        mainFrame.setVisible(true);
        choosePanel.setVisible(true);
    }

    private void initPieces() {
        //White Pieces
        chessBoard[0][0] = new ChessBoardUnit(new Coordinate(0, 0), new Rook(true));
        chessBoard[7][0] = new ChessBoardUnit(new Coordinate(7, 0), new Rook(true));

        chessBoard[1][0] = new ChessBoardUnit(new Coordinate(1, 0), new Knight(true));
        chessBoard[6][0] = new ChessBoardUnit(new Coordinate(6, 0), new Knight(true));

        chessBoard[2][0] = new ChessBoardUnit(new Coordinate(2, 0), new Bishop(true));
        chessBoard[5][0] = new ChessBoardUnit(new Coordinate(5, 0), new Bishop(true));

        chessBoard[3][0] = new ChessBoardUnit(new Coordinate(3, 0), new Queen(true));

        chessBoard[4][0] = new ChessBoardUnit(new Coordinate(4, 0), new King(true));

        for (int i = 0; i < 8; i++) {
            chessBoard[i][1] = new ChessBoardUnit(new Coordinate(i, 1), new Pawn(true));
        }

        //Black Pieces
        chessBoard[0][7] = new ChessBoardUnit(new Coordinate(0, 7), new Rook(false));
        chessBoard[7][7] = new ChessBoardUnit(new Coordinate(7, 7), new Rook(false));

        chessBoard[1][7] = new ChessBoardUnit(new Coordinate(1, 7), new Knight(false));
        chessBoard[6][7] = new ChessBoardUnit(new Coordinate(6, 7), new Knight(false));

        chessBoard[2][7] = new ChessBoardUnit(new Coordinate(2, 7), new Bishop(false));
        chessBoard[5][7] = new ChessBoardUnit(new Coordinate(5, 7), new Bishop(false));

        chessBoard[3][7] = new ChessBoardUnit(new Coordinate(3, 7), new Queen(false));

        chessBoard[4][7] = new ChessBoardUnit(new Coordinate(4, 7), new King(false));

        for (int i = 0; i < 8; i++) {
            chessBoard[i][6] = new ChessBoardUnit(new Coordinate(i, 6), new Pawn(false));
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 2; j < 6; j++) {
                chessBoard[i][j] = new ChessBoardUnit(new Coordinate(i, j));
            }
        }
    }

    private void initGUI() {
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(new Dimension(700, 700));
        mainFrame.setResizable(false);


        choosePanel.setPreferredSize(new Dimension(500, 500));
        mainFrame.add(choosePanel, BorderLayout.CENTER);

        choosePanel.setLayout(new GridLayout(8, 8));
        choosePanel.setBorder(new LineBorder(Color.BLACK));

        initPieces();
        updateBoard();
    }

    private void updateBoard() {
        while(choosePanel.getComponentCount()!=0) {
            choosePanel.remove(0);
        }
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                System.out.println("(" + i + "," + j + ")" + " : " + chessBoard[i][j]);
                choosePanel.add(chessBoard[j][i]);
            }
        }
    }
}

