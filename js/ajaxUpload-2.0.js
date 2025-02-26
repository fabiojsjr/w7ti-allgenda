function replaceAllUp(valor, from, to) {
	while (valor.indexOf(from) > 0) {
		valor = valor.replace(from, to);
	}
	return valor;
}

// function ajaxUploadThis(id, extra) {
function ajaxUploadThis(callback, extra, element) {

	var progressBar = '<div class="progress progress-striped active"><div style="width: 0%" class="bar"  id="progressBar" ></div></div>';
	// var progressBar = '<progress max="100" value="" id="progressBar">
	// </progress >';
	if ($("#progressBar").length <= 0) {
		element.after(progressBar);
	}

	var idq = new String((Math.random() + "").replace('.', '') + "");

	var va = "";
	var vt = "";
	var ve = "";

	try {
		va = formX.getAttributeNode("action").value;
	} catch (e) {
		va = "/allgenda/web";
	}
	try {
		vt = formX.getAttributeNode("target").value;
	} catch (e) {
		vt = "";
	}
	try {
		ve = formX.getAttributeNode("enctype").value;
	} catch (e) {
		ve = "application/x-www-form-urlencoded";
	}

	if (extra != "undefined") {
		extra = "&extra=" + extra;
	} else {
		extra = "";
	}

	formX.getAttributeNode("action").value = "externalAjaxUpload?ajaxUploadPath="
			+ formX.ajaxUploadPath.value
			+ "&ajaxUploadRenameTo="
			+ formX.ajaxUploadRenameTo.value
			+ "&ajaxUploadExcecao="
			+ formX.ajaxUploadExcecao.value
			+ "&idq="
			+ idq
			+ "&AjaxRequestUniqueId="
			+ (Math.random() + "").replace('.', '')
			+ extra;

	try { // InternetExplorer
		formX.getAttributeNode("target").value = "ifajaxUpload";
		formX.getAttributeNode("enctype").value = "multipart/form-data";
	} catch (e) { // Chrome
		formX.target = "ifajaxUpload";
		formX.enctype = "multipart/form-data";
	}
	
	formX.acceptCharset = "UTF-8";

	formX.submit();

	formX.getAttributeNode("action").value = va;
	try { // InternetExplorer
		formX.getAttributeNode("target").value = vt;
		formX.getAttributeNode("enctype").value = ve;
	} catch (e) { // Chrome

	}

	// document.getElementById("ajaxUploadProg"+id).innerHTML =
	// "<br/>Processando...";

	ajaxTimer(idq);
	// ajaxTimer(id,idq);

}

function ajaxTimer(idarquivo) {
	var req = null;
	if (window.XMLHttpRequest) {
		req = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		req = new ActiveXObject("Microsoft.XMLHTTP");
	}
	if (req == null) {
		alert('Sem AJAX');
	}

	var idq = new String(idarquivo);
	req.onreadystatechange = function() {
		if (req.readyState == 4) {
			var strPercent = new Number(0);
			if (req.status == 200) {

				strPercent = new Number((req.responseText.substring(
						req.responseText.lastIndexOf('*') + 1, req.responseText
								.lastIndexOf(':'))) * 1);
				var strKs = new String(req.responseText.substring(
						req.responseText.lastIndexOf(':') + 1, req.responseText
								.lastIndexOf('?')));
				$("#progressBar").css('width', strPercent + "%");
			}
			if (strPercent != 100) {
				setTimeout("ajaxTimer('" + idq + "')", 1000);
			} else {
				if (formX.ajaxUploadExcecao.value == 6) {// documento
					$.get("tradutor.jsp?label=UPLOAD_COMPLETO", function(data) {
						parent.infoAlert("", data, function() {
							document.location.href = document.location.href;
						});
					});
				}
			}
		}
	};

	try {
		var url = new String("externalAjaxUpload?AjaxRequestUniqueId="
				+ (Math.random() + "").replace('.', '') + "&idq=" + idarquivo
				+ "&ajaxUploadPath=" + formX.ajaxUploadPath.value
				+ "&ajaxUploadRenameTo=" + formX.ajaxUploadRenameTo.value
				+ "&ajaxUploadExcecao=" + formX.ajaxUploadExcecao.value);
		req.open("GET", url, true);
		req.setRequestHeader('Content-type', 'charset=UTF-8');
		req.send(url);
	} catch (e) {
		// alert("Erro ao enviar arquivo. Entre em contato com o administrador
		// do sistema");
	}

}

function trim(str) {
	return str.replace(/^\s+|\s+$/g, "");
}
