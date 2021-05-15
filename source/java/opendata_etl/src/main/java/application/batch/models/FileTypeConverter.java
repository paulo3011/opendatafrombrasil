package application.batch.models;

import application.batch.enums.FileType;
import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.converters.EnumConverter;

public class FileTypeConverter implements IStringConverter<FileType> {
    private static EnumConverter<FileType> _converter = new EnumConverter<>("", FileType.class);
    @Override
    public FileType convert(String value) {
        if(value == null || value.isEmpty())
            return null;
        return _converter.convert(value);
    }
}
