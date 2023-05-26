package com.example.doctor.controller.back;

import com.example.doctor.model.dto.DiseasePrecautionDTO;
import com.example.doctor.model.entity.Disease;
import com.example.doctor.model.entity.DiseasePrecaution;
import com.example.doctor.model.entity.Precaution;
import com.example.doctor.model.mapper.DiseasePrecautionMapper;
import com.example.doctor.service.DiseasePrecautionService;
import com.example.doctor.service.DiseaseService;
import com.example.doctor.service.PrecautionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/back/diseases-precautions")
public class DiseasePrecautionController {

    private static final String REDIRECT_URL = "/back/diseases-precautions/";

    @Autowired
    private DiseasePrecautionService diseasePrecautionService;

    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private PrecautionService precautionService;

    @Autowired
    private DiseasePrecautionMapper precautionMapper;

    @GetMapping("/form")
    public String create(Model model) {
        try {
            List<Disease> diseaseList = diseaseService.findAll();
            List<Precaution> precautionList = precautionService.findAll();
            DiseasePrecautionDTO diseasePrecautionDTO = new DiseasePrecautionDTO();

            model.addAttribute("diseaseList", diseaseList);
            model.addAttribute("precautionList", precautionList);
            model.addAttribute("diseasePrecautionDTO", diseasePrecautionDTO);
            return "back/disease_precaution_form";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping("/form/{id}")
    public String edit(Model model, @PathVariable long id) {
        try {
            Disease disease = diseaseService.findById(id);
            DiseasePrecautionDTO diseasePrecautionDTO = new DiseasePrecautionDTO();

            List<DiseasePrecaution> diseasePrecautionList = diseasePrecautionService.findByDisease(disease);
            List<Precaution> precautionList = precautionService.findAll();
            List<String> listPrecautionId = new ArrayList<>();
            diseasePrecautionList.forEach(
                    element -> {
                        listPrecautionId.add(String.valueOf(element.getPrecaution().getId()));
                    }
            );
            diseasePrecautionDTO.setDiseaseId(id);
            diseasePrecautionDTO.setListPrecautionId(listPrecautionId);

            model.addAttribute("diseaseList", disease);
            model.addAttribute("diseasePrecautionDTO", diseasePrecautionDTO);
            model.addAttribute("precautionList", precautionList);
            return "back/disease_precaution_form";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "redirect:" + REDIRECT_URL;
        }
    }

    @PostMapping("/form/")
    public String save(DiseasePrecautionDTO diseasePrecautionDTO) {
        try {
            Disease disease = diseaseService.findById(diseasePrecautionDTO.getDiseaseId());
            List<DiseasePrecaution> diseasePrecautionList = diseasePrecautionService.findByDisease(disease);
            diseasePrecautionService.delete(diseasePrecautionList);

            diseasePrecautionDTO.getListPrecautionId().forEach(
                    precautionId -> {
                        DiseasePrecautionDTO temp = new DiseasePrecautionDTO();
                        temp.setPrecautionId(Long.parseLong(precautionId));
                        temp.setDiseaseId(disease.getId());
                        temp.setStatus(true);
                        diseasePrecautionService.save(precautionMapper.toEntity(temp));
                    }
            );

            String redirect = "/back/diseases/form/" + disease.getId() + "?action=save&status=success";
            return "redirect:" + redirect;
        } catch (Exception exception) {
            exception.printStackTrace();
            return "redirect:" + REDIRECT_URL;
        }
    }

}
