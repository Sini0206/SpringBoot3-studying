package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Entity
@Getter
public class Coffee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column
    String name;
    @Column
    String price;

    public Coffee(Long id, String name, String price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
    public void patch(Coffee coffee){
        if (coffee.getName() != null)
            name = coffee.getName();    // this. 꼭 안 붙여도 됨
        if (coffee.getPrice() != null)
            price = coffee.getPrice();
    }
}
