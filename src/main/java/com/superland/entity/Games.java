package com.superland.entity;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name ="m_games")
@Data
//@Builder(toBuilder = true)
public class Games {
    @Id
    private String id;
    private  String name;
    private Integer pointPrice;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
