package com.trading.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.model.Coin;
import com.trading.service.CoinService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/coins")
public class CoinController {

    @Autowired
    private CoinService coinService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/{page}")
    ResponseEntity<List<Coin>> getCoinList(@PathVariable("page") int page) throws Exception{

        List<Coin> coins=coinService.getCoinList(page);

        return new ResponseEntity<>(coins,HttpStatus.ACCEPTED);
         
    }

    @GetMapping("/{coinId}/chart")
    ResponseEntity<JsonNode> getMarketChart(@PathVariable("coinId") String coinId,
                                              @RequestParam("days")int days) throws Exception{

        String res=coinService.getMarketChart(coinId, days);
        JsonNode jsonNode=objectMapper.readTree(res);


        return new ResponseEntity<>(jsonNode,HttpStatus.ACCEPTED);
         
    }

    @GetMapping("/search")
    ResponseEntity<JsonNode> searchCoin(@RequestParam("q") String keyword) throws Exception{

        String coin=coinService.searchCoin(keyword);
        JsonNode jsonNode=objectMapper.readTree(coin);

        return ResponseEntity.ok(jsonNode);

    }

    @GetMapping("/top50")
    ResponseEntity<JsonNode> getTop50CoinByMarketCapRank() throws Exception{

        String coin=coinService.getTop50CoinByMarketCapRank();

        JsonNode jsonNode= objectMapper.readTree(coin);

        return ResponseEntity.ok(jsonNode);
    }

    @GetMapping("/treading")
    ResponseEntity<JsonNode> getTredingCoin() throws Exception{
    
        String coin= coinService.getTrendingCoins();
        JsonNode jsonNode=objectMapper.readTree(coin);
        return ResponseEntity.ok(jsonNode);
    }

    @GetMapping("/details/{coinId}")
    ResponseEntity<JsonNode> getCoinDetails(@PathVariable String coinId) throws Exception{

        String coin = coinService.getCoinDetails(coinId);
        System.out.println(coin);
        JsonNode jsonNode=objectMapper.readTree(coin);
        return ResponseEntity.ok(jsonNode);
    }

}
