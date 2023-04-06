package myGame;

import static com.almasb.fxgl.dsl.FXGL.getUIFactoryService;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class DawnseekerMenu extends FXGLMenu {

	public DawnseekerMenu() {
		super(MenuType.MAIN_MENU);

		DSButton btnPlayGame = new DSButton("Play Game", () -> fireNewGame());
		DSButton btnOptions = new DSButton("Options", () -> {});
		DSButton btnQuit = new DSButton("Quit", () -> fireExit());
		
		
		var box = new VBox(15, btnPlayGame, btnOptions, btnQuit);
		box.setAlignment(Pos.CENTER_LEFT);
		box.setTranslateX(100);
		box.setTranslateY(550);
		
		//The background is created first before the box is generated. If reversed, the box will appear behind the .png.
		getContentRoot().getChildren().add(createBackground(1024, 1024));
		getContentRoot().getChildren().addAll(box);
	}
//	@Override
	protected Button createActionButton(StringBinding stringBinding, Runnable runnable) {
		return new Button();
	}
	
//	@Override 
	protected Button createActionButton(String s, Runnable runnable) {
		return new Button();
	}
	
//	@Override
	protected Node createBackground(double w, double h) {
		Node node = FXGL.texture("bg.png");
		return node;
	}
	
//	@Override
	protected Node createProfileView(String s) {
		return new Rectangle();
	}
	
//	@Override
	protected Node createTitleView(String s) {
		return new Rectangle();
	}
	
//	@Override
	protected Node createVersionView(String s) {
		return new Rectangle();
	}
	
	private static class DSButton extends StackPane{
		private String name;
		private Runnable action;
		
		private Text text;
		
		public DSButton(String name, Runnable action) {
			this.name = name;
			this.action = action;
			
			text = getUIFactoryService().newText(name, Color.BLACK, 30.0);
			
			text.fillProperty().bind(
                    Bindings.when(hoverProperty())
                            .then(Color.RED)
                            .otherwise(Color.WHITE)
            );
		
			setOnMouseClicked(e -> action.run());
			
			setAlignment(Pos.CENTER_LEFT);
			
			getChildren().addAll(text);
		
		}
		
		
	}
}
