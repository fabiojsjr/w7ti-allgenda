package com.technique.engine.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.tools.shell.Global;

public class SJCL {

	public String decrypt( String pass ) throws Exception {
		Global global = new Global();
		Context context  = createAndInitializeContext( global );
		Scriptable scope = context.initStandardObjects( global );

		URL url = new URL("https://github.com/bitwiseshiftleft/sjcl/raw/version-0.8/sjcl.js");
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		compileAndExec(in, "classpath:" + url.toString(), context, scope);
		in.close();
		
		exec("var result = sjcl.decrypt('password', '" + pass + "')", "start", context,scope);
		Object result = scope.get("result", scope);
		String json = "";
		if (result != Scriptable.NOT_FOUND) {
			json =  Context.toString(result);
			System.out.println(json);
		}
		
		return json; 
	}
	public static void exec(String script, String name, Context context, Scriptable scope) {
		context.compileString(script, name, 1, null).exec(context,scope);
	}
	public static void compileAndExec(Reader in, String name, Context rhinoContext, Scriptable scope) throws IOException {
		rhinoContext.compileReader(in, name, 1, null).exec(rhinoContext,scope);
	}

	private Context createAndInitializeContext(Global global) {
		Context context = ContextFactory.getGlobal().enterContext();
		global.init(context);
		context.setOptimizationLevel(-1);
		context.setLanguageVersion(Context.VERSION_1_5);
		return context;
	}
	
	public static void main(String[] args) {
		try {
			new SJCL().decrypt( "{\"iv\":\"GFQpHNMvo1NHgfkqg551HQ\",\"v\":1,\"iter\":1000,\"ks\":128,\"ts\":64,\"mode\":\"ccm\",\"adata\":\"\",\"cipher\":\"aes\",\"salt\":\"GGQ433Ernc8\",\"ct\":\"dekp3sLkf2PzEMlip7U\"}" );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
