package com.example.doctor.controller.back;

import com.example.doctor.model.dto.MessageDTO;
import com.example.doctor.model.dto.PrecautionDTO;
import com.example.doctor.model.entity.Precaution;
import com.example.doctor.model.mapper.PrecautionMapper;
import com.example.doctor.service.PrecautionService;
import com.example.doctor.util.ValidatorUtil;
import com.example.doctor.validator.PrecautionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/back/precautions")
public class PrecautionController {
    
    private static final String REDIRECT_URL = "/back/precautions/";

    @Autowired
    private PrecautionService precautionService;

    @Autowired
    private PrecautionMapper precautionMapper;

    @Autowired
    private PrecautionValidator precautionValidator;

    @Autowired
    private ValidatorUtil validatorUtil;

    @GetMapping(value = {"", "/"})
    public String list(Model model) {
        try {
            List<Precaution> precautionList = precautionService.findAll();
            model.addAttribute("precautionList", precautionList);
            return "back/precaution_list";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping("/form")
    public String create(Model model) {
        try {
            PrecautionDTO precautionDTO = new PrecautionDTO();
            model.addAttribute("precautionDTO", precautionDTO);
            return "back/precaution_form";
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
            Precaution precaution = precautionService.findById(id);
            model.addAttribute("precautionDTO", precautionMapper.toDTO(precaution));

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

            return "back/precaution_form";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "redirect:" + REDIRECT_URL;
        }
    }

    @PostMapping("/form/")
    public String save(Model model, PrecautionDTO precautionDTO, BindingResult bindingResult) {
        try {
            precautionValidator.validate(precautionDTO, bindingResult);

            if (bindingResult.hasErrors()) {
                model.addAttribute("errorList",
                        validatorUtil.toErrors(bindingResult.getFieldErrors()));
                model.addAttribute("status", "warning");
                model.addAttribute("messageDTO", new MessageDTO("save",
                        "Vui lòng kiểm tra lại thông tin!"));
                return "back/precaution_form";
            }
            Precaution precaution = precautionService.save(precautionMapper.toEntity(precautionDTO));
            String redirect = "/back/precautions/form/" + precaution.getId() + "?action=save&status=success";
            return "redirect:" + redirect;
        } catch (Exception exception) {
            exception.printStackTrace();
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        Precaution precaution = precautionService.findById(id);
        precaution.setStatus(false);
        precautionService.save(precaution);
        return "redirect:" + REDIRECT_URL;
    }

}
