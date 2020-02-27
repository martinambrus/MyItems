// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.config.plugin;

import com.praya.myitems.manager.plugin.PlaceholderManager;
import java.util.ArrayList;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.List;
import java.io.File;
import com.praya.myitems.manager.plugin.DataManager;
import com.praya.myitems.manager.plugin.PluginManager;
import java.util.regex.Pattern;
import org.bukkit.plugin.java.JavaPlugin;
import com.praya.agarthalib.utility.FileUtil;
import core.praya.agarthalib.builder.message.MessageBuild;
import java.util.Iterator;
import java.util.Collection;
import com.praya.myitems.MyItems;
import core.praya.agarthalib.builder.main.LanguageBuild;
import java.util.HashMap;
import com.praya.myitems.builder.handler.HandlerConfig;

public class LanguageConfig extends HandlerConfig
{
    private final HashMap<String, LanguageBuild> mapLanguage;
    
    public LanguageConfig(final MyItems plugin) {
        super(plugin);
        this.mapLanguage = new HashMap<String, LanguageBuild>();
        this.setup();
    }
    
    public final Collection<String> getLanguageIDs() {
        return this.mapLanguage.keySet();
    }
    
    public final Collection<LanguageBuild> getLanguageBuilds() {
        return this.mapLanguage.values();
    }
    
    public final LanguageBuild getLanguageBuild(final String id) {
        if (id != null) {
            for (final String key : this.getLanguageIDs()) {
                if (key.equalsIgnoreCase(id)) {
                    return this.mapLanguage.get(key);
                }
            }
        }
        return null;
    }
    
    public final LanguageBuild getLanguage(final String id) {
        if (id != null) {
            final String[] parts = id.split("_");
            int size;
            for (int length = size = parts.length; size > 0; --size) {
                final StringBuilder builder = new StringBuilder();
                for (int index = 0; index < size; ++index) {
                    final String component = parts[index];
                    builder.append(component);
                    if (index != size - 1) {
                        builder.append("_");
                    }
                    final String identifier = builder.toString();
                    final LanguageBuild languageBuild = this.getLanguageBuild(identifier);
                    if (languageBuild != null) {
                        return languageBuild;
                    }
                }
            }
        }
        return this.getLanguageBuild("en");
    }
    
    public final MessageBuild getMessageBuild(final String id, final String key) {
        if (id != null && key != null) {
            final LanguageBuild languageBuild = this.getLanguageBuild(id);
            if (languageBuild != null) {
                return languageBuild.getMessage(key);
            }
        }
        return new MessageBuild();
    }
    
    public final MessageBuild getMessage(final String id, final String key) {
        if (id != null) {
            final String[] parts = id.split("_");
            int size;
            for (int length = size = parts.length; size > 0; --size) {
                final StringBuilder builder = new StringBuilder();
                for (int index = 0; index < size; ++index) {
                    final String component = parts[index];
                    builder.append(component);
                    if (index != size - 1) {
                        builder.append("_");
                    }
                    final String identifier = builder.toString();
                    final LanguageBuild languageBuild = this.getLanguageBuild(identifier);
                    if (languageBuild != null) {
                        final MessageBuild message = languageBuild.getMessage(key);
                        if (message.isSet()) {
                            return message;
                        }
                    }
                }
            }
        }
        return this.getMessageBuild("en", key);
    }
    
    public final void setup() {
        this.moveOldFile();
        this.reset();
        this.loadConfig();
    }
    
    private final void reset() {
        this.mapLanguage.clear();
    }
    
    private final void loadConfig() {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final DataManager dataManager = pluginManager.getDataManager();
        final String pathFolder = dataManager.getPath("Path_Folder_Language");
        final File folder = FileUtil.getFile((JavaPlugin)this.plugin, pathFolder);
        final List<String> listPath = dataManager.getListPath("Path_File_Language");
        for (final String pathFile : listPath) {
            final File file = FileUtil.getFile((JavaPlugin)this.plugin, pathFile);
            final String name = file.getName().toLowerCase();
            final String id = name.split(Pattern.quote("."))[0];
            final String locale = id.startsWith("lang_") ? id.replaceFirst("lang_", "") : "en";
            if (!file.exists()) {
                FileUtil.saveResource((JavaPlugin)this.plugin, pathFile);
            }
            final FileConfiguration config = FileUtil.getFileConfigurationResource((JavaPlugin)this.plugin, pathFile);
            final LanguageBuild language = this.loadLanguage(locale, config);
            this.mapLanguage.put(locale, language);
        }
        File[] listFiles;
        for (int length = (listFiles = folder.listFiles()).length, i = 0; i < length; ++i) {
            final File file2 = listFiles[i];
            final String name2 = file2.getName().toLowerCase();
            final String id2 = name2.split(Pattern.quote("."))[0];
            final String locale2 = id2.startsWith("lang_") ? id2.replaceFirst("lang_", "") : "en";
            final FileConfiguration config2 = FileUtil.getFileConfiguration(file2);
            final LanguageBuild language2 = this.loadLanguage(locale2, config2);
            final LanguageBuild localeLang = this.getLanguage(locale2);
            if (localeLang != null) {
                localeLang.mergeLanguage(language2);
            }
            else {
                this.mapLanguage.put(id2, language2);
            }
        }
    }
    
    private final LanguageBuild loadLanguage(final String locale, final FileConfiguration config) {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final PlaceholderManager placeholderManager = pluginManager.getPlaceholderManager();
        final HashMap<String, MessageBuild> mapLanguage = new HashMap<String, MessageBuild>();
        for (final String path : config.getKeys(true)) {
            final String key = path.replace(".", "_");
            if (config.isString(path)) {
                final String text = config.getString(path);
                final List<String> list = new ArrayList<String>();
                list.add(text);
                final List<String> listPlaceholder = placeholderManager.localPlaceholder(list);
                final MessageBuild messages = new MessageBuild((List)listPlaceholder);
                mapLanguage.put(key, messages);
            }
            else {
                if (!config.isList(path)) {
                    continue;
                }
                final List<String> list2 = (List<String>)config.getStringList(path);
                final List<String> listPlaceholder2 = placeholderManager.localPlaceholder(list2);
                final MessageBuild messages2 = new MessageBuild((List)listPlaceholder2);
                mapLanguage.put(key, messages2);
            }
        }
        return new LanguageBuild(locale, (HashMap)mapLanguage);
    }
    
    private final void moveOldFile() {
        final String pathSource = "language.yml";
        final String pathTarget = "Language/lang_en.yml";
        final File fileSource = FileUtil.getFile((JavaPlugin)this.plugin, "language.yml");
        final File fileTarget = FileUtil.getFile((JavaPlugin)this.plugin, "Language/lang_en.yml");
        if (fileSource.exists()) {
            FileUtil.moveFileSilent(fileSource, fileTarget);
        }
    }
}
