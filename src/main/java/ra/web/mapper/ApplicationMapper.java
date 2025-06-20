package ra.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ra.web.dto.candidate.ApplicationDto;
import ra.web.entity.Application;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {
    @Mapping(source = "recruitmentPosition.id", target = "recruitmentPositionId")
    @Mapping(source = "recruitmentPosition.name", target = "recruitmentPositionName")
    ApplicationDto toDto(Application application);
}