package me.masterejay.inferno;

import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissionsException;
import com.sk89q.minecraft.util.commands.CommandUsageException;
import com.sk89q.minecraft.util.commands.CommandsManager;
import com.sk89q.minecraft.util.commands.MissingNestedCommandException;
import com.sk89q.minecraft.util.commands.WrappedCommandException;
import me.masterejay.inferno.commands.AdminCommands;
import me.masterejay.inferno.commands.JoinCommand;
import me.masterejay.inferno.kits.KitItem;
import me.masterejay.inferno.listeners.BlockListener;
import me.masterejay.inferno.listeners.ConnectionListener;
import me.masterejay.inferno.listeners.DeathListener;
import me.masterejay.inferno.listeners.InventoryListener;
import me.masterejay.inferno.listeners.MiscListener;
import me.masterejay.inferno.match.MatchHandler;
import me.masterejay.inferno.match.MatchState;
import me.masterejay.inferno.shop.ShopHandler;
import me.masterejay.inferno.shop.ShopItem;
import me.masterejay.inferno.teams.TeamHandler;
import me.masterejay.inferno.utils.StringUtils;
import me.masterejay.inferno.utils.TeamPicker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MasterEjay
 */
public class Inferno extends JavaPlugin{

    public CommandsManager<CommandSender> commands;
   	private static Inferno plugin = null;

	World world = null;

	public static Inferno getPlugin(){
		return plugin;
	}


	@Override
	public void onDisable(){
		if (MatchHandler.getState() == MatchState.PLAYING){
			MatchHandler.finish("DISABLE");
		}
	}
	@Override
    public void onEnable(){
	    plugin = this;
		getServer().getPluginManager().registerEvents(new ConnectionListener(), this);
		getServer().getPluginManager().registerEvents(new DeathListener(), this);
		getServer().getPluginManager().registerEvents(new BlockListener(), this);
		getServer().getPluginManager().registerEvents(new InventoryListener(), this);
		getServer().getPluginManager().registerEvents(new MiscListener(), this);
        if (!new File(getDataFolder(), "config.yml").exists()) {
            saveDefaultConfig();
        }
        setupCommands();
		MatchHandler.resetScoreboard();
		MatchHandler.initScoreboard();
		TeamPicker.initInventory();
	    if (getServer().getOnlinePlayers().length > 0){
		    for (Player p : getServer().getOnlinePlayers()){
			    if (TeamHandler.getBlueTeam().isPlayerOnBlue(p)){
				    TeamHandler.getBlueTeam().removeBluePlayer(p);
			    }
			    else if (TeamHandler.getRedTeam().isPlayerOnRed(p)){
				    TeamHandler.getRedTeam().removeRedPlayer(p);
			    }
			    else if (TeamHandler.getObservers().isPlayerObserving(p)){
				    TeamHandler.getObservers().removeObserver(p);
			    }
			    TeamHandler.getObservers().addObserver(p);
		    }
	    }
	  setWorld();

	  parseConfig();


    }

	private void parseConfig(){
		String[] obsLoc = {getConfig().getString("observer.x"), getConfig().getString("observer.y"),
				getConfig().getString("observer.z")};
		for (String s : obsLoc){
			if (!StringUtils.isInteger(s)) {
				getLogger().severe(ChatColor.RED + "ERROR PARSING CONFIG! SERVER SHUTING DOWN...." + s);
				getServer().shutdown();
			}
		}

		Values.setObserverSpawn(new Location(getWorld(),
				Double.parseDouble(obsLoc[0]), Double.parseDouble(obsLoc[1]), Double.parseDouble(obsLoc[2])));

		String[] redLoc = {getConfig().getString("red.x"), getConfig().getString("red.y"),
				getConfig().getString("red.z")};
		for (String s : redLoc){
			if (!StringUtils.isInteger(s)) {
				getLogger().severe(ChatColor.RED + "ERROR PARSING CONFIG! SERVER SHUTING DOWN....");
				getServer().shutdown();
			}
		}

		Values.setRedSpawn(new Location(getWorld(),
				Double.parseDouble(redLoc[0]),Double.parseDouble(redLoc[1]),Double.parseDouble(redLoc[2])));

		String[] blueLoc = {getConfig().getString("blue.x"), getConfig().getString("blue.y"),
				getConfig().getString("blue.z")};
		for (String s : blueLoc){
			if (!StringUtils.isInteger(s)) {
				getLogger().severe(ChatColor.RED + "ERROR PARSING CONFIG! SERVER SHUTING DOWN....");
				getServer().shutdown();
			}
		}

		Values.setBlueSpawn(new Location(getWorld(),
				Double.parseDouble(blueLoc[0]),Double.parseDouble(blueLoc[1]),Double.parseDouble(blueLoc[2])));

		Values.setPointsPerKill(Integer.parseInt(getConfig().getString("ppk")));
		Values.setPointsToWin(Integer.parseInt(getConfig().getString("pointsToWin")));

		Values.setPointsToBroadcast(Integer.parseInt(getConfig().getString("pointsToBroadcast")));

		Values.setBreakBlocks(getConfig().getBoolean("settings.breakBlocks"));


		TeamHandler.getBlueTeam().setKit(parseKits("blue"));
		TeamHandler.getRedTeam().setKit(parseKits("red"));



		ShopHandler.setItems(parseItems());
		String[] shopItem = getConfig().getString("shop.shopItem").split(",");
		ItemStack itemStack = new ItemStack(Material.getMaterial(Integer.parseInt(shopItem[0])));
		String name = shopItem[1];
		ShopHandler.setItemOpen(new ShopItem(itemStack, 0, name, "", 0));
		getLogger().info(String.valueOf(getConfig().getInt("shop.size")));
		ShopHandler.setSize(getConfig().getInt("shop.size"));
		ShopHandler.setPointsPerKill(getConfig().getInt("shop.pointsPerKill"));
		ShopHandler.initShop();

	}


