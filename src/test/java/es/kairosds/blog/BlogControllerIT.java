package es.kairosds.blog;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import es.kairosds.security.SSLUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BlogControllerIT {
	
	@MockBean
	private SwearWordDetectorService swearWordDetectorService;
	
	@MockBean
	private BlogService blogService;
	
	@Autowired
    private WebApplicationContext wac;
	
	@Autowired
    private FilterChainProxy springSecurityFilterChain;
	
	private MockMvc mockMvc;
	
	private String accessToken;
	 
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
          .addFilter(springSecurityFilterChain).build();
        
        accessToken = "eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE1ODUxMzA1NzQsImlzcyI6Imh0dHBzOi8vd3d3LmthaXJvc2RzLmNvbS8iLCJzdWIiOiJhZG1pbiIsImV4cCI6MTU4NTk5NDU3NH0.BrWvN-Xxa6H5tDnugrU9t4bbSylpCfMnn_igp97h1WEZNH4bANRlH_6N0P0SoPt_Gcha15TabI3XbFxPj16HLQ";
 	   
    }
	    

	@Test
	public void getArticles() throws Exception {
		
		Entry mockEntry = new Entry("Luis Corral", "jlcorral", "Los austrias", "El vuelo del buho", "La gran saga sobre la forja de un imperio");
		List<Entry> mockEntries = Arrays.asList(mockEntry);
		Mockito.when(blogService.findAllEntries()).thenReturn(mockEntries);
		MockitoAnnotations.initMocks(this);
		
		MvcResult result =mockMvc.perform(get("/articles")
	      .header("Authorization", "Bearer " + accessToken))
	      .andExpect(status().isOk()).andReturn();
		
		String content = result.getResponse().getContentAsString();
		assertThat(content, is("[{\"id\":0,\"authorName\":\"Luis Corral\",\"authorNick\":\"jlcorral\",\"title\":\"Los austrias\",\"summary\":\"El vuelo del buho\",\"text\":\"La gran saga sobre la forja de un imperio\",\"comments\":null}]"));
	
	}
	
	
	@Test
	public void getArticle() throws Exception {
		   
		Entry mockEntry = new Entry("Luis Corral", "jlcorral", "Los austrias", "El vuelo del buho", "La gran saga sobre la forja de un imperio");
		Mockito.when(blogService.findEntryById(0L)).thenReturn(mockEntry);
		MockitoAnnotations.initMocks(this);
		
		MvcResult result =mockMvc.perform(get("/articles/0")
	      .header("Authorization", "Bearer " + accessToken))
	      .andExpect(status().isOk()).andReturn();
		
		String content = result.getResponse().getContentAsString();
		assertThat(content, is("{\"id\":0,\"authorName\":\"Luis Corral\",\"authorNick\":\"jlcorral\",\"title\":\"Los austrias\",\"summary\":\"El vuelo del buho\",\"text\":\"La gran saga sobre la forja de un imperio\",\"comments\":null}"));
	
	}
	
	@Test
	public void postArticle() throws Exception {
	
		String postBody = "{\"authorName\":\"Luis Corral\",\"authorNick\":\"jlcorral\",\"title\":\"Los austrias\",\"summary\":\"El vuelo del buho\",\"text\":\"La gran saga sobre la forja de un imperio\"}";
		Entry mockEntry = new Entry("Luis Corral", "jlcorral", "Los austrias", "El vuelo del buho", "La gran saga sobre la forja de un imperio");
		Mockito.when(blogService.saveEntry(mockEntry)).thenReturn(mockEntry);
		MockitoAnnotations.initMocks(this);
		
		mockMvc.perform(post("/articles")
			.header("Authorization", "Bearer " + accessToken)
			.contentType(MediaType.APPLICATION_JSON)
			.content(postBody))
			.andExpect(status().isOk()).andReturn();
	
	}
	
	@Test
	public void postComment() throws Exception {

		String postBody = "{\"author\": \"Yo mismo\",\"message\": \"El tigre comía trigo en el trigal\"}";
		
		Comment mockComment = new Comment();
		mockComment.setAuthor("Yo mismo");
		mockComment.setMessage("El tigre comía trigo en el trigal");
		
		Mockito.when(swearWordDetectorService.hasSwearWords("El tigre comía trigo en el trigal")).thenReturn(new ResponseEntity<>(false, HttpStatus.ACCEPTED));
		MockitoAnnotations.initMocks(this);
		
		mockMvc.perform(post("/articles/0/comment", mockComment)
			    .header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(postBody))
			    .andExpect(status().isAccepted()).andReturn();

	}
	
	
	@Before
	public void turnOffSslChecking() throws KeyManagementException, NoSuchAlgorithmException {
		SSLUtil.turnOffSslChecking();
	}
	
	@After
	public void turnOnSslChecking() throws KeyManagementException, NoSuchAlgorithmException {
		SSLUtil.turnOnSslChecking();
	}

}
