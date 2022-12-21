package by.clevertech.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import by.clevertech.data.entity.CheckItem;
import by.clevertech.data.entity.DiscountCard;
import by.clevertech.data.entity.Product;
import by.clevertech.data.repository.CardRepository;
import by.clevertech.data.repository.ProductRepository;
import by.clevertech.service.CheckService;
import by.clevertech.service.dto.CheckInDto;
import by.clevertech.service.dto.CheckOutDto;
import by.clevertech.service.exception.ClientException;
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
    private static final String MSG_EXC_INVALID_NUMBER = "invalid number of products";
    private static final int PERCENT_100 = 100;
    /**
     * Set amount of discount
     */
    private static final int DISCOUNT_SIZE = 10;
    private static final int DECIMAL_SCALE = 2;
    private static final String HEADER = "CASH RECEIPT SUPERMARKET 123 12, MILKYWAY Galaxy/ Earth Tel: 123-456-7890 CASHIER: 1234";
    /**
     * Minimal number of products in check to apply discounts
     */
    private static final int MIN_NUMBER_OF_PRODUCTS = 5;
    private final ProductRepository productRepository;
    private final CardRepository cardRepository;

    /**
     * The main method of business logic. Calculates the full cost and the cost with
     * discounts, including the discount card
     */
    @Override
    public CheckOutDto get(CheckInDto checkInputDto) {
        CheckOutDto check = new CheckOutDto();
        List<CheckItem> items = getCheckItems(checkInputDto.getProducts());
        check.setItems(items);
        Long cardId = checkInputDto.getCardId();
        check.setFullCost(getFullCost(items));
        BigDecimal totalCost = getTotalCost(cardId, items);
        check.setTotalCost(totalCost);
        check.setHeader(HEADER);
        check.setTimestamp(LocalDateTime.now());
        return check;
    }

    /**
     * serializes a map containing product ids and quantities into a list of product
     * items
     * 
     * @param products the map containing product ids and quantities
     * @return list of product
     */
    private List<CheckItem> getCheckItems(Map<Long, Integer> products) {
        List<CheckItem> items = new ArrayList<>();
        try {
            products.forEach((id, quantity) -> items.add(getCheckItem(id, quantity)));
            return items;
        } catch (NullPointerException e) {
            throw new ClientException(MSG_EXC_INVALID_NUMBER);
        }
    }

    /**
     * serializes to heading based on id and quantity
     * 
     * @param id       product id
     * @param quantity the quantity of product
     * @return item
     */
    private CheckItem getCheckItem(Long id, Integer quantity) {
        CheckItem item = new CheckItem();
        Product product = productRepository.findById(id);
        BigDecimal total = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        item.setQuantity(quantity);
        item.setProduct(product);
        item.setTotal(total);
        return item;
    }

    /**
     * Calculates the full cost of a receipt
     * 
     * @param items commodity items
     * @return full cost
     */
    private BigDecimal getFullCost(List<CheckItem> items) {
        BigDecimal fullCost = BigDecimal.ZERO;
        for (CheckItem item : items) {
            fullCost = fullCost.add(item.getTotal());
        }
        return fullCost.setScale(DECIMAL_SCALE, RoundingMode.HALF_UP);
    }

    /**
     * Gets the final cost of the receipt after all discounts have been applied
     * 
     * @param cardId discount card id
     * @param items  list of commodity items
     * @return price with discount
     */
    private BigDecimal getTotalCost(Long cardId, List<CheckItem> items) {
        BigDecimal costWithoutCard = applyProductDiscounts(items);
        return cardId == null ? costWithoutCard : applyDiscountCard(costWithoutCard, cardId);
    }

    /**
     * Applies a discount on promotional items subject to the quantity purchase
     * requirement
     * 
     * @param items the list of commodity items
     * @return price including discount for promotional goods
     */
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

    /**
     * Calculates the cost of a commodity position of a product that is on sale
     * 
     * @param item the check item
     * @return unit cost
     */
    private BigDecimal costDicsountItem(CheckItem item) {
        BigDecimal discountForDiscountProduct = BigDecimal.valueOf(DISCOUNT_SIZE);
        BigDecimal discountFactor = BigDecimal.valueOf(PERCENT_100).subtract(discountForDiscountProduct);
        BigDecimal cost = item.getTotal().multiply(discountFactor).divide(BigDecimal.valueOf(PERCENT_100));
        return cost.setScale(DECIMAL_SCALE, RoundingMode.HALF_UP);
    }

    /**
     * applies a discount on a discount card
     * 
     * @param cost   the cost of the check calculated taking into account the
     *               discount on promotional goods
     * @param cardId discount card id
     * @return
     */
    private BigDecimal applyDiscountCard(BigDecimal cost, Long cardId) {
        BigDecimal totalCost = cost;
        DiscountCard card = cardRepository.findById(cardId);
        if (card != null) {
            BigDecimal discountSize = card.getDiscountSize();
            BigDecimal discountFactor = BigDecimal.valueOf(PERCENT_100).subtract(discountSize);
            totalCost = totalCost.multiply(discountFactor).divide(BigDecimal.valueOf(PERCENT_100));
        }
        return totalCost.setScale(DECIMAL_SCALE, RoundingMode.HALF_UP);
    }
}
