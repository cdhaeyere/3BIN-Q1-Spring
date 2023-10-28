package be.vinci.wishlists;

import be.vinci.wishlists.models.Product;
import be.vinci.wishlists.models.Wishlist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Map;

@Controller
public class WishlistsController {

    private final WishlistsService wishlistsService;

    public WishlistsController(WishlistsService wishlistsService) {
        this.wishlistsService = wishlistsService;
    }

    /**
     * Add the product to the wishlist of the user
     */
    @PutMapping("/wishlists/{pseudo}/{product}")
    public ResponseEntity<Void> addProductToWishlist(@PathVariable String pseudo, @PathVariable int product) {
        if (pseudo == null || pseudo.isBlank()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Wishlist wishlist = new Wishlist();
        wishlist.setPseudo(pseudo);
        wishlist.setProduct(product);
        boolean created = wishlistsService.createOne(wishlist);
        if (!created) return new ResponseEntity<>(HttpStatus.CONFLICT);
        else return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Remove the product from the wishlist of the user
     */
    @DeleteMapping("/wishlists/{pseudo}/{product}")
    public ResponseEntity<Void> removeProductFromWishlist(@PathVariable String pseudo, @PathVariable int product) {
        if (pseudo == null || pseudo.isBlank()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        boolean deleted = wishlistsService.deleteOne(pseudo, product);
        if (!deleted) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Get the wishlist of the user
     */
    @GetMapping("/wishlists/user/{pseudo}")
    public ResponseEntity<Map<Wishlist, Product>> getWishlist(@PathVariable String pseudo) {
        if (pseudo == null || pseudo.isBlank()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Map<Wishlist, Product> map = wishlistsService.readAll(pseudo);
        if (map == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * Delete the wishlist of the user
     */
    @DeleteMapping("/wishlists/user/{pseudo}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable String pseudo) {
        if (pseudo == null || pseudo.isBlank()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        boolean deleted = wishlistsService.deleteWishlist(pseudo);
        if (!deleted) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Delete the product from all the wishlists
     */
    @DeleteMapping("/wishlists/products/{product}")
    public ResponseEntity<Void> deleteProductFromWishlists(@PathVariable int product) {
        if (product < 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        boolean deleted = wishlistsService.deleteProductFromWishlists(product);
        if (!deleted) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(HttpStatus.OK);
    }

}
