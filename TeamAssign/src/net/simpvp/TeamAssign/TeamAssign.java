package net.simpvp.TeamAssign;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;



public class TeamAssign extends JavaPlugin implements Listener {

	@Override
	public void onEnable(){
		getCommand("teamassign").setExecutor(new TeamAssignCommand());
	}
}
