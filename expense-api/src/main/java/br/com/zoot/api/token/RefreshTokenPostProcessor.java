package br.com.zoot.api.token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		// caso retorne false, não executa o outro método
		return returnType.getMethod().getName().equals("postAccessToken");
	}

	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, 
			                                 MethodParameter returnType,
			                                 MediaType selectedContentType, 
			                                 Class<? extends HttpMessageConverter<?>> selectedConverterType,
			                                 ServerHttpRequest request, 
			                                 ServerHttpResponse response) {
		
		HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
		HttpServletResponse resp = ((ServletServerHttpResponse) response).getServletResponse();
		
		String refreshToken = body.getRefreshToken().getValue();

		addRefreshTokenCookie(refreshToken, req, resp);
		delRefreshTokenCookie(body);
		
		return body;
	}

	private void delRefreshTokenCookie(OAuth2AccessToken body) {
		
		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;
		
		token.setRefreshToken(null); 
	}

	private void addRefreshTokenCookie(String refreshToken, 
			                           HttpServletRequest req, 
			                           HttpServletResponse resp) {
		
		Cookie tokenCookie = new Cookie("refreshToken", refreshToken);
		tokenCookie.setHttpOnly(true);
		tokenCookie.setSecure(false); //TODO: Mudar para true em produção
		tokenCookie.setPath(req.getContextPath() + "/oauth/token");
		tokenCookie.setMaxAge(2592000);
		
		resp.addCookie(tokenCookie);		
	}

}
