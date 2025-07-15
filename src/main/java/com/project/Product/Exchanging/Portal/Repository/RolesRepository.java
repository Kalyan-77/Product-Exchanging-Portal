package com.project.Product.Exchanging.Portal.Repository;

import com.project.Product.Exchanging.Portal.Model.Roles;
import com.project.Product.Exchanging.Portal.Model.Roles.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByName(ERole name);
}