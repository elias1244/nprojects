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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Elias
 */
@Entity
@Table(name = "genres")
@NamedQueries({
    @NamedQuery(name = "deleteAllGenres", query = "delete FROM Genre g"),
    @NamedQuery(name = "getAllGenres", query = "select g from Genre g")})
@SequenceGenerator(name = "genreSequence", initialValue = 1, allocationSize = 1)
public class Genre implements Serializable {

    /**
     * clients who want to excecute the deleteAllGenres query have to know name
     * of the query so this static constant has public accessablity.
     */
    public static final String QUERY_DELETE_ALL_NAME = "deleteAllGenres";

    /**
     * clients who want to excecute the getAllGenres query have to know name of
     * the query so this static constant has public accessablity.
     */
    public static final String QUERY_GET_ALL_NAME = "getAllGenres";

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;

    public Genre() {
    }

    public Genre(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof Genre)) {
            return false;
        }
        Genre other = (Genre) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistence.Genre[ id=" + id + " ]";
    }
}
