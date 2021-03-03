package ru.handy.handy.controllers.update;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.handy.handy.models.lead_heap.FlatFotoEntity;
import ru.handy.handy.models.lead_heap.LeadEntity;
import ru.handy.handy.service.lead_heap.FlatFotoService;
import ru.handy.handy.service.lead_heap.LeadService;

import java.io.IOException;


@Controller
@RequestMapping("/api/update/foto")
public class UpdatableFlatFotoController {
    private final LeadService leadService;
    private final FlatFotoService flatFotoService;

    public UpdatableFlatFotoController(LeadService leadService, FlatFotoService flatFotoService) {
        this.leadService = leadService;
        this.flatFotoService = flatFotoService;
    }

    /************************************   upload flat fotos   *****************/
    @PostMapping(value = "/upload_fotos")
    public String uploadFotos(@RequestParam(value = "path") String path,
                              @RequestParam(value = "leadId") Long leadId,
                              @RequestParam(value = "foto_files", required = false) MultipartFile[] fotos){

        LeadEntity lead = leadService.findLeadById(leadId);

        for (MultipartFile file : fotos) {

            FlatFotoEntity newFlatFoto = new FlatFotoEntity();
            newFlatFoto.setLead(lead);

            try {

                newFlatFoto.setFoto(file.getBytes());

            } catch (IOException e) {
                e.printStackTrace();
                return "error/error"; //??????????????????????????????????????????????????????
            }

            flatFotoService.saveNewFlatFoto(newFlatFoto);
        }
        return "redirect:/api/" + path + "/" + leadId;
    }

    /************************************   delete fotos   *****************/
    @PostMapping(value = "/delete_fotos")
    public String deleteFotos(@RequestParam(value = "path") String path,
                              @RequestParam(value = "fotoId", required = false) String[] allFotoId,
                              @RequestParam(value = "leadId") Long leadId){

        if (allFotoId != null){
            for (String foto: allFotoId) {

                Long fotoId;

                try{

                    fotoId = Long.parseLong(foto);

                }catch (Exception e){
                    e.printStackTrace();
                    return "error/error"; //??????????????????????????????????????????????????????
                }

                flatFotoService.deleteFlatFoto(fotoId);
            }
        }
        return "redirect:/api/" + path + "/" + leadId;
    }
}
