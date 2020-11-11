package com.fa20se28.vma.component;

import org.springframework.security.core.Authentication;

public interface AuthenticationComponent {
    Authentication getAuthentication();
}
