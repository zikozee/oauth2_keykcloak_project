package com.appsdeveloperblog.ws.api.resourceserver.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : zikoz
 * @created : 14 Jul, 2021
 */

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Student {
    private String name;
    private double mathGrade;
    private double englishGrade;
    private double historyGrade;
    private double scienceGrade;

    // Constructor, getters and setters and toString()
}