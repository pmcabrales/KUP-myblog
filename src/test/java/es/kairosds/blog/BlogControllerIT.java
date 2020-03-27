package es.kairosds.blog;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import es.kairosds.security.SSLUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BlogControllerIT {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@WithMockUser(value = "admin")
	@Test
	public void contextLoads() throws JSONException {
		
		JSONArray response = this.restTemplate.getForObject("/articles", JSONArray.class);
		
		JSONAssert.assertEquals("[]", response, false);
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
