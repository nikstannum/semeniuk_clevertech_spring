package by.clevertech.service;

import by.clevertech.service.dto.CheckOutDto;
import by.clevertech.service.dto.CheckInDto;

public interface CheckService {
	public CheckOutDto get(CheckInDto checkInputDto);
}
