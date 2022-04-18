package com.project.msv.service;

import com.project.msv.domain.Member;
import com.project.msv.domain.Role;
import com.project.msv.dto.MemberDetail;
import com.project.msv.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PrincipalOAuth2MemberService extends DefaultOAuth2UserService {


    private final MemberRepository memberRepository;







    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String provideId = oAuth2User.getAttribute("sub");
        String username = provider + " " + provideId;

        String password = UUID.randomUUID().toString().substring(0, 6);

        String email = oAuth2User.getAttribute("email");
        Role role = Role.USER;

        Member member = memberRepository.findByUsername(username);

        if (member == null) {
            member = Member.oAuth2Resister()
                    .username(username)
                    .password(password)
                    .point(0)
                    .providerId(provideId)
                    .role(role)
                    .provider(provider)
                    .email(email)
                    .build();
            memberRepository.save(member);
        }




        return new MemberDetail(member, oAuth2User.getAttributes());
    }
}
