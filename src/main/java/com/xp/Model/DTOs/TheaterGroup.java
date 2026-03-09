package com.xp.Model.DTOs;

import com.xp.Model.Show;

import java.util.List;

public record TheaterGroup(Long theaterId, String theaterName, List<Show> shows) { }