package life;
import javax.swing.*;
import java.io.IOException;
import java.util.*;
public class Main {
    public static void main(String[] args) {


        GameOfLife game = new GameOfLife();
        //game.doSimulation(game.universe, game);
//        game.universe.createUniverse();
//        game.doSimulation(game.universe, game);




        //this
        //new Thread(game).start();
        //game.special();
        //SwingUtilities.invokeLater(game);
        //game.special();

//        while (true) {
//            //game.universe.createUniverse();
//            game.newSimulation = false;
//            game.doSimulation(game.universe, game);
//
//        }





    }


    static void mainLoop() {

    }

    static void f() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        Random random = new Random();

        Universe universe = new Universe(n, random);

        GameOfLife game = new GameOfLife();
        game.initComponents(n);

        int generations = 100;
        int cnt = 1;

        while (generations > 0) {
            generations--;
            universe.algo.nextGeneration();
            System.out.println("#Generation: #" + cnt);
            cnt++;
            System.out.println("Alive: " + universe.algo.countAliveCells());

            System.out.println(universe);

            game.generationLabel.setText("Generation #" + cnt);
            game.aliveLabel.setText("Alive:  " + universe.algo.countAliveCells());
            game.state = universe.current;
            System.out.println("x: " + game.optionsPanel.getX() + " y: " + game.optionsPanel.getY());
            game.repaint();

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                System.out.println(e);
            }

            //refreshConsole();
        }
    }

    static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    static void refreshConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException e) {
        }
    }
}

