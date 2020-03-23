/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import jackson.JACKSON;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Yo
 */
public class Configuration implements Serializable {

    private ArrayList<Pair> files = new ArrayList<>();

    private String gitVersionFolder = "abc\\.git\\refs\\tags";

    private String versionDesdeFile = "version.json";
    private String versionHastaFile = "version.json";

    private boolean doUpdate = true;

    private String updateFolder = "123\\App\\updates";

    private String updateProjectLibFolder = "C:\\Users\\Yo\\Documents\\GIT Projects\\UpdateMeca\\App\\updates\\Proyecto\\dist\\lib";
    private String updateProjectConfigFile = "C:\\Users\\Yo\\Documents\\GIT Projects\\UpdateMeca\\App\\updates\\Proyecto\\cfg.json";
    private String updateProjectSQLFile = "C:\\Users\\Yo\\Documents\\GIT Projects\\Meca\\Proyecto\\db_update_queries.txt";
    private String launch4jJar = "C:\\Users\\Yo\\Documents\\GIT Projects\\Meca\\App\\configuration.xml";
    private String mainJarXMLConfigFile = "C:\\Users\\Yo\\Documents\\GIT Projects\\Meca\\App\\configuration.xml";
    private String compiledEXE = "C:\\Users\\Yo\\Documents\\GIT Projects\\Meca\\App\\Meca.exe";

    private ArrayList<Pair> updateFolders = new ArrayList<>();
    private ArrayList<Pair> updateFiles = new ArrayList<>();

    public Configuration() {
        Pair file = new Pair();
        file.setDesde("123");
        file.setHasta("123");
        Pair file2 = new Pair();
        file2.setDesde("123");
        file2.setHasta("123");
        files.add(file);
        files.add(file2);

        Pair folder = new Pair();
        folder.setDesde("123");
        folder.setHasta("123");
    }

    public boolean saveToJSON() {
        try {
            JACKSON.write(Main.cfgJsonFile, new Configuration());
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean isDoUpdate() {
        return doUpdate;
    }

    public void setDoUpdate(boolean doUpdate) {
        this.doUpdate = doUpdate;
    }

    public ArrayList<Pair> getUpdateFolders() {
        return updateFolders;
    }

    public void setUpdateFolders(ArrayList<Pair> updateFolders) {
        this.updateFolders = updateFolders;
    }

    public ArrayList<Pair> getUpdateFiles() {
        return updateFiles;
    }

    public void setUpdateFiles(ArrayList<Pair> updateFiles) {
        this.updateFiles = updateFiles;
    }

    public String getCompiledEXE() {
        return compiledEXE;
    }

    public void setCompiledEXE(String compiledEXE) {
        this.compiledEXE = compiledEXE;
    }

    public String getMainJarXMLConfigFile() {
        return mainJarXMLConfigFile;
    }

    public void setMainJarXMLConfigFile(String mainJarXMLConfigFile) {
        this.mainJarXMLConfigFile = mainJarXMLConfigFile;
    }

    public String getLaunch4jJar() {
        return launch4jJar;
    }

    public void setLaunch4jJar(String launch4jJar) {
        this.launch4jJar = launch4jJar;
    }

    public String getUpdateProjectSQLFile() {
        return updateProjectSQLFile;
    }

    public void setUpdateProjectSQLFile(String updateProjectSQLFile) {
        this.updateProjectSQLFile = updateProjectSQLFile;
    }

    public String getUpdateProjectLibFolder() {
        return updateProjectLibFolder;
    }

    public void setUpdateProjectLibFolder(String updateProjectLibFolder) {
        this.updateProjectLibFolder = updateProjectLibFolder;
    }

    public String getUpdateProjectConfigFile() {
        return updateProjectConfigFile;
    }

    public void setUpdateProjectConfigFile(String updateProjectConfigFile) {
        this.updateProjectConfigFile = updateProjectConfigFile;
    }

    public String getUpdateFolder() {
        return updateFolder;
    }

    public void setUpdateFolder(String updateFolder) {
        this.updateFolder = updateFolder;
    }

    public String getVersionDesdeFile() {
        return versionDesdeFile;
    }

    public void setVersionDesdeFile(String versionDesdeFile) {
        this.versionDesdeFile = versionDesdeFile;
    }

    public String getVersionHastaFile() {
        return versionHastaFile;
    }

    public void setVersionHastaFile(String versionHastaFile) {
        this.versionHastaFile = versionHastaFile;
    }

    public String getGitVersionFolder() {
        return gitVersionFolder;
    }

    public void setGitVersionFolder(String gitVersionFolder) {
        this.gitVersionFolder = gitVersionFolder;
    }

    public ArrayList<Pair> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<Pair> files) {
        this.files = files;
    }

}
