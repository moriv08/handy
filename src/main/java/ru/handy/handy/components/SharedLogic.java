package ru.handy.handy.components;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import ru.handy.handy.models.lead_heap.BookingEntity;
import ru.handy.handy.models.lead_heap.LeadEntity;
import ru.handy.handy.models.lead_heap.WeakSideEntity;
import ru.handy.handy.models.mappers.Paths;
import ru.handy.handy.models.mappers.Statuses;
import ru.handy.handy.service.AdministrationsService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import static java.lang.String.valueOf;

@Component
//@Controller
public class SharedLogic {

    private final AdministrationsService administrationsService;

    public SharedLogic(AdministrationsService administrationsService) {
        this.administrationsService = administrationsService;
    }

    public synchronized void addLabelLinksToModel(Model model){

        model.addAttribute("flat_path", "/api/flat/");
        model.addAttribute("booking_path", "/api/booking/");
        model.addAttribute("juridical_path", "/api/juridical/");
        model.addAttribute("advertisment_path", "/api/advertisment/");

    }

    public synchronized void addPageCommonLinksToPersonModel(Model model, LeadEntity lead){

        model.addAttribute("lead", lead);

        model.addAttribute("workingPeriod", workingPeriod(lead));
        model.addAttribute("contractPeriod", contractPeriod(lead));
    }
    public synchronized void addBookingAndWeakSideObjectsToModel(Model model, LeadEntity lead){

        BookingEntity booking = lead.getBooking();
        WeakSideEntity weakSide = lead.getWeakSide();
        if (booking == null)
            booking = new BookingEntity();
        model.addAttribute("booking", booking);
        model.addAttribute("weakSide", weakSide);

        model.addAttribute("lead", lead);
    }

    public void addBookingPageDigitCheckersAtTheModel(Model model){

        Integer active = Statuses.STATUS_ACTIVE.digit;
        Integer passive = Statuses.STATUS_PASSIVE.digit;
        Integer archive = Statuses.STATUS_ARCHIVE.digit;
        Integer postponed = Statuses.STATUS_POSTPONED.digit;
        model.addAttribute("active", active);
        model.addAttribute("passive", passive);
        model.addAttribute("archive", archive);
        model.addAttribute("postponed", postponed);

        Integer clientAgree = Statuses.CLIENT_AGREE.digit;
        Integer clientThinks = Statuses.CLIENT_THINKS.digit;
        Integer clientReject = Statuses.CLIENT_REJECT.digit;
        Integer clientOtherProgram = Statuses.CLIENT_OTHER_PROGRAM.digit;
        model.addAttribute("clientAgree", clientAgree);
        model.addAttribute("clientThinks", clientThinks);
        model.addAttribute("clientReject", clientReject);
        model.addAttribute("clientOtherProgram", clientOtherProgram);
    }
    public void addCommmonHomePageColoredLeadsDigitCheckersAtTheModel(Model model){

        model.addAttribute("announcement", administrationsService.findAdministrationById(1L).getAnnouncement());

        Integer blinkingNewLead = Statuses.NEW_LEAD.digit;
        model.addAttribute("blinkingNewLead", blinkingNewLead);

        Integer redTodayCall = Statuses.CALL_TODAY.digit;
        Integer redYesterdayCall = Statuses.CALL_YESTERDAY.digit;
        model.addAttribute("redTodayCall", redTodayCall);
        model.addAttribute("redYesterdayCall", redYesterdayCall);

        Integer docOk = Statuses.DOC_OK.digit;
        model.addAttribute("docOk", docOk);
        Integer docsChecking = Statuses.DOC_WAITING.digit;
        model.addAttribute("docsChecking", docsChecking);

        Integer greenStartContract = Statuses.DOC_SIGNED.digit;
        Integer greenEndContract = Statuses.CONTRACT_CLOSED.digit;
        model.addAttribute("greenStartContract", greenStartContract);
        model.addAttribute("greenEndContract", greenEndContract);

        Integer yellowExpertWaiting = Statuses.NEW_EXPERT_PRICE.digit;
//        Integer yellowExpert = Statuses.DOC_OK.digit;
        model.addAttribute("yellowExpertWaiting", yellowExpertWaiting);
//        model.addAttribute("yellowLast", yellowLast);

//        Integer blinkRedExpertFirst = Statuses.NEW_SUPER_ANSWER.digit;
//        Integer blinkRedExpertLast = Statuses.NEW_SUPER_PRICE.digit;
//        model.addAttribute("newSuperPrice", blinkRedExpertFirst);
//        model.addAttribute("newSuperAnswer", blinkRedExpertLast);

        Integer newExpertPrice = Statuses.NEW_EXPERT_PRICE.digit;
        model.addAttribute("newExpertPrice", newExpertPrice);

        Integer newSuperPrice = Statuses.NEW_SUPER_PRICE.digit;
        Integer newSuperAnswer = Statuses.NEW_SUPER_ANSWER.digit;
        model.addAttribute("newSuperPrice", newSuperPrice);
        model.addAttribute("newSuperAnswer", newSuperAnswer);
    }

