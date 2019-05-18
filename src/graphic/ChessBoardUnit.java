package graphic;

import logic.Coordinate;
import logic.Piece;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

import static graphic.GUI.*;
import static javax.swing.JOptionPane.showMessageDialog;

public class ChessBoardUnit extends JButton {
    private final Coordinate unitCoordinates;
    private Piece piece;
    private Color defaultColor;
    private static ChessBoardUnit clickedUnit = null;
    private boolean whiteCheck;
    private boolean blackCheck;


    ChessBoardUnit(Coordinate unitCoordinates) {
        this(unitCoordinates, null);
    }

    ChessBoardUnit(Coordinate unitCoordinates, Piece piece) {
        this.unitCoordinates = unitCoordinates;
        this.piece = piece;
        if ((unitCoordinates.getX() + unitCoordinates.getY()) % 2 == 0) {
            defaultColor = Color.WHITE;
            this.setBackground(defaultColor);
        } else {
            defaultColor = Color.BLACK;
            this.setBackground(defaultColor);
        }

        if (piece != null) {
            this.setMargin(new Insets(0, 0, 0, 0));
            this.setIcon(piece.getIcon());
        }
        addListener();
    }

    private static void updateChecks() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessBoard[i][j].setBlackCheck(false);
                chessBoard[i][j].setWhiteCheck(false);
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece thisPiece = chessBoard[i][j].getPiece();
                if (thisPiece == null) {
                    continue;
                }
                ArrayList<Coordinate> checkedUnits = thisPiece.getAllMoves(new Coordinate(i, j), chessBoard);
                for (Coordinate checked : checkedUnits) {
                    int x = checked.getX();
                    int y = checked.getY();
                    if (thisPiece.isWhite()) {
                        chessBoard[x][y].setBlackCheck(true);
                    } else {
                        chessBoard[x][y].setWhiteCheck(true);
                    }
                }
            }
        }
    }

    private static Coordinate findKing(boolean isWhite) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece thisPiece = chessBoard[i][j].getPiece();
                if (thisPiece == null) {
                    continue;
                }
                if (thisPiece.getType().equals("King") && thisPiece.isWhite() == isWhite) {
                    return new Coordinate(i, j);
                }
            }
        }
        return null;
    }

    private static boolean checkColorChecked(boolean isWhite) {
        Coordinate kingPlace = findKing(isWhite);
        if (kingPlace != null) {
            if (isWhite && chessBoard[kingPlace.getX()][kingPlace.getY()].whiteCheck)
                return true;
            return (!isWhite && chessBoard[kingPlace.getX()][kingPlace.getY()].blackCheck);
        }
        return false;
    }

    private static ArrayList<Coordinate> getMovables(Coordinate unitCoordinates, Piece thisPiece) {
        ArrayList<Coordinate> moves = thisPiece.getMoves(unitCoordinates, chessBoard);
        if (checkColorChecked(thisPiece.isWhite())) {
            for (int i = 0; i < moves.size(); i++) {
                Coordinate move = moves.get(i);
                Piece targetPiece = chessBoard[move.getX()][move.getY()].getPiece();
                move(chessBoard[move.getX()][move.getY()], chessBoard[unitCoordinates.getX()][unitCoordinates.getY()]);
                updateChecks();
                if (checkColorChecked(thisPiece.isWhite())) {
                    moves.remove(i);
                    i--;
                }
                move(chessBoard[unitCoordinates.getX()][unitCoordinates.getY()], chessBoard[move.getX()][move.getY()]);
                if (targetPiece != null) {
                    chessBoard[move.getX()][move.getY()].setPiece(targetPiece);
                    chessBoard[move.getX()][move.getY()].setIcon(targetPiece.getIcon());

                }
                updateChecks();
            }
        }
        return moves;
    }

    private static void colorizeMovables(Coordinate unitCoordinates, Piece thisPiece) {
        ArrayList<Coordinate> moves = getMovables(unitCoordinates, thisPiece);
        for (Coordinate move : moves) {
            for (Object o : choosePanel.getComponents()) {
                if (o instanceof ChessBoardUnit) {
                    if (((ChessBoardUnit) o).unitCoordinates.equals(move)) {
                        if (((ChessBoardUnit) o).piece == null) {
                            ((ChessBoardUnit) o).setBackground(Color.GREEN);
                        } else {
                            ((ChessBoardUnit) o).setBackground(Color.RED);
                        }
                    }
                }
            }
        }
    }

    private static void undoColorizeMovables(Coordinate unitCoordinates, Piece thisPiece) {
        ArrayList<Coordinate> moves = thisPiece.getMoves(unitCoordinates, chessBoard);
        for (Coordinate move : moves) {
            for (Object o : choosePanel.getComponents()) {
                if (o instanceof ChessBoardUnit) {
                    if (((ChessBoardUnit) o).unitCoordinates.equals(move)) {
                        ((ChessBoardUnit) o).setBackground(((ChessBoardUnit) o).defaultColor);
                    }
                }
            }
        }
    }

    private static void checkWin() {
        int whiteAvailableMoves = 0;
        int blackAvailableMoves = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece thisPiece = chessBoard[i][j].getPiece();
                if (thisPiece == null) {
                    continue;
                }
                if (thisPiece.isWhite()) {
                    whiteAvailableMoves += getMovables(new Coordinate(i, j), thisPiece).size();
                } else {
                    blackAvailableMoves += getMovables(new Coordinate(i, j), thisPiece).size();
                }
                undoColorizeMovables(new Coordinate(i, j), thisPiece);
            }
        }
        if (checkColorChecked(true) && whiteAvailableMoves == 0 && turn % 2 == 0) {
            System.out.println("Winner is black!");
            mainFrame.dispose();
            showMessageDialog(null, "Winner is black!");
            System.exit(0);
            return;
        } else if (whiteAvailableMoves == 0 && turn % 2 == 0) {
            System.out.println("POT!");
            mainFrame.dispose();
            showMessageDialog(null, "POT!");
            System.exit(0);
            return;
        }
        if (checkColorChecked(false) && blackAvailableMoves == 0 && turn % 2 == 1) {
            System.out.println("Winner is white!");
            mainFrame.dispose();
            showMessageDialog(null, "Winner is white!");
            System.exit(0);
        } else if (blackAvailableMoves == 0 && turn % 2 == 1) {
            System.out.println("POT!");
            mainFrame.dispose();
            showMessageDialog(null, "POT!");
            System.exit(0);
        }

    }

    private static void move(ChessBoardUnit target, ChessBoardUnit source) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoard[i][j].getBackground() == Color.RED || chessBoard[i][j].getBackground() == Color.GREEN) {
                    chessBoard[i][j].setBackground(chessBoard[i][j].defaultColor);
                }
            }
        }
        target.setPiece(source.getPiece());
        source.getPiece().setFirstMove(false);
        target.setIcon(source.getIcon());
        source.setPiece(null);
        System.out.println(source);
        source.setIcon(null);


    }

    private void addListener() {
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (piece != null && ((piece.isWhite() && turn % 2 == 0) || (!piece.isWhite() && turn % 2 == 1)) && clickedUnit == null) {
                    colorizeMovables(((ChessBoardUnit) evt.getSource()).unitCoordinates, piece);
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (piece != null && ((piece.isWhite() && turn % 2 == 0) || (!piece.isWhite() && turn % 2 == 1)) && clickedUnit == null) {
                    undoColorizeMovables(((ChessBoardUnit) evt.getSource()).unitCoordinates, piece);
                }
            }

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ChessBoardUnit target = (ChessBoardUnit) evt.getSource();
                if (clickedUnit == null) {
                    if (piece != null && ((piece.isWhite() && turn % 2 == 0) || (!piece.isWhite()) && turn % 2 == 1)) {
                        setBackground(Color.YELLOW);
                        colorizeMovables(((ChessBoardUnit) evt.getSource()).unitCoordinates, piece);
                        clickedUnit = target;
                    }
                } else {
                    if (clickedUnit == target) {
                        undoColorizeMovables(((ChessBoardUnit) evt.getSource()).unitCoordinates, piece);
                        clickedUnit.setBackground(clickedUnit.defaultColor);
                        clickedUnit = null;
                    } else {
                        if ((((clickedUnit.piece.isWhite() && turn % 2 == 0) || (!clickedUnit.piece.isWhite()) && turn % 2 == 1))
                                && (target.getBackground() == Color.RED || target.getBackground() == Color.GREEN)) {

                            //successful move
                            move(target, clickedUnit);

                            //Pawn Transfer
                            if (target.getPiece().getType().equals("Pawn")
                                    && (target.unitCoordinates.getY() == 0 || target.unitCoordinates.getY() == 7)) {
                                JFrame chooseWindow = new JFrame();
                                chooseWindow.setLayout(new BorderLayout());
                                chooseWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                                chooseWindow.setSize(new Dimension(300, 600));
                                chooseWindow.setResizable(false);
                                JPanel choosePanel = new JPanel();
                                choosePanel.setPreferredSize(new Dimension(300, 600));

                                chooseWindow.add(choosePanel, CENTER);
                                choosePanel.setLayout(new GridLayout(4, 1));
                                choosePanel.setBorder(new LineBorder(Color.BLACK));

                                JButton bishop = new ChooseUnit("Bishop", target.unitCoordinates, chooseWindow);
                                JButton knight = new ChooseUnit("Knight", target.unitCoordinates, chooseWindow);
                                JButton rook = new ChooseUnit("Rook", target.unitCoordinates, chooseWindow);
                                JButton queen = new ChooseUnit("Queen", target.unitCoordinates, chooseWindow);
                                choosePanel.add(bishop);
                                choosePanel.add(knight);
                                choosePanel.add(rook);
                                choosePanel.add(queen);


                                mainFrame.setVisible(false);
                                chooseWindow.setVisible(true);
                                choosePanel.setVisible(true);
                            }

                            //Castling
                            int deltaX = Math.abs(target.unitCoordinates.getX() - clickedUnit.unitCoordinates.getX());
                            if (target.getPiece().getType().equals("King") && deltaX > 1) {
                                if (deltaX == 2) {
                                    ChessBoardUnit targetPrim = chessBoard[target.unitCoordinates.getX() - 1][target.unitCoordinates.getY()];
                                    ChessBoardUnit sourceRook = chessBoard[target.unitCoordinates.getX() + 1][target.unitCoordinates.getY()];
                                    move(targetPrim, sourceRook);
                                }
                                if (deltaX == 3) {
                                    ChessBoardUnit targetPrim = chessBoard[target.unitCoordinates.getX() + 1][target.unitCoordinates.getY()];
                                    ChessBoardUnit sourceRook = chessBoard[target.unitCoordinates.getX() - 1][target.unitCoordinates.getY()];
                                    move(targetPrim, sourceRook);
                                }
                            }

                            updateUI();
                            updateChecks();
                            clickedUnit.setBackground(clickedUnit.defaultColor);
                            clickedUnit = null;
                            turn++;
                            checkWin();
                        }
                    }
                }
            }
        });
    }

    public Piece getPiece() {
        return piece;
    }

    void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean isWhiteCheck() {
        return whiteCheck;
    }

    private void setWhiteCheck(boolean whiteCheck) {
        this.whiteCheck = whiteCheck;
    }

    public boolean isBlackCheck() {
        return blackCheck;
    }

    private void setBlackCheck(boolean blackCheck) {
        this.blackCheck = blackCheck;
    }

    @Override
    public String toString() {
        return "ChessBoardUnit{" +
                "" + unitCoordinates +
                " piece=" + piece +
                " White check = " + whiteCheck +
                " Black check = " + blackCheck +
                '}';
    }
}
