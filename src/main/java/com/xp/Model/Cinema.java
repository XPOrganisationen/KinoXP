package com.xp.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Cinemas")
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cinemaId;

    @OneToMany(mappedBy = "cinema")
    private List<Theater> theaters;

    private String cinemaName; // Remember that this becomes snake_case in the DB by default
    private String cinemaAddress;

    public Cinema() {}

    public Cinema(String cinemaName, String cinemaAddress) {
        this.cinemaName = cinemaName;
        this.cinemaAddress = cinemaAddress;
    }

    public Long getCinemaId() {
        return  cinemaId;
    }

    public String getCinemaAddress() {
        return cinemaAddress;
    }

    public void setCinemaAddress(String cinemaAddress) {
        this.cinemaAddress = cinemaAddress;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }
}
