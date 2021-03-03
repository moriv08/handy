package ru.handy.handy.models.mappers;

public enum Labels {

    YOUR_NEW_LEAD("Новая заявка"),
    ALL_NEW_LEADS("Все новые заявки"),

    YOUR_ACTIVE_LEADS("Активные заявки"),
    ALL_ACTIVE_LEADS("Все активные заявки"),

    YOUR_LEADS("Все Ваши заявки"),
    ALL_LEADS("Все заявки"),
    YOUR_IN_WORK_LEADS("В работе"),

    YESTERDAY_CALL("Просроченный звонок"),
    TODAY_CALL("Звонок сегодня"),


    REQUIRED_DOCS("Необходимо предоставить документы"),
    ENDING_BOOKING("Необходимо продлить бронь"),


    THE_ROLE("role");

    public String title;

    Labels(String title) {
        this.title = title;
    }
}
