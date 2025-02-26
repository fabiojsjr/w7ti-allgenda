/***************/
/** Functions **/
/***************/
function login(){
	formAdmin.command.value = "usuario";
	formAdmin.action.value = "login";
	formAdmin.submit();
	// var janelaCompleta = window.open("/allgenda/" + createQueryString(formAdmin), "geral", "width=700,height=650,scrollbars=1,resizable=yes,menubar=no,toolbar=no");
}

function logout(){
	formAdmin.command.value = "usuario";
	formAdmin.action.value = "logout";
	formAdmin.submit();
}

function goHome() {
	formAdmin.command.value="usuario";
	formAdmin.action.value="home";
	formAdmin.submit();
}

function showEsqueciSenha() {
	showObject("grayBox");
	showObject("esqueciSenha");
}

function hideEsqueciSenha() {
	hideObject("grayBox");
	hideObject("esqueciSenha");
}

function enviarSenhaIntranet() {
	if (formAdmin.esqueciSenhaEmail.value==""){
		alert("Digite seu email!");
	}else{
		alert(formAdmin.esqueciSenhaEmail.value);
		ChamaPaginaArray("/allgenda/web?sid=Allgenda&command=usuario&action=esqueciSenha&email=" + formAdmin.esqueciSenhaEmail.value, "esqueciSenhaSendIntranet");
	}
}


function saveLogotipo() {
	formAdmin.command.value="logotipo";
	formAdmin.action.value="save";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
}

/*****************************/
/** Customizaééo de Colunas **/
/*****************************/
function showChangeColumns(){
	document.getElementById("changeColumns").style.display = "block";
	document.getElementById("buttonChangeColumns").href = "javascript:hideChangeColumns();";
}

function hideChangeColumns(){
	document.getElementById("changeColumns").style.display = "none";
	document.getElementById("buttonChangeColumns").href = "javascript:showChangeColumns();";
}

function changeColumns(atual, columns){
	for(var i = 2; i <= 4; i++){
		var img = document.getElementById("imageColumn"+i);
		img.src = img.src.replace(/_highlight/, "");
		if(i == columns)
			img.src = img.src.replace(/.gif/, "_highlight.gif");
	}
	if(atual != columns){
		if(confirm("A disposiééo da sua érea de trabalho seré reconfigurada, deseja continuar?")){
			formAdmin.command.value = "intra";
			formAdmin.action.value = "configIntranetColumns";
			formAdmin.id.value = columns;
			formAdmin.submit();
		}
	}
}

/***********************/
/** Elementos da Home **/
/***********************/
function enviarDuvida() {
	var textoDuvida=formAdmin.duvida.value;
	var regex = /\n/;
	while(textoDuvida.match(regex)){
		 textoDuvida = textoDuvida.replace(regex, "#");
	}
	ChamaPaginaArray("web?sid=Allgenda&command=intra&action=enviarPost&textoDuvida="+textoDuvida,"elementoInterno3");
}

function limparDicionario() {
	formAdmin.dicionarioPalavra.value = "";
	document.getElementById("dicionarioListagem").innerHTML = "";
}
function enviarDicionario() {
	ChamaPaginaArray("web?sid=Allgenda&command=intra&action=dicionario&palavra="+formAdmin.dicionarioPalavra.value + "&tipo=" + formAdmin.dicionarioTipo.value,"dicionarioListagem");
}

/**********/
/** Menu **/
/**********/
function openMenu(id){
	var div = document.getElementById('menu_' + id);
	formAdmin.command.value="intra";

 	if(div.style.display == "none"){
		div.style.display = "block";
	  	formAdmin.action.value = "menuAjax";
	  	var url = createQuerySimple(formAdmin) + "&idGrupo=" + id;
	  	ChamaPaginaArray(url, "menu_"+id);
	}else{
		div.style.display = "none";
	}
}

function openSubMenu(id){
	var div = document.getElementById('subMenu_' + id);
 	if(div.style.display == "none"){
		div.style.display = "block";
	}else{
		div.style.display = "none";
	}
}

function favoritos(tipo, idFuncao) {
	if( tipo == 'adicionar' ) {
		if (confirm('Confirma adicionar esse programa aos favoritos?')) {
			var url = 'web?sid=Allgenda&command=intra&action=favoritos&idFuncao=' + idFuncao + "&tipo=" + tipo;
			ChamaPaginaArray( url, "blank");

			alert("A funééo vai aparecer no menu Favoritos assim que a pégina for atualizada.");
		}
	} else {
		if (confirm('Confirma remover esse programa aos favoritos?')) {
			var url = 'web?sid=Allgenda&command=intra&action=favoritos&idFuncao=' + idFuncao + "&tipo=" + tipo;
			ChamaPaginaArray( url, "blank");

			alert("A funééo seré removida do menu Favoritos assim que a pégina for atualizada.");
		}
	}
}

