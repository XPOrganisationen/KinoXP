package com.xp.Repository;

import com.xp.Model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRepository extends JpaRepository <Theater, Long> {
}
