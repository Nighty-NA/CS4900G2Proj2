package animationComponent;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.util.Duration;

public class CoinAnimationComponent extends Component {
	
	private AnimationChannel coin;
	private AnimatedTexture texture;
	
	public CoinAnimationComponent(){
		coin = new AnimationChannel(FXGL.image("Coin.png"), 2, 16, 16, Duration.seconds(0.25), 0, 4);
		texture = new AnimatedTexture(coin);
	}
	
	public void onAdded() {
		entity.getViewComponent().addChild(texture);
		texture.loopAnimationChannel(coin);
	}
}
