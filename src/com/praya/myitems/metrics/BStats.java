// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.metrics;

import java.util.Map;
import java.util.concurrent.Callable;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import java.util.logging.Level;
import java.lang.reflect.InvocationTargetException;
import org.bukkit.plugin.RegisteredServiceProvider;
import java.lang.reflect.Method;
import org.bukkit.entity.Player;
import java.util.Collection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.TimerTask;
import java.util.Timer;
import java.util.Iterator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.Bukkit;
import java.io.IOException;
import java.util.UUID;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.plugin.java.JavaPlugin;

public class BStats
{
    public static final int B_STATS_VERSION = 1;
    private static final String URL = "https://bStats.org/submitData/bukkit";
    private static boolean logFailedRequests;
    private static String serverUUID;
    private final JavaPlugin plugin;
    private final List<CustomChart> charts;
    
    static {
        if (System.getProperty("bstats.relocatecheck") == null || !System.getProperty("bstats.relocatecheck").equals("false")) {
            final String defaultPackage = new String(new byte[] { 111, 114, 103, 46, 98, 115, 116, 97, 116, 115, 46, 98, 117, 107, 107, 105, 116 });
            final String examplePackage = new String(new byte[] { 121, 111, 117, 114, 46, 112, 97, 99, 107, 97, 103, 101 });
            if (BStats.class.getPackage().getName().equals(defaultPackage) || BStats.class.getPackage().getName().equals(examplePackage)) {
                throw new IllegalStateException("bStats Metrics class has not been relocated correctly!");
            }
        }
    }
    
