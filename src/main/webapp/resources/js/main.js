$(document).ready(function (e) {
    $('[data-toggle="tab"]').tab().on('shown.bs.tab', function (e) {
        updateMasonry();
    });
    updateMasonry();
    console.log ($('input'));
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

$(document).ready(function () {
    $('#phone_number').mask("+7(999)999-9999");
})


$(function(){
    $('input[data-type="number"]').bind('keypress', function(e){
        var keyCode = (e.which)?e.which:event.keyCode
        return !(keyCode>31 && (keyCode<48 || keyCode>57));
    });
});