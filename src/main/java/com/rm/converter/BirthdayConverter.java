package com.rm.converter;

import com.rm.entity.Birthday;

import javax.persistence.AttributeConverter;
import java.sql.Date;
import java.util.Optional;

//@Converter(autoApply = true) // аналог @Convert(converter = BirthdayConverter.class)
public class BirthdayConverter implements AttributeConverter<Birthday, Date> {

    @Override
    public Date convertToDatabaseColumn(Birthday attribute) {
        return Optional.ofNullable(attribute)
                .map(Birthday::birthday)
                .map(Date::valueOf)
                .orElse(null);
    }

    @Override
    public Birthday convertToEntityAttribute(Date dbData) {
        return Optional.ofNullable(dbData)
                .map(Date::toLocalDate)
                .map(Birthday::new)
                .orElse(null);
    }
}
