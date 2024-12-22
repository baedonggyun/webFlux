package com.example.webflux.shop.repository;

import com.example.webflux.shop.entity.Shop;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ShopRepository extends ReactiveCrudRepository<Shop, String> {

}