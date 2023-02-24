package enemyComponent;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import javafx.util.Duration;


/**
 * with the help of Almas Baimagambetov (almaslvl@gmail.com)
 */

//this class allows the player to move along the grid
public class PlayerComponent extends Component {

    private CellMoveComponent cell;
    private AStarMoveComponent astar;

    public void moveRight() {
        astar.moveToRightCell();
    }

    public void moveLeft() {
        astar.moveToLeftCell();
    }

    public void moveUp() {
        astar.moveToUpCell();
    }

    public void moveDown() {
        astar.moveToDownCell();
    }
}

