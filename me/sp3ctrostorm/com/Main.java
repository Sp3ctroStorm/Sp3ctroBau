package me.sp3ctrostorm.com;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;



public class Main
  extends JavaPlugin
  implements CommandExecutor
{
  private PlayerPoints playerPoints;
 33    private final Map<UUID, Inventory> chests = new HashMap<>();





  
  public void onEnable() {
 41      hookPlayerPoints();
  }
  private boolean hookPlayerPoints() {
 44      Plugin plugin = getServer().getPluginManager().getPlugin("PlayerPoints");
 45      if (plugin == null) {
 46        getServer().getPluginManager().disablePlugin((Plugin)this);
 47        return true;
    } 
 49      this.playerPoints = PlayerPoints.class.cast(plugin);
 50      return (this.playerPoints != null);
  }
  
  public PlayerPoints getPlayerPoints() {
 54      return this.playerPoints;
  }

  
  public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
 59      Player p = (Player)sender;
 60      if (cmd.getName().equalsIgnoreCase("bau")) {
 61        if (p.hasPermission("sp3cchest.usar")) {
 62          Inventory chest = getChest(p.getUniqueId());
 63          p.openInventory(chest);
 64        } else if (this.playerPoints.getAPI().take(p.getName(), 100)) {
 65          ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
 66          Bukkit.dispatchCommand((CommandSender)console, "pex user " + p.getName() + " add sp3cchest.usar");
 67          Inventory chest = getChest(p.getUniqueId());
 68          p.openInventory(chest);
      } else {
 70          p.sendMessage("§c§lVocê não tem cash o suficiente para comprar um bau");
      } 
    }
 73      return false;
  }
  
  public Inventory getChest(UUID playerUUID) {
 77      Inventory chest = this.chests.get(playerUUID);
 78      if (chest == null) {
 79        String playerName = Bukkit.getPlayer(playerUUID).getName();
 80        chest = Bukkit.getServer().createInventory(null, 54, "§aBau de §d§l" + playerName);
 81        this.chests.put(playerUUID, chest);
    } 

    
 85      return chest;
  }
}
