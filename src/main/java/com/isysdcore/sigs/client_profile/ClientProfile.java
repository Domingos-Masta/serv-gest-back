/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.client_profile;

import com.isysdcore.sigs.client.Client;
import com.isysdcore.sigs.payment.Payment;
import com.isysdcore.sigs.service.Service;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * @author domingos.fernando
 */
@Entity
//@Document("ClientProfile")
@Table(name = "client_profiles")
@Data
@EqualsAndHashCode
@ToString
public class ClientProfile implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "started_at")
    private Date started_at;

    @NotNull
    @Column(name = "ended_at")
    private Date ended_at;

    @NotNull
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;

    @NotNull
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @ManyToOne
    @JoinColumn(name = "service")
    private Service service;

    @ManyToOne
    @JoinColumn(name = "client")
    private Client client;

    @OneToMany(mappedBy = "clientProfile")
    private Collection<Payment> payments;

}
