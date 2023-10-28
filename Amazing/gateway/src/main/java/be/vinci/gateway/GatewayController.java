package be.vinci.gateway;

import be.vinci.gateway.exceptions.BadRequestException;
import be.vinci.gateway.exceptions.NotFoundException;
import be.vinci.gateway.models.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GatewayController {

    private final GatewayService service;

    public GatewayController(GatewayService service) {
        this.service = service;
    }

    @GetMapping("/products")
    public Iterable<Product> readAllProducts() {
        return service.readAllProducts();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> readOneProduct(@PathVariable int id) {
        Product product;

        try {
            product = service.readOneProduct(id);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
