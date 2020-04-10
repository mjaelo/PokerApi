package org.java.poker.service;


import org.java.poker.domain.Player;
import org.java.poker.domain.User;
import org.java.poker.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public void addPlayer(User user) {
        Player player = new Player();
        long cash = 1000;

        player.setCash(cash);

        playerRepository.save(player);
    }

    public Player getPlayer(Long id) {
        Player player = playerRepository.findById(id).get();
        return player;
    }
}
