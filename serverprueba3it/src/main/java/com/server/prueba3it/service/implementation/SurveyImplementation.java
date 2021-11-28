package com.server.prueba3it.service.implementation;

import com.server.prueba3it.model.Survey;
import com.server.prueba3it.repository.SurveyRepository;
import com.server.prueba3it.service.SurveyService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class SurveyImplementation implements SurveyService {

    private final SurveyRepository surveyRepository;


    @Override
    public List<Survey> list(int limit) {
        log.info("obteniendo todas las encuestas");
        return (List<Survey>) surveyRepository.findAll(Sort.by(Sort.Direction.DESC, "surveyId"));
    }

    @Override
    public Survey create(Survey survey) {
        log.info("Guardando nueva encuesta: {}", survey.getSurveyId());
        survey.setCreationDate(LocalDateTime.now());
        return surveyRepository.save(survey);
    }
}
