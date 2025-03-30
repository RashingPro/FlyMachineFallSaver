package dev.rashing.fmfs.command.client;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import dev.rashing.fmfs.config.client.ConfigManager;

import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Objects;

public class CommandConfig implements Command<FabricClientCommandSource> {
    @Override
    public int run(CommandContext context) throws CommandSyntaxException {
        ClientCommandSource source = (ClientCommandSource) context.getSource();
        MinecraftClient client = MinecraftClient.getInstance();
        assert client.player != null;

        String arg_action = StringArgumentType.getString(context, "action");
        if (!(Objects.equals(arg_action, "leave") || Objects.equals(arg_action, "command"))) {
            client.player.sendMessage(getUsageHelp(), false);
            return 1;
        }

        String arg_command;
        try {
            arg_command = StringArgumentType.getString(context, "command");
        } catch (IllegalArgumentException e) {
            arg_command = "";
        }
        if (Objects.equals(arg_action, "command") && Objects.equals(arg_command, "")) {
            client.player.sendMessage(getUsageHelp(), false);
            return 1;
        }

        ConfigManager.getConfig().action_type = arg_action;
        ConfigManager.getConfig().command = arg_command;
        ConfigManager.saveConfig();

        Text message = Text.literal("Обновлены настройки:").formatted(Formatting.RED)
                .append(Text.literal("\n  – Действие при падении: ").formatted(Formatting.GOLD))
                .append(Text.literal(
                        Objects.equals(arg_action, "leave") ? "выход с сервера" : "выполнение команды")
                        .formatted(Formatting.YELLOW));
        if (Objects.equals(arg_action, "command")) {
            message = message.copy()
                    .append(Text.literal("\n  – Команда при падении: ").formatted(Formatting.GOLD))
                    .append(Text.literal(arg_command).formatted(Formatting.YELLOW));
        }
        client.player.sendMessage(message, false);

        return 1;
    }

    public static Text getUsageHelp() {
        Text message = Text.literal("Использование: ").formatted(Formatting.RED)
                .append(Text.literal("/f_config leave ").formatted(Formatting.GOLD))
                .append(Text.literal("или ").formatted(Formatting.RED))
                .append(Text.literal("/f_config command ").formatted(Formatting.GOLD))
                .append(Text.literal("<command>").formatted(Formatting.YELLOW));
        return message;
    }
}
