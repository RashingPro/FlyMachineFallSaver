package dev.rashing.fmfs.command.client;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.rashing.fmfs.FlyMachineFallSaverClient;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class CommandStop implements Command<FabricClientCommandSource> {
    @Override
    public int run(CommandContext<FabricClientCommandSource> commandContext) throws CommandSyntaxException {
        MinecraftClient client = MinecraftClient.getInstance();
        assert client.player != null;
        FlyMachineFallSaverClient.savedY = null;
        Text message = Text.translatable("fmfs.message.fly_stop").formatted(Formatting.RED);
        client.player.sendMessage(message, true);
        return 1;
    }
}
