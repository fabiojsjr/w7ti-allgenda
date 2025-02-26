function setWizard(element, onLeave, onFinish, allSteps, selected){
	$(element).smartWizard({
		selected: selected,
		keyNavigation: false,
		onFinish: function(obj){
			$("body").addClass("cursorBusy");
			onFinish(obj);
		},
		onLeaveStep: onLeave,
	    labelNext:'Pr\u00F3ximo', // label for Next button
	    labelPrevious:'Anterior', // label for Previous button
	    labelFinish:'Finalizar',  // label for Finish button
	    enableAllSteps: true,
	    fixHeight: true
	});
}

var numIdUsuario = "1234567890,.";
function valores(e){
	var chr = String.fromCharCode(e.which); 
	if(numIdUsuario.indexOf(chr) < 0)	return false;
}

function moeda(z) {
	v = z.value;
	v = v.replace(/\D/g, ""); // permite digitar apenas números
	v = v.replace(/[0-9]{12}/, "inválido"); // limita pra máximo
												// 999.999.999,99
	v = v.replace(/(\d{1})(\d{8})$/, "$1.$2"); // coloca ponto antes dos
												// últimos 8 digitos
	v = v.replace(/(\d{1})(\d{5})$/, "$1.$2"); // coloca ponto antes dos
												// últimos 5 digitos
	v = v.replace(/(\d{1})(\d{1,2})$/, "$1,$2"); // coloca virgula antes dos
													// últimos 2 digitos
	z.value = v;
}

function ieIsNaN(x){
	try{
		return Number.isNaN(x);
	}catch(e){
		return x !== x;
	}
}

function moedaBr(valor){
	
	if(valor === undefined ) return "0,00";
	var isNegative = false;
	try{
		valor = Number(valor);
		if(ieIsNaN(valor)){
			return "0,00";
		}
		if(valor < 0){
			isNegative = true;
			valor = valor * -1;
		}
		valor = valor.toFixed(2);
		if(valor.length > 23){
			return "0,00";
		}
	}catch(err){
		return "0,00";
	}
	valor = valor.replace(/\./g, ",");
	var parteInteira = valor.substring(0, valor.indexOf(","));
	var parteDecimal = valor.substring(parteInteira.length, valor.length);
	var tamanhoParteInteira = parteInteira.length <= 0 ? 0 : parteInteira.length;
	var i = 1;
	var resposta = "";
	if(tamanhoParteInteira / 3 > 1){
		while(i < tamanhoParteInteira / 3 ){
			var texto =  parteInteira.substring(parteInteira.length - 3*i, parteInteira.length - 3*(i-1))
			resposta = "."+texto + resposta; 
			i++;
		}
	}
	return (isNegative ? "-" : "") + parteInteira.substring(0, parteInteira.length - (3*(i -1)))+resposta+parteDecimal;
}

function moedaBrToNumber(valor){
	valor = $.trim(valor);
	var x = Number(valor.replace(/\./g, "").replace(/\,/g, "."));
	if(ieIsNaN(x)){
		return 0;
	}
	return x;
	
}

function confirmBox(title, msg, callback){
	try{
		$("body").removeClass("cursorBusy");
	}catch(err){
		
	}
	jQuery.alerts.okButton = 'Sim';
	jQuery.alerts.cancelButton = 'N&atilde;o';
    jQuery.alerts.dialogClass = 'alert-warning ui-draggable';
    if(title == ""){
		$.get("tradutor.jsp?label=MENSAGEM", function(data){
			jConfirm($.trim(msg), $.trim(data), function(x){
				jQuery.alerts.dialogClass = null;
				callback(x);
			
			});
		});
    }
    jConfirm(msg, title, function(x){
			jQuery.alerts.dialogClass = null;
			callback(x);
	});
}

function salvar(command, action, div) {
//	var htmldiv = $("#" + div).html();

	formX.command.value = command;
	formX.action.value = action;
	
	$("#" + div).load(createQueryString(formX), function() {
		// alert("ok");
	});
}
function deleteCategoria(id, adminTable){
	jConfirm('Deseja remover essa categoria?', 'Atencao!', function(response) {
		if(response){
			$.ajax({
				type: 'POST',
				url: 'web?sid=Allgenda&id=&command=cliente&action=deleteCategoria&idCategoria='+id,
				dataType: 'html',
				contentType: "application/x-www-form-urlencoded;charset=UTF-8",
				processData: false,
				success: function(data){
					$('#erro').html(data);
					adminTable.fnDraw();
					
				}
			});
		}
	});
}
function deleteCofins(id, adminTable){
	jConfirm('Deseja remover esse cofins?', 'Atencao!', function(response) {
		if(response){
			$.ajax({
				type: 'POST',
				url: 'web?sid=Allgenda&id=&command=cofins&action=deleteCofins&idCofins='+id,
				dataType: 'html',
				contentType: "application/x-www-form-urlencoded;charset=UTF-8",
				processData: false,
				success: function(data){
					$('#erro').html(data);
					adminTable.fnDraw();
					
				}
			});
		}
	});
}
function deleteConta(id, adminTable){
	jConfirm('Deseja remover esse conta?', 'Atencao!', function(response) {
		if(response){
			$.ajax({
				type: 'POST',
				url: 'web?sid=Allgenda&id=&command=conta&action=deleteConta&idConta='+id,
				dataType: 'html',
				contentType: "application/x-www-form-urlencoded;charset=UTF-8",
				processData: false,
				success: function(data){
					$('#erro').html(data);
					adminTable.fnDraw();
					
				}
			});
		}
	});
}
function deleteCliente(id, adminTable){
	jConfirm('Deseja remover esse cliente?', 'Atencao!', function(response) {
		if(response){
			$.ajax({
				type: 'POST',
				url: 'web?sid=Allgenda&id=&command=cliente&action=deleteCliente&idCliente='+id,
				dataType: 'html',
				contentType: "application/x-www-form-urlencoded;charset=UTF-8",
				processData: false,
				success: function(data){
					$('#erro').html(data);
					adminTable.fnDraw();
					
				}
			});
		}
	});
}
function deleteCsosn(id, adminTable){
	jConfirm('Deseja remover esse Csosn?', 'Atencao!', function(response) {
		if(response){
			$.ajax({
				type: 'POST',
				url: 'web?sid=Allgenda&id=&command=csosn&action=deleteCsosn&idCsosn='+id,
				dataType: 'html',
				contentType: "application/x-www-form-urlencoded;charset=UTF-8",
				processData: false,
				success: function(data){
					$('#erro').html(data);
					adminTable.fnDraw();
					
				}
			});
		}
	});
}
function deleteCPagamento(id, adminTable){
	jConfirm('Deseja remover esse pagamento?', 'Atencao!', function(response) {
		if(response){
			$.ajax({
				type: 'POST',
				url: 'web?sid=Allgenda&id=&command=pagamento&action=deleteCPagamento&idPagamento='+id,
				dataType: 'html',
				contentType: "application/x-www-form-urlencoded;charset=UTF-8",
				processData: false,
				success: function(data){
					$('#erro').html(data);
					adminTable.fnDraw();
					
				}
			});
		}
	});
}

function deleteConsultor(id, adminTable){
	jConfirm('Deseja remover esse Consultor?', 'Atencao!', function(response) {
		if(response){
			$.ajax({
				type: 'POST',
				url: 'web?sid=Allgenda&id=&command=consultor&action=deleteConsultor&idConsultor='+id,
				dataType: 'html',
				contentType: "application/x-www-form-urlencoded;charset=UTF-8",
				processData: false,
				success: function(data){
					$('#erro').html(data);
					adminTable.fnDraw();
					
				}
			});
		}
	});
}

function deleteVisitantes(id, adminTable){
	jConfirm('Deseja remover esse Visitante?', 'Atencao!', function(response) {
		if(response){
			$.ajax({
				type: 'POST',
				url: 'web?sid=Allgenda&id=&command=visitantes&action=deleteVisitantes&idVisitantes='+id,
				dataType: 'html',
				contentType: "application/x-www-form-urlencoded;charset=UTF-8",
				processData: false,
				success: function(data){
					$('#erro').html(data);
					adminTable.fnDraw();
					
				}
			});
		}
	});
}



function ativarcaixa(pagina) {
	$('html, body').animate({
		scrollTop : $("#topo").offset().top
	// div
	}, {
		duration : 700,
		axis : "y"
	}); // tempo em mls para chegar

	ativarc();
	$("#boxc").load(pagina);
}

var lastOn;
function hoverHolder(n) {
	lastOn = "#hoverHolder" + n;
	$("#hoverHolder" + n).toggle(120);
}

function hoverHolderOut(n) {
	$(lastOn).delay(130).toggle("fast");
}

function showMenu(n) {
	var i;
	for (i = 1; i < 10; i++) {
		hideMenu(i);
		$("div#menuItem" + i).css('background', '');
	}

	$("div#menuItem" + n).css('background',
			"url('images/topo_interna_hover.png') 0 0 repeat-x");
	$("div#submenuItem" + n).slideDown(0, function() {
		$("div#submenuList" + n).fadeIn(500);
	});
}

