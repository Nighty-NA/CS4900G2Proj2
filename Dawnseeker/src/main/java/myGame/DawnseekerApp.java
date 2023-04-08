package myGame;

import java.util.Map;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.app.scene.SimpleGameMenu;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthDoubleComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.inventory.Inventory;
import com.almasb.fxgl.inventory.view.InventoryView;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.collision.ContactID.Type;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.ui.Position;
import com.almasb.fxgl.ui.ProgressBar;

import animationComponent.AnimationComponent;
import myGame.simplefactory;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
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
        PLAYER, ENEMY, ENEMY2, ENEMY3, BULLET, WALL, COIN, SPOWER, APOWER, HPOWER, BADWALL, SHOP, BPOWER
    }
	
    private ShopTest shop;
    
	private final simplefactory SF = new simplefactory(); 
	
	private Entity player;
	
	public double speed = 3.0;
	public static int EHP = 10;
	public static int PHP = 100;
	public static int PHPM = 100;
	public static int EDMG = 10;
	public static int PDMG = 20;
	public static int PBC = 0;
	
    public static int getEHP() {
    	return EHP;
    }
    public static int getPBC() {
    	return PBC;
    }
    public static int getEDMG() {
    	return EDMG;
    }
    public static int getPHP() {
    	return PHP;
    }

    public static int getPHPM() {
    	return PHPM;
    }
    
    public static int getPDMG() {
    	return PDMG;
    }
	
	public Entity getPlayer() {
		return player;
	}
	
    public static void main(String[] args) {
        launch(args);
    }
	
    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("Coins", 0);
        vars.put("hp",getPHP());
    }
    
    @Override
    protected void initUI() {
        Label scoreLabel = new Label();
        scoreLabel.setTextFill(Color.BLACK);
        scoreLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        scoreLabel.textProperty().bind(FXGL.getip("Coins").asString("Coins: %d"));
        FXGL.addUINode(scoreLabel, 40, 35);
        
        Label HPLabel = new Label();
        HPLabel.setTextFill(Color.BLACK);
        HPLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        HPLabel.textProperty().bind(FXGL.getip("hp").asString());
        FXGL.addUINode(HPLabel, 350, 35);
        
        ProgressBar hpBarr = new ProgressBar();
        hpBarr.setMinValue(0);
        hpBarr.setMaxValue(getPHPM());
        var w = player.getComponent(HealthDoubleComponent.class);
        hpBarr.setCurrentValue(w.getValue());
        hpBarr.setWidth(300);
        hpBarr.setFill(Color.RED);
        FXGL.addUINode(hpBarr,400,35);

    }
 
    @Override
    protected void initSettings(GameSettings settings) {
		settings.setWidth(1024);
		settings.setHeight(1024);
		settings.setTitle("Dawnseeker");
		settings.setVersion("0.4");
		settings.setMainMenuEnabled(true);
		
		//Custom main menu		
		settings.setSceneFactory(new SceneFactory() {
            @Override
            public FXGLMenu newMainMenu() {
            	String BGM = new String("shop.mp3");
            	Music gameMusic = FXGL.getAssetLoader().loadMusic(BGM);
            	FXGL.getAudioPlayer().loopMusic(gameMusic);
                return new DawnseekerMenu();
                
            }
        });
    }

    @Override
    protected void initInput() {
    	
        onBtnDown(MouseButton.PRIMARY, () -> {
        	if(PBC==0) {
        		spawn("bullet", this.player.getCenter());
        	}
        	if(PBC==1) {
        		spawn("bullet", this.player.getCenter());
        		spawn("bullet2", this.player.getCenter());
        	}
        	if(PBC==2) {
        		spawn("bullet", this.player.getCenter());
            	spawn("bullet2", this.player.getCenter());
            	spawn("bullet3", this.player.getCenter());
        	}
        	if(PBC==3) {
            	spawn("bullet", this.player.getCenter());
            	spawn("bullet2", this.player.getCenter());
            	spawn("bullet3", this.player.getCenter());
        		spawn("bullet4", this.player.getCenter());
        	}
        	if(PBC>=4) {
            	spawn("bullet", this.player.getCenter());
            	spawn("bullet2", this.player.getCenter());
            	spawn("bullet3", this.player.getCenter());
            	spawn("bullet4", this.player.getCenter());
        		spawn("bullet5", this.player.getCenter());
        	}
        	
        	
        	
        	
        	
        	
        	
        	//spawn("bullet", this.player.getCenter());
        	//spawn("bullet2", this.player.getCenter());
        	//spawn("bullet3", this.player.getCenter());
        	//spawn("bullet4", this.player.getCenter());
        	//spawn("bullet5", this.player.getCenter());
        	
        	//spawn("bullet2", this.player.getX()+65,this.player.getY()+65);
        	//spawn("bullet3", this.player.getX()-65,this.player.getY()-65);
        });

        
        FXGL.getInput().addAction(new UserAction("Up") {
            @Override
            protected void onAction() {
                player.getComponent(AnimationComponent.class).moveUp();
                player.translateY(-speed);
            }
        }, KeyCode.W);
        
        FXGL.getInput().addAction(new UserAction("Down") {
            @Override
            protected void onAction() {
                player.getComponent(AnimationComponent.class).moveDown();
                player.translateY(speed);
            }
        }, KeyCode.S);
        
        FXGL.getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player.getComponent(AnimationComponent.class).moveRight();
                player.translateX(speed);
            }
        }, KeyCode.D);
        
        FXGL.getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player.getComponent(AnimationComponent.class).moveLeft();
                player.translateX(-speed);
            }
        }, KeyCode.A);
        
        //SHOP LOGIC =========================================== UNIVERSAL SHOP STUFF FOR TESTING
