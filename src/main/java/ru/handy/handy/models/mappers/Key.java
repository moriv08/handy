package ru.handy.handy.models.mappers;

public enum Key {
    TEL("10."),
    EXPERT("11."),

    OWNER_NAME("20."),
    ADDRESS("21."),
    FLOOR("22."),
    ROOMS("23."),
    FLAT_SQUARE("24."),
    BALCONY("25."),
    FLAT_CONDITION("26."),
    SOME_EXTRA_INFO("27."),

    BOOGING_LOCATION("30."),
    BOOKING_PRICE("31."),
    BOOKING_ROOMS("32."),
    BOOKING_SQUARE("33."),
    BOOKING_START("34."),
    BOOKING_END("35."),


    INDEX("000");

    public String key;

    Key(String key) {
        this.key = key;
    }
}
