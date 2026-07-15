import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

public class Checkers extends JPanel {
    private JButton gameModeButton;
    private JButton resignButton;
    private JButton undoButton;
    private JLabel message;

    private Board board;
    private Stack<int[][]> boardStack;

    private boolean playerVsComputer;

    public static void main(String[] args) {
        JFrame window = new JFrame("Checkers");
        Checkers content = new Checkers();
        window.setContentPane(content);
        window.pack();
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();

        window.setLocation((screensize.width - window.getWidth()) / 2,
                (screensize.height - window.getHeight()) / 2);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setVisible(true);
    }

    public Checkers() {
        setLayout(null);
        setPreferredSize(new Dimension(350, 250));
        setBackground(new Color(50, 50, 50)); 

        board = new Board();
        add(board);
        gameModeButton = new JButton("Game Mode");
        gameModeButton.addActionListener(this::gameModeClicked);

        resignButton = new JButton("Resign");
        resignButton.addActionListener(this::resignClicked);

        undoButton = new JButton("Undo");
        undoButton.addActionListener(this::undoClicked);

        message = new JLabel("", JLabel.CENTER);
        message.setFont(new Font("Serif", Font.BOLD, 14));
        message.setForeground(Color.WHITE); //text color

        boardStack = new Stack<>();
        boardStack.push(copyBoardState(board.getBoardState()));

        playerVsComputer = true;

        add(gameModeButton);
        add(resignButton);
        add(undoButton);
        add(message);

        board.setBounds(20, 20, 164, 164);
        gameModeButton.setBounds(210, 20, 120, 30);
        resignButton.setBounds(210, 80, 120, 30);
        undoButton.setBounds(210, 140, 120, 30);
        message.setBounds(0, 200, 350, 30);
    }

    private int[][] copyBoardState(int[][] boardState) {
        int[][] copy = new int[8][8];
        for (int i = 0; i < 8; i++) {
            System.arraycopy(boardState[i], 0, copy[i], 0, 8);
        }
        return copy;
    }

    private void gameModeClicked(ActionEvent evt) {
        int option = JOptionPane.showOptionDialog(null,
                "Choose game mode:", "Game Mode",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new String[]{"Player vs. Player", "Player vs. Computer"}, "Player vs. Player");

        playerVsComputer = (option == JOptionPane.NO_OPTION);

        board.setUpGame();
        if (playerVsComputer) {
            message.setText("Red: Make your move.");
        } else {
            message.setText("Red: Make your move.");
        }
        gameModeButton.setEnabled(false);
        resignButton.setEnabled(true);
        undoButton.setEnabled(true);
        repaint();
        if (!playerVsComputer && !board.isRedsTurn()) {
            computerTurn();
        }
    }
//--------------------------------------
    private void resignClicked(ActionEvent evt) {
        if (board.isRedsTurn()) {
            gameOver("BLACK wins. RED resigns.");
        } else {
            gameOver("RED wins. BLACK resigns.");
        }
    }

    private void undoClicked(ActionEvent evt) {
        System.out.println("Undo button clicked.");
        if (boardStack.size() > 1) {
            boardStack.pop();
            int[][] previousBoardState = boardStack.peek();
            System.out.println("Previous board state: " + Arrays.deepToString(previousBoardState));
            board.setBoard(previousBoardState);
            board.repaint();
            if (board.isRedsTurn()) {
                message.setText("Red: Make your move.");
            } else {
                message.setText("Black: Make your move.");
            }
        } else {
            System.out.println("No moves to undo.");
        }
    }

    private void gameOver(String str) {
        message.setText(str);
        if (str.contains("RED")) {
            JOptionPane.showMessageDialog(this, "RED wins!");
        } else {
            JOptionPane.showMessageDialog(this, "BLACK wins!");
        }
        gameModeButton.setEnabled(true);
        resignButton.setEnabled(false);
        undoButton.setEnabled(false);
    }

    private void computerTurn() {
        if (!board.isRedsTurn()) {
            board.makeComputerMove();
            repaint();
            if (board.isRedsTurn()) {
                message.setText("Red: Make your move.");
            }
        }
        if (isGameOver()) {
            gameOver("RED wins. BLACK cannot move.");
        }
    }

