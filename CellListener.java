package life;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class CellListener implements ChangeListener {
    GameOfLife game;
    public CellListener(GameOfLife game) {
        this.game = game;
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        JSlider source = (JSlider)changeEvent.getSource();
        int x = source.getValue();
        game.cellWidth = x;
        game.cellHeight = x;


        int frameWidth = game.optionsPanel.getWidth() + game.chartPanel.getWidth() + game.n * game.cellWidth;
        int frameHeight = Math.max(Math.max(game.optionsPanel.getHeight(), game.chartPanel.getHeight()), game.n * game.cellHeight);

        //System.out.println(frameWidth + " x " + frameHeight);
        game.setSize(new Dimension(frameWidth, frameHeight));





    }
}
