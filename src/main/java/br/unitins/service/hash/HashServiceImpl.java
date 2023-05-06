package br.unitins.service.hash;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class HashServiceImpl implements HashService {

    private String salt = "#22121ab12";
    private Integer iterationCount = 405;
    private Integer keylenght = 512;

    @Override
    public String getHashSenha(String senha) {
        try {
            byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                    .generateSecret(
                            new PBEKeySpec(senha.toCharArray(), salt.getBytes(), iterationCount, keylenght))
                    .getEncoded();
            return Base64.getEncoder().encodeToString(result);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        HashService service = new HashServiceImpl();

        System.out.println();
        System.out.println(service.getHashSenha("senha"));
        System.out.println(service.getHashSenha("senha"));
        System.out.println(service.getHashSenha("Senha"));
    }

}
