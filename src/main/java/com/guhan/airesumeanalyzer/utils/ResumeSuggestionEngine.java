
package com.guhan.airesumeanalyzer.utils;

import java.util.ArrayList;
import java.util.List;

public class ResumeSuggestionEngine {

    public static List<String> generateSuggestions(
            String skills) {

        List<String> suggestions = new ArrayList<>();

        String lowerSkills = skills.toLowerCase();

        // JAVA
        if (!lowerSkills.contains("java")) {
            suggestions.add(
                "Add Java skill for backend development roles");
        }

        // SPRING BOOT
        if (!lowerSkills.contains("spring boot")) {
            suggestions.add(
                "Learn Spring Boot framework");
        }

        // MYSQL
        if (!lowerSkills.contains("mysql")) {
            suggestions.add(
                "Add database skills like MySQL");
        }

        // REST API
        if (!lowerSkills.contains("rest api")) {
            suggestions.add(
                "Mention REST API experience");
        }

        // AWS
        if (!lowerSkills.contains("aws")) {
            suggestions.add(
                "Learn cloud platforms like AWS");
        }

        // DOCKER
        if (!lowerSkills.contains("docker")) {
            suggestions.add(
                "Add Docker knowledge");
        }

        // GITHUB
        if (!lowerSkills.contains("github")) {
            suggestions.add(
                "Include GitHub project links");
        }

        if (suggestions.isEmpty()) {
            suggestions.add(
                "Excellent resume profile");
        }

        return suggestions;
    }
}
