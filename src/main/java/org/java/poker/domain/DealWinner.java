package org.java.poker.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DealWinner.
 */
@Entity
@Table(name = "deal_winner")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DealWinner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private Player winner;

    @OneToOne
    @JoinColumn(unique = true)
    private Game game;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getWinner() {
        return winner;
    }

    public DealWinner winner(Player player) {
        this.winner = player;
        return this;
    }

    public void setWinner(Player player) {
        this.winner = player;
    }

    public Game getGame() {
        return game;
    }

    public DealWinner game(Game game) {
        this.game = game;
        return this;
    }

    public void setGame(Game game) {
        this.game = game;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DealWinner)) {
            return false;
        }
        return id != null && id.equals(((DealWinner) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DealWinner{" +
            "id=" + getId() +
            "}";
    }
}
