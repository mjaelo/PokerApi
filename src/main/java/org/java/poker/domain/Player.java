package org.java.poker.domain;


import javax.persistence.*;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nick;

    private Long cash;

    private Long card_1;
    private Long card_2;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Long getCash() {
        return cash;
    }

    public void setCash(Long cash) {
        this.cash = cash;
    }

    public Long getCard_1() {
        return card_1;
    }

    public void setCard_1(Long card_1) {
        this.card_1 = card_1;
    }

    public Long getCard_2() {
        return card_2;
    }

    public void setCard_2(Long card_2) {
        this.card_2 = card_2;
    }
}
