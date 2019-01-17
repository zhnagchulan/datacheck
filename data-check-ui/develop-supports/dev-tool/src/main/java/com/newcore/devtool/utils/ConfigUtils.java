package com.newcore.devtool.utils;

import org.springframework.util.Assert;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by maeag_000 on 2016/1/18.
 */
public class ConfigUtils {

    private static final String PROPERTIES_FILE = "config.properties";

    private static Properties properties = new Properties();

    static {
        InputStream is = null;
        try {
            Enumeration<URL> urls = ConfigUtils.class.getClassLoader()
                    .getResources(PROPERTIES_FILE);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                URLConnection con = url.openConnection();
                con.setUseCaches(false);
                is = con.getInputStream();
                properties.load(is);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                is.close();
            } catch (Exception e) {
            }
        }
    }

    public static String getProperty(String name) {
        Assert.notEmpty(properties, "找不到 " + PROPERTIES_FILE + " 文件");
        return properties.getProperty(name);
    }

    public static String getTargetPath(String projectName) {
        return String.format(ConfigUtils.getProperty("common.target.rootpath"), projectName, projectName);
    }

    public static String getDuplicatePath(String fileType) {
        return ConfigUtils.getProperty("duplicate." + fileType + ".path");
    }
}
