package by.clevertech.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import by.clevertech.service.CheckService;
import by.clevertech.service.dto.CheckInDto;
import by.clevertech.service.dto.CheckOutDto;
import lombok.RequiredArgsConstructor;

/**
 * RESTFUL interface for getting a receipt
 * 
 * @author Nikita Semeniuk
 *
 */
@RestController
@RequestMapping("check")
@RequiredArgsConstructor
public class RestCheckController {

    private final CheckService checkService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public CheckOutDto getCheck(@RequestParam(value = "itemId") List<Long> items,
            @RequestParam(value = "card", required = false) Long cardId) {
        CheckInDto dto = processParams(items, cardId);
        CheckOutDto out = checkService.get(dto);
        return out;
    }

    private CheckInDto processParams(List<Long> list, Long cardId) {
        CheckInDto dto = new CheckInDto();
        dto.setCardId(cardId);
        Map<Long, Integer> items = new HashMap<>();
        list.forEach(itemId -> items.merge(itemId, 1, Integer::sum));
        dto.setProducts(items);
        return dto;
    }
}
