package p012;

import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class Billiards extends JFrame {

	public static int Width = 800;
	public static int Height = 600;

	private JButton b_start, b_stop;

	private Board board;

	private final int N_BALL = 9;
	
	//Vector donde guardo las bolas
	Ball[] bolas = new Ball[N_BALL];
	
	//Vector donde guardo los hilos asociados a cada bola
	Thread[] hilos = new Thread[N_BALL];

	public Billiards() {

		board = new Board();
		board.setForeground(new Color(0, 128, 0));
		board.setBackground(new Color(0, 128, 0));

		initBalls();

		b_start = new JButton("Empezar");
		b_start.addActionListener(new StartListener());
		b_stop = new JButton("Parar");
		b_stop.addActionListener(new StopListener());

		JPanel p_Botton = new JPanel();
		p_Botton.setLayout(new FlowLayout());
		p_Botton.add(b_start);
		p_Botton.add(b_stop);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(board, BorderLayout.CENTER);
		getContentPane().add(p_Botton, BorderLayout.PAGE_END);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Width, Height);
		setLocationRelativeTo(null);
		setTitle("Práctica programación concurrente objetos móviles independientes");
		setResizable(false);
		setVisible(true);
	}

	
	private void initBalls() {
		//Creo las bolas
		for (int i=0;i<N_BALL;i++){
			bolas[i] = new Ball();
			//Las asigno al tablero
			board.setBalls(bolas);
		}
	}

	private class StartListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//Si hemos pulsado el boton parar y volvemos a pulsar empezar, las bolas siguen su camino
			//Si las bolas se han detenido, al pulsar empezar, la ejecución se reinicia
			boolean aux = true;
			for (int i=0;i<N_BALL;i++){
				if (bolas[i].getdr() == 0 && aux){
					aux = true;
				} else {
					aux = false;
				}
			}
			if(aux == true){
				initBalls();
			}
			
			//Por cada bola creo un nuevo hilo 
			for (int i=0;i<N_BALL;i++){
				//Recibo la bola y el tablero
				hilos[i]= new Thread(new MovBall(bolas[i],board));
				hilos[i].start();
			}
		}
	}

	private class StopListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			for (int i=0;i<N_BALL;i++){
				hilos[i].interrupt();
			}

		}
	}

	public static void main(String[] args) {
		new Billiards();
	}
}