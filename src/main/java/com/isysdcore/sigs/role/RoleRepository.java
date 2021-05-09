/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.role;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author domingos.fernando
 */
public interface RoleRepository extends MongoRepository<Role, Long>
{

}
