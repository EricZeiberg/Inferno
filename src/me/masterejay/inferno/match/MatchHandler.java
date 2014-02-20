package me.masterejay.inferno.match;


import me.masterejay.inferno.Inferno;
import me.masterejay.inferno.Values;
import me.masterejay.inferno.countdowns.CountdownMethods;
import me.masterejay.inferno.countdowns.RestartCountdown;
import me.masterejay.inferno.kits.KitItem;
import me.masterejay.inferno.shop.ShopHandler;
import me.masterejay.inferno.teams.BlueTeam;
import me.masterejay.inferno.teams.RedTeam;
import me.masterejay.inferno.teams.TeamHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

/**
 * @author MasterEjay
 */
public class MatchHandler {

	private static MatchState state = MatchState.WAITING;

	private static ScoreboardManager manager = Bukkit.getScoreboardManager();
	private static Scoreboard board;

	private static Team red;
	private static Team blue;
	private static Team obs;
	private static Objective objective;
	private static Score redScore;
	private static Score blueScore;

	public static void initScoreboard(){
		board = manager.getNewScoreboard();
		red = board.registerNewTeam("Red");
		red.setAllowFriendlyFire(false);
		red.setPrefix(ChatColor.RED + "");
		blue = board.registerNewTeam("Blue");
		blue.setAllowFriendlyFire(false);
		blue.setPrefix(ChatColor.BLUE + "");
		obs = board.registerNewTeam("Observers");
		obs.setAllowFriendlyFire(false);
		obs.setPrefix(ChatColor.AQUA + "");


		objective = board.registerNewObjective("scoreboard", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName("Points");
		redScore = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.RED + "Red:     "));
		blueScore = objective.getScore(Bukkit.getOfflinePlayer(ChatColor.BLUE + "Blue:   "));
	}

	public static void start(){
		MatchHandler.setState(MatchState.PLAYING);
		for (Player p : Values.getParticipating()){
			p.getInventory().clear();
			p.setGameMode(GameMode.SURVIVAL);
			for (Player obs : TeamHandler.getObservers().getObservers()){
				p.hidePlayer(obs);
			}
			if (TeamHandler.getBlueTeam().isPlayerOnBlue(p)){
				p.teleport(Values.getBlueSpawn());
				p.setBedSpawnLocation(Values.getBlueSpawn(), true);
				for (KitItem i : BlueTeam.getKit()){
					if (p.hasPermission(i.getPermission())){
						if (i.getSlot() == 100){
						  p.getInventory().setBoots(i.getItem());
							continue;
						}
						else if (i.getSlot() == 101){
							p.getInventory().setLeggings(i.getItem());
							continue;
						}
						else if (i.getSlot() == 102){
							p.getInventory().setChestplate(i.getItem());
							continue;
						}
						else if (i.getSlot() == 103){
							p.getInventory().setHelmet(i.getItem());
							continue;
						}
						p.getInventory().setItem(i.getSlot(), i.getItem());
					}
				}

			}
			else if (TeamHandler.getRedTeam().isPlayerOnRed(p)){
				p.teleport(Values.getRedSpawn());
				p.setBedSpawnLocation(Values.getRedSpawn(), true);
				for (KitItem i : RedTeam.getKit()){
					if (p.hasPermission(i.getPermission())){
						if (i.getSlot() == 100){
							p.getInventory().setBoots(i.getItem());
							continue;
						}
						else if (i.getSlot() == 101){
							p.getInventory().setLeggings(i.getItem());
							continue;
						}
						else if (i.getSlot() == 102){
							p.getInventory().setChestplate(i.getItem());
							continue;
						}
						else if (i.getSlot() == 103){
							p.getInventory().setHelmet(i.getItem());
							continue;
						}
						p.getInventory().setItem(i.getSlot(), i.getItem());
					}

				}
			}
			p.getInventory().setItem(8, ShopHandler.getItemOpen().getItem());
		}
	}

	public static void finish(String winner){
		MatchHandler.setState(MatchState.FINISHED);
		if (winner == null){
			Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "############");
			Bukkit.broadcastMessage(ChatColor.GREEN + "The match has been force ended!");
			Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "############");
		}
		else if (!winner.equalsIgnoreCase("disable")) {
			Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "############");
			Bukkit.broadcastMessage(ChatColor.GREEN + "The match has ended!");
			Bukkit.broadcastMessage(ChatColor.GREEN + winner + " has won!");
			Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "############");
		}
		else if (winner.equalsIgnoreCase("disable")){
			Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "############");
			Bukkit.broadcastMessage(ChatColor.GREEN + "The match has been force ended!");
			Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "############");
			for (Player p : Values.getParticipating()) {
				p.setGameMode(GameMode.CREATIVE);
				for (Player obs : TeamHandler.getObservers().getObservers()){
					p.showPlayer(obs);
				}
			}
			return;
		}
		 for (Player p : Values.getParticipating()) {
			 p.setGameMode(GameMode.CREATIVE);
			 for (Player obs : TeamHandler.getObservers().getObservers()){
				 p.showPlayer(obs);
			 }
		 }
		CountdownMethods.start(new RestartCountdown(),20);
	}

	public static void addPoints(int points, String team){
		if (points + Values.getBluePoints() == Values.getPointsToWin()){
			finish(ChatColor.BLUE + "Blue");
		}
		else if (points + Values.getRedPoints() == Values.getPointsToWin()){
			finish(ChatColor.RED + "Red");
		}
		if (team.equalsIgnoreCase("blue")){
			Values.setBluePoints(Values.getBluePoints() + points);
			addPointsToScore(points, "blue");
			if (Values.getBluePoints() % Values.getPointsToBroadcast() == 0){
				broadcast();
			}
		}
		else if (team.equalsIgnoreCase("red")){
			Values.setRedPoints(Values.getRedPoints() + points);
			addPointsToScore(points, "red");
			if (Values.getRedPoints() % Values.getPointsToBroadcast() == 0){
				broadcast();
			}
		}

	}

	private static void broadcast(){
		Bukkit.broadcastMessage(ChatColor.GREEN + "Score: " + ChatColor.BLUE + String.valueOf(Values.getBluePoints()) + ChatColor.WHITE + " | " +
				ChatColor.RED + String.valueOf(Values.getRedPoints()));
	}

	//Unloading maps, to rollback maps. Will delete all player builds until last server save
	public static void unloadMap(String mapname){
		if(Bukkit.getServer().unloadWorld(Bukkit.getServer().getWorld(mapname), false)){
			Inferno.getPlugin().getLogger().info("Successfully unloaded " + mapname);
		}else{
			Inferno.getPlugin().getLogger().severe("COULD NOT UNLOAD " + mapname);
		}
	}
	//Loading maps (MUST BE CALLED AFTER UNLOAD MAPS TO FINISH THE ROLLBACK PROCESS)
	public static void loadMap(String mapname){
		Bukkit.getServer().createWorld(new WorldCreator(mapname));
	}

	//Maprollback method, because were too lazy to type 2 lines
	public static void rollback(String mapname){
		unloadMap(mapname);
		loadMap(mapname);
	}

	public static MatchState getState(){
		return state;
	}

	public static void setState(MatchState state){
		MatchHandler.state = state;
	}

	public static Team getRedTeamScoreboard(){
		return red;
	}

	public static Team getBlueTeamScoreboard(){
		return blue;
	}

	public static Team getObserverTeamScoreboard(){
		return obs;
	}

	public static void addPlayerToScore(Player p, String team){
		if (team.equalsIgnoreCase("red")){
		  red.addPlayer(p);
		}
		else if (team.equalsIgnoreCase("blue")){
		  blue.addPlayer(p);
		}
		else if (team.equalsIgnoreCase("obs")){
			obs.addPlayer(p);
		}
	}

	public static void removePlayerFromScore(Player p, String team){
		if (team.equalsIgnoreCase("red")){
			red.removePlayer(p);
		}
		else if (team.equalsIgnoreCase("blue")){
			blue.removePlayer(p);
		}
		else if (team.equalsIgnoreCase("obs")){
			obs.removePlayer(p);
		}
	}


	public static Score getRedScore(){
		return redScore;
	}

	public static Score getBlueScore(){
		return blueScore;
	}

	public static Scoreboard getBoard(){
		return board;
	}

	public static ScoreboardManager getManager(){
		return manager;
	}

	public static void addPointsToScore(int points, String team){
		if (team.equalsIgnoreCase("red")) {
			redScore.setScore(redScore.getScore() + points);
		}
		else if (team.equalsIgnoreCase("blue")){
			blueScore.setScore(blueScore.getScore() + points);
		}
	}

	public static void resetScoreboard(){
		if (board != null && red != null && blue!= null && obs != null){
			board.resetScores(Bukkit.getOfflinePlayer(ChatColor.BLUE + "Blue:"));
			board.resetScores(Bukkit.getOfflinePlayer(ChatColor.RED + "Red:"));
			red.unregister();
			blue.unregister();
			red.getPlayers().clear();
			blue.getPlayers().clear();
			obs.getPlayers().clear();
		}

	}
}
