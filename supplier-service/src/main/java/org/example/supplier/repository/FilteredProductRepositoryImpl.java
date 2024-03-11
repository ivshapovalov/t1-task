package org.example.supplier.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.example.supplier.model.entity.Category;
import org.example.supplier.model.entity.Product;
import org.example.supplier.model.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FilteredProductRepositoryImpl implements FilteredProductRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Product> getFilteredProducts(String name,
                                             String description,
                                             BigDecimal price,
                                             String categoryName,
                                             Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> products = criteriaQuery.from(Product.class);
        Fetch<Product, Review> productItemFetch = products.fetch("reviews", JoinType.LEFT);
        Fetch<Product, Category> productCategoryFetch = products.fetch("category", JoinType.LEFT);
        Subquery<Category> subquery = criteriaQuery.subquery(Category.class);
        Root<Category> categories = subquery.from(Category.class);
        subquery.select(categories)
                .distinct(true)
                .where(criteriaBuilder.like(categories.get("name"), "%" + categoryName + "%"));
        criteriaQuery.select(products);

        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(name)) {
            predicates.add(criteriaBuilder.like(products.get("name"), "%" + name + "%"));
        }
        if (!StringUtils.isEmpty(description)) {
            predicates.add(criteriaBuilder.like(products.get("description"), "%" + description + "%"));
        }
        if (!ObjectUtils.isEmpty(price)) {
            predicates.add(criteriaBuilder.equal(products.get("price"), price));
        }
        if (!StringUtils.isEmpty(categoryName)) {
            predicates.add(criteriaBuilder.in(products.get("category")).value(subquery));
        }

        criteriaQuery.orderBy(QueryUtils.toOrders(pageable.getSort(), products, criteriaBuilder));

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        TypedQuery<Product> tq = entityManager.createQuery(criteriaQuery);
        var totalSize = tq.getResultList().size();
        tq.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        tq.setMaxResults(pageable.getPageSize());
        return new PageImpl<>(tq.getResultList(), pageable, totalSize);
    }
}
