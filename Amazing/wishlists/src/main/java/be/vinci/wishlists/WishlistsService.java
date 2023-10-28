package be.vinci.wishlists;

import be.vinci.wishlists.models.Product;
import be.vinci.wishlists.models.Wishlist;
import be.vinci.wishlists.repositories.ProductProxy;
import be.vinci.wishlists.repositories.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WishlistsService {

    private final WishlistRepository repository;
    private final ProductProxy productProxy;

    public WishlistsService(WishlistRepository repository, ProductProxy productProxy) {
        this.repository = repository;
        this.productProxy = productProxy;
    }

    public boolean createOne(Wishlist wishlist) {
        repository.save(wishlist);
        return true;
    }

    public boolean deleteOne(String pseudo, int product) {
        if (!repository.existsByPseudoAndProduct(pseudo, product)) return false;
        repository.deleteByPseudoAndProduct(pseudo, product);
        return true;
    }

    public Map<Wishlist, Product> readAll(String pseudo) {
        Iterable<Wishlist> wishlists = repository.findByPseudo(pseudo);

        Map<Wishlist, Product> wishlistProductMap = new HashMap<>();
        for (Wishlist wishlist : wishlists) {
            Product product = productProxy.readOne(wishlist.getProduct());
            if (product != null) {
                wishlistProductMap.put(wishlist, product);
            }
        }
        return wishlistProductMap;
    }

    public boolean deleteWishlist(String pseudo) {
        if (repository.findByPseudo(pseudo) == null) return false;
        repository.deleteByPseudo(pseudo);
        return true;
    }

    public boolean deleteProductFromWishlists(int product) {
        Iterable<Wishlist> wishlists = repository.findAllByProduct(product);
        if (wishlists == null) return false;
        for (Wishlist wishlist : wishlists) {
            repository.deleteByPseudoAndProduct(wishlist.getPseudo(), product);
        }
        return true;
    }
}
