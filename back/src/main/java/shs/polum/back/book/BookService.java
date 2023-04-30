package shs.polum.back.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shs.polum.back.exception.ResourceNotFoundException;
import shs.polum.back.exception.ValidationException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    public final BookRepository bookRepository;

    public List<Book> getAllBooksByLibrary(Integer libraryId) {
        return bookRepository.findByLibraryId(libraryId);
    }

    public List<Book> getAllBooksByUser(Integer userId) {
        return bookRepository.findByUserId(userId);
    }

    public void createBook(Book book) throws ValidationException {
        if (book.getTitle() == null) {
            throw new ValidationException("Title is required");
        } else if (book.getTitle().length() >= 255) {
            throw new ValidationException("Title must be less than 255 characters");
        }

        if (book.getAuthor() == null) {
            throw new ValidationException("Author is required");
        } else if (book.getAuthor().length() >= 150) {
            throw new ValidationException("Author must be less than 150 characters");
        }

        if (book.getGenre() == null) {
            throw new ValidationException("Genre is required");
        } else if (book.getGenre().length() >= 50) {
            throw new ValidationException("Genre must be less than 50 characters");
        }

        if (book.getPages() == null) {
            throw new ValidationException("Pages is required");
        } else if (book.getPages() >= 9999) {
            throw new ValidationException("Pages must be less than 9999");
        }

        if (book.getDaysToRead() == null) {
            throw new ValidationException("Days to read is required");
        } else if (book.getDaysToRead() >= 999) {
            throw new ValidationException("Days to read must be less than 999");
        }

        bookRepository.save(book);
    }

    public void updateBook(Book book, Integer id) throws ResourceNotFoundException, ValidationException {
        if (bookRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Book not found");
        }
        Book originalBook = bookRepository.findById(id).get();

        if (book.getTitle() != null && book.getTitle().length() >= 255) {
            throw new ValidationException("Title must be less than 255 characters");
        } else if (book.getTitle() != null)
            originalBook.setTitle(book.getTitle());

        if (book.getAuthor() != null && book.getAuthor().length() >= 150) {
            throw new ValidationException("Author must be less than 150 characters");
        } else if (book.getAuthor() != null)
            originalBook.setAuthor(book.getAuthor());

        if (book.getGenre() != null && book.getGenre().length() >= 50) {
            throw new ValidationException("Genre must be less than 50 characters");
        } else if (book.getGenre() != null)
            originalBook.setGenre(book.getGenre());

        if (book.getPages() != null && book.getPages() >= 9999) {
            throw new ValidationException("Pages must be less than 9999");
        } else if (book.getPages() != null)
            originalBook.setPages(book.getPages());

        if (book.getDaysToRead() != null && book.getDaysToRead() >= 999) {
            throw new ValidationException("Days to read must be less than 999");
        } else if (book.getDaysToRead() != null)
            originalBook.setDaysToRead(book.getDaysToRead());

        bookRepository.save(originalBook);
    }

    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }
}