	private List<KitItem> parseKits(String team){
		List<KitItem> red = new ArrayList<>();
		List<KitItem> blue = new ArrayList<>();
	   List<String> kitList = getConfig().getStringList("kit");
		for (String s : kitList){
			String[] string = s.split(",");
			ItemStack item = new ItemStack(Material.getMaterial(Integer.parseInt(string[0])), Integer.parseInt(string[2]));
			int slot = Integer.parseInt(string[1]);
			String permission = string[3];
			String teamKit = string[4];
			if (teamKit.equalsIgnoreCase("blue")){
				blue.add(new KitItem(item, slot, permission));
			}
			else if (teamKit.equalsIgnoreCase("red")){
				red.add(new KitItem(item, slot, permission));
			}
		}
		if (team.equalsIgnoreCase("red")){
			return red;
		}
		else if (team.equalsIgnoreCase("blue")){
			return blue;
		}
		return null;
	}


	private List<ShopItem> parseItems(){
		List<ShopItem> items = new ArrayList<>();
		List<String> strings = getConfig().getStringList("shop.items");
		for (String s: strings) {
			String[] stringList = s.split(",");
			ItemStack item = new ItemStack(Material.getMaterial(Integer.parseInt(stringList[0])));
			int slot = Integer.parseInt(stringList[1]);
			String name = stringList[2];
			String command = stringList[3];
			int price = Integer.parseInt(stringList[4]);
			Inferno.getPlugin().getLogger().info(String.valueOf(price));
			items.add(new ShopItem(item, slot, name, command, price));
		}
		return items;
	}

	private World getWorld(){

		return world;
	}

	private void setWorld(){
		world = Bukkit.getServer().createWorld(new WorldCreator("Map"));
		world.setAutoSave(false);
	}


    public void setupCommands() {
        commands = new CommandsManager<CommandSender>() {
            @Override
            public boolean hasPermission(CommandSender sender, String permission) {
                return sender instanceof ConsoleCommandSender || sender.hasPermission(permission);
            }
        };

        CommandsManagerRegistration cmdRegister = new CommandsManagerRegistration(this, commands);
        cmdRegister.register(JoinCommand.class);
	    cmdRegister.register(AdminCommands.class);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        try {
            this.commands.execute(cmd.getName(), args, sender, sender);
        } catch (CommandPermissionsException ex) {
            sender.sendMessage(ChatColor.RED + "You don't have permission.");
        } catch (MissingNestedCommandException ex) {
            sender.sendMessage(ChatColor.RED + ex.getUsage());
        } catch (CommandUsageException ex) {
            sender.sendMessage(ChatColor.RED + ex.getMessage());
            sender.sendMessage(ChatColor.RED + ex.getUsage());
        } catch (WrappedCommandException ex) {
            if (ex.getCause() instanceof NumberFormatException) {
                sender.sendMessage(ChatColor.RED + "Number expected, string received instead.");
            } else {
                sender.sendMessage(ChatColor.RED + "An error has occurred running command " + ChatColor.DARK_RED + cmd.getName());
                ex.printStackTrace();
            }
        } catch (CommandException ex) {
            sender.sendMessage(ChatColor.RED + ex.getMessage());
        }

        return true;
    }
}
