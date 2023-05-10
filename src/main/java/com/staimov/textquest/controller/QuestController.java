package com.staimov.textquest.controller;

import com.staimov.textquest.service.QuestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class QuestController {
    private static final Logger logger = LoggerFactory.getLogger(QuestController.class);

    private QuestService service;

    @Autowired
    public QuestController(QuestService service) {
        this.service = service;
    }

    @GetMapping("/welcome")
    public String welcome(Model model) {
        service.resetQuest();
        model.addAttribute("questModel", service.getQuestModel());
        return "welcome";
    }

    @GetMapping("/startQuest")
    public String startQuest(Model model) {
        service.restartQuest();
        return "redirect:/currentStep";
    }

    @GetMapping("/nextStep")
    public String nextStep(@RequestParam("choiceId") int choiceId) {
        service.nextQuestStep(choiceId);
        return "redirect:/currentStep";
    }

    @GetMapping("/currentStep")
    public String currentStep(Model model) {
        model.addAttribute("questName", service.getQuestModel().getName());
        model.addAttribute("currentStep", service.getCurentQuestStep());
        return "currentStep";
    }
}
