package es.kairosds.user;

import static java.util.Collections.emptyList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoggedUserDetailsServiceImpl implements UserDetailsService {

	private LoggedUserRepository userRepository;

	public LoggedUserDetailsServiceImpl(LoggedUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		LoggedUser loggedUser = userRepository.findByUsername(userName);
		if (loggedUser == null) {
			throw new UsernameNotFoundException(userName);
		}
		return new User(loggedUser.getUsername(), loggedUser.getPassword(), emptyList());
	}
}
