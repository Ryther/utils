package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edoardo Zanoni
 */
public class FileUtils {
    
    private static final String MAIN_PATH = System.getProperty("user.dir")+"/data/";
    private static final String STANDARD_FILE = "city_data.dat";
    
    public static boolean salvataggioDati(byte[] dato, String fileName) {
        
        RandomAccessFile writer = null;
        try {
            writer = new RandomAccessFile(new StringBuilder(MAIN_PATH).append(fileName).toString(), "rwd");
            writer.seek(0);
            writer.write(dato);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return true;
    }
    
    public static boolean salvataggioDati(byte[] dato) {
        
        return FileUtils.salvataggioDati(dato, STANDARD_FILE);
    }
    
    public static String[] caricamentoDati(String fileName) {
        
        RandomAccessFile reader = null;
        byte[] b = null;
        try {
            reader = new RandomAccessFile(new StringBuilder(MAIN_PATH).append(fileName).toString(), "rwd");
            b = new byte[(int)reader.length()];
            reader.seek(0);
            reader.read(b);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
        return Arrays.toString(b).split("\n");
    }
    
    public static String[] caricamentoDati() {
        
        return FileUtils.caricamentoDati(STANDARD_FILE);
    }
}