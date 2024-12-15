import org.example.Constants;
import org.example.abstractionClasses.BirdMovement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BirdMovementTest {

    @Test
    void testResetBird() {
        BirdMovement birdMovement = new BirdMovement();

        birdMovement.birdVelocity = 10;
        birdMovement.birdAcceleration = 5;
        birdMovement.updateBirdPhysics();

        birdMovement.resetBird();

        assertEquals(Constants.BIRD_STARTING_HEIGHT, birdMovement.getBirdPosition(),
                "Bird should reset to the starting height.");
        assertEquals(0, birdMovement.birdVelocity, "Bird velocity should reset to 0.");
        assertEquals(0, birdMovement.birdAcceleration, "Bird acceleration should reset to 0.");
    }

    @Test
    void testUpdateBirdPhysics() {
        BirdMovement birdMovement = new BirdMovement();

        birdMovement.updateBirdPhysics();

        assertTrue(birdMovement.getBirdPosition() > Constants.BIRD_STARTING_HEIGHT,
                "Bird should fall after updating physics.");
        assertTrue(birdMovement.birdVelocity > 0, "Bird velocity should increase after updating physics.");
        assertTrue(birdMovement.birdAcceleration > 8, "Bird acceleration should increase.");
    }

    @Test
    void testSetBirdAcceleration() {
        BirdMovement birdMovement = new BirdMovement();

        birdMovement.setBirdAcceleration();

        assertEquals(-12, birdMovement.birdAcceleration,
                "Bird acceleration should be -12 for upward fly.");
    }

    @Test
    void testBirdPositionAfterMultipleUpdates() {
        BirdMovement birdMovement = new BirdMovement();

        birdMovement.updateBirdPhysics();
        int positionAfterFirstUpdate = birdMovement.getBirdPosition();

        birdMovement.updateBirdPhysics();
        int positionAfterSecondUpdate = birdMovement.getBirdPosition();

        assertTrue(positionAfterSecondUpdate > positionAfterFirstUpdate,
                "Bird position should change after multiple updates.");
    }
}
