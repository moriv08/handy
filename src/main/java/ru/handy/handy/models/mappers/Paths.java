package ru.handy.handy.models.mappers;

public enum Paths {

    API("/api"),
    FLAT("/flat"),
    BOOKING("/booking"),
    JURIDICAL("/juridical"),
    SALE("/sale"),

//    FLAT_ADMIN(API.path + FLAT.path +"/advanced"),
    FLAT_ADMIN(API.path + FLAT.path +"/"),
    FLAT_SUPERUSER(API.path + FLAT.path +"/superuser/"),
    FLAT_EXPERT(API.path + FLAT.path +"/expert/"),
    FLAT_TOURISM(API.path + FLAT.path +"/tourism/"),

    BOOKING_ADMIN(API.path + BOOKING.path + "/advanced/"),
    BOOKING_SUPERUSER(API.path + BOOKING.path + "/advanced/"),
    BOOKING_EXPERT(API.path + BOOKING.path + "/advanced/"),
    BOOKING_TOURISM(API.path + BOOKING.path + "/tourism/"),

    JURIDICAL_ADMIN(API.path + JURIDICAL.path + "/advanced/"),
    JURIDICAL_JURIST(API.path + JURIDICAL.path + "/advanced/"),
    JURIDICAL_SUPERUSER(API.path + JURIDICAL.path + "/advanced/"),
    JURIDICAL_EXPERT(API.path + JURIDICAL.path + "/expert/"),
    JURIDICAL_TOURISM(API.path + JURIDICAL.path + "/tourism/"),

    SALE_ALL(API.path + SALE.path + "/advanced/");
//    ADVERTISER_ADMIN_ADVERTISER_EXPERT_SUPERUSER("");

    public String path;

    Paths(String path) {
        this.path = path;
    }
}
