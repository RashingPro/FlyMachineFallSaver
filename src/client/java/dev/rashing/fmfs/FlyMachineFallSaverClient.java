package dev.rashing.fmfs;

import com.mojang.brigadier.arguments.StringArgumentType;
import dev.rashing.fmfs.command.client.CommandConfig;

import dev.rashing.fmfs.command.client.CommandConfigActionSuggestionProvider;
import dev.rashing.fmfs.config.ConfigManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlyMachineFallSaverClient implements ClientModInitializer {
	public static final String MOD_ID = "fmfs";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	@Override
	public void onInitializeClient() {
		LOGGER.info("test");
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("config")
				.then(ClientCommandManager.argument("action", StringArgumentType.word())
						.suggests(new CommandConfigActionSuggestionProvider())
						.executes(commandContext -> new CommandConfig().run(commandContext))
						.then(ClientCommandManager.argument("command", StringArgumentType.string())
								.executes(commandContext -> new CommandConfig().run(commandContext))
						)
				)
				.executes(commandContext -> {
					Text message = Text.literal("Использование: ").setStyle(Style.EMPTY.withColor(Formatting.RED))
							.append(Text.literal("/config leave ").setStyle(Style.EMPTY.withColor(Formatting.GOLD)))
							.append(Text.literal("или ").setStyle(Style.EMPTY.withColor(Formatting.RED)))
							.append(Text.literal("/config command ").setStyle(Style.EMPTY.withColor(Formatting.GOLD)))
							.append(Text.literal("<command>").setStyle(Style.EMPTY.withColor(Formatting.YELLOW)));
					commandContext.getSource().sendFeedback(message);
                    return 1;
                })
        ));

		ConfigManager.loadConfig();
	}
}
