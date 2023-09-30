package be.vinci.amazing;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    private static final List<Product> products = new ArrayList<>();

    static {
        products.add(new Product(
                "1",
                "Tablette",
                "Electronics",
                199.99
        ));
        products.add(new Product(
                "2",
                "Bic",
                "Stationery",
                0.99
        ));
        products.add(new Product(
                "3",
                "Crayon",
                "Stationery",
                0.49
        ));
    }

    /**
     * Find the index in the list of videos of the video with a certain id
     * @param id the id to search for
     * @return the index of the video with this hash, or -1 if none is found
     */
    private int findIndex(String id) {
        return products.stream().map(Product::getId).toList().indexOf(id);
    }

    /**
     * Check if a video with a certain hash exists
     * @param id the id to search for
     * @return true if the video with this hash exists, false otherwise
     */
    private boolean exists(String id) {
        return findIndex(id) != -1;
    }


    /**
     * Create a product
     * @request POST /products
     * @body product to create
     * @response 209: product already exists, 201: returns created product
     */
    @PostMapping("/products")
    public ResponseEntity<Product> createOne(@RequestBody Product product) {
        if (exists(product.getId())) return new ResponseEntity<>(HttpStatus.CONFLICT);

        products.add(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    /**
     * Read all products
     * @request GET /products
     * @response 200: returns all products
     */
    @GetMapping("/products")
    public Iterable<Product> readAll() {
        return products;
    }

    /**
     * Read a product
     * @request GET /products/{hash}
     * @response 404: product not found, 200: returns found product
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> readOne(@PathVariable String id) {
        Product product = products.stream().filter(it -> it.getId().equals(id)).findFirst().orElse(null);

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

        int index = findIndex(id);
        if (index == -1) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Product oldProduct = products.set(index, product);
        return new ResponseEntity<>(oldProduct, HttpStatus.OK);
    }

    /**
     * Delete all products
     * @request DELETE /products
     * @response 200: all products are deleted
     */
    @DeleteMapping("/products")
    public void deleteAll() {
        products.clear();
    }

    /**
     * Delete a product
     * @request  DELETE /products/{id}
     * @response 404: product not found, 200: returns deleted product
     */
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Product> deleteOne(@PathVariable String id) {
        int index = findIndex(id);
        if (index == -1) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Product product = products.remove(index);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

}
