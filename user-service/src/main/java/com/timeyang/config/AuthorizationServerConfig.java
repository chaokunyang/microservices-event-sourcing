package com.timeyang.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * @author yangck
 */
@Configuration
public class AuthorizationServerConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
        authManagerBuilder
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER")
                .and().withUser("admin").password("password").roles("ADMIN", "USER");
    }

   /* @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .withDefaultSchema()
                .withUser("user").password("password").roles("USER").and()
                .withUser("admin").password("password").roles("USER", "ADMIN");
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/assets*/**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").permitAll()
                .and().logout().permitAll()
                .and().csrf().disable();
                //.and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()); // 使用angularjs并且开启csrf保护的话就需要配置该行代码。需要注意withHttpOnlyFalse后容易受到XSS攻击

    }

    @Configuration
    @EnableAuthorizationServer
    protected static class OAuth2Config extends AuthorizationServerConfigurerAdapter {
        /**
         * 也可以放在application.yml配置文件里面
         *
         * security:
             oauth2:
                 client:
                     client-id: timeyang
                     client-secret: timeyangsecret
                     scope: read,write
                     auto-approve-scopes: '.*'
         *
         * @param clients
         * @throws Exception
         */
        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients
                    .inMemory()
                    .withClient("timeyang")
                    .secret("timeyangsecret")
                    .authorizedGrantTypes("authorization_code", "refresh_token", "password").scopes("openid");
        }
    }
}
