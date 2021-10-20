package life;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Hashtable;
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

    int timeMillis = 90;
    Color cellsColor = Color.BLACK;

    int previouslyAlive;
    int currentAlive;

    int startXofPanel = 25;
    int chartPanelX = startXofPanel;

    //int previousChartPanelX = chartPanelX + 1;

    int imageWidth = 415;
    int imageHeight = 415;
    BufferedImage clearImg = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
    BufferedImage img = prepareBufferedImage(clearImg);
    BufferedImage canvas = img;


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

        this.cellWidth = 15;
        this.cellHeight = 15;

        //System.out.println("Universe: " + universe);
        this.initComponents(universe.n);



        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pack();

        this.setSize(1180,650);
        this.setLocation(450, 100);
        setVisible(true);
        //doSimulation(universe, this);

        System.out.println("init end");
    }


    void doSimulation(Universe universe, GameOfLife game) {
        newSimulation = false;
        started = true;
        System.out.println("simulation starts");
        //universe.createUniverse();
        int generations = 500;
        for (int i = 1; i < generations; i++) {
            //System.out.println("for starts " + i);
            universe.algo.nextGeneration();
            game.generationLabel.setText("Generation #" + i);
            game.generationLabel.repaint();
            int currAlive = universe.algo.countAliveCells();
            game.aliveLabel.setText("Alive:  " + universe.algo.countAliveCells());
            game.state = universe.current;

            this.repaint();

            if (newSimulation) {
                BufferedImage clearImg = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
                img = prepareBufferedImage(clearImg);
                canvas = img;
                break;
            }

            sleep(timeMillis);
            while (!game.started && !newSimulation) {
                sleep(50);
            }
            previouslyAlive = currAlive;
        }
        System.out.println("wjscie z doSimulation()");
        newSimulation = true;
        BufferedImage clearImg = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        img = prepareBufferedImage(clearImg);
        canvas = img;

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

    void initComponents(int n) {
        this.n = n;

        Border padding = BorderFactory.createEmptyBorder(0, 0, 10, 10);
        this.getRootPane().setBorder(padding);
        setBackground(new Color(222, 184, 134));


        generationLabel.setName("GenerationLabel");
        generationLabel.setFont(new Font( "Calabri", Font.BOLD, 22));

        aliveLabel.setName("AliveLabel");
        aliveLabel.setFont(new Font( "Calabri", Font.BOLD, 22));

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
        refreshButton.setToolTipText("Start new simulation");
        startPauseButton.setToolTipText("Start / Pause currentsimulation");
        startPauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (started) {
                    startPauseButton.setIcon(new ImageIcon("C:\\Users\\Pawel\\IdeaProjects\\Game of Life\\Game of Life\\task\\src\\myResources\\play-button.png"));
                    started = false;
                } else {
                    startPauseButton.setIcon(new ImageIcon("C:\\Users\\Pawel\\IdeaProjects\\Game of Life\\Game of Life\\task\\src\\myResources\\pause.png"));
                    started = true;
                    //chartPanelX = startXofPanel;
                }

            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                newSimulation = !newSimulation;
            }
        });


        int MILLIS_MIN = 10;
        int MILLIS_MAX = 250;
        int MILLIS_INIT = 90;
        JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, MILLIS_MIN, MILLIS_MAX , MILLIS_INIT);

        speedSlider.addChangeListener(new MyListener(this));

        Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
        labelTable.put(250, new JLabel("Fast") );
        labelTable.put(125, new JLabel("Medium") );
        labelTable.put(10, new JLabel("Slow"));
        speedSlider.setLabelTable(labelTable);
        speedSlider.setPaintLabels(true);

        try {
            startPauseButton.setIcon(new ImageIcon("C:\\Users\\Pawel\\IdeaProjects\\Game of Life\\Game of Life\\task\\src\\myResources\\pause.png"));
            refreshButton.setIcon(new ImageIcon("C:\\Users\\Pawel\\IdeaProjects\\Game of Life\\Game of Life\\task\\src\\myResources\\reload.png"));
        } catch (Exception ex) {
            System.out.println(ex);
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(nextButton);
        buttonPanel.add(startPauseButton);
        buttonPanel.add(refreshButton);
        buttonPanel.setBackground(Color.RED);
        buttonPanel.setAlignmentX(0);
        buttonPanel.setSize(new Dimension(200, 50));
        buttonPanel.setPreferredSize(new Dimension(200, 50));
        buttonPanel.setMaximumSize(new Dimension(200, 50));


        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new FlowLayout());
        sliderPanel.add(speedSlider);
        sliderPanel.setAlignmentX(0);
        sliderPanel.setAlignmentY(0);
        sliderPanel.setMaximumSize(new Dimension(200, 50));



        JButton chooseColorButton = new JButton("Choose cells color");
        chooseColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Color newColor = JColorChooser.showDialog(
                        null,
                        "Choose Background Color",
                        Color.BLACK);
                cellsColor = newColor;

            }
        });

        JLabel cellsSliderLabel = new JLabel("Size of life cell", JLabel.CENTER);
        JSlider cellsSlider = new JSlider(JSlider.HORIZONTAL, 5, 50 , this.cellWidth);
        //cellsSlider.setMaximumSize(new Dimension(200, 50));

        cellsSlider.setAlignmentX(0);
        cellsSlider.setAlignmentY(0);
        cellsSlider.setEnabled(true);
        cellsSlider.setName("Cell Size Slider");
        Hashtable<Integer, JLabel> cellLabelTable = new Hashtable<Integer, JLabel>();
        cellLabelTable.put(5, new JLabel("5px") );
        cellLabelTable.put(15, new JLabel("15px") );
        cellLabelTable.put(50, new JLabel("50px"));
        cellsSlider.setLabelTable(cellLabelTable);
        cellsSlider.setPaintLabels(true);

        cellsSlider.addChangeListener(new CellListener(this));

        JPanel sliderPanel2 = new JPanel();
        sliderPanel2.setLayout(new FlowLayout());
        sliderPanel2.add(cellsSlider);
        sliderPanel2.setAlignmentX(0);
        sliderPanel2.setAlignmentY(0);
        sliderPanel2.setMaximumSize(new Dimension(200, 50));

