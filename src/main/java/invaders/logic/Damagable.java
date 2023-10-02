package invaders.logic;

public interface Damagable {

	public void takeDamage(double amount);

	public double getHealth();

	public boolean isAlive();

}
