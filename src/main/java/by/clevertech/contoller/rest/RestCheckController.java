package by.clevertech.contoller.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import by.clevertech.service.CheckService;
import by.clevertech.service.dto.CheckDto;
import by.clevertech.service.dto.CheckInputDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("check")
@RequiredArgsConstructor
public class RestCheckController {

	private final CheckService checkService;

	@GetMapping()
	public CheckDto getCheck(@RequestParam(value = "itemId") List<Long> items,
					@RequestParam(value = "card", required = false) Long cardId) {
		CheckInputDto dto = processParams(items, cardId);
		CheckDto check = checkService.get(dto);
		return check;
	}

	private CheckInputDto processParams(List<Long> list, Long cardId) {
		CheckInputDto dto = new CheckInputDto();
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
