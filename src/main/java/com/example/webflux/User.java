package com.example.webflux;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Data
@Table("USERS")
public class User {

    private String id;
    private String name;
    private String email;

    @Id
    private LocalDateTime createDate;

}