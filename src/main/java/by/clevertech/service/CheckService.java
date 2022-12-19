package by.clevertech.service;

import by.clevertech.service.dto.CheckOutDto;
import by.clevertech.service.dto.CheckInDto;

/**
 * Business domain main interface
 * 
 * @author Nikita Semeniuk
 *
 */
public interface CheckService {
	public CheckOutDto get(CheckInDto checkInputDto);
}
