package by.clevertech.service;

import by.clevertech.service.dto.CheckOutDto;
import by.clevertech.service.dto.CheckInDto;

public interface CheckService extends CrudService<CheckOutDto, Long> {
	public CheckOutDto get(CheckInDto checkInputDto);

	public CheckOutDto findById(Long id);
}
