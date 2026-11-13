package org.example.model;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="track")
public class Track {
    @Id
    @Column(name="track_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "duration_sec")
    private Integer durationSec;

    @Column(name = "play_count")
    private Long playCount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @ManyToMany(mappedBy = "tracks")
    private List<Playlist> playlists = new ArrayList<>();

    public Track() {
    }

    public String getDurationFormatted() {
        if (durationSec == null) return "";
        return String.format("%02d:%02d", durationSec / 60, durationSec % 60);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDurationSec() {
        return durationSec;
    }

    public void setDurationSec(Integer duration_sec) {
        this.durationSec = duration_sec;
    }

    public Long getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Long play_count) {
        this.playCount = play_count;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist_id) {
        this.artist = artist_id;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }
}
