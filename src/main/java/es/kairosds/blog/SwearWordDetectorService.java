package es.kairosds.blog;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "swear", url = "http://localhost:3445/swear")
public interface SwearWordDetectorService {
	@GetMapping("/swear") 
	ResponseEntity<Boolean> hasSwearWords(String comment);
}


