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
import lombok.Data;
import others.Pair;

/**
 *
 * @author Yo
 */
@Data
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

    private ArrayList<Pair<String>> updateFolders = new ArrayList<>();
    private ArrayList<Pair<String>> updateFiles = new ArrayList<>();

    public Configuration() {
        Pair file = new Pair("123", "123");
        Pair file2 = new Pair("123", "123");
        files.add(file);
        files.add(file2);
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

}
