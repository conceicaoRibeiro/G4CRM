$(document).ready(function() {
  console.log(CLIENTE_SELECIONADO);
  abreJanelaConteudo('.item-breadcrumb-link');
  $('.bloco-conteudo').show('slow');
  $('#campo-busca').hide();
  $('.item-menu-principal').fadeIn('fast');
  retornaHome();
  carregaDadosCliente();
  carregaOfertas();
  carregaAcoes();
  changeComboOferta();
  changeComboAcao();
  salvar();
  listaAcoesCliente();
});

/*
* Busca os dados do cliente no JSON e exibe na area de detalhes.
*/
function carregaDadosCliente() {
  $('#nome-do-cliente').html(CLIENTE_SELECIONADO.nome+' '+CLIENTE_SELECIONADO.sobrenome);
  $('#cpf-do-cliente').html(CLIENTE_SELECIONADO.cpf);
  $('#email-do-cliente').html(CLIENTE_SELECIONADO.email);
}

/*
* preenche a combo com as ofertas
*/
function carregaOfertas() {
  $.ajax({
    url: '../oferta/lista',
    type: 'GET',
    dataType: 'json',
    contentType: 'application/json; charset=utf-8'
  })
  .done(function(data) {
    console.log(data);
    OFERTAS = data;
    $.each(data, function(index, el) {
      $('#selecionar-oferta').append('<option value='+index+'>'+el.descricao+'</option>');
    });
  })
  .fail(function() {
    console.log("error");
  })
  .always(function() {
    console.log("complete");
  });
}

/*
* preenche a combo com as ofertas
*/
function carregaAcoes() {
  $.ajax({
    url: '../acao/lista',
    type: 'GET',
    dataType: 'json',
    contentType: 'application/json; charset=utf-8'
  })
  .done(function(data) {
    console.log(data);
    ACOES = data;
    $.each(data, function(index, el) {
      $('#selecionar-acao').append('<option value='+index+'>'+el.descricao+'</option>');
    });
  })
  .fail(function() {
    console.log("error");
  })
  .always(function() {
    console.log("complete");
  });
}

/*
* vincula o evento de click do botao de salvar aos eventos de salvar.
*/
function salvar() {
  $('#salvar-acao').click(function(event) {
    salvaClienteOferta();
  });
}

/*
* Ao salvar a primeira acao, salva o objeto ClienteOferta
*/
function salvaClienteOferta() {
  adicionaLoader();
  let url = '';
  let clienteOfertaId = '';
  let clienteOferta = '';
  let payload = '';
  let preco = '';
  if($('#selecionar-oferta').val()!='') {
    url = '../clienteoferta/salva';
    clienteOfertaId = {
      oferta: {
        ofertaId: OFERTAS[$('#selecionar-oferta').val()].ofertaId
      },
      cliente: {
        clienteId: CLIENTE_SELECIONADO.clienteId
      }
    }
    if($('#selecionar-acao').val()=='0') {
      preco=$('#novo-valor').val();
    } else {
      preco=OFERTAS[$('#selecionar-oferta').val()].preco
    }
    clienteOferta = {
      clienteOfertaId: clienteOfertaId,
      descricao: OFERTAS[$('#selecionar-oferta').val()].descricao,
      preco: preco
    }
    payload = JSON.stringify(clienteOferta);
    $.ajax({
      url: url,
      type: 'POST',
      dataType: 'json',
      data: payload,
      contentType: 'application/json; charset=utf-8'
    })
    .done(function(data) {
      console.log(data);
      //chamar ajax que salva na tabela acao_usuario_cliente ou acao_usuario_cliente_oferta
      salvaAcaoUsuario(true,data.entity);
    })
    .fail(function() {
      console.log("error");
    })
    .always(function(){
      removeLoader();
    });
  } else {
    salvaAcaoUsuario(false,null);
  }
}

function changeComboOferta() {
  $('#selecionar-oferta').change(function(event) {
    if($('#selecionar-oferta').val()!='') {
      buscaClienteOferta();
    } else {
      listaAcoesCliente();
    }
  });
}

function changeComboAcao() {
  $('#selecionar-acao').change(function(event) {
    $('#novo-valor').val('');
    if($('#selecionar-acao').val()==0) {
      $('.div-novo-valor').show();
    } else {
      $('.div-novo-valor').hide();
    }
  });
}

/*
* busca clienteOferta.
*/
function buscaClienteOferta() {
  let clienteOferta = '';
  let clienteOfertaId = {
    cliente: CLIENTE_SELECIONADO,
    oferta: OFERTAS[$('#selecionar-oferta').val()]
  }
  $.ajax({
    url: '../clienteoferta/buscaporid',
    type: 'POST',
    dataType: 'json',
    contentType: 'application/json; charset=utf-8',
    data: JSON.stringify(clienteOfertaId),
  })
  .done(function(data) {
    console.log(data);
    listaAcoesCliente(data.entity);
  })
  .fail(function() {
    console.log("error");
  })
  .always(function() {
    console.log("complete");
  });
}

function salvaAcaoUsuario(isClienteOferta, clienteOferta) {
  let acaoUsuarioClienteOferta = '';
  let acaoUsuarioCliente = '';
  let url = '';
  if(isClienteOferta) {
    acaoUsuarioClienteOferta = {
      clienteOferta: clienteOferta,
      acao: ACOES[$('#selecionar-acao').val()],
      descricao: $('#descrever-acao').val()
    }
    url = '../acaousuarioclienteoferta/salva';
    payload = JSON.stringify(acaoUsuarioClienteOferta);
  } else {
    acaoUsuarioCliente = {
      cliente: CLIENTE_SELECIONADO,
      acao: ACOES[$('#selecionar-acao').val()],
      descricao: $('#descrever-acao').val()
    }
    url = '../acaousuariocliente/salva';
    payload = JSON.stringify(acaoUsuarioCliente);
  }
  $.ajax({
    url: url,
    type: 'POST',
    dataType: 'json',
    contentType: 'application/json; charset=utf-8',
    data: payload,
  })
  .done(function(data) {
    console.log(data);
    listaAcoesCliente(clienteOferta);
  })
  .fail(function() {
    console.log("error");
  })
  .always(function() {
    console.log("complete");
  });
}

/*
* Recupera a lista de acoes com o cliente.
*/
function listaAcoesCliente(clienteOferta) {
  let url = '';
  let payload = '';
  $('.timeline').html('');
  if($('#selecionar-oferta').val()=='') {
    url = '../acaousuariocliente/lista';
    payload = JSON.stringify(CLIENTE_SELECIONADO);
  } else {
    // fazer ainda.
    url = '../acaousuarioclienteoferta/lista';
    payload = JSON.stringify(clienteOferta);
  }
  $.ajax({
    url: url,
    type: 'POST',
    dataType: 'json',
    data: payload,
    contentType: 'application/json; charset=utf-8'
  })
  .done(function(data) {
    $('#descrever-acao').val('');
    console.log(data);
    let dadosTimeline = '';
    let i='';
    $('.timeline').html(dadosTimeline);
    $.each(data.entity, function(index, el) {
      dadosTimeline='<li>';
      dadosTimeline+='<a>'+el.acao.descricao+'</a>';
      dadosTimeline+='<a class="float-right">'+el.dataAcao+'</a>';
      dadosTimeline+='<p>'+el.descricao+'</p>';
      dadosTimeline+='</li>';
      $('.timeline').prepend(dadosTimeline);
    });
  })
  .fail(function() {
    console.log("error");
  })
  .always(function() {
    console.log("complete");
  });
}
