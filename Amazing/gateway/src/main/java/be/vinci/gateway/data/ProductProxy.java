package be.vinci.gateway.data;

import be.vinci.gateway.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@FeignClient(name = "products")
public interface ProductProxy {

    @GetMapping("/products")
    Iterable<Product> readAll();

    @GetMapping("/products/{id}")
    ResponseEntity<Product> readOne(@PathVariable int id);
}
