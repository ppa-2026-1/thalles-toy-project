package com.example.demo.model.dto;

import java.util.List;

import com.example.demo.repository.entity.VulnerabilityReport;

public record VulneratibilityReportsDTO(
  List<VulnerabilityReport> content,
  int page,
  int totalPages,
  long totalElements
) {}