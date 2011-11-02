$(function() { 
  var wrapper = $('div.container_12')

  $("#filter").keyup(function() {
    $.uiTableFilter( wrapper, this.value );
  })

  $('#filter-form').submit(function(){
    return false;
  }).focus(); //Give focus to input field
});  
