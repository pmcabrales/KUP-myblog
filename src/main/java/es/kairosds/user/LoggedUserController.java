package es.kairosds.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggedUserController {

	@Autowired
	private LoggedUserService loggedUserService;

	@GetMapping("/users/")
	public List<LoggedUser> getAllUsers() {
		return loggedUserService.getAllUsers();
	}

	@GetMapping("/users/{username}")
	public LoggedUser getUser(@PathVariable String username) {
		return loggedUserService.getUserByUserName(username);
	}

	@PostMapping("/users/")
	public void saveUser(@RequestBody LoggedUser user) {
		loggedUserService.saveUser(user);
	}
}
