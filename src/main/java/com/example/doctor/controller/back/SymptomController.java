package com.example.doctor.controller.back;

import com.example.doctor.model.dto.MessageDTO;
import com.example.doctor.model.dto.SymptomDTO;
import com.example.doctor.model.entity.Symptom;
import com.example.doctor.model.mapper.SymptomMapper;
import com.example.doctor.service.SymptomService;
import com.example.doctor.util.ValidatorUtil;
import com.example.doctor.validator.SymptomValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/back/symptoms")
public class SymptomController {

    private static final String REDIRECT_URL = "/back/symptoms/";

    @Autowired
    private SymptomService symptomService;

    @Autowired
    private SymptomMapper symptomMapper;

    @Autowired
    private SymptomValidator symptomValidator;

    @Autowired
    private ValidatorUtil validatorUtil;

    @GetMapping(value = {"", "/"})
    public String list(Model model) {
        try {
            List<Symptom> symptomList = symptomService.findAll();
            model.addAttribute("symptomList", symptomList);
            return "back/symptom_list";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping("/form")
    public String create(Model model) {
        try {
            SymptomDTO symptomDTO = new SymptomDTO();
            model.addAttribute("symptomDTO", symptomDTO);
            return "back/symptom_form";
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
            Symptom symptom = symptomService.findById(id);
            model.addAttribute("symptomDTO", symptomMapper.toDTO(symptom));

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

            return "back/symptom_form";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "redirect:" + REDIRECT_URL;
        }
    }

    @PostMapping("/form/")
    public String save(Model model, SymptomDTO symptomDTO, BindingResult bindingResult) {
        try {
            symptomValidator.validate(symptomDTO, bindingResult);

            if (bindingResult.hasErrors()) {
                model.addAttribute("errorList",
                        validatorUtil.toErrors(bindingResult.getFieldErrors()));
                model.addAttribute("status", "warning");
                model.addAttribute("messageDTO", new MessageDTO("save",
                        "Vui lòng kiểm tra lại thông tin!"));
                return "back/symptom_form";
            }
            Symptom symptom = symptomService.save(symptomMapper.toEntity(symptomDTO));
            String redirect = "/back/symptoms/form/" + symptom.getId() + "?action=save&status=success";
            return "redirect:" + redirect;
        } catch (Exception exception) {
            exception.printStackTrace();
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        Symptom symptom = symptomService.findById(id);
        symptom.setStatus(false);
        symptomService.save(symptom);
        return "redirect:" + REDIRECT_URL;
    }

}
