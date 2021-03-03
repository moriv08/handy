package ru.handy.handy.controllers.update;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.handy.handy.components.SharedLogic;
import ru.handy.handy.models.lead_heap.BookingEntity;
import ru.handy.handy.models.lead_heap.ExpertCommentEntity;
import ru.handy.handy.models.lead_heap.LeadEntity;
import ru.handy.handy.models.mappers.Statuses;
import ru.handy.handy.service.lead_heap.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/api/update/flat")
public class UpdatableFlatController {

    private final LeadService leadService;
    private final FlatFotoService flatFotoService;
    private final ExpertCommentService expertCommentService;
    private final WeakSideService weakSideService;
    private final BookingService bookingService;
    private final SharedLogic sharedLogic;

    public UpdatableFlatController(LeadService leadService, FlatFotoService flatFotoService, ExpertCommentService expertCommentService, WeakSideService weakSideService, BookingService bookingService, SharedLogic sharedLogic) {
        this.leadService = leadService;
        this.flatFotoService = flatFotoService;
        this.expertCommentService = expertCommentService;
        this.weakSideService = weakSideService;
        this.bookingService = bookingService;
        this.sharedLogic = sharedLogic;
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EXPERT', 'ROLE_SUPERUSER')")
    @PostMapping("/main_info")
    public String updateMainFlat(@RequestParam(value = "leadOwnerName", required = false) String leadOwnerName,
                                 @RequestParam(value = "tel", required = false) String tel,
                                 @RequestParam(value = "newLeadStatus", required = false) String newLeadStatus,
                                 @RequestParam(value = "precontractType", required = false) String precontractType,
                                 @RequestParam(value = "controlDate", required = false) String controlDate,

                                 @RequestParam(value = "address", required = false) String address,
                                 @RequestParam(value = "metro", required = false) String metro,
                                 @RequestParam(value = "buildYear", required = false) String buildYear,
                                 @RequestParam(value = "houseType", required = false) String houseType,
                                 @RequestParam(value = "floor", required = false) String floor,
                                 @RequestParam(value = "rooms", required = false) String rooms,
                                 @RequestParam(value = "flatSquare", required = false) String flatSquare,

                                 @RequestParam(value = "firstLast", required = false) String firstLast,
                                 @RequestParam(value = "badView", required = false) String badView,
                                 @RequestParam(value = "cargo", required = false) String cargo,
                                 @RequestParam(value = "gazStation", required = false) String gazStation,
                                 @RequestParam(value = "other", required = false) String other,

                                 @RequestParam(value = "bookingLocation", required = false) String bookingLocation,
                                 @RequestParam(value = "bookingPrice", required = false) String bookingPrice,
                                 @RequestParam(value = "bookingHanding", required = false) String bookingHanding,
                                 @RequestParam(value = "bookingSquare", required = false) String bookingSquare,
                                 @RequestParam(value = "bookingRooms", required = false) String bookingRooms,
                                 @RequestParam(value = "bookingStart", required = false) String bookingStart,
                                 @RequestParam(value = "bookingEnd", required = false) String bookingEnd,

                                 @RequestParam("leadId") Long leadId){

        LeadEntity lead = leadService.findLeadById(leadId);
        LocalDate controlLocalDate = null;

        if (controlDate.length() > 0){
            try{
                controlLocalDate = LocalDate.parse(sharedLogic.reformatThymeleafDateIntoLocal(controlDate));
            } catch (IndexOutOfBoundsException e){

                e.printStackTrace();
                return "error/strange_date";

            } catch (Exception e){

                e.printStackTrace();
                return "error/error";
            }
            if (!lead.getLeadStatus().equals(Statuses.NEW_LEAD.status)){
//                leadService.setLeadStatus(leadId, Statuses.NEW_NEGOTIATIONS.status, Statuses.NEW_NEGOTIATIONS.digit);
                leadService.setStatusEmergency(leadId, Statuses.NEW_NEGOTIATIONS.status, Statuses.NEW_NEGOTIATIONS.digit);
            }
        }

        //************************** 1.1 flat owner
        if (leadOwnerName.trim().length() > 0)
            leadService.setLeadOwnerName(leadId, leadOwnerName);
        if (tel.trim().length() > 0)
            leadService.setTel(leadId, tel);
        if (newLeadStatus != null){
            if (newLeadStatus.trim().length() > 0 && !newLeadStatus.equalsIgnoreCase(lead.getLeadStatus())){

                Integer digit = Statuses.findOutDigitStatusByStatus(newLeadStatus);
                leadService.setLeadStatus(leadId, newLeadStatus, digit);
            }
        }
        if (precontractType.trim().length() > 0)
            leadService.precontractType(leadId, precontractType);
        if (controlDate.trim().length() > 0)
            leadService.setLeadControlDate(leadId, controlLocalDate);

        //************************** 1.2 flat description
        if (address.trim().length() > 0)
            leadService.setAddress(leadId, address);
        if (metro.trim().length() > 0)
            leadService.setMetro(leadId, metro);
        if (buildYear.trim().length() > 0)
            leadService.setBuildYear(leadId, buildYear);
        if (houseType.trim().length() > 0)
            leadService.setHouseType(leadId, houseType);
        if (floor.trim().length() > 0)
            leadService.setFloor(leadId, floor);
        if (rooms.trim().length() > 0)
            leadService.setRooms(leadId, rooms);
        if (flatSquare.trim().length() > 0)
            leadService.setFlatSquare(leadId, flatSquare);

        //************************** 1.3 flat disadvantages
        if (firstLast != null && firstLast.trim().length() > 0)
            weakSideService.setFirstLastDisadv(lead, firstLast);
        if (badView != null && badView.trim().length() > 0)
            weakSideService.setBadView(lead, badView);
        if (cargo != null && cargo.trim().length() > 0)
            weakSideService.setCargo(lead, cargo);
        if (gazStation != null && gazStation.trim().length() > 0)
            weakSideService.setGazStation(lead, gazStation);
        if (other != null && other.trim().length() > 0)
            weakSideService.setOther(lead, other);

        //**************************  1.4 flat booking
        BookingEntity booking = lead.getBooking();

        if (bookingLocation != null && bookingLocation.trim().length() > 0)
            bookingService.setBookingLocationByBookingId(booking.getId(), bookingLocation);
        if (bookingPrice != null && bookingPrice.trim().length() > 0)
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

        return "redirect:/api/flat/" + leadId;
    }

    /************************************   upload flat fotos   *****************/
//    @PostMapping(value = "/upload_fotos/{role}")
//    public String uploadFotos(@PathVariable(value = "role") String role,
//                              @RequestParam(value = "leadId") Long leadId,
//                              @RequestParam(value = "foto_files", required = false) MultipartFile[] fotos){
//
//        LeadEntity lead = leadService.findLeadById(leadId);
//
//        for (MultipartFile file : fotos) {
//
//            FlatFotoEntity newFlatFoto = new FlatFotoEntity();
//            newFlatFoto.setLead(lead);
//
//            try {
//
//                newFlatFoto.setFoto(file.getBytes());
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                return "error/error"; //??????????????????????????????????????????????????????
//            }
//
//            flatFotoService.saveNewFlatFoto(newFlatFoto);
//        }
//        return "redirect:/api/" + role + "/flat/" + leadId;
//    }

    /************************************   delete fotos   *****************/
//    @PostMapping(value = "/delete_fotos/{role}")
//    public String deleteFotos(@PathVariable(value = "role") String role,
//                              @RequestParam(value = "fotoId", required = false) String[] allFotoId,
//                              @RequestParam(value = "leadId") Long leadId){
//
//        if (allFotoId != null){
//            for (String foto: allFotoId) {
//
//                Long fotoId;
//
//                try{
//
//                    fotoId = Long.parseLong(foto);
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                    return "error/error"; //??????????????????????????????????????????????????????
//                }
//
//                flatFotoService.deleteFlatFoto(fotoId);
//            }
//        }
//        return "redirect:/api/" + role + "/flat/" + leadId;
//    }

    /************************************   expert comment and price  *****************/
    @PostMapping("/actualization/{role}")
    public String updateActualization(@PathVariable("role") String role,
                                      @RequestParam(value = "expertComment", required = false) String expertComment,
                                      @RequestParam(value = "expertPrice", required = false) String expertPrice,
                                      @RequestParam("leadId") Long leadId){

        LeadEntity lead = leadService.findLeadById(leadId);

        if (expertComment.trim().length() > 0){

            ExpertCommentEntity newExpertComment = new ExpertCommentEntity();
            newExpertComment.setExpertComment(expertComment);
            LocalDate currentDate = LocalDate.now();
            newExpertComment.setCommentDate(currentDate);
            newExpertComment.setLead(lead);

            expertCommentService.saveComment(newExpertComment);
            leadService.setFirstCall(leadId, true);

            String statusNegotiations = Statuses.NEW_NEGOTIATIONS.status;
            Integer statusDigit = Statuses.NEW_NEGOTIATIONS.digit;
            leadService.setLeadStatus(leadId, statusNegotiations, statusDigit);
        }

        if (expertPrice.trim().length() > 0)
            if (!expertPrice.equals(lead.getExpertPrice())) {
                leadService.saveExpertPrice(leadId, expertPrice);

                String statusExpertPrice = Statuses.NEW_EXPERT_PRICE.status;
                Integer digit = Statuses.NEW_EXPERT_PRICE.digit;
                leadService.setLeadStatus(leadId, statusExpertPrice, digit);
            }

        return "redirect:/api/flat/" + leadId;
    }

    /************************************   super comment and price  *****************/
    @PostMapping("/super_comment")
    public String superuserCommentAndPriceSetter(@RequestParam(value = "leadId") Long leadId,
                                                 @RequestParam(value = "superPrice", required = false) String superPrice,
                                                 @RequestParam(value = "superComment", required = false) String superComment){

        LeadEntity lead = leadService.findLeadById(leadId);

        if(superComment.trim().length() > 0){
            if(!superComment.trim().equals(lead.getSuperComment())){
                leadService.saveSuperComment(leadId, superComment.trim());

                String superAnswer = Statuses.NEW_SUPER_ANSWER.status;
                Integer digit = Statuses.NEW_SUPER_ANSWER.digit;
                leadService.setLeadStatus(leadId, superAnswer, digit);
            }
        }

        if(superPrice.trim().length() > 0){
            if(!superPrice.trim().equals(lead.getSuperPrice())){
                leadService.saveSuperPrice(leadId, superPrice.trim());

                String newSuperPrice = Statuses.NEW_SUPER_PRICE.status;
                Integer digit = Statuses.NEW_SUPER_PRICE.digit;
                leadService.setLeadStatus(leadId, newSuperPrice, digit);
            }
        }

        return "redirect:/api/flat/" + leadId;
    }

}