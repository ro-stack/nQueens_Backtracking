import java.util.Scanner;

public class EightQueens
{
    static Scanner scan; // Scanner for user input
    static int N = 8; // Size of board
    static int fRow; // First row
    static int fCol; // First column   
    static int solutionsFound = 0; // Number of solutions found
    static int maxSolutions = Integer.MAX_VALUE; // Maximum number of solutions
    static char[] rowNo = "12345678".toCharArray(); // Convert character of numbers to array for rows
    static boolean hasPrePlacedQueen = false; // Check if a Queen is placed

    // Method for printing a board which has Queens placed on it. 
    static void printBoard(int board[][]) {
        System.out.println("    1   2   3   4   5   6   7   8"); // Column headers
        printBoardLine(); // Calls printBoardLine for Row numbers and board design
        for (int i = 0; i < N; i++) {
            System.out.print(rowNo[i] + " ");
            System.out.print("|");
            // Prints the outline for the board by row
            for (int j = 0; j < N; j++)
                if (board[i][j] == 1) {
                    System.out.print(" Q |");
                } else {
                    System.out.print("   |");
                } // Prints a Q which represents where a Queen should go, or blank if else
            System.out.println();
            printBoardLine(); // Calls printBoardLine for board design
        }
    }

    // Method used to print a line on the board for design purposes
    static void printBoardLine() {
        System.out.print("  +"); // Prints a plus sign
        for( int i = 0;  i < N;  i += 1 ) {
            System.out.print("---+");
        } // Prints the joining part of the line for all rows
        System.out.println();
    }

    // Method to check whether or not a Queen can be placed in position
    static boolean toPlaceOrNotToPlace(int board[][], int row, int col) {
        int i, j;
        // Check all columns of the row, to the left of the column.
        for (i = 0; i < N; i++) {
            if (board[row][i] == 1)
                return false; 
        }
        // Check the diagonal collision to the North West of the row and column.
        for (i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1)
                return false;
        }
        // Check the diagonal collison to the South West of the row and column.
        for (i = row, j = col; j >= 0 && i < N; i++, j--) {
            if (board[i][j] == 1)
                return false;
        }
        return true;
    }

    // Method that uses backtracking to solve the solutions for the input row/column
    static boolean theBoardSolver(int board[][], int col) {
        if (col >= N) {
            solutionsFound += 1;
            System.out.println();
            System.out.println( "Solution "+solutionsFound+":"); // Counts number of solutions
            printBoard( board ); // Print board as the solutions are found
            if( solutionsFound >= maxSolutions ) {
                return true;    // Done no more possibilities
            }
            return false;   // Backtrack and keep looking for solution
        }
        // If there is a pre-placed queen,
        // When theBoardSolver gets to column fCol, don't try every row, only try fRow.
        if( hasPrePlacedQueen  &&  col == fCol ) { 
            // If fRow,fCol doesn't work, then backtrack.
            if (toPlaceOrNotToPlace(board, fRow, fCol)) {
                board[fRow][fCol] = 1;
                if (theBoardSolver(board, fCol + 1)) {
                    return true;
                }
                // Backtracking
                board[fRow][fCol] = 0;
            }
        }
        else {
            // Try every row of column 'col'.
            for (int i = 0; i < N; i++) {
                if (toPlaceOrNotToPlace(board, i, col)) {
                    board[i][col] = 1;
                    if (theBoardSolver(board, col + 1)) {
                        return true;
                    }
                    // Backtracking
                    board[i][col] = 0; 
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        scan = new Scanner(System.in); // new Scanner        
        int[][] board = new int[N][N]; // Creates new board that's 8 x 8
        // Instructions for user:
        System.out.println("OPTIONAL: Enter the row and column for first queen to be pre-placed.");
        System.out.println("FORMAT  : 1 , 2  ---> (Row space Comma space Column.)");
        System.out.println("ELSE    : Enter nothing for all eight queen possibilities.");
        while(true) {
            String s = scan.nextLine(); // New string for input
            if( s.trim().length() < 1 ) {
                break;  // escape while loop
            }
            // If user entered anything, then scan it.
            Scanner scan2 = new Scanner( s );
            if( scan2.hasNextInt() ) {
                fRow = scan2.nextInt(); // User enters row
                if( scan2.hasNext(",") ) { // Check that ' , ' has been entered
                    String junk1 = scan2.next(","); 
                    if( scan2.hasNextInt() ) {
                        fCol = scan2.nextInt(); // User enters column
                        // If fRow,fCol are valid...
                        // Normalise row and column from array form
                        if( fRow > 0  && fRow <= N  &&  fCol > 0  &&  fCol <= N ) {
                            hasPrePlacedQueen = true;
                            fRow -= 1;
                            fCol -= 1;
                            break;  
                        }
                        System.out.println("The row and column must be on an "+N+" x "+N+" board.");
                        continue;   // Return to start of loop 
                    }
                }
            }
            System.out.println("Input not recognized.  Example:  3 , 4"); // Input in wrong format
            System.out.println("Or enter nothing to skip pre-placing a queen on the board.");
        }
        theBoardSolver(board, 0);  // If nothings entered then it prints every 8x8 solution
        if( solutionsFound < 1 ) {
            System.out.println("No solution found.");
            // If solutions are less than 1, then it means there are no solutions found
        }
    }
}