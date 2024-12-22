package com.example.webflux.shop.repository;

import com.example.webflux.shop.entity.Seller;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class CustomSellerRepository {

    private final DatabaseClient databaseClient;

    public CustomSellerRepository(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    public Mono<Seller> findSellerById(String sellerId) {
        String sql = "SELECT * FROM SELLER WHERE SELLER_ID = :sellerId AND ROWNUM <= 2";
        return databaseClient.sql(sql)
                .bind("sellerId", sellerId)
                .map((row, metadata) ->
                        new Seller(
                                row.get("SELLER_ID", String.class),
                                row.get("SELLER_NAME", String.class)
                        ))
                .first();
    }

}
