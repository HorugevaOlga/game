package com.game.service;

import com.game.entity.Player;
import com.game.exception.NotFoundException;
import com.game.repository.PlayerRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final Checked checked;

    public PlayerService(PlayerRepository playerRepository, Checked checked) {
        this.playerRepository = playerRepository;
        this.checked = checked;
    }

    public Player add(Player player) {
        checked.checkAdd(player);
        player.setLevel(levelPlayer(player));
        player.setUntilNextLevel(untilNextLevePlayer(player));
        return playerRepository.save(player);
    }


    public List<Player> getAll(Pageable pageable, Specification<Player> sp) {
        return playerRepository.findAll(sp, pageable).getContent();
    }

    public List<Player> getAll(Specification<Player> sp) {
        return playerRepository.findAll(sp);
    }

    public List<Player> getAll() {
        return playerRepository.findAll();
    }

    public void delById(String id) {
        getById(id);

        playerRepository.deleteById(Long.valueOf(id));
    }

    public Player getById(String id) {
        checked.checkId(id);
        return playerRepository.findById(Long.valueOf(id)).
                orElseThrow(() -> new NotFoundException("Сущность не найдена"));
    }

    public Player update(Player player, String id) {
        Player entity = getById(id);
        setToExternalPlayer(player, entity);
        //set Experience from entity
        checked.checkUpdate(player);
        player.setId(null);
        setToEntityData(player, entity);
        return playerRepository.save(entity);
    }

    private void setToEntityData(Player player, Player entity) {
        //set data in entity
        entity.setName(player.getName());
        entity.setTitle(player.getTitle());
        entity.setRace(player.getRace());
        entity.setProfession(player.getProfession());
        entity.setExperience(player.getExperience());
        entity.setLevel(levelPlayer(player));
        entity.setUntilNextLevel(untilNextLevePlayer(player));
        entity.setBirthday(player.getBirthday());
        entity.setBanned(player.getBanned());
    }

    private void setToExternalPlayer(Player player, Player entity) {
        if (player.getExperience() == null) {
            player.setExperience(entity.getExperience());
        }
        if (player.getBirthday() == null) {
            player.setBirthday(entity.getBirthday());
        }
        if (player.getLevel() == null) {
            player.setLevel(entity.getLevel());
        }
        if (player.getUntilNextLevel() == null) {
            player.setUntilNextLevel(entity.getUntilNextLevel());
        }
        if (player.getName() == null) {
            player.setName(entity.getName());
        }
        if (player.getTitle() == null) {
            player.setTitle(entity.getTitle());
        }
        if (player.getRace() == null) {
            player.setRace(entity.getRace());
        }
        if (player.getProfession() == null) {
            player.setProfession(entity.getProfession());
        }
        if (player.getBanned() == null) {
            player.setBanned(entity.getBanned());
        }
    }

    private Integer levelPlayer(Player player) {
        Integer exp = player.getExperience();
        Integer level = (int) (((Math.sqrt(2500 + 200 * exp)) - 50) / 100);
        return level;
    }

    private Integer untilNextLevePlayer(Player player) {
        Integer exp = player.getExperience();
        Integer untilNextLeve = 50 * (levelPlayer(player) + 1) * (levelPlayer(player) + 2) - exp;
        return untilNextLeve;
    }

}