function hideMenu(n) {
	$("div#submenuList" + n).slideUp(500, function() {
		$("div#submenuItem" + n).hide(0);
		$("div#menuItem" + n).css('background', '');
	});
}

function hideBar(n) {
	var i;
	for (i = 1; i <= 4; i++) {
		$("div#hideBar" + i).hide(0);
		$("div#topoIcone" + i).attr("class", "topoIcone");
	}
	$("div#hideBar" + n).show(300);
	$("div#topoIcone" + n).attr("class", "topoIconeS");
}

function ativarc() {
	$('#overlayc').fadeIn('fast', function() {
		$('#boxc').animate({
			'top' : '200px'
		}, 500, 'linear', function() {
			/*
			 * $('html, body').animate({ scrollTop: $('#boxc').offset().top },
			 * 'slow');
			 */
		});
	});
}

function os() {
	$("#socialInfo").hide('slow');
	swapPanel('#labelLogin', '#labelOS');
	swapPanel('#loginInfo', '#osInfo');
}

function osSubmit() {
	formX.command.value = "usuario";
	formX.action.value = "os";
	formX.osMensagem.value = replaceQuebraLinha(formX.osMensagem.value, "<br>");

	if (formX.osLogin.value == "") {
		$.get("tradutor.jsp", {
			label : "PREENCHA_LOGIN_OU_EMAIL"
		}, function(data) {
			alert($.trim(data));
		});
		// alert("Por favor, preencha o seu login ou o email cadastrado");
		return;
	}
	if (!(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/
			.test(formX.osLogin.value))) {
		$.get("tradutor.jsp", {
			label : "DIGITE_EMAIL_VALIDO"
		}, function(data) {
			alert($.trim(data));
		});
		return;
	}

	query = createQueryString(formX);
	$("#osInfo").load("tradutor.jsp", {
		label : "AGUARDE_ENVIANDO_SOLICITACAO"
	});
	// document.getElementById("osInfo").innerHTML = "Por favor, aguarde...
	// estamos enviando sua solicitacao...";
	$("#osInfo").load(query);
}

function login(hist) {
	formX.command.value = "usuario";
	formX.action.value = "login";

	var a = location.href;

	if (hist == 'true') {
		$("#loginResposta").load(createQueryString(formX), function() {
			if (formX.loginOk.value == 'logado')
				location.href = a;
		});
	} else {
		$("#loginResposta").load(createQueryString(formX), function() {
			if (formX.loginOk.value == 'logado')
				location.href = "usuario.jsp";
		});
	}
}

function loginSite(form) {
	form.command.value = "usuario";
	form.action.value = "login";

	$.ajax({
		url: "/allgenda/"+createQueryString(form),
		method: "POST",
		success: function(data){
			$("#loginResposta").html(data);
			if (form.loginOk.value == 'logado')
				location.href = "/allgenda/painel.jsp";
		}
	});

}

function esqueci(form) {
	
	var erro = 	!$(form).valid();
	if (erro) return;
	$.ajax({
		type: 'POST',
		url: "/allgenda/"+createQueryString(form),
		dataType: 'html',
		success: function(data){
			$("#respostaRecuperarSenha").html(data);
		}
	});
		
}

function alterarSenha(form) {
	
	var erro = 	!$(form).valid();
	if (erro) return;
	$.ajax({
		type: 'POST',
		url: createQueryString(form),
		dataType: 'html',
		success: function(data){
			$("#respostaNovaSenha").html(data);
		}
	});
}

function deletarFoto(idFoto) {
	formX.command.value = "produto";
	formX.action.value = "deletarFoto";
	formX.idFoto.value = idFoto;

	var produto = formX.idProduto.value;
	$("#miolo").load(createQueryString(formX), function() {
		abre("miolo", "usuarioCpProdutosList.jsp?idProduto=" + produto);
	});
}

function definirFoto(idFoto) {
	formX.command.value = "produto";
	formX.action.value = "definirFoto";
	formX.idFoto.value = idFoto;

	var produto = formX.idProduto.value;
	$("#miolo").load(createQueryString(formX), function() {
		abre("miolo", "usuarioCpProdutosList.jsp?idProduto=" + produto);
	});
}


function logout() {
	formX.command.value = "usuario";
	formX.action.value = "logout";
	formX.submit();
}

function fbs_click(idProduto) {
	u = location.href;
	t = document.title;
	window
			.open(
					'http://www.facebook.com/sharer.php?u=http:teste.allgenda.org.br/allgenda/usuario.jsp?idProduto="'
							+ idProduto + '"', 'sharer',
					'toolbar=0,status=0,width=626,height=436');
	return false;
}

function blockAppend(id) {
	$("#" + id).addClass(function(index) {
		$("#" + id).removeClass(function() {
			return "input_check";
		});
		return "input_block";
	});
}

function checkAppend(id) {
	$("#" + id).addClass(function() {
		$("#" + id).removeClass(function() {
			return "input_block";
		});
		return "input_check";
	});
}

function block(id) {
	$("#block_" + id).attr("class", "block");
	$("#" + id).attr("class", "input_block");
}

function check(id) {
	$("#block_" + id).attr("class", "check");
	$("#" + id).attr("class", "input_check");
}

function error(id, msg) {
	$("#" + id).load("resposta.jsp?msgClean=" + msg);
	location.href = "#" + id;
}

function swapPanel(from, to) {
	$(from).hide('slow');
	$(to).show('slow');
}

function trocaMenu(id, total, pagina) {
	var i = 1;
	for (i = 1; i <= total; i++) {
		$("#aba" + i).attr("class", "aba");
	}
	$("#" + id).attr("class", "abaAtiva");
	abre("miolo", pagina);
}

function trocaMenuId(id, total, pagina) {
	var idProduto = formX.idProdutoVendedor.value;
	var i = 1;
	for (i = 1; i <= total; i++) {
		$("#aba" + i).attr("class", "aba");
	}
	$("#" + id).attr("class", "abaAtiva");

	if (pagina != undefined) {
		abre("miolo", pagina + "?idProduto=" + idProduto + "&idUsuario="
				+ formX.idUsuario.value);
	}
}

function desativarc(div) {
	$('#boxc').animate({
		'top' : '-1000px'
	}, 500, function() {
		$('#overlayc').fadeOut('fast');
	});
	$('html, body').animate({
		scrollTop : $(div).offset().top - 20
	}, 'slow');
}


function abre(div, pagina) {
	// historico.push(pagina);
	document.getElementById(div).innerHTML = "<center><img alt='' src='images/loaders/loader1.gif'/></center>";

	if (pagina.substring(pagina.lastIndexOf(".") + 1, pagina.length) == "jsp") {
		pagina = pagina + "?no_cache=" + new Date().getTime();
	} else {
		pagina = pagina + "&no_cache=" + new Date().getTime();
	}
	
	$("#" + div).load(
			pagina,
			function() {
				/*
				try {
					$("#" + formX.idSuporte.value).html("<div>" + $("#duvidas").val() + "</div>");
					($("#" + formX.idSuporte.value).parent().parent().children("#data")).html("<span>"
							+ ($.datepicker.formatDate("dd/mm/yy", new Date()))
							+ "</span>");
				} catch (e) {
				}
				*/
				
				try {
					activateFancybox();
				} catch (e) {
				}
				/*
				try {
					activateDatePickerEN();
				} catch (e) {
				}
				try {
					activateDatePicker();
				} catch (e) {
				}
				*/
				try {
					activateSlider();
				} catch (e) {
				}
				try {
					activateTooltip();
				} catch (e) {
				}
				
				if( pagina.indexOf("relatorio.jsp") != -1 ) {
					try {
						activateGrafico();
					} catch (e) {
					}
				}

				if( pagina.indexOf("Enviar.jsp") != -1 || 
					pagina.indexOf("Template.jsp") != -1 ) {
					
					try {
						activateWYSIWYG();
					} catch (e) {
					}
				}
			});
}

