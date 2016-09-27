package com.dpattern.samples;

/**
 * Created by lenovo on 2016/9/23.
 */
public final class Husband extends Member {

    private final Wife wife;

    public Husband(Wife wife) {
        this.wife = wife;
    }

    @Override
    public Gender getMemberGender() {
        return Gender.M;
    }
}
