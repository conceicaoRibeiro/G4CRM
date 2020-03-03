$(document).ready(function() {
  console.log(OFERTA_SELECIONADA);
  abreJanelaConteudo('.item-breadcrumb-link');
  $('.bloco-conteudo').show('slow');
  $('#campo-busca').hide();
  $('.item-menu-principal').fadeIn('fast');
  buscaEtapasFunilDeVendas();
  descreveOferta();
});

/*
* preenche a descricao da oferta.
*/
function descreveOferta() {
  $('#descricao-oferta').html(OFERTA_SELECIONADA.descricao);
}

/*
* recupera a lista de etapas do funil de vendas.
*/
function buscaEtapasFunilDeVendas() {
  $.ajax({
    url: '../funiletapa/lista',
    type: 'GET',
    dataType: 'json',
    contentType: 'application/json; charset=utf-8'
  })
  .done(function(data) {
    console.log(data);
    FUNIL_ETAPAS = data;
    montaRaiasFunilDeVendas(data);
  })
  .fail(function() {
    console.log("error");
  })
  .always(function() {
    console.log("complete");
  });
}

/*
* constroi as raias do funil de vendas e a raia inicial.
*/
function montaRaiasFunilDeVendas(etapas) {
  //monta a classe do bootstrap com a divisao da tela (12 colunas) pelo numero de etapas do funil mais a raia inicial.
  let divisor = Math.round(12/(etapas.length+1));
  let classeColuna = 'col-md-'+divisor;

  let raia = '';
  $('.raias').append(raia);
  $.each(etapas, function(index, el) {
    raia='';
    raia+='<div class="'+classeColuna+' envelope-raia">';
    raia+='<div funil-etapa-id="'+el.funilEtapaId+'" index="'+index+'" class="raia">';
    raia+='<h6>'+el.descricao+'</h6>'
    raia+='</div>';
    $('.raias').append(raia);
  });
  //busca ClienteOferta depois de montar as raias do funil de vendas.
  listaClienteOferta();
}

/*
* busca a lista de ClienteOferta da oferta selecionada.
*/
function listaClienteOferta() {
  $.ajax({
    url: '../clienteoferta/listaporoferta',
    type: 'POST',
    dataType: 'json',
    data: JSON.stringify(OFERTA_SELECIONADA),
    contentType: 'application/json; charset=utf-8'
  })
  .done(function(data) {
    CLIENTES_OFERTA = data.entity;
    console.log(data.entity);
    montaCardsClienteOferta(data.entity);
  })
  .fail(function() {
    console.log("error");
  })
  .always(function() {
    console.log("complete");
  });

}

/*
* monta os elementos com os nomes dos clientes.
*/
function montaCardsClienteOferta(clientes) {
  let card = '';
  let raia = '';
  $.each(clientes, function(index, el) {
    card='';
    card+='<div index="'+index+'" class="col-md-12 cliente-oferta">';
    card+='<p>'+el.clienteOfertaId.cliente.nome+' '+el.clienteOfertaId.cliente.sobrenome+'</p>';
    card+='<p>R$: '+el.preco+'</p>';
    card+='</div>';
    if(el.funilEtapa==null) {
      $('.raia[index="-1"]').append(card);
    } else {
      $('.raia[funil-etapa-id="'+el.funilEtapa.funilEtapaId+'"]').append(card);
    }
  });
  controlaDragAndDrop();
}

/*
* vincula os eventos de drag and drop dos cards.
*/
function controlaDragAndDrop() {
  $('.cliente-oferta').draggable({
    stack: ".cliente-oferta",
    revert : function(event, ui) {
      return !event;
    }
  });

  $('.raia').droppable({
    drop:function(e, ui) {
        $(e.target).append($(ui.draggable).detach().css({'top':'', 'left':''}));
        //atualiza a etapa no funil de vendas do cliente.
        atualizaClienteOferta(ui.draggable);
        atualizaTotalRaia();
      }
    });
    atualizaTotalRaia();
}

/*
* atualiza a etapa do funil de vendas onde esta o cliente.
*/
function atualizaClienteOferta(elemento) {
  let funilEtapa = '';
  let indiceFunilEtapa = $(elemento).parent('div').attr('index');
  let clienteOferta = CLIENTES_OFERTA[$(elemento).attr('index')];
  if(indiceFunilEtapa!='-1') {
    funilEtapa = FUNIL_ETAPAS[indiceFunilEtapa];
  } else {
    funilEtapa = null;
  }
  clienteOferta.funilEtapa = funilEtapa;
  $.ajax({
    url: '../clienteoferta/atualiza',
    type: 'POST',
    dataType: 'json',
    data: JSON.stringify(clienteOferta),
    contentType: 'application/json; charset=utf-8'
  })
  .done(function(data) {
    console.log(data.entity);
  })
  .fail(function() {
    console.log("error");
  })
  .always(function() {
    console.log("complete");
  });
}

/*
* realiza o somatorio das ofertas dos clientes em cada raia.
*/
function atualizaTotalRaia() {
  let total = 0
  $('.total-na-etapa').remove();
  $('.raia').each(function(index, el) {
    total=0;
    $(this).find('.cliente-oferta').each(function(index, elm) {
      total+=CLIENTES_OFERTA[$(elm).attr('index')].preco;
    });
    $(this).after('<p class="total-na-etapa">Total na etapa: '+total+'</p>');
  });
}
