package by.clevertech.service;

import by.clevertech.dao.entity.Check;
import by.clevertech.service.dto.CheckDto;

public class Mapper {

	public CheckDto toCheckDto(Check check) {
		CheckDto dto = new CheckDto();
		dto.setHeader(check.getHeader());
		dto.setProducts(check.getProducts());
		dto.setTimestamp(check.getTimestamp());
		dto.setTotalCost(check.getTotalCost());
		return dto;
	}

	public Check toCheckEntity(CheckDto dto) {
		Check check = new Check();
		check.setHeader(dto.getHeader());
		check.setProducts(dto.getProducts());
		check.setTimestamp(dto.getTimestamp());
		check.setTotalCost(dto.getTotalCost());
		return check;
	}

}
