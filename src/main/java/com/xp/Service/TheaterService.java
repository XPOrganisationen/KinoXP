package com.xp.Service;

import com.xp.Model.Theater;

import java.util.List;

public interface TheaterService {
    public List<Theater> findAllTheaters();
    public Theater findTheaterById(Long id);
    public Theater createTheater(Theater theater);
    public Theater updateTheater(Theater theater);
    public void deleteTheaterById(Long id);
}
