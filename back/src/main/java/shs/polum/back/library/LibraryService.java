package shs.polum.back.library;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shs.polum.back.exception.ResourceNotFoundException;
import shs.polum.back.exception.ValidationException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryService {
    public final LibraryRepository libraryRepository;

    public List<Library> getAllLibrariesByUser(Integer userId) {
        return libraryRepository.findByUserId(userId);
    }

    public void createLibrary(Library library) throws ValidationException {
        if (library.getName() == null) {
            throw new ValidationException("Name is required");
        } else if (library.getName().length() >= 50) {
            throw new ValidationException("Name must be less than 50 characters");
        }

        libraryRepository.save(library);
    }

    public void updateLibrary(Library library, Integer id) throws ResourceNotFoundException, ValidationException {
        if (libraryRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Library not found");
        }
        Library originalLibrary = libraryRepository.findById(id).get();

        if (library.getName() != null && library.getName().length() >= 50) {
            throw new ValidationException("Name must be less than 50 characters");
        } else if (library.getName() != null)
            originalLibrary.setName(library.getName());

        libraryRepository.save(originalLibrary);
    }

    public void deleteLibrary(Integer id) {
        libraryRepository.deleteById(id);
    }
}