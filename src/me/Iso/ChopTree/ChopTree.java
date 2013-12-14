package me.Iso.ChopTree;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ChopTree
extends JavaPlugin
{
	private final ChopTreeBlockListener blockListener = new ChopTreeBlockListener(this);
	private final ChopTreeBlockListenerModded blockListenermodded = new ChopTreeBlockListenerModded(this);
	public final HashMap<Player, Block[]> trees = new HashMap<Player, Block[]>();
	private FileConfiguration config;
	protected boolean defaultActive;
	protected boolean useAnything;
	protected boolean moreDamageToTools;
	protected boolean interruptIfToolBreaks;
	protected boolean logsMoveDown;
	protected boolean onlyTrees;
	protected boolean popLeaves;
	protected int leafRadius;
	protected String[] allowedTools;
	private File playerFile;
	protected FileConfiguration playersDb;

	public void onEnable()
	{
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this.blockListener, this);
		pm.registerEvents(this.blockListenermodded, this);
		getConfig().options().copyDefaults(true);
		loadConfig();
		this.playersDb = getPlayers();
	}

	public void onDisable() {}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if ((commandLabel.equalsIgnoreCase("ChopTree")) || (commandLabel.equalsIgnoreCase("ct")))
		{
			if (Array.getLength(args) == 0)
			{
				if (!sender.hasPermission("choptree.commands.choptree.info"))
				{
					sender.sendMessage(ChatColor.RED + "You do not have permission to use that command!");
					return false;
				}
				sender.sendMessage(ChatColor.GOLD + "ChopTree2 v" + getDescription().getVersion() + " : By ellbristow");
				sender.sendMessage(ChatColor.GRAY + "===================================");
				sender.sendMessage(ChatColor.GOLD + "ActiveByDefault : " + ChatColor.GRAY + this.defaultActive);
				sender.sendMessage(ChatColor.GOLD + "UseAnything : " + ChatColor.GRAY + this.useAnything);
				sender.sendMessage(ChatColor.GOLD + "MoreDamageToTools : " + ChatColor.GRAY + this.moreDamageToTools);
				sender.sendMessage(ChatColor.GOLD + "InterruptIfToolBreaks : " + ChatColor.GRAY + this.interruptIfToolBreaks);
				sender.sendMessage(ChatColor.GOLD + "LogsMoveDown : " + ChatColor.GRAY + this.logsMoveDown);
				sender.sendMessage(ChatColor.GOLD + "OnlyTrees : " + ChatColor.GRAY + this.onlyTrees);
				if (this.useAnything) {
					sender.sendMessage(ChatColor.GOLD + "AllowedTools : " + ChatColor.GRAY + "ANYTHING!");
				} else {
					sender.sendMessage(ChatColor.GOLD + "AllowedTools : " + ChatColor.GRAY + arrayToString(this.allowedTools, ","));
				}
				sender.sendMessage(ChatColor.GOLD + "PopLeaves : " + ChatColor.GRAY + this.popLeaves);
			}
			else if ((args[0].equalsIgnoreCase("reload")) || (args[0].equalsIgnoreCase("r")))
			{
				if (!sender.hasPermission("choptree.commands.choptree.reload"))
				{
					sender.sendMessage(ChatColor.RED + "You do not have permission to use that command!");
					return false;
				}
				loadConfig();
				sender.sendMessage(ChatColor.GREEN + "Reloaded settings from properties file.");
			}
			else if (args[0].equalsIgnoreCase("toggle"))
			{
				if (args.length == 1)
				{
					sender.sendMessage(ChatColor.RED + "You must specify an option to toggle!");
					sender.sendMessage(ChatColor.GRAY + "(ActiveByDefault|UseAnything|MoreDamageToTools|InterruptIfToolBreaks|LogsMoveDown|OnlyTrees|PopLeaves)");
					return false;
				}
				if (!sender.hasPermission("choptree.commands.choptree.toggle." + args[1].toLowerCase()))
				{
					sender.sendMessage(ChatColor.RED + "You do not have permission to toggle that setting!");
					return false;
				}
				if (args[1].equalsIgnoreCase("ActiveByDefault"))
				{
					if (this.defaultActive)
					{
						this.defaultActive = false;
						this.config.set("ActiveByDefault", Boolean.valueOf(false));
					}
					else
					{
						this.defaultActive = true;
						this.config.set("ActiveByDefault", Boolean.valueOf(true));
					}
					sender.sendMessage(ChatColor.GOLD + "ActiveByDefault set to : " + ChatColor.GRAY + this.defaultActive);
				}
				else if (args[1].equalsIgnoreCase("UseAnything"))
				{
					if (this.useAnything)
					{
						this.useAnything = false;
						this.config.set("UseAnything", Boolean.valueOf(false));
					}
					else
					{
						this.useAnything = true;
						this.config.set("UseAnything", Boolean.valueOf(true));
					}
					sender.sendMessage(ChatColor.GOLD + "UseAnything set to : " + ChatColor.GRAY + this.useAnything);
				}
				else if (args[1].equalsIgnoreCase("MoreDamageToTools"))
				{
					if (this.moreDamageToTools)
					{
						this.moreDamageToTools = false;
						this.config.set("MoreDamageToTools", Boolean.valueOf(false));
					}
					else
					{
						this.moreDamageToTools = true;
						this.config.set("MoreDamageToTools", Boolean.valueOf(true));
					}
					sender.sendMessage(ChatColor.GOLD + "MoreDamageToTools set to : " + ChatColor.GRAY + this.moreDamageToTools);
				}
				else if (args[1].equalsIgnoreCase("InterruptIfToolBreaks"))
				{
					if (this.interruptIfToolBreaks)
					{
						this.interruptIfToolBreaks = false;
						this.config.set("InterruptIfToolBreaks", Boolean.valueOf(false));
					}
					else
					{
						this.interruptIfToolBreaks = true;
						this.config.set("InterruptIfToolBreaks", Boolean.valueOf(true));
					}
					sender.sendMessage(ChatColor.GOLD + "InterruptIfToolBreaks set to : " + ChatColor.GRAY + this.interruptIfToolBreaks);
				}
				else if (args[1].equalsIgnoreCase("LogsMoveDown"))
				{
					if (this.logsMoveDown)
					{
						this.logsMoveDown = false;
						this.config.set("LogsMoveDown", Boolean.valueOf(false));
					}
					else
					{
						this.logsMoveDown = true;
						this.config.set("LogsMoveDown", Boolean.valueOf(true));
					}
					sender.sendMessage(ChatColor.GOLD + "LogsMoveDown set to : " + ChatColor.GRAY + this.logsMoveDown);
				}
				else if (args[1].equalsIgnoreCase("OnlyTrees"))
				{
					if (this.onlyTrees)
					{
						this.onlyTrees = false;
						this.config.set("OnlyTrees", Boolean.valueOf(false));
					}
					else
					{
						this.onlyTrees = true;
						this.config.set("OnlyTrees", Boolean.valueOf(true));
					}
					sender.sendMessage(ChatColor.GOLD + "OnlyTrees set to : " + ChatColor.GRAY + this.onlyTrees);
				}
				else if (args[1].equalsIgnoreCase("PopLeaves"))
				{
					if (this.popLeaves)
					{
						this.popLeaves = false;
						this.config.set("PopLeaves", Boolean.valueOf(false));
					}
					else
					{
						this.popLeaves = true;
						this.config.set("PopLeaves", Boolean.valueOf(true));
					}
					sender.sendMessage(ChatColor.GOLD + "PopLeaves set to : " + ChatColor.GRAY + this.popLeaves);
				}
				else
				{
					sender.sendMessage(ChatColor.RED + "I can't find a setting called " + ChatColor.WHITE + args[1] + ChatColor.RED + "!");
					sender.sendMessage(ChatColor.GRAY + "(ActiveByDefault|UseAnything|MoreDamageToTools|InterruptIfToolBreaks|LogsMoveDown|OnlyTrees|PopLeaves)");
					return true;
				}
				saveConfig();
			}
		}
		else if ((commandLabel.equalsIgnoreCase("ToggleChop")) || (commandLabel.equalsIgnoreCase("tc")))
		{
			ChopTreePlayer ctPlayer = new ChopTreePlayer(this, sender.getName());
			ctPlayer.toggleActive();
			if (ctPlayer.isActive()) {
				sender.sendMessage(ChatColor.GOLD + "ChopeTree Activated!");
			} else {
				sender.sendMessage(ChatColor.GOLD + "ChopeTree Deactivated!");
			}
		}
		return true;
	}

	private String arrayToString(String[] array, String separator)
	{
		String outString = "";
		for (String string : array)
		{
			if (!"".equals(outString)) {
				outString = outString + separator;
			}
			outString = outString + string;
		}
		return outString;
	}

	public void loadConfig()
	{
		reloadConfig();
		this.config = getConfig();
		this.defaultActive = this.config.getBoolean("ActiveByDefault", true);
		this.config.set("ActiveByDefault", Boolean.valueOf(this.defaultActive));
		this.useAnything = this.config.getBoolean("UseAnything", false);
		this.config.set("UseAnything", Boolean.valueOf(this.useAnything));
		this.allowedTools = this.config.getString("AllowedTools", "WOOD_AXE,STONE_AXE,IRON_AXE,GOLD_AXE,DIAMOND_AXE").split(",");
		this.config.set("AllowedTools", arrayToString(this.allowedTools, ","));
		this.moreDamageToTools = this.config.getBoolean("MoreDamageToTools", false);
		this.config.set("MoreDamageToTools", Boolean.valueOf(this.moreDamageToTools));
		this.interruptIfToolBreaks = this.config.getBoolean("InterruptIfToolBreaks", false);
		this.config.set("InterruptIfToolBreaks", Boolean.valueOf(this.interruptIfToolBreaks));
		this.logsMoveDown = this.config.getBoolean("LogsMoveDown", false);
		this.config.set("LogsMoveDown", Boolean.valueOf(this.logsMoveDown));
		this.onlyTrees = this.config.getBoolean("OnlyTrees", true);
		this.config.set("OnlyTrees", Boolean.valueOf(this.onlyTrees));
		this.popLeaves = this.config.getBoolean("PopLeaves", false);
		this.config.set("PopLeaves", Boolean.valueOf(this.popLeaves));
		this.leafRadius = this.config.getInt("LeafRadius", 3);
		this.config.set("LeafRadius", Integer.valueOf(this.leafRadius));
		saveConfig();
	}

	public void loadPlayers()
	{
		if (this.playerFile == null) {
			this.playerFile = new File(getDataFolder(), "players.yml");
		}
		this.playersDb = YamlConfiguration.loadConfiguration(this.playerFile);
	}

	public FileConfiguration getPlayers()
	{
		if (this.playersDb == null) {
			loadPlayers();
		}
		return this.playersDb;
	}

	public void savePlayers()
	{
		if ((this.playersDb == null) || (this.playerFile == null)) {
			return;
		}
		try
		{
			this.playersDb.save(this.playerFile);
		}
		catch (IOException ex)
		{
			String message = "Could not save " + this.playerFile;
			getLogger().severe(message);
		}
	}
}