function acao( comando ) {
	formAdmin.enctype = '';
	//formAdmin.encoding = '';
	if( comando.indexOf("action") < 0 ) 
		comando += "&action=default";
	if( comando.indexOf("ajax=false") > 0 ) {
		comando = comando.replace("&ajax=false", "");
    //formAdmin.extra.value = comando;
		if( comando.indexOf("action") < 0 ) {
			if( comando.indexOf("&") > 0 ) comando = comando.substring( 0, comando.indexOf("&") );
			formAdmin.action.value  = "";
			formAdmin.command.value = comando;
		} else {
			formAdmin.command.value = comando.substring(0, comando.indexOf("&") );
			comando = comando.replace( comando.substring(0, comando.indexOf("&"), "") );

			formAdmin.action.value = comando.substring( comando.indexOf("action=")+7, comando.length );
		}
		formAdmin.submit();
	} else {
		ChamaPaginaArray( "web?sid=Allgenda&command=" + comando , "principal");
		location.href = "#topo";
	}
}

/***********/
/** Aéées **/
/***********/
function exec(command, action, id){
	formAdmin.command.value = command;
	formAdmin.action.value = action;
	if(id!=null || id!=undefined)
		formAdmin.id.value = id;
	
	try {
		getMarcadas();
	} catch(e) {
	}
	
	var query = createQueryString(formAdmin);
//	query = replaceAll(query, "\n", "#");
	ChamaPaginaArray(query, "principal");
}

/*
function exec(command, action, id, div, executeAfter){
	formAdmin.command.value = command;
	formAdmin.action.value = action;
	if(id!=null || id!=undefined)
		formAdmin.id.value = id;
	
	
	var query = createQueryString(formAdmin);
//	query = replaceAll(query, "\n", "#");
	ChamaPaginaArray(query, div, executeAfter);
}
*/

function execPost(command, action, id){
	formAdmin.command.value = command;
	formAdmin.action.value = action;
	if(id!=null || id!=undefined)
		formAdmin.id.value = id;

	var query = createQueryString(formAdmin);
	ChamaPaginaPost(query, "principal");
}

function execSubmit(command, action, id){
	formAdmin.command.value = command;
	formAdmin.action.value = action;
	if(id!=null || id!=undefined)
		formAdmin.id.value = id;
	
	formAdmin.submit();
}

function execSemMultiPart(command, action, id){
	formAdmin.enctype = '';
	formAdmin.encoding = '';

	formAdmin.command.value = command;
	formAdmin.action.value = action;
	if(id!=null || id!=undefined)
		formAdmin.id.value = id;
	
	formAdmin.submit();
}

function find(command, action, id){
	formAdmin.command.value = command;
	formAdmin.action.value = "find";
	if(action!=null || action!=undefined)
		formAdmin.action.value = action;
	
	if(id!=null || id!=undefined)
		formAdmin.id.value = id;
	
	var query = createQueryString(formAdmin);
	ChamaPaginaArray(query, "listagem");
}
function find(command, action, id, id2){
	formAdmin.command.value = command;
	formAdmin.action.value = "find";
	if(action!=null || action!=undefined)
		formAdmin.action.value = action;
	
	if(id!=null || id!=undefined)
		formAdmin.id.value = id;
	
	var query = createQueryString(formAdmin);
	query += "&id2=" + id2;
	ChamaPaginaArray(query, "listagem");
}

function backToFind(command, action, id){
	formAdmin.command.value = command;
	formAdmin.action.value = "";
	if(action!=null || action!=undefined)
		formAdmin.action.value = action;
	
	if(id!=null || id!=undefined)
		formAdmin.id.value = id;
	
	var query = createQueryString(formAdmin);
	ChamaPaginaArray(query, "principal", "find('"+command+"');");
}

function above(command, action, id){
	formAdmin.command.value = command;
	formAdmin.action.value = action;
	if(id!=null || id!=undefined)
		formAdmin.id.value = id;
	
	showGrayBox();
	var query = createQueryString(formAdmin);
	ChamaPaginaArray(query, "aboveGrayBox");
}