    public BStats(final JavaPlugin plugin) {
        this.charts = new ArrayList<CustomChart>();
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null!");
        }
        this.plugin = plugin;
        final File bStatsFolder = new File(plugin.getDataFolder().getParentFile(), "bStats");
        final File configFile = new File(bStatsFolder, "config.yml");
        final YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        if (!config.isSet("serverUuid")) {
            config.addDefault("enabled", (Object)true);
            config.addDefault("serverUuid", (Object)UUID.randomUUID().toString());
            config.addDefault("logFailedRequests", (Object)false);
            config.options().header("bStats collects some data for plugin authors like how many servers are using their plugins.\nTo honor their work, you should not disable it.\nThis has nearly no effect on the server performance!\nCheck out https://bStats.org/ to learn more :)").copyDefaults(true);
            try {
                config.save(configFile);
            }
            catch (IOException ex) {}
        }
        BStats.serverUUID = config.getString("serverUuid");
        BStats.logFailedRequests = config.getBoolean("logFailedRequests", false);
        if (config.getBoolean("enabled", true)) {
            boolean found = false;
            for (final Class<?> service : Bukkit.getServicesManager().getKnownServices()) {
                try {
                    service.getField("B_STATS_VERSION");
                    found = true;
                    break;
                }
                catch (NoSuchFieldException ex2) {}
            }
            Bukkit.getServicesManager().register((Class)BStats.class, (Object)this, (Plugin)plugin, ServicePriority.Normal);
            if (!found) {
                this.startSubmitting();
            }
        }
    }
    
    public void addCustomChart(final CustomChart chart) {
        if (chart == null) {
            throw new IllegalArgumentException("Chart cannot be null!");
        }
        this.charts.add(chart);
    }
    
    private void startSubmitting() {
        final Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!BStats.this.plugin.isEnabled()) {
                    timer.cancel();
                    return;
                }
                Bukkit.getScheduler().runTask((Plugin)BStats.this.plugin, (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        BStats.this.submitData();
                    }
                });
            }
        }, 300000L, 1800000L);
    }
    
    public JSONObject getPluginData() {
        final JSONObject data = new JSONObject();
        final String pluginName = this.plugin.getDescription().getName();
        final String pluginVersion = this.plugin.getDescription().getVersion();
        data.put((Object)"pluginName", (Object)pluginName);
        data.put((Object)"pluginVersion", (Object)pluginVersion);
        final JSONArray customCharts = new JSONArray();
        for (final CustomChart customChart : this.charts) {
            final JSONObject chart = customChart.getRequestJsonObject();
            if (chart == null) {
                continue;
            }
            customCharts.add((Object)chart);
        }
        data.put((Object)"customCharts", (Object)customCharts);
        return data;
    }
    
    private JSONObject getServerData() {
        int playerAmount;
        try {
            final Method onlinePlayersMethod = Class.forName("org.bukkit.Server").getMethod("getOnlinePlayers", (Class<?>[])new Class[0]);
            playerAmount = (onlinePlayersMethod.getReturnType().equals(Collection.class) ? ((Collection)onlinePlayersMethod.invoke(Bukkit.getServer(), new Object[0])).size() : ((Player[])onlinePlayersMethod.invoke(Bukkit.getServer(), new Object[0])).length);
        }
        catch (Exception e) {
            playerAmount = Bukkit.getOnlinePlayers().size();
        }
        final int onlineMode = Bukkit.getOnlineMode() ? 1 : 0;
        String bukkitVersion = Bukkit.getVersion();
        bukkitVersion = bukkitVersion.substring(bukkitVersion.indexOf("MC: ") + 4, bukkitVersion.length() - 1);
        final String javaVersion = System.getProperty("java.version");
        final String osName = System.getProperty("os.name");
        final String osArch = System.getProperty("os.arch");
        final String osVersion = System.getProperty("os.version");
        final int coreCount = Runtime.getRuntime().availableProcessors();
        final JSONObject data = new JSONObject();
        data.put((Object)"serverUUID", (Object)BStats.serverUUID);
        data.put((Object)"playerAmount", (Object)playerAmount);
        data.put((Object)"onlineMode", (Object)onlineMode);
        data.put((Object)"bukkitVersion", (Object)bukkitVersion);
        data.put((Object)"javaVersion", (Object)javaVersion);
        data.put((Object)"osName", (Object)osName);
        data.put((Object)"osArch", (Object)osArch);
        data.put((Object)"osVersion", (Object)osVersion);
        data.put((Object)"coreCount", (Object)coreCount);
        return data;
    }
    
    private void submitData() {
        final JSONObject data = this.getServerData();
        final JSONArray pluginData = new JSONArray();
        for (final Class<?> service : Bukkit.getServicesManager().getKnownServices()) {
            try {
                service.getField("B_STATS_VERSION");
                for (final Object provider : Bukkit.getServicesManager().getRegistrations((Class)service)) {
                    try {
                        pluginData.add(((RegisteredServiceProvider)provider).getService().getMethod("getPluginData", (Class[])new Class[0]).invoke(((RegisteredServiceProvider)provider).getProvider(), new Object[0]));
                    }
                    catch (NullPointerException ex) {}
                    catch (NoSuchMethodException ex2) {}
                    catch (IllegalAccessException ex3) {}
                    catch (InvocationTargetException ex4) {}
                }
            }
            catch (NoSuchFieldException ex5) {}
        }
        data.put((Object)"plugins", (Object)pluginData);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sendData(data);
                }
                catch (Exception e) {
                    if (BStats.logFailedRequests) {
                        BStats.this.plugin.getLogger().log(Level.WARNING, "Could not submit plugin stats of " + BStats.this.plugin.getName(), e);
                    }
                }
            }
        }).start();
    }
    
    private static void sendData(final JSONObject data) throws Exception {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        }
        if (Bukkit.isPrimaryThread()) {
            throw new IllegalAccessException("This method must not be called from the main thread!");
        }
        final HttpsURLConnection connection = (HttpsURLConnection)new URL("https://bStats.org/submitData/bukkit").openConnection();
        final byte[] compressedData = compress(data.toString());
        connection.setRequestMethod("POST");
        connection.addRequestProperty("Accept", "application/json");
        connection.addRequestProperty("Connection", "close");
        connection.addRequestProperty("Content-Encoding", "gzip");
        connection.addRequestProperty("Content-Length", String.valueOf(compressedData.length));
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("User-Agent", "MC-Server/1");
        connection.setDoOutput(true);
        final DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.write(compressedData);
        outputStream.flush();
        outputStream.close();
        connection.getInputStream().close();
    }
    
    private static byte[] compress(final String str) throws IOException {
        if (str == null) {
            return null;
        }
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final GZIPOutputStream gzip = new GZIPOutputStream(outputStream);
        gzip.write(str.getBytes("UTF-8"));
        gzip.close();
        return outputStream.toByteArray();
    }
    
    public abstract static class CustomChart
    {
        final String chartId;
        
        CustomChart(final String chartId) {
            if (chartId == null || chartId.isEmpty()) {
                throw new IllegalArgumentException("ChartId cannot be null or empty!");
            }
            this.chartId = chartId;
        }
        
        private JSONObject getRequestJsonObject() {
            final JSONObject chart = new JSONObject();
            chart.put((Object)"chartId", (Object)this.chartId);
            try {
                final JSONObject data = this.getChartData();
                if (data == null) {
                    return null;
                }
                chart.put((Object)"data", (Object)data);
            }
            catch (Throwable t) {
                if (BStats.logFailedRequests) {
                    Bukkit.getLogger().log(Level.WARNING, "Failed to get data for custom chart with id " + this.chartId, t);
                }
                return null;
            }
            return chart;
        }
        
        protected abstract JSONObject getChartData() throws Exception;
    }
    
    public static class SimplePie extends CustomChart
    {
        private final Callable<String> callable;
        
        public SimplePie(final String chartId, final Callable<String> callable) {
            super(chartId);
            this.callable = callable;
        }
        
        @Override
        protected JSONObject getChartData() throws Exception {
            final JSONObject data = new JSONObject();
            final String value = this.callable.call();
            if (value == null || value.isEmpty()) {
                return null;
            }
            data.put((Object)"value", (Object)value);
            return data;
        }
    }
    
    public static class AdvancedPie extends CustomChart
    {
        private final Callable<Map<String, Integer>> callable;
        
        public AdvancedPie(final String chartId, final Callable<Map<String, Integer>> callable) {
            super(chartId);
            this.callable = callable;
        }
        
        @Override
        protected JSONObject getChartData() throws Exception {
            final JSONObject data = new JSONObject();
            final JSONObject values = new JSONObject();
            final Map<String, Integer> map = this.callable.call();
            if (map == null || map.isEmpty()) {
                return null;
            }
            boolean allSkipped = true;
            for (final Map.Entry<String, Integer> entry : map.entrySet()) {
                if (entry.getValue() == 0) {
                    continue;
                }
                allSkipped = false;
                values.put((Object)entry.getKey(), (Object)entry.getValue());
            }
            if (allSkipped) {
                return null;
            }
            data.put((Object)"values", (Object)values);
            return data;
        }
    }
    
    public static class DrilldownPie extends CustomChart
    {
        private final Callable<Map<String, Map<String, Integer>>> callable;
        
        public DrilldownPie(final String chartId, final Callable<Map<String, Map<String, Integer>>> callable) {
            super(chartId);
            this.callable = callable;
        }
        
        public JSONObject getChartData() throws Exception {
            final JSONObject data = new JSONObject();
            final JSONObject values = new JSONObject();
            final Map<String, Map<String, Integer>> map = this.callable.call();
            if (map == null || map.isEmpty()) {
                return null;
            }
            boolean reallyAllSkipped = true;
            for (final Map.Entry<String, Map<String, Integer>> entryValues : map.entrySet()) {
                final JSONObject value = new JSONObject();
                boolean allSkipped = true;
                for (final Map.Entry<String, Integer> valueEntry : map.get(entryValues.getKey()).entrySet()) {
                    value.put((Object)valueEntry.getKey(), (Object)valueEntry.getValue());
                    allSkipped = false;
                }
                if (!allSkipped) {
                    reallyAllSkipped = false;
                    values.put((Object)entryValues.getKey(), (Object)value);
                }
            }
            if (reallyAllSkipped) {
                return null;
            }
            data.put((Object)"values", (Object)values);
            return data;
        }
    }
    
    public static class SingleLineChart extends CustomChart
    {
        private final Callable<Integer> callable;
        
        public SingleLineChart(final String chartId, final Callable<Integer> callable) {
            super(chartId);
            this.callable = callable;
        }
        
        @Override
        protected JSONObject getChartData() throws Exception {
            final JSONObject data = new JSONObject();
            final int value = this.callable.call();
            if (value == 0) {
                return null;
            }
            data.put((Object)"value", (Object)value);
            return data;
        }
    }
    
    public static class MultiLineChart extends CustomChart
    {
        private final Callable<Map<String, Integer>> callable;
        
        public MultiLineChart(final String chartId, final Callable<Map<String, Integer>> callable) {
            super(chartId);
            this.callable = callable;
        }
        
        @Override
        protected JSONObject getChartData() throws Exception {
            final JSONObject data = new JSONObject();
            final JSONObject values = new JSONObject();
            final Map<String, Integer> map = this.callable.call();
            if (map == null || map.isEmpty()) {
                return null;
            }
            boolean allSkipped = true;
            for (final Map.Entry<String, Integer> entry : map.entrySet()) {
                if (entry.getValue() == 0) {
                    continue;
                }
                allSkipped = false;
                values.put((Object)entry.getKey(), (Object)entry.getValue());
            }
            if (allSkipped) {
                return null;
            }
            data.put((Object)"values", (Object)values);
            return data;
        }
    }
    
    public static class SimpleBarChart extends CustomChart
    {
        private final Callable<Map<String, Integer>> callable;
        
        public SimpleBarChart(final String chartId, final Callable<Map<String, Integer>> callable) {
            super(chartId);
            this.callable = callable;
        }
        
        @Override
        protected JSONObject getChartData() throws Exception {
            final JSONObject data = new JSONObject();
            final JSONObject values = new JSONObject();
            final Map<String, Integer> map = this.callable.call();
            if (map == null || map.isEmpty()) {
                return null;
            }
            for (final Map.Entry<String, Integer> entry : map.entrySet()) {
                final JSONArray categoryValues = new JSONArray();
                categoryValues.add((Object)entry.getValue());
                values.put((Object)entry.getKey(), (Object)categoryValues);
            }
            data.put((Object)"values", (Object)values);
            return data;
        }
    }
    
    public static class AdvancedBarChart extends CustomChart
    {
        private final Callable<Map<String, int[]>> callable;
        
        public AdvancedBarChart(final String chartId, final Callable<Map<String, int[]>> callable) {
            super(chartId);
            this.callable = callable;
        }
        
        @Override
        protected JSONObject getChartData() throws Exception {
            final JSONObject data = new JSONObject();
            final JSONObject values = new JSONObject();
            final Map<String, int[]> map = this.callable.call();
            if (map == null || map.isEmpty()) {
                return null;
            }
            boolean allSkipped = true;
            for (final Map.Entry<String, int[]> entry : map.entrySet()) {
                if (entry.getValue().length == 0) {
                    continue;
                }
                allSkipped = false;
                final JSONArray categoryValues = new JSONArray();
                int[] array;
                for (int length = (array = entry.getValue()).length, i = 0; i < length; ++i) {
                    final int categoryValue = array[i];
                    categoryValues.add((Object)categoryValue);
                }
                values.put((Object)entry.getKey(), (Object)categoryValues);
            }
            if (allSkipped) {
                return null;
            }
            data.put((Object)"values", (Object)values);
            return data;
        }
    }
}
