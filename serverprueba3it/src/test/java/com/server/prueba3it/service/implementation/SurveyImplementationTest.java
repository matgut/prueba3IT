package com.server.prueba3it.service.implementation;

import com.server.prueba3it.model.Survey;
import com.server.prueba3it.repository.SurveyRepository;
import com.server.prueba3it.service.SurveyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SurveyImplementationTest {

    @Mock private SurveyRepository surveyRepository;
    private SurveyImplementation underTest;

    @BeforeEach
    void setUp(){
        underTest = new SurveyImplementation(surveyRepository);
    }

    @Test
    void canListSurveys() {
        underTest.list(10);

        verify(surveyRepository).findAll(Sort.by(Sort.Direction.DESC, "surveyId"));

    }

    @Test
    void canCreateSurvey() {
        LocalDateTime actualDate = LocalDateTime.now();
        Survey survey = new Survey(
                "example@example.com",
                "Rock",
                actualDate
        );

        underTest.create(survey);

        ArgumentCaptor<Survey> surveyArgumentCaptor = ArgumentCaptor.forClass(Survey.class);

        verify(surveyRepository).save(surveyArgumentCaptor.capture());

        Survey capturedSurvey = surveyArgumentCaptor.getValue();

        assertThat(capturedSurvey).isEqualTo(survey);

    }
}