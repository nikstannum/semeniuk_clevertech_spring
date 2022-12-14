package by.clevertech.service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import by.clevertech.dao.entity.CheckItem;
import lombok.Data;

@Data
public class CheckDto {
	private String header;
	private LocalDateTime timestamp;
	private List<CheckItem> products;
	private BigDecimal totalCost;
}