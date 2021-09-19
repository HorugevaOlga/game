package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

public class SpecBuilder {
    public static Specification<Player> buildFilterSpec(String name,
                                                        String title,
                                                        Race race,
                                                        Profession profession,
                                                        Long after,
                                                        Long before,
                                                        Boolean banned,
                                                        Integer minExperience,
                                                        Integer maxExperience,
                                                        Integer minLevel,
                                                        Integer maxLevel) {
        Specification specUCN = Specification.<Player>where(
                        equalsFiledEnumRace(race, "race"))
                .and(likeFiled(name, "name"))
                .and(likeFiled(title, "title"))
                .and(equalsFiledEnumProfession(profession, "profession"))
                .and(greaterThanOrEqualFieldDate(after, "birthday"))
                .and(lessThanOrEqualFieldDate(before, "birthday"))
                .and(equalsBooleanFiled(banned, "banned"))
                .and(greaterThanOrEqualsFieldExperience(minExperience, "experience"))
                .and(lessThanOrEqualsFieldLevel(maxExperience, "experience"))
                .and(greaterThanOrEqualsFieldExperience(minLevel, "level"))
                .and(lessThanOrEqualsFieldLevel(maxLevel, "level"));
        return specUCN;
    }


    private static <T> Specification<T> likeFiled(String number, String filed) {
        return (root, query, criteriaBuilder) -> Optional.ofNullable(number)
                .map(value -> criteriaBuilder.like(root.get(filed), "%" + value + "%"))
                .orElse(null);
    }

    private static <T> Specification<T> greaterThanOrEqualsFieldExperience(Integer amount, String filed) {
        return (root, query, criteriaBuilder) -> Optional.ofNullable(amount)
                .map(value -> {
                    if (BigDecimal.valueOf(0L).equals(value)) {
                        return criteriaBuilder.or(
                                criteriaBuilder.isNull(root.get(filed)),
                                criteriaBuilder.greaterThanOrEqualTo(root.get(filed), value)
                        );
                    } else {
                        return criteriaBuilder.greaterThanOrEqualTo(root.get(filed), value);
                    }
                })
                .orElse(null);
    }

    private static <T> Specification<T> lessThanOrEqualsFieldLevel(Integer level, String filed) {
        return (root, query, criteriaBuilder) -> Optional.ofNullable(level)
                .map(value -> {
                    if (BigDecimal.valueOf(0L).equals(value)) {
                        return criteriaBuilder.or(
                                criteriaBuilder.isNull(root.get(filed)),
                                criteriaBuilder.lessThanOrEqualTo(root.get(filed), value)
                        );
                    } else {
                        return criteriaBuilder.lessThanOrEqualTo(root.get(filed), value);
                    }
                })
                .orElse(null);
    }

    private static <T> Specification<T> equalsBooleanFiled(Boolean booleanField, String filed) {
        return (root, query, criteriaBuilder) -> Optional.ofNullable(booleanField)
                .map(value -> criteriaBuilder.or(
                        criteriaBuilder.equal(root.get(filed), value),
                        criteriaBuilder.isNull(root.get(filed))))
                .orElse(null);
    }

    private static <T> Specification<T> equalsFiledEnumRace(Race race, String filed) {
        if (race != null) {
            return (root, query, criteriaBuilder) -> Optional.ofNullable(race)
                    .map(value -> criteriaBuilder.equal(root.get(filed), value))
                    .orElse(null);
        } else {
            return null;
        }
    }

    private static <T> Specification<T> equalsFiledEnumProfession(Profession profession, String filed) {
        if (profession != null) {
            return (root, query, criteriaBuilder) -> Optional.ofNullable(profession)
                    .map(value -> criteriaBuilder.equal(root.get(filed), value))
                    .orElse(null);
        } else {
            return null;
        }
    }

    private static <T> Specification<T> greaterThanOrEqualFieldDate(Long dateFrom, String filed) {
        if (dateFrom != null) {
            Date date = new Date(dateFrom);
            return (root, query, criteriaBuilder) -> Optional.of(date)
                    .map(value -> criteriaBuilder.greaterThanOrEqualTo(root.get(filed), value))
                    .orElse(null);
        } else return null;
    }

    private static <T> Specification<T> lessThanOrEqualFieldDate(Long dateTo, String filed) {
        if (dateTo != null) {
            Date date = new Date(dateTo);
            return (root, query, criteriaBuilder) -> Optional.of(date)
                    .map(value -> criteriaBuilder.lessThanOrEqualTo(root.get(filed), value))
                    .orElse(null);
        } else return null;
    }
}
