$(function () {

      var fixTag = function(tag) {
          var tagFix = "" + tag;
          return tagFix.toLowerCase().split(" ").join("_")
              .split(".").join("_")
              .split("#").join("_");
      };

      var filterArticles = function (filters) {
          
          var fixedFilters = _.map(filters, function(value) {
                                       return fixTag(value);
                                   });
          
          $('article').each(function () {
                                var article = $(this);
                                var tags = article.find(".tag").map(function () {
                                                                        return fixTag($(this).text());
                                                                    });
                                var valid = _.difference(fixedFilters, tags).length == 0;
                                toggle(article, valid);
                            });
      };

      var toggle = function (element, valid) {
          var visible = element.is(":visible");
          if (valid && !visible) {
              element.fadeIn()
          }
          if (!valid && visible) {
              element.fadeOut();
          }
      };

      var fjernEllerLeggTilTag = function(tag) {
          var finnes = $("#activetags ." + fixTag(tag));
          if(finnes.size() == 1) {
              finnes.parent().remove();

            if($("#activetags").children("li").size() == 0) {
                $("#active-tags-title").fadeOut();
            }

        }   else {
            var newLi = '<li> <span class="label warning rmtag ' + fixTag(tag) + '">' + tag + '</span></li>';
            $("#activetags").append(newLi);

            if($("#activetags").children("li").size() > 0) {
                $("#active-tags-title").fadeIn();
            }
        }
    };
      

      $(".tag").click(function() {
                          
                          fjernEllerLeggTilTag($(this).text());
                          
                          var filters = $("#activetags .rmtag").map(function () {
                                                                        return $(this).text();
                                                                    });
                          
                          filterArticles(filters);
                      });
      
      $(".rmtag").live('click', function(){
                           $(this).parent().remove();
                           var filters = $("#activetags .rmtag").map(function () {
                                                                         return $(this).text();
                                                                     });
                           filterArticles(filters);
                           
                           if($("#activetags").children("li").size() == 0) {
                               $("#active-tags-title").fadeOut();
                           }
                           
                       });
      

   /* $("#filter-form").submit(function(event) {
        event.preventDefault();
        var input = $("#filter").val();
        if (input == "") {
            filterArticles([]);
        } else {
            filterArticles(input.toLowerCase().split(" "));
        }
    });
    */

      $(".abstract").popover({
                                 content: function() {
                                     return $(this).parent().find(".wiki").html();
                                 },
                                 
                                 title: function() {
                                     return $(this).closest("article").find("h2").text();
                                 },
                                 
                                 placement: 'left',
                                 html: true
                             });

      
      $(".speaker-link").popover({
                                     content: function() {
                                         return $(this).parent().find(".bio").html();
                                     },
                                     title: function() {
                                         return $(this).text();
                                     },
                                     placement: 'left',
                                     html: true
                                 });
      
      $(".speaker-link-right").popover({
                                           content: function() {
                                               return $(this).parent().find(".bio").html();
                                           },
                                           title: function() {
                                               return $(this).text();
                                           },
                                           placement: 'left',
                                           html: true
                                       });
  });

var jz = jz || {};

jz.common_props = {
    version: 2, 
    interval: 10000,
    width: 300,
    height: 250,
    theme: {
        shell: {
            background: '#222222',
            color: '#f1f1f1'
        },
        tweets: {
            background: '#222222',
            color: '#f1f1f1',
            links: '#1985b5'
        }
    },
    features: {
        scrollbar: false,
        loop: true,
        live: true,
        behavior: 'default'
    }  
};

jz.searcher = function searcher(element,search_words) {
    var props = jQuery.extend(true, {}, jz.common_props);
    props.id = element;
    props.type = 'search';
    props.width = 250;
    props.title = "Search for: " + search_words.join(" ");
    props.search = search_words.join(" ");
    new TWTR.Widget(props).render().start();;
}

jz.feed = function feed(element, username) {
    var props = jQuery.extend(true, {}, jz.common_props);
    props.id = element;
    props.type = 'profile';
    props.rpp = 5;
    new TWTR.Widget(props).render().setUser(username).start();
};
