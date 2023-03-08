//Credit goes to almasb from GeoWars application.
package enemyComponent;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class DelayedBadGuy extends Component {
	
		private Point2D velocity = Point2D.ZERO;
	    private Entity player;
	    private Entity enemy1;
	    
	    private LocalTimer adjustDirectionTimer = FXGL.newLocalTimer();
	    private Duration adjustDelay = Duration.seconds(1);

	    private int moveSpeed;
	    
	public DelayedBadGuy (Entity player, int moveSpeed) {
		this.player = player;
		this.moveSpeed = moveSpeed;
	}
	
	//When the enemy entity is added to the world, adjust it's velocity.
	@Override
    public void onAdded() {
        enemy1 = entity;
        adjustVelocity(0.016);
	}
	
	//As the game updates frame by frame. "tpf" = time per frame.
	@Override
    public void onUpdate(double tpf) {
        move(tpf);
//        rotate();

    }

	//This allows the enemies to move a certain speed and direction.
    private void move(double tpf) {
        if (adjustDirectionTimer.elapsed(adjustDelay)) {
            adjustVelocity(tpf);
            adjustDirectionTimer.capture();
        }

        enemy1.translate(velocity);
    }
    
    //This adjusts the velocity of the enemies, and points them to the center of the Player object.
    private void adjustVelocity(double tpf) {
        Point2D directionToPlayer = player.getCenter()
                .subtract(enemy1.getCenter())
                .normalize()
                .multiply(moveSpeed);

        velocity = velocity.add(directionToPlayer).multiply(tpf);
    }
    
    //This rotates the enemies. We can take this out to make them not rotate.
    private void rotate() {
        if (!velocity.equals(Point2D.ZERO)) {
            enemy1.rotateToVector(velocity);
        }
    }

}
