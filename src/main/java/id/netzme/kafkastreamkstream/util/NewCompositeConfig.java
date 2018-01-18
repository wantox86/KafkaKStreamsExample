package id.netzme.kafkastreamkstream.util;

import org.apache.commons.configuration.CompositeConfiguration;

public class NewCompositeConfig extends CompositeConfiguration {

    public String getPropertyString(String key){
        if(super.getProperty(key) == null){
            return "";
        }
        return (String) super.getProperty(key);
    }

    public String getPropertyString(String key, String defaultValue){
        if(getPropertyString(key) == ""){
            return defaultValue;
        }
        return getPropertyString(key);
    }

    public int getPropertyInt(String key){
        if(super.getProperty(key) == null){
            return -1;
        }
        return Integer.valueOf(super.getProperty(key).toString());
    }

    public int getPropertyInt(String key, int defaultValue){
        if(getPropertyInt(key) == -1){
            return defaultValue;
        }
        return getPropertyInt(key);
    }
}
