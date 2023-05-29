package com.example.doctor.controller.back;

import com.example.doctor.model.dto.AccountDiseaseDTO;
import com.example.doctor.model.dto.DiseaseDTO;
import com.example.doctor.model.entity.Account;
import com.example.doctor.model.entity.Disease;
import com.example.doctor.model.mapper.DiseaseMapper;
import com.example.doctor.service.AccountDiseaseService;
import com.example.doctor.service.DiseaseService;
import com.example.doctor.util.DateUtil;
import com.example.doctor.util.ValidatorUtil;
import com.example.doctor.validator.AccountDiseaseValidator;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/back/report-disease")
public class ReportDiseaseController {

    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private AccountDiseaseService accountDiseaseService;

    @Autowired
    private DiseaseMapper diseaseMapper;

    @Autowired
    private AccountDiseaseValidator accountDiseaseValidator;

    @Autowired
    private ValidatorUtil validatorUtil;

    @GetMapping(value = {"", "/"})
    public String list(Model model) {
        Date date = new Date();
        String dateStr = DateUtil.convertDateToString(date, "yyyy-MM-dd");

        AccountDiseaseDTO accountDiseaseDTO = new AccountDiseaseDTO();
        accountDiseaseDTO.setStartDate(dateStr);
        accountDiseaseDTO.setEndDate(dateStr);

        setModel(model, accountDiseaseDTO);

        return "back/report_disease";
    }

    @GetMapping(value = {"/search"})
    public String search(Model model, AccountDiseaseDTO accountDiseaseDTO, BindingResult bindingResult) {
        accountDiseaseValidator.validate(accountDiseaseDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            Date date = new Date();
            String dateStr = DateUtil.convertDateToString(date, "yyyy-MM-dd");
            accountDiseaseDTO.setStartDate(dateStr);
            accountDiseaseDTO.setEndDate(dateStr);
            setModel(model, accountDiseaseDTO);
            model.addAttribute("errorList", validatorUtil.toErrors(bindingResult.getFieldErrors()));
            return "back/report_disease";
        }

        setModel(model, accountDiseaseDTO);
        return "back/report_disease";
    }

    private void setModel(Model model, AccountDiseaseDTO accountDiseaseDTO){
        List<Disease> diseaseList = diseaseService.findAll();
        List<Long> diseaseIds = diseaseList.stream()
                .map(Disease::getId)
                .collect(Collectors.toList());

        HashMap<Long, Integer> list = accountDiseaseService.findByCreatedOn(diseaseIds,
                accountDiseaseDTO.getStartDate(),
                accountDiseaseDTO.getEndDate());

        List<AccountDiseaseDTO> accountDiseaseDTOList = new ArrayList<>();

        list.forEach(
                (diseasedId, diseaseCount) -> {
                    DiseaseDTO diseaseDTO = diseaseMapper.toDTO(diseaseService.findById(diseasedId));
                    AccountDiseaseDTO accountDiseaseDTOTemp = new AccountDiseaseDTO();
                    accountDiseaseDTOTemp.setDiseaseId(diseasedId);
                    accountDiseaseDTOTemp.setDiseaseDTO(diseaseDTO);
                    accountDiseaseDTOTemp.setDiseaseCount(diseaseCount);

                    accountDiseaseDTOList.add(accountDiseaseDTOTemp);
                }
        );
        int accountDiseaseTotal = accountDiseaseDTOList.stream()
                .mapToInt(AccountDiseaseDTO::getDiseaseCount)
                .sum();


        model.addAttribute("accountDiseaseDTOList", accountDiseaseDTOList);
        model.addAttribute("accountDiseaseDTO", accountDiseaseDTO);
        model.addAttribute("accountDiseaseTotal", accountDiseaseTotal);
    }





}
