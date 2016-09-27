package com.dpattern.samples;

import java.util.Set;

/**
 * Created by lenovo on 2016/9/23.
 */
public class Baby extends Member {

    private Set<String> mostLikeFood;

    private boolean likeCry;

    private boolean lively;

    private Gender babyGender;

    public Baby(Set<String> mostLikeFood, boolean likeCry, boolean lively, Gender babyGender) {
        this.mostLikeFood = mostLikeFood;
        this.likeCry = likeCry;
        this.lively = lively;
        this.babyGender = babyGender;
    }

    public Set<String> getMostLikeFood() {
        return mostLikeFood;
    }

    public boolean isLikeCry() {
        return likeCry;
    }

    public boolean isLively() {
        return lively;
    }

    @Override
    Gender getMemberGender() {
        return babyGender;
    }
}
