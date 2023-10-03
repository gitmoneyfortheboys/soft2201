package invaders.entities;

import invaders.logic.Damagable;
import invaders.GameObject;
import invaders.physics.BoxCollider;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Bunker implements GameObject, Damagable, Renderable, EntityView {

    private static final int MAX_HITS = 3;
    private int hitCount;

    private Vector2D position;
    private Vector2D size;
    private final Image image;

    private boolean toBeDeleted = false;
    private boolean delete = false;

    private final BoxCollider collider;

    private BunkerState state;

    public Bunker(Vector2D position, Vector2D size) {
        this.position = position;
        this.size = size;
        this.hitCount = 0;
        this.image = new Image(new File("src/main/resources/bunker.png").toURI().toString());
        this.collider = new BoxCollider(size.getX(), size.getY(), position);
        this.state = new GreenState(); // Bunker starts in the Green state
    }

    @Override
    public void start() {
        // For now, we'll leave this empty. 
        // This can be used to initialize any logic when the game starts.
    }

    @Override
    public void update() {
        // This method will be called every frame. 
        // Any logic that needs to be executed for the bunker can be placed here.
    }

    @Override
    public void takeDamage(double amount) {
        state.takeDamage(this);
    }

    @Override
    public double getHealth() {
        // Since we're not using health, we can return the number of hits left before the bunker is destroyed
        return MAX_HITS - hitCount;
    }

    @Override
    public boolean isAlive() {
        return hitCount < MAX_HITS;
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public double getWidth() {
        return size.getX();
    }

    @Override
    public double getHeight() {
        return size.getY();
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
    public void update(double xViewportOffset, double yViewportOffset) {
        // You can leave this empty if the bunker doesn't need any specific update logic
    }

    @Override
    public boolean matchesEntity(Renderable entity) {
        return this.equals(entity);
    }

    @Override
    public void markForDelete() {
        delete = true;
    }

    @Override
    public Node getNode() {
        // Return the graphical representation (Node) of the bunker.
        // This could be an ImageView or any other JavaFX Node.
        return new ImageView(this.image); // Assuming you're using an ImageView for rendering
    }

    @Override
    public boolean isMarkedForDelete() {
        return delete;
    }

    public BoxCollider getCollider() {
        return collider;
    }

    public void setState(BunkerState state) {
        this.state = state;
    }

    public String getColor() {
        return state.getColor();
    }
}
