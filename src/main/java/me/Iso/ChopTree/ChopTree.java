//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.Iso.ChopTree;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ChopTree extends JavaPlugin {
    private final ChopTreeBlockListener blockListener = new ChopTreeBlockListener(this);
    public final HashMap<Player, Block[]> trees = new HashMap();
    private FileConfiguration config;
    protected boolean defaultActive;
    protected boolean useAnything;
    protected boolean moreDamageToTools;
    protected boolean interruptIfToolBreaks;
    protected boolean logsMoveDown;
    protected boolean onlyTrees;
    protected boolean popLeaves;
    protected boolean matchLogType;
    protected int leafRadius;
    protected List<Material> allowedTools = new ArrayList();
    protected String confirmedTools = "";
    private File playerFile;
    protected FileConfiguration playersDb;

    public ChopTree() {
    }

    public void onEnable() {
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(this.blockListener, this);
        this.loadConfig();
        this.playersDb = this.getPlayers();
    }

    public void onDisable() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!commandLabel.equalsIgnoreCase("ChopTree") && !commandLabel.equalsIgnoreCase("ct")) {
            if (commandLabel.equalsIgnoreCase("ToggleChop") || commandLabel.equalsIgnoreCase("tc")) {
                ChopTreePlayer ctPlayer = new ChopTreePlayer(this, sender.getName());
                ctPlayer.toggleActive();
                if (ctPlayer.isActive()) {
                    sender.sendMessage(ChatColor.GOLD + "ChopeTree Activated!");
                } else {
                    sender.sendMessage(ChatColor.GOLD + "ChopeTree Deactivated!");
                }
            }
        } else if (Array.getLength(args) == 0) {
            if (!sender.hasPermission("choptree.commands.choptree.info")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use that command!");
                return false;
            }

            sender.sendMessage(ChatColor.GOLD + "ChopTree2 v" + this.getDescription().getVersion() + " : By ellbristow");
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
                sender.sendMessage(ChatColor.GOLD + "AllowedTools : " + ChatColor.GRAY + this.confirmedTools);
            }

            sender.sendMessage(ChatColor.GOLD + "PopLeaves : " + ChatColor.GRAY + this.popLeaves);
            sender.sendMessage(ChatColor.GOLD + "MatchLogType : " + ChatColor.GRAY + this.matchLogType);
        } else if (!args[0].equalsIgnoreCase("reload") && !args[0].equalsIgnoreCase("r")) {
            if (args[0].equalsIgnoreCase("toggle")) {
                if (args.length == 1) {
                    sender.sendMessage(ChatColor.RED + "You must specify an option to toggle!");
                    sender.sendMessage(ChatColor.GRAY + "(ActiveByDefault|UseAnything|MoreDamageToTools|InterruptIfToolBreaks|LogsMoveDown|OnlyTrees|PopLeaves)");
                    return false;
                }

                if (!sender.hasPermission("choptree.commands.choptree.toggle." + args[1].toLowerCase())) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to toggle that setting!");
                    return false;
                }

                if (args[1].equalsIgnoreCase("ActiveByDefault")) {
                    if (this.defaultActive) {
                        this.defaultActive = false;
                        this.config.set("ActiveByDefault", false);
                    } else {
                        this.defaultActive = true;
                        this.config.set("ActiveByDefault", true);
                    }

                    sender.sendMessage(ChatColor.GOLD + "ActiveByDefault set to : " + ChatColor.GRAY + this.defaultActive);
                } else if (args[1].equalsIgnoreCase("UseAnything")) {
                    if (this.useAnything) {
                        this.useAnything = false;
                        this.config.set("UseAnything", false);
                    } else {
                        this.useAnything = true;
                        this.config.set("UseAnything", true);
                    }

                    sender.sendMessage(ChatColor.GOLD + "UseAnything set to : " + ChatColor.GRAY + this.useAnything);
                } else if (args[1].equalsIgnoreCase("MoreDamageToTools")) {
                    if (this.moreDamageToTools) {
                        this.moreDamageToTools = false;
                        this.config.set("MoreDamageToTools", false);
                    } else {
                        this.moreDamageToTools = true;
                        this.config.set("MoreDamageToTools", true);
                    }

                    sender.sendMessage(ChatColor.GOLD + "MoreDamageToTools set to : " + ChatColor.GRAY + this.moreDamageToTools);
                } else if (args[1].equalsIgnoreCase("InterruptIfToolBreaks")) {
                    if (this.interruptIfToolBreaks) {
                        this.interruptIfToolBreaks = false;
                        this.config.set("InterruptIfToolBreaks", false);
                    } else {
                        this.interruptIfToolBreaks = true;
                        this.config.set("InterruptIfToolBreaks", true);
                    }

                    sender.sendMessage(ChatColor.GOLD + "InterruptIfToolBreaks set to : " + ChatColor.GRAY + this.interruptIfToolBreaks);
                } else if (args[1].equalsIgnoreCase("LogsMoveDown")) {
                    if (this.logsMoveDown) {
                        this.logsMoveDown = false;
                        this.config.set("LogsMoveDown", false);
                    } else {
                        this.logsMoveDown = true;
                        this.config.set("LogsMoveDown", true);
                    }

                    sender.sendMessage(ChatColor.GOLD + "LogsMoveDown set to : " + ChatColor.GRAY + this.logsMoveDown);
                } else if (args[1].equalsIgnoreCase("OnlyTrees")) {
                    if (this.onlyTrees) {
                        this.onlyTrees = false;
                        this.config.set("OnlyTrees", false);
                    } else {
                        this.onlyTrees = true;
                        this.config.set("OnlyTrees", true);
                    }

                    sender.sendMessage(ChatColor.GOLD + "OnlyTrees set to : " + ChatColor.GRAY + this.onlyTrees);
                } else if (args[1].equalsIgnoreCase("PopLeaves")) {
                    if (this.popLeaves) {
                        this.popLeaves = false;
                        this.config.set("PopLeaves", false);
                    } else {
                        this.popLeaves = true;
                        this.config.set("PopLeaves", true);
                    }

                    sender.sendMessage(ChatColor.GOLD + "PopLeaves set to : " + ChatColor.GRAY + this.popLeaves);
                } else {
                    if (!args[1].equalsIgnoreCase("MatchLogType")) {
                        sender.sendMessage(ChatColor.RED + "I can't find a setting called " + ChatColor.WHITE + args[1] + ChatColor.RED + "!");
                        sender.sendMessage(ChatColor.GRAY + "(ActiveByDefault|UseAnything|MoreDamageToTools|InterruptIfToolBreaks|LogsMoveDown|OnlyTrees|PopLeaves|MatchLogType)");
                        return true;
                    }

                    if (this.matchLogType) {
                        this.matchLogType = false;
                        this.config.set("MatchLogType", false);
                    } else {
                        this.matchLogType = true;
                        this.config.set("MatchLogType", true);
                    }

                    sender.sendMessage(ChatColor.GOLD + "MatchLogType set to : " + ChatColor.GRAY + this.matchLogType);
                }

                this.saveConfig();
            }
        } else {
            if (!sender.hasPermission("choptree.commands.choptree.reload")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use that command!");
                return false;
            }

            this.loadConfig();
            sender.sendMessage(ChatColor.GREEN + "Reloaded settings from properties file.");
        }

        return true;
    }

    private String arrayToString(String[] array, String separator) {
        String outString = "";

        for(String string : array) {
            if (!"".equals(outString)) {
                outString = outString + separator;
            }

            outString = outString + string;
        }

        return outString;
    }

    public void loadConfig() {
        this.reloadConfig();
        this.config = this.getConfig();
        this.defaultActive = this.config.getBoolean("ActiveByDefault", true);
        this.config.set("ActiveByDefault", this.defaultActive);
        this.useAnything = this.config.getBoolean("UseAnything", false);
        this.config.set("UseAnything", this.useAnything);
        String[] allowedToolString = this.config.getString("AllowedTools", "WOODEN_AXE,STONE_AXE,IRON_AXE,GOLDEN_AXE,DIAMOND_AXE").split(",");

        for(String tool : allowedToolString) {
            Material toolType = Material.matchMaterial(tool);
            if (toolType != null) {
                this.allowedTools.add(toolType);
                if (!"".equals(this.confirmedTools)) {
                    this.confirmedTools = this.confirmedTools + ",";
                }

                this.confirmedTools = this.confirmedTools + toolType.toString();
            }
        }

        this.config.set("AllowedTools", this.confirmedTools);
        this.moreDamageToTools = this.config.getBoolean("MoreDamageToTools", false);
        this.config.set("MoreDamageToTools", this.moreDamageToTools);
        this.interruptIfToolBreaks = this.config.getBoolean("InterruptIfToolBreaks", false);
        this.config.set("InterruptIfToolBreaks", this.interruptIfToolBreaks);
        this.logsMoveDown = this.config.getBoolean("LogsMoveDown", false);
        this.config.set("LogsMoveDown", this.logsMoveDown);
        this.onlyTrees = this.config.getBoolean("OnlyTrees", true);
        this.config.set("OnlyTrees", this.onlyTrees);
        this.popLeaves = this.config.getBoolean("PopLeaves", false);
        this.config.set("PopLeaves", this.popLeaves);
        this.matchLogType = this.config.getBoolean("MatchLogType", false);
        this.config.set("MatchLogType", this.matchLogType);
        this.leafRadius = this.config.getInt("LeafRadius", 3);
        this.config.set("LeafRadius", this.leafRadius);
        this.saveConfig();
    }

    public void loadPlayers() {
        if (this.playerFile == null) {
            this.playerFile = new File(this.getDataFolder(), "players.yml");
        }

        this.playersDb = YamlConfiguration.loadConfiguration(this.playerFile);
    }

    public FileConfiguration getPlayers() {
        if (this.playersDb == null) {
            this.loadPlayers();
        }

        return this.playersDb;
    }

    public void savePlayers() {
        if (this.playersDb != null && this.playerFile != null) {
            try {
                this.playersDb.save(this.playerFile);
            } catch (IOException var3) {
                String message = "Could not save " + this.playerFile;
                this.getLogger().severe(message);
            }

        }
    }
}
