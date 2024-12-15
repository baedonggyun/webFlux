package com.example.webflux;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Data
@Table("USERS")
public class User {

    @Column("id")
    private String id;

    @Column("name")
    private String name;

    @Column("email")
    private String email;

    @Id
    @Column("create_date") // 명시적으로 컬럼 이름 매핑
    private LocalDateTime createDate;

}