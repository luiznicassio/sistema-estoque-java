package com.mycompany.sistemadeestoque.util;
import org.mindrot.jbcrypt.BCrypt;

//Classe utilitária de encriptação e verificação de senha (usando BCrypt).
public class SenhaUtil {
    public static String criptografarSenha(String senha) {
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }
    public static boolean verificarSenha(String senhaDigitada, String senhaHash) {
        return BCrypt.checkpw(senhaDigitada, senhaHash);
    }
}