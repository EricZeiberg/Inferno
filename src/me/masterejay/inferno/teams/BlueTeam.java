package me.masterejay.inferno.teams;

import me.masterejay.inferno.Values;
import me.masterejay.inferno.kits.KitItem;
import me.masterejay.inferno.match.MatchHandler;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MasterEjay
 */
public class BlueTeam {

    private static List<Player> blueTeam = new ArrayList<Player>();
	private static List<KitItem> kit = new ArrayList<>();

	public static List<KitItem> getKit(){
		return kit;
	}

	public static void setKit(List<KitItem> kit){
		BlueTeam.kit=kit;
	}

	public void addBluePlayer(Player player){
        if (blueTeam.contains(player)){
            return;
        }
        blueTeam.add(player);
	    MatchHandler.addPlayerToScore(player, "blue");
        player.setDisplayName(ChatColor.BLUE + player.getName());
        player.sendMessage(ChatColor.GRAY + "You have joined the " + ChatColor.BLUE + "Blue Team");
        player.setGameMode(GameMode.CREATIVE);
	    if (!Values.getParticipating().contains(player)){
		    Values.addParticipating(player);
	    }

    }

    public List<Player> getBlueTeam(){
        return blueTeam;
    }

    public void removeBluePlayer(Player player){
        if (blueTeam.contains(player)){
            blueTeam.remove(player);
	        MatchHandler.removePlayerFromScore(player, "blue");
        }
    }

    public boolean isPlayerOnBlue(Player player){
        if (blueTeam.contains(player)){
            return true;
        }
        else {
            return false;
        }
    }
}
