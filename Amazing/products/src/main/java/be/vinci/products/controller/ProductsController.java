package be.vinci.products.controller;

import be.vinci.products.model.Product;
import be.vinci.products.service.ProductsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductsController {

    private final ProductsService service;

    public ProductsController(ProductsService service) {
        this.service = service;
    }

    /**
     * Create a product
     * @request POST /products
     * @body product to create
     * @response 209: product already exists, 201: returns created product
     */
    @PostMapping("/products")
    public ResponseEntity<Product> createOne(@RequestBody Product product) {
        boolean created = service.createOne(product);
        if (!created) return new ResponseEntity<>(HttpStatus.CONFLICT);
        else return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    /**
     * Read all products
     * @request GET /products
     * @response 200: returns all products
     */
    @GetMapping("/products")
    public Iterable<Product> readAll() {
        return service.readAll();
    }

    /**
     * Read a product
     * @request GET /products/{hash}
     * @response 404: product not found, 200: returns found product
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> readOne(@PathVariable String id) {
        Product product = service.readOne(id);

        if (product == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /**
     * Update a product
     * @request PUT /products/{id}
     * @body new value of the product
     * @response 400: product does not match hash, 404: product not found, 200: returns old value of product
     */
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateOne(@PathVariable String id, @RequestBody Product product) {
        if (!product.getId().equals(id)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        boolean updated = service.updateOne(id, product);
        if (!updated) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /**
     * Delete all products
     * @request DELETE /products
     * @response 200: all products are deleted
     */
    @DeleteMapping("/products")
    public void deleteAll() {
        service.deleteAll();
    }

    /**
     * Delete a product
     * @request  DELETE /products/{id}
     * @response 404: product not found, 200: returns deleted product
     */
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Product> deleteOne(@PathVariable String id) {
        if (!service.deleteOne(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(HttpStatus.OK);
    }

}
