package ru.handy.handy.controllers.update;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.handy.handy.models.lead_heap.LeadEntity;
import ru.handy.handy.models.mappers.Statuses;
import ru.handy.handy.service.lead_heap.LeadService;

@Controller
@RequestMapping("/api/update/sale")
public class UpdatableSaleController {

    private final LeadService leadService;

    public UpdatableSaleController(LeadService leadService) {
        this.leadService = leadService;
    }

    /****************   adding calls info  *****/
    @GetMapping("/calls")
    public String updateAdvertiserCalls(@RequestParam("leadId") Long leadId,

                                        @RequestParam(value = "curentCalls", required = false) String curentCalls,
                                        @RequestParam(value = "curentViews", required = false) String curentViews,
                                        @RequestParam(value = "briefComment", required = false) String briefComment){

        LeadEntity lead = leadService.findLeadById(leadId);

        if (curentCalls != null){
            if (curentCalls.length() > 0 && curentCalls.length() < 3){

                Integer calls = 0;

                try{

                    calls = Integer.parseInt(curentCalls);

                } catch (Exception e){
                    e.printStackTrace();
                    return "error/error"; //?????????????????????????
                }
                Integer addingNewCalls = lead.getAllCalls() + calls;

                if (calls != 0)
                    leadService.setAllCalls(leadId, addingNewCalls);
            }
        }

        if (curentViews != null){
            if (curentViews.length() > 0 && curentViews.length() < 5){

                Integer views = 0;
                try{

                    views = Integer.parseInt(curentViews);

                } catch (Exception e){
                    e.printStackTrace();
                    return "error/error"; //?????????????????????????
                }
                Integer addingNewViews = lead.getAllViews() + views;

                if (views != 0)
                    leadService.setAllViews(leadId, addingNewViews);
            }
        }

        if (briefComment != null)
            if (briefComment.length() > 0 && (!briefComment.equals(lead.getBriefComment())))
                leadService.setBriefComment(leadId, briefComment);

        return "redirect:/api/advertisment/" + leadId;
    }
    /**************************************************************************************/

    /****************   adding selling text **************/
    @PostMapping("/selling_text")
    public String writeUserCellingText(@RequestParam("leadId") Long leadId,
                                       @RequestParam(value = "sellingText", required = false) String sellingText){

        LeadEntity lead = leadService.findLeadById(leadId);

        if (lead.getSellingText() == null){
            leadService.setAnnouncementByApplId(leadId, sellingText);

        }
        else if (sellingText.length() > 0 && !(lead.getSellingText().equals(sellingText))){
            leadService.setAnnouncementByApplId(leadId, sellingText);

        }

        return "redirect:/api/advertisment/" + leadId;
    }
    /**************************************************************************************/

    /****************   adding selling text **************/
    @PostMapping("/selling_started")
    public String sellingStarted(@RequestParam("leadId") Long leadId){

        leadService.setLeadStatus(leadId, Statuses.SELLING_FLAT.status, Statuses.SELLING_FLAT.digit);

        return "redirect:/api/advertisment/" + leadId;
    }
    /**************************************************************************************/

}