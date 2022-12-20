package by.clevertech.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import by.clevertech.data.entity.DiscountCard;
import by.clevertech.data.entity.Product;
import by.clevertech.data.repository.CardRepository;
import by.clevertech.data.repository.ProductRepository;
import by.clevertech.exception.ClevertechException;
import by.clevertech.service.CheckPreparer;
import by.clevertech.service.CheckService;
import by.clevertech.service.dto.CheckInDto;
import by.clevertech.service.dto.CheckOutDto;

@SpringBootTest
class CheckServiceImplTest {
    @Autowired
    private static CheckService checkService;
    private static ProductRepository productRepository;
    private static CardRepository cardRepository;
    private static CheckPreparer preparer;
    private CheckInDto validInDto;
    private CheckInDto invalidInDto;

    @BeforeAll
    static void beforeAll() {
        productRepository = Mockito.mock(ProductRepository.class);
        Product product = new Product();
        product.setId(1L);
        product.setName("milk");
        product.setPrice(BigDecimal.valueOf(0.99));
        product.setDiscount(false);
        Mockito.when(productRepository.findById(1L)).thenReturn(product);
        Mockito.when(productRepository.findById(999L)).thenThrow(new RuntimeException()); // FIXME ENFExc. See class
                                                                                          // PRImpl
        cardRepository = Mockito.mock(CardRepository.class);
        DiscountCard card = new DiscountCard();
        card.setCardId(1L);
        card.setDiscountSize(BigDecimal.valueOf(1.5));
        Mockito.when(cardRepository.findById(1L)).thenReturn(card);
        Mockito.when(cardRepository.findById(999L)).thenThrow(new RuntimeException()); // FIXME
        preparer = new CheckPreparer();
        checkService = new CheckServiceImpl(productRepository, cardRepository, preparer);
    }

    @BeforeEach
    void validInputData() {
        validInDto = new CheckInDto();
        validInDto.setCardId(1L);
        Map<Long, Integer> map = new HashMap<>();
        map.put(1L, 2);
        validInDto.setProducts(map);
    }

    @BeforeEach
    void invalidInputData() {
        invalidInDto = new CheckInDto();
        invalidInDto.setCardId(11L);
        Map<Long, Integer> map = new HashMap<>();
        map.put(111L, 2);
        invalidInDto.setProducts(map);
    }

    @Test
    void fullCost() {
        CheckOutDto actual = checkService.get(validInDto);
        assertEquals(BigDecimal.valueOf(100), actual.getFullCost());
    }

    @Test
    void getCheckNullIn() {
        assertThrows(ClevertechException.class, () -> checkService.get(null));
    }

}
