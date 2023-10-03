package invaders.entities;

import invaders.GameObject;
import invaders.rendering.Renderable;
import invaders.physics.Vector2D;
import javafx.scene.image.Image;
import java.io.File;

public class Enemy implements GameObject, Renderable {

    private Vector2D position;
    private final Image image;

    public Enemy(Vector2D position) {
        this.position = position;
        this.image = new Image(new File("src/main/resources/enemy.png").toURI().toString());
    }

    @Override
    public void start() {
        // Initialization logic for the enemy
    }

    @Override
    public void update() {
        // Update logic for the enemy
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public double getWidth() {
        return image.getWidth();
    }

    @Override
    public double getHeight() {
        return image.getHeight();
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public Renderable.Layer getLayer() {
        return Renderable.Layer.FOREGROUND;
    }
}
