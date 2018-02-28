$(document).ready(function (e) {
    $('.cards').scrollspy({
        target: '.navbar'
    }).masonry({
        //columnWidth: 30,
        itemSelector: '.card-holder',
        percentPosition: true
    });
})