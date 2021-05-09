/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.role;

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
public class RoleModelAssembler implements RepresentationModelAssembler<Role, EntityModel<Role>>
{

    @Override
    public EntityModel<Role> toModel(Role role)
    {

        return EntityModel.of(role, //
            linkTo(methodOn(RoleController.class).findById(role.getId())).withSelfRel(),
            linkTo(methodOn(RoleController.class).all()).withRel("roles"));
    }
}
