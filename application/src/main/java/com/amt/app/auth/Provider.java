package com.amt.app.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;


public class Provider {

    private Algorithm algorithm;
    private String issuer;
    private JWTVerifier verifier;

    public Provider(String algorithm, byte[] secret, String issuer) throws Exception {
        if (!algorithm.equals("HS256")) {
            throw new Exception("Only HS256 algorithm is supported");
        }
        this.algorithm = Algorithm.HMAC256(secret);
        this.issuer = issuer;
        this.verifier = JWT.require(this.algorithm)
                .withIssuer(this.issuer)
                .build();
    }

    public User login(String token) throws JWTVerificationException {
        DecodedJWT jwt = verifier.verify(token);
        return User.fromString(jwt.getPayload());
    }
    public User login(String username, String password) {
        // GET on /auth/login
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXUyJ9.eyJpc3MiOiJhdXRoMCJ9.AbIJTDMFc7yUa5MhvcP03nJPyCPzZtQcGEp-zWfOkEE";
        token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.XbPfbIHMI6arZ3Y922BhjWgQzWXcXNrz0ogtVhfEd2o";
        Integer account_id = 0;
        String account_username = "";
        String account_role = "";
        // Verify if all information are consistant2
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .withSubject(account_id.toString())
                .build(); //Reusable verifier instance
        DecodedJWT jwt = verifier.verify(token);
        return login(token);
    }
    
    public int register() {
        return -1;
    }

    public static void test() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXUyJ9.eyJpc3MiOiJhdXRoMCJ9.AbIJTDMFc7yUa5MhvcP03nJPyCPzZtQcGEp-zWfOkEE";
        token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.XbPfbIHMI6arZ3Y922BhjWgQzWXcXNrz0ogtVhfEd2o";
        try {
            //Algorithm algorithm = Algorithm.HMAC256("secret");
/*            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .withSubject("my-user-id")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
*/

        } catch (JWTVerificationException exception) {
            //Invalid signature/claims
        }
    }

}
