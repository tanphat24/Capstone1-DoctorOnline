package com.example.doctor.controller.front;

import com.example.doctor.model.dto.CommentDTO;
import com.example.doctor.model.dto.MessageDTO;
import com.example.doctor.model.entity.*;
import com.example.doctor.model.mapper.DiseaseMapper;
import com.example.doctor.model.mapper.PrecautionMapper;
import com.example.doctor.model.mapper.SymptomMapper;
import com.example.doctor.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/front/article")
public class ArticleController {

    private String redirectUrl = "/front/article";

    @Autowired
    private AccountService accountService;

    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private SymptomService symptomService;

    @Autowired
    private PrecautionService precautionService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private DiseaseSymptomService diseaseSymptomService;

    @Autowired
    private DiseasePrecautionService diseasePrecautionService;

    @Autowired
    private SymptomMapper symptomMapper;

    @Autowired
    private DiseaseMapper diseaseMapper;

    @Autowired
    private PrecautionMapper precautionMapper;

    @GetMapping(value = {"/disease", "/disease/"})
    public String list(Model model) {
        try {
            List<Disease> diseaseList = diseaseService.findAll();
            model.addAttribute("diseaseList", diseaseList);
            return "front/disease";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "redirect:" + redirectUrl;
        }
    }

    @GetMapping(value = {"/disease/{diseaseId}", "/disease/{diseaseId}"})
    public String view(Model model, @PathVariable Long diseaseId) {
        try {
            // get disease
            Disease disease = setValue(model, diseaseId);
            if (disease == null) {
                return "front/not_found";
            }

            return "front/article";
        } catch (Exception ex) {
            return "redirect:" + redirectUrl;
        }
    }

    @PostMapping(value = {"/comment", "/comment/"})
    public String comment(Model model, CommentDTO commentDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            Account customAccount = customUserDetails.getAccount();

            // get disease
            Disease disease = setValue(model, commentDTO.getDiseaseId());
            if (disease == null) {
                return "front/not_found";
            }

            // validator
            if (commentDTO.getContent() == null || commentDTO.getContent().trim().equalsIgnoreCase("")) {
                model.addAttribute("messageDTO", new MessageDTO("save",
                        "Vui lòng nhập nội dung bình luận!"));

                return "front/article";
            } else {
                Account account = accountService.findById(customAccount.getId());

                // save
                Comment comment = new Comment();
                comment.setAccount(account);
                comment.setDisease(disease);
                comment.setStatus(false);
                comment.setContent(commentDTO.getContent());
                commentService.save(comment);

                // redirect to review
                redirectUrl = "/front/review";
                return "redirect:" + redirectUrl;
            }
        } catch (Exception ex) {
            return "redirect:" + redirectUrl;
        }
    }

    private Disease setValue(Model model, long diseaseId) {
        // get disease
        Disease disease = diseaseService.findById(diseaseId);
        if (disease == null) {
            return null;
        }

        // get list symptom
        List<DiseaseSymptom> diseaseSymptomList = diseaseSymptomService.findByDisease(disease);
        List<Long> symptomIds = diseaseSymptomList.stream()
                .map((DiseaseSymptom d) -> d.getSymptom().getId())
                .collect(Collectors.toList());
        List<Symptom> symptomList = symptomService.findSymptomByIdIn(symptomIds);

        // get list precaution
        List<DiseasePrecaution> diseasePrecautionList = diseasePrecautionService.findByDisease(disease);
        List<Long> precautionIds = diseasePrecautionList.stream()
                .map((DiseasePrecaution d) -> d.getPrecaution().getId())
                .collect(Collectors.toList());
        List<Precaution> precautionList = precautionService.findPrecautionByIdIn(precautionIds);

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent(null);
        commentDTO.setDiseaseId(disease.getId());

        model.addAttribute("diseaseDTO", diseaseMapper.toDTO(disease));
        model.addAttribute("symptomList", symptomMapper.toListDTO(symptomList));
        model.addAttribute("precautionList", precautionMapper.toListDTO(precautionList));
        model.addAttribute("commentList", commentService.findByDisease(disease));
        model.addAttribute("commentDTO", commentDTO);

        return disease;
    }

}
