package animationComponent;



import static com.almasb.fxgl.dsl.FXGL.onKey;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import myGame.simplefactory;

public class PlayerAnimationComponent extends Component {
	private int horiSpeed = 0;
	private int vertSpeed = 0;
	
	private AnimatedTexture texture;
	private AnimationChannel idle,walkDown,walkUp,walkRight,walkLeft;
	
	
	public PlayerAnimationComponent() {
		idle = new AnimationChannel(FXGL.image("DawnseekerPCV2.png"), 4, 64, 64, Duration.seconds(0.5), 0, 0);
		walkDown = new AnimationChannel(FXGL.image("DawnseekerPCV2Down.png"), 4, 64, 64, Duration.seconds(1), 0, 3);
		walkUp = new AnimationChannel(FXGL.image("DawnseekerPCV2Up.png"), 4, 64, 64, Duration.seconds(1), 0, 3);
		walkRight = new AnimationChannel(FXGL.image("DawnseekerPCV2Right.png"), 4, 64, 64, Duration.seconds(1), 0, 3);
		walkLeft = new AnimationChannel(FXGL.image("DawnseekerPCV2Left.png"), 4, 64, 64, Duration.seconds(1), 0, 3);
		
		texture = new AnimatedTexture(idle);
	
	}
	
	@Override
	public void onAdded() {
		entity.getViewComponent().addChild(texture);
	}
	
	@Override
	public void onUpdate(double tpf) {
		if(vertSpeed < 0) {
			if (texture.getAnimationChannel() == idle) {
	            texture.loopAnimationChannel(walkDown);
	            
	        }

			
			vertSpeed = (int) (vertSpeed * 0.9);

	        if (FXGLMath.abs(vertSpeed) < 1) {
	            vertSpeed = 0;
	            texture.loopAnimationChannel(idle);
	        }
	      
		}
		
		if(vertSpeed > 0) {
			if (texture.getAnimationChannel() == idle) {
	            texture.loopAnimationChannel(walkUp);
	            
	        }

			
			vertSpeed = (int) (vertSpeed * 0.9);

	        if (FXGLMath.abs(vertSpeed) < 1) {
	            vertSpeed = 0;
	            texture.loopAnimationChannel(idle);
	        }
	        
	    
		}
		
		if(horiSpeed > 0) {
			if (texture.getAnimationChannel() == idle) {
	            texture.loopAnimationChannel(walkRight);
	            
	        }

			
			horiSpeed = (int) (horiSpeed * 0.9);

	        if (FXGLMath.abs(horiSpeed) < 1) {
	            horiSpeed = 0;
	            texture.loopAnimationChannel(idle);
	        }
		}
		
		if(horiSpeed < 0) {
			if (texture.getAnimationChannel() == idle) {
	            texture.loopAnimationChannel(walkLeft);
	            
	        }

			
			horiSpeed = (int) (horiSpeed * 0.9);

	        if (FXGLMath.abs(horiSpeed) < 1) {
	            horiSpeed = 0;
	            texture.loopAnimationChannel(idle);
	        }
		}
		
	}
	
	public void moveDown() {
		vertSpeed = -3;
	}
	
	
	public void moveUp() {
		vertSpeed = 3;
	}
	
	public void moveRight() {
		horiSpeed = 3;
	}
	
	public void moveLeft() {
		horiSpeed = -3;
	}
}
	
	
	

