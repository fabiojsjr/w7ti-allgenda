function replaceAllUp(valor,from,to) {
	while (valor.indexOf(from) > 0) {
		valor = valor.replace(from,to);
	}
	return valor;
}

function ajaxUploadThis(id, extra) {
	var idq = new String((Math.random()+"").replace('.','')+"");
	
	var va = "";
	var vt = "";
	var ve = "";
	
	try {
		va = formX.getAttributeNode("action").value;
	} catch(e) {
		va = "/allgenda/web";
	}
	try {
		vt = formX.getAttributeNode("target").value;
	} catch(e) {
		vt = "";
	}
	try {
		ve = formX.getAttributeNode("enctype").value;
	} catch(e) {
		ve = "application/x-www-form-urlencoded";
	}
	
	if( extra != "undefined" ) {
		extra = "&extra=" + extra;
	} else {
		extra = "";
	}
	
	var upDocs = "";
	if(formX.uploadDocumentos){
		upDocs = formX.uploadDocumentos.value;
	}
	
	formX.getAttributeNode("action").value="externalAjaxUpload?uploadDocumentos="+upDocs+"&ajaxUploadPath=" + formX.ajaxUploadPath.value + "&ajaxUploadRenameTo=" + formX.ajaxUploadRenameTo.value + "&ajaxUploadExcecao=" + formX.ajaxUploadExcecao.value + "&idq=" + idq + "&AjaxRequestUniqueId=" + (Math.random()+"").replace('.','') + extra;
	
	try { // InternetExplorer
		formX.getAttributeNode("target").value="ifajaxUpload";
		formX.getAttributeNode("enctype").value="multipart/form-data";
	} catch(e) { // Chrome 
		formX.target="ifajaxUpload";
		formX.enctype="multipart/form-data";
	}
	
	formX.submit();

	formX.getAttributeNode("action").value=va;
	try { // InternetExplorer
		formX.getAttributeNode("target").value=vt;
		formX.getAttributeNode("enctype").value=ve;
	} catch(e) { // Chrome
		
	}

//	document.getElementById("ajaxUploadProg"+id).innerHTML = "<br/>Processando...";

	ajaxTimer(id,idq);
}



