package dev.twardosz;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shelter_id", nullable = false)
    private AnimalShelter shelter;

    @Column(nullable = false)
    private int rating;
    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private Date date;

    public Rating(AnimalShelter shelter, int rating, String comment, Date date) {
        this.shelter = shelter;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    public Rating() {
        this.shelter = null;
        this.rating = 0;
        this.comment = "";
        this.date = new Date();
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

    public Date getDate() {
        return date;
    }
}
