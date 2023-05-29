package com.example.doctor.controller.back;

import com.example.doctor.model.dto.AccountDTO;
import com.example.doctor.model.dto.AccountHistoryDTO;
import com.example.doctor.model.mapper.AccountHistoryMapper;
import com.example.doctor.service.AccountHistoryService;
import com.example.doctor.util.DateUtil;
import com.example.doctor.util.ValidatorUtil;
import com.example.doctor.validator.AccountHistoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/back/report-history")
public class ReportHistoryController {

    private String redirectUrl = "/back/report-history";

    @Autowired
    private AccountHistoryService accountHistoryService;

    @Autowired
    private AccountHistoryMapper accountHistoryMapper;

    @Autowired
    private AccountHistoryValidator accountHistoryValidator;

    @Autowired
    private ValidatorUtil validatorUtil;

    @GetMapping(value = {"", "/"})
    public String list(Model model) {
        Date date = new Date();
        String dateStr = DateUtil.convertDateToString(date, "yyyy-MM-dd");

        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
        accountHistoryDTO.setCreatedOn(dateStr);
        accountHistoryDTO.setUpdatedOn(dateStr);
        accountHistoryDTO.setBegin(true);

        setReport(model, accountHistoryDTO);
        return "/back/report_history";
    }

    @GetMapping(value = {"/search"})
    public String search(Model model, AccountHistoryDTO accountHistoryDTO, BindingResult bindingResult) {
        accountHistoryValidator.validate(accountHistoryDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            Date date = new Date();
            String dateStr = DateUtil.convertDateToString(date, "yyyy-MM-dd");
            accountHistoryDTO.setCreatedOn(dateStr);
            accountHistoryDTO.setUpdatedOn(dateStr);
            setReport(model, accountHistoryDTO);
            model.addAttribute("errorList", validatorUtil.toErrors(bindingResult.getFieldErrors()));
            return "/back/report_history";
        }

        setReport(model, accountHistoryDTO);
        return "/back/report_history";
    }

    public void setReport(Model model, AccountHistoryDTO accountHistoryDTO) {
        List<AccountHistoryDTO> accountHistoryList = accountHistoryMapper.toListDTO(accountHistoryService.findByCreatedOn(accountHistoryDTO));

        Map<Long, AccountDTO> accountMap = new HashMap<>();
        Map<String, String> diseaseMap = new HashMap<>();   

        for (AccountHistoryDTO accountHistory : accountHistoryList) {
            // account
            accountMap.put(accountHistory.getAccountDTO().getId(), accountHistory.getAccountDTO());

            // disease
            List<String> diseaseList = Arrays.asList(accountHistory.getDisease()
                            .replace("[", "").replace("]", "")
                            .split(",")).stream()
                    .map(s -> s.trim()).collect(Collectors.toList());
            if (diseaseList != null) {
                for (String s : diseaseList) {
                    diseaseMap.put(s, s);
                }
            }
        }

        accountHistoryDTO.setSumAccount(accountMap.size());
        accountHistoryDTO.setSumDisease(diseaseMap.size());
        accountHistoryDTO.setSumAccountHistory(accountHistoryList.size());

        model.addAttribute("accountHistoryList", accountHistoryList);
        model.addAttribute("accountHistoryDTO", accountHistoryDTO);
    }

}
