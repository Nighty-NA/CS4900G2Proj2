package enemyComponent;

import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.state.EntityState;
import com.almasb.fxgl.entity.state.StateComponent;
import com.almasb.fxgl.pathfinding.astar.AStarCell;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.state.EntityState;
import com.almasb.fxgl.entity.state.StateComponent;
import com.almasb.fxgl.pathfinding.astar.AStarCell;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AIChaseComponent extends Component {
	
	private static final List<AStarCell> MOVE_TO_CELLS = new ArrayList<>();
	
	private StateComponent state;
	private LazyValue<AStarMoveComponent> astar = new LazyValue<>(() -> {
        return entity.getComponent(AStarMoveComponent.class);
    });
	//private BadGuyOne bgo;
	
	private Entity player;
	private Entity enemy;
	
	private AStarCell moveToCell = null;

}
			
