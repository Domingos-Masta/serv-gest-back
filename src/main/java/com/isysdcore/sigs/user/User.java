/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.user;

import com.isysdcore.sigs.payment.Payment;
import com.isysdcore.sigs.role.Role;
import com.isysdcore.sigs.util.UniqueEmail;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
@Table(name = "Users")
@Relation(value = "user", collectionRelation = "userList")
@Data
@EqualsAndHashCode
@ToString
public class User implements Serializable, UserDetails
{

    @Id
    private Long id;

    @UniqueEmail
    @Column(name = "email", unique = true)
    private String email;

    @NotEmpty
    private String name;

    @NotEmpty
    @NotBlank(message = "Phone is mandatory")
    @Column(name = "phone", unique = true)
    private String phone;

    @NotEmpty
    @Column(name = "password")
    private String password;

    @Size(max = 10)
    @Column(name = "enabled", columnDefinition = "boolean default true")
    private Boolean enabled;

    @ManyToOne
    @JoinColumn(name = "role")
    private Role role;

    @OneToMany(mappedBy = "operator")
    private Collection<Payment> payments;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUsername()
    {
        return this.email.isEmpty() ? this.phone : this.email;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isAccountNonLocked()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isEnabled()
    {
        return this.getEnabled();
    }

}
