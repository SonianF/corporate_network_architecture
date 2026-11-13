
package org.example.controller;

import org.example.model.Playlist;
import org.example.model.Track;
import org.example.service.PlaylistService;
import org.example.service.TrackService;

import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.application.FacesMessage;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named("playlistTracksController")
@ViewScoped
public class PlaylistTracksController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private PlaylistService playlistService;

    @EJB
    private TrackService trackService;

    private Playlist playlist;

    private Track selectedTrack;

    private Long selectedTrackId;

    private List<Track> availableTracks;

    @PostConstruct
    public void init() {

        Map<String, String> params =
                FacesContext.getCurrentInstance()
                        .getExternalContext()
                        .getRequestParameterMap();

        String id = params.get("id");

        if (id != null) {

            playlist = playlistService.findById(Long.valueOf(id));

        }

        availableTracks = trackService.findAll();

        selectedTrack = new Track();
    }

    public void prepareNew() {

        selectedTrack = new Track();
        selectedTrackId = null;

    }

    public void edit(Long id) {

        selectedTrack = trackService.findById(id);

        if (selectedTrack != null) {
            selectedTrackId = selectedTrack.getId();
        }

    }

    public void saveTrack() {

        if (playlist == null || selectedTrackId == null) {
            return;
        }

        Track track = trackService.findById(selectedTrackId);

        if (track == null) {
            return;
        }

        if (selectedTrack.getId() == null) {

            playlistService.addTrack(
                    playlist.getId(),
                    track.getId()
            );

        } else {

            playlistService.replaceTrack(
                    playlist.getId(),
                    selectedTrack.getId(),
                    track.getId()
            );

        }

        playlist = playlistService.findById(playlist.getId());

        selectedTrack = new Track();
        selectedTrackId = null;

    }

    public void removeTrack(Long trackId) {

        if (playlist == null) {
            return;
        }

        playlistService.removeTrack(
                playlist.getId(),
                trackId
        );

        playlist = playlistService.findById(playlist.getId());

    }

    public void cancel() {

        selectedTrack = new Track();
        selectedTrackId = null;

    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public Track getSelectedTrack() {
        return selectedTrack;
    }

    public Long getSelectedTrackId() {
        return selectedTrackId;
    }

    public void setSelectedTrackId(Long selectedTrackId) {
        this.selectedTrackId = selectedTrackId;
    }

    public List<Track> getAvailableTracks() {
        return availableTracks;
    }

}
