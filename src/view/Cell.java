package view;

	import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

	import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

	public class Cell extends JButton implements ActionListener {
		public Cell() {
			this.setBackground(Color.WHITE); 
			this.addActionListener(this);
			this.setFocusable(false);
			this.setSize(getMaximumSize());
		}
//
//		public void addFighter() {
//			try {
//				Image img = ImageIO.read(getClass().getClassLoader().getResource(
//						"Captain America.png"));
//				this.setIcon((new ImageIcon(img)));
//				this.setFocusable(false); // removes the border around the image
//			} catch (IOException ex) {
//			}
//		}

		@Override
		public void actionPerformed(ActionEvent e) {
			//this.addFighter();
		}
	}

	