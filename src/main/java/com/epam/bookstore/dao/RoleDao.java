package com.epam.bookstore.dao;

import com.epam.bookstore.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Long> {
}
