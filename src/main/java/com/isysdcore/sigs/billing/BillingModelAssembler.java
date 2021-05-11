/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.billing;

import com.isysdcore.sigs.client.*;
import com.isysdcore.sigs.client_profile.*;
import com.isysdcore.sigs.payment.*;
import com.isysdcore.sigs.provider.*;
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
public class BillingModelAssembler implements RepresentationModelAssembler<Billing, EntityModel<Billing>>
{

    @Override
    public EntityModel<Billing> toModel (Billing category)
    {

        return EntityModel.of(category, //
                linkTo(methodOn(BillingController.class).findById(category.getId())).withSelfRel(),
                linkTo(methodOn(BillingController.class).all()).withRel("category"));
    }
}
