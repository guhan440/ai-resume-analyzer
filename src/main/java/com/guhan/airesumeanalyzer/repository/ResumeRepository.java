package com.guhan.airesumeanalyzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guhan.airesumeanalyzer.entity.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

}