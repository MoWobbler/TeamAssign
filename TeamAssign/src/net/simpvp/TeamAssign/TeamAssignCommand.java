package net.simpvp.TeamAssign;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class TeamAssignCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
		
		Team team1 = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(args[0]);
		Team team2 = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(args[1]);
		
		//Run when command sender is a player
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (!player.isOp()) {
				player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
				return true;
			}
			
			if (args.length != 2) {
				player.sendMessage(ChatColor.RED + "/teamassign <team 1> <team 2>");
				return true;
			}
			
			if (team1 == null) {
				player.sendMessage(ChatColor.RED + args[0]+" is not a valid team.");
				return true;
			}
		
			if (team2 == null) {
				player.sendMessage(ChatColor.RED + args[1]+" is not a valid team.");
				return true;
			}
			//Assign teams to everyone within 25 blocks, excluding players in creative mode
			for (Player p : Bukkit.getOnlinePlayers()) {
				  if (p.getLocation().distance(player.getLocation()) <= 25 && p.getGameMode() != GameMode.CREATIVE) {
				    getSmallestTeam(team1,team2).addEntry(p.getName());
				  }
				}
		}
		//Run when command sender is a command block
		if (sender instanceof BlockCommandSender) {
			Location location = ((BlockCommandSender) sender).getBlock().getLocation();
			
			if (args.length != 2) {
				messageNearestOp(location,"/teamassign <team 1> <team 2>");
				return true;
			}
			
			if (team1 == null) {
				messageNearestOp(location,args[0]+" is not a valid team.");
				return true;
			}
			
			if (team2 == null) {
				messageNearestOp(location,args[1]+" is not a valid team.");
				return true;
			}
			//Assign teams to everyone within 25 blocks, excluding players in creative mode
			for (Player p : Bukkit.getOnlinePlayers()) {
				  if (p.getLocation().distance(location) <= 25 && p.getGameMode() != GameMode.CREATIVE) {
				    getSmallestTeam(team1,team2).addEntry(p.getName());
				  }
				}
			
		}
		return false;
	}

	//Get smallest team
	@SuppressWarnings("deprecation")
	public Team getSmallestTeam(Team t1, Team t2) {
	    //smallest team, starting with a default team
	    Team smallestTeam = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(t1.getDisplayName());
	   
	    //loop through all teams and update the smallest team if needed
	    
	        if (t1.getPlayers().size() < smallestTeam.getPlayers().size()) {
	        	smallestTeam = t1;
	        }
	        
	        else if (t2.getPlayers().size() < smallestTeam.getPlayers().size()) {
	        	smallestTeam = t2;
	        }

	   
	    //return the smallest team
	    return smallestTeam;
	}
	
	//Message OPs in creative mode within 10 blocks of the command block
	public void messageNearestOp(Location loc, String msg) {
		for(Player player : Bukkit.getServer().getOnlinePlayers()) {
			if (player.isOp() && player.getGameMode() == GameMode.CREATIVE) {
				if (player.getLocation().distance(loc) <= 10) {
					player.sendMessage(ChatColor.RED + msg);
				}
			}
		}
	}
	
}
