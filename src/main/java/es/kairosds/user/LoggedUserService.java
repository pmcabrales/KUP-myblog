package es.kairosds.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class LoggedUserService {

	@Autowired
	private LoggedUserRepository userRepository;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public List<LoggedUser> getAllUsers() {
		return userRepository.findAll();
	}

	public LoggedUser getUserByUserName(@PathVariable String username) {
		return userRepository.findByUsername(username);
	}

	public void saveUser(@RequestBody LoggedUser user) {
		bCryptPasswordEncoder = new BCryptPasswordEncoder();
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
}
