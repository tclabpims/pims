package com.dpattern.samples;

/**
 * Created by lenovo on 2016/9/23.
 */
public final class Wife extends Member {

    private final Husband husband;

    public Wife(Husband husband) {
        this.husband = husband;
    }

    @Override
    public Gender getMemberGender() {
        return Gender.F;
    }
}
