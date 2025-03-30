package dev.rashing.fmfs.command.client;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import dev.rashing.fmfs.FlyMachineFallSaverClient;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class CommandStart implements Command<FabricClientCommandSource> {
    @Override
    public int run(CommandContext<FabricClientCommandSource> commandContext) throws CommandSyntaxException {
        MinecraftClient client = MinecraftClient.getInstance();
        assert client.player != null;
        if (FlyMachineFallSaverClient.savedY != null) {
            Text message = Text.literal("Ошибка: ").setStyle(Style.EMPTY.withColor(Formatting.RED))
                    .append(Text.literal("полет уже начат. ").setStyle(Style.EMPTY.withColor(Formatting.GOLD)))
                    .append(Text.literal("Используйте ").setStyle(Style.EMPTY.withColor(Formatting.RED)))
                    .append(Text.literal("/stop").setStyle(Style.EMPTY.withColor(Formatting.GOLD)));
            client.player.sendMessage(message, true);
            return 1;
        }
        int playerY = (int) client.player.getY();
        FlyMachineFallSaverClient.savedY = playerY;

        Text message = Text.literal("Начат полет на высоте: ").setStyle(Style.EMPTY.withColor(Formatting.RED))
                .append(Text.literal("%s".formatted(playerY)).setStyle(Style.EMPTY.withColor(Formatting.GOLD)));
//        client.player.sendMessage(message, true);
        client.inGameHud.setOverlayMessage(message, false);
        return 1;
    }

    public static Text getUsageHelp() {
        Text message = Text.literal("Использование: ").setStyle(Style.EMPTY.withColor(Formatting.RED))
                .append(Text.literal("/f_start").setStyle(Style.EMPTY.withColor(Formatting.GOLD)));
        return message;
    }
}
