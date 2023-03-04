package myGame;

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

import java.util.Map;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.collision.ContactID.Type;

import myGame.simplefactory;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import static com.almasb.fxgl.dsl.FXGL.*;

public class DawnseekerApp extends GameApplication{
	
    public enum EntityType {
        PLAYER, ENEMY, BULLET, WALL, COIN, SPOWER, APOWER, HPOWER
    }
	private AStarGrid grid;
	
	public AStarGrid getGrid() {
		return grid;
	}
    
	private final simplefactory SF = new simplefactory(); 
	
	private Entity player;
	
	public double speed = 2;
	public static int EHP = 10;
	public static int PHP = 20;
	public static int EDMG = 10;
	public static int PDMG = 20;
	
	public Entity getPlayer() {
		return player;
	}
	
    public static void main(String[] args) {
        launch(args);
    }
	
    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("Coins", 0);
    }
    
    @Override
    protected void initUI() {
        Label scoreLabel = new Label();
        scoreLabel.setTextFill(Color.BLACK);
        scoreLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        scoreLabel.textProperty().bind(FXGL.getip("Coins").asString("Coins: %d"));
        FXGL.addUINode(scoreLabel, 40, 35);
    }
    
    @Override
    protected void initSettings(GameSettings settings) {
		settings.setWidth(1024);
		settings.setHeight(1024);
		settings.setTitle("Dawnseeker");
		settings.setVersion("0.2");
		settings.setMainMenuEnabled(true);
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
    	
    	//Background music ----- Arrowood
    	String BGM = new String("heartache.mp3");
    	Music gameMusic = FXGL.getAssetLoader().loadMusic(BGM);
    	FXGL.getAudioPlayer().loopMusic(gameMusic);
    	
    	this.player = spawn("player", getAppWidth() / 2 - 15, getAppHeight() / 2 - 15);// getAppWidth() / 2 - 15, getAppHeight() / 2 - 15
        spawn("BG");
		spawn("W");
		spawn("W2");
		spawn("W3");
		spawn("W4");
        grid = AStarGrid.fromWorld(getGameWorld(), 15, 15, 40, 40, type -> {
            if (type.equals(EntityType.WALL))//was set to type was changed to entitytype
                return CellState.NOT_WALKABLE;

            return CellState.WALKABLE;
        });
        
        //Enemies spawn every half a second, and their damage is increased by ??? every 10 in-game seconds.
    	run(() -> spawn("enemy"), Duration.seconds(.5) );
    	getGameTimer().runAtInterval(() -> { EHP=EHP*2;EDMG=EDMG*2; }, Duration.seconds(10));
    }
    
 
    
    @Override
    protected void initPhysics() {
        onCollisionBegin(EntityType.BULLET, EntityType.ENEMY, (bullet, enemy) -> {
        	bullet.removeFromWorld();
        	enemy.setProperty("Health", enemy.getInt("Health")-bullet.getInt("Dmg"));
            if(enemy.getInt("Health") <= 0) {
            	killEnemy(enemy);
            }
        		
        }); 
        
        onCollisionBegin(EntityType.PLAYER, EntityType.ENEMY, (player, enemy) -> {
        	player.setProperty("Health", player.getInt("Health")-enemy.getInt("Dmg"));
        	enemy.translateTowards(player.getCenter(), -Math.sqrt(player.getX() + player.getY()));
        	FXGL.play("player_oof.wav"); // ----- ADDS SOUND PER ENEMY COLLISION

        	//If player dies...
        	if(player.getInt("Health") <= 0) {
        		FXGL.getAudioPlayer().stopAllSounds();
        		FXGL.play("yoda_death.wav");
        		player.setPosition(getAppWidth() / 2 - 15, getAppHeight() / 2 - 15);
        		player.setProperty("Health", PHP);
        		getGameWorld().removeEntities(getGameWorld().getEntitiesByType(EntityType.COIN,EntityType.ENEMY,EntityType.SPOWER,EntityType.APOWER,EntityType.HPOWER));

        	}
        });
        
        onCollisionBegin(EntityType.PLAYER, EntityType.COIN, (player, coin) -> {
            coin.removeFromWorld();
            FXGL.play("coin_pickup.wav");
            FXGL.inc("Coins", 1);
        });
        
        onCollisionBegin(EntityType.PLAYER, EntityType.SPOWER, (player, spower) -> {
            spower.removeFromWorld();
            speed = speed+(speed*.01);

        });
        
        onCollisionBegin(EntityType.PLAYER, EntityType.APOWER, (player, apower) -> {
            apower.removeFromWorld();
            PDMG=PDMG+5;

        });
        
        onCollisionBegin(EntityType.PLAYER, EntityType.HPOWER, (player, hpower) -> {
            hpower.removeFromWorld();
            PHP = PHP+10;

        });
        
        onCollisionBegin(EntityType.BULLET, EntityType.WALL, (bullet, wall) -> {
            bullet.removeFromWorld();
            
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
    public static int getEHP() {
    	return EHP;
    }
    public static int getEDMG() {
    	return EDMG;
    }
    public static int getPHP() {
    	return PHP;
    }
    public static int getPDMG() {
    	return PDMG;
    }
    
    private void killEnemy(Entity e) {
    	Point2D cSpawnPoint = e.getCenter();
    	double rng = Math.random()*10;
    	if(rng < 8) {
    		spawn("coin", cSpawnPoint);
    	}
    	else {
    		if(rng >= 8 && rng < 8.7 ) {
    			spawn("spower", cSpawnPoint);	
    		}
    		if(rng >= 8.7 && rng < 9.4 ) {
    			spawn("apower", cSpawnPoint);	
    		}
    		if(rng >= 9.4) {
    			spawn("hpower", cSpawnPoint);	
    		}
    	}
    	FXGL.play("bong.wav");
    	e.removeFromWorld();
    }
    


}