package com.producer.plaidclient.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String itemId;

    private String accessToken;

    private LocalDateTime lastUpdated;

    private String cursor;

    @JoinColumn(name = "user", nullable = false)
    @Column(name = "user")
    private Long userId;

    @Column(name = "action_required")
    @Enumerated(EnumType.STRING)
    private Action actionRequired;

    public enum Action {
        Initial,
        None,
        SYNC_UPDATES_AVAILABLE,
        Error,
        NewAccount,
        Expiring

    }

}
