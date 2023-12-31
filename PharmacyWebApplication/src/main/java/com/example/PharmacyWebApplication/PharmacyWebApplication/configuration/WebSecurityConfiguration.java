package com.example.PharmacyWebApplication.PharmacyWebApplication.configuration;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.EnumSet;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Autowired
    private UserDetailsService jwtService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        RequestMatcher requestMatcher = (HttpServletRequest request) ->
                EnumSet.of(DispatcherType.REQUEST).contains(request.getDispatcherType());

        httpSecurity
                .csrf(customizer -> customizer
                        .ignoringRequestMatchers("/authenticate","/registerNewUser","/product/list", "/product/add", "product/list/{id}", "product/update/{id}", "product/delete/{id}", "/category/create", "/category/list", "/category/update/{categoryId}", "/category/delete/{categoryId}","/placeOrder", "/addToCart/{id}", "/getCartDetails", "/getProductDetails/{isSingleProductCheckout}/{id}", "/updateCartItemQuantity/{cartId}", "/deleteCartItem/{cartId}", "/placeOrder/{isSingleProductCheckout}", "/getAllOrderDetails/{status}", "/api/v1/files",
                                "/api/v1/prescriptions", "/api/v1/prescriptions/add", "/api/v1/prescriptions/list", "/api/v1/prescriptions/getPrescriptionDetails","/api/v1/prescriptions/all", "/api/sales/day", "/api/sales/last7days", "/api/sales/last30days", "/api/sales/bydayofweek", "/api/sales/percentages")) // Exclude "/authenticate" from CSRF protection
                .authorizeRequests()
                .requestMatchers("/authenticate","/registerNewUser","/product/list", "/product/add", "product/list/{id}",
                        "product/update/{id}", "product/delete/{id}", "/category/create", "/category/list", "/category/update/{categoryId}", "/category/delete/{categoryId}", "/placeOrder", "/addToCart/{id}", "/getCartDetails", "/getProductDetails/{isSingleProductCheckout}/{id}",
                        "/updateCartItemQuantity/{cartId}", "/deleteCartItem/{cartId}", "/placeOrder/{isSingleProductCheckout}", "/getAllOrderDetails/{status}", "/api/v1/files", "/api/v1/prescriptions" , "/api/v1/prescriptions/add", "/api/v1/prescriptions/list", "/api/v1/prescriptions/getPrescriptionDetails", "/api/v1/prescriptions/all", "/api/sales/day", "/api/sales/last7days", "/api/sales/last30days", "/api/sales/bydayofweek", "/api/sales/percentages").permitAll()
                .requestMatchers(HttpHeaders.ALLOW).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(jwtService).passwordEncoder(passwordEncoder());
    }
}
