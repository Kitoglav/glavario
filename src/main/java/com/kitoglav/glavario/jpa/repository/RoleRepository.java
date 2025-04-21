package com.kitoglav.glavario.jpa.repository;

import com.kitoglav.glavario.jpa.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    Set<Role> findRolesByUsersId(Long id);

    Set<Role> findRolesByUsersUsername(String username);
}
