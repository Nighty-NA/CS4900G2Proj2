package animationComponent;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;


import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import myGame.simplefactory;

/* Currently Unfinished */

public class ShopAnimationComponent extends Component{
	private AnimationChannel shop;

	public ShopAnimationComponent(){
		AnimationChannel anim =  new AnimationChannel(FXGL.image("AnimShop.png"), 2, 64, 64, Duration.seconds(0.5), 0, 1);
	}
	
	@Override
	public void onUpdate(double tpf) {
		Input input = new Input();
		Point2D mpos = input.getMousePositionUI();
		
	}
	
	
	
}
