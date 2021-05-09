/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.user;

import com.isysdcore.sigs.exceptions.EntityNotFoundException;
import com.isysdcore.sigs.util.Constants;
import com.isysdcore.sigs.util.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author domingos.fernando
 */
@RestController
@RequestMapping(Constants.DEFAULT_APP_URL_BASE + Constants.DEFAULT_APP_API_VERSION + Constants.APP_NAME)
public class UserController
{

    @Autowired
    private final UserRepository repository;

    @Autowired
    private final UserModelAssembler assembler;

    public UserController(UserRepository repository, UserModelAssembler assembler)
    {

        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping(value = "/user/pages", params = {"page", "size", "sort"})
    public Page<User> pages(@RequestParam("page") int page,
                            @RequestParam("size") int size,
                            @RequestParam("sort") int sort)
    {

        Pageable sortedById;
        if (sort > 0) {
            sortedById = PageRequest.of(page, size, Sort.by("id").ascending());
        }
        else {
            sortedById = PageRequest.of(page, size, Sort.by("id").descending());
        }
        Page<User> usersResult = repository.findAll(sortedById); //

        return usersResult;
    }

    @GetMapping("/user")
    public CollectionModel<EntityModel<User>> all()
    {
        List<EntityModel<User>> users = repository.findAll().stream() //
            .map(assembler::toModel) //
            .collect(Collectors.toList());

        return CollectionModel.of(users, linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    @PostMapping("/user")
    public ResponseEntity<?> save(@RequestBody User user)
    {
        EntityModel<User> entityModel = assembler.toModel(repository.save(user));

        return ResponseEntity //
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
            .body(entityModel);
    }

    @GetMapping("/user/{id}")
    public EntityModel<User> findById(@PathVariable Long id)
    {
        User user = repository.findById(id) //
            .orElseThrow(() -> new EntityNotFoundException(new User(), id));

        return assembler.toModel(user);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> update(@RequestBody User newUser, @PathVariable Long id)
    {
        User updatedUser = repository.findById(id) //
            .map(user -> {
                user.setName(newUser.getName());
                user.setPhone(newUser.getPhone());
                user.setEmail(newUser.getEmail());
                user.setRole(newUser.getRole());
                return repository.save(user);
            }) //
            .orElseGet(() -> {
                newUser.setId(id);
                return repository.save(newUser);
            });

        EntityModel<User> entityModel = assembler.toModel(updatedUser);

        return ResponseEntity //
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
            .body(entityModel);
    }

    @PutMapping("/user/resetPassword/{id}")
    public ResponseEntity<?> updatePassword(@RequestBody User newUser, @PathVariable Long id)
    {
        User updatedUser = repository.findById(id) //
            .map(user -> {
                user.setPassword(new PasswordEncoder().getPasswordEncoder().encode(newUser.getPassword()));
                return repository.save(user);
            }) //
            .orElseThrow(() -> new EntityNotFoundException(new User(), id));

        EntityModel<User> entityModel = assembler.toModel(updatedUser);

        return ResponseEntity //
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
            .body(entityModel);
    }

}
