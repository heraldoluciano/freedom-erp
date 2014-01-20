package org.freedom.infra.functions;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
//import java.nio.charset.Charset;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Vector;
import java.util.jar.Attributes;
//import java.util.jar.JarFile;
//import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.swing.JOptionPane;

import org.freedom.infra.model.jdbc.DbConnection;

//import com.sun.org.apache.xerces.internal.util.HTTPInputSource;

public class SystemFunctions {

	public static final int OS_LINUX = 0;

	public static final int OS_WINDOWS = 1;	

	public static final int OS_OSX = 3;
	
	public static final int OS_VERSION_WINDOWS_XP = 10;
	
	public static final int OS_VERSION_WINDOWS_VISTA = 11;
	
	public static final int OS_VERSION_WINDOWS_SEVEN = 12;
	
	public static final int OS_VERSION_WINDOWS_EIGHT = 13;
	

	/**
	 * 
	 * @return A name of operational system.<BR>
	 */
	public static int getOS() {

		int ret = -1;

		final String system = System.getProperty("os.name").toLowerCase();

		if (system.indexOf("linux") > -1) {
			ret = OS_LINUX;
		}
		else if (system.indexOf("windows") > -1) {
			ret = OS_WINDOWS;
		}
		else if (system.indexOf("mac os x") > -1) {
			ret = OS_OSX;
		}
		

		return ret;

	}
	
	/**
	 * 
	 * @return A version of operational system windows.<BR>
	 */
	public static int getWindowsVersion() {

		int ret = -1;

		final String system = System.getProperty("os.name").toLowerCase();

		if (system.indexOf("windows") > -1) {
		
			if (System.getProperty("os.name").indexOf("xp") >-1 ) {
				ret = OS_VERSION_WINDOWS_XP; 
			}
			else if (System.getProperty("os.name").indexOf("vista") >-1 ) {
				ret = OS_VERSION_WINDOWS_VISTA; 
			}
			else if (System.getProperty("os.name").indexOf("7") >-1 ) {
				ret = OS_VERSION_WINDOWS_SEVEN; 
			}
			else if (System.getProperty("os.name").indexOf("8") >-1 ) {
				ret = OS_VERSION_WINDOWS_EIGHT; 
			}
		} 

		return ret;

	}

	public static java.util.Date getClassDateCompilation(Class<?> clazz) throws IOException {
		String className = clazz.getName();
		className = className.replaceAll("\\.", "/");
		className = "/" + className + ".class";
		URL url = Class.class.getResource(className);
		URLConnection urlConnection = url.openConnection();
		java.util.Date lastModified = new java.util.Date(urlConnection.getLastModified());
		return lastModified;
	}

	public static String getVersionSis(Class<?> clazz) {
		String versao = "";
		try {
			//JOptionPane.showMessageDialog(null, "Tentando carregar versão");
			
			URL urlToJar = clazz.getProtectionDomain().getCodeSource().getLocation(); // some HTTP URL
			//System.out.println(clazz.getProtectionDomain().getCodeSource().getLocation());
			//JOptionPane.showMessageDialog(null, "carregou URL "+clazz.getProtectionDomain().getCodeSource().getLocation());
			URL jarUrl = new URL(urlToJar, "jar:" + urlToJar + "!/");
			//JOptionPane.showMessageDialog(null, "carregou URL completa");
			JarURLConnection jconn = (JarURLConnection)jarUrl.openConnection();
			Manifest manifest = jconn.getManifest();
			
			//File file = new File(clazz.getProtectionDomain().getCodeSource().getLocation().toURI());
			//JOptionPane.showMessageDialog(null, "jar:"+clazz.getProtectionDomain().getCodeSource().getLocation().toString());
			//FileInputStream fis = new FileInputStream(clazz.getProtectionDomain().getCodeSource().getLocation().toString());
			//JarInputStream jis = new JarInputStream(fis);
			
			//JarFile jarfile = new JarFile(clazz.getProtectionDomain().getCodeSource().getLocation().toString());
			//JOptionPane.showMessageDialog(null, "Criou file");
			//System.out.println("Criou o jarFile");
			//Manifest manifest = jarfile.getManifest();
			//System.out.println("Carregou o manifest");
			Attributes att = manifest.getMainAttributes();
			//System.out.println("Carregou os atributos");
			versao = att.getValue("Signature-Version");
		}
		catch (Exception e) {
			//JOptionPane.showMessageDialog(null, "Erro: \n"+e.getMessage());
			//e.printStackTrace();
			versao = "Indefinida";
		}

		return versao;

	}