function combo(command, action, id, selecionado ){
	formAdmin.command.value = command;
	formAdmin.action.value = action;
	if(id!=null || id!=undefined)
		formAdmin.id.value = id;
	
	var query = createQueryString(formAdmin);
	
	if( selecionado != null ) query += "&selecionado=" + selecionado;
	
	ChamaPaginaArray(query, "combo");
}

function revisar(id, aceitar){
	formAdmin.command.value = "comentario";
	formAdmin.action.value = "save";
	
	var query = createQueryString(formAdmin);
	query += "&aceitar="+aceitar+"&idCom="+id;
	ChamaPaginaArray(query, "box", "ativar()");
}

/***********************/
/** Empresa Prestador **/
/***********************/

function empresaPrestadorSalvar() {
	var email = document.getElementById("email");
	if(eEmail(email) || email.value==""){
		formAdmin.command.value="empresaPrestador";
		formAdmin.action.value="saveEmpresaPrestador";
		if( ValidarCPF_CNPJ(formAdmin.cpf_cnpj) && verificarResposta("cpf_cnpj") && verificarResposta("inscricaoMunicipal") && verificarResposta("razaoSocial") && verificarResposta("apelido") )
			ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal");
	}
	else
		alert("E-mail inválido");
}

function empresaPrestadorNovo() {
	formAdmin.command.value="empresaPrestador";
	formAdmin.action.value="insert";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal");
}

function empresaPrestadorPesquisar() {
    formAdmin.command.value="empresaPrestador";
	formAdmin.action.value="findEmpresaPrestador";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "listagem");
}

function empresaPrestadorExcluir(idEmpresa) {
	formAdmin.idFilial.value = idEmpresa;
    formAdmin.command.value="empresaPrestador";
    if (confirm('Confirma excluséo deste registro?')) {
        formAdmin.action.value="deleteEmpresaPrestador";
		ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "listagem");
	}
}

function empresaPrestadorEditar(idEmpresa) {
    formAdmin.idFilial.value = idEmpresa;
    formAdmin.command.value="empresaPrestador";
    formAdmin.action.value="editEmpresaPrestador";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal");
}

function empresaSession() {
	if( formAdmin.idFilial.value == 0 ) {
		alert("Selecione uma empresa");
	} else {
		formAdmin.command.value="usr";
		formAdmin.action.value="empresaSession";
		formAdmin.submit();
	}
}

function empresaSession2() {
	formAdmin.command.value="usr";
	formAdmin.action.value="empresaSession2";
	formAdmin.submit();
}

/***********************/
/** Natureza Jurédica **/
/***********************/

function naturezaJuridicaSalvar() {
	formAdmin.command.value="naturezaJuridica";
	formAdmin.action.value="saveNaturezaJuridica";
	if(verificarResposta("codigo") && verificarResposta("descricao"))
		ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
}

function naturezaJuridicaNovo() {
	formAdmin.command.value="naturezaJuridica";
	formAdmin.action.value="insert";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
}

function naturezaJuridicaPesquisar() {
    formAdmin.command.value="naturezaJuridica";
	formAdmin.action.value="findNaturezaJuridica";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "listagem");
}

function naturezaJuridicaExcluir(idNaturezaJuridica) {
	formAdmin.idNaturezaJuridica.value = idNaturezaJuridica;
    formAdmin.command.value="naturezaJuridica";
    if (confirm('Confirma excluséo deste registro?')) {
        formAdmin.action.value="deleteNaturezaJuridica";
		ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "listagem" );
	}
}

function naturezaJuridicaEditar(idNaturezaJuridica) {
    formAdmin.idNaturezaJuridica.value = idNaturezaJuridica;
    formAdmin.command.value="naturezaJuridica";
    formAdmin.action.value="editNaturezaJuridica";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
}

/***********************/
/******** CNAE *********/
/***********************/

function cnaeSalvar() {
	formAdmin.command.value="cnae";
	formAdmin.action.value="saveCnae";
	if(verificarResposta("codigo") && verificarResposta("descricao"))
		ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
}

function cnaeNovo() {
	formAdmin.command.value="cnae";
	formAdmin.action.value="insert";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
}

function cnaePesquisar() {
    formAdmin.command.value="cnae";
	formAdmin.action.value="findCnae";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "listagem");
}

function cnaeExcluir(idCnae) {
	formAdmin.idCnae.value = idCnae;
    formAdmin.command.value="cnae";
    if (confirm('Confirma excluséo deste registro?')) {
        formAdmin.action.value="deleteCnae";
		ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "listagem" );
	}
}

function cnaeEditar(idCnae) {
    formAdmin.idCnae.value = idCnae;
    formAdmin.command.value="cnae";
    formAdmin.action.value="editCnae";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
}

