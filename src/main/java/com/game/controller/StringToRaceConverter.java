package com.game.controller;

import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.core.convert.converter.Converter;

public class StringToRaceConverter implements Converter<String, Race> {
    @Override
    public Race convert(String source) {
        try {
            return Race.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}