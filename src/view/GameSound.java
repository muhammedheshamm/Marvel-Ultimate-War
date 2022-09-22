package view;

import java.io.File;
import java.util.Scanner;

import javax.sound.sampled.*;

public class GameSound {
	static Clip clip;

public static void PlaySound(String Sound){
	try{
	    AudioInputStream input = AudioSystem.getAudioInputStream(new File(Sound));
		clip= AudioSystem.getClip();
	    clip.open(input);
	    clip.loop(Clip.LOOP_CONTINUOUSLY);
	    clip.start();
	   }
	catch (Exception e){
	     e.printStackTrace();
		     }
	
}
public static void StopSound(){
	clip.stop();
}

}
