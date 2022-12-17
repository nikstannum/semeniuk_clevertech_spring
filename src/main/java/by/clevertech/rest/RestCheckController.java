package by.clevertech.rest;

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
        CheckOutDto check = checkService.get(dto);
        return check;
    }

    private CheckInDto processParams(List<Long> list, Long cardId) {
        CheckInDto dto = new CheckInDto();
        Map<Long, Integer> items = new HashMap<>();
        if (cardId != null) {
            dto.setCardId(cardId);
        }
        for (Long itemId : list) {
            if (items.containsKey(itemId)) {
                Integer quantity = items.get(itemId);
                quantity++;
                items.replace(itemId, quantity);
            } else {
                items.put(itemId, 1);
            }
        }
        dto.setProducts(items);
        return dto;
    }
}
