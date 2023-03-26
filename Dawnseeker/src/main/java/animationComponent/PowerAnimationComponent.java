package animationComponent;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.util.Duration;

public class PowerAnimationComponent extends Component{
	
	private AnimationChannel power;
	private AnimatedTexture powert;
	
	public PowerAnimationComponent() {
		power = new AnimationChannel(FXGL.image("Power.png"), 2, 16, 16, Duration.seconds(0.25), 0, 4);
		powert = new AnimatedTexture(power);
	}
	
	public void onAdded() {
		entity.getViewComponent().addChild(powert);
		powert.loopAnimationChannel(power);
	}
}
