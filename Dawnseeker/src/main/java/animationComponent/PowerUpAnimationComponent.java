package animationComponent;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.util.Duration;

public class PowerUpAnimationComponent extends Component{
	
	private AnimationChannel power;
	private AnimationChannel speed;
	private AnimationChannel health;
	private AnimatedTexture powert;
	private AnimatedTexture speedt;
	private AnimatedTexture healtht;
	
	public PowerUpAnimationComponent(){
		power = new AnimationChannel(FXGL.image("Power.png"), 2, 16, 16, Duration.seconds(0.25), 0, 4);
		speed = new AnimationChannel(FXGL.image("Speed.png"), 2, 16, 16, Duration.seconds(0.25), 0, 4);
		health = new AnimationChannel(FXGL.image("HealthPotion.png"), 2, 16, 16, Duration.seconds(0.25), 0, 4);
		powert = new AnimatedTexture(power);
		speedt = new AnimatedTexture(speed);
		healtht = new AnimatedTexture(health);

		
	}
	
	public void onAdded() {
		entity.getViewComponent().addChild(powert);
		powert.loopAnimationChannel(power);
		entity.getViewComponent().addChild(speedt);
		speedt.loopAnimationChannel(speed);
		entity.getViewComponent().addChild(healtht);
		healtht.loopAnimationChannel(health);
	}
	
}
