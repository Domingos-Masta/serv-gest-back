/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.role;

import com.isysdcore.sigs.exceptions.EntityNotFoundException;
import com.isysdcore.sigs.util.Constants;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 *
 * @author domingos.fernando
 */
@RestController
@RequestMapping(Constants.DEFAULT_APP_URL_BASE + Constants.DEFAULT_APP_API_VERSION + Constants.APP_NAME)
public class RoleController
{

    private final RoleRepository repository;
    private final RoleModelAssembler assembler;

    public RoleController(RoleRepository repository, RoleModelAssembler assembler)
    {

        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/role")
    public CollectionModel<EntityModel<Role>> all()
    {
        List<EntityModel<Role>> roles = repository.findAll().stream() //
            .map(assembler::toModel) //
            .collect(Collectors.toList());

        return CollectionModel.of(roles, linkTo(methodOn(RoleController.class).all()).withSelfRel());
    }

    @GetMapping("/role/{id}")
    public EntityModel<Role> findById(@PathVariable Long id)
    {
        Role role = repository.findById(id) //
            .orElseThrow(() -> new EntityNotFoundException(Role.class, id));

        return assembler.toModel(role);
    }

}
