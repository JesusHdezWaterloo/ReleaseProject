package main;

import bundles.toast.TOAST;
import exceptions.ExceptionHandlerUtil;
import file.FILE;
import jackson.JACKSON;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import others.Pair;
import others.SemanticVersioningModel;

/**
 * 
 * @author Jesús Hernández Barrios (jhernandezb96@gmail.com)
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
            cfg = JACKSON.read(cfgJsonFile, Configuration.class);
        } catch (IOException e) {
            e.printStackTrace();
            TOAST.makeNotificationFAIL("Error en configuración, usando default.");
            cfg = new Configuration();
            cfg.saveToJSON();
        }
        desplegarRelease();
        TOAST.makeNotificationOK("Terminado el despliegue.");
        Thread.sleep(3 * 1000);
        System.exit(0);
    }

    private static void desplegarRelease() throws IOException, InterruptedException {
        //copia ficheros
        for (Pair<String> pair : cfg.getFiles()) {
            try {
                FILE.copy(pair.getA(), pair.getB());
            } catch (Exception e) {
                ExceptionHandlerUtil.saveException(errorJsonFile, e);
                TOAST.makeNotificationFAIL("Error copiando el fichero.");
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
            TOAST.makeNotificationFAIL("Error configurando versión.");
        }
    }

    private static void createUpdateApp() {
        try {
            String v = JACKSON.read(new File(cfg.getVersionHastaFile()), String.class);
            update = new File(new File(cfg.getUpdateFolder()), v);

            File app = new File(update, "App");
            app.mkdirs();

            //copia ficheros
            for (Pair<String> pair : cfg.getFiles()) {
                try {
                    FILE.copy(pair.getA(), app.getAbsolutePath());
                } catch (Exception e) {
                    ExceptionHandlerUtil.saveException(errorJsonFile, e);
                    TOAST.makeNotificationFAIL("Error copiando el fichero.");
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
            TOAST.makeNotificationFAIL("Error creando el update.");
        }
    }

    private static void createUpdate() {
        //copia carpetas
        for (Pair<String> pair : cfg.getUpdateFolders()) {
            try {
                FILE.copy(pair.getA(), cfg.getUpdateFolder());
            } catch (Exception e) {
                ExceptionHandlerUtil.saveException(errorJsonFile, e);
                TOAST.makeNotificationFAIL("Error copiando la carpeta.");
            }
        }

        //copia ficheros
        for (Pair<String> pair : cfg.getUpdateFiles()) {
            try {
                FILE.copy(pair.getA(), cfg.getUpdateFolder());
            } catch (Exception e) {
                ExceptionHandlerUtil.saveException(errorJsonFile, e);
                TOAST.makeNotificationFAIL("Error copiando el fichero.");
            }
        }
    }

}
