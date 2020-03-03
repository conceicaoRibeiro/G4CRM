var painel = '';
var produtos = '';

$(document).ready(function() {
  $('.bloco-conteudo').show('slow');
  $('#campo-busca').hide();
  $('.item-menu-principal').fadeIn('fast');
  retornaHome();
  listaOfertas($('#tabela'), true);
  listaProdutos($('.lista-de-campos'));
  abreModalNovaOferta($('#btn-nova-oferta'));
  abreModalRelatorios($('#btn-relatorio-oferta'));
  salvar($('#salvar-oferta'),null);
});

function listaOfertas(tabela, loadInicial) {
  $.ajax({
    url: '../oferta/lista',
    type: 'GET',
    dataType: 'json',
    contentType: 'application/json; charset=utf-8'
  })
  .done(function(data) {
    if(loadInicial) {
      console.log(data);
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
        defaultContent: '<i class="fas fa-filter"></i>'
      },
      {
        targets: [ 2 ],
        data: 'descricao',
        orderable: false,
        defaultContent: ''
      },
      {
        targets: [ 3 ],
        data: 'dataInicio',
        orderable: false,
        defaultContent: ''
      },
      {
        targets: [ 4 ],
        data: 'dataFim',
        orderable: false,
        defaultContent: ''
      },
      {
        targets: [ 5 ],
        data: 'preco',
        orderable: false,
        defaultContent: ''
      },
      {
        targets: [ 6 ],
        data: 'produto.descricao',
        orderable: false,
        defaultContent: ''
      },
      {
        targets: [ 7 ],
        data: 'status',
        orderable: false,
        defaultContent: ''
      },
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
      abreModalEditarOferta(row,data);
      abreFunilDeVendas(row,data);
    }
  });
  escondeBotoes();
  formataCampoBusca();
}

/*
* lista os niveis de instrucao.
*/
function listaProdutos(select) {
  $.ajax({
    url: '../produto/lista',
    type: 'GET',
    dataType: 'json',
    contentType: 'application/json; charset=utf-8'
  })
  .done(function(data) {
    let lista='';
    console.log(data);
    produtos = data;
    $.each(data, function(index, el) {
      lista+='<option value="'+el.descricao+'">'+el.descricao+'</option>';
    });
    $(select).append(lista);
  })
  .fail(function() {
    console.log("error");
  })
  .always(function() {
    console.log("complete");
  });

}

/*
* abre o modal de nova oferta.
*/
function abreModalNovaOferta(btnNovaOferta) {
  $(btnNovaOferta).click(function(event) {
    $('#nome').val('');
    $('#status').val('');
    $('#data-inicio').val('');
    $('#data-fim').val('');
    $('#data-inicio').val('');
    $('#produto').val('');
    $('#valor').val('');
    $('#modal-nova-oferta').modal('show');
  });
}

/*
* abre o modal de relatorios.
*/
function abreModalRelatorios(btnRelatorios) {
  $(btnRelatorios).click(function(event) {
    $('#modal-relatorio-oferta').modal('show');
    abreRelatorio($('#exibir-relatorio'));
  });
}

/*
* abre a tela do relatorio selecionado
*/
function abreRelatorio(btnAbrir) {
  $(btnAbrir).click(function(event) {
    $('#modal-relatorio-oferta').modal('hide');
    $('.desktop').load($('#lista-relatorio-oferta').val()+'.html');
    $.getScript($('#lista-relatorio-oferta').val()+'.js');
  });
}

/*
* abre o modal de editar oferta.
*/
function abreModalEditarOferta(row, data) {
  $(row).children('td').eq(0).click(function(event) {
    $('#editar-nome').val(data.descricao);
    $('#editar-status').val(data.status);
    $('#editar-data-inicio').val(data.dataInicio);
    $('#editar-data-fim').val(data.dataFim);
    $('#editar-data-inicio').val(data.dataInicio);
    $('#editar-produto').val(data.produto.descricao);
    $('#editar-valor').val(data.preco);
    $('#modal-editar-oferta').modal('show');
    salvar($('#editar-oferta'), data.ofertaId);
  });
}

/*
* abre a tela de administracao de funil de vendas da oferta selecionada.
*/
function abreFunilDeVendas(row, data) {
  $(row).children('td').eq(1).click(function(event) {
    OFERTA_SELECIONADA = data;
    $('.desktop').load('componentes/oferta/funil-de-vendas/funil-de-vendas.html');
    $.getScript('componentes/oferta/funil-de-vendas/funil-de-vendas.js');
  });
}

/*
* Salva um oferta.
*/
function salvar(btnSalvar, ofertaId) {
  $(btnSalvar).click(function(event) {
    let descricao = '';
    let dataInicio = '';
    let dataFim = '';
    let valor = '';
    let produto = '';
    let preco = '';
    let status = '';
    if(ofertaId==null) {
      descricao = $('#nome').val();
      dataInicio = moment($('#data-inicio').val()).format('DD/MM/YYYY');
      dataFim = moment($('#data-fim').val()).format('DD/MM/YYYY');
      preco = $('#valor').val();
      status = $('#status').val();
      $.each(produtos, function(index, el) {
        if(el.descricao==$('#nova-oferta-produto').val()) {
          produto=el;
        }
      });
    } else {
      descricao = $('#editar-nome').val();
      dataInicio = moment($('#editar-data-inicio').val()).format('DD/MM/YYYY');
      dataFim = moment($('#editar-data-fim').val()).format('DD/MM/YYYY');
      preco = $('#editar-valor').val();
      status = $('#editar-status').val();
      $.each(produtos, function(index, el) {
        if(el.descricao==$('#editar-oferta-produto').val()) {
          produto=el;
        }
      });
    }
    let oferta = {
      ofertaId: ofertaId,
      descricao: descricao,
      dataInicio: dataInicio,
      dataFim: dataFim,
      preco: preco,
      status: status,
      produto: produto
    }
    $.ajax({
      url: '../oferta/salva',
      type: 'POST',
      dataType: 'json',
      data: JSON.stringify(oferta),
      contentType: 'application/json; charset=utf-8'
    })
    .done(function() {
      listaOfertas(null, false);
    })
    .fail(function() {
      console.log("error");
    })
    .always(function() {
      console.log("complete");
    });

  });
}
