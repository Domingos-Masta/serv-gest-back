/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.provider;

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
public class ProviderController
{

    private final ProviderRepository repository;
    private final ProviderModelAssembler assembler;

    public ProviderController(ProviderRepository repository, ProviderModelAssembler assembler)
    {

        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/provider")
    public CollectionModel<EntityModel<Provider>> all()
    {
        List<EntityModel<Provider>> categorys = repository.findAll().stream() //
            .map(assembler::toModel) //
            .collect(Collectors.toList());

        return CollectionModel.of(categorys, linkTo(methodOn(ProviderController.class).all()).withSelfRel());
    }

    @GetMapping("/provider/{id}")
    public EntityModel<Provider> findById(@PathVariable(value = "id") String id)
    {
        Provider provider = repository.findById(id) //
            .orElseThrow(() -> new EntityNotFoundException(new Provider(), id));

        return assembler.toModel(provider);
    }

    @PostMapping("/provider")
    public ResponseEntity<?> save(@RequestBody Provider provider)
    {
        EntityModel<Provider> entityModel = assembler.toModel(repository.save(provider));

        return ResponseEntity //
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
            .body(entityModel);

    }

    @PutMapping("/provider/{id}")
    public ResponseEntity<?> update(@RequestBody Provider newProvider, @PathVariable String id)
    {
        Provider updatedCategory = repository.findById(id) //
            .map(provider
                -> {
                provider.setName(newProvider.getName());
                provider.setDescription(newProvider.getDescription());

                return repository.save(provider);
            }) //
            .orElseGet(()
                -> {
                newProvider.setId(id);
                return repository.save(newProvider);
            });

        EntityModel<Provider> entityModel = assembler.toModel(updatedCategory);

        return ResponseEntity //
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
            .body(entityModel);
    }

    @DeleteMapping("/provider/{id}")
    public ResponseEntity<?> delete(@PathVariable String id)
    {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
