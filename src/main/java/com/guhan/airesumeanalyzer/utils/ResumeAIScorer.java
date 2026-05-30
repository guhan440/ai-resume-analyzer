package com.guhan.airesumeanalyzer.utils;


import java.util.List;
import java.util.ArrayList;

public class ResumeAIScorer {

	public static int calculateScore(String resumeText,
            List<String> resumeSkills,
            String jobDescription) {

if (resumeText == null || jobDescription == null) {
return 0;
}

String resume = resumeText.toLowerCase().replaceAll("\\s+", " ");
String job = jobDescription.toLowerCase();

String[] requiredSkills = {
"java", "spring boot", "mysql", "rest api",
"hibernate", "microservices", "sql"
};

int matched = 0;

for (String skill : requiredSkills) {
if (resume.contains(skill)) {
matched++;
}
}

int totalSkills = requiredSkills.length;

// 1. Skill Score (50)
double skillScore = ((double) matched / totalSkills) * 50;

// 2. Missing Skill Penalty (20) - FIXED
int missing = totalSkills - matched;
double missingScore = Math.max(0, 20 - (missing * 4));

// 3. Experience Score (15)
double expScore = (resume.contains("experience") || resume.contains("year")) ? 15 : 5;

// 4. Keyword relevance (15) - FIXED
int keywordHits = 0;

for (String skill : requiredSkills) {
if (job.contains(skill)) {
keywordHits++;
}
}

double keywordScore = ((double) keywordHits / totalSkills) * 15;

// FINAL TOTAL
double total = skillScore + missingScore + expScore + keywordScore;

return (int) Math.min(100, total);
}

    // Extract skills from job description
    private static List<String> extractSkillsFromJob(String job) {

        List<String> skills = new ArrayList<>();

        String[] keywords = {
                "java",
                "spring boot",
                "mysql",
                "rest api",
                "hibernate",
                "microservices",
                "sql"
        };

        for (String k : keywords) {
            if (job.contains(k)) {
                skills.add(k);
            }
        }

        return skills;
    }

    // Keyword overlap scoring
    private static double calculateKeywordOverlap(String resume, String job) {

        String normalizedResume = resume.replaceAll("\\s+", " ");
        String[] keywords = {
                "java",
                "spring boot",
                "mysql",
                "rest api",
                "hibernate",
                "microservices",
                "sql"
        };

        long count = 0;

        for (String k : keywords) {
            if (normalizedResume.contains(k)) {
                count++;
            }
        }

        return Math.min(15, count * 3);
    }
}