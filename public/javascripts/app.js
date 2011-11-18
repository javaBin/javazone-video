$(function () {


    var filterArticles = function (filters) {
        $('article').each(function () {
            var article = $(this);
            var tags = article.find(".tag").map(function () {
                return this.text.toLowerCase().replace(" ", "_");
            });
            var valid = _.difference(filters, tags).length == 0;

            toggle(article, valid);
        });
    }
    var filterArticlesbyYear = function (filters) {
        $('article').each(function () {
            var article = $(this);
            var year = article.find(".year").text();

            if (filters.size() == 0) {
                toggle(article, true);
            } else {
                toggle(article, _.include(filters, year));
            }
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

    $("#filter-tags").buttonset();
    $("#filter-years").buttonset();

    $("#filter-tags label").click(function (event) {
        var filters = $(this).parent().find(".ui-state-active").map(function () {
            return $(this).attr("for").toLowerCase();
        });
        filterArticles(filters);


    });


    $("#filter-years label").click(function (event) {
        var filters = $(this).parent().find(".ui-state-active").map(function () {
            return $(this).attr("for").toLowerCase();
        });
        filterArticlesbyYear(filters);
    });

    $("#filter-form").submit(function (event) {
        event.preventDefault();
        var input = $("#filter").val();
        if (input == "") {
            filterArticles([]);
        } else {
            filterArticles(input.split(" "));
        }
    });


});
