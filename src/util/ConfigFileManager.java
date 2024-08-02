package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigFileManager extends FileManager{

    public ConfigFileManager(String file_name) {
        super(file_name);
        
    }

    public String getPropertyData(String property){
        Properties properties = new Properties();
        
        try (InputStream input = new FileInputStream(this.file_name)) {
            properties.load(input);

            return properties.getProperty(property);

        } catch (IOException e) {
            return null;
        }
        
    }
    


    
}
