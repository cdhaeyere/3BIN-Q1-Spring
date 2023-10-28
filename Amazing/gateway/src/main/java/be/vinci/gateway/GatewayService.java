package be.vinci.gateway;

import be.vinci.gateway.data.ProductProxy;
import be.vinci.gateway.exceptions.BadRequestException;
import be.vinci.gateway.exceptions.NotFoundException;
import be.vinci.gateway.models.*;
import feign.FeignException;
import org.springframework.stereotype.Service;

@Service
public class GatewayService {

    private final ProductProxy productProxy;

    public GatewayService(ProductProxy productProxy) {
        this.productProxy = productProxy;
    }

    public Iterable<Product> readAllProducts() {
        return productProxy.readAll();
    }

    public Product readOneProduct(int id) throws NotFoundException, BadRequestException {
        Product product;

        try {
            product = productProxy.readOne(id).getBody();
        } catch (FeignException e) {
            if (e.status() == 404) throw new NotFoundException();
            else throw new BadRequestException();
        }

        return product;
    }
}
