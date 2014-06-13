import java.util.ArrayList;


public class GoL 
{
	//Just holds the state of the cell.
	//the buf is there because creating a second 2d arraylist thingy is a pain
	public class Cell 
	{
		boolean alive;
		boolean buf;
	}
	
	//data members
	private ArrayList<ArrayList<Cell>> grid;		//"dynamic 2d array" that holds cell data, as well as buffer cell data
	private int width, height;						//width and height, self explanatory
	boolean wraps;									//whether cells "wrap around." if true, the cells at the edge will affect 
														//the ones at the other edge. Think of the spaceship in asteroids.
	
	
	
	//construction
	public GoL(int w, int h, boolean wrap) throws NegativeArraySizeException
	{
		if(w < 1 || h < 1)							
			throw new NegativeArraySizeException();		
				
		width = w;
		height = h;
		wraps = wrap;
		
		grid = new ArrayList<ArrayList<Cell>>(); 
		
		for(int x = 0; x < width; x++)
		{
			grid.add(new ArrayList<Cell>());
			for(int y = 0; y < width; y++)
			{
				grid.get(x).add(new Cell());
				grid.get(x).get(y).buf = false;
				grid.get(x).get(y).alive = false;
			}
		}
	}
	
	
	
	
	//tick
	public void tick()
	{
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{	
				int fc = getFriendCount(x, y);
				if(fc < 2 || fc > 3)
				{
					setCellBuf(x, y, false);
				}
				else if(getCell(x, y).alive == false && fc == 3)
				{
					setCellBuf(x, y, true); 
				}
				else 
				{
					setCellBuf(x,y, getCell(x, y).alive);
				}
				
			}
		}
		pushGridBuffer();
	}
	
	public int getFriendCount(int xloc, int yloc)
	{
		int friendCount = 0;
		for(int x = 0; x < 3; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				int xpos = xloc - 1 + x;
				int ypos = yloc - 1 + y;
				try
				{	
					Cell temp = getCell(xpos, ypos);
					if(temp.alive == true && !(x == 1 && y == 1))
					{
						friendCount ++;
					}
				}
				catch(ArrayIndexOutOfBoundsException iobe)
				{
					//System.out.println("oh noes!");
				}
			}
		}
		
		return friendCount ; //we subtract one to account for the cell it is being called for
	}
	
	public void pushGridBuffer()
	{
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{	
				grid.get(x).get(y).alive = grid.get(x).get(y).buf;
			}
		}
	}
	
	
	
	
	/*
	 * gets, gets, gets!
	 */
	
	//gets a cell
	public Cell getCell(int x, int y) throws ArrayIndexOutOfBoundsException
	{
		if(x >= width  || y >= height || x < 0 || y < 0)
		{
			if(wraps)
			{
				x %= width;
				y %= height;
				
				while(x < 0)
					x = width + x;
				while(y < 0)
					y = height + y;
				return grid.get(x).get(y);
			}
			else
			{
				throw new ArrayIndexOutOfBoundsException("There is no cell there!");
			}
		}
		
		return grid.get(x).get(y);
		
	}
	
	//access
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
	public boolean doesWrap()
	{
		return wraps;
	}
	
	
	
	/*
	 * sets, sets, sets!
	 */
	
	//sets a cell
	public void setCell(int x, int y, boolean val) throws ArrayIndexOutOfBoundsException
	{
		if(x >= width || x < 0 || y >= width || y < 0)
		{
			if(wraps)
			{
				x %= width;
				y %= height;
				
				while(x < 0)
					x = width + x;
				while(y < 0)
					y = height + y;

				grid.get(x).get(y).alive = val;
				return;
			}
			else
			{
				throw new ArrayIndexOutOfBoundsException("There is no cell there!");
			}
		}
		grid.get(x).get(y).alive = val;
	}

	//sets a cells buffer value
	public void setCellBuf(int x, int y, boolean val) throws ArrayIndexOutOfBoundsException
	{
		if(x >= width || x < 0 || y >= width || y < 0)
		{
			if(wraps)
			{
				x %= width;
				y %= height;
				
				while(x < 0)
					x = width + x;
				while(y < 0)
					y = height + y;

				grid.get(x).get(y).buf = val;
				return;
			}
			else
			{
				throw new ArrayIndexOutOfBoundsException("There is no cell there!");
			}
		}
		
		grid.get(x).get(y).buf = val;
	}
	
}
