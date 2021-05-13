/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.service_type;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 *
 * @author domingos.fernando
 */
@Component
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long>
{

}
