package com.example.doctor.controller.back;

import com.example.doctor.model.dto.AccountDTO;
import com.example.doctor.model.dto.MessageDTO;
import com.example.doctor.model.entity.Account;
import com.example.doctor.model.mapper.AccountMapper;
import com.example.doctor.service.AccountService;
import com.example.doctor.service.RoleService;
import com.example.doctor.validator.AccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/back/account")
public class AccountController {

    private String redirectUrl = "/back/account";

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AccountValidator accountValidator;

    @Autowired
    private AccountMapper accountMapper;

    @GetMapping(value = {"", "/"})
    public String list(Model model) {
        try {
            List<Account> accountList = accountService.findAll();
            model.addAttribute("accountList", accountMapper.toListDTO(accountList));
            return "back/account_list";
        } catch (Exception ex) {
            return "redirect:" + redirectUrl;
        }
    }

    @GetMapping(value = {"/form"})
    public String form(Model model) {
        try {
            Account account = new Account();
            model.addAttribute("messageDTO", null);
            model.addAttribute("accountDTO", account);
            model.addAttribute("roleListDTO", roleService.findAll());
            return "back/account_form";
        } catch (Exception ex) {
            return "redirect:" + redirectUrl;
        }
    }

    @GetMapping(value = {"/form/{id}"})
    public String form(Model model, @PathVariable long id, @RequestParam(required = false) String action) {
        try {
            Account account = accountService.findById(id);
            if (account == null) {
                return "redirect:" + redirectUrl;
            }

            MessageDTO messageDTO = null;

            if (action != null) {
                messageDTO = new MessageDTO(action, action.equalsIgnoreCase("success") ? "success" : "error");
            }

            model.addAttribute("messageDTO", messageDTO);
            model.addAttribute("accountDTO", account);
            model.addAttribute("roleListDTO", roleService.findAll());

            return "back/account_form";
        } catch (Exception ex) {
            return "redirect:" + redirectUrl;
        }
    }

    @PostMapping(value = "/form")
    public String save(Model model, AccountDTO accountDTO, BindingResult bindingResult) {
        try {
            // validate
            accountValidator.validate(accountDTO, bindingResult);

            if (bindingResult.hasErrors()) {
                model.addAttribute("messageDTO", new MessageDTO("error",
                        "Vui lòng kiểm tra lại thông tin!"));
                return "back/account_form";
            } else {
                // save
                Account account = accountService.save(accountDTO);
                if (account != null) {
                    redirectUrl = "/back/account/form/" + account.getId() + "?action=success";
                } else {
                    redirectUrl = "/back/account/form/" + account.getId() + "?action=error";
                }

                return "redirect:" + redirectUrl;
            }
        } catch (Exception ex) {
            return "redirect:" + redirectUrl;
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        Account account = accountService.findById(id);
        account.setStatus(false);
        accountService.save(account);
        return "redirect:" + redirectUrl;
    }

}
