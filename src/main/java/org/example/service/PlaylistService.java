
package org.example.service;

import org.example.model.Playlist;
import org.example.model.Track;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class PlaylistService {

    @PersistenceContext(unitName = "MusicStreamingPU")
    private EntityManager em;

    public List<Playlist> findAll() {
        return em.createQuery(
                "SELECT p FROM Playlist p ORDER BY p.title",
                Playlist.class).getResultList();
    }

    public Playlist findById(Long id) {
        List<Playlist> result = em.createQuery(
                        "SELECT DISTINCT p FROM Playlist p " +
                                "LEFT JOIN FETCH p.tracks t " +
                                "LEFT JOIN FETCH t.artist " +
                                "WHERE p.id = :id",
                        Playlist.class
                )
                .setParameter("id", id)
                .getResultList();

        return result.isEmpty() ? null : result.get(0);
    }

    public void create(Playlist playlist) {
        em.persist(playlist);
    }

    public Playlist update(Playlist playlist) {
        return em.merge(playlist);
    }

    public void delete(Long id) {
        Playlist playlist = em.find(Playlist.class, id);

        if (playlist != null) {
            em.remove(playlist);
        }
    }

    public void addTrack(Long playlistId, Long trackId) {

        Playlist playlist = em.find(Playlist.class, playlistId);
        Track track = em.find(Track.class, trackId);

        if (playlist != null && track != null) {

            if (!playlist.getTracks().contains(track)) {

                playlist.getTracks().add(track);
                track.getPlaylists().add(playlist);

            }
        }
    }

    public void removeTrack(Long playlistId, Long trackId) {

        Playlist playlist = em.find(Playlist.class, playlistId);

        if (playlist != null) {

            Track track = em.find(Track.class, trackId);

            if (track != null) {

                playlist.getTracks().remove(track);
                track.getPlaylists().remove(playlist);

            }
        }
    }

    public void replaceTrack(
            Long playlistId,
            Long oldTrackId,
            Long newTrackId) {

        Playlist playlist = em.find(Playlist.class, playlistId);

        Track oldTrack = em.find(Track.class, oldTrackId);
        Track newTrack = em.find(Track.class, newTrackId);

        if (playlist != null && oldTrack != null && newTrack != null) {

            if (!playlist.getTracks().contains(newTrack)) {

                playlist.getTracks().remove(oldTrack);
                oldTrack.getPlaylists().remove(playlist);

                playlist.getTracks().add(newTrack);
                newTrack.getPlaylists().add(playlist);

            }
        }
    }

}
