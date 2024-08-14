package com.slava.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "players1_id", referencedColumnName = "id")
    private Player player1;

    @ManyToOne
    @JoinColumn(name = "players2_id", referencedColumnName = "id")
    private Player player2;

    @ManyToOne
    @JoinColumn(name = "winner_id", referencedColumnName = "id")
    private Player winner;

}
