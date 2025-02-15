package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigFileManager extends FileManager{

    public ConfigFileManager(String file_path) {
        super(file_path);
    }

    public String getPropertyData(String property){
        Properties properties = new Properties();
        
        try (InputStream input = new FileInputStream(this.file_path)) {
            properties.load(input); //Load .properties file

            return properties.getProperty(property);

        } catch (IOException e) {
            return null;
        }
        
    }
    


    
}