function activateWYSIWYG() {
	/*
	tinymce.remove();
	
	tinymce.init({
		  mode : "specific_textareas",
		  editor_selector : "tinymce",
//		  selector: 'textarea',
		  height: 500,
		  theme: 'modern',
		  plugins: [
		    'advlist autolink lists link image charmap print preview hr anchor pagebreak',
		    'searchreplace wordcount visualblocks visualchars code fullscreen',
		    'insertdatetime media nonbreaking save table contextmenu directionality',
		    'emoticons template paste textcolor colorpicker textpattern imagetools'
		  ],
		  toolbar1: 'insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image',
		  toolbar2: 'print preview media | forecolor backcolor emoticons',
		  image_advtab: true,
		  templates: [
		    { title: 'Test template 1', content: 'Test 1' },
		    { title: 'Test template 2', content: 'Test 2' }
		  ],
		  content_css: [
		    '//fast.fonts.net/cssapi/e6dc9b99-64fe-4292-ad98-6974f93cd2a2.css',
		    '//www.tinymce.com/css/codepen.min.css'
		  ]
		 });	
	
//	tinymce.init({ mode : "specific_textareas", editor_selector : "tinymce" });
//	tinymce.init({ selector:'textarea' });
    */

	jQuery('textarea.tinymce').tinymce({

		script_url : 'js/tinymce/tiny_mce.js',
		language : 'pt',

		// General options
		gecko_spellcheck : true,
		theme : "advanced",
		skin : "themepixels",
		width: "100%",
		height: 500,
		plugins : "autolink,lists,pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,inlinepopups,insertdatetime,preview,media,searchreplace,print,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template,advlist",
		inlinepopups_skin: "themepixels",

		// Theme options
		theme_advanced_buttons1 : "bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,outdent,indent,blockquote,formatselect,fontselect,fontsizeselect",
		theme_advanced_buttons2 : "pastetext,pasteword,|,bullist,numlist,|,undo,redo,|,link,unlink,image,help,code,|,preview,|,forecolor,backcolor,removeformat,|,charmap,media,|,fullscreen",
		theme_advanced_buttons3 : "",
		theme_advanced_toolbar_location : "top",
		theme_advanced_toolbar_align : "left",
		theme_advanced_statusbar_location : "bottom",
		theme_advanced_resizing : true,

		
		// Example content CSS (should be your site CSS)
		// content_css : "css/plugins/tinymce.css",

		// Drop lists for link/image/media/template dialogs
		template_external_list_url : "lists/template_list.js",
		external_link_list_url : "lists/link_list.js",
		external_image_list_url : "lists/image_list.js",
		media_external_list_url : "lists/media_list.js",

		// Replace values for the template plugin
		template_replace_values : {
			username : "Some User",
			staffid : "991234"
		}
	});
	
	jQuery('.editornav a').click(function(){
		jQuery('.editornav li.current').removeClass('current');
		jQuery(this).parent().addClass('current');
		if(jQuery(this).hasClass('visual'))
			jQuery('#elm1').tinymce().show();
		else
			jQuery('#elm1').tinymce().hide();
		return false;
	});
}

function activateSlider() {
	$('#slider1').bxSlider();
	$('#slider2').bxSlider();
	$('#slider3').bxSlider();
}

function activateTooltip() {
	//Replaces data-rel attribute to rel.
	//We use data-rel because of w3c validation issue
	jQuery('a[data-rel]').each(function() {
    	jQuery(this).attr('rel', jQuery(this).data('rel'));
	});
        
	// tooltip sample
	if(jQuery('.tooltipsample').length > 0)
		jQuery('.tooltipsample').tooltip({selector: "a[rel=tooltip]"});
		
	jQuery('.popoversample').popover({selector: 'a[rel=popover]', trigger: 'hover'});
}

function activateFancybox() {
	$("a.grouped_elements_login").fancybox({
		'transitionIn' : 'elastic',
		'transitionOut' : 'elastic',
		'autoDimensions' : true,
		'overlayShow' : true,
		'easingIn' : 'easeOutBack',
		'easingOut' : 'easeInBack',
		'border': 0,
//		 onComplete: function() {
//			 $('#fancybox-outer').addClass("transparent80");
//			 $('#fancybox-wrap').addClass("transparent100");
//			 $('#fancybox-content').addClass("no-border");
//		 },
	
	});
	
	$("a.grouped_elements").fancybox();
	/* Using custom settings */

	$("a#inline").fancybox({
		'hideOnContentClick' : true
	});

	/* Apply fancybox to multiple items */
	$("a.grouped_elements").fancybox({
		'transitionIn' : 'elastic',
		'transitionOut' : 'elastic',
		'autoDimensions' : true,
		'overlayShow' : true,
		'easingIn' : 'easeOutBack',
		'easingOut' : 'easeInBack',		
	});
	
	$("a.grouped_elements2").fancybox({
		'width': 875,
		'height': 630
	});
}


function upDown(div) {
	if (document.getElementById(div).style.display == "block") {
		$("#" + div).slideUp("slow");
	} else {
		$("#" + div).slideDown("slow");
	}
}

function hideShow(div) {
	if (document.getElementById(div).style.display == "block") {
		$("#" + div).hide("slow");
	} else {
		$("#" + div).show("slow");
	}
}

function teclaEnter(evt) {
	var key_code = evt.keyCode ? evt.keyCode : evt.charCode ? evt.charCode
			: evt.which ? evt.which : void 0;
	if (key_code == 13) {
		return true;
	} else {
		return false;
	}
}

function showAccount() {
	location.href = "usuario.jsp";
}

function combo(command, action, id, selecionado ){
	formX.command.value = command;
	formX.action.value = action;
	if(id!=null || id!=undefined)
		formX.id.value = id;
	
	var query = createQueryString(formX);
	
	if( selecionado != null ) query += "&selecionado=" + selecionado;
	
	ChamaPaginaArray(query, "combo");
}

/* Configuracao - Dados Pessoais */
function activateDatePicker() {
	$(function() {
		$.datepicker.setDefaults($.datepicker.regional['']);
		$("#from").datepicker($.datepicker.regional['pt-BR']);
		$("#to").datepicker($.datepicker.regional['pt-BR']);
		var dates = $('#from, #to').datepicker(
				{
					defaultDate : "+1w",
					changeMonth : true,
					numberOfMonths : 2,
					onSelect : function(selectedDate) {
						var option = this.id == "from" ? "minDate" : "maxDate";
						var instance = $(this).data("datepicker");
						var date = $.datepicker.parseDate(
								instance.settings.dateFormat
										|| $.datepicker._defaults.dateFormat,
								selectedDate, instance.settings);
						dates.not(this).datepicker("option", option, date);
					}
				});
		$('#from, #to').datepicker( "option", "dateFormat", "dd/mm/yy" );
	});
}

function activateDatePickerEN() {
	$(function() {
		$.datepicker.setDefaults($.datepicker.regional['']);
		$("#from").datepicker($.datepicker.regional['en-US']);
		$("#to").datepicker($.datepicker.regional['en-US']);
		var dates = $('#from, #to').datepicker(
				{
					defaultDate : "+1w",
					changeMonth : true,
					numberOfMonths : 2,
					onSelect : function(selectedDate) {
						var option = this.id == "from" ? "minDate" : "maxDate";
						var instance = $(this).data("datepicker");
						var date = $.datepicker.parseDate(
								instance.settings.dateFormat
										|| $.datepicker._defaults.dateFormat,
								selectedDate, instance.settings);
						dates.not(this).datepicker("option", option, date);
					}
				});
		$('#from, #to').datepicker( "option", "dateFormat", "dd/mm/yy" );
	});
}


function activateDatePickerGeneric(data) {
	$(function() {
		$.datepicker.setDefaults($.datepicker.regional['']);
		$(data).datepicker($.datepicker.regional['pt-BR']);
		var dates = $(data).datepicker(
				{
					defaultDate : "+1w",
					changeMonth : true,
					numberOfMonths : 2,
					onSelect : function(selectedDate) {
						var option = this.id == "from" ? "minDate" : "maxDate";
						var instance = $(this).data("datepicker");
						var date = $.datepicker.parseDate(
								instance.settings.dateFormat
										|| $.datepicker._defaults.dateFormat,
								selectedDate, instance.settings);
						dates.not(this).datepicker("option", option, date);
					}
				});
		$('#from, #to').datepicker( "option", "dateFormat", "dd/mm/yy" );
	});
}

function activateDatePickerSite( id ) {
	$(function() {
		var form = "dd/mm/yy";
		
		$.datepicker.setDefaults( $.datepicker.regional[''] );
		id.datepicker($.datepicker.regional['en-US']);
		var dates = id.datepicker(
				{
					defaultDate : "+1w",
					changeMonth : true,
					numberOfMonths : 2,
					onSelect : function(selectedDate) {
						var option = this.id == "from" ? "minDate" : "maxDate";
						var instance = $(this).data("datepicker");
						var date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
						dates.not(this).datepicker("option", option, date);
					}
				});
		
		id.datepicker( "option", "dateFormat", form );
	});
}


function activateDatePickerID( id, lang ) {
	$(function() {
		$.datepicker.setDefaults($.datepicker.regional['pt-BR']);
		id.datepicker($.datepicker.regional['pt-BR']);
		var dates = id.datepicker(
				{
					defaultDate : "+1w",
					changeMonth : true,
					numberOfMonths : 2,
					onSelect : function(selectedDate) {
						var option = this.id == "from" ? "minDate" : "maxDate";
						var instance = $(this).data("datepicker");
						var date = $.datepicker.parseDate(
								instance.settings.dateFormat || $.datepicker._defaults.dateFormat,
								selectedDate, instance.settings);
						dates.not(this).datepicker("option", option, date);
					}
				});
		id.datepicker( "option", "dateFormat", "dd/mm/yy" );
	});
}


