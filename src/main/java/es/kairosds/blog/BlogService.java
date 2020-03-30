package es.kairosds.blog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogService {

	@Autowired
	private EntryRepository entryRepository;

	Entry createComment(long id, Comment comment) {

		Entry entry = entryRepository.findById(id).get();

		List<Comment> comments = entry.getComments();
		comments.add(comment);
		entry.setComments(comments);
		return entryRepository.save(entry);
	}

	Entry saveEntry(Entry entry) {
		return entryRepository.save(entry);

	}

	List<Entry> findAllEntries() {
		return entryRepository.findAll();

	}

	Entry findEntryById(long id) {
		return entryRepository.findById(id).get();

	}

}
