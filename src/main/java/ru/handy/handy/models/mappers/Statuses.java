package ru.handy.handy.models.mappers;


import java.util.ArrayList;
import java.util.List;

public enum Statuses {

    NEW_LEAD("новая заявка", 1),

    NEW_EXPERT_PRICE("ожидает подтверждения оценки",11) ,
    NEW_SUPER_ANSWER("получен комментарий супервайзера", 14),
    NEW_SUPER_PRICE("посмотреть оценку супервайзера", 15),
    CLIENT_REJECT("клиент не согласен", 16),

    NEW_NEGOTIATIONS("ведем переговоры с продавцом", 21),
    CLIENT_TAKING_PRICE("эксперт озвучивает цену", 22),
    CLIENT_THINKS("клиент думает", 23),

    CLIENT_AGREE("клиент согласен", 31),
    CLIENT_OTHER_PROGRAM("клиент покупает без продажи", 32),
    DOC_WAITING("документы отправлены на проверку", 33),
    DOC_OK("документы проверены", 34),

    CALL_TODAY("необходимо сегодня сделать контрольный звонок", 41),
    CALL_YESTERDAY("звонок пропущен! Необходмо сделать звонок", 42),

    DOC_SIGNED("договор подписан", 51),
    SELLING_FLAT("объект в рекламе", 52),
    GOT_PREPAYMENT("принят аванс", 53),
    REGISTRATION("документы на регистрации", 54),
    REGISTRATION_PROBLEM("сложности с регистрацией", 55),

    CONTRACT_CLOSED("сделка закрыта", 100),


    STATUS_ACTIVE("активный", 25),
    DOC_REJECT("продажа невозможна", 101),
    STATUS_PASSIVE("пассивный", 102),
    STATUS_POSTPONED("отложенный", 103),

    STATUS_ARCHIVE("архив", 0);


    public String status;
    public Integer digit;
    Statuses(String status, Integer digit) {
        this.status = status;
        this.digit = digit;
    }

    public static synchronized Integer findOutDigitStatusByStatus(String status){
        List<Statuses> statuses = new ArrayList<>();

        statuses.add(NEW_LEAD);
        statuses.add(NEW_NEGOTIATIONS);
        statuses.add(NEW_EXPERT_PRICE);
        statuses.add(NEW_SUPER_ANSWER);
        statuses.add(NEW_SUPER_PRICE);

        statuses.add(CLIENT_TAKING_PRICE);
        statuses.add(CLIENT_THINKS);
        statuses.add(CLIENT_AGREE);
        statuses.add(CLIENT_REJECT);
        statuses.add(CLIENT_OTHER_PROGRAM);

        statuses.add(CALL_TODAY);
        statuses.add(CALL_YESTERDAY);

        statuses.add(DOC_WAITING);
        statuses.add(DOC_OK);
        statuses.add(DOC_SIGNED);
        statuses.add(DOC_REJECT);

        statuses.add(SELLING_FLAT);

        statuses.add(GOT_PREPAYMENT);
        statuses.add(REGISTRATION);
        statuses.add(REGISTRATION_PROBLEM);
        statuses.add(CONTRACT_CLOSED);

        statuses.add(STATUS_ACTIVE);
        statuses.add(STATUS_ARCHIVE);
        statuses.add(STATUS_PASSIVE);
        statuses.add(STATUS_POSTPONED);

        return statuses.stream()
                .filter(sts -> sts.status.equalsIgnoreCase(status))
                .findFirst().get().digit;
    }

    public static synchronized String findOutStatusByDigitStatus(Integer digitStatus){
        List<Statuses> statuses = new ArrayList<>();

        statuses.add(NEW_LEAD);
        statuses.add(NEW_NEGOTIATIONS);
        statuses.add(NEW_EXPERT_PRICE);
        statuses.add(NEW_SUPER_ANSWER);
        statuses.add(NEW_SUPER_PRICE);

        statuses.add(CLIENT_TAKING_PRICE);
        statuses.add(CLIENT_THINKS);
        statuses.add(CLIENT_AGREE);
        statuses.add(CLIENT_REJECT);
        statuses.add(CLIENT_OTHER_PROGRAM);

        statuses.add(DOC_WAITING);
        statuses.add(DOC_OK);
        statuses.add(DOC_SIGNED);
        statuses.add(DOC_REJECT);

        statuses.add(SELLING_FLAT);

        statuses.add(GOT_PREPAYMENT);
        statuses.add(REGISTRATION);
        statuses.add(REGISTRATION_PROBLEM);
        statuses.add(CONTRACT_CLOSED);

        statuses.add(STATUS_ACTIVE);
        statuses.add(STATUS_ARCHIVE);
        statuses.add(STATUS_PASSIVE);
        statuses.add(STATUS_POSTPONED);

        return statuses.stream()
                .filter(sts -> sts.digit.equals(digitStatus))
                .findFirst().get().status;
    }
}
