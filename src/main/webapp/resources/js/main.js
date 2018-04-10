$(document).ready(function (e) {
    $('[data-toggle="tab"]').tab().on('shown.bs.tab', function (e) {
        updateMasonry();
    });
    updateMasonry();
})

function updateMasonry(){
    $('.cards').scrollspy({
        target: '.navbar'
    }).masonry({
        //columnWidth: 30,
        itemSelector: '.card-holder',
        percentPosition: true
    });
}