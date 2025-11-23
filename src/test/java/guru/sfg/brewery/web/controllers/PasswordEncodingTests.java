package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordEncodingTests {
    static final String PASSWORD = "password";
    static final String GURU_PASSWORD = "guru";
    static final String TIGER_PASSWORD = "tiger";

    @Test
    void testNoOp() {
        PasswordEncoder noOp = NoOpPasswordEncoder.getInstance();

        System.out.println(noOp.encode(PASSWORD));
    }

    @Test
    void testLdap() {
        PasswordEncoder ldapShaPasswordEncoder = new LdapShaPasswordEncoder();
        System.out.println(ldapShaPasswordEncoder.encode(GURU_PASSWORD));
        System.out.println(ldapShaPasswordEncoder.encode(GURU_PASSWORD));

        String encode = ldapShaPasswordEncoder.encode(PASSWORD);

        assertTrue(ldapShaPasswordEncoder.matches(PASSWORD, encode));
    }

    @Test
    void testSha256() {
        PasswordEncoder sha256 = new StandardPasswordEncoder();
        System.out.println(sha256.encode(TIGER_PASSWORD));
        System.out.println(sha256.encode(TIGER_PASSWORD));

        String encode = sha256.encode(PASSWORD);

        assertTrue(sha256.matches(PASSWORD, encode));
    }

    @Test
    void testBCrypt() {
        PasswordEncoder bCrypt = new BCryptPasswordEncoder(10);
        System.out.println(bCrypt.encode(PASSWORD));
//        System.out.println(bCrypt.encode(PASSWORD));
//
//        String encode = bCrypt.encode(PASSWORD);
//
//        assertTrue(bCrypt.matches(PASSWORD, encode));
    }

    @Test
    void hashingExample() {
        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes(StandardCharsets.UTF_8)));
        System.out.println(DigestUtils.md5DigestAsHex(PASSWORD.getBytes(StandardCharsets.UTF_8)));
//NOT VALID EXAMPLE FOR PRODUCTION HASHING!
        String salted = PASSWORD + "ThisIsMySALTVALUE";

        System.out.println(DigestUtils.md5DigestAsHex(salted.getBytes()));
    }
}
