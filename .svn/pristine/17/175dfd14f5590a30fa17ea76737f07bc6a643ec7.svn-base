/**
	TextHandler plugin
	Develop by : Jhonatan S. Souza
	
	Tetrasoft(C)(R) 2015 all rights reserved

*/
var textHandler=function(t){return this.data=t,this.replaceAndRemove=function(){return"Not implemented yet!!!"},this.replaceAll=function(t,a){for(var e="",r=0;r<this.data.length;r++)e+=this.data.charAt(r)==t?this.data.substring(r,r+1).replace(t,a):this.data.charAt(r);return e},this.removeHtmltags=function(){for(var t,a,e="",r=!0,h=!0,i=0;i<this.data.length;i++)"<"==this.data.charAt(i)?(t=i,r=!1,h=!1):">"==this.data.charAt(i)&&(a=i,r=!0,h=!0),r&&h&&(e+=this.data.charAt(i).replace(">",""));return e},this.removeTagsLeastSelected=function(t){for(var a,e,r="",h=!0,i=!0,n=(t.length,0);n<this.data.length;n++)"<"==this.data.charAt(n)?(a=n,h=!1,i=!1):">"==this.data.charAt(n)&&(e=n,h=!0,i=!0),h&&i&&(r+=this.data.charAt(n).replace(">",""));return r},this};