/***********************/
/******** CNES *********/
/***********************/

function cnesSalvar() {
	formAdmin.command.value="cnes";
	formAdmin.action.value="saveCnes";
	if(verificarResposta("codigo") && verificarResposta("descricao"))
		ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
}

function cnesNovo() {
	formAdmin.command.value="cnes";
	formAdmin.action.value="insert";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
}

function cnesPesquisar() {
    formAdmin.command.value="cnes";
	formAdmin.action.value="findCnes";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "listagem");
}

function cnesExcluir(idCnes) {
	formAdmin.idCnes.value = idCnes;
    formAdmin.command.value="cnes";
    if (confirm('Confirma excluséo deste registro?')) {
        formAdmin.action.value="deleteCnes";
		ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "listagem" );
	}
}

function cnesEditar(idCnes) {
    formAdmin.idCnes.value = idCnes;
    formAdmin.command.value="cnes";
    formAdmin.action.value="editCnes";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
}

/***********************/
/******** IGBE *********/
/***********************/

function ibgeSalvar() {
	formAdmin.command.value="ibge";
	formAdmin.action.value="saveIbge";
	if(verificarResposta("codigo") && verificarResposta("descricao"))
		ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
}

function ibgeNovo() {
	formAdmin.command.value="ibge";
	formAdmin.action.value="insert";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
}

function ibgePesquisar() {
    formAdmin.command.value="ibge";
	formAdmin.action.value="findIbge";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "listagem");
}

function ibgeExcluir(idIbge) {
	formAdmin.idIBGE.value = idIbge;
    formAdmin.command.value="ibge";
    if (confirm('Confirma excluséo deste registro?')) {
        formAdmin.action.value="deleteIbge";
		ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "listagem" );
	}
}

function ibgeEditar(idIbge) {
    formAdmin.idIBGE.value = idIbge;
    formAdmin.command.value="ibge";
    formAdmin.action.value="editIbge";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
}

/**********************************/
/******** Cédigo Serviéos *********/
/**********************************/

function codigoServicosSalvar() {
	formAdmin.command.value="codigoServicos";
	formAdmin.action.value="saveCodigoServicos";
	if(verificarResposta("codigo") && verificarResposta("descricao"))
		ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
}

function codigoServicosNovo() {
	formAdmin.command.value="codigoServicos";
	formAdmin.action.value="insert";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
}

function codigoServicosPesquisar() {
    formAdmin.command.value="codigoServicos";
	formAdmin.action.value="findServico";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "listagem");
}

function codigoServicosExcluir(idServico) {
	formAdmin.idServico.value = idServico;
    formAdmin.command.value="codigoServicos";
    if (confirm('Confirma excluséo deste registro?')) {
        formAdmin.action.value="deleteCodigoServicos";
		ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "listagem" );
	}
}

function codigoServicosEditar(idServico) {
    formAdmin.idServico.value = idServico;
    formAdmin.command.value="codigoServicos";
    formAdmin.action.value="editCodigoServicos";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
}

/**********************************/
/*********** Médicos **************/
/**********************************/

function medicosSalvar() {
	var email = document.getElementById("email");
	if(eEmail(email) || email.value==""){

		if( ValidarCPF(formAdmin.cpf) && verificarResposta("nome") ) {
			formAdmin.command.value="medicos";
			formAdmin.action.value="saveMedicos";
			if(verificarResposta("nome"))
				ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );	
		}
	}
	else
		alert("E-mail inválido");	
}

function medicosNovo() {
	formAdmin.command.value="medicos";
	formAdmin.action.value="insert";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
}

function medicosPesquisar() {
    formAdmin.command.value="medicos";
	formAdmin.action.value="findMedicos";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "listagem");
}

function medicosExcluir(idMedicos) {
	formAdmin.idMedicos.value = idMedicos;
    formAdmin.command.value="medicos";
    if (confirm('Confirma excluséo deste registro?')) {
        formAdmin.action.value="deleteMedicos";
		ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "listagem" );
	}
}

function medicosEditar(idMedicos) {
    formAdmin.idMedicos.value = idMedicos;
    formAdmin.command.value="medicos";
    formAdmin.action.value="editMedicos";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
}

/**********************************/
/******* Cliente Paciente *********/
/**********************************/

