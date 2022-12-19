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

import by.clevertech.data.entity.Product;
import by.clevertech.data.repository.ProductRepository;
import by.clevertech.exception.ClientException;
import lombok.RequiredArgsConstructor;

/**
 * Implements {@link ProductRepository}
 * <p>
 * Class for data access {@link Product}.
 * <p>
 * Accessed using named parameters {@link NamedParameterJdbcTemplate}
 * 
 * @author Nikita Semeniuk
 *
 */
@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    private static final String EXC_MSG_UPDATE = "couldn't update product with id = ";
    private static final String EXC_MSG_CREATE = "couldn't create new product";
    private static final String PARAM_ID = "id";
    private static final String COL_DISCOUNT = "discount";
    private static final String COL_PRICE = "price";
    private static final String COL_NAME = "name";
    private static final String COL_PRODUCT_ID = "product_id";
    private static final String INSERT = "INSERT into ptoducts (name, price, discount) VALUES (:name, :price, :discount)";
    private static final String FIND_BY_ID = "SELECT p.product_id, p.name, p.price, p.discount FROM products p WHERE product_id=:id";
    private static final String FIND_ALL = "SELECT p.product_id, p.name, p.price, p.discount FROM products p";
    private static final String UPDATE = "UPDATE ptoducts SET name = :name, price = :price, discount = :discount WHERE product_id = :id";
    private static final String DELETE = "DELETE FROM products WHERE product_id = :id";

    private final NamedParameterJdbcTemplate template;

    @Override
    public Product create(Product entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(COL_NAME, entity.getName()).addValue(COL_PRICE, entity.getPrice()).addValue(COL_DISCOUNT,
                entity.isDiscount());
        template.update(INSERT, params, keyHolder);
        Number key = keyHolder.getKey();
        if (key == null) {
            throw new ClientException(EXC_MSG_CREATE);
        }
        Long id = key.longValue();
        return findById(id);
    }

    @Override
    public Product findById(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put(PARAM_ID, id);
        return template.queryForObject(FIND_BY_ID, params, this::mapRow);
    }

    @Override
    public List<Product> findAll() {
        return template.query(FIND_ALL, this::mapRow);
    }

    @Override
    public Product update(Product entity) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(COL_NAME, entity.getName()).addValue(COL_PRICE, entity.getPrice())
                .addValue(COL_DISCOUNT, entity.isDiscount()).addValue(PARAM_ID, entity.getId());
        int rowUpdated = template.update(UPDATE, params);
        if (rowUpdated == 0) {
            throw new ClientException(EXC_MSG_UPDATE + entity.getId());
        }
        return findById(entity.getId());
    }

    @Override
    public boolean delete(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put(PARAM_ID, id);
        return template.update(DELETE, params) == 1;
    }

    private Product mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong(COL_PRODUCT_ID));
        product.setName(resultSet.getString(COL_NAME));
        product.setPrice(resultSet.getBigDecimal(COL_PRICE));
        product.setDiscount(resultSet.getBoolean(COL_DISCOUNT));
        return product;
    }

}