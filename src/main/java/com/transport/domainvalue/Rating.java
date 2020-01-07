package com.transport.domainvalue;

public enum Rating {

    VERY_BAD(1), BAD(2), GOOD(3), VERY_GOOD(4), EXCELLENT(5);

    private int value;

    private Rating(int value) {
        this.value = value;
    }


    public int getValue() {
        return value;
    }

}
