
package com.guhan.airesumeanalyzer.utils;

import java.util.*;

public class ResumeScorer {

    public static Map<String, Object> calculateScore(
            String skills) {

        int score = 0;

        List<String> strengths = new ArrayList<>();
        List<String> suggestions = new ArrayList<>();

        String lowerSkills = skills.toLowerCase();

        // JAVA
        if (lowerSkills.contains("java")) {
            score += 25;
            strengths.add("Good Java skills");
        } else {
            suggestions.add("Learn Java");
        }

        // SPRING BOOT
        if (lowerSkills.contains("spring boot")) {
            score += 25;
            strengths.add("Spring Boot experience");
        } else {
            suggestions.add("Learn Spring Boot");
        }

        // MYSQL
        if (lowerSkills.contains("mysql")) {
            score += 25;
            strengths.add("Database knowledge");
        } else {
            suggestions.add("Learn MySQL");
        }

        // REST API
        if (lowerSkills.contains("rest api")) {
            score += 25;
            strengths.add("REST API skills");
        } else {
            suggestions.add("Learn REST API");
        }

        Map<String, Object> result = new HashMap<>();

        result.put("score", score);
        result.put("strengths", strengths);
        result.put("suggestions", suggestions);

        return result;
    }
}

