$(document).ready(function() {
  $('.bloco-conteudo').show('slow');
  $('#campo-busca').hide();
  $('.item-menu-principal').fadeIn('fast');
  abreJanelaConteudo('.item-breadcrumb-link');
  retornaHome();
  removeFundoModal();
  buscaDadosAcoes();
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
* busca a lista de acoes com suas quantidades para alimentar o grafico.
*/
function buscaDadosAcoes() {
  $.ajax({
    url: '../acaousuariocliente/listatotalporacao',
    type: 'GET',
    dataType: 'json',
    contentType: 'application/json; charset=utf-8'
  })
  .done(function(data) {
    console.log(data.entity);
    montaGrafico(data.entity);
  })
  .fail(function() {
    console.log("error");
  })
  .always(function() {
    console.log("complete");
  });
}

/*
* monta o grafico de acoes.
*/
function montaGrafico(dados) {
  let total = 0;
  let titulos = [];
  let percentual = [];
  $.each(dados, function(key, quantidade) {
    titulos.push(key+ '('+quantidade+')');
    total+=Number(quantidade);
  });
  $.each(dados, function(key, quantidade) {
    percentual.push((Number((100*(quantidade))/total)).toFixed(2));
  });
  console.log(total);
  var ctx = document.getElementById('grafico').getContext('2d');
  var grafico = new Chart(ctx, {
    type: 'horizontalBar',
    //responsive: true,
    data: {
      labels: titulos,
      datasets: [{
        label: 'Porcentagem sobre o total',
        data: percentual,
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
