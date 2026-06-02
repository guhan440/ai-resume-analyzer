package com.guhan.airesumeanalyzer.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

import com.guhan.airesumeanalyzer.dto.ResumeDTO;
import com.guhan.airesumeanalyzer.dto.JobDescriptionDTO;
import com.guhan.airesumeanalyzer.entity.Resume;
import com.guhan.airesumeanalyzer.service.ResumeParserService;
import com.guhan.airesumeanalyzer.service.ResumeService;
import com.guhan.airesumeanalyzer.utils.ResumeDataExtractor;
import com.guhan.airesumeanalyzer.utils.ResumeMatcher;
import com.guhan.airesumeanalyzer.utils.ResumeSuggestionEngine;
import com.guhan.airesumeanalyzer.utils.ResumeAIScorer;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/resume")
@CrossOrigin(origins = "http://localhost:5173")

public class ResumeController {

    @Autowired
    private ResumeService service;

    @Autowired
    private ResumeParserService parserService;

    // CREATE
    @PostMapping
    public Resume save(@Valid @RequestBody ResumeDTO dto) {
        Resume resume = new Resume();
        resume.setName(dto.getName());
        resume.setEmail(dto.getEmail());
        resume.setSkills(dto.getSkills());
        return service.saveResume(resume);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Resume update(@PathVariable Long id,
                         @Valid @RequestBody ResumeDTO dto) {
        Resume resume = new Resume();
        resume.setName(dto.getName());
        resume.setEmail(dto.getEmail());
        resume.setSkills(dto.getSkills());
        return service.updateResume(id, resume);
    }

    // GET ALL
    @GetMapping
    public List<Resume> getAllResumes() {
        return service.getAllResumes();
    }

    // ANALYZE
    @GetMapping("/analyze/{id}")
    public Map<String, String> analyzeResume(@PathVariable Long id) throws IOException {

        Resume resume = service.getResumeById(id);

        String text = parserService.extractText(resume.getFilePath());

        Map<String, String> result = new HashMap<>();
        result.put("email", ResumeDataExtractor.extractEmail(text));
        result.put("phone", ResumeDataExtractor.extractPhone(text));
        result.put("skills", ResumeDataExtractor.extractSkills(text));

        return result;
    }

    // MATCH + AI SCORE ⭐ FIXED
    @PostMapping("/match/{id}")
    public Map<String, Object> matchResume(
            @PathVariable Long id,
            @RequestBody JobDescriptionDTO dto) throws IOException {

        Resume resume = service.getResumeById(id);

        String text = parserService.extractText(resume.getFilePath());

        String extractedSkills = ResumeDataExtractor.extractSkills(text);

        Map<String, Object> result =
                ResumeMatcher.matchSkills(extractedSkills, dto.getJobDescription());

        // ✅ FIXED SKILL LIST
        List<String> skillList = new ArrayList<>();

        if (extractedSkills != null && !extractedSkills.isEmpty()) {
            String[] arr = extractedSkills.split(",");

            for (String s : arr) {
                skillList.add(s.trim().toLowerCase());
            }
        }

        // ⭐ AI SCORE
        int aiScore = ResumeAIScorer.calculateScore(
                text,
                null,
                dto.getJobDescription()
        );

        result.put("aiScore", aiScore);

        return result;
    }

    // SUGGESTIONS
    @GetMapping("/suggest/{id}")
    public List<String> getSuggestions(@PathVariable Long id) throws IOException {

        Resume resume = service.getResumeById(id);

        String text = parserService.extractText(resume.getFilePath());

        String skills = ResumeDataExtractor.extractSkills(text);

        return ResumeSuggestionEngine.generateSuggestions(skills);
    }

    // UPLOAD
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public Resume uploadResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam String name,
            @RequestParam String email) throws IOException {

        String uploadDir = System.getProperty("user.dir") + "/uploads/";

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String filePath = uploadDir + file.getOriginalFilename();
        file.transferTo(new File(filePath));

        String text = parserService.extractText(filePath);

        String skills = ResumeDataExtractor.extractSkills(text);

        Resume resume = new Resume();
        resume.setName(name);
        resume.setEmail(email);
        resume.setSkills(skills);
        resume.setFilePath(filePath);

        return service.saveResume(resume);
    }
}