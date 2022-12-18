package by.clevertech.service;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import by.clevertech.data.entity.Check;
import by.clevertech.service.dto.CheckOutDto;

@Mapper
public interface CheckMapper {
    CheckMapper INSTANCE = Mappers.getMapper(CheckMapper.class);

    public CheckOutDto toCheckDto(Check check);
}
