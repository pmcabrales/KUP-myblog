package es.kairosds.blog;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@WebMvcTest(BlogService.class)
class BlogServiceTest {
	
	@MockBean
	private EntryRepository entryRepository;
	
	@InjectMocks
	private BlogService blogService;
	
	@Test
	public void findAllEntries() throws Exception {
		
		Entry mockEntry = new Entry("Luis Corral", "jlcorral", "Los austrias", "El vuelo del buho", "La gran saga sobre la forja de un imperio");
		List<Entry> mockEntries = Arrays.asList(mockEntry);
	
		Mockito.when(entryRepository.findAll()).thenReturn(mockEntries);
		
		MockitoAnnotations.initMocks(this);
		
		
		List<Entry> entries = blogService.findAllEntries();
		Entry entry = entries.get(0);
		
		assertThat(entries.size(), is(1));
		assertThat(entry.getId(), is(0L));
        assertThat(entry.getAuthorName(), is("Luis Corral"));
        assertThat(entry.getAuthorNick(), is("jlcorral"));
        assertThat(entry.getTitle(), is("Los austrias"));
        assertThat(entry.getSummary(), is("El vuelo del buho"));
        assertThat(entry.getText(), is("La gran saga sobre la forja de un imperio"));
	
	}
	
	@Test
	public void findEntryById() throws Exception {
		
		Entry mockEntry = new Entry("Luis Corral", "jlcorral", "Los austrias", "El vuelo del buho", "La gran saga sobre la forja de un imperio");
		
		Mockito.when(entryRepository.findById(0L)).thenReturn(Optional.of(mockEntry));
		
		MockitoAnnotations.initMocks(this);
		
		
		Entry entry = blogService.findEntryById(0L);
		
		assertThat(entry.getId(), is(0L));
        assertThat(entry.getAuthorName(), is("Luis Corral"));
        assertThat(entry.getAuthorNick(), is("jlcorral"));
        assertThat(entry.getTitle(), is("Los austrias"));
        assertThat(entry.getSummary(), is("El vuelo del buho"));
        assertThat(entry.getText(), is("La gran saga sobre la forja de un imperio"));
	
	}
	
	
	@Test
	public void saveEntry() throws Exception {
		
		Entry mockEntry = new Entry("Luis Corral", "jlcorral", "Los austrias", "El vuelo del buho", "La gran saga sobre la forja de un imperio");
		
		Mockito.when(entryRepository.save(mockEntry)).thenReturn(mockEntry);
		
		MockitoAnnotations.initMocks(this);
		
		
		Entry entry = blogService.saveEntry(mockEntry);
		
		assertThat(entry.getId(), is(0L));
        assertThat(entry.getAuthorName(), is("Luis Corral"));
        assertThat(entry.getAuthorNick(), is("jlcorral"));
        assertThat(entry.getTitle(), is("Los austrias"));
        assertThat(entry.getSummary(), is("El vuelo del buho"));
        assertThat(entry.getText(), is("La gran saga sobre la forja de un imperio"));
	
	}
	
	
	@Test
	public void createComment() throws Exception {
		
		Entry mockEntry = new Entry("Luis Corral", "jlcorral", "Los austrias", "El vuelo del buho", "La gran saga sobre la forja de un imperio");
		Comment mockComment = new Comment("Juan", "My comment");
		mockEntry.setComments(new ArrayList<Comment>());
		Mockito.when(entryRepository.findById(0L)).thenReturn(Optional.of(mockEntry));
		
		Mockito.when(entryRepository.save(mockEntry)).thenReturn(mockEntry);
		
		MockitoAnnotations.initMocks(this);
		
		Entry entry = blogService.createComment(0L, mockComment);
		
		assertThat(entry.getId(), is(0L));
        assertThat(entry.getAuthorName(), is("Luis Corral"));
        assertThat(entry.getAuthorNick(), is("jlcorral"));
        assertThat(entry.getTitle(), is("Los austrias"));
        assertThat(entry.getSummary(), is("El vuelo del buho"));
        assertThat(entry.getText(), is("La gran saga sobre la forja de un imperio"));
        assertThat(entry.getComments().size(), is(1));
        assertThat(entry.getComments().get(0).getAuthor(), is("Juan"));
        assertThat(entry.getComments().get(0).getMessage(), is("My comment"));
	}
	
}