/*
function saveDadosPessoais() {
	formX.command.value = "usuario";
	formX.action.value = "salvarConfigDadosPessoais";

	if(!$(formX).valid()) return;
	$.ajax({
		type: 'POST',
		url: createQueryString(formX),
		dataType: 'html',
        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
        processData: false,
		success: function(data){
			$('#erro').html(data);
		}
	});
}
*/

function saveDadosPessoaisAdmin(form) {
	form.command.value = "usuario";
	form.action.value = "salvarConfigDadosPessoaisAdmin";
	
	if(!$(form).valid()) return;
	
	$.ajax({
		type: 'POST',
		url: createQueryString(form),
		dataType: 'html',
		contentType: "application/x-www-form-urlencoded;charset=UTF-8",
		processData: false,
		success: function(data){
			$('#erro').html(data);			
		}
	});
}


function saveTimeSheet(form) {
	form.command.value = "usuario";
	form.action.value = "salvarTimeSheetDiario";
	
	if(!$(form).valid()) return;
	
	// alert( createQueryString(form) );
	
	if( createQueryString(form).indexOf("idUser_") == -1 ) {
		alert("Selecione pelo menos 1 colaborador.");
		return;
	}
	
	$.ajax({
		type: 'POST',
		url: createQueryString(form),
		dataType: 'html',
		contentType: "application/x-www-form-urlencoded;charset=UTF-8",
		processData: false,
		success: function(data){
			$('#erro').html(data);
		}
	});
}

function timesheetChange() {
	form.command.value = "usuario";
	form.action.value = "timeSheetChange";
	
	if(!$(form).valid()) return;
	
	if( form.idUsuarioTS.value == "" || form.dataTS.value == "" ) {
		return;
	}
	
	$.ajax({
		type: 'POST',
		url: createQueryString(form),
		dataType: 'html',
		contentType: "application/x-www-form-urlencoded;charset=UTF-8",
		processData: false,
		success: function(data){
			$('#tsDetail').html(data);
		}
	});	
}


function saveGeneric(form, c, a ) {
	form.command.value = c;
	form.action.value  = a;

	if(!$(form).valid()) {
		// alert("Preencha todos os campos corretamente. Verifique os itens em vermelho.");
		return false;
	}

//	alert(createQueryString(form));

	$.ajax({
		type: 'POST',
		url: createQueryString(form),
		dataType: 'html',
		contentType: "application/x-www-form-urlencoded;charset=UTF-8",
		processData: false,
		success: function(data){
			var erro = data.trim();
//			console.log(erro);
			$('#erro').html(data);
//			jQuery.fancybox.close();
		}
	});
	
	return true;
}

function savePerfilAdmin(form) {
	form.command.value = "perfil";
	form.action.value = "save";
	
	try {
		if( form.nome.value == "" && form.id.value == "0" ) {
			alert("Preencha o nome!");
			return;
		}
	} catch(e) {
	}
	
	if(!$(form).valid()) return;
	
	$.ajax({
		type: 'POST',
		url: createQueryString(form),
		dataType: 'html',
        contentType: "application/x-www-form-urlencoded;charset=UTF-8",
        processData: false,
		success: function(data){
			$('#erro').html(data);
			
			if($("#popup_ok") != undefined){
				
				setTimeout(function(){
					$("#popup_ok").trigger('click');
					$("#fancybox-close").trigger('click');
				},1000);
			}
		}
	});
}

function showDefaultError(msg) {
	var erro = "ERRO_PADRAO";
	if (msg != undefined)
		erro = msg;

	// $("#defaultError").load("tradutor.jsp?label="+erro);
	ChamaPaginaArray("tradutor.jsp?label=" + erro, "defaultError");
	$("#erro").show("slow");
}

function validaDadosPessoais() {
	if (!(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/
			.test(formX.email.value))) {
		blockAppend("email");
		showDefaultError("INFORME_EMAIL");
		return true;
	} else {
		checkAppend("email");
	}

	if (formX.nome.value == "") {
		blockAppend("nome");
		showDefaultError("INFORME_NOME");
		return true;
	} else {
		checkAppend("nome");
	}

	if (formX.telefone.value == "") {
		blockAppend("telefone");
		showDefaultError("INFORME_TELEFONE");
		return true;
	} else {
		checkAppend("telefone");
	}

	if (formX.celular.value == "") {
		blockAppend("celular");
		showDefaultError("INFORME_CELULAR");
		return true;
	} else {
		checkAppend("celular");
	}

	if (formX.dddTelefone.value == "") {
		blockAppend("dddTelefone");
		showDefaultError("INFORME_DDD");
		return true;
	} else {
		checkAppend("dddTelefone");
	}

	if (formX.dddCelular.value == "") {
		blockAppend("dddCelular");
		showDefaultError("INFORME_DDD");
		return true;
	} else {
		checkAppend("dddCelular");
	}

	if (formX.endereco.value == "") {
		blockAppend("endereco");
		showDefaultError("INFORME_ENDERECO");
		return true;
	} else {
		checkAppend("endereco");
	}

	if (formX.numero.value == "") {
		blockAppend("numero");
		showDefaultError("INFORME_NUMERO");
		return true;
	} else {
		checkAppend("numero");
	}

	if (formX.bairro.value == "") {
		blockAppend("bairro");
		showDefaultError("INFORME_BAIRRO");
		return true;
	} else {
		checkAppend("bairro");
	}


	if (formX.cep.value == "") {
		blockAppend("cep");
		showDefaultError("INFORME_CEP");
		return true;
	} else {
		checkAppend("cep");
	}


	if (formX.senha.value != "" && formX.senha.value != formX.senha2.value) {
		blockAppend("senha");
		blockAppend("senha2");
		showDefaultError("SENHA_IGUAL_CONFIRMACAO");
		return true;
	} else {
		checkAppend("senha");
		checkAppend("senha2");
	}

	if (formX.senha.value != "" && formX.senhaMaster.value != "") {
		if (formX.senha.value == formX.senhaMaster.value) {
			blockAppend("senha");
			blockAppend("senha2");
			blockAppend("senhaMaster");
			blockAppend("senhaMaster2");
			showDefaultError("SENHA_IGUAL_SENHA_MASTER");
			return true;
		} else {
			checkAppend("senha");
			checkAppend("senha2");
			checkAppend("senhaMaster");
			checkAppend("senhaMaster2");
		}
	}

	if (formX.aceitoTermos.checked == false) {
		$("#div_aceitoTermos").attr("class", "check_block");
		showDefaultError("CONCORDE_TERMOS");
		return true;
	} else {
		$("#div_aceitoTermos").attr("class", "");
	}
	return false;
}

/* Configurao - Marketing */

function saveMensagem() {
	formX.command.value = "usuario";
	formX.action.value = "mensagemEnviar";
	if(!$(formX).valid()) return;
	
	$.ajax({
		type: 'POST',
		url: '/allgenda/web?sid=Allgenda&command=usuario&action=mensagemEnviar',
//		dataType: 'html',
		data: { value: createQueryString(formX) },
//	    data: { value: 'cebola preta' },
//		contentType: "application/x-www-form-urlencoded;charset=UTF-8",
//		processData: false,
		success: function(data){
			$('#erro').html($.trim(data));
		}
	});
}

function saveOSDocumento() {
	var formDOC;
	var idOS;
	try {
		if( formOS.nomeDocumento.value == "" ) {}
		formDOC = formOS;
		idOS 	= formDOC.idOS.value;
	} catch(e) {
		formDOC = formAdminCliente;
		idOS 	= formDOC.idQuoteAtiva.value;
	}
	
	$.ajax({
		type: 'POST',
		url: '/allgenda/web?sid=Allgenda&command=os&action=documentoEnviar',
		data: { value: createQueryString(formDOC) },
		success: function(data){
			$('#erro').html($.trim(data));
			abre("osDocDiv", "osDocumento.jsp?idOS=" + idOS);
		}
	});
}

function saveTemplate() {
	formX.command.value = "usuario";
	formX.action.value = "mensagemTemplate";
	if(!$(formX).valid()) return;
	
	$.ajax({
		type: 'POST',
		url: '/allgenda/web?sid=Allgenda&command=usuario&action=mensagemTemplate',
		data: { value: createQueryString(formX) },
		success: function(data){
			$('#erro').html($.trim(data));
		}
	});
}

function saveOS() {
	formX.command.value = "usuario";
	formX.action.value = "duvida";
	if(!$(formX).valid()) return;

	$.ajax({
		type: 'POST',
		url: createQueryString(formX),
		dataType: 'html',
		contentType: "application/x-www-form-urlencoded;charset=UTF-8",
		processData: false,
		success: function(data){
			$('#erro').html($.trim(data));
		}
	});
}



