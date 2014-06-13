
public class Main
{

	public static void main(String[] args)
	{
		GoLScreen gs = new GoLScreen(512, 512, 64, 64, true);
		Thread t = new Thread(gs);
		t.start();
	}

}
