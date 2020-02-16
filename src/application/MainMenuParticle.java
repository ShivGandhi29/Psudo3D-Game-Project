package application;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javafx.scene.paint.Color;

public class MainMenuParticle {

	//current location of particle
	private double x, y;
	//size of particle
	private double w, h;
	//current movement speed of particle
	private double dx, dy;

	private Color colour;

	//for creating random particle values
	private static final Random random = new Random();

	MainMenuParticle (int windowSize) {
		x = (double) random.nextInt(windowSize) + 1;
		y = 0;
		dx = 0;
		dy = random.nextDouble()*2;
		w = (double) random.nextInt(3) + 1;
		h = w;;
		colour = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256), random.nextDouble());
	}

	public void update() {
		x += dx;
		y += dy;
	}

	public boolean canRemove(int windowSize) {
		return y > windowSize;
	}


	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getW() {
		return w;
	}

	public double getH() {
		return h;
	}

	public double getDx() {
		return dx;
	}

	public double getDy() {
		return dy;
	}

	public Color getColour() {
		return colour;
	}



}
