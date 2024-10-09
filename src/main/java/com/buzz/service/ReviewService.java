package com.buzz.service;

import com.buzz.exception.ProductException;
import com.buzz.model.Review;
import com.buzz.model.User;
import com.buzz.request.ReviewRequest;

import java.util.List;

public interface ReviewService {
    public Review createReview(ReviewRequest req, User user) throws ProductException;
    public List<Review> getAllReview(Long productId);
}
