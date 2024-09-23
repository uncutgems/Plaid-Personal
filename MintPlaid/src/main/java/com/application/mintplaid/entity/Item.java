package com.application.mintplaid.entity;

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

    @JoinColumn(name = "id", nullable = false)
    @Column(name = "user_id")
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
