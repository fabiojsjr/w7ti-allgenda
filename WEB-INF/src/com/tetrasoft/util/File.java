package com.tetrasoft.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import jcifs.smb.SmbFile;

import com.technique.engine.app.SystemParameter;
import com.technique.engine.util.ExceptionWarning;

public class File extends java.io.File {

	public File(java.io.File file) {
		super(file.toString());
	}

	public File(String parent, String child) {
		super(parent,child);
	}

	public File(String pathname) {
		super(pathname);
	}

	public File(File parent, String child) {
		super(parent, child);
	}

	/**
	 * 'Delete' tenta 10 vezes com intervalos de 100ms
	 */
	@Override
	public boolean delete() {
		if(isDirectory()){
			java.io.File[] files = listFiles();
			for (java.io.File file : files) {
				new File(file).delete();
			}
			super.delete();
		}else{
			super.delete();
			if ( super.exists() ) {
				for ( int i = 1; i < 10; i++ ) {
					try {
						Thread.sleep(100);
						super.delete();
						if ( !super.exists() ) break;
					} catch (Exception e) {}
				}
			}
		}
		return !this.exists();
	}

	/**
	 * @see com.futuware.maquina.util.File#renameTo(com.futuware.maquina.util.File
	 *      dest)
	 */
	@Override
	public boolean renameTo(java.io.File dest) {
		return renameTo((File) dest);
	}

	/**
	 * Usa o 'renameTo' tentando 10 vezes a cada 100ms
	 */
	public boolean renameTo(File dest) {
		super.renameTo(dest);
		if ( super.exists() ) {
			for ( int i = 1; i < 10; i++ ) {
				try {
					Thread.sleep(100);
					super.renameTo(dest);
					if ( !super.exists() ) break;
				} catch (Exception e) {}
			}
		}
		return !super.exists();
	}

	/**
	 * @see copyTo();
	 * @param sourceFile
	 * @param destFile
	 * @throws ExceptionWarning
	 */
	public static void copyFile(java.io.File sourceFile, File destFile) throws ExceptionWarning {
		copyFile(new File(sourceFile.toString()), destFile);
	}

	public void copyTo(SmbFile file) throws Exception {
		/*
                try {
                        SmbFile dirs = new SmbFile(file.getParent());
                        if (!dirs.exists()) dirs.mkdirs();
                        if ( file.exists() ) file.delete();

                int i;
                byte[] buf = new byte[8192];

                FileInputStream in = new FileInputStream( this );
                SmbFileOutputStream out = new SmbFileOutputStream( file );

                while(( i = in.read( buf )) > 0 ) {
                    out.write( buf, 0, i );
                }

                in.close();
                out.close();

                } catch (Exception e) {
                        throw new ExceptionWarning("Exception in copyFile SMB. Reason:" + e.getMessage(), e);
                }
		 */
	}

	public void copyTo(File file) throws ExceptionWarning {
		copyFile(this, file);
	}

	/**
	 * @see copyTo();
	 * @param sourceFile
	 * @param destFile
	 * @throws ExceptionWarning
	 */
	public static void copyFile(File sourceFile, File destFile) throws ExceptionWarning {
		try {
			new File(destFile.getParent()).mkdirs();
			if ( destFile.exists() ) destFile.delete();

			InputStream in = new FileInputStream(sourceFile);
			OutputStream out = new FileOutputStream(destFile);

			byte[] buf = new byte[1024];
			int len;
			while ( (len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		} catch (Exception e) {
			throw new ExceptionWarning("Exception in copyFile. Reason:" + e.getMessage(), e);
		}
	}

	public static void copyDir(File sourceFile, File destFile) throws ExceptionWarning {
		try {
			java.io.File[] tmp;
			if ( !destFile.exists() ) destFile.mkdirs();

			if(sourceFile.isDirectory()){
				tmp = sourceFile.listFiles();
				for (java.io.File file : tmp) {
					if(file.isDirectory())
						copyDir(new File(file), new File(destFile + "/" + file.getName()));
					else{
						InputStream in = new FileInputStream(file);
						OutputStream out = new FileOutputStream(destFile + "/" + file.getName());

						byte[] buf = new byte[1024];
						int len;
						while ( (len = in.read(buf)) > 0) {
							out.write(buf, 0, len);
						}
						in.close();
						out.close();
					}
				}
			}
		} catch (Exception e) {
			throw new ExceptionWarning("Exception in copyFile. Reason:" + e.getMessage(), e);
		}
	}

	/**
	 * Copia o arquivo para os outros servidores
	 *
	 * @return Retorna false se o arquivo não  pertence ao 'root' ou se não 
	 *         consegue conexao. Retorna true em outros casos.
	 */
	public boolean rpl() {
		return true;
		//              return FileReplica.getInstance().rpl(this);
	}

	/**
	 * Deleta o arquivo inclusive de outros servidores
	 *
	 * @return Retorna false se o arquivo não  pertence ao 'root' ou se não 
	 *         consegue conexao. Retorna true em outros casos.
	 */
	public boolean rplDel() {
		return true;
		//              return FileReplica.getInstance().rpl(this, true);
	}

	/**
	 * Também replica
	 */
	public static void saveToOutputPath(String sid, java.io.File sourceFile, String fileName) throws ExceptionWarning {
		try {
			String destDir = SystemParameter.get(sid, "fileUpload", "outputPath");
			File destFile = new File(destDir + fileName);
			copyFile(sourceFile, destFile);
			destFile.rpl();
		} catch (Exception e) {
			throw new ExceptionWarning("Exception in saveToOutputPath. Reason:" + e.getMessage(), e);
		}
	}
}
