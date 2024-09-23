package com.trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trading.model.Order;
import com.trading.model.User;
import com.trading.model.Wallet;
import com.trading.model.WalletTransaction;
import com.trading.service.OrderService;
import com.trading.service.UserService;
import com.trading.service.WalletService;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
     private UserService userService;

    @Autowired
     private OrderService orderService;

    @GetMapping("/api/wallet")
    public ResponseEntity<Wallet> getWallet(@RequestHeader("Authorization")String jwt) throws Exception{
        User user=userService.findUserProfileByJwt(jwt);
        Wallet wallet=walletService.getUserWallet(user);
        return new ResponseEntity<>(wallet,HttpStatus.OK);
    }

    @PutMapping("/api/wallet/{walletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTransfer(
        @RequestHeader("Authorization")String jwt,
        @PathVariable Long walletId,
        @RequestBody WalletTransaction req) throws Exception{
        
        User senderUser=userService.findUserProfileByJwt(jwt);
        Wallet revceiverWallet=walletService.findWalletById(walletId);
        Wallet wallet=walletService.wallettoWalletTransfer(senderUser, revceiverWallet, req.getAmmount());

        return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
    }

    @PutMapping("/api/wallet/order/{orderId}/pay")
    public ResponseEntity<Wallet> payOrderPayment(
        @RequestHeader("Authorization")String jwt,
        @PathVariable Long orderId) throws Exception{
        
        User user=userService.findUserProfileByJwt(jwt);
        Order order = orderService.getOrderById(orderId);
        Wallet wallet = walletService.payOrderPayment(order, user);
        
        return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
    }
}
