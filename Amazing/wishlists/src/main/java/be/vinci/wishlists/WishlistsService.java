package be.vinci.wishlists;

import be.vinci.wishlists.models.Wishlist;
import be.vinci.wishlists.repositories.WishlistRepository;
import org.springframework.stereotype.Service;

@Service
public class WishlistsService {

    private final WishlistRepository repository;

    public WishlistsService(WishlistRepository repository) {
        this.repository = repository;
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

    public Iterable<Wishlist> readAll(String pseudo) {
        return repository.findByPseudo(pseudo);
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
