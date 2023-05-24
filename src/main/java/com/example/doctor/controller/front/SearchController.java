package com.example.doctor.controller.front;

import com.example.doctor.model.dto.MessageDTO;
import com.example.doctor.model.dto.SearchDTO;
import com.example.doctor.model.entity.Account;
import com.example.doctor.model.entity.Disease;
import com.example.doctor.model.entity.Symptom;
import com.example.doctor.model.mapper.DiseaseMapper;
import com.example.doctor.model.mapper.SymptomMapper;
import com.example.doctor.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/front/search")
public class SearchController {

    private String redirectUrl = "/front/search";

    @Autowired
    private SearchService searchService;

    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private SymptomService symptomService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private SymptomMapper symptomMapper;

    @Autowired
    private DiseaseMapper diseaseMapper;

    @GetMapping(value = {"", "/"})
    public String view(Model model) {
        try {
            SearchDTO searchDTO = new SearchDTO();

            List<Symptom> symptomList = symptomService.findByActive();
            model.addAttribute("searchDTO", searchDTO);
            model.addAttribute("symptomListAll", symptomMapper.toListDTO(symptomList));
            return "front/search";
        } catch (Exception ex) {
            return "redirect:" + redirectUrl;
        }
    }

    @PostMapping(value = {"", "/"})
    public String search(Model model, SearchDTO searchDTO) {
        try {
            List<Disease> diseaseList = searchService.findDiseaseBySymptom(searchDTO);

            List<Long> diseaseIds = new ArrayList<>();
            HashMap<Long, Integer> commentMap = new HashMap<>();

            if (diseaseList == null || diseaseList.isEmpty()) {
                model.addAttribute("messageDTO", new MessageDTO("save",
                        "Không tìm thấy kết quả phù hợp!"));
            } else {
                diseaseIds = diseaseList.stream()
                        .map(Disease::getId)
                        .collect(Collectors.toList());

                commentMap = commentService.findByDiseaseIds(diseaseIds);
            }

            model.addAttribute("diseaseList", diseaseMapper.toListDTO(diseaseList));
            model.addAttribute("diseaseIds", diseaseIds.toString());
            model.addAttribute("symptomList", searchDTO.getSymptomList());
            model.addAttribute("commentMap", commentMap);

            return "front/result";
        } catch (Exception ex) {
            return "redirect:" + redirectUrl;
        }
    }

    @PostMapping(value = {"/form", "/form/"})
    public String save(Model model, SearchDTO searchDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            Account account = customUserDetails.getAccount();

            // validator
            if (searchDTO.getDiseaseList() == null) {
                List<Long> diseaseIds = Arrays.asList(searchDTO.getDiseaseIds()
                                .replace("[", "").replace("]", "")
                                .split(",")).stream()
                        .map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
                List<Disease> diseaseList = diseaseService.findDiseaseByIdIn(diseaseIds);

                HashMap<Long, Integer> commentMap = commentService.findByDiseaseIds(diseaseIds);

                model.addAttribute("diseaseList", diseaseMapper.toListDTO(diseaseList));
                model.addAttribute("diseaseIds", diseaseIds.toString());
                model.addAttribute("symptomList", searchDTO.getSymptomList());
                model.addAttribute("commentMap", commentMap);
                model.addAttribute("messageDTO", new MessageDTO("save",
                        "Bạn cần chọn ít nhất 1 loại bệnh để lưu!"));

                return "front/result";
            } else {
                // save
                searchService.save(account.getId(), searchDTO);

                // redirect to history
                redirectUrl = "/front/history";
                return "redirect:" + redirectUrl;
            }
        } catch (Exception ex) {
            return "redirect:" + redirectUrl;
        }
    }

    @PostMapping(value = {"/retry", "/retry/"})
    public String retry(Model model, SearchDTO searchDTO) {
        try {
            List<String> symptomIds = Arrays.asList(searchDTO.getSymptomList().toString()
                            .replace("[", "").replace("]", "")
                            .split(",")).stream()
                    .map(s -> s.trim()).collect(Collectors.toList());

            searchDTO.setSymptomList(symptomIds);

            List<Symptom> symptomList = symptomService.findByActive();
            model.addAttribute("searchDTO", searchDTO);
            model.addAttribute("symptomListAll", symptomMapper.toListDTO(symptomList));
            return "front/search";
        } catch (Exception ex) {
            return "redirect:" + redirectUrl;
        }
    }

}
