package animationComponent;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.util.Duration;

public class FireballAnimationComponent extends Component{
		private AnimationChannel fireball;
		private AnimatedTexture texture;
		
		public FireballAnimationComponent() {
			fireball = new AnimationChannel(FXGL.image("FireBallProjectile.png"), 2, 64, 64, Duration.seconds(0.5), 0, 1);
			texture = new AnimatedTexture(fireball);
		}
		
		public void onAdded() {
			entity.getViewComponent().addChild(texture);
			texture.loopAnimationChannel(fireball);
		}
}
