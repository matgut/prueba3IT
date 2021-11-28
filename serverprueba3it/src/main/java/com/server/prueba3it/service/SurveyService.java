package com.server.prueba3it.service;

import com.server.prueba3it.model.Survey;

import java.util.Collection;
import java.util.List;

public interface SurveyService {
    List<Survey> list(int limit);
    Survey create(Survey survey);

}
