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
import myGame.DawnseekerApp;
import myGame.simplefactory;


/* Currently Unfinished */

public class ShopAnimationComponent extends Component{
	private AnimationChannel flash;
	private AnimatedTexture texture;

	public ShopAnimationComponent(){
		flash =  new AnimationChannel(FXGL.image("AnimShop.png"), 2, 64, 64, Duration.seconds(1), 0, 1);
		texture = new AnimatedTexture(flash);
	}
	
	public void onAdded() {
		entity.getViewComponent().addChild(texture);
	}
	
	@Override
	public void onUpdate(double tpf) {
		Input input = new Input();
		Point2D mpos = input.getMousePositionWorld();
		if(((mpos.getX() >= 300) && (mpos.getY() <= 364)) && ((mpos.getY() >= 600) && (mpos.getY() <= 664))) 
		{
			texture.loopAnimationChannel(flash);
		}
	}
	
	
	
}
