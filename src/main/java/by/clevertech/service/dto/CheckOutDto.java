package by.clevertech.service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import by.clevertech.data.entity.CheckItem;
import lombok.Data;

/**
 * An entity that represents the output as the result of processing the input
 * parameters.
 * 
 * @author Nikita Semeniuk
 *
 */
@Data
public class CheckOutDto {
    private String header;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime timestamp;
    private List<CheckItem> items;
    private BigDecimal fullCost;
    private BigDecimal totalCost;
}