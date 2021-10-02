package life;

import java.util.Random;

public class Universe {
    int n;
    Random random;
    String[][] current;
    String[][] next;
    Algorithm algo;

    public Universe(int n, Random random) {
        this.n = n;
        this.random = random;
        this.current = new String[n][n];
        this.next = new String[n][n];

        createUniverse();
        algo = new Algorithm(this);
    }

//    @Override
//    public void run() {
//        int generations = 100;
//        for (int i = 1; i < generations; i++) {
//            //System.out.println("for starts " + i);
//            algo.nextGeneration();
////            this.generationLabel.setText("Generation #" + i);
////            this.generationLabel.repaint();
////            this.aliveLabel.setText("Alive:  " + universe.algo.countAliveCells());
//            //this.state = universe.current;
//
//
//        }
//    }

    public void createUniverse() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                current[i][j] = random.nextBoolean() ? "O" : " ";
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                next[i][j] = random.nextBoolean() ? " " : " ";
            }
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(current[i][j]);
            }
            s.append("\n");
        }
        return s.toString();
    }


}
