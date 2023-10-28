package be.vinci.authentication;

import be.vinci.authentication.models.SafeCredentials;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends CrudRepository<SafeCredentials, String> {
}