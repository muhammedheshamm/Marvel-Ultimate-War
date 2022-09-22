package view;
import java.io.IOException;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameView extends JFrame{
	
	private Dimension screenSize;
	private int screenHeight;
	private int screenWidth;
	private JPanel main;
	private final int mainPanelHeight=800;
	private final int mainPanelWidth=1300;
	public GameView(){
		//just setting the background image and the frame size
		
		ImageIcon logo=new ImageIcon("download.png");
		this.setIconImage(logo.getImage());
		this.setTitle("MARVEL WAR");
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenHeight = screenSize.height;
		screenWidth = screenSize.width;	
		this.setPreferredSize(screenSize);
		this.setVisible(true);	
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		ImageIcon original = new ImageIcon("marvel.png");
		JLabel background = new JLabel(getScaledImage(original,screenWidth,screenHeight));
		this.setContentPane(background);
		this.setLayout(null); 
	    setExtendedState(JFrame.MAXIMIZED_BOTH);
	    ImageIcon soundON =new ImageIcon("soundOn.png");
	    //this for loop is to make the window full-screen
//	    for ( Window w : Window.getWindows() ) {
//	        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow( w );
//	    }
	    
	    //the main transparent panel which will include the whole thing 
	    main = new JPanel();
	    main.setBackground(new Color(0,0,0,180));
	    main.setBounds(screenWidth-(screenWidth/2)-670,screenHeight-(screenHeight/2)-460,mainPanelWidth+50,mainPanelHeight+50);
	    main.setLayout(null);
	    
	    add(main);
	    
	    
	    
		revalidate();
		repaint();
	}
	
	
	
	//method to just scale images (you don't have to check it out)
	public static ImageIcon getScaledImage(ImageIcon srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg.getImage(), 0, 0, w, h, null);
	    g2.dispose();

	    return new ImageIcon(resizedImg);
	}
	
	//method to make the color transparent
	public void paintComponent(Graphics g) {
	    
        g.setColor( getBackground() );
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponents(g);
	 }
	
	public Dimension getScreenSize() {
		return screenSize;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public int getScreenWidth() {
		return screenWidth;
	}
	
	public JPanel getMain() {
		return main;
	}

	public int getMainPanelHeight() {
		return mainPanelHeight;
	}


	public int getMainPanelWidth() {
		return mainPanelWidth;
	}
}
