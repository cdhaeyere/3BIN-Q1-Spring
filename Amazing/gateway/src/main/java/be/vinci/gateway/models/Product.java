package be.vinci.gateway.models;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private int id;
    private String name;
    private String category;
    private double price;
}
