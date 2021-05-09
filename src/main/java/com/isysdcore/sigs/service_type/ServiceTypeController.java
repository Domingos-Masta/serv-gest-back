/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.service_type;

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
public class ServiceTypeController
{

    private final ServiceTypeRepository repository;
    private final ServiceTypeModelAssembler assembler;

    public ServiceTypeController(ServiceTypeRepository repository, ServiceTypeModelAssembler assembler)
    {

        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/serviceType")
    public CollectionModel<EntityModel<ServiceType>> all()
    {
        List<EntityModel<ServiceType>> categorys = repository.findAll().stream() //
            .map(assembler::toModel) //
            .collect(Collectors.toList());

        return CollectionModel.of(categorys, linkTo(methodOn(ServiceTypeController.class).all()).withSelfRel());
    }

    @GetMapping("/serviceType/{id}")
    public EntityModel<ServiceType> findById(@PathVariable(value = "id") String id)
    {
        ServiceType serviceType = repository.findById(id) //
            .orElseThrow(() -> new EntityNotFoundException(new ServiceType(), id));

        return assembler.toModel(serviceType);
    }

    @PostMapping("/serviceType/newcategory")
    public ResponseEntity<?> save(@RequestBody ServiceType serviceType)
    {
        EntityModel<ServiceType> entityModel = assembler.toModel(repository.save(serviceType));

        return ResponseEntity //
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
            .body(entityModel);

    }

    @PutMapping("/serviceType/{id}")
    public ResponseEntity<?> update(@RequestBody ServiceType newServiceType, @PathVariable String id)
    {
        ServiceType updatedCategory = repository.findById(id) //
            .map(serviceType
                -> {
                serviceType.setName(newServiceType.getName());
                serviceType.setDescription(newServiceType.getDescription());

                return repository.save(serviceType);
            }) //
            .orElseGet(()
                -> {
                newServiceType.setId(id);
                return repository.save(newServiceType);
            });

        EntityModel<ServiceType> entityModel = assembler.toModel(updatedCategory);

        return ResponseEntity //
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
            .body(entityModel);
    }

    @DeleteMapping("/serviceType/{id}")
    public ResponseEntity<?> delete(@PathVariable String id)
    {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
