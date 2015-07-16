import javax.swing.*;

public class connect4 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		c4gui c4 = new c4gui();
		JFrame window = new JFrame("Connect 4");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setContentPane(c4.container);
		window.pack();
		window.setVisible(true);
	}

}
