package com.project.msv.config.security;
import com.project.msv.service.MemberDetailService;
import com.project.msv.service.PrincipalOAuth2MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberDetailService memberDetailService;
    private final PrincipalOAuth2MemberService oauth2service;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailHandler loginFailHandler;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/, /login, /signup, /, /vocaboard/list, /vocaboard/listsearch").permitAll()
                .antMatchers("/user/**").hasRole("USER")
                .and() // 로그인 설정
                .formLogin()
                .loginPage("/login")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailHandler)
                .permitAll()
                .and() // 로그아웃 설정
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .deleteCookies("isLogin")
                .invalidateHttpSession(true)
                .and()
                .oauth2Login()
                .loginPage("/login/oauth2")
                .defaultSuccessUrl("/")
                .failureUrl("/login")
                .userInfoEndpoint()
                .userService(oauth2service);


    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberDetailService).passwordEncoder(encoder());

    }





}
