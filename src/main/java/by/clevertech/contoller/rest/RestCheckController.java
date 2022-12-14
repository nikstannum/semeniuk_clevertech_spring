package by.clevertech.contoller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import by.clevertech.service.CheckService;
import by.clevertech.service.dto.CheckDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/check")
@RequiredArgsConstructor
public class RestCheckController {

	private final CheckService checkService;

	@GetMapping()
	public CheckDto getCheck(@RequestParam Long itemId) {
		return checkService.findById(itemId);
	}

}
