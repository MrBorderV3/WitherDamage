 package me.border.witherdamage;

 import me.border.witherdamage.listeners.WitherEvents;
 import org.bukkit.plugin.java.JavaPlugin;

 public class WitherDamage extends JavaPlugin {
     public void onEnable() {
         saveDefaultConfig();
         getConfig().options().copyDefaults(true);
         getServer().getPluginManager().registerEvents(new WitherEvents(this), this);
     }
 }