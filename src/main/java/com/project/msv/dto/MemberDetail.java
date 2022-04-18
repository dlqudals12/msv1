package com.project.msv.dto;

import com.project.msv.domain.Member;
import com.project.msv.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
public class MemberDetail implements UserDetails, OAuth2User {

    private Member member;
    private Map<String, Object> attributes;

    //oauth로그인시
    public MemberDetail(Member member, Map<String, Object> attributes) {
        this.member = member;
        this.attributes = attributes;
    }

    //form로그인시
    public MemberDetail(Member member) {
        this.member = member;
    }

    //UserDetail
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(() -> "ROLE_" + member.getRole());
        return collectors;
    }

    //UserDetail
    @Override
    public String getPassword() {
        return member.getPassword();
    }

    //UserDetail
    @Override
    public String getUsername() {
        return member.getUsername();
    }

    //UserDetail
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //UserDetail
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //UserDetail
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //UserDetail
    @Override
    public boolean isEnabled() {
        return true;
    }

    //oauth2
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    //oauth2
    @Override
    public String getName() {
        String sub = attributes.get("sub").toString();
        return sub;
    }
}
