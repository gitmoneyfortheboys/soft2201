package invaders.entities;

import invaders.GameObject;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import javafx.scene.image.Image;
import invaders.physics.BoxCollider;

public class Enemy implements Renderable, GameObject {
    private Vector2D position;
    private Image image;
    private BoxCollider collider;  // Add this line

    private Enemy() {
        // Private constructor to ensure instances are created only through the builder
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public double getWidth() {
        // Return the width of the enemy image
        return image.getWidth();
    }

    @Override
    public double getHeight() {
        // Return the height of the enemy image
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

    @Override
    public void start() {
        // Initialization logic for the enemy
    }

    @Override
    public void update() {
        // Update logic for the enemy
    }

    public BoxCollider getCollider() {
        if (collider == null) {
            collider = new BoxCollider(getWidth(), getHeight(), getPosition());
        }
        return collider;
    }

    public static class EnemyBuilder {
        private Vector2D position;
        private Image image;

        public EnemyBuilder setPosition(Vector2D position) {
            this.position = position;
            return this;
        }

        public EnemyBuilder setImage(String imagePath, double width, double height) {
            this.image = new Image(getClass().getResource(imagePath).toString(), width, height, true, true);
            return this;
        }
        
        public Enemy build() {
            Enemy enemy = new Enemy();
            enemy.position = this.position;
            enemy.image = this.image;
            return enemy;
        }
    }
}
