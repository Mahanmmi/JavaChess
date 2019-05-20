package graphic;

import logic.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

class GUI {
    static JFrame mainFrame;
    static JPanel mainBoard;
    private static JPanel blackTakenBoard;
    private static JPanel whiteTakenBoard;
    private static JPanel turnShowcase;
    static ChessBoardUnit[][] chessBoard;
    static ArrayList<AbstractPiece> newWhiteTakenPieces;
    static ArrayList<AbstractPiece> newBlackTakenPieces;
    static int turn = 0;

    GUI() {
        mainFrame = new JFrame();
        mainBoard = new JPanel();
        chessBoard = new ChessBoardUnit[8][8];

        blackTakenBoard = new JPanel();
        whiteTakenBoard = new JPanel();
        newWhiteTakenPieces = new ArrayList<>();
        newBlackTakenPieces = new ArrayList<>();
        turnShowcase = new JPanel();


        initGUI();


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = screenSize.width / 2 - mainFrame.getWidth() / 2;
        int y = screenSize.height / 2 - mainFrame.getHeight() / 2;
        mainFrame.setLocation(x, y);

        mainBoard.setVisible(true);
        blackTakenBoard.setVisible(true);
        whiteTakenBoard.setVisible(true);
        turnShowcase.setVisible(true);
        mainFrame.setVisible(true);
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
        mainFrame.setSize(new Dimension(1100, 800));
        mainFrame.setResizable(false);


        mainBoard.setPreferredSize(new Dimension(500, 500));
        blackTakenBoard.setPreferredSize(new Dimension(200, 500));
        whiteTakenBoard.setPreferredSize(new Dimension(200, 500));
        turnShowcase.setPreferredSize(new Dimension(1100, 100));

        mainBoard.setLayout(new GridLayout(8, 8));
        mainBoard.setBorder(new LineBorder(Color.BLACK));

        turnShowcase.setLayout(new GridLayout(1, 1));
        turnShowcase.setBorder(new LineBorder(Color.BLACK));

        blackTakenBoard.setBorder(new LineBorder(Color.BLACK));

        JButton blackHead = new JButton();
        blackHead.setSize(new Dimension(200, 50));
        blackHead.setText("Black Taken Pieces");
        blackHead.setEnabled(false);
        blackHead.setBackground(Color.cyan);
        blackHead.setForeground(Color.black);

        blackTakenBoard.add(blackHead);

        whiteTakenBoard.setBorder(new LineBorder(Color.BLACK));

        JButton whiteHead = new JButton();
        whiteHead.setSize(new Dimension(200, 50));
        whiteHead.setText("White Taken Pieces");
        whiteHead.setEnabled(false);
        whiteHead.setBackground(Color.cyan);
        whiteHead.setForeground(Color.BLACK);
        whiteTakenBoard.add(whiteHead);


        mainFrame.add(blackTakenBoard, BorderLayout.WEST);
        mainFrame.add(whiteTakenBoard, BorderLayout.EAST);
        mainFrame.add(mainBoard, BorderLayout.CENTER);
        mainFrame.add(turnShowcase, BorderLayout.SOUTH);

        initPieces();
        updateBoards();
    }

    static void updateBoards() {
        while (mainBoard.getComponentCount() != 0) {
            mainBoard.remove(0);
        }
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                System.out.println("(" + i + "," + j + ")" + " : " + chessBoard[i][j]);
                mainBoard.add(chessBoard[j][i]);
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoard[i][j].getAbstractPiece() == null) {
                    chessBoard[i][j].setEnabled(false);
                } else {
                    chessBoard[i][j].setEnabled(true);
                }
            }
        }

        for (AbstractPiece whiteTakenPiece : newWhiteTakenPieces) {
            if (whiteTakenBoard.getComponentCount() == 1) {
                JButton tmp = new JButton();
                tmp.setSize(new Dimension(0, 50));
                tmp.setEnabled(false);
                tmp.setVisible(false);
                whiteTakenBoard.add(tmp);
            }
            JButton takenPiece = new JButton(whiteTakenPiece.getIcon());
            takenPiece.setBackground(Color.white);
            takenPiece.setEnabled(false);
            whiteTakenBoard.add(takenPiece);
        }

        for (AbstractPiece blackTakenPiece : newBlackTakenPieces) {
            if (blackTakenBoard.getComponentCount() == 1) {
                JButton tmp = new JButton();
                tmp.setEnabled(false);
                tmp.setVisible(false);
                tmp.setSize(new Dimension(0, 50));
                blackTakenBoard.add(tmp);
            }
            JButton takenPiece = new JButton(blackTakenPiece.getIcon());
            takenPiece.setBackground(Color.black);
            takenPiece.setEnabled(false);
            blackTakenBoard.add(takenPiece);
        }

        while (newWhiteTakenPieces.size() != 0) {
            newWhiteTakenPieces.remove(0);
        }
        while (newBlackTakenPieces.size() != 0) {
            newBlackTakenPieces.remove(0);
        }
        if (turnShowcase.getComponentCount() != 0) {
            turnShowcase.remove(0);
        }
        JButton showcase = new JButton();
        showcase.setBackground((turn % 2 == 0) ? Color.WHITE : Color.BLACK);
        showcase.setText((turn % 2 == 0) ? "WHITE TURN" : "BLACK TURN");
        showcase.setSize(new Dimension(1100, 100));
        showcase.setEnabled(false);
        turnShowcase.add(showcase);
    }
}