    private boolean isGameOver() {
        return board.getLegalMoves().isEmpty();
    }

    private class Board extends JPanel implements ActionListener, MouseListener {


        int[][] getBoardState() {
            return board;
        }
        private static final int EMPTY = 0;
        private static final int RED = 1;
        private static final int RED_KING = 2;
        private static final int BLACK = 3;
        private static final int BLACK_KING = 4;

        private int[][] board;
        private boolean redsTurn;
        private ArrayList<int[]> legalMoves;
        private ArrayList<int[]> highlightedMoves;
        private int selectedRow;
        private int selectedCol;
        void setBoard(int[][] newBoard) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    board[i][j] = newBoard[i][j];
                }
            }
        }

        Board() {
            setBackground(Color.BLACK);
            addMouseListener(this);
            board = new int[8][8];
            setUpGame();
            highlightedMoves = new ArrayList<>();
        }

        void setUpGame() {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if (row % 2 == col % 2) {
                        if (row < 3)
                            board[row][col] = BLACK;
                        else if (row > 4)
                            board[row][col] = RED;
                        else
                            board[row][col] = EMPTY;
                    } else {
                        board[row][col] = EMPTY;
                    }
                }
            }
            redsTurn = true;
            legalMoves = getLegalMoves();
            selectedRow = -1;
            selectedCol = -1;
        }

        boolean isRedsTurn() {
            return redsTurn;
        }
