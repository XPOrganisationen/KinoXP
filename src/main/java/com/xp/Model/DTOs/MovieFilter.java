package com.xp.Model.DTOs;

import java.util.List;

public record MovieFilter(List<String> categories, Integer ageLimit, String title) {}
