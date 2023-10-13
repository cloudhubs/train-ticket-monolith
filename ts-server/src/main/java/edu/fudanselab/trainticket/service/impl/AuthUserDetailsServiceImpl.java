package edu.fudanselab.trainticket.service.impl;

import edu.fudanselab.trainticket.constant.InfoConstant;
import edu.fudanselab.trainticket.repository.AuthUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * @author fdse
 */
@Component("userDetailServiceImpl")
public class AuthUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AuthUserRepository authUserRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthUserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        LOGGER.info("[loadUserByUsername][load UsernamePasswordAuthenticationToken][username: {}]", s);
        return authUserRepository.findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException(
                        MessageFormat.format(InfoConstant.USER_NAME_NOT_FOUND_1, s)
                ));
    }
}
