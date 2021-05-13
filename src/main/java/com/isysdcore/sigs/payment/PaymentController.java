/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.payment;

import com.isysdcore.sigs.exceptions.EntityNotFoundException;
import com.isysdcore.sigs.payment.Payment;
import com.isysdcore.sigs.payment.PaymentModelAssembler;
import com.isysdcore.sigs.payment.PaymentRepository;
import com.isysdcore.sigs.util.Constants;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author domingos.fernando
 */
@RestController
@RequestMapping(Constants.DEFAULT_APP_URL_BASE + Constants.DEFAULT_APP_API_VERSION + Constants.APP_NAME)
public class PaymentController
{

    private final PaymentRepository repository;
    private final PaymentModelAssembler assembler;

    public PaymentController(PaymentRepository repository, PaymentModelAssembler assembler)
    {

        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/payment")
    public CollectionModel<EntityModel<Payment>> all()
    {
        List<EntityModel<Payment>> categorys = repository.findAll().stream() //
            .map(assembler::toModel) //
            .collect(Collectors.toList());

        return CollectionModel.of(categorys, linkTo(methodOn(PaymentController.class).all()).withSelfRel());
    }

    @GetMapping("/payment/{id}")
    public EntityModel<Payment> findById(@PathVariable(value = "id") Long id)
    {
        Payment payment = repository.findById(id) //
            .orElseThrow(() -> new EntityNotFoundException(new Payment(), id));

        return assembler.toModel(payment);
    }

    @PostMapping("/payment")
    public ResponseEntity<?> save(@RequestBody Payment payment)
    {
        EntityModel<Payment> entityModel = assembler.toModel(repository.save(payment));

        return ResponseEntity //
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
            .body(entityModel);

    }

    @PutMapping("/payment/{id}")
    public ResponseEntity<?> update(@RequestBody Payment newPayment, @PathVariable Long id)
    {
        Payment updatedCategory = repository.findById(id) //
            .map(payment
                -> {
                payment.setDescription(newPayment.getDescription());
                return repository.save(payment);
            }) //
            .orElseGet(()
                -> {
                newPayment.setId(id);
                return repository.save(newPayment);
            });

        EntityModel<Payment> entityModel = assembler.toModel(updatedCategory);

        return ResponseEntity //
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
            .body(entityModel);
    }

    @DeleteMapping("/payment/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id)
    {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
