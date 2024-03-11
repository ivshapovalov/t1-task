package org.example.customer.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.customer.model.dto.request.AddReviewRequest;
import org.example.customer.model.dto.request.UpdateReviewRequest;
import org.example.customer.model.dto.response.GenericResponse;
import org.example.customer.model.entity.Review;
import org.example.customer.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Repository
@Slf4j
public class ReviewRepositoryPagingRestImpl extends PagingRestRepository implements ReviewRepository {

    private final RestTemplate restTemplate;

    private final String supplierReviewUrl;

    private final String supplierProductUrl;

    public ReviewRepositoryPagingRestImpl(RestTemplate restTemplate, @Value("${supplier.url}") String supplierRootUrl) {
        this.restTemplate = restTemplate;
        this.supplierReviewUrl = supplierRootUrl + "/reviews";
        this.supplierProductUrl = supplierRootUrl + "/products";
    }

    @Override
    public Review findById(Long reviewId) {
        String remoteUrl = supplierReviewUrl + "/" + reviewId;
        ResponseEntity<Review> response = restTemplate.getForEntity(remoteUrl, Review.class);
        return response.getBody();
    }


    @Override
    public void update(Long reviewId, UpdateReviewRequest updateReviewRequest) {
        String remoteUrl = supplierReviewUrl + "/" + reviewId;
        restTemplate.put(remoteUrl, updateReviewRequest);
    }

    @Override
    public void delete(Long reviewId) {
        String remoteUrl = supplierReviewUrl + "/" + reviewId;
        restTemplate.delete(remoteUrl);
    }

    @Override
    public GenericResponse<List<Review>> findAllProductReviews(Long productId, Integer page, Integer size, String[] sort) {
        String params = convertPagingParamsToString(page, size, sort);
        String remoteUrl = supplierProductUrl + "/%s/reviews".formatted(productId) + params;
        ResponseEntity<GenericResponse> response = restTemplate.getForEntity(remoteUrl, GenericResponse.class);
        return response.getBody();
    }

    @Override
    public List<Review> saveProductReviews(Long productId, List<AddReviewRequest> addReviewRequests) {
        String remoteUrl = supplierProductUrl + "/%s/reviews".formatted(productId);
        ResponseEntity<Review[]> response = restTemplate.postForEntity(remoteUrl, addReviewRequests, Review[].class);
        return Arrays.asList(response.getBody());
    }
}
