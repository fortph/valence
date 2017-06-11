package divers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encode {
	
	public String encode(String s){
		byte[] cle=s.getBytes();
		byte[]hash=null;
		try {
			hash=MessageDigest.getInstance("MD5").digest(cle);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuilder hashString=new StringBuilder();
		 for (int i = 0; i < hash.length; i++)
	        {
	            String hex = Integer.toHexString(hash[i]);
	            if (hex.length() == 1)
	            {
	                hashString.append('0');
	                hashString.append(hex.charAt(hex.length() - 1));
	            }
	            else
	                hashString.append(hex.substring(hex.length() - 2));
	        }
		return hashString.toString();
	}

}
