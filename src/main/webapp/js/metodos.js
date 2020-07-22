
function selecionaItens(selectMany) {
	document.getElementById("selectMany:selectItems").disabled = true;
	
}

function event(pag){
	 window.location = "reservas_alimento.xhtml";
}

function evento(tipo_reservas){
    alert('O valor do radio clicado é '+tipo_reservas);
    
    if(tipo_reservas.equals("refeicao")){
    	$("#r").load("/"+reservas_refeicoes.xhtml);
    }
    else if(tipo_reservas.equals("alimento")){
    	$("#r").load("/"+reservas_alimento.xhtml);
    }
    else if(tipo_reservas.equals("aluno")){
    	$("#r").load("/"+reservas_alunos.xhtml);

        //document.getElementById("reservas").innerHTML = "reservas_alunos.xhtml";

    }
    

}