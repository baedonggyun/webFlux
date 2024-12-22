package com.example.webflux.shop.dto;

import com.example.webflux.shop.entity.Seller;
import com.example.webflux.shop.entity.Shop;
import lombok.Data;

import java.util.List;


@Data
public class CombinedResult {
    private final Seller seller;
    private final List<Shop> shops;
}