function changePessoa(valor) {
	if( valor == 'pf' ) {
		hideObject("pj_1");
		hideObject("pj_2");
		hideObject("pj_3");
		hideObject("pj_4");
		hideObject("pj_5");
		hideObject("pj_6");
		hideObject("pj_7");
		hideObject("pj_8");
		showTR("pf_1");
		showTR("pf_2");
		showTR("pf_3");
		showTR("pf_4");
		showTR("pf_5");
		showTR("pf_6");
		showTR("pf_7");
	} else if( valor == 'pj' ) {
		showTR("pj_1");
		showTR("pj_2");
		showTR("pj_3");
		showTR("pj_4");
		showTR("pj_5");
		showTR("pj_6");
		showTR("pj_7");
		showTR("pj_8");
		hideObject("pf_1");
		hideObject("pf_2");
		hideObject("pf_3");
		hideObject("pf_4");
		hideObject("pf_5");
		hideObject("pf_6");
		hideObject("pf_7");
	}
}
function verificarIdade(){
	var result = eDate.isOverAge(eDate.getNew({'dd/mm/yyyy': formAdmin.pfDataNascimento.value}), 18	);
	return result;
}

function clienteNovo() {
	formAdmin.command.value="cliente";
	formAdmin.action.value="insert";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
}

function clienteSalvar() {
	formAdmin.command.value="cliente";
	formAdmin.action.value="saveCliente";
	if( formAdmin.personalidade.value == "pj" ) {
		if( ValidarCNPJ(formAdmin.pjCNPJ) ) {
			if( verificarResposta("codigo") && verificarResposta("nome") && verificarResposta("personalidade") )
				ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
		}
	} else {
		if(verificarIdade()){
			if(ValidarCPF(formAdmin.pfCPF)){
				if( verificarResposta("codigo") && verificarResposta("nome") && verificarResposta("personalidade") )
					ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
			}
		}
		else{
			if( verificarResposta("codigo") && verificarResposta("nome") && verificarResposta("personalidade") )
				ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
		}
	}
}

function clientePesquisar() {
    formAdmin.command.value="cliente";
	formAdmin.action.value="findCliente";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "listagem");
}

function clienteExcluir(idCliente) {
	formAdmin.idCliente.value = idCliente;
    formAdmin.command.value="cliente";
    if (confirm('Confirma excluséo deste registro?')) {
        formAdmin.action.value="deleteCliente";
		ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "listagem" );
	}
}

function clienteEditar(idCliente) {
    formAdmin.idCliente.value = idCliente;
    formAdmin.command.value="cliente";
    formAdmin.action.value="editCliente";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal", "changePessoa(formAdmin.personalidade.value)" );
}

/**********************************/
/******* Controle *********/
/**********************************/
function sendChave() {
	formAdmin.command.value="intra";
	formAdmin.action.value="sendChave";
	formAdmin.submit();
}

function sendChaveAdm() {
	formAdmin.command.value="intra";
	formAdmin.action.value="chave2";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
}

/**********************************/
/******* Servico Prestado *********/
/**********************************/

function servicoNovo() {
	formAdmin.command.value="servico";
	formAdmin.action.value="insert";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
}

function servicoSalvar() {
	formAdmin.command.value="servico";
	formAdmin.action.value="saveServico";
	if(verificarResposta("codigo") && verificarResposta("descricao"))
		ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
}

function servicoPesquisar() {
    formAdmin.command.value="servico";
	formAdmin.action.value="findServico";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "listagem");
}

function servicoExcluir(idServico) {
	formAdmin.idServico.value = idServico;
    formAdmin.command.value="servico";
    if (confirm('Confirma excluséo deste registro?')) {
        formAdmin.action.value="deleteServico";
		ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "listagem" );
	}
}

function servicoEditar(idServico) {
    formAdmin.idServico.value = idServico;
    formAdmin.command.value="servico";
    formAdmin.action.value="editServico";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
}

/**********************************/
/*********    Recibo    ***********/
/**********************************/

function reciboNovo() {
	formAdmin.command.value="recibo";
	formAdmin.action.value="insert";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
}

function reciboSalvar(imprimir) {
	formAdmin.command.value="recibo";
	formAdmin.action.value="saveRecibo";
	var texto = formAdmin.idClienteResponsavel.options[formAdmin.idClienteResponsavel.selectedIndex].text;
	
	if(verificarResposta("idClienteResponsavel")){
		if(verificarResposta("idServico1")){
			if(texto.indexOf("(PF)") != -1){
				if(verificarResposta("idCliente")){
					if( imprimir ) {
						ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal", "reciboImprimir()" );
					} else {
						ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal", "changeCliente()" );
					}
				}
			} else {
				if( imprimir ) {
					ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal", "reciboImprimir()" );
				} else {
					ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal", "changeCliente()" );
				}
			}
		}
	}
}

