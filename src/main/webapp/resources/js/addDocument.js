$(document).ready(function () {
  $('#addNewAuthor').click(function () {
    var i = $('.author-list .author').length + 1;
    var name = $('[name="author_name"]').val();
    var last_name = $('[name="author_lastname"]').val();
    if (i == 1) {
      $('.author-list').append('<span class="author-heading">Authors list: </span>')
    }
    var el = '<div class="author form-row" id="' + i + '">' +
        '<div class="col-4"><input class="form-control" type="text" name="author_name_' + i + '" placeholder="First name" value="' + name + '"></div>' +
        '<div class="col-4"><input class="form-control" type="text" name="author_lastname_' + i + '" placeholder="Last name" value="' + last_name + '"></div>' +
        '<span class="col-1">' +
        '<button type="button" class="close float-right" aria-label="Close">\n' +
        '<span aria-hidden="true">&times;</span>\n' +
        '</button>' +
        '</span>' + '</div>';
    $('.author-list').append(el).find('.close').click(function (e) {
      $(e.target).closest('.author').remove();
      if ($('.author-list .author').length == 0) {
        $('.author-list').empty();
      }
    })
    $('[name="author_name"]').val('');
    $('[name="author_lastname"]').val('');
  });
  $('.document-type').change(function () {
    console.log($(this).val());
    switch (parseInt($(this).val())) {
      case 1:
        $('.book-props').removeClass('hidden');
        $('.ja-props').addClass('hidden');
        break;
      case 2:
        $('.ja-props').removeClass('hidden');
        $('.book-props').addClass('hidden');
        break;
      default:
        $('.book-props').addClass('hidden');
        $('.ja-props').addClass('hidden')
        break;
    }
  })
});