package es.kairosds.blog;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.base.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(BlogController.class)
class BlogControllerTest {

	@InjectMocks
	private BlogController blogControllerSUT;
	
	@MockBean
	private BlogService blogService;
	
	@MockBean
	private SwearWordDetectorService swearWordDetectorService;
	

	@Test
	public void getEntries() throws Exception {
		
		Entry mockEntry = new Entry("Luis Corral", "jlcorral", "Los austrias", "El vuelo del buho", "La gran saga sobre la forja de un imperio");
		List<Entry> mockEntries = Arrays.asList(mockEntry);
		Mockito.when(blogService.findAllEntries()).thenReturn(mockEntries);
		MockitoAnnotations.initMocks(this);
		
		List<Entry> entries = blogControllerSUT.getEntries();
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
	public void getEntry() throws Exception {
		
		Entry mockEntry = new Entry("Luis Corral", "jlcorral", "Los austrias", "El vuelo del buho", "La gran saga sobre la forja de un imperio");
		Mockito.when(blogService.findEntryById(0L)).thenReturn(mockEntry);
		MockitoAnnotations.initMocks(this);
		
		
		Entry entry = blogControllerSUT.getEntry(0L);
		
		assertThat(entry.getId(), is(0L));
        assertThat(entry.getAuthorName(), is("Luis Corral"));
        assertThat(entry.getAuthorNick(), is("jlcorral"));
        assertThat(entry.getTitle(), is("Los austrias"));
        assertThat(entry.getSummary(), is("El vuelo del buho"));
        assertThat(entry.getText(), is("La gran saga sobre la forja de un imperio"));
	
	}
	

}
