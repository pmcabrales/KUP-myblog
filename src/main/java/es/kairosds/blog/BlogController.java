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
	private EntryRepository entryRepository;

	@Autowired
	private SwearWordDetectorService swearWordDetectorService;

	@GetMapping("")
	List<Entry> getEntries() {
		
		return entryRepository.findAll();
		
	}

	@GetMapping("/{id}")
	Entry getEntry(@PathVariable long id) {

		return entryRepository.findById(id).get();

	}
	
	@PostMapping("")
	Entry createEntry(@RequestBody Entry entry) {

		return entryRepository.save(entry);
	
	}
	
	@PostMapping("/{id}/comment")
	ResponseEntity<Entry> createComment(@PathVariable long id, @RequestBody Comment comment) {

		Entry entry = entryRepository.findById(id).get();
		
		try {
			swearWordDetectorService.hasSwearWords(comment.getMessage());
		}catch(Exception ex) {
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
		
		List<Comment> comments = entry.getComments();
		comments.add(comment);
		entry.setComments(comments);
		entry = entryRepository.save(entry);
		return new ResponseEntity<>(entry,HttpStatus.BAD_REQUEST);
	
	}


}