//-----------------------------------------------------------------
        ArrayList<int[]> getLegalMoves() {
            ArrayList<int[]> moves = new ArrayList<>();
            boolean hasCaptureMove = false;
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if (board[row][col] == (redsTurn ? RED : BLACK) || board[row][col] == (redsTurn ? RED_KING : BLACK_KING)) {
                        if (canMove(row, col, row + 1, col + 1)) {
                            moves.add(new int[]{row, col, row + 1, col + 1});
                            if (canCapture(row, col, row + 2, col + 2)) {
                                hasCaptureMove = true;
                            }
                        }
                        if (canMove(row, col, row - 1, col + 1)) {
                            moves.add(new int[]{row, col, row - 1, col + 1});
                            if (canCapture(row, col, row - 2, col + 2)) {
                                hasCaptureMove = true;
                            }
                        }
                        if (canMove(row, col, row + 1, col - 1)) {
                            moves.add(new int[]{row, col, row + 1, col - 1});
                            if (canCapture(row, col, row + 2, col - 2)) {
                                hasCaptureMove = true;
                            }
                        }
                        if (canMove(row, col, row - 1, col - 1)) {
                            moves.add(new int[]{row, col, row - 1, col - 1});
                            if (canCapture(row, col, row - 2, col - 2)) {
                                hasCaptureMove = true;
                            }
                        }
                    }
                }
            }
            if (hasCaptureMove) {
                ArrayList<int[]> captureMoves = new ArrayList<>();
                for (int[] move : moves) {
                    int fromRow = move[0];
                    int fromCol = move[1];
                    int toRow = move[2];
                    int toCol = move[3];
                    if (isCaptureMove(fromRow, fromCol, toRow, toCol)) {
                        captureMoves.add(move);
                    }
                }
                if (!captureMoves.isEmpty()) {
                    return captureMoves;
                }
            }
            return moves;
        }

        boolean canMove(int fromRow, int fromCol, int toRow, int toCol) {
            if (toRow < 0 || toRow >= 8 || toCol < 0 || toCol >= 8 || board[toRow][toCol] != EMPTY)
                return false;
            if (board[fromRow][fromCol] == RED && toRow > fromRow)
                return false;
            if (board[fromRow][fromCol] == BLACK && toRow < fromRow)
                return false;
            return true;
        }

        boolean canCapture(int fromRow, int fromCol, int toRow, int toCol) {
            if (toRow < 0 || toRow >= 8 || toCol < 0 || toCol >= 8 || board[toRow][toCol] != EMPTY)
                return false;
            int opponentPiece = (redsTurn ? BLACK : RED);
            int opponentKingPiece = (redsTurn ? BLACK_KING : RED_KING);
            int middlePieceRow = (fromRow + toRow) / 2;
            int middlePieceCol = (fromCol + toCol) / 2;
            if (board[middlePieceRow][middlePieceCol] != opponentPiece && board[middlePieceRow][middlePieceCol] != opponentKingPiece)
                return false;
            return true;
        }


        boolean isCaptureMove(int fromRow, int fromCol, int toRow, int toCol) {
            if (toRow < 0 || toRow >= 8 || toCol < 0 || toCol >= 8)
                return false;
            int opponentPiece = (redsTurn ? BLACK : RED);
            int opponentKingPiece = (redsTurn ? BLACK_KING : RED_KING);
            int middlePieceRow = (fromRow + toRow) / 2;
            int middlePieceCol = (fromCol + toCol) / 2;
            if (board[middlePieceRow][middlePieceCol] != opponentPiece && board[middlePieceRow][middlePieceCol] != opponentKingPiece)
                return false;
            return true;
        }

        void makeMove(int fromRow, int fromCol, int toRow, int toCol) {
            // Check if it's a capture move
            int middlePieceRow = (fromRow + toRow) / 2;
            int middlePieceCol = (fromCol + toCol) / 2;

            if (Math.abs(fromRow - toRow) == 2 && Math.abs(fromCol - toCol) == 2) {
                // Capture move, remove the captured piece
                board[middlePieceRow][middlePieceCol] = EMPTY;
            }

            // Move the piece to the new position
            board[toRow][toCol] = board[fromRow][fromCol];
            board[fromRow][fromCol] = EMPTY;

            // Push a copy of the current board state onto the stack
            boardStack.push(copyBoardState(board));

            // Check if the moved piece becomes a king
            if (toRow == 0 && board[toRow][toCol] == RED) {
                board[toRow][toCol] = RED_KING;
            }
            if (toRow == 7 && board[toRow][toCol] == BLACK) {
                board[toRow][toCol] = BLACK_KING;
            }
        }


        void makeComputerMove() {
            if (!legalMoves.isEmpty()) {
                ArrayList<int[]> captureMoves = new ArrayList<>();
                for (int[] move : legalMoves) {
                    if (isCaptureMove(move[0], move[1], move[2], move[3])) {
                        captureMoves.add(move);
                    }
                }
                if (!captureMoves.isEmpty()) {
                    // Prioritize capturing moves if available
                    int[] move = captureMoves.get(0); // Select the first capturing move
                    makeMove(move[0], move[1], move[2], move[3]);
                } else {
                    // If no capturing moves available, select a random move
                    Random random = new Random();
                    int[] move = legalMoves.get(random.nextInt(legalMoves.size()));
                    makeMove(move[0], move[1], move[2], move[3]);
                }
                redsTurn = true; // After the computer's move, it becomes the player's turn
                legalMoves = getLegalMoves(); // Update legal moves for the player
                repaint(); // Repaint the board
            }
            if (isGameOver()) { // Check if the game is over after the computer's move
                gameOver("RED wins. BLACK cannot move.");
            }
        }
        

