package com.example.webflux.shop;

import com.example.webflux.shop.dto.CombinedResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import  com.example.webflux.shop.ShopService;

import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/combined-result")
    public Mono<CombinedResult> getCombinedResult(
            @RequestParam String sellerId,
            @RequestParam List<String> shopIds
    ) {
        return shopService.getCombinedResult(sellerId, shopIds);
    }

}
