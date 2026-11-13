
package org.example.controller;

import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.annotation.PostConstruct;

import org.example.model.Artist;
import org.example.service.ArtistService;

import java.io.Serializable;
import java.util.List;

@Named("artistController")
@ViewScoped
public class ArtistController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private ArtistService artistService;

    private List<Artist> artists;
    private Artist artist;

    @PostConstruct
    public void init() {
        artists = artistService.findAll();
        artist = new Artist();
    }


    public void prepareNew() {
        artist = new Artist();
    }


    public void save() {

        if (artist.getId() == null) {
            artistService.create(artist);
        } else {
            artistService.update(artist);
        }

        refresh();
    }


    public void edit(Long id) {

        artist = artistService.findById(id);

        if (artist == null) {
            artist = new Artist();
        }
    }


    public void delete(Long id) {
        artistService.delete(id);
        refresh();
    }

    private void refresh() {
        artists = artistService.findAll();
        artist = new Artist();
    }

    public void cancel() {
        artist = new Artist();
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
