package ru.handy.handy.controllers.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.handy.handy.components.SharedLogic;
import ru.handy.handy.models.PrincipalEntity;
import ru.handy.handy.models.lead_heap.BookingEntity;
import ru.handy.handy.models.lead_heap.JuristInfoEntity;
import ru.handy.handy.models.lead_heap.LeadEntity;
import ru.handy.handy.models.lead_heap.WeakSideEntity;
import ru.handy.handy.models.mappers.Statuses;
import ru.handy.handy.service.AdministrationsService;
import ru.handy.handy.service.PrincipalUserDetailsService;
import ru.handy.handy.service.lead_heap.BookingService;
import ru.handy.handy.service.lead_heap.JuristInfoService;
import ru.handy.handy.service.lead_heap.LeadService;
import ru.handy.handy.service.lead_heap.WeakSideService;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

@Controller
@RequestMapping("/api/update/admin")
public class UpdatableAdminController {

    private final LeadService leadService;
    private final WeakSideService weakSideService;
    private final BookingService bookingService;
    private final SharedLogic sharedLogic;
    private final JuristInfoService juristInfoService;
    private final PrincipalUserDetailsService principalService;
    private final AdministrationsService administrationsService;

    public UpdatableAdminController(LeadService leadService, WeakSideService weakSideService, BookingService bookingService, SharedLogic sharedLogic, JuristInfoService juristInfoService, PrincipalUserDetailsService principalService, AdministrationsService administrationsService) {
        this.leadService = leadService;
        this.weakSideService = weakSideService;
        this.bookingService = bookingService;
        this.sharedLogic = sharedLogic;
        this.juristInfoService = juristInfoService;
        this.principalService = principalService;
        this.administrationsService = administrationsService;
    }

