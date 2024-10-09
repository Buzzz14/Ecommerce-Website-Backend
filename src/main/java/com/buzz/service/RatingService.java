package com.buzz.service;

import com.buzz.exception.ProductException;
import com.buzz.model.Rating;
import com.buzz.model.User;
import com.buzz.request.RatingRequest;

import java.util.List;

public interface RatingService {

    public Rating createRating(RatingRequest req, User user) throws ProductException;
    public List<Rating> getProductsRating(Long productId);

}