//=---------------------------------------------------

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(Color.black);
            g.drawRect(0, 0, getSize().width - 1, getSize().height - 1);
            g.drawRect(1, 1, getSize().width - 3, getSize().height - 3);

            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if (row % 2 == col % 2)
                        g.setColor(Color.LIGHT_GRAY);
                    else
                        g.setColor(Color.GRAY);
                    g.fillRect(2 + col * 20, 2 + row * 20, 20, 20);
                    switch (board[row][col]) {
                        case RED:
                            g.setColor(Color.RED);
                            g.fillOval(4 + col * 20, 4 + row * 20, 15, 15);
                            break;
                        case BLACK:
                            g.setColor(Color.BLACK);
                            g.fillOval(4 + col * 20, 4 + row * 20, 15, 15);
                            break;
                        case RED_KING:
                            g.setColor(Color.RED);
                            g.fillOval(4 + col * 20, 4 + row * 20, 15, 15);
                            g.setColor(Color.WHITE);
                            g.drawString("K", 7 + col * 20, 16 + row * 20);
                            break;
                        case BLACK_KING:
                            g.setColor(Color.BLACK);
                            g.fillOval(4 + col * 20, 4 + row * 20, 15, 15);
                            g.setColor(Color.WHITE);
                            g.drawString("K", 7 + col * 20, 16 + row * 20);
                            break;
                    }
                }
            }

            if (selectedRow != -1 && selectedCol != -1) {
                g.setColor(Color.YELLOW);
                g.drawRect(2 + selectedCol * 20, 2 + selectedRow * 20, 19, 19);
                g.drawRect(3 + selectedCol * 20, 3 + selectedRow * 20, 17, 17);
            }

            if (!highlightedMoves.isEmpty()) {
                g.setColor(new Color(0, 255, 255, 128)); // Semi-transparent cyan
                for (int[] move : highlightedMoves) {
                    int col = move[1];
                    int row = move[0];
                    g.fillRect(2 + col * 20, 2 + row * 20, 20, 20);
                }
            }
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            // actionPerformed logic if needed
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            int col = (e.getX() - 2) / 20;
            int row = (e.getY() - 2) / 20;
            if (row >= 0 && row < 8 && col >= 0 && col < 8) {
                selectedRow = row;
                selectedCol = col;
                legalMoves = getLegalMovesForPiece(selectedRow, selectedCol);
                highlightAvailableMoves(legalMoves);
                repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int col = (e.getX() - 2) / 20;
            int row = (e.getY() - 2) / 20;
            if (selectedRow != -1 && selectedCol != -1 && row >= 0 && row < 8 && col >= 0 && col < 8) {
                for (int[] move : legalMoves) {
                    if (move[2] == row && move[3] == col) {
                        makeMove(selectedRow, selectedCol, row, col);
                        if (redsTurn && row == 0) {
                            board[row][col] = RED_KING;
                        } else if (!redsTurn && row == 7) {
                            board[row][col] = BLACK_KING;
                        }
                        redsTurn = !redsTurn;
                        legalMoves = getLegalMoves();
                        selectedRow = -1;
                        selectedCol = -1;
                        highlightedMoves.clear();
                        if (playerVsComputer && !redsTurn) {
                            computerTurn();
                        }
                        repaint();
                        break;
                    }
                }
            } else {
                selectedRow = -1;
                selectedCol = -1;
                highlightedMoves.clear();
                repaint();
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        ArrayList<int[]> getLegalMovesForPiece(int row, int col) {
            ArrayList<int[]> moves = new ArrayList<>();
            if (board[row][col] == (redsTurn ? RED : BLACK) || board[row][col] == (redsTurn ? RED_KING : BLACK_KING)) {
                // Check capturing moves
                for (int dRow = -2; dRow <= 2; dRow += 4) {
                    for (int dCol = -2; dCol <= 2; dCol += 4) {
                        int toRow = row + dRow;
                        int toCol = col + dCol;
                        int captureRow = row + dRow / 2;
                        int captureCol = col + dCol / 2;
                        if (canCapture(row, col, toRow, toCol)) {
                            moves.add(new int[]{row, col, toRow, toCol});
                        }
                    }
                }
                // Check regular moves
                if (moves.isEmpty()) {
                    if (canMove(row, col, row + 1, col + 1))
                        moves.add(new int[]{row, col, row + 1, col + 1});
                    if (canMove(row, col, row - 1, col + 1))
                        moves.add(new int[]{row, col, row - 1, col + 1});
                    if (canMove(row, col, row + 1, col - 1))
                        moves.add(new int[]{row, col, row + 1, col - 1});
                    if (canMove(row, col, row - 1, col - 1))
                        moves.add(new int[]{row, col, row - 1, col - 1});
                }
            }
            return moves;
        }


        void highlightAvailableMoves(ArrayList<int[]> moves) {
            highlightedMoves.clear();
            for (int[] move : moves) {
                highlightedMoves.add(new int[]{move[2], move[3]});
            }
        }
    }
}