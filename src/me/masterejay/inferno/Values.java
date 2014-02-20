package me.masterejay.inferno;

import me.masterejay.inferno.kits.KitItem;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MasterEjay
 */
public class Values{

	private static Location observerSpawn = null;
	private static Location redSpawn = null;
	private static Location blueSpawn = null;

	private static List<Player> participating = new ArrayList<Player>();

	private static int pointsToWin = 0;
	private static int redPoints = 0;
	private static int bluePoints = 0;
	private static int pointsPerKill = 0;
	private static int pointsToBroadcast = 0;


	private static boolean breakBlocks = true;


	public static Location getObserverSpawn(){
		return observerSpawn;
	}

	public static void setObserverSpawn(Location observerSpawn){
		Values.observerSpawn=observerSpawn;
	}

	public static Location getRedSpawn(){
		return redSpawn;
	}

	public static void setRedSpawn(Location redSpawn){
		Values.redSpawn=redSpawn;
	}

	public static Location getBlueSpawn(){
		return blueSpawn;
	}

	public static void setBlueSpawn(Location blueSpawn){
		Values.blueSpawn=blueSpawn;
	}

	public static List<Player> getParticipating(){
		return participating;
	}

	public static void addParticipating(Player participating){
		Values.participating.add(participating);
	}

	public static void removeParticipating(Player participating){
		Values.participating.remove(participating);
	}

	public static int getPointsToWin(){
		return pointsToWin;
	}

	public static void setPointsToWin(int pointsToWin){
		Values.pointsToWin=pointsToWin;
	}

	public static int getRedPoints(){
		return redPoints;
	}

	public static void setRedPoints(int redPoints){
		Values.redPoints=redPoints;
	}

	public static int getBluePoints(){
		return bluePoints;
	}

	public static void setBluePoints(int bluePoints){
		Values.bluePoints=bluePoints;
	}

	public static int getPointsPerKill(){
		return pointsPerKill;
	}

	public static void setPointsPerKill(int pointsPerKill){
		Values.pointsPerKill=pointsPerKill;
	}


	public static boolean isBreakBlocks(){
		return breakBlocks;
	}

	public static void setBreakBlocks(boolean breakBlocks){
		Values.breakBlocks=breakBlocks;
	}

	public static int getPointsToBroadcast(){
		return pointsToBroadcast;
	}

	public static void setPointsToBroadcast(int pointsToBroadcast){
		Values.pointsToBroadcast=pointsToBroadcast;
	}




}
