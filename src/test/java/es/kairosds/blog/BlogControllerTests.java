package es.kairosds.blog;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(BlogController.class)
class BlogControllerTests {

	private BlogController blogControllerSUT;
	
	@MockBean
	private EntryRepository entryRepository;
	
	@MockBean
	private SwearWordDetectorService swearWordDetectorService;
	
	@Mock
	private Entry entryTD;
	
	@Before
    public void init() {
		entryTD = new Entry("Luis Corral", "jlcorral", "Los austrias", "El vuelo del buho", "La gran saga sobre la forja de un imperio");
		List<Entry> entries = Arrays.asList(entryTD);
		Mockito.when(entryRepository.findAll()).thenReturn(entries);
		
		blogControllerSUT = new BlogController();
	}
	
	@Test
	public void getEntries() throws Exception {
	
		List<Entry> entries = blogControllerSUT.getEntries();
		Entry entry = entries.get(0);
		
		assertThat(entries.size(), is(1L));
		assertThat(entry.getId(), is(1L));
        assertThat(entry.getAuthorName(), is("Luis Corral"));
        assertThat(entry.getAuthorNick(), is("jlcorral"));
        assertThat(entry.getTitle(), is("Los austrias"));
        assertThat(entry.getSummary(), is("El vuelo del buho"));
        assertThat(entry.getText(), is("La gran saga sobre la forja de un imperio"));
        assertThat(entry.getComments(), is(null));
	
	}
	
	@Test
	public void getEntry() throws Exception {
		
		Entry entry = blogControllerSUT.getEntry(1L);
		
		assertThat(entry.getId(), is(1L));
        assertThat(entry.getAuthorName(), is("Luis Corral"));
        assertThat(entry.getAuthorNick(), is("jlcorral"));
        assertThat(entry.getTitle(), is("Los austrias"));
        assertThat(entry.getSummary(), is("El vuelo del buho"));
        assertThat(entry.getText(), is("La gran saga sobre la forja de un imperio"));
        assertThat(entry.getComments(), is(null));
	
	}
	
	@Test
	public void getEntry_doesnt_exists() throws Exception {
		
		Entry entry = blogControllerSUT.getEntry(3L);
		
        assertThat(entry, is(null));	
	}

}
