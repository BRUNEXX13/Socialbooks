package com.bss.socialbooks.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/* Classe de Seguranças*/

@EnableWebSecurity // Habiltando a segurança da API
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// Para configura o usuario
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("brunoss")
				.password("12345")
					.roles("USER");
	}

	// Permite como a autenticacao vai proceder
	protected void configure(HttpSecurity http) throws Exception {
		// Como queremos autorizar nossa requisição // Qualquer Requisicao deve estar
		// autenticada
		http.authorizeRequests()
		//Any Matcher = Liberar Url especifica da segurança.
		.antMatchers("/h2-console/**").permitAll()
			.anyRequest()
				.authenticated()
		.and()
				.httpBasic()
			.and()
				// CRSF = Protecao para evitar tipo de ataque
				.csrf().disable();
	}

}