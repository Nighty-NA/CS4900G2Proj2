package myGame;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;

import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class testest extends GameApplication{
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setTitle("Simple Game App");
        settings.setVersion("1.0dev");
        settings.setIntroEnabled(true);
        settings.setMainMenuEnabled(true);
    }

    public enum EntityType {
        PLAYER, COIN
    }

    protected void initInput() {
        onKey(KeyCode.D, () -> {
            player.translateX(5); // move right 5 pixels
            inc("pixelsMoved", +5);
        });

        onKey(KeyCode.A, () -> {
            player.translateX(-5); // move left 5 pixels
            inc("pixelsMoved", -5);
        });

        onKey(KeyCode.W, () -> {
            player.translateY(-5); // move up 5 pixels
            inc("pixelsMoved", +5);
        });

        onKey(KeyCode.S, () -> {
            player.translateY(5); // move down 5 pixels
            inc("pixelsMoved", +5);
        });

        onKeyDown(KeyCode.F, () -> {
            play("drop.wav");
        });
    }

    protected void initGameVars(Map<String, Object> vars) {
        vars.put("pixelsMoved", 0);
    }

    private Entity player;

    protected void initGame() {
        player = entityBuilder()
                .type(EntityType.PLAYER)
                .at(300, 300)
                .viewWithBBox("brick.png")
                .with(new CollidableComponent(true))
                .buildAndAttach();

        entityBuilder()
                .type(EntityType.COIN)
                .at(500, 200)
                .viewWithBBox(new Circle(15, 15, 15, Color.YELLOW))
                .with(new CollidableComponent(true))
                .buildAndAttach();
        
    }

    @Override
    protected void initPhysics() {
        // order of types on the right is the same as on the left
        onCollisionBegin(EntityType.PLAYER, EntityType.COIN, (player, coin) -> {
            coin.removeFromWorld();
        });
    }

    protected void initUI() {
        Text textPixels = new Text();
        textPixels.setTranslateX(50); // x = 50
        textPixels.setTranslateY(100); // y = 100
        textPixels.textProperty().bind(getip("pixelsMoved").asString());

        addUINode(textPixels); // add to the scene graph

        var brickTexture = texture("brick.png");
        brickTexture.setTranslateX(50);
        brickTexture.setTranslateY(450);

        addUINode(brickTexture);
    }
    
    protected void initMainMenu(Pane mainMenuRoot) {
		Rectangle bg = new Rectangle(1280, 720);
		
		Font font = Font.font(72);
		
		Button btnStart = new Button("START");
		btnStart.setFont(font);
		btnStart.setOnAction(event -> System.exit(0));
		
		Button btnExit = new Button("EXIT");
		btnExit.setFont(font);
		btnExit.setOnAction(event -> System.exit(0));
		
		VBox vbox = new VBox(50, btnStart, btnExit);
		vbox.setTranslateX(400);
		vbox.setTranslateY(200);
		
		mainMenuRoot.getChildren().addAll(bg, vbox);
		
	}

    public static void main(String[] args) {
    	launch(args);
    	
    }
}
