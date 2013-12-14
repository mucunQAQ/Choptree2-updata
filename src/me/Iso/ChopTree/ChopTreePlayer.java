package me.Iso.ChopTree;

public class ChopTreePlayer
{
  private static ChopTree plugin;
  private boolean active;
  private String playerName;
  
  public ChopTreePlayer(ChopTree instance, String playerName)
  {
    plugin = instance;
    this.playerName = playerName;
    this.active = getSetting("active");
    if (plugin.playersDb.get(playerName + ".active") == null)
    {
      addPlayer();
      this.active = plugin.defaultActive;
    }
  }
  
  public boolean isActive()
  {
    return this.active;
  }
  
  public void setActive(boolean setting)
  {
    this.active = setting;
    plugin.playersDb.set(this.playerName + ".active", Boolean.valueOf(setting));
    plugin.savePlayers();
  }
  
  public void toggleActive()
  {
    if (this.active) {
      this.active = false;
    } else {
      this.active = true;
    }
    plugin.playersDb.set(this.playerName + ".active", Boolean.valueOf(this.active));
    plugin.savePlayers();
  }
  
  private boolean getSetting(String setting)
  {
    boolean value = false;
    if (setting.equalsIgnoreCase("active")) {
      value = plugin.playersDb.getBoolean(this.playerName + "." + setting, plugin.defaultActive);
    }
    return value;
  }
  
  private void addPlayer()
  {
    plugin.playersDb.set(this.playerName + ".active", Boolean.valueOf(plugin.defaultActive));
    plugin.savePlayers();
  }
}
