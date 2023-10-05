package invaders.engine;

import java.util.ArrayList;
import java.util.List;

import invaders.GameObject;
import invaders.entities.Bunker;
import invaders.entities.Enemy;
import invaders.entities.Player;
import invaders.entities.PlayerProjectileFactory;
import invaders.entities.Projectile;
import invaders.entities.Projectile.ProjectileType;
import invaders.physics.Moveable;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import javafx.scene.image.Image;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

/**
 * This class manages the main loop and logic of the game
 */
public class GameEngine {

	private List<GameObject> gameobjects;
	private List<Renderable> renderables;
	private Player player;

	private boolean left;
	private boolean right;

	private double windowWidth;
	private double windowHeight;

	private List<Projectile> projectiles = new ArrayList<>();


	public GameEngine(String configPath) {
		// read the config here
		gameobjects = new ArrayList<GameObject>();
		renderables = new ArrayList<Renderable>();
	
		// Parse the JSON file
		JSONParser parser = new JSONParser();
		try (FileReader reader = new FileReader(configPath)) {
			JSONObject jsonObject = (JSONObject) parser.parse(reader);
			JSONObject playerConfig = (JSONObject) jsonObject.get("Player");
			JSONObject position = (JSONObject) playerConfig.get("position");
			
			// First cast to Long and then convert to double
			double x = ((Long) position.get("x")).doubleValue();
			double y = ((Long) position.get("y")).doubleValue();
	
			// Instantiate the Player with the PlayerProjectileFactory
			player = new Player(new Vector2D(x, y), new PlayerProjectileFactory());
			renderables.add(player);

			// Read the window size
			JSONObject gameConfig = (JSONObject) jsonObject.get("Game");
			JSONObject size = (JSONObject) gameConfig.get("size");
			windowWidth = ((Long) size.get("x")).doubleValue();
			windowHeight = ((Long) size.get("y")).doubleValue();

			// Read and instantiate bunkers
			JSONArray bunkersConfig = (JSONArray) jsonObject.get("Bunkers");
			for (Object obj : bunkersConfig) {
				JSONObject bunkerConfig = (JSONObject) obj;
				JSONObject bunkerPosition = (JSONObject) bunkerConfig.get("position");
				JSONObject bunkerSize = (JSONObject) bunkerConfig.get("size");

				double bunkerX = ((Long) bunkerPosition.get("x")).doubleValue();
				double bunkerY = ((Long) bunkerPosition.get("y")).doubleValue();
				double bunkerWidth = ((Long) bunkerSize.get("x")).doubleValue();
				double bunkerHeight = ((Long) bunkerSize.get("y")).doubleValue();

				Bunker bunker = new Bunker(new Vector2D(bunkerX, bunkerY), new Vector2D(bunkerWidth, bunkerHeight));
				gameobjects.add(bunker);
				renderables.add(bunker); // Assuming Bunker implements Renderable
			}

			// Inside the GameEngine constructor, after reading bunkers:

			JSONArray enemiesConfig = (JSONArray) jsonObject.get("Enemies");
			for (Object obj : enemiesConfig) {
				JSONObject enemyConfig = (JSONObject) obj;
				JSONObject enemyPosition = (JSONObject) enemyConfig.get("position");
				
				double enemyX = ((Long) enemyPosition.get("x")).doubleValue();
				double enemyY = ((Long) enemyPosition.get("y")).doubleValue();
				
				Enemy enemy = new Enemy.EnemyBuilder()
				.setPosition(new Vector2D(enemyX, enemyY))
				.setImage("/enemy.png", 35, 35)  // Updated path
				.build();
			

				gameobjects.add(enemy);
				renderables.add(enemy);
			}



		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Updates the game/simulation
	 */
	public void update(){
		movePlayer();
		for(GameObject go: gameobjects){
			go.update();
		}

		// ensure that renderable foreground objects don't go off-screen
		for(Renderable ro: renderables){
			if(!ro.getLayer().equals(Renderable.Layer.FOREGROUND)){
				continue;
			}
			if(ro.getPosition().getX() + ro.getWidth() >= 640) {
				ro.getPosition().setX(639-ro.getWidth());
			}

			if(ro.getPosition().getX() <= 0) {
				ro.getPosition().setX(1);
			}

			if(ro.getPosition().getY() + ro.getHeight() >= 400) {
				ro.getPosition().setY(399-ro.getHeight());
			}

			if(ro.getPosition().getY() <= 0) {
				ro.getPosition().setY(1);
			}
		}

		for (Projectile projectile : projectiles) {
			if (projectile.getType() == ProjectileType.PLAYER) {
				projectile.moveUp();
			} else if (projectile.getType() == ProjectileType.ENEMY) {
				projectile.moveDown();
			}
		}

			projectiles.removeIf(projectile -> 
			(projectile.getType() == ProjectileType.PLAYER && projectile.getPosition().getY() <= 0) ||
			(projectile.getType() == ProjectileType.ENEMY && projectile.getPosition().getY() >= windowHeight - projectile.getHeight())
		);
	

		renderables.removeIf(renderable -> (renderable instanceof Projectile) && !projectiles.contains(renderable));

		// Make enemies shoot periodically
		for (GameObject gameObject : gameobjects) {
			if (gameObject instanceof Enemy) {
				Enemy enemy = (Enemy) gameObject;
				if (Math.random() < 0.005) {  // Adjust this probability as needed
					Projectile enemyProjectile = enemy.shoot();
					enemyProjectile.moveDown();  // Make the enemy projectile move downwards
					projectiles.add(enemyProjectile);
					renderables.add(enemyProjectile);
				}
			}
		}


		// Check for collisions between projectiles and bunkers
		List<Projectile> projectilesToRemove = new ArrayList<>();
		for (Projectile projectile : projectiles) {
			for (GameObject gameObject : gameobjects) {
				if (gameObject instanceof Bunker) {
					Bunker bunker = (Bunker) gameObject;
					if (projectile.getCollider().isColliding(bunker.getCollider())) {
						bunker.takeDamage(1);  // Assuming 1 damage per projectile
						bunker.getColor();
						projectilesToRemove.add(projectile);
					}
				}
			}
		}

		// Remove collided projectiles
		projectiles.removeAll(projectilesToRemove);
		renderables.removeAll(projectilesToRemove);

		// Remove bunkers that are marked for deletion
		gameobjects.removeIf(gameObject -> (gameObject instanceof Bunker) && ((Bunker) gameObject).isMarkedForDelete());
		renderables.removeIf(renderable -> (renderable instanceof Bunker) && ((Bunker) renderable).isMarkedForDelete());

		// Check for collisions between projectiles and enemies
		List<Enemy> enemiesToRemove = new ArrayList<>();
		for (Projectile projectile : projectiles) {
			if (projectile.getType() == ProjectileType.PLAYER) {
				for (GameObject gameObject : gameobjects) {
					if (gameObject instanceof Enemy) {
						Enemy enemy = (Enemy) gameObject;
						if (projectile.getCollider().isColliding(enemy.getCollider())) {
							enemiesToRemove.add(enemy);
							projectilesToRemove.add(projectile);
						}
					}
				}
			}
		}

		projectiles.removeAll(projectilesToRemove);
		renderables.removeAll(projectilesToRemove);
		gameobjects.removeAll(enemiesToRemove);
		renderables.removeAll(enemiesToRemove);


		// Check for collisions between bunkers and enemies
		for (GameObject gameObject : gameobjects) {
			if (gameObject instanceof Bunker) {
				Bunker bunker = (Bunker) gameObject;
				for (GameObject enemyObject : gameobjects) {
					if (enemyObject instanceof Enemy) {
						Enemy enemy = (Enemy) enemyObject;
						if (bunker.getCollider().isColliding(enemy.getCollider())) {
							bunker.markForDelete();  // Mark the bunker for deletion
						}
					}
				}
			}
		}

		// Remove bunkers that are marked for deletion
		gameobjects.removeIf(gameObject -> (gameObject instanceof Bunker) && ((Bunker) gameObject).isMarkedForDelete());
		renderables.removeIf(renderable -> (renderable instanceof Bunker) && ((Bunker) renderable).isMarkedForDelete());

	}

	public List<Renderable> getRenderables(){
		return renderables;
	}


	public void leftReleased() {
		this.left = false;
	}

	public void rightReleased(){
		this.right = false;
	}

	public void leftPressed() {
		this.left = true;
	}
	public void rightPressed(){
		this.right = true;
	}

	public boolean shootPressed(){
		Projectile projectile = player.shoot();
		projectiles.add(projectile);
		renderables.add(projectile);
		return true;
	}
	

	private void movePlayer(){
		if(left){
			player.left();
		}

		if(right){
			player.right();
		}
	}

	public double getWindowWidth() {
		return windowWidth;
	}
	
	public double getWindowHeight() {
		return windowHeight;
	}
}
