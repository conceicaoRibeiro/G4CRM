var camposAdicionais = '';
var painel = '';
$(document).ready(function() {
  $('.bloco-conteudo').show('slow');
  $('#campo-busca').hide();
  $('.item-menu-principal').fadeIn('fast');
  retornaHome();
  listaClientes($('#tabela'), true);
  vinculaNovoClienteAdicionarCampos($('#novo-cliente-adicionar-campo'), $('#novo-cliente-campos-adicionais'));
  abreModalNovoCliente($('#btn-novo-cliente'));
  abreModalRelatorios($('#btn-relatorio-cliente'));
  salvaCliente($('#salvar-cliente'));
});

function listaClientes(tabela, loadInicial) {
  $.ajax({
    url: '../cliente/lista',
    type: 'GET',
    dataType: 'json',
    contentType: 'application/json; charset=utf-8'
  })
  .done(function(data) {
    if(loadInicial) {
      carregaTabela(tabela, data);
    } else {
      painel.clear().draw();
      painel.rows.add(data).draw();
    }
  })
  .fail(function() {
    console.log("error");
  })
  .always(function() {
    console.log("complete");
  });
}

function carregaTabela(tabela, data) {
  painel = $(tabela).DataTable({
    data: data,
    dom:'tBf',
    paging: false,
    order: [],
    scrollResize: true,
    info: false,
    buttons: [
      'excel', 'pdf'
    ],
    columns: [
        { targets: 0, className: "text-center" },
        { targets: 1, className: "text-center" },
    ],
    columnDefs: [
      {
        targets: [ 0 ],
        data: null,
        orderable: false,
        defaultContent: '<i class="fas fa-pencil-alt"></i>'
      },
      {
        targets: [ 1 ],
        data: null,
        orderable: false,
        defaultContent: '<i class="far fa-clock"></i>'
      },
      {
        targets: [ 2 ],
        data: 'cpf',
        orderable: false,
        defaultContent: ''
      },
      {
        targets: [ 3 ],
        data: 'nome',
        orderable: false,
        defaultContent: ''
      },
      {
        targets: [ 4 ],
        data: 'sobrenome',
        orderable: false,
        defaultContent: ''
      },
      {
        targets: [ 5 ],
        data: 'email',
        orderable: false,
        defaultContent: ''
      },
      {
        targets: [ 6 ],
        data: 'status',
        orderable: false,
        defaultContent: ''
      }
    ],
    language: {
      lengthMenu: "Exibindo _MENU_ resultados por página",
      zeroRecords: "Nenhum registro encontrado.",
      loadingRecords: "Carregando registros...",
      processing: "Processando...",
      info: "",
      search: "",
    },
    createdRow: function(row, data) {
      abreModalEditarCliente(row,data);
      abreTimeLineCliente(row, data);
    }
  });
  escondeBotoes();
  formataCampoBusca();
}

/*
* abre a tela de administracao de acoes com o cliente/timeline.
*/
function abreTimeLineCliente(row, data) {
  $(row).children('td').eq(1).click(function(event) {
    CLIENTE_SELECIONADO = data;
    $('.desktop').load('componentes/cliente/linha-do-tempo/linha-do-tempo.html');
    $.getScript('componentes/cliente/linha-do-tempo/linha-do-tempo.js');
  });
}

/*
* abre o modal de relatorios.
*/
function abreModalRelatorios(btnRelatorios) {
  $(btnRelatorios).click(function(event) {
    $('#modal-relatorio-cliente').modal('show');
    abreRelatorio($('#exibir-relatorio'));
  });
}

/*
* abre a tela do relatorio selecionado
*/
function abreRelatorio(btnAbrir) {
  $(btnAbrir).click(function(event) {
    $('#modal-relatorio-cliente').modal('hide');
    $('.desktop').load($('#lista-relatorio-cliente').val()+'.html');
    $.getScript($('#lista-relatorio-cliente').val()+'.js');
  });
}

/*
* abre o modal de novo cliente.
*/
function abreModalNovoCliente(btnNovoCliente) {
  $(btnNovoCliente).click(function(event) {
    $('#cpf').val('');
    $('#nome').val('');
    $('#sobrenome').val('');
    $('#email').val('');
    $('#status').val('');
    $('#novo-cliente-campos-adicionais .campo-adicional').remove();
    $('#modal-novo-cliente').modal('show');
  });
}

/*
* abre o modal de novo cliente.
*/
function abreModalEditarCliente(row,data) {
  $(row).children('td').eq(0).click(function(event) {
    $('#editar-cpf').val(data.cpf);
    $('#editar-nome').val(data.nome);
    $('#editar-sobrenome').val(data.sobrenome);
    $('#editar-email').val(data.email);
    $('#editar-status').val(data.status);
    $('#editar-cliente-campos-adicionais .campo-adicional').remove();
    //buscar dados adicionais do cliente e preencher a lista.
    $.ajax({
      url: '../cliente/listadadoscliente',
      type: 'POST',
      dataType: 'json',
      data: JSON.stringify(data),
      contentType: 'application/json; charset=utf-8'
    })
    .done(function(clienteDados) {
      console.log(clienteDados);
      $.each(clienteDados, function(index, el) {
        $('#editar-cliente-adicionar-campo').click();
        $('#editar-cliente-campos-adicionais .campo-adicional').find('.lista-de-campos').eq(index).val(el.dadoTipo.descricao);
        $('#editar-cliente-campos-adicionais .campo-adicional').find('.valor-dado').eq(index).val(el.valor);
      });
    })
    .fail(function() {
      console.log("error");
    })
    .always(function() {
      console.log("complete");
    });
    editaCliente($('#editar-cliente'), data.clienteId);
    $('#modal-editar-cliente').modal('show');
  });
}

