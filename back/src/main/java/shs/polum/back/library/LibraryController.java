package shs.polum.back.library;

        import lombok.RequiredArgsConstructor;
        import org.springframework.web.bind.annotation.*;
        import shs.polum.back.exception.ResourceNotFoundException;
        import shs.polum.back.exception.ValidationException;

        import java.util.List;

@RestController
@RequestMapping("/api/libraries")
@RequiredArgsConstructor
public class LibraryController {
    public final LibraryService libraryService;
    @GetMapping("/{userId}")
    public List<Library> getAllLibrariesByUser(@PathVariable("userId") Integer userId) {
        return libraryService.getAllLibrariesByUser(userId);
    }
    @PostMapping
    public void createLibrary(@RequestBody Library library) throws ValidationException {
        libraryService.createLibrary(library);
    }
    @PutMapping("/{id}")
    public void updateLibrary(@RequestBody Library library, @PathVariable("id") Integer id) throws ValidationException, ResourceNotFoundException {
        libraryService.updateLibrary(library, id);
    }
    @DeleteMapping("/{id}")
    public void deleteLibrary(@PathVariable("id") Integer id) {
        libraryService.deleteLibrary(id);
    }
}