package converter;

import entity.seminar4.Gender1;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class Gender1Converter implements AttributeConverter<Gender1, String> {
    @Override
    public String convertToDatabaseColumn(Gender1 gender1) {
        return gender1.getCode();
    }

    @Override
    public Gender1 convertToEntityAttribute(String s) {
        return Gender1.fromCode(s);
    }
}
