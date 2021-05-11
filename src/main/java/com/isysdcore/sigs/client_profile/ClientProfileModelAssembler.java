/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.client_profile;

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
public class ClientProfileModelAssembler implements RepresentationModelAssembler<ClientProfile, EntityModel<ClientProfile>>
{

    @Override
    public EntityModel<ClientProfile> toModel(ClientProfile category)
    {

        return EntityModel.of(category, //
            linkTo(methodOn(ClientProfileController.class).findById(category.getId())).withSelfRel(),
            linkTo(methodOn(ClientProfileController.class).all()).withRel("category"));
    }
}
