package es.kairosds.blog;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import es.kairosds.security.SSLUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BlogControllerIT {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@MockBean
	private BlogService blogService;
	
	@WithMockUser(value = "admin")
	@Test
	public void getArticles() throws JSONException {
		
		final ResponseEntity<JSONArray> response = this.restTemplate
				.getForEntity("/articles", JSONArray.class);
		
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody(), is("[]"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	public void getArticle() throws JSONException {
		
		final ResponseEntity<JSONArray> response = this.restTemplate
				.getForEntity("/articles/0", JSONArray.class);
		
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody(), is("[]"));
	}
	
	
	@WithMockUser(username  = "admin", password = "password")
	@Test
	public void postArticle() throws JSONException {
	
		String postBody = "{\"authorName\": \"Test2\",\"authorNick\": \"Test2\",\"title\": \"Test2\",\"summary\": \"Test2\",\"text\": \"Test2\"}";
		
		final ResponseEntity<JSONArray> response = this.restTemplate
				//.headForHeaders("/articles", {})
				.postForEntity("/articles", postBody, JSONArray.class);
		
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody(), is(postBody));
	}
	
	@Test
	public void postComment() throws JSONException {
	
		Entry mockEntry = new Entry("Luis Corral", "jlcorral", "Los austrias", "El vuelo del buho", "La gran saga sobre la forja de un imperio");
		Mockito.when(blogService.findEntryById(0L)).thenReturn(mockEntry);
		MockitoAnnotations.initMocks(this);
		
		String postBody = "{\"author\": \"Yo mismo\",\"message\": \"El tigre comía trigo en el trigal\"}";
		final ResponseEntity<Entry> response = this.restTemplate
				.postForEntity("/articles/0/comment", postBody, Entry.class);
		
		assertThat(response.getStatusCode(), is(HttpStatus.ACCEPTED));
		assertThat(response.getBody(), is(postBody));
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
