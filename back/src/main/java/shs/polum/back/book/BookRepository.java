package shs.polum.back.book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByLibraryId(Integer libraryId);
    List<Book> findByUserId(Integer userId);
}