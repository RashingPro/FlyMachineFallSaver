package dev.rashing.fmfs.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File("config/fmfs.json");
    private static ConfigMain config;

    public static void loadConfig() {
        if (!CONFIG_FILE.getParentFile().exists()) {
            CONFIG_FILE.getParentFile().mkdirs();
        }

        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                config = GSON.fromJson(reader, ConfigMain.class);
            } catch (IOException e) {
                createDefaultConfig();
            }
        } else {
            createDefaultConfig();
        }
    }

    private static void createDefaultConfig() {
        config = new ConfigMain();
        saveConfig();
    }

    public static void saveConfig() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(config, writer);
        } catch (IOException ignored) {}
    }

    public static ConfigMain getConfig() {
        return config;
    }
}
