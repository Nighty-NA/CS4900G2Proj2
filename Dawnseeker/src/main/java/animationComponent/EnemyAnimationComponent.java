package animationComponent;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.util.Duration;

public class EnemyAnimationComponent extends Component{
	private AnimationChannel enemy1;
	private AnimatedTexture enemy1t;
	
	public EnemyAnimationComponent(){
		enemy1 = new AnimationChannel(FXGL.image("EnemyDawnseeker.png"), 2, 64, 64, Duration.seconds(0.25), 0, 1);
		enemy1t = new AnimatedTexture(enemy1);
	}
	
	public void onAdded() {
		entity.getViewComponent().addChild(enemy1t);
		enemy1t.loopAnimationChannel(enemy1);
		
	}
}
