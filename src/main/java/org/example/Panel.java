package org.example;

import org.example.abstractionClasses.BirdMovement;
import org.example.abstractionClasses.WallMovement;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Panel extends GamePanel {
    private final BirdMovement birdMovement = new BirdMovement();
    private final WallMovement wallMovement = new WallMovement();
    private int score = 0;
    private final boolean[] wallPassed = {false, false};

    public Panel() {
        super();
        addKeyListener(this);
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (!gameOver) {
            birdMovement.updateBirdPhysics();
            wallMovement.moveWalls();
            logic();
        }
        repaint();
    }

    @Override
    protected void drawGame(Graphics g) {
        wallMovement.drawWalls(g);
        graphicsHandler.drawBird(g, 250, birdMovement.getBirdPosition(), 50, 60);
        graphicsHandler.drawScore(g, score, Constants.WIDTH - 120, 10);
    }

    private void logic() {
        if (gameOver) return;

        checkCollisions();
        checkWallPasses();
        resetWallsIfOffScreen();
    }

    private void checkCollisions() {
        Rectangle birdHitbox = graphicsHandler.getBirdHitbox();
        for (int i = 0; i < 2; i++) {
            if (birdHitbox.intersects(wallMovement.wallReactTop(i)) || birdHitbox.intersects(wallMovement.wallReactBottom(i))) {
                gameOver = true;
            }
        }

        if (birdHitbox.y <= -30 || birdHitbox.y + birdHitbox.height >= Constants.HEIGHT + 30) {
            gameOver = true;
        }
    }

    private void checkWallPasses() {
        for (int i = 0; i < 2; i++) {
            if (wallMovement.isWallPassed(i) && !wallPassed[i]) {
                score++;
                wallPassed[i] = true;
            }
        }
    }

    private void resetWallsIfOffScreen() {
        for (int i = 0; i < 2; i++) {
            if (wallMovement.isNoMoreWalls(i)) {
                wallMovement.resetWallsPosition(i);
                wallPassed[i] = false;
            }
        }
    }

    public int getScore() {
        return score;
    }

    private void resetGame() {
        time.stop();
        birdMovement.resetBird();
        wallMovement.resetWalls();
        gameOver = false;
        score = 0;
        resetWallPasses();
        repaint();
    }

    private void resetWallPasses() {
        for (int i = 0; i < wallPassed.length; i++) {
            wallPassed[i] = false;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_SPACE) {
            handleFlap();
        } else if (code == KeyEvent.VK_R) {
            resetGame();
        }
    }

    private void handleFlap() {
        graphicsHandler.triggerFlap();
        birdMovement.setBirdAcceleration();
        if (!time.isRunning()) {
            time.start();
        }
    }

    protected int getFinalScore(){
        return score;
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
