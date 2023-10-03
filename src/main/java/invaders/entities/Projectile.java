package invaders.entities;

import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import javafx.scene.image.Image;
import java.io.File;

public class Projectile implements Renderable {

    private final Vector2D position;
    private final double width = 5;  // Adjust as needed
    private final double height = 10;  // Adjust as needed
    private final Image image;

    public Projectile(Vector2D position){
        this.image = new Image(new File("src/main/resources/projectile.png").toURI().toString(), width, height, true, true);
        this.position = position;
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
}
