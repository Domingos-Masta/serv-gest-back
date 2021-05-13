/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.client_profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 *
 * @author domingos.fernando
 */
@Component
public interface ClientProfileRepository extends JpaRepository<ClientProfile, Long>
{

}
