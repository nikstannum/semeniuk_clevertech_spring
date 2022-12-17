package by.clevertech.service;

import by.clevertech.data.entity.Check;
import by.clevertech.service.dto.CheckOutDto;

public class Mapper {

	public CheckOutDto toCheckDto(Check check) {
		CheckOutDto dto = new CheckOutDto();
		dto.setHeader(check.getHeader());
		dto.setProducts(check.getProducts());
		dto.setTimestamp(check.getTimestamp());
		dto.setTotalCost(check.getTotalCost());
		return dto;
	}

	public Check toCheckEntity(CheckOutDto dto) {
		Check check = new Check();
		check.setHeader(dto.getHeader());
		check.setProducts(dto.getProducts());
		check.setTimestamp(dto.getTimestamp());
		check.setTotalCost(dto.getTotalCost());
		return check;
	}

}
