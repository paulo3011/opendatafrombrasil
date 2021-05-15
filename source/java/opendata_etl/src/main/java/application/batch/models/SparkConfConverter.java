package application.batch.models;

import com.beust.jcommander.IStringConverter;

import java.util.Properties;

public class SparkConfConverter implements IStringConverter<Properties> {
    @Override
    public Properties convert(String value) {
        return convertFromString(value);
    }

    public static Properties convertFromString(String values)
    {
        if(values == null || values.isEmpty())
            return null;

        Properties properties = new Properties();

        String[] key_values = values.split(",");

        if(key_values.length == 0)
            return  null;

        for (String param : key_values) {
            String[] key_value = param.split("=");
            properties.put(key_value[0], key_value[1]);
        }

        return properties;
    }
}
