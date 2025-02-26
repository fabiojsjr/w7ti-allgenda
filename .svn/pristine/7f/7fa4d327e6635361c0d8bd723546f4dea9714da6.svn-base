package com.tetrasoft.app.util;

import java.security.MessageDigest;

public class SHA1 {
	public static String encrypt( String input ) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
//			System.out.println("Message digest object info: ");
//			System.out.println("   Algorithm = "+md.getAlgorithm());
//			System.out.println("   Provider = "+md.getProvider());
//			System.out.println("   toString = "+md.toString());
			
			md.update(input.getBytes()); 
			byte[] output = md.digest();
//			System.out.println();
//			System.out.println("SHA1(\""+input+"\") =");
//			System.out.println("   "+bytesToHex(output));
			
			return bytesToHex(output);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}
	
	public static void main(String[] a) {
		// http://bv.nnex.com/prd/p_dirf.asp?CPF=00013149121&SID=c1e5e4d6cc385330a78945451a771ce27d308772
		String input = "03044621445dirf";
		System.out.println( "http://bv.nnex.com/prd/p_dirf.asp?CPF=03044621445&SID=" + encrypt(input).toLowerCase() );
	}
	
	public static String bytesToHex(byte[] b) {
		char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
		StringBuffer buf = new StringBuffer();
		for (int j=0; j<b.length; j++) {
			buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
			buf.append(hexDigit[b[j] & 0x0f]);
		}
		return buf.toString();
	}
}