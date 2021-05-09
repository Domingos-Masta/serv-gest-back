/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author domingos.fernando
 */
public interface UserRepository extends MongoRepository<User, Long>
{

    @Transactional(timeout = 10)
    @Override
    <S extends User> S save(S s);

    public Object findByEmail(String email);

    public User findByCred(String email);

}
