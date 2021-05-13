/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.service;

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
public class ServiceController
{

    private final ServiceRepository repository;
    private final ServiceModelAssembler assembler;

    public ServiceController(ServiceRepository repository, ServiceModelAssembler assembler)
    {

        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/service")
    public CollectionModel<EntityModel<Service>> all()
    {
        List<EntityModel<Service>> categorys = repository.findAll().stream() //
            .map(assembler::toModel) //
            .collect(Collectors.toList());

        return CollectionModel.of(categorys, linkTo(methodOn(ServiceController.class).all()).withSelfRel());
    }

    @GetMapping("/service/{id}")
    public EntityModel<Service> findById(@PathVariable(value = "id") Long id)
    {
        Service service = repository.findById(id) //
            .orElseThrow(() -> new EntityNotFoundException(new Service(), id));

        return assembler.toModel(service);
    }

    @PostMapping("/service/newcategory")
    public ResponseEntity<?> save(@RequestBody Service service)
    {
        EntityModel<Service> entityModel = assembler.toModel(repository.save(service));

        return ResponseEntity //
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
            .body(entityModel);

    }

    @PutMapping("/service/{id}")
    public ResponseEntity<?> update(@RequestBody Service newService, @PathVariable Long id)
    {
        Service updatedCategory = repository.findById(id) //
            .map(service
                -> {
                service.setName(newService.getName());
                service.setDescription(newService.getDescription());

                return repository.save(service);
            }) //
            .orElseGet(()
                -> {
                newService.setId(id);
                return repository.save(newService);
            });

        EntityModel<Service> entityModel = assembler.toModel(updatedCategory);

        return ResponseEntity //
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
            .body(entityModel);
    }

    @DeleteMapping("/service/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id)
    {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
