package com.example.doctor.controller.back;

import com.example.doctor.model.dto.DiseaseSymptomDTO;
import com.example.doctor.model.entity.Disease;
import com.example.doctor.model.entity.DiseaseSymptom;
import com.example.doctor.model.entity.Symptom;
import com.example.doctor.model.mapper.DiseaseSymptomMapper;
import com.example.doctor.service.DiseaseSymptomService;
import com.example.doctor.service.DiseaseService;
import com.example.doctor.service.SymptomService;
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
@RequestMapping("/back/diseases-symptoms")
public class DiseaseSymptomController {

    private static final String REDIRECT_URL = "/back/diseases-symptoms/";

    @Autowired
    private DiseaseSymptomService diseaseSymptomService;

    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private SymptomService symptomService;

    @Autowired
    private DiseaseSymptomMapper symptomMapper;

    @GetMapping("/form")
    public String create(Model model) {
        try {
            List<Disease> diseaseList = diseaseService.findAll();
            List<Symptom> symptomList = symptomService.findAll();
            DiseaseSymptomDTO diseaseSymptomDTO = new DiseaseSymptomDTO();

            model.addAttribute("diseaseList", diseaseList);
            model.addAttribute("symptomList", symptomList);
            model.addAttribute("diseaseSymptomDTO", diseaseSymptomDTO);
            return "back/disease_symptom_form";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping("/form/{id}")
    public String edit(Model model, @PathVariable long id) {
        try {
            Disease disease = diseaseService.findById(id);
            DiseaseSymptomDTO diseaseSymptomDTO = new DiseaseSymptomDTO();

            List<DiseaseSymptom> diseaseSymptomList = diseaseSymptomService.findByDisease(disease);
            List<Symptom> symptomList = symptomService.findAll();
            List<String> listSymptomId = new ArrayList<>();
            diseaseSymptomList.forEach(
                    element -> {
                        listSymptomId.add(String.valueOf(element.getSymptom().getId()));
                    }
            );
            diseaseSymptomDTO.setDiseaseId(id);
            diseaseSymptomDTO.setListSymptomId(listSymptomId);

            model.addAttribute("diseaseList", disease);
            model.addAttribute("diseaseSymptomDTO", diseaseSymptomDTO);
            model.addAttribute("symptomList", symptomList);
            return "back/disease_symptom_form";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "redirect:" + REDIRECT_URL;
        }
    }

    @PostMapping("/form/")
    public String save(DiseaseSymptomDTO diseaseSymptomDTO) {
        try {
            Disease disease = diseaseService.findById(diseaseSymptomDTO.getDiseaseId());
            List<DiseaseSymptom> diseaseSymptomList = diseaseSymptomService.findByDisease(disease);
            diseaseSymptomService.delete(diseaseSymptomList);

            diseaseSymptomDTO.getListSymptomId().forEach(
                    symptomId -> {
                        DiseaseSymptomDTO temp = new DiseaseSymptomDTO();
                        temp.setSymptomId(Long.parseLong(symptomId));
                        temp.setDiseaseId(disease.getId());
                        temp.setStatus(true);
                        diseaseSymptomService.save(symptomMapper.toEntity(temp));
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
