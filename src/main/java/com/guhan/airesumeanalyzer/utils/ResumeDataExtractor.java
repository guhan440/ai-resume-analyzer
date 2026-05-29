
package com.guhan.airesumeanalyzer.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResumeDataExtractor {

    // EMAIL
    public static String extractEmail(String text) {

        Pattern pattern = Pattern.compile(
                "[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+");

        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group();
        }

        return "Email Not Found";
    }

    // PHONE
    public static String extractPhone(String text) {

        Pattern pattern = Pattern.compile(
                "\\b\\d{10}\\b");

        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group();
        }

        return "Phone Not Found";
    }

    // SKILLS
    public static String extractSkills(String text) {

        String[] knownSkills = {
                "Java",
                "Spring Boot",
                "MySQL",
                "REST API",
                "Hibernate",
                "Microservices",
                "Python",
                "React",
                "AWS"
        };

        StringBuilder skills = new StringBuilder();

        for (String skill : knownSkills) {

            if (text.toLowerCase()
                    .contains(skill.toLowerCase())) {

                skills.append(skill).append(", ");
            }
        }

        if (skills.length() > 0) {
            return skills.substring(0,
                    skills.length() - 2);
        }

        return "No Skills Found";
    }
}

