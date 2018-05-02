package BattleshipPackage;
import java.util.Random;
import java.util.Scanner;

class Board {
    private int width, length;
    private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private String[][] board, secondaryBoard;
    private static Scanner scan;

    Board(int dimensionsIn, int numberShips, String typeIn) {
        // Both are increased by one to accommodate the coordinate labels
        width = dimensionsIn + 1;
        length = dimensionsIn + 1;
        board = new String[length][width];
        // secondaryBoard is the opponent board visible to the player
        secondaryBoard = new String[length][width];

        // Constructs the grid and its labels
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                // Sets the top left spot of the grid to a blank space
                if (i == 0 && j == 0) {
                    board[i][j] = " ";
                    secondaryBoard[i][j] = " ";
                }
                // Sets the horizontal labels as letters
                else if (i == 0) {
                    board[i][j] = Character.toString(alphabet.charAt(j - 1));
                    secondaryBoard[i][j] = Character.toString(alphabet.charAt(j - 1));
                }
                // Sets the vertical labels as numbers
                else if (j == 0) {
                    board[i][j] = Integer.toString(i);
                    secondaryBoard[i][j] = Integer.toString(i);
                }
                // Sets all other grid spaces to the ~
                else {
                    board[i][j] = "~";
                    secondaryBoard[i][j] = "~";
                }
            }
        }
        positionShips(typeIn, numberShips);
    }

    private void placeShip(int x, int y) {
        String shipMarker = "$";
        board[y][x] = shipMarker;
    }

    private void positionShips(String type, int numberShips) {
        int x, y;

        // If type is Computer, ships will be randomly placed on the grid
        if (type.equalsIgnoreCase("Computer")) {
            for (int i = 0; i < numberShips; i++) {
                x = generateCoordinate(width);
                y = generateCoordinate(length);
                // If the spot is free, a ship will be placed there. Otherwise, i is reduced and another attempt is made
                if (board[y][x].equals("~")) { placeShip(x, y); }
                else { i--; }
            }
        }

        // Otherwise the player will be prompted for coordinates in order to place their own ships
        else {
            int[] response;
            scan = new Scanner(System.in);

            for (int i = 0; i < numberShips; i++) {
                response = getCoordinates("\nEnter a position to place a ship: ", 0);
                x = response[0];
                y = response[1];

                // If the spot is free, place the ship. Otherwise, i is reduced and another attempt is made
                if (board[y][x].equals("~")) { placeShip(x, y); }
                else {
                    System.out.println("There is already a ship at these coordinates.");
                    i--;
                }
            }

            // Show player's board with all ships placed.
            System.out.println("\n----------------------------");
            System.out.println("\n:: Your grid ::");
            printBoard(0);
            System.out.print("\nPress any key to continue. ");
            scan.nextLine();
            System.out.println("\n----------------------------");
        }

    }

    private void printBoard(int type) {
        System.out.println();
        if (type == 0) {
            for (int i = 0; i < this.length; i++) {
                for (int j = 0; j < this.width; j++) {
                    System.out.print(this.board[i][j] + " ");
                }
                System.out.println();
            }
        } else if (type == 1) {
            for (int i = 0; i < this.length; i++) {
                for (int j = 0; j < this.width; j++) {
                    System.out.print(this.secondaryBoard[i][j] + " ");
                }
                System.out.println();
            }
        }
    }

    public int fireUpon(int type) {
        int x, y;

        // If type is 0, that means the user is the one firing upon the computer's ships
        if (type == 0) {
            scan = new Scanner(System.in);
            boolean validData;

            do {
                validData = true;
                System.out.println("\n:: Opponent Grid ::");
                int[] response = getCoordinates("\nEnter coordinates to fire upon: ", 1);
                x = response[0];
                y = response[1];
                if (secondaryBoard[y][x].equals("#")) {
                    System.out.println("These coordinates have already been fired upon.");
                    validData = false;
                }
            } while (!validData);

            if (board[y][x].equals("$")) {
                System.out.print("The target was sunk. (Press any key to continue) ");
                scan.nextLine();
                System.out.println("\n----------------------------");
                secondaryBoard[y][x] = "X";
                return 1;
            } else {
                System.out.print("It didn't hit anything. (Press any key to continue) ");
                scan.nextLine();
                System.out.println("\n----------------------------");
                secondaryBoard[y][x] = "#";
                return 0;
            }

        }

        // Otherwise the computer is firing upon the player's ships
        else {
            // This will ensure the same spot isn't fired on more than once
            do {
                x = generateCoordinate(width);
                y = generateCoordinate(length);
            } while (!board[y][x].equals("~") && !board[y][x].equals("$"));

            String coordinates = Character.toString(alphabet.charAt(x - 1)) + Integer.toString(y);
            System.out.println("\nThe enemy fired on " + coordinates + ".");

            if (board[y][x].equals("$")) {
                System.out.println("One of your ships was sunk.");
                board[y][x] = "X";
                System.out.println("\n:: Your board ::");
                printBoard(0);
                System.out.print("\nPress any key to continue.");
                scan.nextLine();
                System.out.println("\n----------------------------");
                return 1;
            } else {
                board[y][x] = "#";
                System.out.println("Nothing was hit.");
                System.out.println("\n:: Your board ::");
                printBoard(0);
                System.out.print("\nPress any key to continue.");
                scan.nextLine();
                System.out.println("\n----------------------------");
                return 0;
            }

        }

    }

    private int[] getCoordinates(String message, int code) {
        boolean validData;
        String data, tempX, tempY;
        int x = 0, y = 0;

        do {
            validData = false;
            printBoard(code);
            System.out.print(message);
            data = scan.nextLine();
            try {
                tempX = Character.toString(data.charAt(0));
                tempY = Character.toString(data.charAt(1));
            } catch (StringIndexOutOfBoundsException e) {
                // This catches if the player enters a string of less than 2 characters
                tempX = "!";
                tempY = "!";
            }

            // Checks to see if the letter coordinate provided exists on the current board
            for (int j = 0; j < width - 1; j++) {
                if (tempX.equalsIgnoreCase(Character.toString(alphabet.charAt(j)))) {
                    validData = true;
                    // Convert the letter into an x-coordinate for use with the placeShip() method
                    x = (j + 1);
                }
            }

            // Checks to see if the number coordinate provided exists on the board
            try {
                y = Integer.parseInt(tempY);
                if (y < 1 || y > length - 1) { validData = false; }
            } catch (NumberFormatException nfe) { validData = false; }

            // If the string provided isn't 2 characters in length, it's marked invalid
            if (data.length() > 2 || tempY.equals("!")) { validData = false; }
            if (!validData) { System.out.println("Invalid coordinates."); }

        } while (!validData);

        return new int[]{x, y};
    }

    private int generateCoordinate(int dimension) {
        Random random = new Random();
        return Math.abs(random.nextInt()) % dimension;
    }

}