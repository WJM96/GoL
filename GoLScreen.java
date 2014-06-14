import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

/*
 * GoLScreen shows the 
 * 
 */

public class GoLScreen extends JPanel implements MouseListener, KeyListener
{	
	/***********************************************************************/
	public static void main(String[] args)
	{
		GoLScreen gs = new GoLScreen(512, 512, 64, 64, true);
		gs.run();
	}
	/***********************************************************************/
	
	
	/***********************************************************************/
	/****  Member Variables  ***********************************************/
	/***********************************************************************/
	private	Color alive, dead;
	private GoL gol;
	private int cellWidth, cellHeight;
	private boolean paused;
	private boolean running;
	private boolean showHelp;
	private final int FRAME_DELAY = 32;		//roughly 30 fps
	
	
	
	/***********************************************************************/
	/****  Constructor  ****************************************************/
	/***********************************************************************/
	
	public GoLScreen(int w, int h, int golW, int golH, boolean wraps)
	{
		try
		{
			gol = new GoL(golW, golH, wraps);
		}
		catch(NegativeArraySizeException e)
		{
			e.printStackTrace();
			return;
		}
		gol.setCell(0, 0, true);
		
		paused = true;
		running = true;
		showHelp = false;
		
		this.setBounds(0, 0, w, h);
		
		cellWidth = this.getBounds().width / gol.getWidth();
		cellHeight = this.getBounds().height / gol.getHeight();
		
		alive = new Color(255, 255, 255);		//white
		dead = new Color(0, 0, 0);				//black
		
		JFrame jf = new JFrame("Game of Life (? for instructions)");
		jf.setBounds(0, 0, w, h);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setResizable(false);
		jf.add(this);
		jf.addKeyListener(this);
		this.addMouseListener(this); //thanks, java
		jf.setVisible(true);
	}
	
	
	
	/***********************************************************************/
	/****  Constructor  ****************************************************/
	/***********************************************************************/
		
	//draws the cells.
	@Override
	public void paint(Graphics g)
	{		
		for(int x = 0; x < gol.getWidth(); x++)
		{
			for(int y = 0; y < gol.getHeight(); y++)
			{
				try
				{
					if(gol.getCell(x, y).alive)
					{
						g.setColor(alive);
					}
					else
						g.setColor(dead);
					
					g.fillRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
				}
				catch(ArrayIndexOutOfBoundsException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		if(showHelp)
		{
			g.setColor(alive);
			g.drawString("Press Space to pause/unpause. ] to step through. Have fun!", 20, 20);
		}
		
	}
	
	//runs the simulation, graphics and all
	public void run() 
	{
		while(running)
		{
			this.repaint();
			
			if(!paused)
			{
				gol.tick();
			}
			
			try
			{
				Thread.sleep(FRAME_DELAY);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}


	/*
	 *Mouse handlers, mostly unused
	 */
	
	@Override
	public void mouseClicked(MouseEvent me)
	{
	}



	@Override
	public void mouseEntered(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}

	//allows the user to set a cell value by clicking.
	@Override
	public void mousePressed(MouseEvent me) 
	{
		int x = (me.getX() - this.getX())/ cellWidth, y = (me.getY() - this.getY()) / cellHeight;
		try
		{
			gol.setCell(x, y, !gol.getCell(x, y).alive);
		}
		catch(ArrayIndexOutOfBoundsException obe)
		{
			obe.printStackTrace();
		}	
	}


	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	/*
	 *Key handlers, mostly unused
	 */
	
	
	//if the user hits space, the game is (un)paused
	@Override
	public void keyPressed(KeyEvent k)
	{
		if(k.getKeyChar() == ' ')
		{
			paused = !paused;
		}
		if(k.getKeyChar() == '/')
		{
			showHelp = !showHelp;
		}
		if(k.getKeyChar() == ']')
		{
			gol.tick();
		}
		
	}



	@Override
	public void keyReleased(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}



	@Override
	public void keyTyped(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

}
