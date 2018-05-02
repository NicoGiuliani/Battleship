package BattleshipPackage;
import java.util.Scanner;

public class Battleship {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String response;

        System.out.println("\n`7MM\"\"\"Yp,      db   MMP\"\"MM\"\"YMM MMP\"\"MM\"\"YMM `7MMF'      `7MM\"\"\"YMM   .M\"\"\"bgd `7MMF'  `7MMF'`7MMF'`7MM\"\"\"Mq. ");
        System.out.println("  MM    Yb     ;MM:  P'   MM   `7 P'   MM   `7   MM          MM    `7  ,MI    \"Y   MM      MM    MM    MM   `MM.");
        System.out.println("  MM    dP    ,V^MM.      MM           MM        MM          MM   d    `MMb.       MM      MM    MM    MM   ,M9 ");
        System.out.println("  MM\"\"\"bg.   ,M  `MM      MM           MM        MM          MMmmMM      `YMMNq.   MMmmmmmmMM    MM    MMmmdM9  ");
        System.out.println("  MM    `Y   AbmmmqMA     MM           MM        MM      ,   MM   Y  , .     `MM   MM      MM    MM    MM       ");
        System.out.println("  MM    ,9  A'     VML    MM           MM        MM     ,M   MM     ,M Mb     dM   MM      MM    MM    MM       ");
        System.out.println(".JMMmmmd9 .AMA.   .AMMA..JMML.       .JMML.    .JMMmmmmMMM .JMMmmmmMMM P\"Ybmmd\"  .JMML.  .JMML..JMML..JMML.");

        do {
            new Game();
            System.out.print("Would you like to play again? (Y/N) ");
            response = scan.nextLine();
        } while (response.equalsIgnoreCase("y"));

    }

}