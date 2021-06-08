package es.uniovi.eii.paquetor.services;

import es.uniovi.eii.paquetor.entities.users.BaseUser;
import es.uniovi.eii.paquetor.repositories.users.CustomerUsersRepository;
import es.uniovi.eii.paquetor.repositories.users.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    CustomerUsersRepository customersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        BaseUser baseUser = customersRepository.findByEmail(email);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(baseUser.getRole()));

        return new User(baseUser.getEmail(), baseUser.getPassword(), grantedAuthorities);
    }
}