/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author domingos.fernando
 */
public interface UserRepository extends JpaRepository<User, Long>
{

    @Transactional(timeout = 10)
    @Override
    <S extends User> S save(S s);

    public Object findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email = ?1 OR u.phone = ?1")
    public User findByCred(String email);

}
