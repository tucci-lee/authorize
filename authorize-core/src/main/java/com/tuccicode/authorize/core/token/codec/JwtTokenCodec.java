package com.tuccicode.authorize.core.token.codec;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

/**
 * @author tucci.lee
 */
public class JwtTokenCodec implements TokenCodec {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String UID = "uid";

    private Algorithm algorithm;

    public JwtTokenCodec(String secret) {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public JwtTokenCodec(String publicKey, String privateKey) {
        try {
            byte[] decodePublicKey = Base64.getDecoder().decode(publicKey);
            byte[] decodePrivateKey = Base64.getDecoder().decode(privateKey);
            RSAPublicKey rsaPublicKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
                    .generatePublic(new X509EncodedKeySpec(decodePublicKey));
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA")
                    .generatePrivate(new PKCS8EncodedKeySpec(decodePrivateKey));
            this.algorithm = Algorithm.RSA256(rsaPublicKey, rsaPrivateKey);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            logger.warn("init jwt token codec error, rsa256 info error, use default algorithm");
            this.algorithm = Algorithm.HMAC256("");
        }
    }

    @Override
    public String encode(Long uid) {
        return JWT.create()
                .withIssuedAt(new Date())
                .withClaim(UID, uid)
                .sign(this.algorithm);
    }

    @Override
    public Long decode(String token) {
        JWTVerifier verifier = JWT.require(this.algorithm).build();
        try {
            DecodedJWT verify = verifier.verify(token);
            return verify.getClaim(UID).asLong();
        } catch (RuntimeException e) {
            return null;
        }
    }
}
