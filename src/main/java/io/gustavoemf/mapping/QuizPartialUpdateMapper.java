package io.gustavoemf.mapping;

import io.gustavoemf.domain.Quiz;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI, nullValuePropertyMappingStrategy = IGNORE)
public interface QuizPartialUpdateMapper {

    void mapPartialUpdate(Quiz input, @MappingTarget Quiz target);
}
