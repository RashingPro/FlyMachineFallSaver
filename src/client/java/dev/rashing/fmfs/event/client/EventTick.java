package dev.rashing.fmfs.event.client;

import dev.rashing.fmfs.FlyMachineFallSaverClient;
import dev.rashing.fmfs.config.client.ConfigManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class EventTick implements ClientTickEvents.EndTick {

    @Override
    public void onEndTick(MinecraftClient client) {
        if (FlyMachineFallSaverClient.savedY == null) {
            return;
        }
        assert client.player != null;
        try {
            client.player.getY();
        } catch (NullPointerException error) {
            return;
        }

        int playerY = (int) client.player.getY();
        if (playerY < FlyMachineFallSaverClient.savedY) {
            FlyMachineFallSaverClient.savedY = null;
            String action_type = ConfigManager.getConfig().action_type;
            switch (action_type) {
                case ("leave") -> {
                    client.disconnect();
                }
                case ("command") -> {
                    String command = ConfigManager.getConfig().command;
                    client.player.networkHandler.sendChatCommand(command);
                }
            }
        }
    }
}
