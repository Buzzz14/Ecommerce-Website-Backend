package com.buzz.controller;

import com.buzz.exception.OrderException;
import com.buzz.model.Order;
import com.buzz.response.ApiResponse;
import com.buzz.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrdersHandler(){
        List<Order> orders= orderService.getAllOrders();
        return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
    }

    @PutMapping("/{orderId}/confirmed")
    public ResponseEntity<Order> ConfirmedOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization")String jwt) throws OrderException{
        Order order = orderService.confirmedOrder(orderId);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/{orderId}/shipped")
    public ResponseEntity<Order> ShippedOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization")String jwt) throws OrderException{
        Order order = orderService.shippedOrder(orderId);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/{orderId}/delivered")
    public ResponseEntity<Order> DeliveredOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization")String jwt) throws OrderException{
        Order order = orderService.deliveredOrder(orderId);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/{orderId}/canceled")
    public ResponseEntity<Order> CanceledOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization")String jwt) throws OrderException{
        Order order = orderService.canceledOrder(orderId);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}/delete")
    public ResponseEntity<ApiResponse> DeleteOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization")String jwt) throws OrderException{
        orderService.deleteOrder(orderId);

        ApiResponse res = new ApiResponse();
        res.setMessage(("Order Delete Successfully!"));
        res.setStatus(true);

        return  new ResponseEntity<>(res, HttpStatus.OK);
    }
}
