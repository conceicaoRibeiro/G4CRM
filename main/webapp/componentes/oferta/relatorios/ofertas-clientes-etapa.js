VALOR_TOTAL=0;
QUANTIDADE_TOTAL=0;
$(document).ready(function() {
  $('.bloco-conteudo').show('slow');
  $('#campo-busca').hide();
  $('.item-menu-principal').fadeIn('fast');
  abreJanelaConteudo('.item-breadcrumb-link');
  retornaHome();
  removeFundoModal();
  buscaDadosClienteOferta();
});

/*
* remove o fundo do modal quando a tela e carregada.
*/
function removeFundoModal() {
  $('body').removeAttr('cz-shortcut-listen');
  $('body').removeClass('modal-open');
  $('.modal-backdrop').remove();
}

/*
* busca os dados aninhados para montar a arvore.
*/
function buscaDadosClienteOferta() {
  $.ajax({
    url: '../clienteoferta/lista',
    type: 'GET',
    dataType: 'json',
    contentType: 'application/json; charset=utf-8'
  })
  .done(function(data) {
    console.log(data);
    montaArvoreOferta(data);
  })
  .fail(function() {
    console.log("error");
  })
  .always(function() {
    console.log("complete");
  });
}

/*
* monta as arvores das ofertas
*/
function montaArvoreOferta(data) {
  let arvore = '';
  arvore += '<div class="treeview-animated w-20 border mx-4 my-4">';
  arvore += '<h6 class="pt-3 pl-3">Ofertas</h6>';
  arvore += '<hr>';
  arvore += '<ul class="treeview-animated-list mb-3">';
  $.each(data, function(key, oferta) {
    arvore += '<li class="treeview-animated-items">';
    arvore += '<a class="closed">';
    arvore += '<i class="fas fa-angle-right"></i>';
    arvore += '<span><i class="far fa-gem ic-w mx-1"></i>'+key+'<span class="valor-quantidade-oferta"></span></span>';
    arvore += '<ul class="nested arvore-oferta">';
    $.each(oferta, function(key, etapa) {
      arvore += '<li class="treeview-animated-items">';
      arvore += '<a class="closed"><i class="fas fa-angle-right"></i>';
      arvore += '<span><i class="far fa-bell ic-w mx-1"></i>'+key+' ('+etapa.length+') <span quantidade="'+etapa.length+'" class="valor-total-etapa" etapa-descricao="'+key+'"></span></span></a>';
      // colocar os subitens.
      arvore += '<ul class="nested etapa-funil">';
      $.each(etapa, function(index, el) {
        arvore += '<li>';
        arvore += '<div class="treeview-animated-element cliente-folha" preco='+el.preco+'><i class="far fa-address-book ic-w mr-1"></i>'+el.clienteOfertaId.cliente.nome+' '+el.clienteOfertaId.cliente.sobrenome;
        arvore += '</li>';
      });

      arvore += '</ul>';
      arvore += '</li>';
    });
    arvore += '</ul>';
  });
  arvore += '</ul>';
  arvore += '</div>';
  $('.bloco-detalhes-cliente').append(arvore);
  $('.treeview-animated').mdbTreeview();
  realizaSomatorioClientesEtapa();
}

/*
* realiza o somatorio dos clientes na etapa
*/
function realizaSomatorioClientesEtapa() {
  $('.etapa-funil').each(function(index, el) {
    let valor = 0;
    $(el).find('.cliente-folha').each(function(index, el) {
      valor += Number($(el).attr('preco'));
    });
    $(el).parent().find('.valor-total-etapa').text('R$:'+ valor);
    $(el).parent().find('.valor-total-etapa').attr('valor-total', valor);
  });
  realizaSomatorioClientesOferta();
  buscaEtapasFunil();
}

/*
* realiza o somatorio dos clientes em todas as etapas
*/
function realizaSomatorioClientesOferta() {
  VALOR_TOTAL;
  QUANTIDADE_TOTAL;
  $('.arvore-oferta').each(function(index, el) {
    let valor = 0;
    let quantidade = 0;
    $(el).find('.valor-total-etapa').each(function(index, el) {
      valor += Number($(el).attr('valor-total'));
      VALOR_TOTAL+=valor;
      quantidade += Number($(el).attr('quantidade'));
      QUANTIDADE_TOTAL+=quantidade;
      $(el).closest('.arvore-oferta').parent().find('.valor-quantidade-oferta').html(' ('+quantidade+') R$:'+valor);
    });
  });
}

/*
* agrupa os valores totais por etapa
*/
function buscaEtapasFunil() {
  $.ajax({
    url: '../funiletapa/lista',
    type: 'GET',
    dataType: 'json',
    contentType: 'application/json; charset=utf-8'
  })
  .done(function(data) {
    FUNIL_ETAPAS = data;
    realizaSomatorioEtapasFunil(data);
  })
  .fail(function() {
    console.log("error");
  })
  .always(function() {
    console.log("complete");
  });
}

/*
* fornece a porcentagem e os valores de cada etapa do funil.
*/
function realizaSomatorioEtapasFunil(data) {
  let totalEtapa = 0;
  $.each(data, function(index, el) {
    totalEtapa = 0;
    $('[etapa-descricao="'+el.descricao+'"]').each(function(index, el) {
      totalEtapa+=Number($(this).attr('valor-total'));
    });
    el.valorTotalEtapa = totalEtapa;
  });
  //console.log(data);
  montaResumoEtapas(data);
}

/*
* monta a lista de resumo dos somatorios das etapas do funil.
*/
function montaResumoEtapas(data) {
  let dados = [];
  let etapas = [];
  let porcentagens = [];
  $.each(data, function(index, el) {
    etapas.push(el.descricao+' (R$:'+el.valorTotalEtapa+')');
    dados.push((Number((100*(el.valorTotalEtapa))/VALOR_TOTAL)).toFixed(2));
  });
  var ctx = document.getElementById('grafico').getContext('2d');
  var grafico = new Chart(ctx, {
    type: 'bar',
    responsive: true,
    data: {
      labels: etapas,
      datasets: [{
        label: 'Porcentagem sobre o total',
        data: dados,
        backgroundColor: '#00325a',
        borderWidth: 1
      }],
      options: {
        scales: {
          yAxes: [{
            ticks: {
              beginAtZero: true
            }
          }]
        },
        animation: {
            onProgress: function(animation) {
                progress.value = animation.animationObject.currentStep / animation.animationObject.numSteps;
            }
        }
      }
    }
   });
  }
