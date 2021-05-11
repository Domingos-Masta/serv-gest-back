/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.payment;

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
public class PaymentModelAssembler implements RepresentationModelAssembler<Payment, EntityModel<Payment>>
{

    @Override
    public EntityModel<Payment> toModel (Payment category)
    {

        return EntityModel.of(category, //
                linkTo(methodOn(PaymentController.class).findById(category.getId())).withSelfRel(),
                linkTo(methodOn(PaymentController.class).all()).withRel("category"));
    }
}
