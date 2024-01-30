package com.kingsly.robotapocalypse.models;

public enum Gender {
    MALE("Male"), FEMALE("Female"), OTHER("Other");

    private String label;

    private Gender(String gender) {
        this.label = gender;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
