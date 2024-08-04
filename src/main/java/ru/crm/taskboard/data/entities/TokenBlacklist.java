package ru.crm.taskboard.data.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "token_blacklist")
public class TokenBlacklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "token")
    private String token;
    @Column(name = "expiration_time")
    private LocalDateTime expirationTime;
    @CreationTimestamp
    @Column(name = "blacklist_time")
    private LocalDateTime blacklistTime;
}
