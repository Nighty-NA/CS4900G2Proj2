package animationComponent;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.util.Duration;

public class SpeedAnimationComponent extends Component{
	private AnimationChannel speed;
	private AnimatedTexture speedt;
	
	public SpeedAnimationComponent() {
		speed = new AnimationChannel(FXGL.image("Speed.png"), 2, 16, 16, Duration.seconds(0.25), 0, 4);
		speedt = new AnimatedTexture(speed);
	}
	
	public void onAdded() {
		entity.getViewComponent().addChild(speedt);
		speedt.loopAnimationChannel(speed);
	}
}
