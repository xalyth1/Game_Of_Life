package life;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameOfLife extends JFrame implements Runnable{
    JLabel generationLabel = new JLabel ("Generation #0   ");
    JLabel aliveLabel = new JLabel("Alive: 0");

    JPanel gridPanel;
    JPanel optionsPanel;
    int n;
    int cellWidth;
    int cellHeight;

    String[][] state;

    boolean started = false;
    Universe universe;
    boolean newSimulation = true;

    public GameOfLife() {
        init();
        new Thread(this).start();
    }

    public void init() {
        this.setUniverse(new Universe(35, new Random()));
        this.universe.createUniverse();
        this.state = universe.current;
        this.setTitle("Game of Life");
        setVisible(true);
        generationLabel.setName("GenerationLabel");
        generationLabel.setPreferredSize(new Dimension(200,30));
        generationLabel.setVisible(true);
        aliveLabel.setName("AliveLabel");

        int gridSize = 35;



        //System.out.println("Universe: " + universe);
        this.initComponents(universe.n);

        this.cellWidth = 15;
        this.cellHeight = 15;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pack();

        this.setSize(900,650);
        this.setLocation(450, 100);
        setVisible(true);
        //doSimulation(universe, this);

        System.out.println("init end");
    }


    void doSimulation(Universe universe, GameOfLife game) {
        newSimulation = false;
        System.out.println("simulation starts");
        //universe.createUniverse();
        int generations = 500;
        for (int i = 1; i < generations; i++) {
            //System.out.println("for starts " + i);
            universe.algo.nextGeneration();
            game.generationLabel.setText("Generation #" + i);
            game.generationLabel.repaint();
            game.aliveLabel.setText("Alive:  " + universe.algo.countAliveCells());
            game.state = universe.current;

            this.repaint();


            //System.out.println("after repaint " + i  + " adf");


            if (newSimulation) {

                break;
            }

            sleep(90);
            while (!game.started && !newSimulation) {
                sleep(50);
            }



//
//            while (!game.started) {
//                sleep(50);
//            }
//
//            if (game.newSimulation) {
//                break;
//            }

        }
        System.out.println("wjscie z doSimulation()");
        newSimulation = true;

    }
    @Override
    public void run() {

        System.out.println("run starts");

        doSimulation(universe, this);
        while (newSimulation) {
            this.setUniverse(new Universe(35, new Random()));
            this.universe.createUniverse();
            this.state = universe.current;
            doSimulation(universe, this);
        }

    }

    public void setUniverse(Universe universe) {
        this.universe = universe;
    }

    void initComponents(int n) {
        this.n = n;

        Border padding = BorderFactory.createEmptyBorder(0, 0, 10, 10);
        this.getRootPane().setBorder(padding);
        setBackground(new Color(222, 184, 134));


        generationLabel.setName("GenerationLabel");
        generationLabel.setFont(new Font( "Calabri", Font.BOLD, 22));

        aliveLabel.setName("AliveLabel");
        aliveLabel.setFont(new Font( "Calabri", Font.BOLD, 22));
//        generationLabel.setAlignmentX(0);
//        generationLabel.setAlignmentY(0);

        optionsPanel = new JPanel();
        BoxLayout optionsBoxLayout = new BoxLayout(optionsPanel, BoxLayout.Y_AXIS);
        optionsBoxLayout.maximumLayoutSize(optionsPanel);
        optionsPanel.setLayout(optionsBoxLayout);
        Box vBox = Box.createVerticalBox();
        vBox.add(generationLabel);
        vBox.add(aliveLabel);

        JButton nextButton = new JButton("N");
        JToggleButton startPauseButton = new JToggleButton();

        startPauseButton.setName("PlayToggleButton");
        JButton refreshButton = new JButton();
        refreshButton.setName("ResetButton");
        startPauseButton.setToolTipText("Start / Pause simulation.");
        startPauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (started) {
                    startPauseButton.setIcon(new ImageIcon("C:\\Users\\Pawel\\IdeaProjects\\Game of Life\\Game of Life\\task\\src\\myResources\\play-button.png"));
                    started = false;
                } else {
                    startPauseButton.setIcon(new ImageIcon("C:\\Users\\Pawel\\IdeaProjects\\Game of Life\\Game of Life\\task\\src\\myResources\\pause.png"));
                    started = true;
                }

            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                newSimulation = !newSimulation;
            }
        });


        try {
            startPauseButton.setIcon(new ImageIcon("C:\\Users\\Pawel\\IdeaProjects\\Game of Life\\Game of Life\\task\\src\\myResources\\pause.png"));
            refreshButton.setIcon(new ImageIcon("C:\\Users\\Pawel\\IdeaProjects\\Game of Life\\Game of Life\\task\\src\\myResources\\reload.png"));
        } catch (Exception ex) {
            System.out.println(ex);
        }
//        Box hBox = Box.createHorizontalBox();
//        hBox.add(nextButton);
//        hBox.add(startPauseButton);
//        hBox.add(refreshButton);
//        hBox.setMaximumSize(new Dimension(50, 30));
        //vBox.add(hBox);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(nextButton);
        buttonPanel.add(startPauseButton);
        buttonPanel.add(refreshButton);
        buttonPanel.setBackground(Color.RED);
        buttonPanel.setAlignmentX(0);

//        vBox.setAlignmentX(Component.TOP_ALIGNMENT);
//        vBox.setAlignmentY(Component.TOP_ALIGNMENT);
        optionsPanel.add(vBox);
        optionsPanel.add(buttonPanel);

        optionsPanel.setBackground(new Color(222, 184, 134)); // Burly Wood

        //optionsPanel.setSize(300,300);
        //optionsPanel.add(Box.createVerticalStrut(0));


        gridPanel = createGridPanel();
        gridPanel.setVisible(true);
        gridPanel.setSize(n * cellWidth,n * cellHeight);
        gridPanel.setPreferredSize(new Dimension(n * cellWidth, n * cellHeight));

        BorderLayout mainLayout = new BorderLayout();
        setLayout(mainLayout);


        add(optionsPanel, BorderLayout.WEST);
        add(gridPanel, BorderLayout.CENTER);

        gridPanel.setBackground(Color.LIGHT_GRAY);

        int width = optionsPanel.getWidth() + gridPanel.getWidth();//n * cellWidth + optionsPanel.getWidth();
        System.out.println("options panel width: " + optionsPanel.getWidth());
        int height = Math.max(gridPanel.getHeight(), optionsPanel.getHeight());//n * cellHeight + 250;
        System.out.println("width : " + width + " height :" + height);
        //setSize(width, height);

        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getSize().width) / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);


        pack();
    }



    static void sleep(long millis) {
        try {
            //Thread.sleep(millis);
            Thread.currentThread().sleep(millis);

        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    private JPanel createGridPanel(){
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int width = cellWidth;
                int height = cellHeight;

                int x = 0;
                int y = gridPanel.getY();

                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        g.drawRect(x + i * width, y + j * height, width, height);
                    }
                }

                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (state[i][j].equals("O")) {
                            g.fillRect(x + i * width, y + j * height, width, height);
                        }
                    }
                }

            }
        };

    }

    public void paint(Graphics g) {
        super.paint(g);
    }


}
