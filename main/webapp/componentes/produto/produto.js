var painel = '';
var niveisInstrucao = '';

$(document).ready(function() {
  $('.bloco-conteudo').show('slow');
  $('#campo-busca').hide();
  $('.item-menu-principal').fadeIn('fast');
  retornaHome();
  listaProdutos($('#tabela'), true);
  listaNivelInstrucao($('.lista-de-campos'));
  abreModalNovoProduto($('#btn-novo-produto'));
  salvar($('#salvar-produto'),null);
});

function listaProdutos(tabela, loadInicial) {
  $.ajax({
    url: '../produto/lista',
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
        data: 'descricao',
        orderable: false,
        defaultContent: ''
      },
      {
        targets: [ 2 ],
        data: 'nivelInstrucao.descricao',
        orderable: false,
        defaultContent: ''
      },
      {
        targets: [ 3 ],
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
      abreModalEditarProduto(row,data);
    }
  });
  escondeBotoes();
  formataCampoBusca();
}

/*
* lista os niveis de instrucao.
*/
function listaNivelInstrucao(select) {
  $.ajax({
    url: '../nivelinstrucao/lista',
    type: 'GET',
    dataType: 'json',
    contentType: 'application/json; charset=utf-8'
  })
  .done(function(data) {
    let lista='';
    console.log(data);
    niveisInstrucao = data;
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
* abre o modal de novo produto.
*/
function abreModalNovoProduto(btnNovoProduto) {
  $(btnNovoProduto).click(function(event) {
    $('#nome').val('');
    $('#status').val('');
    $('#novo-produto-campos-adicionais .campo-adicional').remove();
    $('#modal-novo-produto').modal('show');
  });
}

/*
* abre o modal de editar produto.
*/
function abreModalEditarProduto(row, data) {
  $(row).click(function(event) {
    $('#editar-nome').val(data.descricao);
    $('#editar-status').val(data.status);
    $('#editar-produto-nivel-instrucao').val(data.nivelInstrucao.descricao);
    $('#novo-produto-campos-adicionais .campo-adicional').remove();
    $('#modal-editar-produto').modal('show');
    salvar($('#editar-produto'), data.produtoId);
  });
}

/*
* Salva um produto.
*/
function salvar(btnSalvar, produtoId) {
  $(btnSalvar).click(function(event) {
    let descricao = '';
    let status = '';
    let nivelInstrucao = '';
    if(produtoId==null) {
      descricao = $('#nome').val();
      status = $('#status').val();
      $.each(niveisInstrucao, function(index, el) {
        if(el.descricao==$('#novo-produto-nivel-instrucao').val()) {
          nivelInstrucao=el;
        }
      });
    } else {
      descricao = $('#editar-nome').val();
      status = $('#editar-status').val();
      $.each(niveisInstrucao, function(index, el) {
        if(el.descricao==$('#editar-produto-nivel-instrucao').val()) {
          nivelInstrucao=el;
        }
      });
    }
    let produto = {
      produtoId: produtoId,
      descricao: descricao,
      status: status,
      nivelInstrucao: nivelInstrucao
    }
    $.ajax({
      url: '../produto/salva',
      type: 'POST',
      dataType: 'json',
      data: JSON.stringify(produto),
      contentType: 'application/json; charset=utf-8'
    })
    .done(function() {
      listaProdutos(null, false);
    })
    .fail(function() {
      console.log("error");
    })
    .always(function() {
      console.log("complete");
    });

  });
}
