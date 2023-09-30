package be.vinci.amazing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Product {

    private String id;
    private String name;
    private String category;
    private double price;
}
