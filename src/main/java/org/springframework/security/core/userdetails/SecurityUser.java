package org.springframework.security.core.userdetails;


import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SecurityUser extends User{

    private final Long id;
    public SecurityUser(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

    public SecurityUser(Long id, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;

    }

    public Long getId(){
        return this.id;
    }
}
