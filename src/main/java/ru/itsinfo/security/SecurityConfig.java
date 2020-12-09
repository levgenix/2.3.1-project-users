package ru.itsinfo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userServiceImpl; // сервис, с помощью которого тащим пользователя
    private final SuccessUserHandler successUserHandler; // класс, в котором описана логика перенаправления пользователей по ролям

    public SecurityConfig(@Qualifier("userServiceImpl") UserDetailsService userServiceImpl,
                          SuccessUserHandler successUserHandler) {
        this.userServiceImpl = userServiceImpl;
        this.successUserHandler = successUserHandler;
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServiceImpl).passwordEncoder(passwordEncoder()); // конфигурация для прохождения аутентификации
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable() // выяснить, что это даёт TODO
                .authorizeRequests()
                .antMatchers("/").permitAll() // доступность всем
                .antMatchers("/user").access("hasAnyRole('ROLE_USER')") // разрешаем входить на /user пользователям с ролью User
                .antMatchers("/", "/users").access("hasAnyRole('ROLE_ADMIN')") // доступность ко всему с ролью Admin
                .and()
                .exceptionHandling().accessDeniedPage("/accessdenied"); // 403 error https://www.codeflow.site/ru/article/spring-security-custom-access-denied-page
//                .formLogin().successHandler(successUserHandler); // подключаем наш SuccessHandler для перенеправления по ролям

        http.formLogin()
                .loginPage("/") // страница с формой логина
                .loginProcessingUrl("/j_spring_security_check") // action с формы логина
                .failureUrl("/?error") // URL при неудачном логине
                .usernameParameter("j_username") // параметры логина и пароля с формы
                .passwordParameter("j_password")
                .permitAll() // доступ к форме логина всем
                .successHandler(successUserHandler); // перенаправление по ролям при успешной регистрации

        http.logout()
                .permitAll() // разрешаем делать логаут всем
                .logoutUrl("/logout") // URL для логаута
                .logoutSuccessUrl("/?logout") // URL при удачном логауте
                .invalidateHttpSession(true); // сделать невалидной текущую сессию
    }

    // TODO
    // Необходимо для шифрования паролей
    // В данном примере не используется, отключен
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}
