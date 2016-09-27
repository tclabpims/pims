package com.dpattern.samples;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lenovo on 2016/9/23.
 */
public final class Family {

   private final Husband husband;

   private final Wife wife;

   private final Set<Baby> babies;

    public Family(Husband husband, Wife wife,Set<Baby> babies) {
        this.husband = husband;
        this.wife = wife;
        this.babies = babies;
    }

    public Husband getHusband() {
        return husband;
    }

    public Wife getWife() {
        return wife;
    }

    public Set<Baby> getBabies() {
        return babies;
    }
}
