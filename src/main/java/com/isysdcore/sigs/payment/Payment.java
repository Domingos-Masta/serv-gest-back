/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.payment;

import com.isysdcore.sigs.client.Client;
import com.isysdcore.sigs.client_profile.ClientProfile;
import com.isysdcore.sigs.service.Service;
import com.isysdcore.sigs.user.User;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
@Document("Payment")
@Data
@EqualsAndHashCode
@ToString
public class Payment implements Serializable
{

    @Id
    private String id;

    @NotNull
    @Column(name = "ammount")
    private BigDecimal ammount;

    @NotNull
    @Column(name = "reference", unique = true)
    private String reference;

    @NotNull
    @Column(name = "quantity")
    private Integer quantity;

    @NotNull
    @Column(name = "payedAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date payedAt;

    @NotNull
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "client")
    private Client client;

    @NotNull
    @Column(name = "paymentStatus")
    private String paymentStatus;

    @ManyToOne
    @JoinColumn(name = "service")
    private Service service;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fkClientProfile")
    private ClientProfile fkClientProfile;

    @ManyToOne
    @JoinColumn(name = "operator")
    private User operator;

}
