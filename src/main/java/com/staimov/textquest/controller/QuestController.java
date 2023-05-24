package com.staimov.textquest.controller;

import com.staimov.textquest.model.QuestStep;
import com.staimov.textquest.service.QuestService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class QuestController {
    private static final Logger logger = LoggerFactory.getLogger(QuestController.class);

    private QuestService service;

    @Autowired
    public QuestController(@Qualifier("selectedQuestService") QuestService service) {
        this.service = service;
    }

    @GetMapping("/welcome")
    public String welcome(@RequestParam(value = "newName", required = false) String newName,
                          Model model,
                          HttpSession session,
                          HttpServletRequest request) {
        logger.debug("welcome path requested");

        if (newName != null && !newName.isBlank()
                && !newName.equals(service.getPlayerName())) {
            service.setPlayerName(newName);
        }

        model.addAttribute("questName", service.getQuestName());
        model.addAttribute("questDescription", service.getQuestDescription());
        model.addAttribute("sessionId", session.getId());
        model.addAttribute("clientIp", request.getRemoteAddr());
        model.addAttribute("startCount", service.getStartCount());
        model.addAttribute("completeCount", service.getCompleteCount());
        model.addAttribute("playerName", service.getPlayerName());

        return "welcome";
    }

    @GetMapping("/nextStep")
    public String nextStep(@RequestParam("stepId") long stepId,
                           @RequestParam("choiceId") int choiceId,
                           RedirectAttributes redirectAttributes) {
        logger.debug("nextStep path requested (stepId = {}, choiceId = {})",
                stepId, choiceId);

        QuestStep nextStep = null;

        nextStep = service.getNextQuestStep(stepId, choiceId);

        if (nextStep == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Quest step not found"
            );
        }

        long nextStepId = nextStep.getId();

        logger.debug("Choice id {} is made on step {}, proceeding to step {}",
                choiceId, stepId, nextStepId);

        redirectAttributes.addAttribute("stepId", nextStepId);
        redirectAttributes.addAttribute("prevStepId", stepId);
        redirectAttributes.addAttribute("prevChoiceId", choiceId);

        return "redirect:/currentStep";
    }

    @GetMapping("/currentStep")
    public String currentStep(@RequestParam(value = "stepId", required = false) Long stepId,
                              @RequestParam(value = "prevStepId", required = false) Long prevStepId,
                              @RequestParam(value = "prevChoiceId", required = false) Integer prevChoiceId,
                              Model model,
                              HttpSession session,
                              HttpServletRequest request) {

        logger.debug("currentStep path requested (stepId = {}, prevStepId = {}, prevChoiceId = {})",
                stepId, prevStepId, prevChoiceId);

        QuestStep currentStep = null;
        String prevChoiceDescription = null;

        if (stepId == null) {
            currentStep = service.getQuestRoot();
            service.startQuest();
        }
        else {
            currentStep = service.getQuestStep(stepId);
        }

        if (currentStep == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Quest step not found"
            );
        }

        if (prevStepId != null && prevChoiceId != null) {
            prevChoiceDescription = service.getChoiceDescription(prevStepId, prevChoiceId);
        }

        if (currentStep.isFinal()) {
            service.completeQuest(currentStep.getType());
        }

        model.addAttribute("questName", service.getQuestName());
        model.addAttribute("currentStep", currentStep);
        model.addAttribute("prevChoiceDescription", prevChoiceDescription);
        model.addAttribute("sessionId", session.getId());
        model.addAttribute("clientIp", request.getRemoteAddr());
        model.addAttribute("startCount", service.getStartCount());
        model.addAttribute("completeCount", service.getCompleteCount());
        model.addAttribute("playerName", service.getPlayerName());

        return "currentStep";
    }
}
