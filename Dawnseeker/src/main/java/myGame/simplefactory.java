package myGame;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGL.getInput;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthDoubleComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.dsl.components.RandomMoveComponent;
import com.almasb.fxgl.dsl.components.WaypointMoveComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.action.ActionComponent;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.collision.ContactID.Type;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.ui.ProgressBar;


import animationComponent.PlayerAnimationComponent;
import animationComponent.ShopAnimationComponent;
import enemyComponent.BadGuyOne;
import enemyComponent.DelayedBadGuy;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import myGame.DawnseekerApp.EntityType;


public class simplefactory implements EntityFactory {
		
	//Implementing randomized spawns in the corners of the map
	private static Point2D getRandomSpawnEnemy() {
		int corner = FXGL.random(0, 3);
		if(corner == 0) {
			return new Point2D(50, 50);
		}
		else if (corner == 1) {
			return new Point2D(getAppWidth() - 50, 50);
		}
		else if (corner == 2) {
			return new Point2D(getAppWidth() - 50, getAppHeight() - 50);
		}
		else{
			return new Point2D(50, getAppHeight() - 50);
		}
	}
	
    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        var hp1 = new HealthDoubleComponent( DawnseekerApp.getPHP());
        var hp1View = new ProgressBar(false);
        hp1View.setFill(Color.RED);
        hp1View.setMaxValue(DawnseekerApp.getPHPM());
        hp1View.setTranslateY(55);
        hp1View.setTranslateX(-10);
        hp1View.setWidth(85);

