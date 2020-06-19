package me.border.witherdamage.listeners;

import java.util.ArrayList;
import java.util.Arrays;

import me.border.witherdamage.WitherDamage;
import org.apache.commons.lang.UnhandledException;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.scheduler.BukkitRunnable;
import sun.misc.Cache;

public class WitherEvents implements Listener {
    private WitherDamage plugin;

    ArrayList<Entity> withers;

    public WitherEvents(WitherDamage plugin) {
        this.withers = new ArrayList<>();
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        final Entity entity = e.getEntity();
        if (!(entity instanceof Wither))
            return;
        this.withers.add(entity);
        final double damage = this.plugin.getConfig().getDouble("damage");
        int time = this.plugin.getConfig().getInt("time");
        final Wither wither = (Wither) entity;
        try {
            (new BukkitRunnable() {
                public void run() {
                    if (!WitherEvents.this.withers.contains(entity))
                        cancel();
                    wither.damage(damage);
                }
            }).runTaskTimer(this.plugin, 5L, (time * 20));
        } catch (UnhandledException ignored) { }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        LivingEntity livingEntity = e.getEntity();
        if (!(livingEntity instanceof Wither))
            return;
        this.withers.remove(livingEntity);
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent e) {
        for (Entity entity : e.getChunk().getEntities()) {
            if (entity.getType() == EntityType.WITHER) {
                entity.remove();
            }
        }
    }
}