//        shop = new ShopTest();
//        shop.getInput().addAction(new UserAction("Close Shop") {
//            @Override
//            protected void onActionBegin() {
//                getSceneService().popSubScene();
//            }
//        }, KeyCode.F);
//
//        onKeyDown(KeyCode.F, "Open Shop", () -> getSceneService().pushSubScene(shop));
    }

    @Override
    protected void initGame() {
    	getGameWorld().addEntityFactory(this.SF);
    	String BGM = new String("heartache.mp3");
    	Music gameMusic = FXGL.getAssetLoader().loadMusic(BGM);
    	FXGL.getAudioPlayer().loopMusic(gameMusic);
    	
    	this.player = spawn("player", getAppWidth() / 2 - 15, getAppHeight() / 2 - 15);// getAppWidth() / 2 - 15, getAppHeight() / 2 - 15
        spawn("BG");
		spawn("W");
		spawn("W2");
		spawn("W3");
		spawn("W4");
		spawn("badWall");
		spawn("shop");
        
        //Enemies spawn every second, and their damage is increased by x2 every 10 in-game seconds.
    	run(() -> spawn("enemy"), Duration.seconds(1) );
    	run(() -> spawn("enemy2"), Duration.seconds(1) );
    	getGameTimer().runAtInterval(() -> { EHP=EHP*2;EDMG=EDMG*2; }, Duration.seconds(10));
    }
    
 
    
    @Override
    protected void initPhysics() {
        onCollisionBegin(EntityType.BULLET, EntityType.ENEMY, (bullet, enemy) -> {
        	bullet.removeFromWorld();
        	var hp = enemy.getComponent(HealthDoubleComponent.class);
        	if (hp.getValue() > 0) {
                bullet.removeFromWorld();
                hp.damage(PDMG);
                if(hp.getValue() <= 0) {
                	killEnemy(enemy);
                }
                return;
            }else {
            	killEnemy(enemy);
            }
        }); 
        
        //ENEMY 2 -- GHOST ENTITY
        onCollisionBegin(EntityType.BULLET, EntityType.ENEMY2, (bullet, ghost) -> {
        	bullet.removeFromWorld();
        	var hp = ghost.getComponent(HealthDoubleComponent.class);
        	if (hp.getValue() > 0) {
                bullet.removeFromWorld();
                hp.damage(PDMG);
                if(hp.getValue() <= 0) {
                	killEnemy(ghost);
                }
                return;
            }else {
            	killEnemy(ghost);
            }
        });
        
        onCollisionBegin(EntityType.PLAYER, EntityType.ENEMY, (player, enemy) -> {
        	var hp = player.getComponent(HealthDoubleComponent.class);
        	
        	if (hp.getValue() > 0) {
                hp.damage(EDMG);
                initUI();
                FXGL.inc("hp", -EDMG);
                if(hp.getValue() <= 0) {
                	killPlayer(player);
                }
                enemy.translateTowards(player.getCenter(), -Math.sqrt(player.getX() + player.getY()));
            	FXGL.play("player_oof.wav"); // ----- ADDS SOUND PER ENEMY COLLISION
                return;
            }
        	
        });
        
        //GHOST ENEMY COLLISION WITH PLAYER
        onCollisionBegin(EntityType.PLAYER, EntityType.ENEMY2, (player, ghost) -> {
        	var hp = player.getComponent(HealthDoubleComponent.class);
        	
        	if (hp.getValue() > 0) {
                hp.damage(EDMG / 2);
                initUI();
                FXGL.inc("hp", -EDMG / 2);
                if(hp.getValue() <= 0) {
                	killPlayer(player);
                }
            	FXGL.play("player_oof.wav");
            }
        	
        });
        
        //When the player moves over a coin
        onCollisionBegin(EntityType.PLAYER, EntityType.COIN, (player, coin) -> {
            coin.removeFromWorld();
            FXGL.play("coin_pickup.wav");
            FXGL.inc("Coins", 1);
        });
        onCollisionBegin(EntityType.PLAYER, EntityType.BPOWER, (player, bpower) -> {
            bpower.removeFromWorld();
            PBC++;
        });
        //When the player moves over the speed power-up
        onCollisionBegin(EntityType.PLAYER, EntityType.SPOWER, (player, spower) -> {
            spower.removeFromWorld();
            speed = speed+(speed*.01);

        });
        
        //When the player moves over the attack power-up
        onCollisionBegin(EntityType.PLAYER, EntityType.APOWER, (player, apower) -> {
            apower.removeFromWorld();
            PDMG=PDMG+5;

        });
        
        //When the player moves over the health power-up
        onCollisionBegin(EntityType.PLAYER, EntityType.HPOWER, (player, hpower) -> {
            hpower.removeFromWorld();
            PHP = PHP+10;
            FXGL.inc("hp", 10);

        });
        
        //When the bullet collides with a wall
        onCollisionBegin(EntityType.BULLET, EntityType.WALL, (bullet, wall) -> {
            bullet.removeFromWorld();
            
        });
        
        // On player collision with harmful wall ----- IN PROGRESS - Arrowood
        onCollision(EntityType.PLAYER, EntityType.BADWALL, (player, badWall) -> {
        	var hp = player.getComponent(HealthDoubleComponent.class);
        	FXGL.play("player_oof.wav");
        	if (hp.getValue() > 1) {
                hp.damage(1);
                initUI();
                FXGL.inc("hp", -1);
        	}
        	
        });

      //When the player collides with the shop
        onCollisionBegin(EntityType.PLAYER, EntityType.SHOP, (player, shop) -> {
            FXGL.play("store_jingle.wav");
            ShopTest shopMenu = new ShopTest();
            getSceneService().pushSubScene(shopMenu);
            shopMenu.getInput().addAction(new UserAction("Close Shop") {
                @Override
                protected void onActionBegin() {
                    getSceneService().popSubScene();
                }
            }, KeyCode.F);

        });
        
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.WALL) {
	    	
	        @Override
	        protected void onCollision(Entity player, Entity wall) {
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
    
    private void killPlayer(Entity p) {
    	FXGL.getAudioPlayer().stopAllSounds();
		FXGL.play("yoda_death.wav");
		player.setPosition(getAppWidth() / 2 - 15, getAppHeight() / 2 - 15);
		player.setProperty("Health", PHP);
		getGameWorld().removeEntities(getGameWorld().getEntitiesByType(
				EntityType.COIN,EntityType.ENEMY,EntityType.SPOWER,EntityType.APOWER,EntityType.HPOWER,EntityType.BULLET));
		gameOver();
    }
    
    private void killEnemy(Entity e) {
    	Point2D cSpawnPoint = e.getCenter();
    	double rng = Math.random()*10;
    	if(rng < 7.5) {
    		spawn("coin", cSpawnPoint);
    	}
    	else {
    		if(rng >= 7.5 && rng < 8 ) {
    			spawn("bpower", cSpawnPoint);	
    		}
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
    
    private void gameOver() {
    	FXGL.getAudioPlayer().stopAllMusic();
    	getGameController().gotoMainMenu();
    	String BGM = new String("shop.mp3");
    	Music menuMusic = FXGL.getAssetLoader().loadMusic(BGM);
    	FXGL.getAudioPlayer().loopMusic(menuMusic);
    	PHP=100;
    	EDMG=10;
    	EHP=10;
    	PBC = 0;
//    	getGameController().startNewGame(); //This will reset the game state automatically!!
//    	getGameController().gotoIntro();
    }
    
    private class ShopTest extends SubScene{
    	public ShopTest() {
            getContentRoot().getChildren().addAll();
            getContentRoot().setTranslateX(300);
            getContentRoot().setTranslateY(0);

            Button buyHP = getUIFactoryService().newButton("Buy Health");
            buyHP.prefHeight(30.0);
            buyHP.prefWidth(135.0);
            buyHP.setTranslateX(35.0);
            buyHP.setTranslateY(320.0);

            buyHP.setOnAction(actionEvent -> {
//            	String strCoin = String.valueOf(FXGL.getip("Coins").asString());
//            	int coin = Integer.parseInt(strCoin);
//            	if(Integer.parseInt(String.valueOf(FXGL.getip("Coins"))) > 0){
//            		FXGL.play("player_oof.wav");
//            	}
            	var hp = player.getComponent(HealthDoubleComponent.class);
            		hp.restore(10);
            		FXGL.play("boom.wav");
            		FXGL.inc("Coins", -1);
            });

            Button buySpeed = getUIFactoryService().newButton("Buy Speed");
            buySpeed.prefHeight(30.0);
            buySpeed.prefWidth(135.0);
            buySpeed.setTranslateX(35.0);
            buySpeed.setTranslateY(370.0);

            buySpeed.setOnAction(actionEvent -> {
            	speed++;
        		FXGL.play("boom.wav");
        		FXGL.inc("Coins", -1);
            });

            this.getContentRoot().getChildren().addAll(buyHP, buySpeed);
        }
    	
    }

}