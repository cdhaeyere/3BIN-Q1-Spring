package be.vinci.products.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "products")
public class Product {

    @Id
    private String id;
    private String name;
    private String category;
    private double price;
}
