
$(function () {

    var fixTag = function(tag) {
        var tagFix = "" + tag;
        return tagFix.toLowerCase().split(" ").join("_")
            .split(".").join("_")
            .split("#").join("_");
    }

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
    }

    var toggle = function (element, valid) {
        var visible = element.is(":visible");
        if (valid && !visible) {
            element.fadeIn()
        }
        if (!valid && visible) {
            element.fadeOut();
        }
    }

     var fjernEllerLeggTilTag = function(tag) {
       var finnes = $("#activetags ." + fixTag(tag));
        if(finnes.size() == 1) {
             finnes.parent().remove();
        }   else {
            var newLi = '<li> <span class="label warning rmtag ' + fixTag(tag) + '">' + tag + '</span></li>';
             $("#activetags").append(newLi);
        }
    }


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
    });


    $("#filter-form").submit(function(event) {
        event.preventDefault();
        var input = $("#filter").val();
        if (input == "") {
            filterArticles([]);
        } else {
            filterArticles(input.toLowerCase().split(" "));
        }
    });


    $(".abstract").popover({
        content: function() {
            return $(this).parent().find(".wiki").html();
        },
        title: function() {
            return $(this).closest("article").find("h2").text();
        },
        placement: 'bottom',
        html: true
    })


    $(".speaker-link").popover({
        content: function() {
            return $(this).parent().find(".bio").html();
        },
        title: function() {
            return $(this).text();
        },
        placement: 'bottom',
        html: true
    })

    $(".speaker-link-right").popover({
        content: function() {
            return $(this).parent().find(".bio").html();
        },
        title: function() {
            return $(this).text();
        },
        placement: 'right',
        html: true
    })
});
