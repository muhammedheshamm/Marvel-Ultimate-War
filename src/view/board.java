package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class board extends JPanel implements ActionListener {
public	Cell[][] map = new Cell[5][5];

	public board() {
		setLayout(new GridLayout(5,5));
		for (int i = 4; i >-1; i--) {
			for (int j = 0; j < 5; j++) {
				map[i][j] = new Cell();
				add(map[i][j]); // adds cell to the world panel 		
				map[i][j].addActionListener(this); 
				
			}
		}
		//map[4][0].addFighter();
		//map[0][1].addFighter();
	}

	public void clearAll() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++)
				map[i][j].setIcon(null);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//clearAll(); //remove all fighters on grid
	}


}