package com.xp.Model.DTOs;

import java.util.List;

public record CinemaGroup(Long cinemaId, String cinemaName, List<TheaterGroup> theaters) { }
