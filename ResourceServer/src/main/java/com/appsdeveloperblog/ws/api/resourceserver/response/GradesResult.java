package com.appsdeveloperblog.ws.api.resourceserver.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * @author : zikoz
 * @created : 14 Jul, 2021
 */

@Getter @Setter @AllArgsConstructor @ToString
public class GradesResult {
    private Map<String, Double> mathGrade;
    private Map<String, Double> englishGrade;
    private Map<String, Double> historyGrade;
    private Map<String, Double> scienceGrade;

    // Constructor, getters, setters and toString()
}