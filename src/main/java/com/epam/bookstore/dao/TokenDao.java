package com.epam.bookstore.dao;

import com.epam.bookstore.entity.IdToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenDao extends JpaRepository<IdToken, Long> {
    @Query("select u.token from IdToken u where u.Id =?1")
    String findTokenById(Long id);
}
