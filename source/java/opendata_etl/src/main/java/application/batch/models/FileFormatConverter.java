package application.batch.models;

import application.batch.enums.FileFormat;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.converters.EnumConverter;

public class FileFormatConverter implements IStringConverter<FileFormat> {
    private static EnumConverter<FileFormat> _converter = new EnumConverter<>("", FileFormat.class);
    @Override
    public FileFormat convert(String value) {
        if(value == null || value.isEmpty())
            return null;
        return _converter.convert(value);
    }
}
