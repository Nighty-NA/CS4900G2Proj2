package animationComponent;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.util.Duration;

public class HealthAnimationComponent extends Component{
	
	private AnimationChannel health;
	private AnimatedTexture healtht;
	
	public HealthAnimationComponent(){
		health = new AnimationChannel(FXGL.image("HealthPotion.png"), 5, 32, 32, Duration.seconds(0.25), 0, 4);
		healtht = new AnimatedTexture(health);
	}
	
	public void onAdded() {
		entity.getViewComponent().addChild(healtht);
		healtht.loopAnimationChannel(health);
	}

}
