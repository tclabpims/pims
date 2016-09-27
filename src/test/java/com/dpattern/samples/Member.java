package com.dpattern.samples;

/**
 * Created by lenovo on 2016/9/23.
 */
public abstract class Member {

    private String memberName;

    private int memberAge;

    private String memberBirthPlace;

    private String memberAppellation;

    public static enum Gender {
        M('M'),F('F');

        private final char flag;

        private Gender(char c) {
            this.flag = c;
        }
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getMemberAge() {
        return memberAge;
    }

    public void setMemberAge(int memberAge) {
        this.memberAge = memberAge;
    }

    public String getMemberBirthPlace() {
        return memberBirthPlace;
    }

    public void setMemberBirthPlace(String memberBirthPlace) {
        this.memberBirthPlace = memberBirthPlace;
    }

    public String getMemberAppellation() {
        return memberAppellation;
    }

    public void setMemberAppellation(String memberAppellation) {
        this.memberAppellation = memberAppellation;
    }

    abstract Gender getMemberGender();
}