function trocarFoto() {
	if ($("#ajaxUploadFile").attr("src") != undefined) {
		$("#fotoUsuario").attr("src", $("#ajaxUploadFile").attr("src"));
		formX.fotoUsuario.value = $("#ajaxUploadFile").attr("src");
		$.fancybox.close();
	} else {
		$("#boxErro").show();
		$("#boxErro").load("resposta.jsp", {
			label : "ENVIE_FOTO"
		});
	}
}

function inativarUsuario(id, form, table){
	$.get("tradutor.jsp", {
		label : "CONFIRMACAO_DELETAR"
	}, function(data) {
		if (confirm($.trim(data))) {
			form.command.value = "usuario";
			form.action.value = "inativarUsuario";
			
			$.ajax({
				type: 'POST',
				url: createQueryString(form)+"&inativo="+id,
				dataType: 'html',
				contentType: "application/x-www-form-urlencoded;charset=UTF-8",
				processData: false,
				success: function(data){
					$('#erro').html(data);
					table.fnDraw();
					infoAlert("Mensagem", $.trim(data) , function(){});
				}
			});
		}
	});

}

function errorDiv(){
	$("#erro").show();
}


function limitText(limitField, limitNum, e) {
	var charCode = (e.which) ? e.which : e.keyCode;
    if (limitField.value.length >= limitNum && charCode != 8 && charCode != 9) {
    	e.preventDefault();
    	return false;
    } 
    return true;
}


function onlyPositiveNumber(evt){
	var charCode = (evt.which) ? evt.which : evt.keyCode;
	
	alert( charCode );
    if (charCode > 31 && (charCode < 48 || charCode > 57)){
    	evt.preventDefault();
    	return false;
    }
    return true;
}

function onlyNumber(evt){
	var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)){
    	evt.preventDefault();
    	return false;
    }
    return true;
}

function onlyValue(evt){
	var charCode = (evt.which) ? evt.which : evt.keyCode;
	if (charCode > 31 && (charCode < 48 || charCode > 57)){
    	evt.preventDefault();
    	return false;
    }
    
    return true;
}

function onlyPercent(value){
	var percent = onlyNumber(value);
	if( percent == "") return "";
	else if(percent > 100) return "100";
	else if(percent < 0) return "0";
	return percent;
}

function onlyMoney(evt){
	var charCode = (evt.which) ? evt.which : evt.keyCode;
	
	if( charCode == 44 ) { // virgula (fazer algo pra trocar)
		evt.preventDefault();
		return false;
	} else {
		if (charCode > 31 && charCode != 45 && charCode != 46 && (charCode < 48 || charCode > 57)){ 
			evt.preventDefault();
			return false;
		}
	}
    
    return true;
}

function onlyNumberJQuery(e) {
    if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
        return false;
    }
}



function warningAlert(title, msg, callback){
    jQuery.alerts.dialogClass = 'alert-danger ui-draggable';
    if(title == ""){
		$.get("tradutor.jsp?label=MENSAGEM", function(data){
			jAlert($.trim(msg), $.trim(data), function(){
				jQuery.alerts.dialogClass = null;
				callback();
			
			});
		});
    }
	jAlert(msg, title, function(){
		jQuery.alerts.dialogClass = null;
		callback();
	});
}

function warningAlertTop(title, msg, callback){
    jQuery.alerts.dialogClass = 'alert-danger ui-draggable';
    $.alerts.verticalOffset = -(window.innerHeight / 3);
	jAlert(msg, title, function(){
			jQuery.alerts.dialogClass = null;
			callback();
	});
}

function infoAlert(title, msg, callback){
    jQuery.alerts.dialogClass = 'alert-info ui-draggable';
    if(title == ""){
		$.get("tradutor.jsp?label=MENSAGEM", function(data){
			jAlert($.trim(msg), $.trim(data), function(){
				jQuery.alerts.dialogClass = null;
				callback();
			});
		});
    }
	jAlert(msg, title, function(){
			jQuery.alerts.dialogClass = null;
			callback();
	});
}

function infoAlertTop(title, msg, callback){
    jQuery.alerts.dialogClass = 'alert-info ui-draggable';
    $.alerts.verticalOffset = -(window.innerHeight / 3);	
    jAlert(msg, title, function(){
			jQuery.alerts.dialogClass = null;
			callback();
	});
}

function alertCreate(id){
	 $( "#dialogCreate" ).dialog({
	      resizable: false,
	      height:200,
	      modal: true,
	      buttons: {
	        "Marcar": {
	        		text : "Marcar",
	        		id : "okbtnid",
	        		click : function(e) {
	    	        	alert(e);
	    	        	$( this ).dialog( "close" );
	    	        }
	        	}, "Cancelar" : {
	        		text : "Cancelar",
	        		id : "btncancel",
	        		click : function(){
	        			$(this).dialog("close");
	        		}
	        	}
	      }
	    });
}

function opeFancyBox(url){
	$.get(url,function(data){
		$.fancybox({content:data});
	});
}

// **************************************************************************************************************************************************************
// **************************************************************************************************************************************************************
// **************************************************************************************************************************************************************

function swapObject(objHide, objShow){
	hideObject(objHide);
	showObject(objShow);
}

function hideObject(obj){
	document.getElementById(obj).style.display = "none";
}

function showObject(obj){
	document.getElementById(obj).style.display = "block";
}

function showTR(obj){
	document.getElementById(obj).style.display = "table-row";
}

function hideObjectByClassName(container, obj){
	var tags = container.childNodes;
	var regExp = new RegExp(obj);
	for (i = 0; i < tags.length; i++) {
		if(regExp.test(tags[i].className)){
			tags[i].style.display = "none";
		}
	}
}

function showObjectByClassName(container, obj){
	var tags = container.childNodes;
	var regExp = new RegExp(obj);
	for (i = 0; i < tags.length; i++) {
		if(regExp.test(tags[i].className)){
			tags[i].style.display = "block";
		}
	}
}

function getElementByClassName(container, obj){
	var tags = container.childNodes;
	var regExp = new RegExp(obj);
	for (i = 0; i < tags.length; i++) {
		if(regExp.test(tags[i].className)){
			return tags[i];
		}
	}
	return null;
}

function getElementsByClassName(container, obj){
	var tags = container.childNodes;
	var elementsArray = new Array();
	var regExp = new RegExp(obj);
	for (i = 0; i < tags.length; i++) {
		if(regExp.test(tags[i].className)){
			elementsArray.push(tags[i]);
		}
	}
	return elementsArray;
}

function expandAccordion() {
	$('.accordion-primary .ui-accordion-header:not(.ui-state-active)').next().slideToggle(); // expande todo o accordion
}

function showGrayBox(){
	showObject("grayBox");
	showObject("aboveGrayBox");
}

function hideGrayBox(){
	hideObject("grayBox");
	hideObject("aboveGrayBox");
	document.getElementById("aboveGrayBox").innerHTML = "";
}

function getMarcadas(){
	var marcada = "";
	for(var i = 0; i < formX.length; i++){
		if(formX[i].type == "checkbox"){
			if(formX[i].checked){
				if(formX[i].name.indexOf("marcada")==0){
					marcada += formX[i].value + ";";
				}
			}
		}
	}
   
	formX.checkMarcadas.value=marcada;
	return marcada;   
}

function formata(campo,estilo,sonum,e){
	if(estilo == 'cnpj')
		estilo = '00.000.000/0000-00';
		
	if(estilo == 'cep')
		estilo = '#####-###';
		
	if(estilo == 'tel')
		estilo = '(##)####-####';
		
	if(estilo == 'rg')
		estilo = '##.###.###-#';
		
	if(estilo == 'cpf')
		estilo = '###.###.###-##';
	
	if(estilo == 'data')
		estilo = '##/##/####';
		
	if(estilo == 'hora')
		estilo = '##:##:##';
		
	if(estilo == 'horaMin')
		estilo = '##:##';
	
	if(estilo == 'preco')
		estilo = '##########';
	
	if(estilo == 'parcelamento')
		estilo = '##';
	if(estilo == 'num')
		estilo = '###';

	var keycode;
	if(window.event)
		keycode = e.keyCode;
	else if(e.which)
		keycode = e.which;
	
	if(keycode == 8 || keycode == 46 || keycode == null){
		return true;
	}else{
		cnum = false;
		
		for(i=48;i<=57;i++){
			if(keycode == i)
				cnum = true;
		}
		
		for(i=96;i<=105;i++){
			if(keycode == i)
				cnum = false;
		}
	
		if(sonum && !cnum)
			return false;
	
		retorno = "";
	
		for(i=0;i<=campo.value.length;i++){
			if(i < estilo.length){
				if(estilo.charAt(i) == '#')
					retorno += campo.value.charAt(i);
				else
					retorno += estilo.charAt(i);
			}
			else{
				return false;
			}
		}
	
		campo.value = retorno;
	}
}

function replaceAll(valor, antes, depois) {
	while( valor.indexOf(antes) >= 0 ) {
		valor = valor.replace(antes, depois);
	}
	return valor;
}

