package dev.twardosz;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Rating implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private AnimalShelter shelter;

    @Column(nullable = false)
    private int rating;
    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private LocalDateTime date;

    public Rating(AnimalShelter shelter, int rating, String comment, LocalDateTime date) {
        this.shelter = shelter;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public Rating() {
        this.shelter = null;
        this.rating = 0;
        this.comment = "";
        this.date = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public AnimalShelter getShelter() {
        return shelter;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
