package by.clevertech.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import by.clevertech.data.entity.CheckItem;
import by.clevertech.data.entity.DiscountCard;
import by.clevertech.data.entity.Product;
import by.clevertech.data.repository.CardRepository;
import by.clevertech.data.repository.ProductRepository;
import by.clevertech.service.CheckPreparer;
import by.clevertech.service.CheckService;
import by.clevertech.service.dto.CheckInDto;
import by.clevertech.service.dto.CheckOutDto;
import lombok.RequiredArgsConstructor;

/**
 * Implements {@link CheckService}
 * <p>
 * Business domain class for processing the {@link CheckInDto} and getting the
 * {@link CheckOutDto}
 * 
 * @author Nikita Semeniuk
 *
 */
@Service
@RequiredArgsConstructor
public class CheckServiceImpl implements CheckService {
    private static final int PERCENT_100 = 100;
    /**
     * Set amount of discount
     */
    private static final int DISCOUNT_SIZE = 10;
    private static final int DECIMAL_SCALE = 2;
    /**
     * Minimal number of products in check to apply discounts
     */
    private static final int MIN_NUMBER_OF_PRODUCTS = 5;
    private final ProductRepository productRepository;
    private final CardRepository cardRepository;
    private final CheckPreparer preparer;

    @Override
    public CheckOutDto get(CheckInDto checkInputDto) {
        CheckOutDto check = new CheckOutDto();
        List<CheckItem> items = getCheckItems(checkInputDto.getProducts());
        check.setItems(items);
        Long cardId = checkInputDto.getCardId();
        check.setFullCost(getFullCost(items));
        BigDecimal totalCost = getTotalCost(cardId, items);
        check.setTotalCost(totalCost);
        CheckOutDto preparedCheck = preparer.prepareCheck(check);
        return preparedCheck;
    }

    private BigDecimal getTotalCost(Long cardId, List<CheckItem> items) {
        BigDecimal costWithoutCard = applyProductDiscounts(items);
        return cardId == null ? costWithoutCard : applyDiscountCard(costWithoutCard, cardId);
    }

    private List<CheckItem> getCheckItems(Map<Long, Integer> products) {
        List<CheckItem> items = new ArrayList<>();
        products.forEach((id, quantity) -> items.add(getCheckItem(id, quantity)));
        return items;
    }

    private CheckItem getCheckItem(Long id, Integer quantity) {
        CheckItem item = new CheckItem();
        Product product = productRepository.findById(id);
        BigDecimal total = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        item.setQuantity(quantity);
        item.setProduct(product);
        item.setTotal(total);
        return item;
    }

    private BigDecimal getFullCost(List<CheckItem> items) {
        BigDecimal fullCost = BigDecimal.ZERO;
        for (CheckItem item : items) {
            fullCost = fullCost.add(item.getTotal());
        }
        return fullCost.setScale(DECIMAL_SCALE, RoundingMode.HALF_UP);
    }

    private BigDecimal costDicsountItem(CheckItem item) {
        BigDecimal discountForDiscountProduct = BigDecimal.valueOf(DISCOUNT_SIZE);
        BigDecimal discountFactor = BigDecimal.valueOf(PERCENT_100).subtract(discountForDiscountProduct);
        BigDecimal cost = item.getTotal().multiply(discountFactor).divide(BigDecimal.valueOf(PERCENT_100));
        return cost.setScale(DECIMAL_SCALE, RoundingMode.HALF_UP);
    }

    private BigDecimal applyProductDiscounts(List<CheckItem> items) {
        BigDecimal totalCost = BigDecimal.ZERO;
        for (CheckItem item : items) {
            Integer quantity = item.getQuantity();
            Product product = item.getProduct();
            if (quantity > MIN_NUMBER_OF_PRODUCTS && product.isDiscount()) {
                totalCost = totalCost.add(costDicsountItem(item));
            } else {
                totalCost = totalCost.add(item.getTotal());
            }
        }
        return totalCost.setScale(DECIMAL_SCALE, RoundingMode.HALF_UP);
    }

    private BigDecimal applyDiscountCard(BigDecimal cost, Long cardId) {
        BigDecimal totalCost = cost;
        DiscountCard card = cardRepository.findById(cardId);
        if (card != null) { // FIXME how will to processing better?
            BigDecimal discountSize = card.getDiscountSize();
            BigDecimal discountFactor = BigDecimal.valueOf(PERCENT_100).subtract(discountSize);
            totalCost = totalCost.multiply(discountFactor).divide(BigDecimal.valueOf(PERCENT_100));
        }
        return totalCost.setScale(DECIMAL_SCALE, RoundingMode.HALF_UP);
    }
}