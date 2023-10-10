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
public class PacMan extends Application implements EventHandler<InputEvent>
{
	int pointcheck=0;
	int points=0;
	int oldpoints=0;
	int oldxpos=108;
	int oldypos=204;
	int wallcheck=0;
	Canvas canvas;
	GraphicsContext gc;
	Image pacman;
	Image lives;
	Image ghost1 = new Image("ghost1.png");
	Image ghost2 = new Image("ghost2.png");
	Image ghost3 = new Image("ghost3.png");
	BufferedImage level;
	AnimateObjects animate;
	ArrayList<Rectangle2D> plist =  new ArrayList<Rectangle2D>();
	ArrayList<Rectangle2D> wlist = new ArrayList<Rectangle2D>();
	int oldx=0;
	int oldy=0;
	int x=0;
	int y=0;
	int check=0;
	int wcheck=0;
	int xpos=108;
	int ypos=208;
	int timer=0;
	Ghost g1 = new Ghost(0, 0, 108, 208, 0, 0, 108, 208, ghost1, 0);
	Ghost g2 = new Ghost(0, 0, 103, 106, 0, 0, 103, 105, ghost2, 0);
	Ghost g3 = new Ghost(0, 0, 103, 106, 0, 0, 103, 105, ghost3, 0);
	URL resource;
	AudioClip clip;
	URL r2;
	AudioClip clip2;
	//ImageView v1 = new ImageView();
	public static final int WIDTH = 224, HEIGHT = 296;
	int win=0;
	public static void main(String[]args)
	{
		launch();
	}
	public void start(Stage stage)
	{
		stage.setTitle("Pac-Man");
		Group root = new Group();
		canvas = new Canvas(WIDTH, HEIGHT);
		root.getChildren().add(canvas);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		/*v1.setImage(pacman);
		root.getChildren().add(v1);
*/
		BufferedImageLoad load = new BufferedImageLoad();
		level = load.loadImage("/map.png");

		gc = canvas.getGraphicsContext2D();
		loadLevel(level);

		scene.addEventHandler(KeyEvent.KEY_PRESSED,this);
		scene.addEventHandler(MouseEvent.MOUSE_CLICKED, this);

		pacman = new Image( "pacman.png" );
		lives = new Image( "pacman.png" );
		gc.drawImage( lives, 20, 272);

		gc.drawImage( lives, 36, 272);
		gc.drawImage( lives, 36, 272);
		/*v1.setX(xpos);
		v1.setY(ypos);
		*/gc.drawImage( pacman, xpos, ypos);
		animate = new AnimateObjects();
		animate.start();
		stage.show();
resource = getClass().getResource("pacman_chomp.wav");
clip = new AudioClip(resource.toString());
r2 = getClass().getResource("pacman_beginning.wav");
clip2 = new AudioClip(r2.toString());
	}

	public void loadLevel(BufferedImage image)
	{
		Wall wall;
		Pellets pellet;
		int w = image.getWidth();
		int h = image.getHeight();
		for(int xx=0; xx<w; xx++)
		{
			for(int yy=0; yy<h; yy++)
			{
				int pixel = image.getRGB(xx,yy);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				if(red == 63 && green == 72 && blue == 204)
				{
					wall = new Wall(xx*4, yy*4);
					wlist.add(new Rectangle2D((xx*4), (yy*4), 4, 4));
					gc.setFill(Color.BLUE);
					gc.fillRect((xx*4), (yy*4), 2, 2);
				}
				if(red == 237 && green == 28 && blue == 36)
				{
					pellet = new Pellets(xx*4, yy*4);
					plist.add(new Rectangle2D((xx*4), (yy*4), 4, 4));
					gc.setFill(Color.YELLOW);
					gc.fillRect(xx*4, yy*4, 3, 3);
				}
			}
		}
		System.out.println(plist.size());
	}

