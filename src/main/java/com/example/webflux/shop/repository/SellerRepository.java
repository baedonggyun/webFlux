package com.example.webflux.shop.repository;

import com.example.webflux.shop.entity.Seller;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SellerRepository extends ReactiveCrudRepository<Seller, String> {

}
