package es.kairosds.blog;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "swear", url = "http://localhost:8445")
public interface SwearWordDetectorService {

	@PostMapping("/swear")
	ResponseEntity<Boolean> hasSwearWords(String comment);

}
