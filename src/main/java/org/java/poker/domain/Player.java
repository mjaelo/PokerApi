package org.java.poker.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Player.
 */
@Entity
@Table(name = "player")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "cash")
    private Long cash;

    @Column(name = "card_1")
    private Long card1;

    @Column(name = "card_2")
    private Long card2;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public Player nickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getCash() {
        return cash;
    }

    public Player cash(Long cash) {
        this.cash = cash;
        return this;
    }

    public void setCash(Long cash) {
        this.cash = cash;
    }

    public Long getCard1() {
        return card1;
    }

    public Player card1(Long card1) {
        this.card1 = card1;
        return this;
    }

    public void setCard1(Long card1) {
        this.card1 = card1;
    }

    public Long getCard2() {
        return card2;
    }

    public Player card2(Long card2) {
        this.card2 = card2;
        return this;
    }

    public void setCard2(Long card2) {
        this.card2 = card2;
    }

    public User getUser() {
        return user;
    }

    public Player user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Player)) {
            return false;
        }
        return id != null && id.equals(((Player) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Player{" +
            "id=" + getId() +
            ", nickname='" + getNickname() + "'" +
            ", cash=" + getCash() +
            ", card1=" + getCard1() +
            ", card2=" + getCard2() +
            "}";
    }
}
