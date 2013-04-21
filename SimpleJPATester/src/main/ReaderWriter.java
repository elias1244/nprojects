/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import persistence.Movie;

/**
 *
 * @author Elias
 */
public class ReaderWriter {

    private static final String PERSISTENCE_UNIT_NAME = "SimpleJPATesterPU";

    private static EntityManagerFactory entityManagerFactory;

    public static void main(String... args) {

        //start reading
        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Query q = entityManager.createQuery("select m from Movie m");

        @SuppressWarnings("unchecked")
        List<Movie> movieList = q.getResultList();
        for (Movie movie : movieList) {
            System.out.println(movie.getMovieName());
        }
        System.out.println("Size: " + movieList.size());
        //end reading

        //start writing
        try {
            String movieName = read();

            //begin transaction
            entityManager.getTransaction().begin();
            Movie newFilm = new Movie();
            newFilm.setMovieName(movieName);
            entityManager.persist(newFilm);
            entityManager.getTransaction().commit();
        } catch (IOException e) {
            System.out.println("Fehler bei einlesen");
            e.printStackTrace();
        }
        //end writing

        entityManager.close();
    }

    private static String read() throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        System.out.print("Filmname: ");
        String eingabe = br.readLine();
        return eingabe;
    }
}
