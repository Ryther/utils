package utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
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
    
    /**
     * This method let the user saves to a file a serializable object to access from later.
     * It is saved to the main path indicated in this class.
     * 
     * @param object the object to save.
     * @param fileName the name of the file.
     * @return true if object is written correctly, false otherwise.
     * @throws ClassCastException
     *      if {@code !(object instanceof Serializable)}
     *      {@link Serializable}.
     */
    public static boolean writeObjectToFile(Object object, String fileName) {
        
        if (!(object instanceof Serializable)) {
            
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE,
                    "L'oggetto deve implementare l'interfaccia Serializable.");
            throw new ClassCastException();
        }
        
        try (
            OutputStream file = new FileOutputStream(MAIN_PATH + fileName);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output =new ObjectOutputStream(buffer);
        ){
            output.writeObject(object);
        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return true;
    }
    
    /**
     * This method let the user saves to a file a serializable object to access from later.
     * It is saved to the main path and file name indicated in this class.
     * 
     * @param object the object to save.
     * @return true if object is written correctly, false otherwise.
     * @throws ClassCastException
     *      if {@code !(object instanceof Serializable)}
     *      {@link Serializable}.
     */
    public static boolean writeObjectToFile(Object object) {
        
        return FileUtils.writeObjectToFile(object, STANDARD_FILE);
    }
    
    /**
     * This method let the user reads from a file a serializable object previously saved.
     * It is read from the main path indicated in this class.
     * 
     * @param fileName the name of the file to read.
     * @return the object stored in the file.
     */
    public static Object readObjectFromFile(String fileName) {
        
        Object object = null;
        try (
            InputStream file = new FileInputStream(MAIN_PATH + fileName);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input =new ObjectInputStream(buffer);
        ){
            object = input.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        return object;
    }
    
    /**
     * This method let the user reads from a file a serializable object previously saved.
     * It is read from the main path and file name indicated in this class.
     * 
     * @return the object stored in the file.
     */
    public static Object readObjectFromFile() {
        
        return FileUtils.readObjectFromFile(STANDARD_FILE);
    }
}