package com.trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trading.service.CoinGeckoService;

@RestController
@RequestMapping("/something/coins")
public class CoinGeckoController {

    @Autowired
    private CoinGeckoService coinGeckoService;

    @GetMapping("/list")
    public String getCoinsList() {
        return coinGeckoService.getCoinsList();
    }
}
