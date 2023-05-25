package com.staimov.textquest.controller;

import com.staimov.textquest.model.QuestChoice;
import com.staimov.textquest.model.QuestStep;
import com.staimov.textquest.service.ObjectNotFoundException;
import com.staimov.textquest.service.QuestService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.nullValue;
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
    public void welcomeShouldOpenWelcomeViewWithStatusOk() throws Exception {
        doReturn("foo").when(service).getQuestName();
        doReturn("bar").when(service).getQuestDescription();
        doReturn(22).when(service).getStartCount();
        doReturn(11).when(service).getCompleteCount();
        doReturn("baz").when(service).getPlayerName();

        mockMvc.perform(get("/welcome").accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("welcome"))
                .andExpect(model().attribute("questName", "foo"))
                .andExpect(model().attribute("questDescription", "bar"))
                .andExpect(model().attributeExists("sessionId"))
                .andExpect(model().attributeExists("clientIp"))
                .andExpect(model().attribute("startCount", 22))
                .andExpect(model().attribute("completeCount", 11))
                .andExpect(model().attribute("playerName", "baz"));
    }

    @Test
    void welcomeWithDifferentNewNameParamShouldCallSetPlayerNameMethod() throws Exception {
        doReturn("bar").when(service).getPlayerName();

        mockMvc.perform(get("/welcome")
                .param("newName", "foo")
                .accept(MediaType.TEXT_HTML));

        Mockito.verify(service, Mockito.times(1)).setPlayerName("foo");
    }

    @Test
    void welcomeWithNoNewNameParamShouldNotCallSetPlayerNameMethod() throws Exception {
        mockMvc.perform(get("/welcome")
                .accept(MediaType.TEXT_HTML));

        Mockito.verify(service, Mockito.never()).setPlayerName(anyString());
    }

    @Test
    void welcomeWithEqualNewNameParamShouldNotCallSetPlayerNameMethod() throws Exception {
        doReturn("foo").when(service).getPlayerName();

        mockMvc.perform(get("/welcome")
                .param("newName", "foo")
                .accept(MediaType.TEXT_HTML));

        Mockito.verify(service, Mockito.never()).setPlayerName("foo");
    }

    @Test
    void nextStepWithValidParamsShouldRedirectToCurrentStepViewWithValidParams() throws Exception {
        QuestStep step = new QuestStep();
        doReturn(step).when(service).getNextQuestStep(anyLong(), anyInt());

        mockMvc.perform(get("/nextStep")
                        .param("stepId", "7")
                        .param("choiceId", "8")
                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attribute("stepId", String.valueOf(step.getId())))
                .andExpect(model().attribute("prevStepId", "7"))
                .andExpect(model().attribute("prevChoiceId", "8"))
                .andExpect(view().name("redirect:/currentStep"));
    }

    @Test
    void nextStepIfNextStepNotFoundShouldLeadToError4xx() throws Exception {
        doReturn(null).when(service).getNextQuestStep(anyLong(), anyInt());

        mockMvc.perform(get("/nextStep")
                        .param("stepId", "0")
                        .param("choiceId", "0")
                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void nextStepWithChoiceIdParamOutOfBoundsShouldThrowIndexOutOfBoundsException() {
        doThrow(IndexOutOfBoundsException.class).when(service).getNextQuestStep(anyLong(), anyInt());

        assertThatThrownBy(
                () -> mockMvc.perform(get("/nextStep")
                        .param("choiceId", "0")
                        .param("stepId", "0")))
            .hasCauseInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void nextStepWithInvalidStepIdParamShouldThrowObjectNotFoundException() {
        doThrow(ObjectNotFoundException.class).when(service).getNextQuestStep(anyLong(), anyInt());

        assertThatThrownBy(
                () -> mockMvc.perform(get("/nextStep")
                        .param("choiceId", "0")
                        .param("stepId", "0")))
                .hasCauseInstanceOf(ObjectNotFoundException.class);
    }

    @Test
    void nextStepWithEmptyParamsShouldLeadToError4xx() throws Exception {
        mockMvc.perform(get("/nextStep").accept(MediaType.TEXT_HTML))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void nextStepWithNoChoiceIdShouldLeadToError4xx() throws Exception {
        mockMvc.perform(get("/nextStep")
                        .param("stepId", "0")
                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void nextStepWithNoStepIdShouldLeadToError4xx() throws Exception {
        mockMvc.perform(get("/nextStep")
                        .param("choiceId", "0")
                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void currentStepShouldOpenCurrentStepViewWithStatusOk() throws Exception {
        long prevStepId = 7;
        int prevChoiceId = 8;
        QuestStep currentStep = new QuestStep();

        doReturn("foo").when(service).getQuestName();
        doReturn(currentStep).when(service).getQuestStep(currentStep.getId());
        doReturn(22).when(service).getStartCount();
        doReturn(11).when(service).getCompleteCount();
        doReturn("baz").when(service).getPlayerName();
        doReturn("qux").when(service).getChoiceDescription(prevStepId, prevChoiceId);

        mockMvc.perform(get("/currentStep")
                        .param("stepId", String.valueOf(currentStep.getId()))
                        .param("prevStepId", String.valueOf(prevStepId))
                        .param("prevChoiceId", String.valueOf(prevChoiceId))
                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("currentStep"))
                .andExpect(model().attribute("questName", "foo"))
                .andExpect(model().attribute("prevChoiceDescription", "qux"))
                .andExpect(model().attribute("currentStep", currentStep))
                .andExpect(model().attributeExists("sessionId"))
                .andExpect(model().attributeExists("clientIp"))
                .andExpect(model().attribute("startCount", 22))
                .andExpect(model().attribute("completeCount", 11))
                .andExpect(model().attribute("playerName", "baz"));
    }

    @Test
    void currentStepWithChoiceIdParamOutOfBoundsShouldThrowIndexOutOfBoundsException() {
        doThrow(IndexOutOfBoundsException.class).when(service).getChoiceDescription(anyLong(), anyInt());
        doReturn(new QuestStep()).when(service).getQuestStep(anyLong());

        assertThatThrownBy(
                () -> mockMvc.perform(get("/currentStep")
                        .param("stepId", "0")
                        .param("prevStepId", "0")
                        .param("prevChoiceId", "0")))
                .hasCauseInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void currentStepWithInvalidStepIdParamShouldThrowObjectNotFoundException() {
        doThrow(ObjectNotFoundException.class).when(service).getChoiceDescription(anyLong(), anyInt());
        doReturn(new QuestStep()).when(service).getQuestStep(anyLong());

        assertThatThrownBy(
                () -> mockMvc.perform(get("/currentStep")
                        .param("stepId", "0")
                        .param("prevStepId", "0")
                        .param("prevChoiceId", "0")))
                .hasCauseInstanceOf(ObjectNotFoundException.class);
    }

    @Test
    void currentStepIfStepNotFoundShouldLeadToError4xx() throws Exception {
        doReturn(null).when(service).getQuestStep(anyLong());

        mockMvc.perform(get("/currentStep")
                        .param("stepId", "0")
                        .param("prevStepId", "0")
                        .param("prevChoiceId", "0")
                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void currentStepIfStepIdIsNullShouldCallStartQuestMethod() throws Exception {
        mockMvc.perform(get("/currentStep")
                        .param("prevStepId", "0")
                        .param("prevChoiceId", "0")
                        .accept(MediaType.TEXT_HTML));

        Mockito.verify(service, Mockito.times(1)).startQuest();
    }

    @Test
    void currentStepIfStepIdIsNotNullShouldNotCallStartQuestMethod() throws Exception {
        mockMvc.perform(get("/currentStep")
                .param("stepId", "0")
                .param("prevStepId", "0")
                .param("prevChoiceId", "0")
                .accept(MediaType.TEXT_HTML));

        Mockito.verify(service, Mockito.never()).startQuest();
    }

    @Test
    void currentStepIfStepIsFinalShouldCallCompleteQuestMethod() throws Exception {
        doReturn(new QuestStep()).when(service).getQuestStep(anyLong());

        mockMvc.perform(get("/currentStep")
                .param("stepId", "0")
                .param("prevStepId", "0")
                .param("prevChoiceId", "0")
                .accept(MediaType.TEXT_HTML));

        Mockito.verify(service, Mockito.times(1)).completeQuest(any());
    }

    @Test
    void currentStepIfStepIsNotFinalShouldNotCallCompleteQuestMethod() throws Exception {
        QuestStep step = new QuestStep();
        step.getChoices().add(new QuestChoice("foo", new QuestStep()));
        doReturn(step).when(service).getQuestStep(anyLong());

        mockMvc.perform(get("/currentStep")
                .param("stepId", "0")
                .param("prevStepId", "0")
                .param("prevChoiceId", "0")
                .accept(MediaType.TEXT_HTML));

        Mockito.verify(service, Mockito.never()).completeQuest(any());
    }

    @Test
    public void currentStepIfPrevStepParamIsNullShouldOpenCurrentStepViewWithValidParams() throws Exception {
        doReturn(new QuestStep()).when(service).getQuestStep(anyLong());
        doReturn("qux").when(service).getChoiceDescription(anyLong(), anyInt());

        mockMvc.perform(get("/currentStep")
                        .param("stepId", "0")
                        .param("prevChoiceId", "0")
                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("currentStep"))
                .andExpect(model().attribute("prevChoiceDescription", nullValue()));
    }

    @Test
    public void currentStepIfPrevChoiceParamIsNullShouldOpenCurrentStepViewWithValidParams() throws Exception {
        doReturn(new QuestStep()).when(service).getQuestStep(anyLong());
        doReturn("qux").when(service).getChoiceDescription(anyLong(), anyInt());

        mockMvc.perform(get("/currentStep")
                        .param("stepId", "0")
                        .param("prevStepId", "0")
                        .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("currentStep"))
                .andExpect(model().attribute("prevChoiceDescription", nullValue()));
    }
}