function reciboPesquisar() {
	formAdmin.command.value="recibo";
	formAdmin.action.value="findRecibo";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "listagem");
}

function relatorioImprimir() {
    formAdmin.command.value="recibo";
	formAdmin.action.value="relatorioImprimir";
	var janelaCompleta = window.open("/allgenda/" + createQueryString(formAdmin), "listagem", "width=950,height=750,scrollbars=1,resizable=yes,menubar=yes,toolbar=yes");
}

function reciboExcluir(idRecibo) {
	formAdmin.idRecibo.value = idRecibo;
    formAdmin.command.value="recibo";
    if (confirm('Confirma cancelamento deste recibo?')) {
        formAdmin.action.value="deleteRecibo";
		ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "listagem" );
	}
}

function reciboEditar(idRecibo) {
    formAdmin.idRecibo.value = idRecibo;
    formAdmin.command.value="recibo";
    formAdmin.action.value="editRecibo";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal", "changeCliente()");
}

function reciboImprimir() {
	formAdmin.command.value="recibo";
	formAdmin.action.value="imprimirRecibo";
	var janelaCompleta = window.open("/allgenda/" + createQueryString(formAdmin), "listagem", "width=700,height=650,scrollbars=1,resizable=yes,menubar=yes,toolbar=yes");
}

function reciboImprimir(formAdmin) {
	formAdmin.command.value="recibo";
	formAdmin.action.value="imprimirRecibo";
	var janelaCompleta = window.open("/allgenda/" + createQueryString(formAdmin), "listagem", "width=700,height=650,scrollbars=1,resizable=yes,menubar=yes,toolbar=yes");
}

function reciboDMED() {
    formAdmin.command.value="recibo";
    formAdmin.action.value="gerarDMED";
    ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "listagem" );
}

function reciboNFe() {
	formAdmin.command.value="recibo";
	formAdmin.action.value="gerarNFe";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "listagem" );
}

function exportar() {
	var arquivo = document.getElementById("arquivo");
	formX.command.value="recibo";
	if(arquivo.value=="DMED"){
		formX.action.value="gerarDMED";
	}
	if(arquivo.value=="NFE"){
		formX.action.value="gerarNFe";
	}
	if(arquivo.value=="CLIENTE"){
		formX.action.value="gerarCliente";
	}
    
    ChamaPaginaArray("/allgenda/" + createQueryString(formX), "listagem" );
}

function checkValor( idServico, destino ) {
    formAdmin.command.value="recibo";
    formAdmin.action.value="checkValor";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin) + "&idServicoGenerico=" + idServico, "div_valor", "checkValor2('" + idServico + "','" + destino + "')" );
}

function checkValor2(idServico, destino) {
	eval("formAdmin." + destino + ".value = formAdmin.valorCheck_" + idServico + ".value" );
	calcularValorTotal( eval("formAdmin." + destino) );
}

function converterVirgula(obj) {
	obj.value = obj.value.replace(",",".");
}

function calcularValorTotal(obj) {
	converterVirgula(obj);
	var total = 0;
	try {
		var dataValor;
		
		for(var a = 1; a < 11; a++){
			dataValor = document.getElementsByName("valor"+a)[0].value;
			if(dataValor != "" && dataValor != "0.00" ){
				
				total += jQuery.isNumeric(dataValor) ? dataValor * 1 : 0;
			}
		}
	} catch(e){
	}
	
	formAdmin.pjIR.value = 0;
	formAdmin.pjCS.value = 0;
	formAdmin.pjPIS.value = 0;
	formAdmin.pjCOFINS.value = 0;
	
	var texto = formAdmin.idClienteResponsavel.options[formAdmin.idClienteResponsavel.selectedIndex].text;
	if( texto.indexOf("(PJ)") != -1 ) { // é PJ
		if(total >= 667.00 && total < 5000 ){
			var IR = total*0.015;
			
			formAdmin.pjIR.value = IR;
			// total += IR;
	
			formAdmin.pjIR.value = Math.round(IR*100)/100;
			formAdmin.pjPIS.value = 0;
			formAdmin.pjCS.value = 0;
			formAdmin.pjCOFINS.value = 0;
	
		}else if(total >= 5000){
			var PIS 	= total*0.0065;
			var COFINS 	= total*0.03;
			var CSLL 	= total*0.01;
			var IR = total*0.015;
	
			// total += IR;
			// total += PIS;
			// total += CSLL;
			// total += COFINS;
	
			formAdmin.pjIR.value =  Math.round(IR*100)/100;
			formAdmin.pjPIS.value = Math.round(PIS*100)/100;
			formAdmin.pjCS.value = Math.round(CSLL*100)/100;
			formAdmin.pjCOFINS.value = Math.round(COFINS*100)/100;
		}
	}
	
	total = Math.round(total*100)/100;
	formAdmin.valor.value = total;
}

