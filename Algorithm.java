package life;

public class Algorithm {
    int n;
    String[][] current;
    String[][] next;
    Universe universe;

    public Algorithm(Universe universe) {
        this.universe = universe;
        this.n = universe.n;
        this.current = universe.current;
        this.next = universe.next;
    }

    public void nextGeneration() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int aliveNeighbours = countAliveNeighbours(i, j);
                if (isAlive(i, j)) {
                    if (aliveNeighbours >= 2 && aliveNeighbours <= 3)
                        next[i][j] = "O";
                    else
                        next[i][j] = " ";
                } else if (aliveNeighbours == 3){
                    next[i][j] = "O";
                }
            }
        }
        current = next;
        universe.current = next;

        clearNext();
    }

    private void clearNext() {
        next = new String[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                next[i][j] = universe.random.nextBoolean() ? " " : " ";
            }
        }

        universe.next = new String[n][n];;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                next[i][j] = universe.random.nextBoolean() ? " " : " ";
            }
        }

    }

    private int countAliveNeighbours(int x, int y) {
        int counter = 0;
        boolean nw, n, ne, e, se, s, sw, w;

        nw = isAlive(getUpIndex(x), getLeftIndex(y));
        n = isAlive(getUpIndex(x), y);
        ne = isAlive(getUpIndex(x), getRightIndex(y));
        e = isAlive(x, getRightIndex(y));
        se = isAlive(getDownIndex(x), getRightIndex(y));
        s = isAlive(getRightIndex(x), y);
        sw = isAlive(getDownIndex(x), getLeftIndex(y));
        w = isAlive(x, getLeftIndex(y));

        boolean[] tab = new boolean[] {nw, n, ne, e, se, s, sw, w};
        for (int i = 0; i < tab.length; i++) {
            if (tab[i]) {
                counter++;
            }
        }
        return counter;
    }

    private int getUpIndex(int x) { // up neighbour index
        if (x == 0) return n - 1;
        return x - 1;
    }
    private int getDownIndex(int x) {
        if (x == n - 1)
            return 0;
        return x + 1;
    }

    private int getLeftIndex(int y) {
        if (y == 0)
            return n - 1;
        return y - 1;
    }

    private int getRightIndex(int y) {
        if (y == n - 1)
            return 0;
        return y + 1;
    }

    private boolean isAlive(int i, int j) {
        if (current[i][j] == null) {
            System.out.println("is Alive null " + i + " " + j);
        }
        return current[i][j].equals("O");
    }

    public int countAliveCells() {
        int counter = 0;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (current[i][j] == "O")
                    counter++;
            }
        }
        return counter;
    }
}
