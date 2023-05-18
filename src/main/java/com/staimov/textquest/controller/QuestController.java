package com.staimov.textquest.controller;

import com.staimov.textquest.service.QuestService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class QuestController {
    private static final Logger logger = LoggerFactory.getLogger(QuestController.class);

    private QuestService service;

    @Autowired
    public QuestController(@Qualifier("selectedQuestService") QuestService service) {
        this.service = service;
    }

    @GetMapping("/welcome")
    public String welcome(Model model, HttpSession session, HttpServletRequest request) {
        logger.debug("welcome path requested");

        service.resetQuest();
        model.addAttribute("questModel", service.getQuestModel());
        model.addAttribute("sessionId", session.getId());
        model.addAttribute("clientIp", request.getRemoteAddr());
        model.addAttribute("startCount", service.getStartCount());
        model.addAttribute("completeCount", service.getCompleteCount());
        model.addAttribute("playerName", service.getPlayerName());
        return "welcome";
    }

    @GetMapping("/startQuest")
    public String startQuest() {
        logger.debug("startQuest path requested");

        service.restartQuest();
        return "redirect:/currentStep";
    }

    @GetMapping("/nextStep")
    public String nextStep(@RequestParam("choiceId") int choiceId,
                           @RequestParam("stepId") int stepId) {
        logger.debug("nextStep path requested (choiceId = {})", choiceId);

        if (!service.isQuestStarted()) {
            throw new IllegalStateException("There is no running quest in this session");
        }
        else if (stepId != service.getCurrentQuestStep().getId()) {
            logger.warn("Looks like another client has already made a choice in this quest session");
        }
        else {
            service.makeQuestChoice(choiceId);
        }

        return "redirect:/currentStep";
    }

    @GetMapping("/currentStep")
    public String currentStep(Model model, HttpSession session, HttpServletRequest request) {
        logger.debug("currentStep path requested");

        model.addAttribute("questName", service.getQuestModel().getName());
        model.addAttribute("currentStep", service.getCurrentQuestStep());
        model.addAttribute("sessionId", session.getId());
        model.addAttribute("clientIp", request.getRemoteAddr());
        model.addAttribute("startCount", service.getStartCount());
        model.addAttribute("completeCount", service.getCompleteCount());
        model.addAttribute("playerName", service.getPlayerName());
        return "currentStep";
    }
}
