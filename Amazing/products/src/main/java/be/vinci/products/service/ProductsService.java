package be.vinci.products.service;

import be.vinci.products.data.ProductsRepository;
import be.vinci.products.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductsService {

    private final ProductsRepository repository;

    public ProductsService(ProductsRepository repository) {
        this.repository = repository;
    }

    public boolean createOne(Product product) {
        if (repository.existsById(product.getId())) return false;
        repository.save(product);
        return true;
    }

    public Iterable<Product> readAll() {
        return repository.findAll();
    }

    public Product readOne(String id) {
        return repository.findById(id).orElse(null);
    }

    public boolean updateOne(String id, Product product) {
        if (!repository.existsById(id)) return false;
        repository.save(product);
        return true;
    }

    public boolean deleteOne(String id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
