package myGame;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGL.getInput;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

import com.almasb.fxgl.dsl.FXGL;
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

import animationComponent.AnimationComponent;
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
    	
        return entityBuilder()
                .type(EntityType.PLAYER)
                .bbox(new HitBox(BoundingShape.box(64, 64)))
                .with(new AnimationComponent())
                //.viewWithBBox("PlayerCharacterDawnseeker.png")
                .at(500,500)
                .collidable()
                .with("Helth", 3)
                .build();
    }
    
    @Spawns("enemy")
    public Entity newEnemy(SpawnData data) {
    	Circle circle = new Circle(20, 20, 20, Color.RED);
        circle.setStroke(Color.BROWN);
        circle.setStrokeWidth(2.0);
        int moveSpeed = 100;

        return entityBuilder()
        		.from(data)
                .type(EntityType.ENEMY)
                .viewWithBBox("EnemyDawnseeker.png")
                .collidable()
                .with("Helth", 10)
                .with("Dmg", 1)
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
                .viewWithBBox("FireBallProjectile.png")
                .collidable()
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
				.viewWithBBox(new Circle(3,3,4, Color.YELLOW))
				.build();
	}
	
	@Spawns("spower")
	public Entity spow(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(EntityType.SPOWER)
				.viewWithBBox(new Rectangle(8,8, Color.GREEN))
				.build();
	}
}