//        int imageWidth = 415;
//        int imageHeight = 415;


        JPanel chartPanel = new JPanel() {
            long counter = 2;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D paintBrush = canvas.createGraphics();

                currentAlive = universe.algo.countAliveCells();

                //25 is ybuffer in preprare... method. todo: make better funct. decomposition

                if (counter > 1)
                    paintBrush.drawLine( chartPanelX, imageHeight - previouslyAlive - 25, chartPanelX + 1, imageHeight - currentAlive - 25);
                paintBrush.dispose();
                repaint();

                if (counter % 150 == 0)
                    chartPanelX++;

                previouslyAlive = currentAlive;

                //if (counter == 0 || counter % 100 == 0)
                //if (!newSimulation)
                g.drawImage(canvas, 0, 0, this);

                if (newSimulation) {
                    int previousChartPanelX = 1;
                    chartPanelX = startXofPanel;


                }
                if (!started) {
                    //save last x

                }

                ++counter;

            }
        };
        chartPanel.setBackground(Color.WHITE);
        chartPanel.setMaximumSize(new Dimension(400, 400));
        chartPanel.setMinimumSize(new Dimension(400, 400));
        chartPanel.setPreferredSize(new Dimension(400, 400));
        chartPanel.setAlignmentX(0);




        //System.out.println(chartPanel.getX() + " , " + chartPanel.getY());

//        cellsSlider.addChangeListener(new ChangeListener() {
//            n = 9;
//            @Override
//            public void stateChanged(ChangeEvent changeEvent) {
//                JSlider source = (JSlider)changeEvent.getSource();
//                int num = source.getValue();
//                if (started = false)
//                    n = num;
//            }
//        });


        //////
//        vBox.add(buttonPanel);
//        vBox.add(sliderPanel);
        /////
        //Box verticalBox2 = Box.createVerticalBox();
        //verticalBox2.add(buttonPanel);
        //verticalBox2.add(sliderPanel);

        optionsPanel.add(vBox);
        optionsPanel.add(buttonPanel);
        optionsPanel.add(sliderPanel);
        optionsPanel.add(chooseColorButton);
        optionsPanel.add(cellsSliderLabel);/////
        optionsPanel.add(sliderPanel2);
        //optionsPanel.add(chartPanel);

        //optionsPanel.add(verticalBox2);

        optionsPanel.setBackground(new Color(222, 184, 134)); // Burly Wood


        gridPanel = createGridPanel();
        gridPanel.setVisible(true);
        gridPanel.setSize(n * cellWidth,n * cellHeight);
        gridPanel.setPreferredSize(new Dimension(n * cellWidth, n * cellHeight));

        BorderLayout mainLayout = new BorderLayout();
        setLayout(mainLayout);


        add(optionsPanel, BorderLayout.WEST);
        add(gridPanel, BorderLayout.CENTER);
        add(chartPanel, BorderLayout.EAST);


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

    public BufferedImage prepareBufferedImage(BufferedImage img) {
        Graphics2D paintBrush = img.createGraphics();

        int imageWidth = img.getWidth();
        int imageHeight = img.getHeight();
        int scaleBufferX = 25;
        int scaleBufferY = 25;



        paintBrush.setColor(Color.RED);
        paintBrush.drawString("" + (imageHeight - scaleBufferY), 0, scaleBufferY);
        paintBrush.drawString("" + (imageHeight - scaleBufferY) / 2, 0, (imageHeight - scaleBufferY) / 2);
        paintBrush.drawString("" + 0, 0, (imageHeight - scaleBufferY));

        paintBrush.drawLine( scaleBufferX, 0, scaleBufferY, imageHeight - scaleBufferY);
        paintBrush.drawLine( scaleBufferX, imageHeight - scaleBufferY, scaleBufferX + imageWidth, imageHeight - scaleBufferY);
        paintBrush.dispose();
        //repaint();
        return img;

    }

    static void sleep(long millis) {
        try {
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
                            g.setColor(cellsColor);
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

    public void setUniverse(Universe universe) {
        this.universe = universe;
    }
}
