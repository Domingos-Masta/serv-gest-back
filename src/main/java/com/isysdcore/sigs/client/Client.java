/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.client;

import com.isysdcore.sigs.client_profile.ClientProfile;
import com.isysdcore.sigs.payment.Payment;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author domingos.fernando
 */
@Entity
@Document("Client")
@Data
@EqualsAndHashCode
@ToString
public class Client implements Serializable
{

    @Id
    private String id;

    @NotNull
    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @NotNull
    @Column(name = "phone", unique = true)
    private String phone;

    @NotNull
    @Column(name = "dob")
    @Temporal(TemporalType.DATE)
    private Date dob;

    @NotNull
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "client")
    private Collection<ClientProfile> clientProfiles;

    @OneToMany(mappedBy = "clientProfile")
    private Collection<Payment> payments;

}
