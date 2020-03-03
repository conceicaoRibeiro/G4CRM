var painel = '';
$(document).ready(function() {
  $('.bloco-conteudo').show('slow');
  $('#campo-busca').hide();
  $('.item-menu-principal').fadeIn('fast');
  abreJanelaConteudo('.item-breadcrumb-link');
  retornaHome();
  removeFundoModal();
  listaOfertasDataFechamento($('#tabela'), true);
  realizaBusca();
});

/*
* remove o fundo do modal quando a tela e carregada.
*/
function removeFundoModal() {
  $('body').removeAttr('cz-shortcut-listen');
  $('body').removeClass('modal-open');
  $('.modal-backdrop').remove();
}

function listaOfertasDataFechamento(tabela, loadInicial) {
  let dataInicial = $('#data-inicial').val();
  let dataFinal = $('#data-final').val();
  $.ajax({
    url: '../acaousuarioclienteoferta/listaporperiodo',
    type: 'GET',
    data: {dataInicial: dataInicial, dataFinal: dataFinal},
    contentType: 'application/json; charset=utf-8'
  })
  .done(function(data) {
    if(loadInicial) {
      carregaTabela(tabela, data.entity);
    } else {
      painel.clear().draw();
      painel.rows.add(data.entity).draw();
    }
  })
  .fail(function() {
    console.log("error");
  })
  .always(function() {
    console.log("complete");
  });
}

function realizaBusca() {
  $('#btn-filtrar').click(function(event) {
    listaOfertasDataFechamento(null,false);
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
    columnDefs: [
      {
        targets: [ 0 ],
        data: 'dataAcao',
        orderable: false,
        defaultContent: ''
      },
      {
        targets: [ 1 ],
        data: 'clienteOferta.descricao',
        orderable: false,
        defaultContent: ''
      },
      {
        targets: [ 2 ],
        data: 'clienteOferta.clienteOfertaId.cliente.nome',
        orderable: false,
        defaultContent: ''
      },
      {
        targets: [ 3 ],
        data: 'clienteOferta.clienteOfertaId.cliente.sobrenome',
        orderable: false,
        defaultContent: ''
      },
      {
        targets: [ 4 ],
        data: 'clienteOferta.clienteOfertaId.cliente.cpf',
        orderable: false,
        defaultContent: ''
      },
      {
        targets: [ 5 ],
        data: 'clienteOferta.preco',
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

    }
  });
  escondeBotoes();
  formataCampoBusca();
}
