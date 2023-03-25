package myGame;

public final class ProgressBar extends javafx.scene.Parent {
	 ProgressBar hpBar = new ProgressBar();
	 hpBar.setMinValue(0);
	 hpBar.setMaxValue(1000);
	 hpBar.setCurrentValue(1000);
	 hpBar.setWidth(300);
	 hpBar.setLabelVisible(true);
	 hpBar.setLabelPosition(Position.RIGHT);
	 hpBar.setFill(Color.GREEN);

	 ProgressBar(hpBar);
}
