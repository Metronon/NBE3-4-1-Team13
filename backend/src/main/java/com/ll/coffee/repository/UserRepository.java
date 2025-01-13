package com.ll.coffee.repository;

import com.ll.coffee.user.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author shjung
 * @since 25. 1. 13.
 */
public interface UserRepository extends JpaRepository<SiteUser, Integer> {
    Optional<SiteUser> findByUsername(String username);
}
