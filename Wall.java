import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.media.AudioClip;
import java.net.URL;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javafx.scene.paint.*;

public class Wall
{
	GraphicsContext gc;
	private int x;
	private int y;
	public Wall(int x, int y)
	{
		this.x=x;
		this.y=y;
	}

	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
}