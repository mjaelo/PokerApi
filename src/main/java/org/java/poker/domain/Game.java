package org.java.poker.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Game.
 */
@Entity
@Table(name = "game")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "card_1")
    private Long card1;

    @Column(name = "card_2")
    private Long card2;

    @Column(name = "card_3")
    private Long card3;

    @Column(name = "card_4")
    private Long card4;

    @Column(name = "card_5")
    private Long card5;

    @Column(name = "player_1_id")
    private Long player1Id;

    @Column(name = "player_2_id")
    private Long player2Id;

    @OneToOne
    @JoinColumn(unique = true)
    private Player player2;

    @OneToOne
    @JoinColumn(unique = true)
    private Player player1;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCard1() {
        return card1;
    }

    public Game card1(Long card1) {
        this.card1 = card1;
        return this;
    }

    public void setCard1(Long card1) {
        this.card1 = card1;
    }

    public Long getCard2() {
        return card2;
    }

    public Game card2(Long card2) {
        this.card2 = card2;
        return this;
    }

    public void setCard2(Long card2) {
        this.card2 = card2;
    }

    public Long getCard3() {
        return card3;
    }

    public Game card3(Long card3) {
        this.card3 = card3;
        return this;
    }

    public void setCard3(Long card3) {
        this.card3 = card3;
    }

    public Long getCard4() {
        return card4;
    }

    public Game card4(Long card4) {
        this.card4 = card4;
        return this;
    }

    public void setCard4(Long card4) {
        this.card4 = card4;
    }

    public Long getCard5() {
        return card5;
    }

    public Game card5(Long card5) {
        this.card5 = card5;
        return this;
    }

    public void setCard5(Long card5) {
        this.card5 = card5;
    }

    public Long getPlayer1Id() {
        return player1Id;
    }

    public Game player1Id(Long player1Id) {
        this.player1Id = player1Id;
        return this;
    }

    public void setPlayer1Id(Long player1Id) {
        this.player1Id = player1Id;
    }

    public Long getPlayer2Id() {
        return player2Id;
    }

    public Game player2Id(Long player2Id) {
        this.player2Id = player2Id;
        return this;
    }

    public void setPlayer2Id(Long player2Id) {
        this.player2Id = player2Id;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Game player2(Player player) {
        this.player2 = player;
        return this;
    }

    public void setPlayer2(Player player) {
        this.player2 = player;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Game player1(Player player) {
        this.player1 = player;
        return this;
    }

    public void setPlayer1(Player player) {
        this.player1 = player;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Game)) {
            return false;
        }
        return id != null && id.equals(((Game) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Game{" +
            "id=" + getId() +
            ", card1=" + getCard1() +
            ", card2=" + getCard2() +
            ", card3=" + getCard3() +
            ", card4=" + getCard4() +
            ", card5=" + getCard5() +
            ", player1Id=" + getPlayer1Id() +
            ", player2Id=" + getPlayer2Id() +
            "}";
    }
}
