/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.provider;

import com.isysdcore.sigs.service.*;
import com.isysdcore.sigs.service_type.*;
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
public class ProviderModelAssembler implements RepresentationModelAssembler<Provider, EntityModel<Provider>>
{

    @Override
    public EntityModel<Provider> toModel (Provider category)
    {

        return EntityModel.of(category, //
                linkTo(methodOn(ProviderController.class).findById(category.getId())).withSelfRel(),
                linkTo(methodOn(ProviderController.class).all()).withRel("category"));
    }
}
