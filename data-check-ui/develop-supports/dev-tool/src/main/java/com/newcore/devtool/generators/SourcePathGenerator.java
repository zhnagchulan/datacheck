package com.newcore.devtool.generators;

import com.newcore.devtool.utils.FileUtils;
import com.newcore.devtool.utils.ConfigUtils;
import org.springframework.util.Assert;

/**
 * Created by maeag_000 on 2016/1/20.
 */
public class SourcePathGenerator {

    private static final String rootPath = "%s/%s-%s/src/main/java/";

    private static final String[] serviceAppSrcPaths = new String[]{"com/halo/%s/dao/api/",
            "com/halo/%s/dao/impl/",
            "com/halo/%s/service/impl/",
            "com/halo/%s/service/business/",
            "com/halo/%s/web/"};

    private static final String[] batchAppSrcPaths = new String[]{"com/halo/%s/dao/api/",
            "com/halo/%s/dao/impl/",
            "com/halo/%s/service/impl/",
            "com/halo/%s/service/business/",
            "com/halo/%s/web/"};

    private static final String[] webAppSrcPaths = new String[]{
            "com/halo/%s/web/"};

    private static final String[] serviceApiSrcPaths = new String[]{
            "com/halo/%s/service/api/"};

    private static final String[] modelsSrcPaths = new String[]{
            "com/halo/%s/models/service/bo",
            "com/halo/%s/models/web/vo"};


    public static void main(String[] args) {
        String[] projectNames = ConfigUtils.getProperty("common.projects").split(",");
        Assert.notEmpty(projectNames);
        for (String projectName : projectNames) {
            generateServiceAppFolder(projectName);
            if (!"common".equals(projectName))
                generateBatchAppFolder(projectName);
            generateWebAppFolder(projectName);
            generateServiceApiFolder(projectName);
            generateModelsFolder(projectName);
        }
    }


    public static void generateServiceAppFolder(String projectName) {
        String root = String.format(rootPath, ConfigUtils.getTargetPath(projectName), projectName, "service-app");
        for (String path : serviceAppSrcPaths) {
            FileUtils.createDirs(root + String.format(path, projectName));
        }
    }

    public static void generateBatchAppFolder(String projectName) {
        String root = String.format(rootPath, ConfigUtils.getTargetPath(projectName), projectName, "batch-app");
        for (String path : batchAppSrcPaths) {
            FileUtils.createDirs(root + String.format(path, projectName));
        }
    }

    public static void generateWebAppFolder(String projectName) {
        String root = String.format(rootPath, ConfigUtils.getTargetPath(projectName), projectName, "web-app");
        for (String path : webAppSrcPaths) {
            FileUtils.createDirs(root + String.format(path, projectName));
        }
    }

    public static void generateServiceApiFolder(String projectName) {
        String root = String.format(rootPath, ConfigUtils.getTargetPath(projectName), projectName, "service-api");
        for (String path : serviceApiSrcPaths) {
            FileUtils.createDirs(root + String.format(path, projectName));
        }
    }

    public static void generateModelsFolder(String projectName) {
        String root = String.format(rootPath, ConfigUtils.getTargetPath(projectName), projectName, "model");
        for (String path : modelsSrcPaths) {
            FileUtils.createDirs(root + String.format(path, projectName));
        }
    }
}
