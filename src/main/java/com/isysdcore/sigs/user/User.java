/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.isysdcore.sigs.payment.Payment;
import com.isysdcore.sigs.role.Role;
import com.isysdcore.sigs.util.UniqueEmail;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author domingos.fernando
 */
@Entity
@Table(name = "users")
@Relation(value = "user", collectionRelation = "userList")
@Data
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties(value = "password", allowSetters = true)
public class User implements Serializable, UserDetails
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @UniqueEmail(message = "Email ja registrado")
    @Size(max = 255)
    @Basic(optional = false)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    @NotBlank(message = "Phone is mandatory")
    @Basic(optional = false)
    @Size(max = 255)
    @Column(name = "phone", unique = true)
    private String phone;

    @NotNull
    @NotEmpty
    @Column(name = "password")
    private String password;

    @Size(max = 10)
    @Column(name = "enabled", columnDefinition = "Boolean default true")
    private Boolean enabled;

    @ManyToOne
    @JoinColumn(name = "fk_role")
    private Role role;

    @OneToMany(mappedBy = "operator")
    private Collection<Payment> payments;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return new ArrayList<>();
    }

    @Override
    public String getUsername()
    {
        return this.email.isEmpty() ? this.phone : this.email;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return this.getEnabled();
    }

}
