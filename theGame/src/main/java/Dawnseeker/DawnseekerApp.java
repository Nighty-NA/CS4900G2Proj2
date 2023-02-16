package Dawnseeker;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGL.getGameController;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.onBtnDown;
import static com.almasb.fxgl.dsl.FXGL.onCollisionBegin;
import static com.almasb.fxgl.dsl.FXGL.onKey;
import static com.almasb.fxgl.dsl.FXGL.run;
import static com.almasb.fxgl.dsl.FXGL.showMessage;
import static com.almasb.fxgl.dsl.FXGL.spawn;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.collision.ContactID.Type;

import Dawnseeker.simplefactory;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.util.Duration;


public class DawnseekerApp extends GameApplication {
	
    public enum EntityType {
        PLAYER, ENEMY, BULLET, WALL, COIN
    }
	private AStarGrid grid;
	
	public AStarGrid getGrid() {
		return grid;
	}
    
	private final simplefactory SF = new simplefactory(); 
	
	private Entity player;
	private int speed = 3;
	
    public static void main(String[] args) {
        launch(args);
    }
	
    @Override
    protected void initSettings(GameSettings settings) {
		settings.setWidth(1024);
		settings.setHeight(1024);
		settings.setTitle("Dawnseeker");
		settings.setVersion("0.1");
		settings.setMainMenuEnabled(true);
        settings.setIntroEnabled(true); //addition for showcase for Sprint 1 -- NArrowood
    }

    @Override
    protected void initInput() {
    	onKey(KeyCode.W, () -> this.player.translateY(-speed));
        onKey(KeyCode.S, () -> this.player.translateY(speed));
        onKey(KeyCode.A, () -> this.player.translateX(-speed));
        onKey(KeyCode.D, () -> this.player.translateX(speed));
        onBtnDown(MouseButton.PRIMARY, () -> spawn("bullet", this.player.getCenter()));
    }
    


    @Override
    protected void initGame() {
    	getGameWorld().addEntityFactory(this.SF);
    	this.player = spawn("player", getAppWidth() / 2 - 15, getAppHeight() / 2 - 15);// getAppWidth() / 2 - 15, getAppHeight() / 2 - 15
        spawn("BG");
        spawn("BWH");
		spawn("BWV");
		spawn("BWH2");
		spawn("BWV2");
		spawn("W");
		spawn("W2");
		spawn("W3");
		spawn("W4");
        grid = AStarGrid.fromWorld(getGameWorld(), 15, 15, 40, 40, type -> {
            if (type.equals(EntityType.WALL))//was set to type was changed to entitytype
                return CellState.NOT_WALKABLE;

            return CellState.WALKABLE;
        });
    	run(() -> spawn("enemy"), Duration.seconds(.35));
        
    }
    
 
    
    @Override
    protected void initPhysics() {
        onCollisionBegin(EntityType.BULLET, EntityType.ENEMY, (bullet, enemy) -> {
            bullet.removeFromWorld();
            killEnemy(enemy);
        });
        onCollisionBegin(EntityType.PLAYER, EntityType.COIN, (player, coin) -> {
            coin.removeFromWorld();
            
        });
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.WALL) {
	    	
	        @Override
	        protected void onCollisionBegin(Entity player, Entity wall) {
	        	player.translateTowards(wall.getCenter(), -Math.sqrt(player.getX() + player.getY()));
	          
	        }
	    });
	    
	    FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.ENEMY, EntityType.WALL) {
	    	
	        @Override
	        protected void onCollisionBegin(Entity enemy, Entity wall) {
	        	enemy.translateTowards(wall.getCenter(), -Math.sqrt(enemy.getX() + enemy.getY()));
	        }
	    });
        
    }
    
    private void killEnemy(Entity e) {
    	Point2D cSpawnPoint = e.getCenter();
    	spawn("coin", cSpawnPoint);
    	e.removeFromWorld();
    }


}
