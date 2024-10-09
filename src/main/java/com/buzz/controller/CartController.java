package com.buzz.controller;

import com.buzz.exception.ProductException;
import com.buzz.exception.UserException;
import com.buzz.model.Cart;
import com.buzz.model.User;
import com.buzz.request.AddItemRequest;
import com.buzz.response.ApiResponse;
import com.buzz.service.CartService;
import com.buzz.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@Tag(name="Cart Management", description="Find user cart, add item to cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    @Operation(description = "Find Cart by userId.")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException{
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart= cartService.findUserCart(user.getId());

        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

    @PostMapping("/add")
    @Operation(description = "Add item to cart.")
    public ResponseEntity<ApiResponse>addItemToCart(@RequestBody AddItemRequest req, @
                                                    RequestHeader("Authorization") String jwt) throws UserException, ProductException{
        User user = userService.findUserProfileByJwt(jwt);
        cartService.addCartItem(user.getId(),req);

        ApiResponse res = new ApiResponse();
        res.setMessage("Item added to cart");
        res.setStatus(true);
        return new ResponseEntity<>(res,HttpStatus.CREATED);
    }


}
