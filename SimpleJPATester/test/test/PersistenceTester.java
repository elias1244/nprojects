/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import persistence.Genre;
import persistence.Movie;

/**
 * A Unit test for tesing the persisting. To test it a PersistenceUnit has to be
 * defined in the persistence.xml. The description specifies the database which
 * should be used by the JPA.
 *
 * @author Noah Ispas
 *
 */
public class PersistenceTester {

    private final String PERSISTENCE_UNIT_NAME = "SimpleJPATesterPU";

    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    private static final String GENRE_ACTION_NAME = "Action";

    private Genre action = new Genre(GENRE_ACTION_NAME);

    private static final String GENRE_DRAMA_NAME = "Drama";

    private Genre drama = new Genre(GENRE_DRAMA_NAME);

    private static final String MOVIE_BADBOYS_NAME = "Bad Boys";

    private Movie badBoys = new Movie(MOVIE_BADBOYS_NAME, action);

    private static final String MOVIE_PIANIST_NAME = "Der Pianist";

    private Movie pianist = new Movie(MOVIE_PIANIST_NAME, drama);

    public PersistenceTester() {
        initialize();
    }

    /**
     * initializes jpa context, but does not create the tables. I think JPA
     * creates the table (if it not already exists) when persisting a entity.
     */
    public void initialize() {
        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    /**
     * Cleares the tables before running a test. There are cases in which the
     * tables were not created already at the executionTime of this method, but
     * i think jpa checks this, so we dont have to catch exeptions.
     *
     */
    @Before
    public void clearTables() {
        entityManager = entityManagerFactory.createEntityManager();
        Query clearingMoviesQuery = entityManager.createNamedQuery(Movie.QUERY_DELETE_ALL_NAME, Movie.class);
        Query clearingGenresQuery = entityManager.createNamedQuery(Genre.QUERY_DELETE_ALL_NAME, Genre.class);

        entityManager.getTransaction().begin();
        clearingMoviesQuery.executeUpdate();
        clearingGenresQuery.executeUpdate();
        entityManager.getTransaction().commit();

        //TODO sequence auch clearen
    }

    @After
    public void TearDown() {
        entityManager.close();
    }

    @Test
    public void testGenrePersisting() {
        persistGenre(action);

        List allGenres = getAllGenres();
        Assert.assertNotNull(allGenres);
        Assert.assertTrue(allGenres.size() == 1);

        Genre actionFromDB = (Genre) allGenres.get(0);
        Assert.assertEquals(action.getDescription(), actionFromDB.getDescription());
    }

    @Test
    public void testGenreDeleting() {
        persistGenre(action);
        deleteGenre(action);

        List allGenres = getAllGenres();
        Assert.assertNotNull(allGenres);
        Assert.assertTrue(allGenres.isEmpty());
    }

    @Test
    public void testMovieInsertion() {
        persistGenre(action);
        persistMovie(badBoys);

        List allMovies = getAllMovies();
        Assert.assertNotNull(allMovies);
        Assert.assertTrue(allMovies.size() == 1);

    }

    @Test
    public void testMovieDeleting() {
    }

    /**
     * @param genreToPersist
     */
    private void persistGenre(Genre genreToPersist) {
        entityManager.getTransaction().begin();
        entityManager.persist(genreToPersist);
        entityManager.getTransaction().commit();
    }

    /**
     * @return
     */
    private List getAllGenres() {
        Query getAllQuery = entityManager.createNamedQuery(Genre.QUERY_GET_ALL_NAME, Genre.class);
        return getAllQuery.getResultList();
    }

    /**
     * @param genreToDelete
     */
    private void deleteGenre(Genre genreToDelete) {
        entityManager.getTransaction().begin();
        entityManager.remove(genreToDelete);
        entityManager.getTransaction().commit();
    }

    private void persistMovie(Movie movieToPersist) {
        entityManager.getTransaction().begin();
        entityManager.persist(movieToPersist);
        entityManager.getTransaction().commit();
    }

    private List getAllMovies() {
        Query getAllQuery = entityManager.createNamedQuery(Movie.QUERY_GET_ALL_NAME);
        return getAllQuery.getResultList();
    }
}
