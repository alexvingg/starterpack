package br.com.starterpack.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.nio.charset.StandardCharsets;

public final class JWT {
	
	private JWT() {
	}
	
	public static Claims claims(final String secretKey, final String token) {
		byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
		return Jwts.parserBuilder()
				.setSigningKey(keyBytes).build().parseClaimsJws(token)
				.getBody();
	}
}
