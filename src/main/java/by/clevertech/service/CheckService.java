package by.clevertech.service;

import by.clevertech.service.dto.CheckDto;
import by.clevertech.service.dto.CheckInputDto;

public interface CheckService extends CrudService<CheckDto, Long> {
	public CheckDto get(CheckInputDto checkInputDto);

	public CheckDto findById(Long id);
}
