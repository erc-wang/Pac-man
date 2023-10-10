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
public class Ghost
{
	private int x;
	private int y;
	private int xpos;
	private int ypos;
	private int oldx;
	private int oldy;
	private int oldxpos;
	private int oldypos;
	private Rectangle2D rghost;
	Canvas canvas;
	GraphicsContext gc;
	private Image ghost;
	private int wallcheck=0;
	int counter=0;
	public Ghost(int x, int y, int xpos, int ypos, int oldx, int oldy, int oldxpos, int oldypos, Image ghost, int wallcheck)
	{
		this.x=x;
		this.y=y;
		this.xpos=xpos;
		this.ypos=ypos;
		this.oldx=oldx;
		this.oldy=oldy;
		this.oldxpos=oldxpos;
		this.oldypos=oldypos;
		this.ghost=ghost;
		this.rghost = new Rectangle2D(xpos, ypos, 0, 0);
		this.wallcheck=wallcheck;
	}
	public void ghostMove(GraphicsContext gc, Canvas canvas, int timer, int x, int y, ArrayList<Rectangle2D> plist, ArrayList<Rectangle2D> wlist)
	{
		if(timer==300)
		{
			gc.drawImage(ghost, xpos, ypos);
		}

		if(timer>300)
		{
			oldxpos=xpos;
			oldypos=ypos;
			gc.clearRect(xpos, ypos, ghost.getWidth(), ghost.getHeight());
			x++;
			xpos+= x;
			ypos+= y;
			gc.drawImage(ghost, xpos, ypos);
			if(xpos==-15)
			{
				gc.drawImage( ghost, canvas.getWidth(), ypos);
				gc.clearRect( xpos, ypos, ghost.getWidth(), ghost.getHeight());
				xpos = (int)canvas.getWidth();
			}
			else if(xpos>canvas.getWidth())
			{
				gc.drawImage( ghost, -15, ypos);
				gc.clearRect( xpos, ypos, ghost.getWidth(), ghost.getHeight());
				xpos = -15;
			}
			for(int i=0; i<wlist.size(); i++)
			{
				if(rghost.intersects(wlist.get(i)))
				{
					wallcheck++;
				}
			}
			if(wallcheck!=0)
			{
				if(x!=0)
					oldx=0;
				if(y!=0)
					oldy=0;
				gc.clearRect(xpos, ypos, ghost.getWidth(), ghost.getHeight());
				xpos=oldxpos;
				ypos=oldypos;
				wallcheck=0;
			}
		}
	}
	public int getX()
	{
		return x;
	}
	public void setX(int x)
	{
		this.x=x;
	}
	public void setY(int y)
	{
		this.y=y;
	}
	public int getY()
	{
		return y;
	}
	public int getOldX()
	{
		return oldx;
	}
	public int getOldY()
	{
		return oldy;
	}
	public int getXPos()
	{
		return xpos;
	}
	public int getYPos()
	{
		return ypos;
	}
	public int getOldXPos()
	{
		return oldxpos;
	}
	public int getOldYPos()
	{
		return oldypos;
	}
	public Image getGhost()
	{
		return ghost;
	}
	public Rectangle2D getRect()
	{
		return rghost;
	}
	public int getWallCheck()
	{
		return wallcheck;
	}
	public void setWallCheck(int i)
	{
		this.wallcheck=i;
	}
	public void setXPos(int xpos)
	{
		this.xpos=xpos;
	}
	public void setYPos(int ypos)
	{
		this.ypos=ypos;
	}
	public int getCounter()
	{
		return counter;
	}
}