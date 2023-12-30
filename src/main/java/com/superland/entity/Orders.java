package com.superland.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_order")
@Data
public class Orders {
    @Id
    @GenericGenerator(strategy = "uuid2", name = "system-uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "order_id")
    private String id;
    private Integer grossAmount;
    private Integer points;
    private String status;
    @ManyToOne()
    @JsonBackReference
    private UserAccounts account;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @PrePersist
    protected void onCreate() {
        status = "PENDING";
        createdAt = LocalDateTime.now();
    }
}