/*
* vincula o evento de adicionar novos campos para um novo cliente.
*/
function vinculaNovoClienteAdicionarCampos(btnNovoCampo, divListaCampos) {
  $.ajax({
    url: '../dadotipo/lista',
    type: 'GET',
    dataType: 'json',
    contentType: 'application/json; charset=utf-8'
  })
  .done(function(data) {
    camposAdicionais = data;
    let dadosAdicionais = ''+
      '<div class="form-group col-lg-12 col-12 campo-adicional">'+
  				'<div class="row">'+
  						'<div class="col-4">'+
  								'<select class="form-control lista-de-campos" name="">';
                  $.each(camposAdicionais, function(index, el) {
                    dadosAdicionais+='<option value="'+el.descricao+'">'+el.descricao+'</option>';
                  });
                  dadosAdicionais+=''+
  								'</select>'+
  						'</div>'+
  						'<div class="col-7">'+
  								'<input type="text" class="form-control valor-dado" placeholder="valor...">'+
  						'</div>'+
  						'<div class="col-1">'+
  								'<button type="button" class="btn btn-danger btn-excluir btn-excluir-campo btn-adicionar-remover-campo">'+
  										'<i class="far fa-trash-alt mr-0"></i>'+
  								'</button>'+
  						'</div>'+
  				'</div>'+
  		'</div>';
      $(btnNovoCampo).click(function(event) {
        $(divListaCampos).append(dadosAdicionais);
        vinculaClienteRemoveCampoAdicional($('.btn-excluir-campo'));
      });
      vinculaEditarClienteAdicionarCampos($('#editar-cliente-adicionar-campo'), $('#editar-cliente-campos-adicionais'), dadosAdicionais);
  })
  .fail(function() {
    console.log("error");
  })
  .always(function() {
    console.log("complete");
  });
}

/*
* vincula o evento de adicionar novos campos para editar um cliente.
*/
function vinculaEditarClienteAdicionarCampos(btnNovoCampo, divListaCampos, dadosAdicionais) {
  $(btnNovoCampo).click(function(event) {
    $(divListaCampos).append(dadosAdicionais);
    vinculaClienteRemoveCampoAdicional($('.btn-excluir-campo'));
  });
}

/*
* vincula evento para remover campos adicionais.
*/
function vinculaClienteRemoveCampoAdicional(btnRemover) {
  $(btnRemover).click(function(event) {
    $(this).parent().parent().parent('.campo-adicional').remove();
  });
}

function salvaCliente(btnSalvarCliente) {
  $(btnSalvarCliente).click(function(event) {
    console.log(camposAdicionais);
    // busca no array de campos adicionais cada objeto selecionado e forma os objetos clienteDado a serem enviados.
    let clienteDados = [];
    $('#novo-cliente-campos-adicionais .lista-de-campos').each(function(index, el) {
      $.each(camposAdicionais, function(index, campo) {
        if($(el).val()==campo.descricao) {
          let clienteDado = {}
          clienteDado.dadoTipo = campo;
          clienteDado.valor = $(el).parent().parent().find('.valor-dado').val();
          clienteDado.status = 'A';
          clienteDados.push(clienteDado);
        }
      });
    });
    console.log(clienteDados);
    let clienteTO = {
      cliente: {
        cpf: $('#cpf').val(),
        nome: $('#nome').val(),
        sobrenome: $('#sobrenome').val(),
        email: $('#email').val(),
        status: $('#status').val(),
      },
      dadosAdicionais: clienteDados
    }
    $.ajax({
      url: '../cliente/insere',
      type: 'POST',
      dataType: 'json',
      data: JSON.stringify(clienteTO),
      contentType: 'application/json; charset=utf-8'
    })
    .done(function(data) {
      console.log(data);
      listaClientes(null,false);
    })
    .fail(function() {
      console.log("error");
    })
    .always(function() {
      console.log("complete");
    });
  });
}

function editaCliente(btnSalvarCliente, clienteId) {
  $(btnSalvarCliente).click(function(event) {
    console.log(camposAdicionais);
    // busca no array de campos adicionais cada objeto selecionado e forma os objetos clienteDado a serem enviados.
    let clienteDados = [];
    $('#editar-cliente-campos-adicionais .lista-de-campos').each(function(index, el) {
      $.each(camposAdicionais, function(index, campo) {
        if($(el).val()==campo.descricao) {
          let clienteDado = {}
          clienteDado.dadoTipo = campo;
          clienteDado.valor = $(el).parent().parent().find('.valor-dado').val();
          clienteDado.status = 'A';
          clienteDados.push(clienteDado);
        }
      });
    });
    console.log(clienteDados);
    console.log(clienteId);
    let clienteTO = {
      cliente: {
        clienteId: clienteId,
        cpf: $('#editar-cpf').val(),
        nome: $('#editar-nome').val(),
        sobrenome: $('#editar-sobrenome').val(),
        email: $('#editar-email').val(),
        status: $('#editar-status').val(),
      },
      dadosAdicionais: clienteDados
    }
    $.ajax({
      url: '../cliente/edita',
      type: 'POST',
      dataType: 'json',
      data: JSON.stringify(clienteTO),
      contentType: 'application/json; charset=utf-8'
    })
    .done(function(data) {
      console.log(data);
      listaClientes(null,false);
    })
    .fail(function() {
      console.log("error");
    })
    .always(function() {
      console.log("complete");
    });
  });
}
