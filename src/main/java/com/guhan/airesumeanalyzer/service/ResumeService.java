package com.guhan.airesumeanalyzer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guhan.airesumeanalyzer.entity.Resume;
import com.guhan.airesumeanalyzer.repository.ResumeRepository;

@Service
public class ResumeService {

    @Autowired
    private ResumeRepository repository;

    // SAVE
    public Resume saveResume(Resume resume) {
        return repository.save(resume);
    }

    // GET ALL
    public List<Resume> getAllResumes() {
        return repository.findAll();
    }

    // GET BY ID
    public Resume getResumeById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // UPDATE
    public Resume updateResume(Long id, Resume updatedResume) {

        Resume existing = repository.findById(id).orElse(null);

        if (existing != null) {
            existing.setName(updatedResume.getName());
            existing.setEmail(updatedResume.getEmail());
            existing.setSkills(updatedResume.getSkills());

            return repository.save(existing);
        }

        return null;
    }
}