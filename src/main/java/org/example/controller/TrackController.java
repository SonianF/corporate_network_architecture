
package org.example.controller;

import org.example.model.Track;
import org.example.model.Artist;
import org.example.service.TrackService;
import org.example.service.ArtistService;

import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.annotation.PostConstruct;

import java.io.Serializable;
import java.util.List;

@Named("trackController")
@ViewScoped
public class TrackController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private TrackService trackService;

    @EJB
    private ArtistService artistService;

    private List<Track> tracks;
    private List<Artist> artists;

    private Track track;
    private Long selectedArtistId;

    @PostConstruct
    public void init() {

        tracks = trackService.findAll();
        artists = artistService.findAll();

        track = new Track();
    }

    public void prepareNew() {

        track = new Track();
        selectedArtistId = null;

    }

    public void save() {

        track.setArtist(
                artistService.findById(selectedArtistId)
        );

        if (track.getId() == null) {

            trackService.create(track);

        } else {

            trackService.update(track);

        }

        tracks = trackService.findAll();

        track = new Track();
        selectedArtistId = null;
    }

    public void edit(Long id) {

        track = trackService.findById(id);

        selectedArtistId = track.getArtist().getId();
    }

    public void delete(Long id) {

        trackService.delete(id);

        tracks = trackService.findAll();

        track = new Track();
        selectedArtistId = null;
    }

    public void cancel() {

        track = new Track();
        selectedArtistId = null;

    }

    public List<Track> getTracks() {
        return tracks;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public Long getSelectedArtistId() {
        return selectedArtistId;
    }

    public void setSelectedArtistId(Long selectedArtistId) {
        this.selectedArtistId = selectedArtistId;
    }
}
