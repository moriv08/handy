package ru.handy.handy.controllers.update;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.handy.handy.models.lead_heap.LeadEntity;
import ru.handy.handy.models.mappers.Statuses;
import ru.handy.handy.service.lead_heap.LeadService;

@Controller
@RequestMapping("/api/update/booking")
public class UpdatableBookingController {

    private final LeadService leadService;

    public UpdatableBookingController(LeadService leadService) {
        this.leadService = leadService;
    }

    @GetMapping("/some")
    public String updateBooking(@RequestParam(value = "leadId") Long leadId,

                                @RequestParam(value = "clientCall", required = false) boolean[] clientCall,
                                @RequestParam(value = "clientDigitAnswer", required = false) Integer[] clientDigitAnswer,
                                @RequestParam(value = "clientDigitStatus", required = false) Integer[] clientDigitStatus){

        LeadEntity lead = leadService.findLeadById(leadId);
//        if (lead != null){

            if (clientCall != null) {
                if (clientCall[0] != lead.getCalled())
                    leadService.setFirstCall(leadId, clientCall[0]);
            }
            if (clientDigitAnswer != null) {
                if (!clientDigitAnswer[0].equals(lead.getClientAnswer())){

                    Integer newClientDigitAnswer = clientDigitAnswer[0];
                    String newClientAnswer = Statuses.findOutStatusByDigitStatus(newClientDigitAnswer);

                    leadService.setLeadStatus(leadId, newClientAnswer, newClientDigitAnswer);
                    leadService.setClientAnswer(leadId, newClientAnswer, newClientDigitAnswer);
                }
            }
            if (clientDigitStatus != null){
                if (!clientDigitStatus[0].equals(lead.getClientDigitStatus())){

                    Integer newDigitStatus = clientDigitStatus[0];
                    String newStatus = Statuses.findOutStatusByDigitStatus(newDigitStatus);

                    leadService.setClientStatus(leadId, newStatus, newDigitStatus);
                    leadService.setLeadStatus(leadId, newStatus, newDigitStatus);

                }
            }
//        }

        return "redirect:/api/booking/" + leadId;
    }

}