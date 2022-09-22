package engine;

import model.abilities.Ability;
import model.world.Direction;

public interface GameListener {
	public void onMove(Direction d);
	public void onAttack(Direction d);
	public void onCastAbility(Ability a);
	public void onCastAbility(Ability a,Direction d);
	public void onCastAbility(Ability a,int x,int y);
	public void onUseLeaderAbility();
	public void onEndTurn();
	public void OncheckGameOver();
	
	
	
}
