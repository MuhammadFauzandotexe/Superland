package com.superland.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "m_accounts")
@Data
public class UserAccounts {
    @Id
    @GenericGenerator(strategy = "uuid2", name = "system-uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "account_id")
    private String userId;
    private Integer point;
    private String statusMember;
    private String profileImage;
    private String qrImage;
    @OneToOne(fetch = FetchType.EAGER)
    private UserCredentials credential;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account")
    @JsonManagedReference
    private List<Orders> transactions;
}
