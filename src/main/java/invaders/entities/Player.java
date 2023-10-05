package invaders.entities;

import invaders.logic.Damagable;
import invaders.physics.BoxCollider;
import invaders.physics.Collider;
import invaders.physics.Moveable;
import invaders.physics.Vector2D;
import invaders.rendering.Animator;
import invaders.rendering.Renderable;

import javafx.scene.image.Image;

import java.io.File;

public class Player implements Moveable, Damagable, Renderable {

    private final Vector2D position;
    private final Animator anim = null;
    private double health = 100;

    private final double width = 25;
    private final double height = 30;
    private final Image image;

    private ProjectileFactory projectileFactory;

    private int lives;

    private BoxCollider collider;

    public Player(Vector2D position, ProjectileFactory factory){
        this.image = new Image(new File("src/main/resources/player.png").toURI().toString(), width, height, true, true);
        this.position = position;
        this.projectileFactory = factory;
        this.collider = new BoxCollider(width, height, position);
    }

    @Override
    public void takeDamage(double amount) {
        this.health -= amount;
    }

    @Override
    public double getHealth() {
        return this.health;
    }

    @Override
    public boolean isAlive() {
        return this.health > 0;
    }

    @Override
    public void up() {
        return;
    }

    @Override
    public void down() {
        return;
    }

    @Override
    public void left() {
        this.position.setX(this.position.getX() - 1);
    }

    @Override
    public void right() {
        this.position.setX(this.position.getX() + 1);
    }

    public Projectile shoot() {
        Vector2D projectilePosition = new Vector2D(this.position.getX() + width/2, this.position.getY() - 10);
        return projectileFactory.createProjectile(projectilePosition);
    }
    

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public Layer getLayer() {
        return Layer.FOREGROUND;
    }

    // Inside the Player class
    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getLives() {
        return this.lives;
    }

    public void loseLife() {
        this.lives--;
    }

    public Collider getCollider() {
        return this.collider;
    }


}