function ajaxTimer(id,idarquivo) {
	var req = null;
	if (window.XMLHttpRequest) {
		req = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		req = new ActiveXObject("Microsoft.XMLHTTP");
	}
	if (req==null) {
		alert('Sem AJAX');
	}
	
	var idf = new Number(id);
	var idq = new String(idarquivo);
	req.onreadystatechange=function(){
		if (req.readyState == 4) {
			var strPercent= new Number(0);
			if (req.status == 200) {

				strPercent= new Number( (req.responseText.substring( req.responseText.lastIndexOf('*')+1, req.responseText.lastIndexOf(':') ))*1 );
				var strKs= new String(req.responseText.substring( req.responseText.lastIndexOf(':')+1, req.responseText.lastIndexOf('?') ));

				document.getElementById("ajaxUploadProg"+idf).innerHTML =
				'<div style="width:'+strPercent+'%;height:20px;position:absolute;border:1px #3399FF solid;background:#3399FF">&nbsp;</div>'+
				'<div style="width:200px;height:20px;position:absolute;border:1px #3399FF solid">'+
					'<table cellpadding="0" cellspacing="0" border="0" align="center">'+
					'<tr>'+
						'<td align="right" class="texto">'+
							strPercent +
						'%'+
						'</td>'+
						'<td>'+
							'&nbsp;'+
						'</td>'+
						'<td align="left" class="texto">'+
							strKs +
						' kbyte/s'+
						'</td>'+
					'</tr>'+
					'</table>'+
				'</div>'+
				'<br/>'
				;
			}
			if (strPercent != 100) {
				setTimeout("ajaxTimer("+ idf +", '"+ idq +"')",1000);
				
			} else {
				document.getElementById("ajaxUploadProg"+idf).innerHTML = (formX.uploadDocumentos.value == 1 || formX.uploadDocumentos.value == 3) ? "" : "<br/>";
				
				if(formX.ajaxUploadExcecao.value == 6){//documento
					
					var retorno = req.responseText;
					var idOS    = retorno.substring( retorno.indexOf("/os")+4, retorno.lastIndexOf("/") );
					
					parent.saveOSDocumento();
					//parent.abre('osDocDiv', 'osDocumento.jsp?idOS=' + idOS);
					
					/*
					$.get("tradutor.jsp?label=UPLOAD_COMPLETO", function(data){
						parent.infoAlert("",data,function(){} );
					});
					*/
				}
				
				if( formX.ajaxUploadExcecao.value == 5 ) { // CNAB
					document.getElementById("ajaxUploadProg"+idf).innerHTML = "Aguarde pelo processamento do arquivo. Seré enviado um resumo em seu email sobre o resultado da operaééo dentro de instantes.";
					// alert("Aguarde pelo processamento do arquivo. Seré enviado um resumo em seu email sobre o resultado da operaééo dentro de instantes.");
				}
				
				if (req.responseText.lastIndexOf('[f]') > 0) {
					document.getElementById("ajaxUploadProg"+idf).innerHTML += req.responseText.substring( req.responseText.lastIndexOf('[f]')+3 );
					
				} else {
					var caminho = req.responseText.substring( req.responseText.lastIndexOf('?')+1 );
					var ext = trim( caminho.substring( caminho.indexOf(".")+1, caminho.length ).toLowerCase() );
					var fileName = req.responseText.substring(req.responseText.lastIndexOf('/')+1);
					
					if(formX.uploadDocumentos.value == 1 ){
						if(!document.getElementById('ajaxUploadResult_'+($.trim(fileName)))){
							document.getElementById('uploadedFiles').innerHTML +="<div><div id='ajaxUploadResult_"+$.trim(fileName)+"' style='margin: 0 0 5px 0; padding: 2px; border-radius: 5px; width: 78%; float: left;'>" +
							"<a target='_blank' href='"+formX.ajaxUploadPath.value+fileName+"'>"+ 
							fileName + "</a></div><div style='width: 20%; float: left;' >" +
							"<img src='images/task_remove.png' style='cursor: pointer' onclick=\"ajaxDeleteFile(this,'"+$.trim(fileName)+"','"+formX.mensagemDeletar.value+"')\"/></div></div><div class='clear'></div>";
						}
						
					}else if(formX.uploadDocumentos.value == 2){
						window.location.reload(true);
						
					}else if (formX.uploadDocumentos.value == 3){
						if( ext == "xls" || ext == "xlsx") {
							if(!document.getElementById('ajaxUploadResult_'+($.trim(fileName)))){
								abre('miolo', 'adminFinanceiroBaixaBoleto.jsp?msg=ARQUIVO_SUCESSO');
							}
						}
						
					}else if (formX.uploadDocumentos.value == 4){
						$.get('adminProdutosImagem.jsp?idProduto='+document.getElementById("uploadDocumentoIdProduto").value+'&nc='+new Date().getTime(), function(data) {$.fancybox(data);});
						
					}else if (formX.uploadDocumentos.value == 5){
						if( ext == "jpg" || ext == "jpeg" || ext == "gif" || ext == "png" || ext == "bmp" ) {
							document.getElementById("ajaxUploadProg"+idf).innerHTML += "" +
							"<div id='ajaxUploadResult' style='margin:0 0 15px 0; padding:2px; border:1px solid #ccc; border-radius:5px; width:100px;'>" +
							"	<a target='_blank' href='"+ caminho +"'>" +
							"		<img id='ajaxUploadFile' src='"+ caminho + "' width='600' height='400' border='0'/>" +
							"	</a>" +
							"</div>";
						} 	
						
					} else{
							if( ext == "jpg" || ext == "jpeg" || ext == "gif" || ext == "png" || ext == "bmp" ) {
									document.getElementById("ajaxUploadProg"+idf).innerHTML += "" +
									"<div id='ajaxUploadResult' style='margin:0 0 15px 0; padding:2px; border:1px solid #ccc; border-radius:5px; width:100px;'>" +
									"	<a target='_blank' href='"+ caminho +"'>" +
									"		<img id='ajaxUploadFile' src='"+ caminho + "' width='100' border='0'/>" +
									"	</a>" +
									"</div>";
							} else {
								document.getElementById("ajaxUploadProg"+idf).innerHTML += "" +
								"<div id='ajaxUploadResult' style='margin:0 0 15px 0; padding:2px; border-radius:5px;'>" +
								"	<a target='_blank' href='"+ caminho +"'>"
								+req.responseText.substring( req.responseText.lastIndexOf('/')+1 )+"</a>" +
								"</div>";
							}
							
							document.getElementById("ajaxUploadProg"+idf).innerHTML += "<input name='arquivo1' type='file' onchange='ajaxUploadThis(1);' style='border: 0px; display: none'  />";
					}
				}

				var diva = new String("ajaxUpload"+idf);
				var ida = new Number(idf+1);
				
				try {
					document.getElementById(diva).innerHTML = replaceAllUp(document.getElementById("ajaxUploadBk").innerHTML,'0',ida); 
				} catch(e){
				}
			}
		}
	};

	try {
		var url = new String("externalAjaxUpload?AjaxRequestUniqueId=" + (Math.random()+"").replace('.','') + "&idq=" + idarquivo + "&ajaxUploadPath=" + formX.ajaxUploadPath.value + "&ajaxUploadRenameTo=" + formX.ajaxUploadRenameTo.value + "&ajaxUploadExcecao=" + formX.ajaxUploadExcecao.value );
		req.open("GET", url, true);
		req.setRequestHeader('Content-type', 'charset=UTF-8');
		req.send(url);
	} catch(e) {
		// alert("Erro ao enviar arquivo. Entre em contato com o administrador do sistema");
	}
	
}

function trim(str){
	return str.replace(/^\s+|\s+$/g,"");
}

function ajaxDeleteFile(div_id, name, msg, tipo){
	if(confirm(msg)){
		name = name.replace(" ", "|");
		$("#erro").load('deleteDocument.jsp?fileName='+name+'&tipo='+tipo, function(){
			if(!(tipo == "deletaProduto")){
				$(div_id).parent().parent().remove();
				$("#erro").show();
				setTimeout(function(){$('#erro').hide();}, 3000);
			}
		});		
	}
}
