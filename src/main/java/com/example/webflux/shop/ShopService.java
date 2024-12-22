package com.example.webflux.shop;


import com.example.webflux.shop.dto.CombinedResult;
import com.example.webflux.shop.entity.Seller;
import com.example.webflux.shop.entity.Shop;
import com.example.webflux.shop.repository.CustomSellerRepository;
import com.example.webflux.shop.repository.ShopRepository;
import com.example.webflux.shop.repository.SellerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ShopService {

    private final SellerRepository sellerRepository;
    private final CustomSellerRepository customSellerRepository;
    private final ShopRepository shopRepository;

    ShopService(SellerRepository sellerRepository, ShopRepository shopRepository, CustomSellerRepository customSellerRepository) {
        this.sellerRepository = sellerRepository;
        this.shopRepository = shopRepository;
        this.customSellerRepository = customSellerRepository;
    }

    public Mono<Seller> getSeller(String sellerId) {
        return customSellerRepository.findSellerById(sellerId);
    }

    public Mono<List<Shop>> getShops(List<String> shopIds) {
        return shopRepository.findAllById(shopIds).collectList();
    }

    public Mono<CombinedResult> getCombinedResult(String sellerId, List<String> shopIds) {
        return Mono.zip(
                getSeller(sellerId).switchIfEmpty(Mono.error(new IllegalArgumentException("Seller not found"))),
                getShops(shopIds).switchIfEmpty(Mono.error(new IllegalArgumentException("No shops found"))),
                CombinedResult::new
        );
    }

}