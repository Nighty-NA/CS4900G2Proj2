package myGame;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class FLGLMenuTestApp extends GameApplication {

	@Override
	protected void initSettings(GameSettings settings) {
		settings.setWidth(1280);
		settings.setHeight(720);

	}
	
//	@Override
//	protected void initMainMenu(Pane mainMenuRoot) {
//		Rectangle bg = new Rectangle(1280, 720);
//		
//		Font font = Font.font(72);
//		
//		Button btnStart = new Button("START");
//		btnStart.setFont(font);
//		btnStart.setOnAction(event -> System.exit(0));
//		
//		Button btnExit = new Button("EXIT");
//		btnExit.setFont(font);
//		btnExit.setOnAction(event -> System.exit(0));
//		
//		VBox vbox = new VBox(50, btnStart, btnExit);
//		vbox.setTranslateX(400);
//		vbox.setTranslateY(200);
//		
//		mainMenuRoot.getChildren().addAll(bg, vbox);
//		
//	}

	public static void main(String[] args) {
launch(args);
	}

}
