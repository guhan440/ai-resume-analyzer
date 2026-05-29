package com.guhan.airesumeanalyzer.utils;


import java.util.*;

public class ResumeMatcher {

    public static Map<String, Object> matchSkills(
            String resumeSkills,
            String jobDescription) {

        List<String> matchedSkills = new ArrayList<>();
        List<String> missingSkills = new ArrayList<>();

        String[] skillsArray = resumeSkills.split(",");

        int totalSkills = skillsArray.length;

        for (String skill : skillsArray) {

            String trimmedSkill = skill.trim();

            if (jobDescription.toLowerCase()
                    .contains(trimmedSkill.toLowerCase())) {

                matchedSkills.add(trimmedSkill);

            } else {

                missingSkills.add(trimmedSkill);
            }
        }

        int matchPercentage =
                (matchedSkills.size() * 100) / totalSkills;

        Map<String, Object> result = new HashMap<>();

        result.put("matchPercentage", matchPercentage);
        result.put("matchedSkills", matchedSkills);
        result.put("missingSkills", missingSkills);

        return result;
    }
}