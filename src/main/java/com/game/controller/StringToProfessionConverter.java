package com.game.controller;


import com.game.entity.Profession;
import org.springframework.core.convert.converter.Converter;

public class StringToProfessionConverter implements Converter<String, Profession> {
    @Override
    public Profession convert(String source) {
        try {
            return Profession.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}