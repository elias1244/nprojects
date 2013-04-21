/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Elias
 */
@Entity
@Table(name = "movies")
@NamedQueries({
    @NamedQuery(name = "deleteAllMovies", query = "delete FROM Movie m"),
    @NamedQuery(name = "getAllMovies", query = "select m from Movie m")})
@SequenceGenerator(name = "movieSequence", initialValue = 1, allocationSize = 1)
public class Movie implements Serializable {

    /**
     * clients who want to excecute the deleteAllMovies query have to know name
     * of the query so this static constant has public accessablity.
     */
    public static final String QUERY_DELETE_ALL_NAME = "deleteAllMovies";

    /**
     * clients who want to excecute the getAllGenres query have to know name of
     * the query so this static constant has public accessablity.
     */
    public static final String QUERY_GET_ALL_NAME = "getAllMovies";

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "movieSequence")
    private Long id;

    private String movieName;

    @ManyToOne
    private Genre genre;

    public Movie() {
    }

    public Movie(String movieName, Genre genre) {
        this.movieName = movieName;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the movieName
     */
    public String getMovieName() {
        return movieName;
    }

    /**
     * @param movieName the movieName to set
     */
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    /**
     * TODO checken ob wir das brauchen
     *
     * @return the genre
     */
    public Genre getGenre() {
        return genre;
    }

    /**
     * TODO checken ob wir das brauchen
     *
     * @param genre the genre to set
     */
    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movie)) {
            return false;
        }
        Movie other = (Movie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistence.Movie[ id=" + id + " ]";
    }
}
