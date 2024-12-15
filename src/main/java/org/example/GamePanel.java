package org.example;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;

public abstract class GamePanel extends JPanel implements KeyListener, ActionListener {
    protected GraphicsHandler graphicsHandler = new GraphicsHandler();
    protected Timer time = new Timer(Constants.TIMER_DELAY, this);
    protected boolean gameOver = false;

    public GamePanel() {
        setSize(Constants.WIDTH, Constants.HEIGHT);
        setFocusable(true);
        setBackground(Color.black);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameOver) {
            drawGameOver(g);
        } else {
            drawGame(g);
        }
    }

    protected abstract void drawGame(Graphics g);

    protected void drawGameOver(Graphics g) {
        int finalScore = getFinalScore();
        graphicsHandler.drawScore(g, finalScore, Constants.WIDTH / 2 - 100, Constants.HEIGHT / 2);
    }
    protected abstract int getFinalScore();
}
