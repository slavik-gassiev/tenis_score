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
public class Matche {

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

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }
}
