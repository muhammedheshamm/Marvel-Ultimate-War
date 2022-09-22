package controller;

import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import view.GameSound;
import view.GameView;
import view.board;
import engine.Game;
import engine.GameListener;
import engine.Player;
import engine.PriorityQueue;
import exceptions.AbilityUseException;
import exceptions.ChampionDisarmedException;
import exceptions.InvalidTargetException;
import exceptions.LeaderAbilityAlreadyUsedException;
import exceptions.LeaderNotCurrentException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.world.Champion;
import model.world.Condition;
import model.world.Cover;
import model.world.Direction;

//important note : if you ran this program you will probably stuck in it , so to close it just press alt + f4

public class Controller implements ActionListener ,MouseListener,GameListener{
	
	private GameView thebigmainview=new GameView();
	private JTextArea name1;
	private JTextArea name2;
	private JButton submit;
	private Game game;
	private JButton champ;
	private JLabel firstName;
	private JLabel secondName;
	private ArrayList<JButton> championsButtons=new ArrayList<JButton>();
	private ArrayList<String> champinformations;
	private JTextArea info;
	private JButton tothegame;
	private board theboard=new board();
	private JPanel optionbuttons;
	private JPanel whoisplaying; 
	private  JButton moveup;
	private JButton movedown;
	private JButton moveright;
	private JButton moveleft;
	private JButton ability1;
	private JButton ability2;
	private JButton ability3;
	private JButton lability;
	private JButton attup;
	private JButton attdown;
	private JButton attright;
	private JButton attleft;
	private JButton ifextraability;
	private JButton endmyturn;
	private JTextArea rightcurrentinfo;
	private JTextArea topleftcurrentinfo;
	private JTextArea toprightcurrentinfo;
	private JTextArea areaofexceptions;
	private JTextArea xarea;
    private JTextArea yarea; 
    private JButton submitxy =new JButton("submit x&y");
	private JPanel directionpanel;
	private JButton updirection;
	private JButton downdirection;
	private JButton leftdirection;
	private JButton rightdirection;
	 private GameSound sound = new GameSound() ;
	private  JLabel champnow;
	private  JLabel champnext;
	private  JOptionPane error = new JOptionPane();
// different costructor for different views
//the first constructor
public Controller() {
	 // sound.PlaySound("game sound.wav");
		//creating the label and text field to the first player
	  name1 = new JTextArea();
		name1.setText("Player 1");
		name1.setFont(new Font("Courier", Font.PLAIN, 25));
		//these bounds are just to set the content in the center
		name1.setBounds(thebigmainview.getMainPanelWidth()-(thebigmainview.getMainPanelWidth()/2)-140 ,150, 280 , 50);
		JLabel p1 = new JLabel("Enter player 1's name");
		p1.setBounds(thebigmainview.getMainPanelWidth()-(thebigmainview.getMainPanelWidth()/2)-140, 100, 280 , 50);
		p1.setFont(new Font("Courier", Font.PLAIN,20));
		p1.setForeground(Color.white);
		

		//the same for the second player
		name2 = new JTextArea();
		name2.setText("Player 2");
		name2.setFont(new Font("Courier", Font.PLAIN, 25));
		name2.setBounds(thebigmainview.getMainPanelWidth()-(thebigmainview.getMainPanelWidth()/2)-140 ,300, 280 , 50);
		JLabel p2 = new JLabel("Enter player 1's name");
		p2.setBounds(thebigmainview.getMainPanelWidth()-(thebigmainview.getMainPanelWidth()/2)-140, 250, 280 , 50);
		p2.setFont(new Font("Courier", Font.PLAIN,20));
		p2.setForeground(Color.white);
		
		//creating the submit button
		submit= new JButton("SUBMIT");
		submit.setBounds(thebigmainview.getMainPanelWidth()-(thebigmainview.getMainPanelWidth()/2)-100,500,200,80);
		submit.setBackground(new Color(175,31,36));
		submit.setText("ENTER");
		submit.setFocusable(false);
		submit.setFont(new Font("Courier", Font.PLAIN, 25));
		submit.setForeground(Color.white);
		submit.addActionListener(this);
		
		
		//adding components to the main panel
	    thebigmainview.getMain().add(name1);
	    thebigmainview.getMain().add(p1);
	    thebigmainview.getMain().add(name2);
	    thebigmainview.getMain().add(p2);
	    thebigmainview.getMain().add(submit);
	    
	    
	    
	    thebigmainview.revalidate();
		thebigmainview.repaint();
	}
//the second constructor
public Controller(String first,String second) {
	game=new Game(new Player(first),new Player(second));
	 thebigmainview.getMain().setBounds(thebigmainview.getScreenWidth()-(thebigmainview.getScreenWidth()/2)-490,thebigmainview.getHeight()-(thebigmainview.getHeight()/2)-400,thebigmainview.getWidth()-600,thebigmainview.getHeight()-300);	
	game.setListener(this);
		//creating the header of the screen
		JLabel label=new JLabel("Choose your teams!\n [first player you pick is the leader]");
		label.setFont(new Font("Arial Black", Font.PLAIN, 15));
		label.setForeground(Color.white);
		label.setBounds(1300-650-400,20,700,90);
		thebigmainview.getMain().add(label);
		
		//creating the panel which will carry then the available champions
		JPanel champs =new JPanel();
		champs.setBounds(1300-650-500,100,800,420);
		champs.setLayout(new GridLayout(3, 5));
		thebigmainview.getMain().add(champs);
		
		//loading abilities and champions
		try {Game.loadAbilities("Abilities.csv");
			Game.loadChampions("Champions.csv");
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		champinformations=new ArrayList<String>();
		Champion c;
		//adding the Champions to the panel
		for(int i=1;i<=Game.getAvailableChampions().size();i++) {
		    String x= i+".png";
		    ImageIcon select = thebigmainview.getScaledImage( new ImageIcon(x),170,150);
			c=Game.getAvailableChampions().get(i-1);
			 champ =new JButton(select);
			champ.setBackground(Color.white);
			champ.setActionCommand(c.getName());
			champ.addActionListener(this);
			champ.setFocusable(false);
			champ.addMouseListener(this);
			champs.add(champ);
			championsButtons.add(champ);
			champinformations.add(c.toString());
		}
		
		//creating the two below labels that will contain the chosen champions later
		firstName=new JLabel();
		firstName.setText(first+"'s Team:");
		firstName.setBackground(Color.black);
		firstName.setFont(new Font("Courier", Font.BOLD, 25));
		firstName.setForeground(Color.white);
		firstName.setBounds(1300-550-700,550,1000,40);
		thebigmainview.getMain().add(firstName);
		
		
		secondName=new JLabel(second+"'s Team:");
		secondName.setFont(new Font("Courier", Font.BOLD, 25));
		secondName.setBackground(Color.black);
		secondName.setForeground(Color.white);
		secondName.setBounds(1300-550-700,620,1200,40);
		thebigmainview.getMain().add(secondName);
		
		
		//information text area to tell us about the info of each player before choosing it
		info=new JTextArea();
		info.setBackground(Color.white);
		info.setBounds(50,150,400,600);
		info.setFont(new Font("Arial Black", Font.ITALIC,12));
		info.setText(game.getFirstPlayer().getName()+" choose your team");
		thebigmainview.add(info);
		info.setVisible(true);
		info.setEditable(false);
		//send us to the battle view
		tothegame=new JButton();
		tothegame.setBackground(new Color(175, 31, 36));
		tothegame .setBounds(150,800, 200, 50);
		tothegame.setFocusable(false);
		tothegame.setText("To The Battle!");
		tothegame.setFont(new Font("Courier", Font.PLAIN, 15));
		tothegame.setForeground(Color.white);
		thebigmainview.add(tothegame);
		tothegame.addActionListener(this);
		
		
		thebigmainview.getMain().revalidate();
		thebigmainview.getMain().repaint();
	}
//the third constructor
public Controller(Game g) {
	//creating the panel which will be our board
	 thebigmainview.getMain().setBounds(thebigmainview.getScreenWidth()-(thebigmainview.getScreenWidth()/2)-490,thebigmainview.getHeight()-(thebigmainview.getHeight()/2)-400,thebigmainview.getWidth()-600,thebigmainview.getHeight()-210);	
	 theboard.setBounds(0,167,1031,665);
			thebigmainview.getMain().add(theboard);
			g.placeChampions();
			g.prepareChampionTurns();
			//very important method set the whole board
			setelementsontheboard(g );
		    // 9 buttons on the left in the optionbuttons pannel
			optionbuttons =new JPanel();
			optionbuttons.setBackground(Color.white);
			optionbuttons.setBounds(192,298,280,650);
			optionbuttons.setLayout(new GridLayout(8,0));
			moveup=new JButton("Move up");
			movedown=new JButton("Move down");
			moveright=new JButton("Move right");
			moveleft=new JButton("Move left");
			moveup.addActionListener(this);
			movedown.addActionListener(this);
			moveleft.addActionListener(this);
			moveright.addActionListener(this);
			moveup.setFocusable(false);
			movedown.setFocusable(false);
			moveright.setFocusable(false);
			moveleft.setFocusable(false);
			ability1=new JButton("use 1st Ability");
			ability2=new JButton("use 2nd Ability");
			ability3=new JButton("use 3rd Ability");
			lability=new JButton("use Leader Ability");
			lability.addActionListener(this);
			ability1.addActionListener(this);
			ability2.addActionListener(this);
			ability3.addActionListener(this);
			lability.setFocusable(false);
			ability1.setFocusable(false);
			ability2.setFocusable(false);
			ability3.setFocusable(false);
			attup=new JButton("Attack up");
			attdown=new JButton("Attack down");
			attright=new JButton("Attack right");
			attleft=new JButton("Attack left");
			attleft.addActionListener(this);
			attup.addActionListener(this);
			attdown.addActionListener(this);
			attright.addActionListener(this);
			attup.setFocusable(false);
			attdown.setFocusable(false);
			attright.setFocusable(false);
			attleft.setFocusable(false);
			ifextraability=new JButton("Extra Ability[punch]");
			ifextraability.addActionListener(this);
			ifextraability.setFocusable(false);
			endmyturn=new JButton("End Turn");
			endmyturn.addActionListener(this);
			optionbuttons.add(moveup);
			optionbuttons.add(movedown);
			optionbuttons.add(moveright);
			optionbuttons.add(moveleft);
			//optionbuttons.add(ifextraability);
			optionbuttons.add(attup);
			optionbuttons.add(attdown);
			optionbuttons.add(attright);
			optionbuttons.add(attleft);
			thebigmainview.add(optionbuttons);
			// 4 buttons on the top
			ability1.setBounds(0, 135, 168, 30);
			ability1.setBackground(new Color(175,31,36));
			thebigmainview.getMain().add(ability1);
			ability2.setBounds(168, 135, 168, 30);
			ability2.setBackground(new Color(175,31,36));
			thebigmainview.getMain().add(ability2);
			ability3.setBounds(336, 135, 168, 30);
			ability3.setBackground(new Color(175,31,36));
			thebigmainview.getMain().add(ability3);
			lability.setBounds(672, 135, 168, 30);
			lability.setBackground(new Color(175,31,36));
			thebigmainview.getMain().add(lability);
			endmyturn.setBounds(837, 135, 194, 30);
			endmyturn.setBackground(Color.YELLOW);
			thebigmainview.getMain().add(endmyturn);
			ability1.addActionListener(this);
			ability2.addActionListener(this);
			ability3.addActionListener(this);
			lability.addActionListener(this);
			ifextraability.setBounds(504, 135, 168, 30);
			endmyturn.setFocusable(false);
			ifextraability.setBackground(new Color(175,31,36));
			thebigmainview.getMain().add(ifextraability);
			// text area to inform us about the playerturns
			PriorityQueue p = new PriorityQueue(6);
	        for (Champion c : game.getFirstPlayer().getTeam())
				if(game.getCurrentChampion()!=c)
				   p.insert(c);
			for (Champion c : game.getSecondPlayer().getTeam())
				if(game.getCurrentChampion()!=c)
				    p.insert(c);
			ImageIcon PlayingNowimage = this.thebigmainview.getScaledImage( new ImageIcon((game.getCurrentChampion().getName())+".png"),140,180);
		    ImageIcon PlayingNextimage = this.thebigmainview.getScaledImage( new ImageIcon(((Champion)p.remove()).getName()+".png"),140,180);
			Border borderPlayingNow=BorderFactory.createLineBorder(Color.YELLOW,3);
			Border borderPlayingNext=BorderFactory.createLineBorder(Color.WHITE,3);
			whoisplaying=new JPanel(new GridLayout(0,2));
			whoisplaying.setBackground(new Color(153, 0, 76));
			whoisplaying.setBounds(190,118,280,180);
		    champnow= new JLabel();
			champnow.setIcon(PlayingNowimage);
			champnow.setBackground(Color.WHITE);
			champnow.setOpaque(true);
			champnow.setBorder(borderPlayingNow);
			champnow.setSize(65,65);
			champnext= new JLabel();
			champnext.setIcon(PlayingNextimage);
			champnext.setBackground(Color.WHITE);
			champnext.setOpaque(true);
			champnext.setBorder(borderPlayingNext);
			champnext.setSize(65,65);
			whoisplaying.add(champnow);
			whoisplaying.add(champnext);
			whoisplaying.setFont(new Font("Courier", Font.BOLD, 13));
			whoisplaying.setForeground(Color.black);
			setareaonleft();
			// text area to tell us about the info of every player on the board
		    rightcurrentinfo=new JTextArea();
			rightcurrentinfo.setBackground(new Color(153, 0, 76));
			rightcurrentinfo.setFont(new Font("Courier", Font.ITALIC,11));
			rightcurrentinfo.setBounds(1033, 0, 305, 827);
			rightcurrentinfo.setForeground(Color.white);
			rightcurrentinfo.setText("current champion playin info "+"\n"+game.getCurrentChampion());
			rightcurrentinfo.setVisible(true);
			thebigmainview.getMain().add(rightcurrentinfo);
			// two text area to show us every team remaining champions and leader and the player name
	        topleftcurrentinfo=new JTextArea();
		    topleftcurrentinfo.setBackground(new Color(0, 204, 204));
		    topleftcurrentinfo.setFont(new Font("Courier", Font.ITALIC, 17));
			topleftcurrentinfo.setBounds(0, 0, 600, 133);
		    topleftcurrentinfo.setForeground(Color.white);
	        toprightcurrentinfo=new JTextArea();
	        toprightcurrentinfo.setBackground(new Color(0, 204, 204));
		    toprightcurrentinfo.setFont(new Font("Courier", Font.ITALIC, 17));
		    toprightcurrentinfo.setBounds(600,0, 430, 133);
		    toprightcurrentinfo.setForeground(Color.white);
		    setthetwoaraeontop();
			   
//		      areaofexceptions=new JTextArea();
//			   areaofexceptions.setBackground(new Color(0, 204, 204));
//			   areaofexceptions.setFont(new Font("Courier", Font.ITALIC, 12));
//			   areaofexceptions.setBounds(0,620,700,65);
//			   areaofexceptions.setForeground(Color.white);
//			   thebigmainview.getMain().add(areaofexceptions);
			  // during singletarget ability 
			   xarea=new JTextArea();
			   xarea.setBackground(Color.white);
			   xarea.setFont(new Font("Courier", Font.ITALIC, 12));
			   xarea.setBounds(1100,50,50,30);
			   xarea.setText("x");
			   xarea.setFocusable(false);
			   xarea.setVisible(false);
			   thebigmainview.getMain().add(xarea);
			   yarea=new JTextArea();
			   yarea.setBackground(Color.white);
			   yarea.setFocusable(false);
			   yarea.setFont(new Font("Courier", Font.ITALIC, 12));
			   yarea.setBounds(1210,50,50,30);
			   yarea.setText("y");
			   yarea.setVisible(false);
			   thebigmainview.getMain().add(yarea);
			   submitxy.setBackground(Color.white);
			   submitxy.setBounds(1140, 90, 95, 40);
			   submitxy.setFont(new Font("Courier", Font.ITALIC, 10));
			   submitxy.addActionListener(this);
			   submitxy.setVisible(false);
			   submitxy.setFocusable(false);
			   thebigmainview.getMain().add(submitxy);
			   //during directional ability
			   directionpanel=new JPanel();
			   directionpanel.setLayout(new GridLayout(4,0));
			   directionpanel.setBounds(1085, 90, 150, 200);
			   updirection=new JButton("UP");
			   updirection.setFocusCycleRoot(false);
			   updirection.addActionListener(this);
			   directionpanel.add(updirection);
			   downdirection=new JButton("DOWN");
			   downdirection.setFocusable(false);
			   downdirection.addActionListener(this);
			   directionpanel.add(downdirection);
			   rightdirection=new JButton("RIGHT");
			   rightdirection.setFocusable(false);
			   rightdirection.addActionListener(this);
			   directionpanel.add(rightdirection);
			   leftdirection=new JButton("LEFT");
			   leftdirection.addActionListener(this);
			   leftdirection.setFocusable(false);
			   directionpanel.add(leftdirection);
			   directionpanel.setVisible(false);
			   thebigmainview.getMain().add(directionpanel);
			   
			
			   
			   
			   
			   
			   
		    thebigmainview.revalidate();
			thebigmainview.repaint();
	        game=g;
}
//the last constructor
public Controller(String a) {
		thebigmainview.setTitle("GAME OVER");
	    JLabel finallabel=new JLabel(a);
		finallabel.setForeground(Color.white);;
		finallabel.setBounds(130, 250, 860, 200);
		finallabel.setFont(new Font("Courier", Font.ITALIC, 30));
		
		
		thebigmainview.getMain().add(finallabel);
		thebigmainview.revalidate();
		thebigmainview.repaint();
	}

public  void setelementsontheboard(Game g ) {
	Cover tempcover=null;
	Champion tempchampion = null;
	String res = null;
    ImageIcon coverimage = this.thebigmainview.getScaledImage( new ImageIcon("cover.jpg"),205,150);
    ImageIcon champimage; 
	for (int i = 4; i >-1; i--) {
			for (int j = 0; j < 5; j++) {
				if (g.getBoard()[i][j] instanceof Cover)
				{tempcover=(Cover)g.getBoard()[i][j];
				theboard.map[i][j].setIcon(coverimage);
				theboard.map[i][j].setHorizontalTextPosition(JButton.CENTER);
				theboard.map[i][j].setVerticalTextPosition(JButton.CENTER);
				theboard.map[i][j].setText("curr hp: "+tempcover.getCurrentHP());
				theboard.map[i][j].setFont(new Font("Arial Black", Font.BOLD, 15));
				theboard.map[i][j].setActionCommand("cover");
				}
				else {
					if (g.getBoard()[i][j] instanceof Champion) {
					tempchampion=(Champion)g.getBoard()[i][j];
                 res=tempchampion.getName()+ ".png";
		           champimage= this.thebigmainview.getScaledImage(new ImageIcon(res),180,80);  
                   theboard.map[i][j].setIcon(champimage);
				  // theboard.map[i][j].setText( tempchampion.getName());
				   theboard.map[i][j].setHorizontalTextPosition(JButton.CENTER);
					theboard.map[i][j].setVerticalTextPosition(JButton.CENTER);
					theboard.map[i][j].setText(""+tempchampion.getCurrentHP());
					theboard.map[i][j].setForeground(Color.yellow);
					theboard.map[i][j].setFont(new Font("Arial Black", Font.BOLD, 13));
					theboard.map[i][j].setActionCommand(tempchampion.getName());
					theboard.map[i][j].addMouseListener(this);
			}
				else {
					theboard.map[i][j].setActionCommand("null");
					theboard.map[i][j].addMouseListener(null);
				theboard.map[i][j].setIcon(null);
				theboard.map[i][j].setText(null);
			}
				}
				theboard.map[i][j].addActionListener(this);
			}
			}

	 thebigmainview.revalidate();
	 thebigmainview.repaint();
	 game=g;  
}
public void actionPerformed(ActionEvent e) {
	if(e.getSource() == submit && !name1.getText().equals("") && !name2.getText().equals("")) 
			{thebigmainview.dispose(); //this just to remove the components of the first screen
			new Controller(name1.getText() ,name2.getText()); 
			return;}
			//calling the second cons.
			//thebigmainview.dispose(); //this just to remove the components of the first screen
			
		if(e.getSource()==tothegame &&game.getSecondPlayer().getTeam().size()==3)
		{  thebigmainview.dispose();
			new Controller(game);
			return;
			}
		if(e.getSource()==tothegame &&game.getSecondPlayer().getTeam().size()!=3)
		{  info.setText("Please Choose 3 champions for each team");
			info.setVisible(true);
			thebigmainview.revalidate();
			thebigmainview.repaint();
		return;
			} 
        for(JButton b : championsButtons){
        	if(e.getSource()==b&&(game.getFirstPlayer().getTeam().size()+game.getSecondPlayer().getTeam().size())<6){
        		b.setFocusable(false);
        		b.setEnabled(false);
        	}}
		ArrayList<Champion>tempchamp =(ArrayList<Champion>)Game.getAvailableChampions().clone();
		for(Champion c :tempchamp) {
			if(e.getActionCommand()==c.getName()&&game.getFirstPlayer().getTeam().size()<3) {
				firstName.setText(firstName.getText()+"  " + c.getName());
				game.getFirstPlayer().getTeam().add(c);
				if(game.getFirstPlayer().getTeam().size()==1)
					game.getFirstPlayer().setLeader(c);
				Game.getAvailableChampions().remove(c);
				  thebigmainview.revalidate();
					thebigmainview.repaint();
					return;
			}
			
			else if(e.getActionCommand()==c.getName()&&game.getSecondPlayer().getTeam().size()<3 &&game.getFirstPlayer().getTeam().size()==3) {
				secondName.setText(secondName.getText()+"  " + c.getName());
				game.getSecondPlayer().getTeam().add(c);
				if(game.getSecondPlayer().getTeam().size()==1)
					game.getSecondPlayer().setLeader(c);
				Game.getAvailableChampions().remove(c);
				  thebigmainview.revalidate();
				  thebigmainview.repaint();
				  return;
			}
		}
		
		if (e.getSource()==ifextraability)
		{
			if(game.getCurrentChampion().getAbilities().size()==4) 
			{
				xarea.setVisible(true);
				yarea.setVisible(true);
				submitxy.setVisible(true);
				rightcurrentinfo.setVisible(false);
				submitxy.setActionCommand(game.getCurrentChampion().getAbilities().get(3).getName());
				thebigmainview.revalidate();
				thebigmainview.repaint();
			       return;	
			}
			else
			 JOptionPane.showMessageDialog(null,"this champion does not have this ability", "Error occurred", JOptionPane.ERROR_MESSAGE);	
		}
		if(e.getSource()==ability1) {
			 if(game.getCurrentChampion().getAbilities().get(0).getCastArea()==AreaOfEffect.SINGLETARGET) {
				xarea.setVisible(true);
				yarea.setVisible(true);
				submitxy.setVisible(true);
				rightcurrentinfo.setVisible(false);
				submitxy.setActionCommand(game.getCurrentChampion().getAbilities().get(0).getName());
				thebigmainview.revalidate();
				thebigmainview.repaint();
			       return;	
			 }
				
			else {     
			if(game.getCurrentChampion().getAbilities().get(0).getCastArea()==AreaOfEffect.DIRECTIONAL)   
			{ directionpanel.setVisible(true);
			rightcurrentinfo.setVisible(false);
			updirection.setActionCommand(game.getCurrentChampion().getAbilities().get(0).getName());
			downdirection.setActionCommand(game.getCurrentChampion().getAbilities().get(0).getName());
			rightdirection.setActionCommand(game.getCurrentChampion().getAbilities().get(0).getName());
			leftdirection.setActionCommand(game.getCurrentChampion().getAbilities().get(0).getName());
			thebigmainview.revalidate();
			thebigmainview.repaint();
		     return;	
			
			}                                                                     
		     else
		     { onCastAbility(game.getCurrentChampion().getAbilities().get(0));
		     return;
		     }
			}
	}
	

		if(e.getSource()==ability2) {
			 if(game.getCurrentChampion().getAbilities().get(1).getCastArea()==AreaOfEffect.SINGLETARGET) {
				xarea.setVisible(true);
				yarea.setVisible(true);
				submitxy.setVisible(true);
				rightcurrentinfo.setVisible(false);
				submitxy.setActionCommand(game.getCurrentChampion().getAbilities().get(1).getName());
				thebigmainview.revalidate();
				thebigmainview.repaint();
				return;
				}
				
			else {     
			if(game.getCurrentChampion().getAbilities().get(1).getCastArea()==AreaOfEffect.DIRECTIONAL)   
			{directionpanel.setVisible(true);
			rightcurrentinfo.setVisible(false);
			updirection.setActionCommand(game.getCurrentChampion().getAbilities().get(1).getName());
			downdirection.setActionCommand(game.getCurrentChampion().getAbilities().get(1).getName());
			rightdirection.setActionCommand(game.getCurrentChampion().getAbilities().get(1).getName());
			leftdirection.setActionCommand(game.getCurrentChampion().getAbilities().get(1).getName());
			thebigmainview.revalidate();
			thebigmainview.repaint();
				return;
			}                                                                     
		     else
		     { onCastAbility(game.getCurrentChampion().getAbilities().get(1));
		     return;}
			}
	}
		

		if(e.getSource()==ability3) {
			 if(game.getCurrentChampion().getAbilities().get(2).getCastArea()==AreaOfEffect.SINGLETARGET) {
				xarea.setVisible(true);
				yarea.setVisible(true);
				submitxy.setVisible(true);
				rightcurrentinfo.setVisible(false);
				submitxy.setActionCommand(game.getCurrentChampion().getAbilities().get(2).getName());
				thebigmainview.revalidate();
				thebigmainview.repaint();
				return;}
				
			else {     
			if(game.getCurrentChampion().getAbilities().get(2).getCastArea()==AreaOfEffect.DIRECTIONAL)   
			{directionpanel.setVisible(true);
			rightcurrentinfo.setVisible(false);
			updirection.setActionCommand(game.getCurrentChampion().getAbilities().get(2).getName());
			downdirection.setActionCommand(game.getCurrentChampion().getAbilities().get(2).getName());
			rightdirection.setActionCommand(game.getCurrentChampion().getAbilities().get(2).getName());
			leftdirection.setActionCommand(game.getCurrentChampion().getAbilities().get(2).getName());
			thebigmainview.revalidate();
			thebigmainview.repaint();
				return;
			}                                                                     
		     else
		     {  onCastAbility(game.getCurrentChampion().getAbilities().get(2));
              return;		     
		     }
			}
	}	         if(e.getSource()==endmyturn)
		            { 
		             onEndTurn();
		             return;}
				    if(e.getSource()==attup)
					 {onAttack(Direction.UP);
				    return;}
				    if(e.getSource()==attdown)
					{onAttack(Direction.DOWN);
				    return;}
				    if(e.getSource()==attleft)
					{onAttack(Direction.LEFT);
				    return;}
				    if(e.getSource()==attright)
					{onAttack(Direction.RIGHT);
				    return;}
				    if(e.getSource()==moveup)
					 {onMove(Direction.UP);
				    return;}
				    if(e.getSource()==movedown)
					{onMove(Direction.DOWN);
				    return;}
				    if(e.getSource()==moveleft)
				    {	onMove(Direction.LEFT);
				    return;}
				    if(e.getSource()==moveright)
					{onMove(Direction.RIGHT);
				    return;}
				    if(e.getSource()==lability)
					{onUseLeaderAbility();
				    return;}
				    
				    
				if(!game.getTurnOrder().isEmpty() ) {
						for(Ability a:game.getCurrentChampion().getAbilities())	{	
				    if(e.getSource()==updirection&& e.getActionCommand()==a.getName())
					{onCastAbility(a, Direction.UP);
					 downdirection.setActionCommand("null"); 
					   updirection.setActionCommand("null"); 
					   rightdirection.setActionCommand("null"); 
					   leftdirection.setActionCommand("null"); 
					   directionpanel.setVisible(false);
					    thebigmainview.revalidate();
					      thebigmainview.repaint();
				    return;}
				    if(e.getSource()==downdirection&& e.getActionCommand()==a.getName())
					{onCastAbility(a, Direction.DOWN);
					 downdirection.setActionCommand("null"); 
					   updirection.setActionCommand("null"); 
					   rightdirection.setActionCommand("null"); 
					   leftdirection.setActionCommand("null"); 
					   directionpanel.setVisible(false);
					    thebigmainview.revalidate();
					      thebigmainview.repaint();
				    return;}
				    if(e.getSource()==leftdirection&& e.getActionCommand()==a.getName())
					{onCastAbility(a, Direction.LEFT);
					 downdirection.setActionCommand("null"); 
					   updirection.setActionCommand("null"); 
					   rightdirection.setActionCommand("null"); 
					   leftdirection.setActionCommand("null"); 
					   directionpanel.setVisible(false);
					    thebigmainview.revalidate();
					      thebigmainview.repaint();
				    return;}
				    if(e.getSource()==rightdirection&& e.getActionCommand()==a.getName())
					{onCastAbility(a, Direction.RIGHT);
					 downdirection.setActionCommand("null"); 
					   updirection.setActionCommand("null"); 
					   rightdirection.setActionCommand("null"); 
					   leftdirection.setActionCommand("null"); 
					   directionpanel.setVisible(false);
					    thebigmainview.revalidate();
					      thebigmainview.repaint();
				    return;}
						}
				    }
				    
				    
				    
				  
				    
				    
				    if(!game.getTurnOrder().isEmpty() ) {
						for(Ability a:game.getCurrentChampion().getAbilities())		
						if(e.getSource()==submitxy && e.getActionCommand()==a.getName())
						  {onCastAbility(a, Integer.parseInt(xarea.getText()), Integer.parseInt(yarea.getText()));
					      submitxy.setActionCommand("null"); 
						  submitxy.setVisible(false);
					       xarea.setVisible(false);
					       yarea.setVisible(false);
					       thebigmainview.revalidate();
					       thebigmainview.repaint();
						  }
				       }
					
		}
public void mouseClicked(java.awt.event.MouseEvent e) {}
public void mousePressed(java.awt.event.MouseEvent e) {}
public void mouseReleased(java.awt.event.MouseEvent e) {}
public void mouseEntered(java.awt.event.MouseEvent e) {
		
	for(int i=0;i<championsButtons.size();i++) {
			if(e.getComponent()==championsButtons.get(i) )	
				{info.setText(champinformations.get(i));
				info.setVisible(true);
				}
	}
	
 for(Champion c:game.getFirstPlayer().getTeam()) {
	 for(int i=4;i>-1;i--)
		for(int j=0;j<5;j++)
			if(e.getComponent()==theboard.map[i][j] &&( (JButton)(theboard.map[i][j])).getActionCommand()==c.getName()) {
	             rightcurrentinfo.setText("Choosen player current info :\n"+c.toString());
	             rightcurrentinfo.setVisible(true);
     thebigmainview.revalidate();
    thebigmainview.repaint();
			}}
	for(Champion c:game.getSecondPlayer().getTeam()) {
		 for(int i=4;i>-1;i--)
			for(int j=0;j<5;j++)
				if(e.getComponent()==theboard.map[i][j] &&( (JButton)(theboard.map[i][j])).getActionCommand()==c.getName())
				{ rightcurrentinfo.setText("Choosen player current info :\n"+c.toString());
			      rightcurrentinfo.setVisible(true);
			      thebigmainview.revalidate();
			      thebigmainview.repaint();
			      }
		          
	}
	}
public void mouseExited(java.awt.event.MouseEvent e) {

		for(int i=0;i<championsButtons.size();i++) {
			if(e.getComponent()==championsButtons.get(i) )	
				{info.setVisible(false);
				thebigmainview.revalidate();
			      thebigmainview.repaint();}}
		 for(int i=4;i>-1;i--)
				for(int j=0;j<5;j++)
					if(e.getComponent()==theboard.map[i][j])
					{rightcurrentinfo.setText("current champion playing info :\n"+game.getCurrentChampion().toString());
				      thebigmainview.revalidate();
				      thebigmainview.repaint();}}
       
	
public void setareaonleft() {
		PriorityQueue temp=new PriorityQueue(6);
		String res="TURN ORDER \n ";
		while(!game.getTurnOrder().isEmpty())	
		{  if ( ((Champion)(game.getTurnOrder().peekMin())).getCondition()!=Condition.INACTIVE )
			res+=((Champion)(game.getTurnOrder().peekMin())).getName()+"\n";
		  temp.insert(game.getTurnOrder().remove());}
		while(!temp.isEmpty())
		  game.getTurnOrder().insert(temp.remove());
		
	//	whoisplaying.setText(res);
		thebigmainview.add(whoisplaying);
	//	whoisplaying.setEditable(false);
		thebigmainview.getMain().revalidate();
		thebigmainview.getMain().repaint();
		
	}

public void setthetwoaraeontop() {
		 String f=game.getFirstPlayer().getName() +" Team is: ";
		   String s=game.getSecondPlayer().getName() +" Team is: ";
		for (int i=0;i<game.getFirstPlayer().getTeam().size();i++)
	      f=f+" "+game.getFirstPlayer().getTeam().get(i).getName();
		for (int i=0;i<game.getSecondPlayer().getTeam().size();i++)
	       s=s+" "+game.getSecondPlayer().getTeam().get(i).getName();
	      
		if(game.isFirstLeaderAbilityUsed())
			f=f+"\n The Leader Ability is used";
		if(!game.isFirstLeaderAbilityUsed())
			f=f+"\n The Leader Ability is not used yet";
		
		if(game.isSecondLeaderAbilityUsed())
			s=s+"\n The Leader Ability is used";
		if(!game.isSecondLeaderAbilityUsed())
			s=s+"\n The Leader Ability is not used yet";
		
		f=f+"\n The Leader is : "+game.getFirstPlayer().getLeader().getName();
		s=s+"\n The Leader is : "+game.getSecondPlayer().getLeader().getName();
		if(game.getFirstPlayer().getTeam().contains(game.getCurrentChampion()))
			f=f+"\n PLAYING ["+game.getCurrentChampion().getName()+"]";
		else
			s=s+"\n PLAYING [" +game.getCurrentChampion().getName()+"]";
		
		
		
		topleftcurrentinfo.setText(f);
		 thebigmainview.getMain().add(topleftcurrentinfo);
		 toprightcurrentinfo.setEditable(false);
		 toprightcurrentinfo.setText(s);
		 topleftcurrentinfo.setEditable(false);
		  thebigmainview.getMain().add(toprightcurrentinfo);

			thebigmainview.getMain().revalidate();
			thebigmainview.getMain().repaint();
	}
public void setareaonright(){
		rightcurrentinfo.setText("Current player current info :\n"+game.getCurrentChampion().toString());
        rightcurrentinfo.setVisible(true);
        thebigmainview.revalidate();
        thebigmainview.repaint();
	}
	
public void onMove(Direction d) {
		try {
			game.move(d);
		} catch (NotEnoughResourcesException c) {
			JOptionPane.showMessageDialog(null,c.getMessage(), "Error occurred", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		catch (UnallowedMovementException e) {
			JOptionPane.showMessageDialog(null,e.getMessage(), "Error occurred", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		setelementsontheboard(game);
		setareaonleft();
		setthetwoaraeontop();
		setareaonright();
	//	areaofexceptions.setText("");
	    directionpanel.setVisible(false); 
		submitxy.setVisible(false);
	       xarea.setVisible(false);
	       yarea.setVisible(false);
	       OncheckGameOver();
	}
public void onAttack(Direction d) {
		try {
			game.attack(d);
		} catch ( NotEnoughResourcesException e ) {
		   error.showMessageDialog(null,e.getMessage(),"Error occurred", JOptionPane.ERROR_MESSAGE);
			return;
		}
		catch(ChampionDisarmedException e)
		{
			error.showMessageDialog(null,e.getMessage(),"Error occurred", JOptionPane.ERROR_MESSAGE);
			return;
		}
		setelementsontheboard(game);
		setareaonleft();
		setthetwoaraeontop();
	//	areaofexceptions.setText("");
		setareaonright();
	    directionpanel.setVisible(false); 
		submitxy.setVisible(false);
	       xarea.setVisible(false);
	       yarea.setVisible(false);
	       OncheckGameOver();
	}

//need to be revised
public void onCastAbility(Ability a) {
		try {
			game.castAbility(a);
			setelementsontheboard(game);
			setareaonleft();
			setthetwoaraeontop();
			setareaonright();
		//	areaofexceptions.setText("");
			submitxy.setVisible(false);
		    directionpanel.setVisible(false); 
		       xarea.setVisible(false);
		       yarea.setVisible(false);
		       OncheckGameOver();
		} catch (NotEnoughResourcesException e) {
			error.showMessageDialog(null,e.getMessage(),"Error occurred", JOptionPane.ERROR_MESSAGE);
			return;
		} catch (AbilityUseException k) {
			error.showMessageDialog(null,k.getMessage(),"Error occurred", JOptionPane.ERROR_MESSAGE);
			return;
		} catch (CloneNotSupportedException g) {
			error.showMessageDialog(null,g.getMessage(),"Error occurred", JOptionPane.ERROR_MESSAGE);
			return;
		}
			
		
	
	}
public void onCastAbility(Ability a, Direction d) {
	try {
		game.castAbility(a, d);
		setelementsontheboard(game);
		setareaonleft();
		setthetwoaraeontop();
	//	areaofexceptions.setText("");
		setareaonright();
		submitxy.setVisible(false);
	     xarea.setVisible(false);
	     yarea.setVisible(false);
	    directionpanel .setVisible(false);  
	       OncheckGameOver();
	} catch (NotEnoughResourcesException e) {
		error.showMessageDialog(null,e.getMessage(), "Error occurred", JOptionPane.ERROR_MESSAGE);
		return;
	} catch (AbilityUseException c) {
		error.showMessageDialog(null,c.getMessage(), "Error occurred", JOptionPane.ERROR_MESSAGE);
		return;
	} catch (CloneNotSupportedException k) {
		error.showMessageDialog(null,k.getMessage(), "Error occurred", JOptionPane.ERROR_MESSAGE);
	}

	
	
	
	}
public void onCastAbility(Ability a, int x, int y) {
		try {
			game.castAbility(a,x,y);
			setelementsontheboard(game);
			setareaonleft();
			setthetwoaraeontop();
		//	areaofexceptions.setText("");
			setareaonright();
			submitxy.setVisible(false);
		    directionpanel.setVisible(false); 
		       xarea.setVisible(false);
		       yarea.setVisible(false);
		       OncheckGameOver();
		} catch (NotEnoughResourcesException e) {
			error.showMessageDialog(null,e.getMessage(), "Error occurred", JOptionPane.ERROR_MESSAGE);
			return;
		} catch (AbilityUseException e) {
			error.showMessageDialog(null,e.getMessage(), "Error occurred", JOptionPane.ERROR_MESSAGE);
			return;
		} catch (InvalidTargetException e) {
			error.showMessageDialog(null,e.getMessage(), "Error occurred", JOptionPane.ERROR_MESSAGE);
			return;
		} catch (CloneNotSupportedException e) {
			error.showMessageDialog(null,e.getMessage(), "Error occurred", JOptionPane.ERROR_MESSAGE);
			return;
		}
			
				
			
		
		
	}
public void onUseLeaderAbility() {
	try {
		game.useLeaderAbility();
		setelementsontheboard(game);
		setareaonleft();
		setthetwoaraeontop();
	//	areaofexceptions.setText("");
	    directionpanel.setVisible(false); 
		submitxy.setVisible(false);
	    xarea.setVisible(false);
	    yarea.setVisible(false);
		setareaonright();
		OncheckGameOver();
		return;
	} 
	catch (LeaderNotCurrentException x ) {
		error.showMessageDialog(null,x.getMessage(), "Error occurred", JOptionPane.ERROR_MESSAGE);
	    return;
	}
	
	catch(LeaderAbilityAlreadyUsedException e) {
		error.showMessageDialog(null,e.getMessage(), "Error occurred", JOptionPane.ERROR_MESSAGE);
	     return;
	}
	}
public void onEndTurn() {
		game.endTurn();
		rightcurrentinfo.setText("current champion playin info "+"\n"+game.getCurrentChampion());
		whoisplaying.removeAll();
		Champion n = null;
		if(game.getTurnOrder().size()>1)
			n= (Champion) game.getTurnOrder().nextChampturn();
		else{
		PriorityQueue p = new PriorityQueue(6);
        for (Champion c : game.getFirstPlayer().getTeam())
			if(game.getCurrentChampion()!=c)
			   p.insert(c);
		for (Champion c : game.getSecondPlayer().getTeam())
			if(game.getCurrentChampion()!=c)
			    p.insert(c);
		n=(Champion)p.remove();}
		ImageIcon PlayingNowimage = this.thebigmainview.getScaledImage( new ImageIcon((game.getCurrentChampion().getName())+".png"),170,150);
	    ImageIcon PlayingNextimage = this.thebigmainview.getScaledImage( new ImageIcon(n.getName()+".png"),170,150);
		Border borderPlayingNow=BorderFactory.createLineBorder(Color.YELLOW,3);
		Border borderPlayingNext=BorderFactory.createLineBorder(Color.WHITE,3);
	    champnow= new JLabel();
		champnow.setIcon(PlayingNowimage);
		champnow.setBackground(Color.WHITE);
		champnow.setOpaque(true);
		champnow.setBorder(borderPlayingNow);
		champnow.setSize(65,65);
		champnext= new JLabel();
		champnext.setIcon(PlayingNextimage);
		champnext.setBackground(Color.WHITE);
		champnext.setOpaque(true);
		champnext.setBorder(borderPlayingNext);
		champnext.setSize(65,65);
		whoisplaying.add(champnow);
		whoisplaying.add(champnext);
		setelementsontheboard(game);
		setareaonleft();
		setthetwoaraeontop();
		//areaofexceptions.setText("");
		submitxy.setVisible(false);
	       xarea.setVisible(false);
	       yarea.setVisible(false);
	       directionpanel.setVisible(false); 
	       setareaonright();
	       OncheckGameOver();
	}
public void OncheckGameOver() {
		if (game.checkGameOver()!=null)
		{  String s ="the winner is               "+"\n"+game.checkGameOver().getName();
			new Controller(s);
			thebigmainview.dispose();
			
		}
	}
public static void main(String[] args) {
		new Controller();}
	
}
