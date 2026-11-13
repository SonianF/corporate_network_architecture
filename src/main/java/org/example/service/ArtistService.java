
package org.example.service;

import org.example.model.Artist;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class ArtistService {

    @PersistenceContext(unitName = "MusicStreamingPU")
    private EntityManager em;

    public List<Artist> findAll() {
        return em.createQuery(
                "SELECT a FROM Artist a ORDER BY a.id",
                Artist.class
        ).getResultList();
    }

    public Artist findById(Long id) {
        return em.find(Artist.class, id);
    }

    public void create(Artist artist) {
        em.persist(artist);
    }

    public Artist update(Artist artist) {
        return em.merge(artist);
    }

    public void delete(Long id) {

        Artist artist = em.find(Artist.class, id);

        if (artist == null) {
            return;
        }

        // удаляем связи треков с плейлистами
        em.createNativeQuery(
                        "DELETE FROM playlist_track " +
                                "WHERE track_id IN (" +
                                "SELECT track_id FROM track WHERE artist_id = ?1)"
                )
                .setParameter(1, id)
                .executeUpdate();

        // удаляем все треки исполнителя
        em.createQuery(
                        "DELETE FROM Track t WHERE t.artist.id = :artistId"
                )
                .setParameter("artistId", id)
                .executeUpdate();

        // удаляем самого исполнителя
        em.remove(artist);
    }
}
