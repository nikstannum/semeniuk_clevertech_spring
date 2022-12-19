package by.clevertech.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import by.clevertech.service.dto.CheckOutDto;

/**
 * A class that prepares a {@link CheckOutDto} before sending it to the user.
 * 
 * @author Nikita Semeniuk
 *
 */
@Component
public class CheckPreparer {
    private static final String HEADER = """
            CASH RECEIPT
            SUPERMARKET 123
            12, MILKYWAY Galaxy/ Earth
            Tel: 123-456-7890
            CASHIER: 1234
            """;

    public CheckOutDto prepareCheck(CheckOutDto dto) {
        dto.setHeader(HEADER);
        LocalDateTime dateTime = LocalDateTime.now();
        dto.setTimestamp(dateTime);
        return dto;
    }
}
