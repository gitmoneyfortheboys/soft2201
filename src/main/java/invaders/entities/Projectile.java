package invaders.entities;

import invaders.physics.BoxCollider;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import javafx.scene.image.Image;
import java.io.File;

public class Projectile implements Renderable {

    private final Vector2D position;
    private final double width = 5;  // Adjust as needed
    private final double height = 10;  // Adjust as needed
    private final Image image;

    private final BoxCollider collider;

    private final ProjectileType type;

    private final ProjectileBehavior behavior;

    public enum ProjectileType {
    PLAYER,
    ENEMY
}


    public Projectile(Vector2D position, ProjectileType type, ProjectileBehavior behavior){
        this.image = new Image(new File("src/main/resources/projectile.png").toURI().toString(), width, height, true, true);
        this.position = position;
        this.collider = new BoxCollider(width, height, position);
        this.type = type;
        this.behavior = behavior;
    }

    public void move() {
        behavior.move(this);
    }

    public ProjectileType getType() {
        return type;
    }

    public void moveUp() {
        this.position.setY(this.position.getY() - 5);  // Adjust the speed as needed
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public double getWidth() {
        return width;
    }

    public void moveDown() {
        this.position.setY(this.position.getY() + 1);  // Adjust the speed as needed
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

    public BoxCollider getCollider() {
        return collider;
    }
}
