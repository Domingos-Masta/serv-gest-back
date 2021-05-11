/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.client;

import com.isysdcore.sigs.client_profile.*;
import com.isysdcore.sigs.payment.*;
import com.isysdcore.sigs.provider.*;
import com.isysdcore.sigs.service.*;
import com.isysdcore.sigs.service_type.*;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

/**
 *
 * @author domingos.fernando
 */
@Component
public interface ClientRepository extends MongoRepository<Client, String>
{

}
