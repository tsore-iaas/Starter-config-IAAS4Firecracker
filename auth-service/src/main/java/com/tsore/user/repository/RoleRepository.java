package com.tsore.user.repository;

import com.tsore.user.entity.Role;
import com.tsore.user.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType name);
}
