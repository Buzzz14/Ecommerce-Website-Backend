package com.buzz.service;

import com.buzz.exception.CartItemException;
import com.buzz.exception.UserException;
import com.buzz.model.Cart;
import com.buzz.model.CartItem;
import com.buzz.model.Product;
import org.springframework.stereotype.Service;

@Service
public interface CartItemService {
    public CartItem createCartItem(CartItem cartItem);
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException;
    public CartItem isCartExist(Cart cart, Product product, String size, Long userId);
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;
    public CartItem findCartItemById(Long cartItemId) throws CartItemException;
}
