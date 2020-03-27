package es.kairosds.blog;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(BlogController.class)
@EnableAutoConfiguration(exclude = UserDetailsServiceAutoConfiguration.class)
class BlogControllerTests {

	@InjectMocks
	private BlogController blogControllerSUT;
	
	@MockBean
	private EntryRepository entryRepository;
	
	@MockBean
	private SwearWordDetectorService swearWordDetectorService;
	
	@Mock
	private Entry entryTD;
	
	@Test
	public void getEntries_basic() throws Exception {
		
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
	public void getEntry_basic() throws Exception {
		
		Entry entry = blogControllerSUT.getEntry(1L);
		
		assertThat(entry.getId(), is(1L));
        assertThat(entry.getAuthorName(), is("Luis Corral"));
        assertThat(entry.getAuthorNick(), is("jlcorral"));
        assertThat(entry.getTitle(), is("Los austrias"));
        assertThat(entry.getSummary(), is("El vuelo del buho"));
        assertThat(entry.getText(), is("La gran saga sobre la forja de un imperio"));
        assertThat(entry.getComments(), is(null));
	
	}

}

/*
@RunWith(SpringRunner.class)
@WebMvcTest(BlogController.class)
class BlogControllerTests {

	@MockBean
	private EntryRepository entryRepository;
	
	@MockBean
	private SwearWordDetectorService swearWordDetectorService;
	
	private BlogController blogControllerSUT;
	private Entry entryTD;

	@Before
    public void init() {
		blogControllerSUT = new BlogController();
		entryTD = mock(Entry.class);
    }
	
	@Test
	public void getEntries_basic() throws Exception {
		
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
	public void getEntry_basic() throws Exception {
		
		Entry entry = blogControllerSUT.getEntry(1L);
		
		assertThat(entry.getId(), is(1L));
        assertThat(entry.getAuthorName(), is("Luis Corral"));
        assertThat(entry.getAuthorNick(), is("jlcorral"));
        assertThat(entry.getTitle(), is("Los austrias"));
        assertThat(entry.getSummary(), is("El vuelo del buho"));
        assertThat(entry.getText(), is("La gran saga sobre la forja de un imperio"));
        assertThat(entry.getComments(), is(null));
	
	}

}
*/