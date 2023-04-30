package shs.polum.back.book;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shs.polum.back.library.Library;
import shs.polum.back.user.User;

@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "author", nullable = false, length = 150)
    private String author;
    @Column(name = "genre", nullable = false, length = 50)
    private String genre;
    @Column(name = "pages", nullable = false, length = 4)
    private Integer pages;
    @Column(name = "days_to_read", nullable = false, length = 3)
    private Integer daysToRead;
    @ManyToOne
    @JoinColumn(name = "library_id", nullable = false)
    private Library library;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}