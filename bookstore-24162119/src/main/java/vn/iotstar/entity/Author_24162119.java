package vn.iotstar.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "author")
public class Author_24162119 {

    @Id
    @GeneratedValue(
            strategy =
                    GenerationType.IDENTITY
    )
    @Column(name = "author_id")
    private Integer authorId;

    @Column(
            name = "author_name",
            length = 100
    )
    private String authorName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    public Author_24162119() {
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(
            Integer authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(
            String authorName) {
        this.authorName =
                authorName;
    }

    public LocalDate
            getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(
            LocalDate dateOfBirth) {
        this.dateOfBirth =
                dateOfBirth;
    }
}