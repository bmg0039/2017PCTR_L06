package p012;

public class MovBall extends Thread{
	private Ball ball;
	private final Board tablero;
	
	public MovBall(Ball ball,Board tablero) {
		this.ball = ball;
		this.tablero = tablero;
	}
	
	
	public void run() {
		try {
			while(true){
				//Muevo la bola
				this.ball.move();
				this.ball.reflect();
				
				//Repinto el tablero
				tablero.repaint();
				
				//Duermo el hilo
				Thread.sleep(10);
			}
		} catch (InterruptedException ex) {
			return;
		}

	}

}
