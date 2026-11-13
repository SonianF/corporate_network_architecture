
package org.example.service;

import org.example.model.Track;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class TrackService {
    @PersistenceContext(unitName = "MusicStreamingPU")
    private EntityManager em;

    public List<Track> findAll() {
        return em.createQuery(
                "SELECT t FROM Track t ORDER BY t.id", Track.class
        ).getResultList();
    }

    public Track findById(Long id) {
        return em.find(Track.class, id);
    }

    public void create(Track track) {
        em.persist(track);
    }

    public Track update (Track track) {
        return em.merge(track);
    }

    public void delete (Long id) {
        Track track = em.find(Track.class, id);
        if(track!= null) {
            for (var playlist : track.getPlaylists()) {
                playlist.getTracks().remove(track);
            }

            em.remove(track);
        }
    }

    public List<Track> findByArtist(Long artistId) {
        return em.createQuery(
                        "SELECT t FROM Track t WHERE t.artist.id = :artistId",
                        Track.class
                )
                .setParameter("artistId", artistId)
                .getResultList();
    }
}