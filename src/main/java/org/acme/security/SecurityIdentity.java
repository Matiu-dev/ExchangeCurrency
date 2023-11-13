package org.acme.security;

import io.quarkus.security.credential.Credential;

import java.security.Principal;

public interface SecurityIdentity {

    Principal getPrincipal();
    boolean isAnonymous();
    boolean hasRole(String role);
    <T extends Credential> T getCredential(Class<T> credentialType);
    <T> T getAttribute(String name);
}
