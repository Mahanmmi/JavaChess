package graphic;

import logic.AbstractPiece;
import logic.Coordinate;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

import static graphic.GUI.*;
import static javax.swing.JOptionPane.showMessageDialog;

public class ChessBoardUnit extends JButton {
    private final Coordinate unitCoordinates;
    private AbstractPiece abstractPiece;
    private Color defaultColor;
    private static ChessBoardUnit clickedUnit = null;
    private boolean whiteCheck;
    private boolean blackCheck;


    ChessBoardUnit(Coordinate unitCoordinates) {
        this(unitCoordinates, null);
    }

    ChessBoardUnit(Coordinate unitCoordinates, AbstractPiece abstractPiece) {
        this.unitCoordinates = unitCoordinates;
        this.abstractPiece = abstractPiece;
        if ((unitCoordinates.getX() + unitCoordinates.getY()) % 2 == 0) {
            defaultColor = Color.WHITE;
            this.setBackground(defaultColor);
        } else {
            defaultColor = Color.BLACK;
            this.setBackground(defaultColor);
        }

        if (abstractPiece != null) {
            this.setMargin(new Insets(0, 0, 0, 0));
            this.setIcon(abstractPiece.getIcon());
            this.setDisabledIcon(abstractPiece.getIcon());
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
                AbstractPiece thisAbstractPiece = chessBoard[i][j].getAbstractPiece();
                if (thisAbstractPiece == null) {
                    continue;
                }
                ArrayList<Coordinate> checkedUnits = thisAbstractPiece.getChecked(new Coordinate(i, j), chessBoard);
                for (Coordinate checked : checkedUnits) {
                    int x = checked.getX();
                    int y = checked.getY();
                    if (thisAbstractPiece.isWhite()) {
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
                AbstractPiece thisAbstractPiece = chessBoard[i][j].getAbstractPiece();
                if (thisAbstractPiece == null) {
                    continue;
                }
                if (thisAbstractPiece.getType().equals("King") && thisAbstractPiece.isWhite() == isWhite) {
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

    private static ArrayList<Coordinate> getMovables(Coordinate unitCoordinates, AbstractPiece thisAbstractPiece) {
        ArrayList<Coordinate> moves = thisAbstractPiece.getMoves(unitCoordinates, chessBoard);

        for (int i = 0; i < moves.size(); i++) {
            Coordinate move = moves.get(i);
            AbstractPiece targetAbstractPiece = chessBoard[move.getX()][move.getY()].getAbstractPiece();
            move(chessBoard[move.getX()][move.getY()], chessBoard[unitCoordinates.getX()][unitCoordinates.getY()]);
            updateChecks();
            if (checkColorChecked(thisAbstractPiece.isWhite())) {
                moves.remove(i);
                i--;
            }
            move(chessBoard[unitCoordinates.getX()][unitCoordinates.getY()], chessBoard[move.getX()][move.getY()]);
            if (targetAbstractPiece != null) {
                chessBoard[move.getX()][move.getY()].setAbstractPiece(targetAbstractPiece);
                chessBoard[move.getX()][move.getY()].setIcon(targetAbstractPiece.getIcon());
                chessBoard[move.getX()][move.getY()].setDisabledIcon(targetAbstractPiece.getIcon());

            }
            updateChecks();
        }

        return moves;
    }

    private static void colorizeMovables(Coordinate unitCoordinates, AbstractPiece thisAbstractPiece) {
        ArrayList<Coordinate> moves = getMovables(unitCoordinates, thisAbstractPiece);
        for (Coordinate move : moves) {
            for (Object o : mainBoard.getComponents()) {
                if (o instanceof ChessBoardUnit) {
                    if (((ChessBoardUnit) o).unitCoordinates.equals(move)) {
                        if (((ChessBoardUnit) o).abstractPiece == null) {
                            ((ChessBoardUnit) o).setBackground(Color.GREEN);
                            ((ChessBoardUnit) o).setEnabled(true);
                        } else {
                            ((ChessBoardUnit) o).setBackground(Color.RED);
                        }
                    }
                }
            }
        }
    }

    private static void undoColorizeMovables(Coordinate unitCoordinates, AbstractPiece thisAbstractPiece) {
        ArrayList<Coordinate> moves = thisAbstractPiece.getMoves(unitCoordinates, chessBoard);
        for (Coordinate move : moves) {
            for (Object o : mainBoard.getComponents()) {
                if (o instanceof ChessBoardUnit) {
                    if (((ChessBoardUnit) o).unitCoordinates.equals(move)) {
                        if (((ChessBoardUnit) o).getBackground() != Color.ORANGE) {
                            if(((ChessBoardUnit) o).getBackground()==Color.GREEN){
                                ((ChessBoardUnit) o).setEnabled(false);
                            }
                            ((ChessBoardUnit) o).setBackground(((ChessBoardUnit) o).defaultColor);
                        }
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
                AbstractPiece thisAbstractPiece = chessBoard[i][j].getAbstractPiece();
                if (thisAbstractPiece == null) {
                    continue;
                }
                if (thisAbstractPiece.isWhite()) {
                    whiteAvailableMoves += getMovables(new Coordinate(i, j), thisAbstractPiece).size();
                } else {
                    blackAvailableMoves += getMovables(new Coordinate(i, j), thisAbstractPiece).size();
                }
                undoColorizeMovables(new Coordinate(i, j), thisAbstractPiece);
            }
        }

        if (checkColorChecked(true) && whiteAvailableMoves == 0 && turn % 2 == 0) {
            System.out.println("Winner is black!");
            showMessageDialog(null, "Winner is black!");
            mainFrame.dispose();
            System.exit(0);
            return;
        } else if (whiteAvailableMoves == 0 && turn % 2 == 0) {
            System.out.println("DRAW!");
            showMessageDialog(null, "DRAW!");
            mainFrame.dispose();
            System.exit(0);
            return;
        }
        if (checkColorChecked(false) && blackAvailableMoves == 0 && turn % 2 == 1) {
            System.out.println("Winner is white!");
            showMessageDialog(null, "Winner is white!");
            mainFrame.dispose();
            System.exit(0);
        } else if (blackAvailableMoves == 0 && turn % 2 == 1) {
            System.out.println("DRAW!");
            showMessageDialog(null, "DRAW!");
            mainFrame.dispose();
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
        target.setAbstractPiece(source.getAbstractPiece());
        target.setIcon(source.getIcon());
        target.setDisabledIcon(source.getIcon());
        source.setAbstractPiece(null);
//        System.out.println(source);
        source.setIcon(null);
        source.setDisabledIcon(null);


    }

    private static void colorizeCheckedKings() {
        if (checkColorChecked(true)) {
            Coordinate whiteKing = findKing(true);
            chessBoard[whiteKing.getX()][whiteKing.getY()].setBackground(Color.ORANGE);
        } else {
            Coordinate whiteKing = findKing(true);
            chessBoard[whiteKing.getX()][whiteKing.getY()].setBackground(chessBoard[whiteKing.getX()][whiteKing.getY()].defaultColor);
        }
        if (checkColorChecked(false)) {
            Coordinate blackKing = findKing(false);
            chessBoard[blackKing.getX()][blackKing.getY()].setBackground(Color.ORANGE);
//            System.out.println("HEY");
        } else {
            Coordinate blackKing = findKing(false);
            chessBoard[blackKing.getX()][blackKing.getY()].setBackground(chessBoard[blackKing.getX()][blackKing.getY()].defaultColor);
//            System.out.println("WAY");
        }
    }


    private void addListener() {
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (((JButton) evt.getSource()).isEnabled() && abstractPiece != null && ((abstractPiece.isWhite() && turn % 2 == 0) || (!abstractPiece.isWhite() && turn % 2 == 1)) && clickedUnit == null) {
                    colorizeMovables(((ChessBoardUnit) evt.getSource()).unitCoordinates, abstractPiece);
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (((JButton) evt.getSource()).isEnabled() && abstractPiece != null && ((abstractPiece.isWhite() && turn % 2 == 0) || (!abstractPiece.isWhite() && turn % 2 == 1)) && clickedUnit == null) {
                    undoColorizeMovables(((ChessBoardUnit) evt.getSource()).unitCoordinates, abstractPiece);
                }
            }

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (((JButton) evt.getSource()).isEnabled()) {
                    ChessBoardUnit target = (ChessBoardUnit) evt.getSource();
                    if (clickedUnit == null) {
                        if (abstractPiece != null && ((abstractPiece.isWhite() && turn % 2 == 0) || (!abstractPiece.isWhite()) && turn % 2 == 1)) {
                            setBackground(Color.YELLOW);
                            colorizeMovables(((ChessBoardUnit) evt.getSource()).unitCoordinates, abstractPiece);
                            clickedUnit = target;
                        }
                    } else {
                        if (clickedUnit == target) {
                            undoColorizeMovables(((ChessBoardUnit) evt.getSource()).unitCoordinates, abstractPiece);
                            clickedUnit.setBackground(clickedUnit.defaultColor);
                            colorizeCheckedKings();
                            clickedUnit = null;
                        } else {
                            if ((((clickedUnit.abstractPiece.isWhite() && turn % 2 == 0) || (!clickedUnit.abstractPiece.isWhite()) && turn % 2 == 1))
                                    && (target.getBackground() == Color.RED || target.getBackground() == Color.GREEN)) {

                                //successful move
                                if (target.abstractPiece != null) {
                                    if (target.abstractPiece.isWhite()) {
                                        newWhiteTakenPieces.add(target.abstractPiece);
                                    } else {
                                        newBlackTakenPieces.add(target.abstractPiece);
                                    }
                                }

                                move(target, clickedUnit);
                                target.getAbstractPiece().setFirstMove(false);

                                //Pawn Promotion
                                boolean isP = false;
                                if (target.getAbstractPiece().getType().equals("Pawn")
                                        && (target.unitCoordinates.getY() == 0 || target.unitCoordinates.getY() == 7)) {
                                    isP = true;

                                    changeButtonsState(false);

                                    JFrame chooseWindow = new JFrame();
                                    chooseWindow.setLayout(new BorderLayout());
                                    chooseWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                                    chooseWindow.setSize(new Dimension(100, 200));
                                    chooseWindow.setResizable(false);
                                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                                    int x = screenSize.width / 2 - chooseWindow.getWidth() / 2;
                                    int y = screenSize.height / 2 - chooseWindow.getHeight() / 2;
                                    chooseWindow.setLocation(x, y);
                                    JPanel choosePanel = new JPanel();
                                    choosePanel.setPreferredSize(new Dimension(100, 200));

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


                                    chooseWindow.setVisible(true);
                                    choosePanel.setVisible(true);
                                }

                                //Castling
                                { //Castling
                                    int deltaX = Math.abs(target.unitCoordinates.getX() - clickedUnit.unitCoordinates.getX());
                                    if (target.getAbstractPiece().getType().equals("King") && deltaX > 1) {
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
                                }

                                updateChecks();
                                clickedUnit.setBackground(clickedUnit.defaultColor);
                                colorizeCheckedKings();
                                clickedUnit = null;
                                turn++;
                                checkWin();
                                if (!isP)
                                    updateBoards();
                            }
                        }
                    }
                }
            }
        });
    }

    public AbstractPiece getAbstractPiece() {
        return abstractPiece;
    }

    void setAbstractPiece(AbstractPiece abstractPiece) {
        this.abstractPiece = abstractPiece;
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
                " abstractPiece=" + abstractPiece +
                " White check = " + whiteCheck +
                " Black check = " + blackCheck +
                '}';
    }
}
