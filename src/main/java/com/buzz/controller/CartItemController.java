package com.buzz.controller;

import com.buzz.exception.CartItemException;
import com.buzz.exception.UserException;
import com.buzz.model.CartItem;
import com.buzz.model.User;
import com.buzz.response.ApiResponse;
import com.buzz.service.CartItemService;
import com.buzz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cartItems")
public class CartItemController {
    @Autowired
    private UserService userService;

    @Autowired
    private CartItemService cartItemService;

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long cartItemId,
                                                      @RequestHeader("Authorization") String jwt)
            throws UserException, CartItemException{
        User user = userService.findUserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);

        ApiResponse res = new ApiResponse();
        res.setMessage("Item deleted from cart.");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItem(@RequestBody CartItem cartItem, @PathVariable Long cartItemId,
                                                   @RequestHeader("Authorization") String jwt)
            throws UserException, CartItemException{
        User user = userService.findUserProfileByJwt(jwt);

        CartItem updatedCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);

        return new ResponseEntity<>(updatedCartItem, HttpStatus.OK);

    }


}
