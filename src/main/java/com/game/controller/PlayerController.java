package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import com.game.service.SpecBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("rest/players")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @GetMapping
    @RequestMapping(method = RequestMethod.GET)
    public List<Player> getAll(@RequestParam(value = "name", required = false) String name,
                               @RequestParam(value = "title", required = false) String title,
                               @RequestParam(value = "race", required = false) Race race,
                               @RequestParam(value = "profession", required = false) Profession profession,
                               @RequestParam(value = "after", required = false) Long after,
                               @RequestParam(value = "before", required = false) Long before,
                               @RequestParam(value = "banned", required = false) Boolean banned,
                               @RequestParam(value = "minExperience", required = false) Integer minExperience,
                               @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                               @RequestParam(value = "minLevel", required = false) Integer minLevel,
                               @RequestParam(value = "maxLevel", required = false) Integer maxLevel,
                               @RequestParam(value = "order", required = false) PlayerOrder order,
                               @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                               @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        Pageable limitResult = createLimitResult(pageNumber, pageSize, order);
        Specification sp = SpecBuilder.buildFilterSpec(name, title, race, profession, after, before,
                banned, minExperience, maxExperience, minLevel, maxLevel);
        return playerService.getAll(limitResult, sp);
    }

    private Pageable createLimitResult(Integer pageNumber, Integer pageSize, PlayerOrder order) {
        Integer number = pageNumber;
        Integer size = pageSize;
        if (pageNumber == null) {
            number = 0;
        }

        if (pageSize == null) {
            size = 3;
        }
        if (order == null) {
            return PageRequest.of(number, size, Sort.by(PlayerOrder.ID.getFieldName()));
        } else {
            return PageRequest.of(number, size, Sort.by(order.getFieldName()));
        }
    }

    @GetMapping("/count")
    public Integer count(@RequestParam(value = "name", required = false) String name,
                         @RequestParam(value = "title", required = false) String title,
                         @RequestParam(value = "race", required = false) Race race,
                         @RequestParam(value = "profession", required = false) Profession profession,
                         @RequestParam(value = "after", required = false) Long after,
                         @RequestParam(value = "before", required = false) Long before,
                         @RequestParam(value = "banned", required = false) Boolean banned,
                         @RequestParam(value = "minExperience", required = false) Integer minExperience,
                         @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                         @RequestParam(value = "minLevel", required = false) Integer minLevel,
                         @RequestParam(value = "maxLevel", required = false) Integer maxLevel,
                         @RequestParam(value = "order", required = false) PlayerOrder order,
                         @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        Specification sp = SpecBuilder.buildFilterSpec(name, title, race, profession, after, before,
                banned, minExperience, maxExperience, minLevel, maxLevel);
        return playerService.getAll(sp).size();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Player getPlayerById(@PathVariable("id") String id) {
        return playerService.getById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deletePlayerById(@PathVariable("id") String id) {
        playerService.delById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST) //
    @ResponseBody
    public Player updatePlayer(@RequestBody Player player, @PathVariable("id") String id) {
        return playerService.update(player, id);
    }


    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Player addPlayer(@RequestBody Player player) {

        return playerService.add(player);
    }

}
