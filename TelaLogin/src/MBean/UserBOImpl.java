package MBean;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import model.User;

public class UserBOImpl {
	 public User isUsuarioReadyToLogin(String email, String senha) {
	        try {
	               email = email.toLowerCase().trim();
	               logger.info("Verificando login do usuário " + email);
	               List retorno = dao.findByNamedQuery(
	                             User.FIND_BY_EMAIL_SENHA,
	                             new 
("email", email
	                                          .trim(), "senha", convertStringToMd5(senha)));

	               if (retorno.size() == 1) {
	                      User userFound = (User) retorno.get(0);
	                      return userFound;
	               }

	               return null;
	        } catch (Exception e) {
	               e.printStackTrace();
	               throw new Exception(e.getMessage());
	        }
		}
	 
	 public String gerarNovaSenha() {
         String[] carct = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                       "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
                       "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",
                       "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                       "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                       "W", "X", "Y", "Z" };

         String senha = "";

         for (int x = 0; x < 10; x++) {
                int j = (int) (Math.random() * carct.length);
                senha += carct[j];

         }

         return senha;
   }
		
		private String convertStringToMd5(String valor) {
	        MessageDigest mDigest;
	        try { 
	               //Instanciamos o nosso HASH MD5, poderíamos usar outro como
	               //SHA, por exemplo, mas optamos por MD5.
	               mDigest = MessageDigest.getInstance("MD5");
	                
	               //Convert a String valor para um array de bytes em MD5
	               byte[] valorMD5 = mDigest.digest(valor.getBytes("UTF-8"));
	                
	               //Convertemos os bytes para hexadecimal, assim podemos salvar
	               //no banco para posterior comparação se senhas
	               StringBuffer sb = new StringBuffer();
	               for (byte b : valorMD5){
	                      sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1,3));
	               }

	               return sb.toString();
	                
	        } catch (NoSuchAlgorithmException e) {
	               // TODO Auto-generated catch block
	               e.printStackTrace();
	               return null;
	        } catch (UnsupportedEncodingException e) {
	               // TODO Auto-generated catch block
	               e.printStackTrace();
	               return null;
	        }
	    }
}
