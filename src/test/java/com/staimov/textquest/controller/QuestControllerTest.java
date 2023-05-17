package com.staimov.textquest.controller;

import com.staimov.textquest.model.QuestModel;
import com.staimov.textquest.model.QuestStep;
import com.staimov.textquest.service.QuestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuestController.class)
class QuestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    @Qualifier("selectedQuestService")
    private QuestService service;

    @Test
    void testStartQuest() throws Exception {
        mockMvc.perform(get("/startQuest").accept(MediaType.TEXT_HTML))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/currentStep"));
    }

    @Test
    void testNextStepWithCorrectChoiceId() throws Exception {
        mockMvc.perform(get("/nextStep").param("choiceId", "0")
                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/currentStep"));
    }

    @Test
    void testNextStepWithEmptyChoiceId() throws Exception {
        mockMvc.perform(get("/nextStep").accept(MediaType.TEXT_HTML))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testNextStepIfServiceThrowsIllegalStateException() {
        doThrow(IllegalStateException.class).when(service).makeQuestChoice(anyInt());

        assertThatThrownBy(
                () -> mockMvc.perform(get("/nextStep").param("choiceId", "0")))
            .hasCauseInstanceOf(IllegalStateException.class);
    }

    @Test
    void testNextStepIfServiceThrowsIndexOutOfBoundsException() {
        doThrow(IndexOutOfBoundsException.class).when(service).makeQuestChoice(anyInt());

        assertThatThrownBy(
                () -> mockMvc.perform(get("/nextStep").param("choiceId", "0")))
            .hasCauseInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    public void testWelcome() throws Exception {
        QuestModel questModel = new QuestModel();

        doReturn(questModel).when(service).getQuestModel();

        mockMvc.perform(get("/welcome").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("welcome"))
                .andExpect(model().attribute("questModel", questModel));
    }

    @Test
    public void testCurrentStep() throws Exception {
        QuestStep currentStep = new QuestStep();
        QuestModel questModel = new QuestModel();
        questModel.setName("foo");

        doReturn(questModel).when(service).getQuestModel();
        doReturn(currentStep).when(service).getCurentQuestStep();

        mockMvc.perform(get("/currentStep").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("currentStep"))
                .andExpect(model().attribute("questName", "foo"))
                .andExpect(model().attribute("currentStep", currentStep));
    }
}
