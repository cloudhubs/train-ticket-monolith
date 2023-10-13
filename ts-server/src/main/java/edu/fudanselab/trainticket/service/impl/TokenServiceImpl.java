package edu.fudanselab.trainticket.service.impl;

import edu.fudanselab.trainticket.constant.InfoConstant;
import edu.fudanselab.trainticket.dto.BasicAuthDto;
import edu.fudanselab.trainticket.dto.TokenDto;
import edu.fudanselab.trainticket.entity.AuthUser;
import edu.fudanselab.trainticket.exception.UserOperationException;
import edu.fudanselab.trainticket.repository.AuthUserRepository;
import edu.fudanselab.trainticket.config.jwt.JWTProvider;
import edu.fudanselab.trainticket.service.ServiceResolver;
import edu.fudanselab.trainticket.service.TokenService;
import edu.fudanselab.trainticket.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;

/**
 * @author fdse
 */
@Service
public class TokenServiceImpl implements TokenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RestTemplate restTemplate;

    /*@Autowired
    private DiscoveryClient discoveryClient;*/

    @Autowired
    private ServiceResolver serviceResolver;

    @Override
    public Response getToken(BasicAuthDto dto, HttpHeaders headers) throws UserOperationException {
        String username = dto.getUsername();
        String password = dto.getPassword();
        String verifyCode = dto.getVerificationCode();
//        LOGGER.info("LOGIN USER :" + username + " __ " + password + " __ " + verifyCode);
        String verification_code_service_url = serviceResolver.getServiceUrl("ts-verification-code-service");
        if (!StringUtils.isEmpty(verifyCode)) {
            HttpEntity requestEntity = new HttpEntity(headers);
            ResponseEntity<Boolean> re = restTemplate.exchange(
                     verification_code_service_url + "/api/v1/verifycode/verify/" + verifyCode,
                    HttpMethod.GET,
                    requestEntity,
                    Boolean.class);
            boolean id = re.getBody();

            // failed code
            if (!id) {
                LOGGER.info("[getToken][Verification failed][userName: {}]", username);
                return new Response<>(0, "Verification failed.", null);
            }
        }

        // verify username and password
        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(username, password);
        try {
            authenticationManager.authenticate(upat);
        } catch (AuthenticationException e) {
            LOGGER.warn("[getToken][Incorrect username or password][username: {}, password: {}]", username, password);
            return new Response<>(0, "Incorrect username or password.", null);
        }

        AuthUser authUser = authUserRepository.findByUsername(username)
                .orElseThrow(() -> new UserOperationException(MessageFormat.format(
                        InfoConstant.USER_NAME_NOT_FOUND_1, username
                )));
        String token = jwtProvider.createToken(authUser);
        LOGGER.info("[getToken][success][USER TOKEN: {} USER ID: {}]", token, authUser.getUserId());
        return new Response<>(1, "login success", new TokenDto(authUser.getUserId(), username, token));
    }
}
