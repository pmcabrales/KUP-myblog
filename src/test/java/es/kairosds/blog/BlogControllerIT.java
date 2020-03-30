package es.kairosds.blog;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
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
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import es.kairosds.security.SSLUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BlogControllerIT {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@MockBean
	private SwearWordDetectorService swearWordDetectorService;
	
	@MockBean
	private BlogService blogService;
	
	@Autowired
    private WebApplicationContext wac;
	
	@Autowired
    private FilterChainProxy springSecurityFilterChain;
	
	private MockMvc mockMvc;
	 
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
          .addFilter(springSecurityFilterChain).build();
    }
	    

	@Test
	public void getArticles() throws Exception {
		
		String accessToken = "eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE1ODUxMzA1NzQsImlzcyI6Imh0dHBzOi8vd3d3LmthaXJvc2RzLmNvbS8iLCJzdWIiOiJhZG1pbiIsImV4cCI6MTU4NTk5NDU3NH0.BrWvN-Xxa6H5tDnugrU9t4bbSylpCfMnn_igp97h1WEZNH4bANRlH_6N0P0SoPt_Gcha15TabI3XbFxPj16HLQ";
	   
		mockMvc.perform(get("/articles")
	      .header("Authorization", "Bearer " + accessToken))
	      .andExpect(status().isOk());
		
	}
	
	
	@Test
	public void getArticle() throws Exception {
		
		String accessToken = "eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE1ODUxMzA1NzQsImlzcyI6Imh0dHBzOi8vd3d3LmthaXJvc2RzLmNvbS8iLCJzdWIiOiJhZG1pbiIsImV4cCI6MTU4NTk5NDU3NH0.BrWvN-Xxa6H5tDnugrU9t4bbSylpCfMnn_igp97h1WEZNH4bANRlH_6N0P0SoPt_Gcha15TabI3XbFxPj16HLQ";
		   
		mockMvc.perform(get("/articles/0")
	      .header("Authorization", "Bearer " + accessToken))
	      .andExpect(status().isOk());
	}
	
	
	@Test
	public void postArticle() throws Exception {
	
		String postBody = "{\"authorName\": \"Test2\",\"authorNick\": \"Test2\",\"title\": \"Test2\",\"summary\": \"Test2\",\"text\": \"Test2\"}";
		
		String accessToken = "eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE1ODUxMzA1NzQsImlzcyI6Imh0dHBzOi8vd3d3LmthaXJvc2RzLmNvbS8iLCJzdWIiOiJhZG1pbiIsImV4cCI6MTU4NTk5NDU3NH0.BrWvN-Xxa6H5tDnugrU9t4bbSylpCfMnn_igp97h1WEZNH4bANRlH_6N0P0SoPt_Gcha15TabI3XbFxPj16HLQ";
		   
		mockMvc.perform(post("/articles", postBody)
	      .header("Authorization", "Bearer " + accessToken))
	      .andExpect(status().isOk());
	}
	
	@Test
	public void postComment() throws JSONException {

		Entry mockEntry = new Entry("Luis Corral", "jlcorral", "Los austrias", "El vuelo del buho", "La gran saga sobre la forja de un imperio");
		
		Comment mockComment = new Comment();
		mockComment.setAuthor("Yo mismo");
		mockComment.setMessage("El tigre comía trigo en el trigal");
	
		Mockito.when(blogService.createComment(0, mockComment)).thenReturn(mockEntry);	
		Mockito.when(swearWordDetectorService.hasSwearWords("El tigre comía trigo en el trigal")).thenReturn(new ResponseEntity<>(false, HttpStatus.ACCEPTED));
		MockitoAnnotations.initMocks(this);
		
		
		final ResponseEntity<Entry> response = this.restTemplate
				.postForEntity("/articles/0/comment", mockComment, Entry.class);
		
		assertThat(response.getStatusCode(), is(HttpStatus.ACCEPTED));
		//assertThat(response.getBody(), is(mockEntry));
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
