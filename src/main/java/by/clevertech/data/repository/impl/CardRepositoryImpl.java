package by.clevertech.data.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import by.clevertech.data.entity.DiscountCard;
import by.clevertech.data.repository.CardRepository;
import by.clevertech.exception.ClientException;
import lombok.RequiredArgsConstructor;

/**
 * Implements {@link CardRepository}
 * <p>
 * Class for data access {@link DiscountCard}.
 * <p>
 * Accessed using named parameters {@link NamedParameterJdbcTemplate} of the
 * Spring Data
 * 
 * @author Nikita Semeniuk
 *
 */
@Repository
@RequiredArgsConstructor
public class CardRepositoryImpl implements CardRepository {
    private static final String COL_CARD_ID = "card_id";
    private static final String EXC_MSG_UPDATE = "couldn't update card with id = ";
    private static final String PARAM_DISCOUNT_SIZE = "discountSize";
    private static final String PARAM_CARD_ID = "cardId";
    private static final String EXC_MSG_CREATE = "couldn't create new card";
    private static final String COL_DISCOUNT_SIZE = "discount_size";
    private static final String FIND_BY_ID = "SELECT c.card_id, c.discount_size FROM discount_card c WHERE c.card_id = :cardId";
    private static final String FIND_ALL = "SELECT c.card_id, c.discount_size FROM discount_card c";
    private static final String INSERT = "INSERT into discount_card (discount_size) VALUES (:discountSize)";
    private static final String UPDATE = "UPDATE discount_card SET discount_size = :discountSize WHERE card_id = :cardId";
    private static final String DELETE = "DELETE FROM discount_card WHERE card_id = :cardId";

    private final NamedParameterJdbcTemplate template;

    @Override
    public DiscountCard create(DiscountCard entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(COL_DISCOUNT_SIZE, entity.getDiscountSize());
        template.update(INSERT, params, keyHolder);
        Number key = keyHolder.getKey();
        if (key == null) {
            throw new ClientException(EXC_MSG_CREATE);
        }
        Long id = key.longValue();
        return findById(id);
    }

    @Override
    public DiscountCard findById(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put(PARAM_CARD_ID, id);
        return template.queryForObject(FIND_BY_ID, params, this::mapRow);
    }

    @Override
    public List<DiscountCard> findAll() {
        return template.query(FIND_ALL, this::mapRow);
    }

    @Override
    public DiscountCard update(DiscountCard entity) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(PARAM_DISCOUNT_SIZE, entity.getDiscountSize());
        params.addValue(PARAM_CARD_ID, entity.getCardId());
        int rowUpdated = template.update(UPDATE, params);
        if (rowUpdated == 0) {
            throw new ClientException(EXC_MSG_UPDATE + entity.getCardId());
        }
        return findById(entity.getCardId());
    }

    @Override
    public boolean delete(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put(PARAM_CARD_ID, id);
        return template.update(DELETE, params) == 1;
    }

    private DiscountCard mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        DiscountCard card = new DiscountCard();
        card.setCardId(resultSet.getLong(COL_CARD_ID));
        card.setDiscountSize(resultSet.getBigDecimal(COL_DISCOUNT_SIZE));
        return card;
    }
}
