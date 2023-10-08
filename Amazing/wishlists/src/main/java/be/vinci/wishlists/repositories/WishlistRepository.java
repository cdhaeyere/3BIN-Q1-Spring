package be.vinci.wishlists.repositories;

import be.vinci.wishlists.models.Wishlist;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends CrudRepository<Wishlist, Long> {

    boolean existsByPseudoAndProduct(String pseudo, int product);

    @Transactional
    void deleteByPseudoAndProduct(String pseudo, int product);

    Iterable<Wishlist> findByPseudo(String pseudo);

    @Transactional
    void deleteByPseudo(String pseudo);

    @Query(value = "SELECT * FROM wishlists WHERE product = ?", nativeQuery = true)
    Iterable<Wishlist> findAllByProduct(int product);
}
