package converter;

import entity.Gender;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class GenderConverter implements AttributeConverter<Gender, String> {
    @Override
    public String convertToDatabaseColumn(Gender gender) {
        return gender.getCode();
    }

    @Override
    public Gender convertToEntityAttribute(String s) {
        return Gender.fromCode(s);
    }
}
