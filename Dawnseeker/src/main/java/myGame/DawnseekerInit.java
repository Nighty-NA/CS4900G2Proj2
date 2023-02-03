package myGame;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.app.scene.SimpleGameMenu;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.text.TextLevelLoader;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.almasb.fxgl.physics.*;
import com.almasb.fxgl.entity.*;
import javafx.scene.input.KeyCode;
import static myGame.DawnseekerTypes.Type;

import static com.almasb.fxgl.dsl.FXGL.*;

public class DawnseekerInit extends GameApplication {
	
	
	private AStarGrid grid;
	
	public AStarGrid getGrid() {
		return grid;
	}
	
	protected void initSettings(GameSettings settings) {
		//settings.setIntroEnabled(true);
		settings.setWidth(1024);
		settings.setHeight(1024);
		settings.setTitle("Dawnseeker");
		settings.setVersion("0.1");

	}
	
	@Override
	protected void initGame() {
		 getGameWorld().addEntityFactory(new DawnseekerTerrainFactory());

//	        Level level = getAssetLoader().loadLevel("0.txt", new TextLevelLoader(40, 40, '0'));
//	        getGameWorld().setLevel(level); 
		 // Not to be messed with rn

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
	            if (type.equals(Type.WALL))
	                return CellState.NOT_WALKABLE;

	            return CellState.WALKABLE;
	        });
	}
	
	@Override
	protected void initPhysics() {
	    FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(Type.PLAYER, Type.WALL) {
	    	
	        @Override
	        protected void onCollisionBegin(Entity player, Entity wall) {
	           //TODO: Add behavior to stop player movement
	        }
	    });
	    
	    FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(Type.ENEMY, Type.WALL) {
	    	
	        @Override
	        protected void onCollisionBegin(Entity enemy, Entity wall) {
	           //TODO: Add behavior to stop enemy movement
	        }
	    });
	
	}
	
	public static void main(String[]args) {
		launch(args);
	}
	
}
