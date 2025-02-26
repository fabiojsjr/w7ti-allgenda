package com.tetrasoft.common;

import java.awt.Image;
import java.awt.MediaTracker;
import java.io.File;

import javax.swing.ImageIcon;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

import com.technique.engine.app.SystemParameter;
import com.technique.engine.util.TechCommonKeys;
import com.tetrasoft.app.sender.SenderInterface;


public class Thumbnail {
	
	public Thumbnail() {
	}
	
	public static boolean convertVideo(File file, String tempo) {
		try {
			String path = SystemParameter.get(SenderInterface.SID, TechCommonKeys.TAG_SYSTEM_PROPERTIES, TechCommonKeys.SYSTEM_ROOT_PATH) + "maquinaNet/";

			if(tempo==null) tempo = "00:01:00";
			String line = path + "ffmpeg -i " + file + " -deinterlace -an -ss " + tempo + " -t 00:00:01 -r 1 -y -vcodec mjpeg -f mjpeg " + file.getAbsolutePath().replaceAll(".wmv", ".jpg");

			Runtime.getRuntime().exec(line);

			File novo = new File(file.getAbsolutePath().replaceAll(".wmv", ".jpg"));
			for(int i=0;i<=10;i++) {
				if (!novo.exists()) {
					try {
						Thread.sleep(500);
					} catch (Exception e) {
					}
				} else {
					break;
				}
			}
			if (novo.exists()) {
				Thumbnail th = new Thumbnail();
				th.setLimite(200,250);
				th.converter(novo.toString(),novo.toString());
			}
			return novo.exists();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
   double largura = 0;
   public double getLargura() {
      return largura;
   }
   public void setLargura( double value ) {
      largura = value;
   }

   double altura = 0;
   public double getAltura() {
      return altura;
   }
   public void setAltura( double value ) {
      altura = value;
   }

   // --------------------------------------------------------------------------
   // --------------------------------------------------------------------------
   // --------------------------------------------------------------------------
   int limiteHorizontal = 0;
   int limiteVertical   = 0;

   private double larguraOriginal = 0; 
   private double alturaOriginal = 0; 

   public void setLimite( int _h, int _v ) {
      setLimiteHorizontal( _h );
      setLimiteVertical( _v );
   }

   public void setLimiteHorizontal( int _h  ) {
      limiteHorizontal = _h;
   }

   public void setLimiteVertical( int _h  ) {
      limiteVertical = _h;
   }

   public int getLimiteHorizontal() {
      return limiteHorizontal;
   }

   public int getLimiteVertical() {
      return limiteVertical;
   }
   // --------------------------------------------------------------------------
   // --------------------------------------------------------------------------
   // --------------------------------------------------------------------------
   public boolean excedeuLimite( String _imagem, int _h, int _v ) {
	   setLimite( _h, _v );
	   return excedeuLimite( _imagem );
   }
   public boolean excedeuLimite( String orig ) {
	   if( orig.indexOf(".keep.") >= 0 ) { // não redimensionar (capas...)
		   return false;
		   
	   } else {
		   try {
			   Image inImage = new ImageIcon(orig).getImage();
			   
			   double l = (double)inImage.getWidth(null);
			   double a = (double)inImage.getHeight(null);
			   
			   setLargura( l );
			   setAltura( a );
			   
			   if( (l > getLimiteHorizontal()) || (a > getLimiteVertical())   ) {
				   
				   System.out.println("Excedeu limite de imagem.");
				   return true;
			   } else {
				   return false;
			   }
		   } catch (Exception e) {
			   e.printStackTrace( System.out );
			   return false;
		   }
	   }
   }

   public boolean excedeuLimiteImg(com.lowagie.text.Image img, int _h, int _v ) {
      setLimite( _h, _v );
      return excedeuLimiteImg( img );
   }
   public boolean excedeuLimiteImg( com.lowagie.text.Image inImage ) {
      try {
         double l = (double)inImage.getPlainWidth();
         double a = (double)inImage.getPlainHeight();

         setLargura( l );
         setAltura( a );

         if( (l > getLimiteHorizontal()) ||
             (a > getLimiteVertical())   ) {

             System.out.println("Excedeu limite de imagem.");
             return true;
         } else {
             return false;
         }
      } catch (Exception e) {
         e.printStackTrace( System.out );
         return false;
      }
   }
   // --------------------------------------------------------------------------
   // --------------------------------------------------------------------------
   // --------------------------------------------------------------------------
   public void converter( String orig, String thumb ) {
      converter( orig, thumb, getLimiteHorizontal(), getLimiteVertical() );
   }

   public double[] getTamanhos( String orig, int l, int a ) {
	   double escala = getEscala( orig, l, a);
	   double[] retorno = new double[]{ larguraOriginal, alturaOriginal }; // largura - altura
	   
	   if( escala > 0 && escala < 1 ) {
	       retorno[0] = larguraOriginal*escala;
	       retorno[1] = alturaOriginal*escala;
	   }

       System.out.println("ret0   = " + retorno[0] );
       System.out.println("ret1   = " + retorno[1] );

       return retorno;
   }
   public double[] getTamanhosOriginal( String orig ) {
	   Image inImage = new ImageIcon(orig).getImage();
	   double larguraOriginal = (double)inImage.getWidth(null);
	   double alturaOriginal  = (double)inImage.getHeight(null);
	   
	   double retorno[] = new double[]{ larguraOriginal, alturaOriginal };
	   return retorno;
   }

   public double getEscala( String orig, int l, int a ) {
	   try {
		   Image inImage = new ImageIcon(orig).getImage();
		   
		   double larguraOriginal = (double)inImage.getWidth(null);
		   double alturaOriginal  = (double)inImage.getHeight(null);
		   
		   double scaleW = (double)l / larguraOriginal;
		   double scaleH = (double)a / alturaOriginal;
		   
		   double escala = 1.0;
		   
		   if( scaleW < scaleH ) { // estourou mais em W
			   escala = scaleW;
		   } else {
			   escala = scaleH;
		   }
		   
//		   System.out.println("scaleW   = " + scaleW );
//		   System.out.println("scaleH   = " + scaleH );
//		   System.out.println("escala   = " + escala );
		   
		   return escala;
		   
	   } catch (Exception e) {
		   e.printStackTrace();
		   return 1.0;
	   }
   }
   public double getEscalaImg( com.lowagie.text.Image inImage, int l, int a ) {
       try {
           double larguraOriginal = (double)inImage.getPlainWidth();
           double alturaOriginal  = (double)inImage.getPlainHeight();

           double scaleW = (double)l / larguraOriginal;
           double scaleH = (double)a / alturaOriginal;

           double escala = 1.0;

           if( scaleW < scaleH ) { // estourou mais em W
              escala = scaleW;
           } else {
              escala = scaleH;
           }

           System.out.println("scaleW   = " + scaleW );
           System.out.println("scaleH   = " + scaleH );
           System.out.println("escala   = " + escala );

           return escala;

       } catch (Exception e) {
           e.printStackTrace();
           return 1.0;
       }
   }

   public void converter(String orig, String thumb, int l, int a) {
	   try {
//		   File dest = new File(thumb);
//		   if(dest.exists()) dest.delete();
		   ImageIcon image = new ImageIcon(orig);
		   Image inImage = image.getImage();
		   double larguraOriginal = (double)inImage.getWidth(null);
		   double alturaOriginal  = (double)inImage.getHeight(null);

		   System.out.println("=================================");
		   System.out.println("Redimensionamento de imagem");
		   System.out.println("original = " + orig );
		   System.out.println("destino  = " + thumb );
		   System.out.println("largura  = " + larguraOriginal );
		   System.out.println("altura   = " + alturaOriginal  );

		   double escala = getEscala( orig, l, a );

		   int scaledW = (int)(larguraOriginal * escala);
		   int scaledH = (int)(alturaOriginal  * escala);

		   ConvertCmd cmd = new ConvertCmd();
		   cmd.setSearchPath(SystemParameter.get("Allgenda", "systemProperties", "imageMagick"));
		   IMOperation op = new IMOperation();
		   op.addImage(orig);
		   op.resize(scaledW, scaledH);
		   op.addImage(thumb);
		   cmd.run(op);
		   image.getImage().flush();
	   } catch (Exception e) {
		   e.printStackTrace();
	   }
   }
   // --------------------------------------------------------------------------
   // --------------------------------------------------------------------------
   // --------------------------------------------------------------------------
   public static void main( String[] args ) {
      try {
			Thumbnail thumb = new Thumbnail();
			
			String path = "c:/Servidor/tomcat/webapps/allgenda/WEB-INF/files/images";
			File files[] = new File(path+"/hd").listFiles();
			File dest = new File(path+"/full");
			if(!dest.exists()) dest.mkdirs();
			dest = new File(path+"/th");
			if(!dest.exists()) dest.mkdirs();
//			dest = new File(path+"/hd");
//			if(!dest.exists()) dest.mkdirs();
			
			for (int i = 0; i < files.length; i++) {
				if(files[i].isFile()){
					System.out.println("\nTotal: " + files.length + " - Pos: " + i + " - File: " + files[i].getName());
					
					dest = new File(path+"/full/"+files[i].getName());
					thumb.converter(files[i].getPath(), dest.getPath(), 800, 600);
					
					dest = new File(path+"/th/"+files[i].getName());
					thumb.converter(files[i].getPath(), dest.getPath(), 90, 45);
					
//					dest = new File(path+"/hd/"+files[i].getName());
//					files[i].renameTo(dest);
					
					dest = new File(path+"/hd/"+files[i].getName());
					thumb.converter(dest.getPath(), path+"/"+files[i].getName(), 350, 270);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
