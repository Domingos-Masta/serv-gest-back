/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.service_type;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 *
 * @author domingos.fernando
 */
@Component
public class ServiceTypeModelAssembler implements RepresentationModelAssembler<ServiceType, EntityModel<ServiceType>>
{

    @Override
    public EntityModel<ServiceType> toModel (ServiceType category)
    {

        return EntityModel.of(category, //
                linkTo(methodOn(ServiceTypeController.class).findById(category.getId())).withSelfRel(),
                linkTo(methodOn(ServiceTypeController.class).all()).withRel("category"));
    }
}
