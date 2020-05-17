package br.com.starterpack.config;

import br.com.starterpack.exception.UnauthorizedException;
import br.com.starterpack.model.User;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class SecurityFilter extends OncePerRequestFilter {

	private final SecurityVerification securityVerification;

	public SecurityFilter(final SecurityVerification securityVerification) {
		this.securityVerification = securityVerification;
	}

	private void bearerAuthorization(final String authHeader, final FilterChain chain, final HttpServletRequest request,
                                     final HttpServletResponse response) throws IOException, ServletException {

		final String token = authHeader.substring(7);

		try {

			if(this.securityVerification.isTokenExpired(token)){
				throw new UnauthorizedException();
			}
			
			final User user = this.securityVerification.findUserByToken(token);
			
			User.setCurrentUser(user);
			chain.doFilter(request, response);

		}
		catch(UnauthorizedException e){
			this.unauthorized(response);
		}catch(Exception e){
			e.printStackTrace();
			if(e.getCause() != null && e.getCause().getMessage().equals("UnauthorizedException")){
				this.unauthorized(response);
				return;
			}
			throw new ServletException(e);
		}

	}

	private void unauthorized(HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.addHeader("Content-Type", "application/json;charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.getWriter().print("");
		response.getWriter().flush();
	}

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

		if ("OPTIONS".equals(httpServletRequest.getMethod())) {
			httpServletResponse.setStatus(HttpServletResponse.SC_OK);
			filterChain.doFilter(httpServletRequest, httpServletResponse);
		}

		else if (!httpServletRequest.getDispatcherType().equals(DispatcherType.ASYNC)) {

			final String authHeader = httpServletRequest.getHeader("authorization");

			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				this.bearerAuthorization(authHeader, filterChain, httpServletRequest, httpServletResponse);
			}
			else {
				this.unauthorized(httpServletResponse);
			}

		}

		else {
			filterChain.doFilter(httpServletRequest, httpServletResponse);
		}

	}
}
