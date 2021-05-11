/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.client_profile;

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
public class ClientProfileController
{

    private final ClientProfileRepository repository;
    private final ClientProfileModelAssembler assembler;

    public ClientProfileController(ClientProfileRepository repository, ClientProfileModelAssembler assembler)
    {

        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/clientProfile")
    public CollectionModel<EntityModel<ClientProfile>> all()
    {
        List<EntityModel<ClientProfile>> categorys = repository.findAll().stream() //
            .map(assembler::toModel) //
            .collect(Collectors.toList());

        return CollectionModel.of(categorys, linkTo(methodOn(ClientProfileController.class).all()).withSelfRel());
    }

    @GetMapping("/clientProfile/{id}")
    public EntityModel<ClientProfile> findById(@PathVariable(value = "id") String id)
    {
        ClientProfile clientProfile = repository.findById(id) //
            .orElseThrow(() -> new EntityNotFoundException(new ClientProfile(), id));

        return assembler.toModel(clientProfile);
    }

    @PostMapping("/clientProfile")
    public ResponseEntity<?> save(@RequestBody ClientProfile clientProfile)
    {
        EntityModel<ClientProfile> entityModel = assembler.toModel(repository.save(clientProfile));

        return ResponseEntity //
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
            .body(entityModel);

    }

    @PutMapping("/clientProfile/{id}")
    public ResponseEntity<?> update(@RequestBody ClientProfile newClientProfile, @PathVariable String id)
    {
        ClientProfile updatedCategory = repository.findById(id) //
            .map(clientProfile
                -> {
                clientProfile.setName(newClientProfile.getName());
                clientProfile.setDescription(newClientProfile.getDescription());

                return repository.save(clientProfile);
            }) //
            .orElseGet(()
                -> {
                newClientProfile.setId(id);
                return repository.save(newClientProfile);
            });

        EntityModel<ClientProfile> entityModel = assembler.toModel(updatedCategory);

        return ResponseEntity //
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
            .body(entityModel);
    }

    @DeleteMapping("/clientProfile/{id}")
    public ResponseEntity<?> delete(@PathVariable String id)
    {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
