/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.client;

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
public class ClientController
{

    private final ClientRepository repository;
    private final ClientModelAssembler assembler;

    public ClientController(ClientRepository repository, ClientModelAssembler assembler)
    {

        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/client")
    public CollectionModel<EntityModel<Client>> all()
    {
        List<EntityModel<Client>> categorys = repository.findAll().stream() //
            .map(assembler::toModel) //
            .collect(Collectors.toList());

        return CollectionModel.of(categorys, linkTo(methodOn(ClientController.class).all()).withSelfRel());
    }

    @GetMapping("/client/{id}")
    public EntityModel<Client> findById(@PathVariable(value = "id") Long id)
    {
        Client client = repository.findById(id) //
            .orElseThrow(() -> new EntityNotFoundException(new Client(), id));

        return assembler.toModel(client);
    }

    @PostMapping("/client")
    public ResponseEntity<?> save(@RequestBody Client client)
    {
        EntityModel<Client> entityModel = assembler.toModel(repository.save(client));

        return ResponseEntity //
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
            .body(entityModel);

    }

    @PutMapping("/client/{id}")
    public ResponseEntity<?> update(@RequestBody Client newClient, @PathVariable Long id)
    {
        Client updatedCategory = repository.findById(id) //
            .map(client
                -> {
                client.setName(newClient.getName());
                client.setDescription(newClient.getDescription());

                return repository.save(client);
            }) //
            .orElseGet(()
                -> {
                newClient.setId(id);
                return repository.save(newClient);
            });

        EntityModel<Client> entityModel = assembler.toModel(updatedCategory);

        return ResponseEntity //
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
            .body(entityModel);
    }

    @DeleteMapping("/client/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id)
    {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
