package myGame;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import static myGame.DawnseekerTypes.Type;

import static com.almasb.fxgl.dsl.FXGL.*;


public class DawnseekerTerrainFactory implements EntityFactory {

	

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
				.type(Type.WALL)
				.at(775, 782)
				.viewWithBBox(new Rectangle(88,58, Color.GRAY))
				.buildAndAttach();
	}
	@Spawns("W2")
	public Entity wall2(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(Type.WALL)
				.at(100,100)
				.viewWithBBox(new Rectangle(64,64, Color.GRAY))
				.buildAndAttach();
	}
	@Spawns("W3")
	public Entity wall3(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(Type.WALL)
				.at(200,150)
				.viewWithBBox(new Rectangle(64,128, Color.GRAY))
				.buildAndAttach();
	}
	
	@Spawns("W4")
	public Entity wall4(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(Type.WALL)
				.at(454,628)
				.viewWithBBox(new Rectangle(16,85, Color.GRAY))
				.buildAndAttach();
	}
	@Spawns("BWH")
	public Entity HBorder(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(Type.WALL)
				.at(992,0)
				.viewWithBBox(new Rectangle(32,1024, Color.GRAY))
				.build();
	}
	@Spawns("BWH2")
	public Entity HBorder2(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(Type.WALL)
				.at(0,0)
				.viewWithBBox(new Rectangle(32,1024, Color.GRAY))
				.build();
	}
	
	@Spawns("BWV")
	public Entity VBorder(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(Type.WALL)
				.at(0,992)
				.viewWithBBox(new Rectangle(1024,32, Color.GRAY))
				.build();
	}
	@Spawns("BWV2")
	public Entity VBorder2(SpawnData data) {
		return entityBuilder(data)
				.with(new CollidableComponent(true))
				.type(Type.WALL)
				.at(0,0)
				.viewWithBBox(new Rectangle(1024,32, Color.GRAY))
				.build();
	}
	
	
	
}