function changePessoa(valor) {
	if( valor == 'pf' ) {
		hideObject("boxPJ");
		showTR("boxPF");
	} else if( valor == 'pj' ) {
		hideObject("boxPF");
		showTR("boxPJ");
	} else {
		showTR("boxPF");
		showTR("boxPJ");
	}
}

function formataCampo(campo, Mascara, evento) { 
    var boleanoMascara; 
    var Digitato = evento.keyCode;
    exp = /\-|\.|\/|\(|\)| /g;
    campoSoNumeros = campo.value.toString().replace( exp, "" ); 
    var posicaoCampo = 0;    
    var NovoValorCampo="";
    var TamanhoMascara = campoSoNumeros.length;; 
    if (Digitato != 8) { 
    	for(var i=0; i<= TamanhoMascara; i++) { 
			boleanoMascara = ((Mascara.charAt(i) == "-") ||  (Mascara.charAt(i) == ".") || (Mascara.charAt(i) == "/"));
			boleanoMascara = boleanoMascara || ((Mascara.charAt(i) == "(") || (Mascara.charAt(i) == ")") || (Mascara.charAt(i) == " ")); 
			if (boleanoMascara) { 
				NovoValorCampo += Mascara.charAt(i); 
                TamanhoMascara++;
			}else{ 
				NovoValorCampo += campoSoNumeros.charAt(posicaoCampo); 
				posicaoCampo++; 
			}
    	}      
    	campo.value = NovoValorCampo;
    	return true; 
    }else { 
    	return true; 
    }
}

function mascaraInteiro(){
	if (event.keyCode < 48 || event.keyCode > 57){
		event.returnValue = false;
		return false;
    }
    return true;
}

function mascaraValor(){
	if (event.keyCode == 46) return true;
	if (event.keyCode < 48 || event.keyCode > 57 ){
		event.returnValue = false;
		return false;
    }
    return true;
}

function MascaraCep(cep){
    return formataCampo(cep, '00.000-000', event);
}

function MascaraTelefone(tel){  
    if(mascaraInteiro(tel)==false){
            event.returnValue = false;
    }       
    return formataCampo(tel, '(00) 0000-0000', event);
}

function MascaraCPF(cpf){
    if(mascaraInteiro(cpf)==false){
        event.returnValue = false;
    }       
    return formataCampo(cpf, '000.000.000-00', event);
}

function MascaraCNPJ(cnpj){
    if(mascaraInteiro(cnpj)==false){
        event.returnValue = false;
    }       
    return formataCampo(cnpj, '00.000.000/0000-00', event);
}

function MascaraData(data){
    if(mascaraInteiro(data)==false){
        event.returnValue = false;
    }       
    return formataCampo(data, '00/00/0000', event);
}

function ValidarCPF_CNPJ(obj){
    var opcao = document.getElementById("personalidade");
    
    if( opcao == null || opcao.value == "pf"){
    	return ValidarCPF(obj);
    }else if(opcao.value == "pj"){
    	return ValidarCNPJ(obj);
    } else {
    	alert("Selecione a personalidade");
    }        
}

function ValidarCPF2(Objcpf){
	if( verificarIdade() ) {
		return ValidarCPF(Objcpf);
	}
}

function ValidarCPF3(Objcpf){
		return ValidarCPF(Objcpf);
}

function verificarIdade(){
	var result = eDate.isOverAge(eDate.getNew({'dd/mm/yyyy': formAdmin.pfDataNascimento.value}), 18	);
	return result;
}

function ValidarCPF(Objcpf) {
	var cpf;
	var digito = new Array(11);
	var digito2 = new Array(11);
	var total_numero_d10 = 0;
	var total_numero_d11 = 0;
	var valor1;
	var valor2;

	cpf = Objcpf.value;
//	Retira os pontos (".") e tra�os ("-") do CPF
	cpf = cpf.replace(".","");
	cpf = cpf.replace(".","");
	cpf = cpf.replace(".","");
	cpf = cpf.replace("-","");
	if (cpf=="") {
//		O usuario deixou o campo em banco
//		alert("Digite um C.P.F para validar");
	}else if (cpf.length!=11) {
//		O cpf informado nao possui 11 digitos
//		alert("O CPF digitado � inv�lido !\nN�opossui 11 d�gitos");
	}else if (isNaN(cpf)) {
//		alert("O CPF informado n�o � um n�mero v�lido");
	}else{
		/* Nessa parte iremos fazer a verifica��o completa do CPF */
//		Atribui valor �s posi��es 10 e 11 do array (d�gitos verificadores)
		digito[10] = cpf.charAt(9);
		digito[11] = cpf.charAt(10);

//		Faz uma array com os n�meros de 10 a 2
		for (i=11;i>=2;i--) {
			digito2[i] = i;
		}
//		Verifica os d�gitos informados no CPF
		for (i=1;i<=9;i++) {
			digito[i] = cpf.charAt(i-1);
			total_numero_d10 += parseInt(digito[i])*parseInt(digito2[11-i]);
		}
		valor1 = total_numero_d10 % 11;
		if (valor1<2) {
			valor1 = 0;
		}else{
			valor1 = 11 - valor1;
		}
		for (i=1;i<=9;i++) {
			total_numero_d11 += parseInt(digito[i])*(11-i+1);
		}

		total_numero_d11 += valor1*2;
		valor2 = total_numero_d11 % 11;
		if (valor2<2) {
			valor2 = 0;
		}else {
			valor2 = 11 - valor2;
		}
		
	    if(cpf == "11111111111" || cpf == "22222222222" || cpf ==
			"33333333333" || cpf == "44444444444" || cpf == "55555555555" || cpf ==
			"66666666666" || cpf == "77777777777" || cpf == "88888888888" || cpf ==
			"00000000000" ){
	    	
	    	valor1 = "99999999";
	    }
		
//	    alert( digito[10] == valor1 );
//	    alert( digito[11] == valor2 );
		
//		Verifica se os d�gitos conferem
		if (digito[10]==valor1 && digito[11]==valor2) {
//			O CPF � v�lido
	    	return true;
		}else{
//			alert("O CPF informado � inv�lido !");
			Objcpf.value = "";
			Objcpf.focus();
			return false;
		}
	}
}


function ValidarCNPJ(ObjCnpj){
    var cnpj = ObjCnpj.value;
    var valida = new Array(6,5,4,3,2,9,8,7,6,5,4,3,2);
    var dig1= new Number;
    var dig2= new Number;
    
    exp = /\.|\-|\//g
    cnpj = cnpj.toString().replace( exp, "" ); 
    var digito = new Number(eval(cnpj.charAt(12)+cnpj.charAt(13)));
            
    for(i = 0; i<valida.length; i++){
            dig1 += (i>0? (cnpj.charAt(i-1)*valida[i]):0);  
            dig2 += cnpj.charAt(i)*valida[i];       
    }
    dig1 = (((dig1%11)<2)? 0:(11-(dig1%11)));
    dig2 = (((dig2%11)<2)? 0:(11-(dig2%11)));
    
    if(((dig1*10)+dig2) != digito) {
//    	alert('CNPJ Invalido!');
    	ObjCnpj.focus();
    	ObjCnpj.value = "";
    	return false;
    } else {
    	return true;
    } 
}


var eDate =  {

		units: {
			minute: 60000,
			hour: 3600000,
			day : 86400000
		},
		
		clone: function (date) {
			return new Date(date);
		},
		
		getInput: function (i) {
			var s;
			if (i['dd/mm/yyyy']) {
				s = i['dd/mm/yyyy'].split('/');
				i.day = s[0]; 
				i.month = s[1]; 
				i.year = s[2];
			}
			if (i['dd/mm/yyyy']) {
				s = i['dd/mm/yyyy'].split('/');
				i.month = s[0];
				i.day = s[1]; 
				i.year = s[2];
			}
			if (i['yyyy/dd/mm']) {
				s = i['dd/mm/yyyy'].split('/');
				i.year = s[0];
				i.month = s[1];
				i.day = s[2]; 
			}		
			return {
				day: parseInt(i.day, 10),
				month: parseInt(i.month, 10) - 1,
				year: parseInt(i.year, 10)
			};
		},
		
		isValid: function (i) {
			var index, fi = this.getInput(i);
			for (index in fi) {
				if (isNaN(fi[index])) {
					return false;
				}
	 		} 
			var 
			testDate = new Date(fi.year, fi.month, fi.day),
			testDateString = 
				testDate.getFullYear().toString() + 
				testDate.getMonth().toString() + 
				testDate.getDate().toString(),
			inputString =
				fi.year.toString() + 
				fi.month.toString() + 
				fi.day.toString();

			return (testDateString === inputString);	
		},
		
		getNew: function (i) {
			var fi = this.getInput(i);
			return new Date(fi.year, fi.month, fi.day);
		},

		zeroDay: function (date) {
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);
			date.setMilliseconds(0);
			return date;
		},	
		
		getToday: function () {
			return this.zeroDay(new Date());
		},
		
		add: function (i) {
			i.date.setTime(
				i.date.getTime() + 
				(parseInt(i.value, 10) * 
				this.units[i.unit]) 
			);
		},
		
		addDays: function (date, value) {
			this.add({
				'date': date,
				'unit': 'day',
				'value': value 
			});
		},

		diffDays: function (date1, date2) {
			var
			cdate1 = this.zeroDay(this.clone(date1)),
			cdate2 = this.zeroDay(this.clone(date2)),
			diff = cdate1.getTime() - cdate2.getTime();
			if (diff === 0) {
				return 0;	
			}
			return Math.round(diff / this.units.day);				
		},
		
		isOverAge: function (date, age) {
			c = this.getToday();
			c.setDate(date.getDate());
			c.setMonth(date.getMonth());
			c.setFullYear(date.getFullYear() + age);
			if (this.getToday().getTime() < c.getTime()) {
				return false;
			}
			return true;
		}
		
};

