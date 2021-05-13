/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isysdcore.sigs.payment;

import com.isysdcore.sigs.client_profile.ClientProfile;
import com.isysdcore.sigs.user.User;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
//@Document("Payment")
@Table(name = "payments")
@Data
@EqualsAndHashCode
@ToString
public class Payment implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @Column(name = "payed_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date payedAt;

    @NotNull
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "fk_client_profile")
    private ClientProfile clientProfile;

    @NotNull
    @Column(name = "payment_status")
    private String paymentStatus;

    @ManyToOne
    @JoinColumn(name = "operator")
    private User operator;

}