function changeCliente(valor) {
	var texto = formAdmin.idClienteResponsavel.options[formAdmin.idClienteResponsavel.selectedIndex].text;

	if( texto.indexOf("(PJ)") != -1 ) { // é PJ
		// hideObject("cliente_paciente");
		showTR("pj_1");
		showTR("pj_2");
		showTR("pj_3");
		showTR("pj_4");
	} else {
		// showTR("cliente_paciente");
		hideObject("pj_1");
		hideObject("pj_2");
		hideObject("pj_3");
		hideObject("pj_4");
	}
	
	calcularValorTotal();
}

/***********************/
/***Recibo Manutenééo***/
/***********************/

function manutencaoSalvar() {
	formAdmin.command.value="reciboManutencao";
	formAdmin.action.value="saveManutencao";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
}

function manutencaoNovo() {
	formAdmin.command.value="reciboManutencao";
	formAdmin.action.value="insert";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
}

function manutencaoPesquisar() {
    formAdmin.command.value="reciboManutencao";
	formAdmin.action.value="findManutencao";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "listagem");
}

function manutencaoExcluir(idManutencao) {
	formAdmin.idManutencao.value = idManutencao;
    formAdmin.command.value="reciboManutencao";
    if (confirm('Confirma excluséo deste registro?')) {
        formAdmin.action.value="deleteManutencao";
		ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "listagem" );
	}
}

function manutencaoEditar(idManutencao) {
    formAdmin.idManutencao.value = idManutencao;
    formAdmin.command.value="reciboManutencao";
    formAdmin.action.value="editManutencao";
	ChamaPaginaArray("/allgenda/" + createQueryString(formAdmin), "principal" );
}


/**********************************/
/***   Mascaras e validaéées   ****/
/**********************************/

