/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import others.SemanticVersioningModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import components.dialog.notification.types._NotificationDialogActionFAIL;
import components.dialog.notification.types._NotificationDialogActionOK;
import file.FILE;
import jackson.JACKSON;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import others.ExceptionHandlerUtil;

/**
 *
 * @author Yo
 */
public class Main {

    public static final File cfgJsonFile = new File(new File("").getAbsolutePath() + File.separator + "cfg_release_project.json");
    public static final File errorJsonFile = new File(new File("").getAbsolutePath() + File.separator + "error_release_library.json");
    public static Configuration cfg;

    private static File update;

    public static void main(String[] args) throws InterruptedException, IOException {
        //cfg = new Configuration();
        //cfg.saveToJSON();

        try {
            cfg = new ObjectMapper().readValue(cfgJsonFile, Configuration.class);
        } catch (IOException e) {
            e.printStackTrace();
            new _NotificationDialogActionFAIL("Error en configuración, usando default.");
            cfg = new Configuration();
            cfg.saveToJSON();
        }
        desplegarRelease();

        new _NotificationDialogActionOK("Terminado el despliegue.");
        Thread.sleep(3 * 1000);
        System.exit(0);
    }

    private static void desplegarRelease() throws IOException, InterruptedException {
        //copia carpetas
        for (Pair pair : cfg.getFolders()) {
            try {
                FILE.copy(pair.getDesde(), pair.getHasta());
            } catch (Exception e) {
                ExceptionHandlerUtil.saveException(errorJsonFile, e);
                new _NotificationDialogActionFAIL("Error copiando la carpeta.");
            }
        }

        //copia ficheros
        for (Pair pair : cfg.getFiles()) {
            try {
                FILE.copy(pair.getDesde(), pair.getHasta());
            } catch (Exception e) {
                ExceptionHandlerUtil.saveException(errorJsonFile, e);
                new _NotificationDialogActionFAIL("Error copiando el fichero.");
            }
        }

        //compile main
        compileJar(cfg.getMainJarXMLConfigFile());

        copyVersion();

        if (cfg.isDoUpdate()) {
            createUpdateApp();

            createUpdate();
        }
    }

    private static void compileJar(String config) throws IOException, InterruptedException {
        String launchJar = "\"" + cfg.getLaunch4jJar() + "\"";
        String configXML = "\"" + config + "\"";
        String cmd = "start /B java -jar " + launchJar + " " + configXML;
        Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", cmd}).waitFor();
    }

    /**
     * Coge todos los tags de versiones, los ordena y devuelve el mayor.
     *
     * @return
     */
    private static String getLatestVersion() {
        String versions[] = new File(cfg.getGitVersionFolder()).list();
        SemanticVersioningModel c[] = new SemanticVersioningModel[versions.length];
        for (int i = 0; i < c.length; i++) {
            c[i] = new SemanticVersioningModel(versions[i]);
        }

        Arrays.sort(c);

        if (c.length == 0) {
            return "0.0.0";
        }
        return c[c.length - 1].toString();
    }

    private static void copyVersion() {
        try {
            //configura version
            String v = getLatestVersion();
            File verDesde = new File(cfg.getVersionDesdeFile());
            JACKSON.write(verDesde, v);//escribe la ultima veersion

            FILE.copy(verDesde.getAbsolutePath(), cfg.getVersionHastaFile());//la copia al nuevo lugar
        } catch (Exception e) {
            ExceptionHandlerUtil.saveException(errorJsonFile, e);
            new _NotificationDialogActionFAIL("Error configurando versión.");
        }
    }

    private static void createUpdateApp() {
        try {
            String v = JACKSON.read(new File(cfg.getVersionHastaFile()), String.class);
            update = new File(new File(cfg.getUpdateFolder()), v);

            File app = new File(update, "App");
            app.mkdirs();

            //copia carpetas
            for (Pair pair : cfg.getFolders()) {
                try {
                    FILE.copy(pair.getDesde(), app.getAbsolutePath());
                } catch (Exception e) {
                    ExceptionHandlerUtil.saveException(errorJsonFile, e);
                    new _NotificationDialogActionFAIL("Error copiando la carpeta.");
                }
            }

            //copia ficheros
            for (Pair pair : cfg.getFiles()) {
                try {
                    FILE.copy(pair.getDesde(), app.getAbsolutePath());
                } catch (Exception e) {
                    ExceptionHandlerUtil.saveException(errorJsonFile, e);
                    new _NotificationDialogActionFAIL("Error copiando el fichero.");
                }
            }

            //exe del update
            if (!new File(cfg.getCompiledEXE()).exists()) {//si no existe, es porque no se a terminado de construir, espero, pero no mucho
                Thread.sleep(5 * 1000);
            }
            FILE.copy(cfg.getCompiledEXE(), app.getAbsolutePath());

            //queries
            FILE.copy(cfg.getUpdateProjectSQLFile(), update.getAbsolutePath());

        } catch (Exception e) {
            ExceptionHandlerUtil.saveException(errorJsonFile, e);
            new _NotificationDialogActionFAIL("Error creando el update.");
        }
    }

    private static void createUpdate() {
        //copia carpetas
        for (Pair pair : cfg.getUpdateFolders()) {
            try {
                FILE.copy(pair.getDesde(), cfg.getUpdateFolder());
            } catch (Exception e) {
                ExceptionHandlerUtil.saveException(errorJsonFile, e);
                new _NotificationDialogActionFAIL("Error copiando la carpeta.");
            }
        }

        //copia ficheros
        for (Pair pair : cfg.getUpdateFiles()) {
            try {
                FILE.copy(pair.getDesde(), cfg.getUpdateFolder());
            } catch (Exception e) {
                ExceptionHandlerUtil.saveException(errorJsonFile, e);
                new _NotificationDialogActionFAIL("Error copiando el fichero.");
            }
        }
    }

}
