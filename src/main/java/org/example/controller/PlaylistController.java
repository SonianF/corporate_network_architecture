
package org.example.controller;

import org.example.model.Playlist;
import org.example.model.User;
import org.example.service.PlaylistService;
import org.example.service.UserService;

import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.annotation.PostConstruct;

import java.io.Serializable;
import java.util.List;

@Named("playlistController")
@ViewScoped
public class PlaylistController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private PlaylistService playlistService;

    @EJB
    private UserService userService;

    private List<Playlist> playlists;
    private List<User> users;

    private Playlist playlist;
    private Long selectedUserId;

    @PostConstruct
    public void init() {

        playlists = playlistService.findAll();
        users = userService.findAll();

        playlist = new Playlist();
    }

    public void prepareNew() {

        playlist = new Playlist();
        selectedUserId = null;

    }

    public void save() {

        playlist.setUser(userService.findById(selectedUserId));

        if (playlist.getId() == null) {

            playlistService.create(playlist);

        } else {

            playlistService.update(playlist);

        }

        playlists = playlistService.findAll();

        playlist = new Playlist();
        selectedUserId = null;
    }

    public void edit(Long id) {
        playlist = playlistService.findById(id);

        if (playlist != null) {
            selectedUserId = playlist.getUser().getId();
        }
    }

    public void delete(Long id) {

        playlistService.delete(id);

        playlists = playlistService.findAll();

        playlist = new Playlist();
        selectedUserId = null;
    }

    public void cancel() {

        playlist = new Playlist();
        selectedUserId = null;

    }

    // переход к трекам плейлиста
    public String viewTracks(Long id) {

        return "playlist-tracks?faces-redirect=true&id=" + id;

    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public List<User> getUsers() {
        return users;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public Long getSelectedUserId() {
        return selectedUserId;
    }

    public void setSelectedUserId(Long selectedUserId) {
        this.selectedUserId = selectedUserId;
    }


}
