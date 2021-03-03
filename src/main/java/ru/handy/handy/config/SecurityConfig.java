package ru.handy.handy.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableAutoConfiguration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeRequests()

                .antMatchers("/api/test/**").authenticated()


                .antMatchers("/api/expert/**").hasAnyRole("EXPERT", "ADMIN")
                .antMatchers("/api/superuser/**").hasAnyRole("SUPERUSER", "ADMIN")
                .antMatchers("/api/jurist/**").hasAnyRole("JURIST", "ADMIN")
                .antMatchers("/api/advertiser/**").hasAnyRole("ADVERTISER", "ADMIN")
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .antMatchers("/api/searcher/find/advanced_query/**").hasAnyRole("ADMIN", "SUPERUSER", "ADVERTISER", "JURIST")
                .antMatchers("/api/searcher/**").authenticated()
                .antMatchers("/api/**").authenticated()
                .antMatchers("/").authenticated()
                .antMatchers("/login").permitAll()
                .and()
                .formLogin().loginPage("/login")
                .and()
                .logout().logoutSuccessUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }











    //    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user").password("uuuu").roles("USER")
//                .and()
//                .withUser("super").password("uuuu").roles("SUPER");
//    }

//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService(){
//
//        return new InMemoryUserDetailsManager(
//                User.builder().username("user")
//                        .password(passwordEncoder().encode("uuuu")).roles("USER").build(),
//                User.builder().username("admin")
//                        .password(passwordEncoder().encode("aaaa")).roles("ADMIN").build(),
//                User.builder().username("admin")
//                        .password(passwordEncoder().encode("aaaa")).roles("ADMIN").build());
//        UserDetails user = User.builder()
//                .username("user")
//                .password(passwordEncoder().encode("uuuu"))
//                .roles("USER")
//                .build();
////                .password("{bcrypt}$2y$12$IkPSjVu4N8a8Hdj/bx/LIuE5KQhAILDEajG4ZSYLMZx5QWOaykGpk")
//
//        UserDetails admin = User.builder()
//                .username("admin")
////                .password("{bcrypt}$2y$12$myxo9KkIBzRbbTVnRGhBsOHrIcLYPfmkfxn0lxobsdw.ZXYbfmGDy")
//                .password(passwordEncoder().encode("aaaa"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails superuser = User.builder()
//                .username("superuser")
////                .password("{bcrypt}$2y$12$myxo9KkIBzRbbTVnRGhBsOHrIcLYPfmkfxn0lxobsdw.ZXYbfmGDy")
//                .password(passwordEncoder().encode("ssss"))
//                .roles("SUPERUSER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin, superuser);
//    }
}
