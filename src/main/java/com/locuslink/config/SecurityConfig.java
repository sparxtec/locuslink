package com.locuslink.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;




@Configuration
@EnableWebSecurity(debug = false)
public class SecurityConfig extends WebSecurityConfigurerAdapter   {

    private static final Logger logger = Logger.getLogger(SecurityConfig.class);

    @Value("${https.enabled}")
    private boolean httpsEnabled;



	@Bean
	@Override
	/*Secuirty Roles*/
	public UserDetailsService userDetailsService() {
		UserDetails user =
			 User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("MED-ADMIN")
				.build();

		return new InMemoryUserDetailsManager(user);
	}


    @Override
    protected void configure(HttpSecurity http) throws Exception {

    	http
	 	.authorizeRequests()

	 	   // .antMatchers("/css/**","/js/**","/images/**","/login","/login*","/logout","/webfonts/**","/fonts/**","/initLogin", "/initDashboard*"    ).permitAll()

	 		.anyRequest()
	 		//.authenticated()
	 		.permitAll()
	 			.and()

             .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
             .and()

			.headers()
				.frameOptions().sameOrigin()
				.and()
	    .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	 	//.csrf().disable();



   }


}