function validateSelect(dataSource){
	var element = new Array();

	for(var a = 0; a < dataSource.length; a++){
		element.push(jQuery("select[name="+dataSource[a]+"]"));
	}
	
	
	jQuery.each(element,function(index,value){
		if(value.val() == "0"){
			jQuery(this).css('border-color','red');
		}else{
			jQuery(this).css('border-color','green');
		}
		
		element[index].change(function(event){
			var cor = "red";
			
			if(jQuery(this).val() != "0"){
				cor = "green";
			}
			
			jQuery(this).css('border-color',cor);
			
		});
	});
	
	this.checkAll = function(){
		
		var returno = true;
		jQuery.each(element,function(index,value){
			if(jQuery(value).val() == "0"){
				returno = false;
			}
		});
		return returno;
	}
	
	return this;
}

function initializeMask(data){
	jQuery.each(data,function(index,value){
		jQuery('input[name='+value.elemento+']').mask(getMask(value.tipo));
	});
	
}

function getMask(mask){
	switch(mask){
		case 'pf' : {
			return '999.999.999-99';
		}case 'pj' :{
			return '99.999.999/9999-99';
		}case 'money' :{
			return '000.000.000.000.000,00';
		}case 'data' :{
			return '99/99/9999';
		}
	}
}

function validateOnflush(data){
	
	this.runValidate = function(){
		
		jQuery(data,function(index,value){
			if(jQuery('input[name='+data[index].nome+']').val().trim() == ''){
				return false;
			}
		});
		
		return true;
	}
	
	return this;
}

// ----------------------------------------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------------------------------------
// pinger para mensagens novas
function carregaMensagens() { // chamada pela home, no reload
	try {
		ChamaPaginaArray('mensagemCheck.jsp', 'divPingMensagem', 'pingMensagem()');
	} catch(e) {
		alert(e);
	}
}

function pingMensagem() {
	try {
		if( formX.mensagensNovas1.value != formX.mensagensNovas2.value ) {
			formX.mensagensNovas1.value = formX.mensagensNovas2.value;
			document.getElementById("naoLidas").innerHTML = formX.mensagensNovas2.value;
			
			if( formX.mensagensNovas1.value == 0 ) {
				jQuery('#mensagemEnvelope').removeClass('blink');
			} else {
				jQuery('#mensagemEnvelope').addClass('blink');
				
				try {
					document.getElementById("sound").innerHTML = "<embed src='/allgenda/images/bip.mp3' hidden='true' autostart='true' loop='false' />";
				} catch(e) {
				}
			}
			
			// checa se est� na home. Se estiver, da um reload nela. Se nao, so atualiza o topo
			try {
				if( formX.painelCheck.value == "ok" ) {
					setTimeout("reloadHome()", 500 );
				}
			} catch(e) {
			}
		}
		
//		setTimeout("carregaMensagens()", 60000 ); 
	} catch(e) {
		alert(e);
		try {
//			setTimeout("carregaMensagens()", 60000 ); 
		} catch(e) {
			alert(e);
		}
	}
}

function reloadHome() {
	location.href = "painel.jsp";
}

function enviarEmail( form, div ) {
	jConfirm('Are you sure you want to SEND this email for the customer?','WARNING',function(response){
		if(response){
			form.command.value = "cliente";
			form.action.value = div;
			
			var query = createQueryString(form); 
			ChamaPaginaArray(query, div);
		}
	});	
}

function displayFin( atual, total ) {
	for(var i = 0; i < total; i++){
		document.getElementById("displayFin" + i).style.display = "none";
	}
	document.getElementById("displayFin" + atual).style.display = "block";
}

function changeGrafico() {
	abre("miolo", "relatorioGrafico.jsp?id=" + document.getElementById('idGrafico').value + "&tipo=" + document.getElementById('tipoGrafico').value );
}


function changeMais(id) {
	document.getElementById("notMais1").style.display = "none";
	document.getElementById("notMais2").style.display = "none";
	document.getElementById("notMais3").style.display = "none";
	
	document.getElementById("notMais" + id).style.display = "block";
	
	$('#maisTit1').removeClass("active");
	$('#maisTit2').removeClass("active");
	$('#maisTit3').removeClass("active");
	
	$('#maisTit'+id).addClass("active");
}

function hideBanner() {
	upDown('bannerPromocional');
	
	if( window.innerWidth <= 800 ) {
		document.getElementById("miolo").style.marginTop = "50px"; 
	}
}


function montarUrlRedesSociais(url, queryString, titulo, noticia) { 	
	var noticiaSemTags = noticia.replace(/<\/?br\s*\/?>/g, '\n').replace(/&nbsp;/g, '').replace(/<\/?[\s]*[^aA>]*\/?>/g,'');
	var urlFull = (queryString !== null || queryString !== '') ? (url + '?' + queryString) : url;
	var urlFullCod = encodeURIComponent(url + '?' + queryString);
	var tituloCod = encodeURIComponent(titulo);
	var noticiaAbbrCod = noticiaSemTags.length > 350 ? encodeURIComponent(noticiaSemTags.substring(0, 350) + '...') : encodeURIComponent(noticiaSemTags);
	var fonteCod = encodeURI('Allgenda - www.allgenda.org.br');
	var mailBody = noticiaAbbrCod + '\r\n' + urlFullCod;
			
	$("#aTwitter").attr('href', 'http://twitter.com/intent/tweet?text=' + tituloCod + ' - ' + urlFullCod);
	$("#aFacebook").attr('href', 'http://www.facebook.com/share.php?u=' + urlFullCod + '&t=' + tituloCod);					
	$("#aGmail").attr('href', 'https://mail.google.com/mail/?view=cm&fs=1&to&su=' + tituloCod +'&body=' +
			mailBody + "&ui=2&tf=1&shva=1");
	$("#aGoogleBookmarks").attr('href', 'http://www.google.com/bookmarks/mark?op=edit&bkmk=' + urlFullCod + 
			'&title=' + tituloCod + "&annotation=" + noticiaAbbrCod);			
	$("#aStumbleUpon").attr('href', 'http://www.stumbleupon.com/submit?url=' + urlFullCod + "&title=" + tituloCod);
	$("#aDelicious").attr('href', 'http://delicious.com/post?url=' + urlFullCod + '&title=' + tituloCod + '&notes=' 
			+ noticiaAbbrCod);
	$("#aLinkedIn").attr('href', 'http://www.linkedin.com/shareArticle?mini=true&url=' + urlFullCod +
			'&title=' + tituloCod + '&source=' + fonteCod + '&summary=' + noticiaAbbrCod);
	$("#aBlinklist").attr('href', 'http://www.blinklist.com/index.php?Action=Blink/addblink.php&Url=' + urlFull + "&Title=" + titulo);
	$("#aDigg").attr('href', 'http://digg.com/submit?phase=2&url=' + urlFullCod + '&title=' + '&bodytext=' + noticiaAbbrCod);
	$("#aReddit").attr('href', 'http://reddit.com/submit?url=' + urlFullCod + "&title=" + tituloCod);
	$("#aYCombinator").attr('href', 'http://news.ycombinator.com/submitlink?u=' + urlFullCod + "&t=" + tituloCod);
	$("#aMSNReporter").attr('href', "http://reporter.es.msn.com/?fn=contribute&Title=" + tituloCod + '&URL=' + urlFullCod + '&cat_id=6' + '&tag_id=31' + '&Remark=' + noticiaAbbrCod);
	$("#aTumblr").attr('href', 'http://www.tumblr.com/share?v=3&u=' + urlFullCod + '&t=' + tituloCod + '&s=' + noticiaAbbrCod);
	$("#aGooglePlus").attr('href', 'https://plus.google.com/share?url={' + urlFullCod + '}');		
}

function mostrarOuEsconderMaisRedes()
{		
	if($('#divMaisSocial').is(':visible'))
		$('#divMaisSocial').slideUp("slow");		
	else
		$('#divMaisSocial').slideDown();
}


