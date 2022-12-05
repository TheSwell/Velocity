package ru.gungale.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageListener implements Listener {


    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player damagedPlayer) {
            if (damagedPlayer.getGameMode() == GameMode.CREATIVE) return;
            if (damagedPlayer.isFlying()) damagedPlayer.setFlying(false);
        }
    }


}
