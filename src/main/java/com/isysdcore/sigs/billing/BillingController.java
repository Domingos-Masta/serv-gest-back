/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.billing;

import com.isysdcore.sigs.exceptions.EntityNotFoundException;
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
public class BillingController
{

    private final BillingRepository repository;
    private final BillingModelAssembler assembler;

    public BillingController(BillingRepository repository, BillingModelAssembler assembler)
    {

        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/billing")
    public CollectionModel<EntityModel<Billing>> all()
    {
        List<EntityModel<Billing>> categorys = repository.findAll().stream() //
            .map(assembler::toModel) //
            .collect(Collectors.toList());

        return CollectionModel.of(categorys, linkTo(methodOn(BillingController.class).all()).withSelfRel());
    }

    @GetMapping("/billing/{id}")
    public EntityModel<Billing> findById(@PathVariable(value = "id") Long id)
    {
        Billing billing = repository.findById(id) //
            .orElseThrow(() -> new EntityNotFoundException(new Billing(), id));

        return assembler.toModel(billing);
    }

    @PostMapping("/billing")
    public ResponseEntity<?> save(@RequestBody Billing billing)
    {
        EntityModel<Billing> entityModel = assembler.toModel(repository.save(billing));

        return ResponseEntity //
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
            .body(entityModel);

    }

    @PutMapping("/billing/{id}")
    public ResponseEntity<?> update(@RequestBody Billing newBilling, @PathVariable Long id)
    {
        Billing updatedCategory = repository.findById(id) //
            .map(billing
                -> {
                billing.setName(newBilling.getName());
                billing.setDescription(newBilling.getDescription());

                return repository.save(billing);
            }) //
            .orElseGet(()
                -> {
                newBilling.setId(id);
                return repository.save(newBilling);
            });

        EntityModel<Billing> entityModel = assembler.toModel(updatedCategory);

        return ResponseEntity //
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
            .body(entityModel);
    }

    @DeleteMapping("/billing/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id)
    {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