function eEmail(email){
    var exclude=/[^@\-\.\w]|^[_@\.\-]|[\._\-]{2}|[@\.]{2}|(@)[^@]*\1/;
    var check=/@[\w\-]+\./;
    var checkend=/\.[a-zA-Z]{2,3}$/;
    var texto = email.value;
    if(((texto.search(exclude) != -1)||(texto.search(check)) == -1)||(texto.search(checkend) == -1)){
    	return false;
	}
	else {
    	return true;
	}
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

function Mascara(campo){
    if(mascaraInteiro(campo)==false){
    	event.returnValue = false;
    }
    var opcao = document.getElementById("personalidade");
    var tamanho = document.getElementById("cpf_cnpj");
    if(opcao.value == "pf"){
    	tamanho.maxLength = 14;
    	return formataCampo(campo, '000.000.000-00', event);
    }else if(opcao.value == "pj"){
    	tamanho.maxLength = 18;
    	return formataCampo(campo, '00.000.000/0000-00', event);
    }
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




function ValidarCPF(Objcpf) {
	var cpf;
	var digito = new Array(11);
	var digito2 = new Array(11);
	var total_numero_d10 = 0;
	var total_numero_d11 = 0;
	var valor1;
	var valor2;

	cpf = Objcpf.value;
//	Retira os pontos (".") e traéos ("-") do CPF
	cpf = cpf.replace(".","");
	cpf = cpf.replace(".","");
	cpf = cpf.replace(".","");
	cpf = cpf.replace("-","");
	if (cpf=="") {
//		O usuário deixou o campo em banco
		alert("Digite um C.P.F para validar");
	}else if (cpf.length!=11) {
//		O cpf informado não possui 11 dígitos
		alert("O CPF digitado é inválido !\nNão possui 11 dígitos");
	}else if (isNaN(cpf)) {
		alert("O CPF informado não é um número válido");
	}else{
		/* Nessa parte iremos fazer a verificaééo completa do CPF */
//		Atribui valor és posições 10 e 11 do array (dígitos verificadores)
		digito[10] = cpf.charAt(9);
		digito[11] = cpf.charAt(10);

//		Faz uma array com os números de 10 a 2
		for (i=11;i>=2;i--) {
			digito2[i] = i;
		}
//		Verifica os dígitos informados no CPF
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
		
//		Verifica se os dígitos conferem
		if (digito[10]==valor1 && digito[11]==valor2) {
//			O CPF é válido
	    	return true;
		}else{
			alert("O CPF informado é inválido !");
			formAdmin.pfCPF.focus();
			return false;
		}
	}
}




/*
function ValidarCPF(Objcpf){
    var cpf = Objcpf.value;
    
    exp = /\.|\-/g
    cpf = cpf.toString().replace( exp, "" ); 
    var digitoDigitado = eval(cpf.charAt(9)+cpf.charAt(10));
    var soma1=0, soma2=0;
    var vlr =11;
    
    for(i=0;i<9;i++){
            soma1+=eval(cpf.charAt(i)*(vlr-1));
            soma2+=eval(cpf.charAt(i)*vlr);
            vlr--;
    }       
    soma1 = (((soma1*10)%11)==10 ? 0:((soma1*10)%11));
    soma2=(((soma2+(2*soma1))*10)%11);
    
    var digitoGerado=(soma1*10)+soma2;
    
    if(cpf == "11111111111" || cpf == "22222222222" || cpf ==
		"33333333333" || cpf == "44444444444" || cpf == "55555555555" || cpf ==
		"66666666666" || cpf == "77777777777" || cpf == "88888888888" || cpf ==
		"00000000000" ){
    	
    	digitoGerado = "";
    }
    
    if(digitoGerado!=digitoDigitado) {
    	alert('CPF Invalido!');
    	formAdmin.pfCPF.focus();
    	return false;
    } else {
    	return true;
    }        
}
*/

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
    	alert('CNPJ Invalido!');
    	formAdmin.pjCNPJ.focus();
    	return false;
    } else {
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

function verificarResposta(nome){
	var objeto = eval("formAdmin."+nome);
	if(objeto.value == "" || objeto.value == "0"){
		alert("Campo obrigatério: " + nome);
		objeto.focus();
		return false;
	}
	return true;
}

function fechar() {
	window.self.close();
}

function formataValor(campo,tammax,teclapres,decimal){
	var tecla;
	if(window.event) 
		tecla = teclapres.keyCode;
	else 
		tecla = teclapres.which;
	vr = limparValor(campo.value,"0123456789");
	tam = vr.length;
	dec=decimal;
	if(tam < tammax && tecla != 8){ 
		tam = vr.length + 1 ; 
	}
	if(tecla == 8 ){
		tam = tam - 1 ; 
	}
	if( tecla == 8 || tecla >= 48 && tecla <= 57 || tecla >= 96 && tecla <= 105 ){
		if( tam <= dec ){
			campo.value = vr ; 
		}
		if( (tam > dec) && (tam <= 5) ){
			campo.value = vr.substr( 0, tam - 2 ) + "," + vr.substr( tam - dec, tam ) ; 
		}
		if( (tam >= 6) && (tam <= 8) ){
			campo.value = vr.substr( 0, tam - 5 ) + "." + vr.substr( tam - 5, 3 ) + "," + vr.substr( tam - dec, tam ) ; 
		}
		if( (tam >= 9) && (tam <= 11) ){
			campo.value = vr.substr( 0, tam - 8 ) + "." + vr.substr( tam - 8, 3 ) + "." + vr.substr( tam - 5, 3 ) + "," + vr.substr( tam - dec, tam ) ; 
		}
		if( (tam >= 12) && (tam <= 14) ){
			campo.value = vr.substr( 0, tam - 11 ) + "." + vr.substr( tam - 11, 3 ) + "." + vr.substr( tam - 8, 3 ) + "." + vr.substr( tam - 5, 3 ) + "," + vr.substr( tam - dec, tam ) ; 
		}
		if( (tam >= 15) && (tam <= 17) ){
			campo.value = vr.substr( 0, tam - 14 ) + "." + vr.substr( tam - 14, 3 ) + "." + vr.substr( tam - 11, 3 ) + "." + vr.substr( tam - 8, 3 ) + "." + vr.substr( tam - 5, 3 ) + "," + vr.substr( tam - 2, tam ) ;
		}
	} else{
		return false;
	}
}

function limparValor(valor, validos) {
	// retira caracteres invalidos da string
	var result = "";
	var aux;
	for (var i=0; i < valor.length; i++) {
		aux = validos.indexOf(valor.substring(i, i+1));
		if (aux>=0) {
			result += aux;
		}
	}
	return result;
}

//Funéées para manipular dadas

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
			if (i['mm/dd/yyyy']) {
				s = i['mm/dd/yyyy'].split('/');
				i.month = s[0];
				i.day = s[1]; 
				i.year = s[2];
			}
			if (i['yyyy/mm/dd']) {
				s = i['mm/dd/yyyy'].split('/');
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
