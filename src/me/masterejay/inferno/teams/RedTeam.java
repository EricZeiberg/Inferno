package me.masterejay.inferno.teams;

import me.masterejay.inferno.Values;
import me.masterejay.inferno.kits.KitItem;
import me.masterejay.inferno.match.MatchHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MasterEjay
 */
public class RedTeam {

    private static List<Player> redTeam = new ArrayList<Player>();
	private static List<KitItem> kit = new ArrayList<>();

	public static List<KitItem> getKit(){
		return kit;
	}

	public static void setKit(List<KitItem> kit){
		RedTeam.kit=kit;
	}

    public void addRedPlayer(Player player){
        if (redTeam.contains(player)){
            return;
        }
        redTeam.add(player);
	    MatchHandler.addPlayerToScore(player,"red");
        player.setDisplayName(ChatColor.RED + player.getName());
        player.sendMessage(ChatColor.GRAY + "You have joined the " + ChatColor.RED + "Red Team");
	    if (!Values.getParticipating().contains(player)){
		    Values.addParticipating(player);
	    }
    }

    public List<Player> getRedTeam(){
        return redTeam;
    }

    public void removeRedPlayer(Player player){
        if (redTeam.contains(player)){
            redTeam.remove(player);
	        MatchHandler.removePlayerFromScore(player, "red");
        }
    }

    public boolean isPlayerOnRed(Player player){
        if (redTeam.contains(player)){
            return true;
        }
        else {
            return false;
        }
    }
}
