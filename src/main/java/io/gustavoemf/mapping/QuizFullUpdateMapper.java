package io.gustavoemf.mapping;

import io.gustavoemf.domain.Quiz;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = ComponentModel.JAKARTA_CDI)
public interface QuizFullUpdateMapper {

    @Mapping(target = "id", ignore = true)
    void mapFullUpdate(Quiz input, @MappingTarget Quiz target);
}
