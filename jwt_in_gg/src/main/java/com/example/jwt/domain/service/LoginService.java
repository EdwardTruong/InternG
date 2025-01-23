package com.example.jwt.domain.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import com.example.jwt.appilication.dto.LoginRequest;

/**
 * Service interface named {@link LoginService} for handling user login
 * operations.
 */
public interface LoginService {

        /**
         * Performs user login based on the provided login request.
         *
         * @param loginRequest The login request containing user credentials.
         * @return The token representing the user's session.
         */
        // Token login(final LoginRequest loginRequest);

        String userLogin(LoginRequest loginRequest)
                        throws io.jsonwebtoken.io.IOException, UnrecoverableKeyException,
                        KeyStoreException, NoSuchAlgorithmException, CertificateException, FileNotFoundException,
                        IOException;

}
