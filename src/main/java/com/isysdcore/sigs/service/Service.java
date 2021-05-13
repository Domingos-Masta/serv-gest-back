/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.service;

import com.isysdcore.sigs.client_profile.ClientProfile;
import com.isysdcore.sigs.provider.Provider;
import com.isysdcore.sigs.service_type.ServiceType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * @author domingos.fernando
 */
@Entity
//@Document("Service")
@Table(name = "services")
@Data
@EqualsAndHashCode
@ToString
public class Service implements Serializable
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

    @ManyToOne
    @JoinColumn(name = "service_type")
    private ServiceType serviceType;

    @NotNull
    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "provider")
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "parent_service")
    private Service parentService;

    @OneToMany(mappedBy = "parentService")
    private Collection<Service> childServices;

    @OneToMany(mappedBy = "service")
    private Collection<ClientProfile> clientProfiles;

}
