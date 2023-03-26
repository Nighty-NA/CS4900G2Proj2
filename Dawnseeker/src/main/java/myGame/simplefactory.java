package myGame;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGL.getInput;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

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

import animationComponent.CoinAnimationComponent;
import animationComponent.EnemyAnimationComponent;
import animationComponent.FireballAnimationComponent;
import animationComponent.HealthAnimationComponent;
import animationComponent.PlayerAnimationComponent;
import animationComponent.PowerAnimationComponent;
import animationComponent.SpeedAnimationComponent;
import enemyComponent.BadGuyOne;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import myGame.DawnseekerApp.EntityType;


public class simplefactory implements EntityFactory {
	
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
<<<<<<< HEAD
                .with(new AnimationComponent())
=======
                .with(new PlayerAnimationComponent())
                //.viewWithBBox("PlayerCharacterDawnseeker.png")
>>>>>>> refs/remotes/origin/Johnson
                .at(500,500)
                .view(hp1View)
                .with(hp1)
                .collidable()
                //.with("Health", DawnseekerApp.getPHP())
                .build();
    }
    
    @Spawns("enemy")
    public Entity newEnemy(SpawnData data) {
    	Circle circle = new Circle(20, 20, 20, Color.RED);
        circle.setStroke(Color.BROWN);
        circle.setStrokeWidth(2.0);
        int moveSpeed = 100;
        var hp = new HealthDoubleComponent( DawnseekerApp.getEHP());
        var hpView = new ProgressBar(false);
        hpView.setFill(Color.LIGHTGREEN);
        hpView.setMaxValue(DawnseekerApp.getEHP());
        hpView.setWidth(85);
        hpView.setTranslateY(45);
        hpView.setTranslateX(-25);
        hpView.currentValueProperty().bind(hp.valueProperty());
        
        
        return entityBuilder()
        		.from(data)
                .type(EntityType.ENEMY)
                .with(new EnemyAnimationComponent())
                .bbox(new HitBox(BoundingShape.box(64, 64)))
                .collidable()
                .view(hpView)
                .with(hp)
                .with("Dmg", DawnseekerApp.getEDMG())
                .at(Math.random() *1000,Math.random() *1000)
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
                .bbox(new HitBox(BoundingShape.box(64, 64)))
                .with(new FireballAnimationComponent())
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
				.view(new Rectangle(1024, 1024, Color.LIGHTGREEN))
				.zIndex(-1)
				.buildAndAttach();
	}
	
	@Spawns("W")
	public Entity wall(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(EntityType.WALL)// was set to just set to type was changed to EntityType
				.at(775, 782)
				.viewWithBBox(new Rectangle(88,58, Color.GRAY))
				.collidable()
				.buildAndAttach();
	}
	
	@Spawns("W2")
	public Entity wall2(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(EntityType.WALL)
				.at(100,100)
				.viewWithBBox(new Rectangle(64,64, Color.GRAY))
				.collidable()
				.buildAndAttach();
	}
	
	@Spawns("W3")
	public Entity wall3(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(EntityType.WALL)
				.at(200,150)
				.viewWithBBox(new Rectangle(64,128, Color.GRAY))
				.collidable()
				.buildAndAttach();
	}
	
	@Spawns("W4")
	public Entity wall4(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(EntityType.WALL)
				.at(454,628)
				.viewWithBBox(new Rectangle(16,85, Color.GRAY))
				.collidable()
				.buildAndAttach();
	}
	
	@Spawns("BWH")
	public Entity HBorder(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(EntityType.WALL)
				.at(992,0)
				.viewWithBBox(new Rectangle(32,1024, Color.GRAY))
				.collidable()
				.buildAndAttach();
	}
	
	@Spawns("BWH2")
	public Entity HBorder2(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(EntityType.WALL)
				.at(0,0)
				.viewWithBBox(new Rectangle(32,1024, Color.GRAY))
				.collidable()
				.buildAndAttach();
	}
	
	@Spawns("BWV")
	public Entity VBorder(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(EntityType.WALL)
				.at(0,992)
				.viewWithBBox(new Rectangle(1024,32, Color.GRAY))
				.collidable()
				.buildAndAttach();
	}
	
	@Spawns("BWV2")
	public Entity VBorder2(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(EntityType.WALL)
				.at(0,0)
				.viewWithBBox(new Rectangle(1024,32, Color.GRAY))
				.collidable()
				.buildAndAttach();
	}
	
	@Spawns("coin")
	public Entity coin(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(EntityType.COIN)
				.with(new CoinAnimationComponent())
				.bbox(new HitBox(BoundingShape.box(16, 16)))
				.build();
	}
	
	@Spawns("spower")
	public Entity spow(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(EntityType.SPOWER)
				.with(new SpeedAnimationComponent())
				.build();
	}
	
	@Spawns("apower")
	public Entity apow(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(EntityType.APOWER)
				.with(new PowerAnimationComponent())
				.build();
	}
	
	@Spawns("hpower")
	public Entity hpow(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(EntityType.HPOWER)
				.with(new HealthAnimationComponent())
				.build();
	}
	
	@Spawns("badWall")
	public Entity badWall(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(EntityType.BADWALL)
				.at(300,450)
				.viewWithBBox(new Rectangle(64,64, Color.RED))
				.buildAndAttach();
	}
}