    public int workingPeriod(LeadEntity lead) {

        double days = 0;

        if (lead != null && lead.getLeadDate() != null){

            String startDate = lead.getLeadDate().toString();
            String curentDate = LocalDate.now().toString();

            try{
                if (parser(startDate)[0] > 0 && parser(curentDate)[0] > 0){

                    int[] start = parser(startDate);
                    int[] curent = parser(curentDate);

                    GregorianCalendar calStart = new GregorianCalendar(start[0], start[1], start[2]);
                    GregorianCalendar calCurent = new GregorianCalendar(curent[0], curent[1], curent[2]);
                    long millisStart = calStart.getTime().getTime();
                    long millisCurent = calCurent.getTime().getTime();
                    long difMs = millisCurent - millisStart;
                    long msPerDay = 1000 * 60 * 60 * 24;

                    days = (double) difMs / msPerDay;
                }
            }catch (NumberFormatException e){
                System.out.println("it wasnt nummber " + e.getMessage());
            }
        }
        return (int) days + 1;
    }
    public synchronized int contractPeriod(LeadEntity lead) {

        if(lead.getJuristInfo() == null)
            return 0;
        if (lead.getJuristInfo().getContractStart() == null)
            return 0;

        String startDate = lead.getJuristInfo().getContractStart().toString();
        String curentDate = LocalDate.now().toString();

        double days = 0;

        try{
            if (parser(startDate)[0] > 0 && parser(curentDate)[0] > 0){

                int[] start = parser(startDate);
                int[] curent = parser(curentDate);

                GregorianCalendar calStart = new GregorianCalendar(start[0], start[1], start[2]);
                GregorianCalendar calCurent = new GregorianCalendar(curent[0], curent[1], curent[2]);
                long millisStart = calStart.getTime().getTime();
                long millisCurent = calCurent.getTime().getTime();
                long difMs = millisCurent - millisStart;
                long msPerDay = 1000 * 60 * 60 * 24;

                days = (double) difMs / msPerDay;
            }
        }catch (NumberFormatException e){
            System.out.println("it wasnt nummber " + e.getMessage());
        }

        return (int) days + 1;
    }
    private synchronized int[] parser(String date) throws NumberFormatException{
        int[] res = new int[3];
        String[] stringRes = date.split("-");
        for (int i = 0; i < stringRes.length; i++) {
            res[i] = Integer.parseInt(stringRes[i]);
        }
        return res;
    }
    public synchronized String reformatThymeleafDateIntoLocal(String thymeleafDate) throws IndexOutOfBoundsException{

        String delimeter = null;
        String emptyAnswer = "1111-11-11";
        int i = -1;
        while (++i < thymeleafDate.length()) {
            if (thymeleafDate.charAt(i) == '-' || thymeleafDate.charAt(i) == '/' || thymeleafDate.charAt(i) == '.')
                delimeter = valueOf(thymeleafDate.charAt(i));
        }

        if (delimeter == null)
            return emptyAnswer;

        String[] splitter = thymeleafDate.split(delimeter);

        if (splitter.length != 3)
            return emptyAnswer;

        String year;
        String month = splitter[1].trim();
        String days;
        if (splitter[0].length() > splitter[2].length()){
            year = splitter[0];
            days  = splitter[2];
        }else {
            year = splitter[2];
            days  = splitter[0];
        }
        return year.trim() + "-" + month + "-" + days.trim();
    }

    //************* searcher wanted */
    public synchronized int findWhatItIs(String wanted){

        int i = -1;
        if (wanted.trim().length() == 0)
            return -1;

        while ((wanted.charAt(++i) > 47 && wanted.charAt(i) < 59) || (wanted.charAt(i) == ' ') || (wanted.charAt(i) == '-')){
            if (wanted.charAt(i) == ' ' || wanted.charAt(i) == '-')
                return 2;
            if (i == (wanted.length() - 1))
                return 1;
        }
        return 3;
    }

    public List<String> makeAllLeadStatusList(){
        List<String> allLeadStatusList = new ArrayList<>();

        allLeadStatusList.add(Statuses.NEW_LEAD.status);

        allLeadStatusList.add(Statuses.NEW_EXPERT_PRICE.status);
        allLeadStatusList.add(Statuses.NEW_NEGOTIATIONS.status);
        allLeadStatusList.add(Statuses.NEW_SUPER_ANSWER.status);
        allLeadStatusList.add(Statuses.NEW_SUPER_PRICE.status);

        allLeadStatusList.add(Statuses.CLIENT_TAKING_PRICE.status);
        allLeadStatusList.add(Statuses.CLIENT_THINKS.status);
        allLeadStatusList.add(Statuses.CLIENT_AGREE.status);
        allLeadStatusList.add(Statuses.CLIENT_REJECT.status);

        allLeadStatusList.add(Statuses.DOC_WAITING.status);
        allLeadStatusList.add(Statuses.DOC_OK.status);
        allLeadStatusList.add(Statuses.DOC_SIGNED.status);
        allLeadStatusList.add(Statuses.DOC_REJECT.status);

        allLeadStatusList.add(Statuses.SELLING_FLAT.status);

        allLeadStatusList.add(Statuses.GOT_PREPAYMENT.status);
        allLeadStatusList.add(Statuses.REGISTRATION.status);
        allLeadStatusList.add(Statuses.REGISTRATION_PROBLEM.status);
        allLeadStatusList.add(Statuses.CONTRACT_CLOSED.status);

        return allLeadStatusList;
    }


}
