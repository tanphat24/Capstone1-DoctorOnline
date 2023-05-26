package com.example.doctor.controller.back;

import com.example.doctor.model.dto.DiseaseDTO;
import com.example.doctor.model.dto.MessageDTO;
import com.example.doctor.model.entity.Disease;
import com.example.doctor.model.entity.DiseasePrecaution;
import com.example.doctor.model.entity.DiseaseSymptom;
import com.example.doctor.model.mapper.DiseaseMapper;
import com.example.doctor.service.DiseasePrecautionService;
import com.example.doctor.service.DiseaseService;
import com.example.doctor.service.DiseaseSymptomService;
import com.example.doctor.util.ValidatorUtil;
import com.example.doctor.validator.DiseaseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/back/diseases")
public class DiseaseController {

    private static final String REDIRECT_URL = "/back/diseases/";

    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private DiseaseMapper diseaseMapper;

    @Autowired
    private DiseasePrecautionService diseasePrecautionService;

    @Autowired
    private DiseaseSymptomService diseaseSymptomService;

    @Autowired
    private DiseaseValidator diseaseValidator;

    @Autowired
    private ValidatorUtil validatorUtil;

    @GetMapping(value = {"", "/"})
    public String list(Model model) {
        try {
            List<Disease> diseaseList = diseaseService.findAll();
            model.addAttribute("diseaseList", diseaseList);
            return "back/disease_list";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping("/form")
    public String create(Model model) {
        try {
            DiseaseDTO diseaseDTO = new DiseaseDTO();
            diseaseDTO.setStatus(true);

            List<DiseasePrecaution> diseasePrecautionList = new ArrayList<>();
            List<DiseaseSymptom> diseaseSymptomList = new ArrayList<>();

            model.addAttribute("diseasePrecautionList", diseasePrecautionList);
            model.addAttribute("diseaseSymptomList", diseaseSymptomList);
            model.addAttribute("diseaseDTO", diseaseDTO);
            return "back/disease_form";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping("/form/{id}")
    public String edit(Model model, @PathVariable long id,
                       @RequestParam(required = false) String action,
                       @RequestParam(required = false) String status) {
        try {
            Disease disease = diseaseService.findById(id);
            List<DiseasePrecaution> diseasePrecautionList = diseasePrecautionService.findByDisease(disease);
            List<DiseaseSymptom> diseaseSymptomList = diseaseSymptomService.findByDisease(disease);

            model.addAttribute("diseaseDTO", diseaseMapper.toDTO(disease));
            model.addAttribute("diseasePrecautionList", diseasePrecautionList);
            model.addAttribute("diseaseSymptomList", diseaseSymptomList);

            if (action != null) {
                model.addAttribute("status", "warning");
                model.addAttribute("messageDTO", new MessageDTO(action,
                        status.equalsIgnoreCase("success") ?
                                "Cập nhật dữ liệu thành công!" :
                                "Vui lòng kiểm tra lại thông tin!"));
            }

            if (status != null && status.equalsIgnoreCase("success")) {
                model.addAttribute("status", "success");
            }

            return "back/disease_form";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "redirect:" + REDIRECT_URL;
        }
    }

    @PostMapping("/form/")
    public String save(Model model, DiseaseDTO diseaseDTO, BindingResult bindingResult) {
        try {
            diseaseValidator.validate(diseaseDTO, bindingResult);

            if (bindingResult.hasErrors()) {
                model.addAttribute("errorList",
                        validatorUtil.toErrors(bindingResult.getFieldErrors()));
                model.addAttribute("status", "warning");
                model.addAttribute("messageDTO", new MessageDTO("save",
                        "Vui lòng kiểm tra lại thông tin!"));
                return "back/disease_form";
            }

            Disease disease = diseaseService.save(diseaseDTO);
            String redirect = "/back/diseases/form/" + disease.getId() + "?action=save&status=success";
            return "redirect:" + redirect;
        } catch (Exception exception) {
            exception.printStackTrace();
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        diseaseService.delete(id);
        return "redirect:" + REDIRECT_URL;
    }

}