	public static String getVersionDB(DbConnection con) {

		String ret = "Indefinida";
		StringBuilder sql = new StringBuilder();
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {

			sql.append("select versao from sgretversao");

			ps = con.prepareStatement(sql.toString());

			rs = ps.executeQuery();

			if (rs.next()) {
				ret = rs.getString("versao");
			}

			ps.close();
			rs.close();
			con.commit();

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String getTxtFile(URL url) {

		String ret = "";
		int size = 0;
		char c = ( char ) 0;

		try {
			System.out.println("carregando arquivo: " + url.toString());
			File fArq = new File(url.getFile());
			FileReader frArq = new FileReader(fArq);

			try {

				size = ( int ) fArq.length();

				for (int i = 0; i < size; i++) {
					c = ( char ) frArq.read();
					ret += c;
				}
			}
			catch (IOException err) {
				err.printStackTrace();
				System.exit(0);
			}
		}
		catch (FileNotFoundException err) {
			err.printStackTrace();
			// System.exit( 0 );
		}
		return ret;
	}

	public static String getTxtFile(String dir, String url) {

		String ret = "";
		int size = 0;
		char c = ( char ) 0;

		try {
			System.out.println("carregando arquivo: " + url.toString());
			File fArq = new File(dir + url);
			FileReader frArq = new FileReader(fArq);

			try {

				size = ( int ) fArq.length();

				for (int i = 0; i < size; i++) {
					c = ( char ) frArq.read();
					ret += c;
				}
			}
			catch (IOException err) {
				err.printStackTrace();
				System.exit(0);
			}
		}
		catch (FileNotFoundException err) {
			err.printStackTrace();
			// System.exit( 0 );
		}
		return ret;
	}

	public static Vector<String> getIniFile(File fileini) {

		Vector<String> vRetorno = new Vector<String>();
		String sTemp = "";
		int iTam = 0;
		char c = ( char ) 0;
		try {
			FileReader frArq = new FileReader(fileini);
			try {
				iTam = ( int ) fileini.length();
				for (int i = 0; i < iTam; i++) {
					c = ( char ) frArq.read();
					if (c == ( char ) 10) {
						vRetorno.addElement(sTemp);
						sTemp = "";
					}
					else if (c == ( char ) 13) {
						if (i == iTam) {
							vRetorno.addElement(sTemp);
							sTemp = "";
						}
						else {
							c = ( char ) frArq.read();
							i++;
							if (c == ( char ) 10) {
								vRetorno.addElement(sTemp);
								sTemp = "";
							}
							else {
								vRetorno.addElement(sTemp);
								sTemp = "";
								sTemp += c;
							}
						}
					}
					else {
						sTemp += c;
					}
				}
			}
			catch (IOException err) {
				JOptionPane.showMessageDialog(null, "Erro ao carregar arquivo de configuração!\nArquivo: "+fileini.getAbsolutePath()+"\n"+err.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}
		catch (FileNotFoundException err) {
			JOptionPane.showMessageDialog(null, "Erro ao carregar arquivo de configuração!\nArquivo: "+ fileini.getAbsolutePath() + "\n" + err.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		return vRetorno;
	}

	public static boolean zip(Vector<File> files, String zipFile) {
		boolean result = false;
		//String encoding = "UTF-8";
		File tmp = null;
		try {
			File filezip = new File(zipFile);
			if (filezip.exists()) {
				filezip.delete();
			}
			FileOutputStream fout = new FileOutputStream(zipFile);
			ZipOutputStream zout = new ZipOutputStream(fout);
			for (File file: files ) {
				tmp = file;
				zip(zout, file, file.getName());
			}
			zout.close();
			fout.close();
			result = true;
		} catch (IOException e) {
			if (tmp!=null) {
				System.out.println("Erro compactando o arquivo "+tmp.getAbsolutePath());
			}
			e.printStackTrace();
		} 

		return result;
	}

	public static boolean zip(File file, String zipFileName, String srcFileName ) {
		boolean result = false;
		//String encoding = "UTF-8";
		try {
			if (srcFileName==null) {
				srcFileName = file.getName();
			}
			FileOutputStream fout = new FileOutputStream(zipFileName);
			ZipOutputStream zout = new ZipOutputStream(fout);
			zip(zout, file, srcFileName);
			zout.close();
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		} 

		return result;
	}

	public static void zip(ZipOutputStream zout, File file, String filename ) throws IOException {
		FileInputStream fin = new FileInputStream(file);
		zout.putNextEntry(new ZipEntry(filename));
		@SuppressWarnings("unused")
		int length=0;
		byte[] buffer = new byte[1];
		while ((length = fin.read(buffer)) > 0) {
			zout.write(buffer);
		}
		zout.flush();
		zout.closeEntry();
		fin.close();
	}
	
	public static Vector<File> unzip(String dir, File zip) {

		Vector<File> ret = new Vector<File>();
		final int BUFFER = 2048;

		try {

			BufferedOutputStream dest = null;
			BufferedInputStream is = null;

			ZipEntry entry;
			ZipFile zipfile = new ZipFile(zip);
			Enumeration<?> e = zipfile.entries();

			while (e.hasMoreElements()) {

				entry = ( ZipEntry ) e.nextElement();
				System.out.println("Extracting: " + entry);

				is = new BufferedInputStream(zipfile.getInputStream(entry));

				int count;

				byte data[] = new byte[BUFFER];

				FileOutputStream fos = new FileOutputStream(dir+entry.getName());
				dest = new BufferedOutputStream(fos, BUFFER);

				while (( count = is.read(data, 0, BUFFER) ) != -1) {
					dest.write(data, 0, count);
				}

				ret.add(new File(entry.getName()));

				dest.flush();
				dest.close();
				is.close();

			}
			zipfile.close();

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static File writeFileString(String sFileName, String content) {

		File ret = null;

		try {

			ret = new File(sFileName);

			ret.createNewFile();

			FileWriter fw = new FileWriter(ret);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write(content);

			bw.flush();
			bw.close();

		}
		catch (IOException ioError) {
			ioError.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return ret;

	}

}
