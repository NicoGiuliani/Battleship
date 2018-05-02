package BattleshipPackage;
import java.util.InputMismatchException;
import java.util.Scanner;

class Game {

    Game() {
        int shipsSunkenByPlayer = 0, shipsSunkenByComputer = 0, difficulty = 0, boardDimensions = 0, numberShips = 0;
        boolean validInput;

        do {
            Scanner scan = new Scanner(System.in);
            validInput = true;
            System.out.println("\n     ~ Difficulty ~\n------------------------");
            System.out.println("1) Mr. Bean in a Row Boat (1 boat on a 4x4 grid)");
            System.out.println("2) A Few Somali Pirates on Jet Skis (3 ships on a 5x5 grid)");
            System.out.println("3) A Handful of Rusty North Korean Subs (4 ships on a 7x7 grid)");
            System.out.println("4) The Hunt for the Red October (5 ships on a 9x9 grid)\n");
            System.out.print("Enter the difficulty you'd like to play at (1-4): ");

            try {
                difficulty = scan.nextInt();
                if (difficulty < 1 || difficulty > 4) {
                    System.out.println("Input must be from the menu.\n");
                    validInput = false;
                }
            } catch (InputMismatchException ime) {
                System.out.println("Input must be an integer.\n");
                validInput = false;
            }
        } while (!validInput);

        switch (difficulty) {
            case 1:
                boardDimensions = 4;
                numberShips = 1;
                break;
            case 2:
                boardDimensions = 5;
                numberShips = 3;
                break;
            case 3:
                boardDimensions = 7;
                numberShips = 4;
                break;
            case 4:
                boardDimensions = 9;
                numberShips = 5;
                break;
        }

        Board computerBoard = new Board(boardDimensions, numberShips, "Computer");
        Board playerBoard = new Board(boardDimensions, numberShips, "Player");

        // So long as both players have at least one ship, they will continue to take turns
        while (shipsSunkenByComputer < numberShips && shipsSunkenByPlayer < numberShips) {
            // The player takes a turn
            shipsSunkenByPlayer += computerBoard.fireUpon(0);
            if (shipsSunkenByPlayer == numberShips) {
                System.out.println("\nAll enemy ships have been sunk.");
                break;
            }

            // The computer then takes a turn
            shipsSunkenByComputer += playerBoard.fireUpon(1);
            if (shipsSunkenByComputer == numberShips) {
                System.out.println("\nAll of your ships have been sunk.");
                break;
            }
        }

    }

}