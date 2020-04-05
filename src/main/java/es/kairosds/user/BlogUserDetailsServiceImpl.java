package es.kairosds.user;

import static java.util.Collections.emptyList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BlogUserDetailsServiceImpl implements UserDetailsService {

	private BlogUserRepository userRepository;

	public BlogUserDetailsServiceImpl(BlogUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		BlogUser blogUser = userRepository.findByUsername(userName);
		if (blogUser == null) {
			throw new UsernameNotFoundException(userName);
		}
		return new User(blogUser.getUsername(), blogUser.getPassword(), emptyList());
	}
}
