package es.kairosds.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogUserController {

	@Autowired
	private BlogUserService BlogUserService;

	@GetMapping("/users/")
	public List<BlogUser> getAllUsers() {
		return BlogUserService.getAllUsers();
	}

	@GetMapping("/users/{username}")
	public BlogUser getUser(@PathVariable String username) {
		return BlogUserService.getUserByUserName(username);
	}

	@PostMapping("/users/")
	public void saveUser(@RequestBody BlogUser user) {
		BlogUserService.saveUser(user);
	}
}