	public class AnimateObjects extends AnimationTimer
	{
		public void handle(long now)
		{
			if(win==0)
			{
			//while(x!=0 || y!=0)
			if(timer==0)
				clip2.play();
			timer++;
			Rectangle2D rectp = new Rectangle2D(xpos, ypos, pacman.getWidth()+1, pacman.getHeight()+1);
			if((timer%30==0 && x!=0) || (timer%30==0 && y!=0))
				clip.play();
			if(timer>=300)
			{
				g1.ghostMove(gc, canvas, timer, g1.getX(), g1.getY(), plist, wlist);

			}

			gc.setFill(Color.BLACK);
			gc.fillRect(g1.getXPos(), g1.getYPos(), g1.getGhost().getWidth(), g1.getGhost().getHeight());
			oldpoints=points;
			oldxpos=xpos;
			oldypos=ypos;
			xpos+=x;
			ypos+=y;
			if(xpos==-15)
			{
				gc.drawImage( pacman, canvas.getWidth(), ypos);
				gc.clearRect( xpos, ypos, pacman.getWidth(), pacman.getHeight());
				xpos = (int)canvas.getWidth();
			}
			else if(xpos>canvas.getWidth())
			{
				gc.drawImage( pacman, -15, ypos);
				gc.clearRect( xpos, ypos, pacman.getWidth(), pacman.getHeight());
				xpos = -15;
			}
			for(int i=0; i<wlist.size(); i++)
			{
				if(rectp.intersects(wlist.get(i)))
					wallcheck++;
			}
			if(wallcheck!=0)
			{
				if(x!=0)
				{
					oldx=0;
				}
				if(y!=0)
				{
					oldy=0;
				}
				gc.clearRect( xpos, ypos, pacman.getWidth(), pacman.getHeight());
				xpos=oldxpos;
				ypos=oldypos;
				wallcheck=0;
			}
			for(int i=plist.size()-1; i>-1; i--)
			{
				if(rectp.intersects(plist.get(i)))
				{
					pointcheck++;
					plist.remove(i);
				}
			}
			if(pointcheck!=0)
			{
				pointcheck=0;
				points+=10;
			}
			gc.clearRect(oldxpos, oldypos, pacman.getWidth(), pacman.getHeight());
			gc.drawImage( pacman, xpos, ypos );
						//v1.setX(xpos);
			//v1.setY(ypos);
			gc.setFill(Color.BLACK);
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(1);
			Font font = Font.font("Arial", FontWeight.NORMAL, 12);
			gc.setFont(font);
			gc.fillText("SCORE", 90, 9);
			gc.clearRect(90, 21, 50, 2);
			gc.fillText(Integer.toString(points), 90, 21);
			if(oldpoints!=points)
			gc.clearRect(0, 15, canvas.getWidth(), 10);
			if(plist.size()==0)
			{
				win++;
			}
		}
		else
		{
			gc.fillText("You Win!", 90, 30);
				gc.fillText("Press R to Restart", 90, 39);
			}

		}
	}
	public void handle(final InputEvent event)
	{
			if (event instanceof KeyEvent)
			{
				if(win!=0 && ((KeyEvent)event).getCode() == KeyCode.R)
						{
							gc.clearRect(0, 15, canvas.getWidth(), 10);
							win=0;
							for(int i=plist.size()-1; i>-1; i--)
							{
								plist.remove(i);
							}
							for(int i=wlist.size()-1; i>-1; i--)
							{
								wlist.remove(i);
							}
							gc.clearRect(oldxpos, oldypos, pacman.getWidth(), pacman.getHeight());
							loadLevel(level);
							pointcheck=0;
							oldpoints=0;
							oldxpos=108;
							oldypos=208;
							wallcheck=0;
							xpos=108;
							ypos=208;
							oldx=0;
							oldy=0;
							x=0;
							y=0;
							timer=0;
							check=0;
							wcheck=0;

		}
				if (((KeyEvent)event).getCode() == KeyCode.LEFT )
				{
					if((x==1 && oldx!=0) || x==-1)
					{

					}
					else
					{
						x=-1;
						y=0;
						oldx--;
					}
				}
				if (((KeyEvent)event).getCode() == KeyCode.RIGHT )
				{
					if((x==-1 && oldx!=0) || x==1)
					{
					}
					else
					{
						x=1;
						y=0;
						oldx++;
					}
				}
				if (((KeyEvent)event).getCode() == KeyCode.DOWN )
				{
					if((y==-1 && oldy!=0) || y==1)
					{
					}
					else
					{
						y=1;
						x=0;
						oldy++;
					}
				}
				if (((KeyEvent)event).getCode() == KeyCode.UP )
				{
					if((y==1 && oldy!=0) || y==-1)
					{
					}
					else
					{
						y=-1;
						x=0;
						oldy--;
					}
				}
				if(((KeyEvent)event).getCode() == KeyCode.A)
				{
					System.out.println(g1.getX());
					System.out.println(g1.getWallCheck());
					System.out.println(g1.getCounter());
			System.out.println("Timer: "+timer);
		}
			}
		if (event instanceof MouseEvent)
		{
			System.out.println( ((MouseEvent)event).getX() );
			System.out.println( ((MouseEvent)event).getY() );
		}
	}
}