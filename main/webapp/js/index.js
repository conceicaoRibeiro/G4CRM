$(document).ready(function() {
  buscaCard($('.item-buscavel'), $('#campo-busca'));
  abreJanelaConteudo($('.item-desktop, .item-menu-link'));
  retornaHome();
  $('#campo-busca').show();
  $('.item-menu-principal').hide();
});

function buscaCard(itemBuscavel, campoBusca) {
  $(campoBusca).keyup(function(event) {
    $.each(itemBuscavel, function(index, el) {
      let valor = $(el).html();
      if(!valor.includes($(campoBusca).val())) {
        $(el).parent('.card-body').parent('.card').hide();
        $(el).parent('.card-body').parent('.card').parent('div').removeClass('col-md-4');
      } else {
        $(el).parent('.card-body').parent('.card').show();
        $(el).parent('.card-body').parent('.card').parent('div').addClass('col-md-4');
      }
    });
  });
}

function abreJanelaConteudo(opcao) {
  $(opcao).click(function(event) {
    $('.desktop').load('componentes/'+$(this).attr('item-id')+'/'+$(this).attr('item-id')+'.html');
    $.getScript('componentes/'+$(this).attr('item-id')+'/'+$(this).attr('item-id')+'.js');
  });
}

function retornaHome() {
  $('.navbar-brand, .link-home').click(function(event) {
    location.reload();
  });
}

/** Os Scripts abaixo sao relacionados ao comportamento global de estilo das tabelas */

/*
* esconde os botoes originais do plugin e o campo de busca em conformidade com o layout e associa o evento os botoes ja existentes.
*/
var escondeBotoes = function() {
  $('.dt-buttons').hide();
  $('#exportar-excel').click(function(event) {
    $('.buttons-excel').click();
  });
  $('#exportar-pdf').click(function(event) {
    $('.buttons-pdf').click();
  });
}

/*
* formata o campo de busca em conformidade com o layout.
*/
var formataCampoBusca = function() {
  $('#tabela_wrapper .row:first-child').hide();
  let campoBusca = $('#tabela_filter input');
  $('#box-filtro').append(campoBusca);
}

/*
* Exibe load screen para as requisições REST.
*/
function adicionaLoader() {
  $('body').prepend('<div id="loader-frame"><div id="loader"></div></div>');
  $('#loader-frame').fadeIn();
}

/*
* Remove o load screen para as requisições REST.
*/
function removeLoader() {
  $('#loader-frame').fadeOut().remove();
}
