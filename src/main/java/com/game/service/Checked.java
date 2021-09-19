package com.game.service;

import com.game.entity.Player;
import com.game.exception.BadRequestException;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.TimeZone;

@Component
public class Checked {


    public void checkAdd(Player player) {
        player.setId(null);
        player.setLevel(null);
        player.setUntilNextLevel(null);
        if (player.getName() == null
                || player.getTitle() == null || player.getRace() == null
                || player.getProfession() == null || player.getBirthday() == null
                || player.getExperience() == null) {
            throw new BadRequestException("Bad Request");
        }
        if (player.getName().length() > 12 || player.getTitle().length() > 30) {
            throw new BadRequestException("Bad Request");
        }
        if (player.getName().equals("")) {
            throw new BadRequestException("Bad Request");
        }
        if (player.getExperience() < 0 || player.getExperience() > 10000000) {
            throw new BadRequestException("Bad Request");
        }
        if (player.getBirthday().getTime() < 0) {
            throw new BadRequestException("Bad Request");
        }
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(player.getBirthday());
        int year = cal.get(Calendar.YEAR);
        if (year < 2000 || year > 3000) {
            throw new BadRequestException("Bad Request");
        }

    }


    public void checkId(String id) {
        try {
            Long iD = Long.parseLong(id);
            if (iD <= 0) {
                throw new BadRequestException("Bad ID");
            }

        } catch (Exception e) {
            throw new BadRequestException("Bad ID");
        }

    }

    public void checkUpdate(Player player) {
        if (player.getExperience() != null
                && (player.getExperience() < 0
                || player.getExperience() > 10000000)) {
            throw new BadRequestException("Bad expirence");
        }
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(player.getBirthday());
        int year = cal.get(Calendar.YEAR);
        if (year < 2000 || year > 3000) {
            throw new BadRequestException("Bad Request");
        }

    }
}
