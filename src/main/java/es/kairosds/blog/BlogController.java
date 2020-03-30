package es.kairosds.blog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
public class BlogController {

	@Autowired
	private BlogService blogService;

	@Autowired
	private SwearWordDetectorService swearWordDetectorService;

	@PostMapping("/{id}/comment")
	ResponseEntity<Entry> createComment(@PathVariable long id, @RequestBody Comment comment) {

		try {
			swearWordDetectorService.hasSwearWords(comment.getMessage());
		} catch (Exception ex) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		Entry entry = blogService.createComment(id, comment);
		return new ResponseEntity<>(entry, HttpStatus.ACCEPTED);

	}

	@PostMapping("")
	Entry createEntry(@RequestBody Entry entry) {

		return blogService.saveEntry(entry);

	}

	@GetMapping("")
	List<Entry> getEntries() {

		return blogService.findAllEntries();

	}

	@GetMapping("/{id}")
	Entry getEntry(@PathVariable long id) {

		return blogService.findEntryById(id);

	}

}
