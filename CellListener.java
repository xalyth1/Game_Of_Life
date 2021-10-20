package life;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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





    }
}