function aumentaTamanhoFonte() {
	var titulo 		= document.getElementById("titulo");
	var subtitulo 	= document.getElementById("subtitulo");
	var texto 		= document.getElementById("texto");
	var texto_li 	= document.getElementsByClassName("li2");
	var fonte 		= document.getElementsByTagName("font");
	
	try {
		if(titulo.style.fontSize) {
			var s = parseInt(titulo.style.fontSize.replace("px",""));
		} else {
			var s = 28;
		}
		if(s <= 34){
			s += 2;
		}
		titulo.style.fontSize = s+"px";
	} catch(e) {
	}
	
	try {
		if(subtitulo) {
			if(subtitulo.style.fontSize) {
				var s = parseInt(subtitulo.style.fontSize.replace("px",""));
			} else {
				var s = 20;
			}
			if(s <=26){
				s += 2;
			}
			subtitulo.style.fontSize = s+"px";
		}
	} catch(e) {
	}
	
	try {
		if(texto.style.fontSize) {
			var s = parseInt(texto.style.fontSize.replace("px",""));
		} else {
			var s = 18;
		}
		if(s <= 24){
			s += 2;
		}
		
		texto.style.fontSize = s+"px";
	} catch(e) {
	}
	// Coloquei esses dois Try abaixo porque ao clicar em baixar o fonte os texto na tag <li> e <font> não estavam aumentando
	try {
		for(var i=0; i < texto_li.length; i++){
			if(texto_li[i].style.fontSize) {
				var s = parseInt(texto_li[i].style.fontSize.replace("px",""));
			} else {
				var s = 16;
			}
			if(s <= 22){
				s += 2;
			}
			
			texto_li[i].style.fontSize = s+"px";
		}
	} catch(e) {
	}
	try {
		for(var i=0; i < fonte.length; i++){
			if(fonte[i].style.fontSize) {
				var s = parseInt(fonte[i].style.fontSize.replace("px",""));
			} else {
				var s = 16;
			}
			if(s <= 22){
				s += 2;
			}
			fonte[i].style.fontSize = s+"px";
		}
	} catch(e) {
	}
}

function diminuiTamanhoFonte() {
	var titulo 		= document.getElementById("titulo");
	var subtitulo 	= document.getElementById("subtitulo");
	var texto 		= document.getElementById("texto");
	var texto_li 	= document.getElementsByClassName("li2");
	var fonte 		= document.getElementsByTagName("font");
	
	try {
		if(titulo.style.fontSize) {
			var s = parseInt(titulo.style.fontSize.replace("px",""));
		} else {
			var s = 24;
		}
		if(s >= 20){
			s -= 2;	
		}

		titulo.style.fontSize = s+"px";
	} catch(e) {
	}

	try {
		if(subtitulo) {
			if(subtitulo.style.fontSize) {
				var s = parseInt(subtitulo.style.fontSize.replace("px",""));
			} else {
				var s = 22;
			}
			if(s >= 18){
				s -= 2;
			}
			
			subtitulo.style.fontSize = s+"px";
		}
	} catch(e) {
	}
		
	try {
		if(texto.style.fontSize) {
			var s = parseInt(texto.style.fontSize.replace("px",""));
		} else {
			var s = 12;
		}
		if(s >= 10){
			s -= 2;	
		}
		texto.style.fontSize = s+"px";
	} catch(e) {
	}
	// Coloquei esses dois Try abaixo porque ao clicar em baixar o fonte os texto na tag <li> e <font> não estavam diminuindo
	try {
		for(var i=0; i < texto_li.length; i++){
			if(texto_li[i].style.fontSize) {
				var s = parseInt(texto_li[i].style.fontSize.replace("px",""));
			} else {
				var s = 14;
			}
			if(s >= 10){
				s -= 2;
			}
			
			texto_li[i].style.fontSize = s+"px";
		}
	} catch(e) {
	}
	try {
		for(var i=0; i < fonte.length; i++){
			if(fonte[i].style.fontSize) {
				var s = parseInt(fonte[i].style.fontSize.replace("px",""));
			} else {
				var s = 14;
			}
			if(s >= 10){
				s -= 2;
			}
			
			fonte[i].style.fontSize = s+"px";
		}
	} catch(e) {
	}
}

function maisNot( pagina, idCategoria, callerDiv ) {
	// callerDiv.style.display = "none";
	callerDiv.innerHTML = "Exibindo p�gina " + pagina;

	abre("maisNot_"+pagina, "maisNoticia.jsp?idCategoria=" + idCategoria + "&pagina=" + pagina);
	
	$('html, body').animate({
	    scrollTop: $("#maisNot_"+pagina).offset().top - 200
	}, 2000);
}

function maisNotBusca( pagina, busca, callerDiv ) {
	callerDiv.innerHTML = "Exibindo p�gina " + pagina;

	abre("maisNot_"+pagina, "maisNoticiaBusca.jsp?busca=" + busca + "&pagina=" + pagina);
	
	$('html, body').animate({
	    scrollTop: $("#maisNot_"+pagina).offset().top - 200
	}, 2000);
}

function catCombo( box, nova ) {
	var id = form.idCategoria1.value + "|" + form.idCategoria2.value;
	try {
		form.nome.value = "";
	} catch(e) {
	}

	try {
		var adicionar = "";
		if( nova ) {
			adicionar = "&id00=--%20ADICIONAR%20NOVA%20--";
		}
		
		if( box == "2" ) {
			if( form.idCategoria2.value == "0" || form.idCategoria2.value == "-1" ) {
				abre("divCombo3","/allgenda/genericCombo.jsp?class=ClubeVantagemCategoriaEntity&nome=idCategoria3&onchange=javascript:catCombo(3," + nova + ")&id0=&id=" + id);
				document.getElementById("block3").style.display    = "none";
				document.getElementById("divCombo3").style.display = "none";
				form.idCategoria3.value = "0";
			} else {
				abre("divCombo3","/allgenda/genericCombo.jsp?class=ClubeVantagemCategoriaEntity&nome=idCategoria3&onchange=javascript:catCombo(3," + nova + ")&id0=" + adicionar + "&id=" + id);
				document.getElementById("block3").style.display    = "block";
				document.getElementById("divCombo3").style.display = "block";
			}
			
		} else if( box == "1" ) {
			abre("divCombo2","/allgenda/genericCombo.jsp?class=ClubeVantagemCategoriaEntity&nome=idCategoria2&onchange=javascript:catCombo(2," + nova + ")&id0=" + adicionar + "&id=" + id);
			document.getElementById("block3").style.display    = "none";
			document.getElementById("divCombo3").style.display = "none";
			form.idCategoria3.value = "0";
		}
	} catch(e) {
	}
} 

function calendario( tipo, idCalendario, idSala) {
	if(idSala)
		ChamaPaginaArray('calendarioPainel.jsp?idCalendario=' + idCalendario + "&idSala=" + idSala, 'centroPainel', 'activateCalendar(1)');
	else
		ChamaPaginaArray('calendarioPainel.jsp?idCalendario=' + idCalendario, 'centroPainel', 'activateCalendar(1)');
}	

function taskDone( id, tipo, status, idCalendario ) {
	ChamaPaginaArray("calendarioPainel.jsp?done=true&id=" + id + "&tipo=" + tipo + "&status=" + status + "&idCalendario=" + idCalendario, "centroPainel", "activateCalendar(1)");
}
function taskDone2( id, tipo, status, idCalendario ) {
	ChamaPaginaArray("calendarioPainel.jsp?done=true&id=" + id + "&tipo=" + tipo + "&status=" + status + "&idCalendario=" + idCalendario, "taskDoneAjax");
}

function activateCalendar(tipo) {
	
	try {
		
		eval( document.getElementById("runscript").innerHTML );
		
	} catch( e ) {
		
	}
}

function converteFloatMoeda(valor){
	var inteiro = null, decimal = null, c = null, j = null;
	var aux = new Array();
	valor = ""+valor;
	c = valor.indexOf(".",0);
	//encontrou o ponto na string
	if(c > 0){
		//separa as partes em inteiro e decimal
		inteiro = valor.substring(0,c);
		decimal = valor.substring(c+1,valor.length);
	}else{
		inteiro = valor;
	}
  
	//pega a parte inteiro de 3 em 3 partes
	for (j = inteiro.length, c = 0; j > 0; j-=3, c++){
		aux[c]=inteiro.substring(j-3,j);
	}
	  
	//percorre a string acrescentando os pontos
	inteiro = "";
	for(c = aux.length-1; c >= 0; c--){
		inteiro += aux[c]+'.';
	}
	//retirando o ultimo ponto e finalizando a parte inteiro
	  
	inteiro = inteiro.substring(0,inteiro.length-1);
	var antes = false;
	if(decimal != null && decimal.length == 2){
		antes = true;
	}
	decimal = parseInt(decimal);
	if(isNaN(decimal)){
	   decimal = "00";
	}else{
	   decimal = ""+decimal;
	   if(decimal.length === 1){
		   if(antes == true){
			   decimal = "0"+decimal;
		   }else{
			   decimal = decimal+"0";
		   }
	   }
	}
	valor = inteiro+","+decimal;
	return valor;
}
