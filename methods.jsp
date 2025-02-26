<%@page import="java.util.Date"%>
<%@page import="javax.swing.text.MaskFormatter"%>
<%@page import="java.text.SimpleDateFormat"%>
<%!
public Object returnNotNull(Object x){
	try{
		if(x != null){
			return x;
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
} 

public String returnNotNull(String x){
	try{
		if(x != null){
			return x;
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return "";
} 

public String returnNotNull(Double x){
	try{
		if(x == 0.00 || x == .00){
		return "";
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return x.toString();
} 
public String toCpf(Object y){
	if(y == null) return "";
	try{
		MaskFormatter mf = new MaskFormatter("AAA.AAA.AAA-AA");
		mf.setValueContainsLiteralCharacters(false);
		if(y.toString().length() == 11){
			return mf.valueToString(y.toString());
		}
	}catch(Exception e){
	}
	return y.toString();
}
public String toRg(Object y){
	if(y == null) return "";
	try{
		MaskFormatter mf = new MaskFormatter("AA.AAA.AAA-A");
		mf.setValueContainsLiteralCharacters(false);
		if(y.toString().length() == 9){
			return mf.valueToString(y.toString());
		}
	}catch(Exception e){
	}
	return y.toString();
}
%>