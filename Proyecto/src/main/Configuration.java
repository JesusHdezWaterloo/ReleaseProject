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

    private ArrayList<Pair> folders = new ArrayList<>();
    private ArrayList<Pair> files = new ArrayList<>();

    private String gitVersionFolder = "abc\\.git\\refs\\tags";

    private String versionDesdeFile = "version.json";
    private String versionHastaFile = "version.json";

    private String updateFolder = "123\\App\\updates";

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
        folders.add(folder);
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

    public ArrayList<Pair> getFolders() {
        return folders;
    }

    public void setFolders(ArrayList<Pair> folders) {
        this.folders = folders;
    }

    public ArrayList<Pair> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<Pair> files) {
        this.files = files;
    }

}
