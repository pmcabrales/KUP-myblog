package es.kairosds;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MyblogApplicationTests {

	@Test
	public void main() {
		MyblogApplication.main(new String[] {});
	}

}