    @ModelAttribute
    public void addCommonObjectsToModel(Model model, Principal principal){
        PrincipalEntity principalEntity = principalService.findPrincipalEntityByUsername(principal.getName());

        model.addAttribute("person", principalEntity);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/new_lead")
    public String newLeadPage(Model model){

        List<String> experts = principalService.findAllExpertNames();
        model.addAttribute("experts", experts);

        return "fragments/home/admin/new_lead";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/few_leads")
    public String fewLeadsPage(Model model){

        List<String> experts = principalService.findAllExpertNames();
        model.addAttribute("experts", experts);

        return "fragments/home/admin/few_leads";
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/announcement")
    public String makeAnnouncement(@RequestParam(value = "announcement") String announcement){

//        List<String> experts = principalService.findAllExpertNames();
//        model.addAttribute("experts", experts);

        administrationsService.setNewAnnouncement(1L, announcement);

        return "redirect:/api/home/admin/filters/start";
    }



    public String makeTel(String strTel){

        char[] answer = new char[16];
        List<Character> tel = new ArrayList<>();

        for (int i = 0; i < strTel.length(); i++)
            if (strTel.charAt(i) > 47 && strTel.charAt(i) < 58)
                tel.add(strTel.charAt(i));

        if (tel.size() == 11)
            tel.set(0, '7');
        else if (tel.size() == 10)
            tel.add(0, '7');
        else
            return null;

        tel.add(0, '+');
        tel.add(2, '(');
        tel.add(6, ')');
        tel.add(10, '-');
        tel.add(13, '-');

        int i = -1;
        while (++i < tel.size())
            answer[i] = tel.get(i);

        return valueOf(answer);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/create_new_lead")
    public String makeNewLead(@RequestParam(value = "tel", required = false) String tel,
                              @RequestParam(value = "expert", required = false) String expert,

                              @RequestParam(value = "leadOwnerName", required = false) String leadOwnerName,
                              @RequestParam(value = "controlDate", required = false) String controlDate,

                              @RequestParam(value = "address", required = false) String address,
//                                 @RequestParam(value = "metro", required = false) String metro,
                              @RequestParam(value = "floor", required = false) String floor,
                              @RequestParam(value = "rooms", required = false) String rooms,
                              @RequestParam(value = "flatSquare", required = false) String flatSquare,

                              @RequestParam(value = "bookingLocation", required = false) String bookingLocation,
                              @RequestParam(value = "bookingPrice", required = false) String bookingPrice,
                              @RequestParam(value = "bookingHanding", required = false) String bookingHanding,
                              @RequestParam(value = "bookingSquare", required = false) String bookingSquare,
                              @RequestParam(value = "bookingRooms", required = false) String bookingRooms,
                              @RequestParam(value = "bookingStart", required = false) String bookingStart,
                              @RequestParam(value = "bookingEnd", required = false) String bookingEnd){

        if (expert == null || tel == null || expert.length() == 0 || tel.trim().length() == 0)
            return "error/check_lead";

        String phoneNumber = makeTel(tel);
        if (phoneNumber == null)
            return "error/check_lead";

        LeadEntity lead = leadService.findLeadByTel(phoneNumber);
        if (lead != null)
            return "error/existing_lead";

        PrincipalEntity expertEntity = principalService.findPrincipalEntityByRealName(expert);
        lead = createNewLead(tel, expertEntity);

        Long leadId = lead.getId();

        LocalDate today;

        if (controlDate.length() > 0){
            try{
                today = LocalDate.parse(sharedLogic.reformatThymeleafDateIntoLocal(controlDate));
            } catch (IndexOutOfBoundsException e){

                e.printStackTrace();
                return "error/strange_date";

            } catch (Exception e){

                e.printStackTrace();
                return "error/error";
            }
//            leadService.setLeadStatus(leadId, Statuses.NEW_NEGOTIATIONS.status, Statuses.NEW_NEGOTIATIONS.digit);
        }else {
            today = LocalDate.now();
        }

        leadService.setLeadControlDate(leadId, today);//??????????????????????????????????????????

        //************************** 1.1 flat owner
        if (leadOwnerName.trim().length() > 0)
            leadService.setLeadOwnerName(leadId, leadOwnerName);

        //************************** 1.2 flat description
        if (address.trim().length() > 0)
            leadService.setAddress(leadId, address);
//        if (metro.trim().length() > 0)
//            leadService.setMetro(leadId, metro);
        if (floor.trim().length() > 0)
            leadService.setFloor(leadId, floor);
        if (rooms.trim().length() > 0)
            leadService.setRooms(leadId, rooms);
        if (flatSquare.trim().length() > 0)
            leadService.setFlatSquare(leadId, flatSquare);

        //**************************  1.4 flat booking
        BookingEntity booking = bookingService.findBookingByLeadId(leadId);

        if (bookingLocation.trim().length() > 0)
            bookingService.setBookingLocationByBookingId(booking.getId(), bookingLocation);
        if (bookingPrice.trim().length() > 0)
            bookingService.setBookingPriceByBookingId(booking.getId(), bookingPrice);
        if (bookingHanding != null && bookingHanding.trim().length() > 0)
            bookingService.setBookingHandingByBookingId(booking.getId(), bookingHanding);
        if (bookingSquare != null && bookingSquare.trim().length() > 0)
            bookingService.setBookingSquareByBookingId(booking.getId(), bookingSquare);
        if (bookingRooms != null && bookingRooms.trim().length() > 0)
            bookingService.setBookingRoomsByBookingId(booking.getId(), bookingRooms);
        if (bookingStart != null && bookingStart.length() > 0){
            LocalDate localBookingSquare;
            try {
                localBookingSquare = LocalDate.parse(sharedLogic.reformatThymeleafDateIntoLocal(bookingStart));
            }catch (IndexOutOfBoundsException e){

                e.printStackTrace();
                return "error/strange_date";

            } catch (Exception e){

                e.printStackTrace();
                return "error/error";
            }
            bookingService.setBookingStartDateByBookingId(booking.getId(), localBookingSquare);
        }
        if (bookingEnd != null && bookingEnd.length() > 0){

            LocalDate localEndBookingDate;
            try {
                localEndBookingDate = LocalDate.parse(sharedLogic.reformatThymeleafDateIntoLocal(bookingEnd));
            }catch (IndexOutOfBoundsException e){

                e.printStackTrace();
                return "error/strange_date";

            } catch (Exception e){

                e.printStackTrace();
                return "error/error";
            }
            bookingService.setBookingEndDateByBookingId(booking.getId(), localEndBookingDate);
        }

        return "redirect:/api/flat/" + lead.getId();
    }

    public synchronized LeadEntity createNewLead(String preTel, PrincipalEntity expertEntity){

        LeadEntity lead = new LeadEntity();

        LocalDate today = LocalDate.now();

        String tel = makeTel(preTel);

        lead.setTel(tel);
        lead.setExpert(expertEntity);
        lead.setLeadDate(today);

        lead.setLeadDigitStatus(Statuses.NEW_LEAD.digit);
        lead.setLeadStatus(Statuses.NEW_LEAD.status);

        lead.setClientDigitStatus(Statuses.STATUS_ACTIVE.digit);
        lead.setClientStatus(Statuses.STATUS_ACTIVE.status);

        lead.setAllCalls(0);
        lead.setAllViews(0);
        lead.setCalled(true);

        leadService.createNewLead(lead);

        WeakSideEntity weakSide = new WeakSideEntity();
        weakSide.setLead(lead);
        weakSideService.createWeakSideEntity(weakSide);

        BookingEntity book = new BookingEntity();
        book.setLead(lead);
        bookingService.createNewBooking(book);

        JuristInfoEntity juristInfo = new JuristInfoEntity();
        juristInfo.setLead(lead);
        juristInfoService.createNewJuristInfo(juristInfo);

        return lead;
    }
    @PostMapping("/create_few_leads")
    public String makeFewLeads(@RequestParam(value = "expert", required = false) String expert,
                               @RequestParam(value = "info", required = false) String info){

        PrincipalEntity expertEntity = principalService.findPrincipalEntityByRealName(expert);

        LocalDate contractStart = null;
        String tel = null;
        String address = null;
        String metro = null;
        String rooms = null;
        String flatSquare = null;
        String floor = null;
//        String houseType = null;
//        String price = null;

        String[] leads = info.split("\t");
        int leadsCounter = leads.length / 18;
        int circleCounter = 0;


//        int counter = -1;
//
//        for (int i = 0; i < leads.length; i++) {
//
//            System.out.println( ++counter + " -------- ' + _ " + leads[i]);
//
//            if (counter == 19)
//                counter = 0;
//        }
//        System.out.println(leads.length);

        if (leads.length + 1 < 19)
            return "redirect:/api/update/admin/few_leads";

        for (int i = 0; i < leadsCounter; i++) {

                contractStart = LocalDate.parse(sharedLogic.reformatThymeleafDateIntoLocal(leads[1 + circleCounter]));
                tel = makeTel(leads[2 + circleCounter]);

                if (leadService.findLeadByTel(tel) != null)
                    continue;

                if (tel == null)
                    return "error/check_lead";

                LeadEntity lead = createNewLead(tel, expertEntity);

                address = leads[4 + circleCounter];
                metro = leads[7 + circleCounter] + " расстояние " + leads[8 + circleCounter];
                rooms = leads[9 + circleCounter];
                flatSquare = leads[10 + circleCounter];
                floor = leads[12 + circleCounter] + " / " + leads[13 + circleCounter];
//                houseType = leads[14];

                if (address.trim().length() > 0)
                    leadService.setAddress(lead.getId(), address);

                if (metro.trim().length() > 0)
                    leadService.setMetro(lead.getId(), metro);
                if (floor.trim().length() > 0)
                    leadService.setFloor(lead.getId(), floor);
                if (rooms.trim().length() > 0)
                    leadService.setRooms(lead.getId(), rooms);
                if (flatSquare.trim().length() > 0)
                    leadService.setFlatSquare(lead.getId(), flatSquare);

            circleCounter += (18);
        }

        return "redirect:/api/update/admin/few_leads";
    }
}
