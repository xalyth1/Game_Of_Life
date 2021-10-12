package life;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MyListener implements ChangeListener {

    GameOfLife game;
    public MyListener(GameOfLife game) {
        this.game = game;
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        JSlider source = (JSlider)changeEvent.getSource();
        int millis = source.getValue();
        if (millis != game.timeMillis) {
            game.timeMillis = 250 - millis;
        }





    }
}
