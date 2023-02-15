//Credit goes to almasb from GeoWars application.
package enemyComponent;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class BadGuyOne extends Component {
	
		private Point2D velocity = Point2D.ZERO;
	    private Entity player;
	    private Entity enemy1;
	    
	    private LocalTimer adjustDirectionTimer = FXGL.newLocalTimer();
	    private Duration adjustDelay = Duration.seconds(0.15);

	    private int moveSpeed;
	    
	public BadGuyOne (Entity player, int moveSpeed) {
		this.player = player;
		this.moveSpeed = moveSpeed;
	}
	
	@Override
    public void onAdded() {
        enemy1 = entity;
        adjustVelocity(0.016);
	}
	
	@Override
    public void onUpdate(double tpf) {
        move(tpf);
        rotate();

    }

    private void move(double tpf) {
        if (adjustDirectionTimer.elapsed(adjustDelay)) {
            adjustVelocity(tpf);
            adjustDirectionTimer.capture();
        }

        enemy1.translate(velocity);
    }

    private void adjustVelocity(double tpf) {
        Point2D directionToPlayer = player.getCenter()
                .subtract(enemy1.getCenter())
                .normalize()
                .multiply(moveSpeed);

        velocity = velocity.add(directionToPlayer).multiply(tpf);
    }

    private void rotate() {
        if (!velocity.equals(Point2D.ZERO)) {
            enemy1.rotateToVector(velocity);
        }
    }

}