        hp1View.currentValueProperty().bind(hp1.valueProperty());
        
        
        return entityBuilder()
                .type(EntityType.PLAYER)
                .bbox(new HitBox(BoundingShape.box(64, 64)))
                .with(new PlayerAnimationComponent())
                .at(500,500)
                .view(hp1View)
                .with(hp1)
                .collidable()
                //.with("Health", DawnseekerApp.getPHP())
                .build();
    }
    
    @Spawns("enemy")
    public Entity newEnemy(SpawnData data) {
        int moveSpeed = 100;
        var hp = new HealthDoubleComponent(DawnseekerApp.getEHP());
        var hpView = new ProgressBar(false);
        hpView.setFill(Color.LIGHTGREEN);
        hpView.setMaxValue(DawnseekerApp.getEHP());
        hpView.setWidth(85);
        hpView.setTranslateY(55);
        hpView.setTranslateX(-10);
        hpView.currentValueProperty().bind(hp.valueProperty());
        
        
        return entityBuilder()
        		.from(data)
                .type(EntityType.ENEMY)
                .view("Skull.gif")
                .bbox(new HitBox(BoundingShape.box(64, 64)))
                .collidable()
                .view(hpView)
                .with(hp)
                .with("Dmg", DawnseekerApp.getEDMG())
                .at(getRandomSpawnEnemy())
                .with(new BadGuyOne(FXGL.<DawnseekerApp>getAppCast().getPlayer(), moveSpeed))
                .build();
    }
    
    @Spawns("enemy2")
    public Entity newEnemy2(SpawnData data) {
        int moveSpeed = 125;
        var hp = new HealthDoubleComponent(DawnseekerApp.getEHP());
        var hpView = new ProgressBar(false);
        hpView.setFill(Color.LIGHTGREEN);
        hpView.setMaxValue(DawnseekerApp.getEHP());
        hpView.setWidth(85);
        hpView.setTranslateY(45);
        hpView.setTranslateX(-15);
        hpView.currentValueProperty().bind(hp.valueProperty());
        
        return entityBuilder()
        		.from(data)
                .type(EntityType.ENEMY2)
                .viewWithBBox("Ghost.gif")
                .collidable()
                .with(hp)
                .view(hpView)
                .with("Dmg", DawnseekerApp.getEDMG())
                .at(getRandomSpawnEnemy())
                .with(new DelayedBadGuy(FXGL.<DawnseekerApp>getAppCast().getPlayer(), moveSpeed))
                .build();
    }
    
    @Spawns("enemy3")
    public Entity newEnemy3(SpawnData data) {
    	Circle circle = new Circle(20, 20, 20, Color.RED);
        circle.setStroke(Color.BROWN);
        circle.setStrokeWidth(2.0);
        int moveSpeed = 50;
        var hp = new HealthDoubleComponent(DawnseekerApp.getEHP());
        var hpView = new ProgressBar(false);
        hpView.setFill(Color.LIGHTGREEN);
        hpView.setMaxValue(DawnseekerApp.getEHP());
        hpView.setWidth(85);
        hpView.setTranslateY(60);
        hpView.setTranslateX(-12);
        hpView.currentValueProperty().bind(hp.valueProperty());

        return entityBuilder()
        		.from(data)
                .type(EntityType.ENEMY3)
                .viewWithBBox("Bonker.gif")
                .collidable()
                .with(hp)
                .view(hpView)
                .with("Dmg", DawnseekerApp.getEDMG())
                .at(getRandomSpawnEnemy())
                .with(new BadGuyOne(FXGL.<DawnseekerApp>getAppCast().getPlayer(), moveSpeed))
                .build();
    }

    @Spawns("bullet")
    public Entity newBullet(SpawnData data) {
        Entity player = getGameWorld().getSingleton(EntityType.PLAYER);
        Point2D direction = getInput().getMousePositionWorld().subtract(player.getCenter());
        FXGL.play("magic_missile.wav"); // ----- This plays a sound every time the fireball is created.
        return entityBuilder()
        		.from(data)
                .type(EntityType.BULLET)
                .bbox(new HitBox(BoundingShape.box(15, 15)))
                .view("Fireball.gif")
                .collidable()
                .with("Dmg", DawnseekerApp.getPDMG())
                .with(new ProjectileComponent(direction, 1000))
                .with(new OffscreenCleanComponent())
                .build();
    }
    
    @Spawns("bullet2")
    public Entity newBullet2(SpawnData data) {
        Entity player = getGameWorld().getSingleton(EntityType.PLAYER);
        Point2D direction = getInput().getMousePositionWorld().subtract(player.getX()+10,player.getY()+10);
      
        
        FXGL.play("magic_missile.wav"); // ----- This plays a sound every time the fireball is created.
        return entityBuilder()
        		.from(data)
                .type(EntityType.BULLET)
                .viewWithBBox("FireBallProjectile.png")
                .collidable()
                .with("Dmg", DawnseekerApp.getPDMG())
                .with(new ProjectileComponent(direction, 1000))
                .with(new OffscreenCleanComponent())
                .build();
    }
    @Spawns("bullet3")
    public Entity newBullet3(SpawnData data) {
        Entity player = getGameWorld().getSingleton(EntityType.PLAYER);
        Point2D direction = getInput().getMousePositionWorld().subtract(player.getX()-10,player.getY()-10);
        FXGL.play("magic_missile.wav"); // ----- This plays a sound every time the fireball is created.
        return entityBuilder()
        		.from(data)
                .type(EntityType.BULLET)
                .viewWithBBox("FireBallProjectile.png")
                .collidable()
                .with("Dmg", DawnseekerApp.getPDMG())
                .with(new ProjectileComponent(direction, 1000))
                .with(new OffscreenCleanComponent())
                .build();
    }
    @Spawns("bullet4")
    public Entity newBullet4(SpawnData data) {
        Entity player = getGameWorld().getSingleton(EntityType.PLAYER);
        Point2D direction = getInput().getMousePositionWorld().subtract(player.getX()-20,player.getY()-20);
        FXGL.play("magic_missile.wav"); // ----- This plays a sound every time the fireball is created.
        return entityBuilder()
        		.from(data)
                .type(EntityType.BULLET)
                .viewWithBBox("FireBallProjectile.png")
                .collidable()
                .with("Dmg", DawnseekerApp.getPDMG())
                .with(new ProjectileComponent(direction, 1000))
                .with(new OffscreenCleanComponent())
                .build();
    }
    @Spawns("bullet5")
    public Entity newBullet5(SpawnData data) {
        Entity player = getGameWorld().getSingleton(EntityType.PLAYER);
        Point2D direction = getInput().getMousePositionWorld().subtract(player.getX()+20,player.getY()+20);
        FXGL.play("magic_missile.wav"); // ----- This plays a sound every time the fireball is created.
        return entityBuilder()
        		.from(data)
                .type(EntityType.BULLET)
                .viewWithBBox("FireBallProjectile.png")
                .collidable()
                .with("Dmg", DawnseekerApp.getPDMG())
                .with(new ProjectileComponent(direction, 1000))
                .with(new OffscreenCleanComponent())
                .build();
    }
    
    
	@Spawns("BG")
	public Entity background(SpawnData data) {
		return entityBuilder()
				.at(0,0)
				.view("background.png")
				.zIndex(-1)
				.buildAndAttach();
	}
	
	@Spawns("W")
	public Entity wall(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(EntityType.WALL)
				.at(200, 800)
				.viewWithBBox("Rock1.png")
				.collidable()
				.buildAndAttach();
	}
	
	@Spawns("W2")
	public Entity wall2(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(EntityType.WALL)
				.at(800,500)
				.viewWithBBox("Rock2.png")
				.collidable()
				.buildAndAttach();
	}
	
	@Spawns("W3")
	public Entity wall3(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(EntityType.WALL)
				.at(1205,150)
				.viewWithBBox("BigRock1.png")
				.collidable()
				.buildAndAttach();
	}
	
	@Spawns("coin")
	public Entity coin(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(EntityType.COIN)
				.view("Coin.gif")
				.bbox(new HitBox(BoundingShape.box(16, 16)))
				.build();
	}
	
	@Spawns("spower")
	public Entity spow(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(EntityType.SPOWER)
				.view("SpeedPotion.gif")
				.bbox(new HitBox(BoundingShape.box(16, 16)))
				.build();
	}
	
	@Spawns("apower")
	public Entity apow(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(EntityType.APOWER)
				.view("PowerPotion.gif")
				.bbox(new HitBox(BoundingShape.box(16, 16)))
				.build();
	}
	
	@Spawns("bpower")
	public Entity bpow(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(EntityType.BPOWER)
				.view("Fireball.gif")
				.bbox(new HitBox(BoundingShape.box(16, 16)))
//				.viewWithBBox(new Rectangle(8,8, Color.PURPLE))
				.build();
	}
	
	@Spawns("hpower")
	public Entity hpow(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(EntityType.HPOWER)
				.view("HealthPotion.gif")
				.bbox(new HitBox(BoundingShape.box(16, 16)))
				.build();
	}
	
	@Spawns("badWall")
	public Entity badWall(SpawnData data) {
        var hp = new HealthDoubleComponent(DawnseekerApp.getEHP() * .5);
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(EntityType.BADWALL)
				.viewWithBBox("FIRE.gif")
				.with(hp)
				.buildAndAttach();
	}
    
    @Spawns("shop")
	public Entity shop(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(EntityType.SHOP)
				.at(300, 600)
				.bbox(new HitBox(BoundingShape.box(64, 64)))
				.with(new ShopAnimationComponent())
				.buildAndAttach();
	}
}

