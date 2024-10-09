package com.buzz.service;

import com.buzz.exception.ProductException;
import com.buzz.model.Cart;
import com.buzz.model.User;
import com.buzz.request.AddItemRequest;

public interface CartService {

    public Cart createCart(User user);
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException;
    public Cart findUserCart(Long userId);
}
