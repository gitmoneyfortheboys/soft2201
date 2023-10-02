package invaders.rendering;

import invaders.GameObject;
import invaders.physics.Vector2D;
import invaders.engine.GameEngine;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * Represents something that can be rendered
 */
public interface Renderable {

    public Image getImage();

    public double getWidth();
    public double getHeight();

    public Vector2D getPosition();

    public Renderable.Layer getLayer();

    /**
     * The set of available layers
     */
    public static enum Layer {
        BACKGROUND, FOREGROUND, EFFECT
    }
}
