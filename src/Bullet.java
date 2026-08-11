public class Bullet {
	private int x;
	private int y;
	
	private int speedX;
	private int speedY;
	private char image;
	
	public Bullet(int x, int y, int dx, int dy,char image) {
		this.x = x;
		this.y = y;
		this.speedX = dx;
		this.speedY = dy;
		this.image = image;
	}
	
	public void update() {
		x += speedX;
		y += speedY;
	}
	
	public void paint(ConsoleView view) {
		update();
		view.put(image,x,y);
	}
	
	public boolean isOutOfScreen() {
		
		return(x < 0 || ConsoleView.WIDTH <= x || 
				y < 0 || ConsoleView.HEIGHT <= y);

	}

}
