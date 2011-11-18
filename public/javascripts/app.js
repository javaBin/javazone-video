$(function () {


    var filterArticles = function (filters) {
        $('article').each(function () {
            var article = $(this);
            var tags = article.find(".tag").map(function () {
                return this.text.toLowerCase().replace(" ", "_");
            });
            var visible = article.is(":visible");
            var valid = _.difference(filters, tags).length == 0;

            if (valid && !visible) {
                article.fadeIn()
            }

            if (!valid && visible) {
                article.fadeOut();
            }
        });

    }

    $("#filter-tags").buttonset();

    $("#filter-tags label").click(function (event) {
        var filters = $(".ui-state-active").map(function () {
            return $(this).attr("for").toLowerCase();
        });
        filterArticles(filters);


    });

    $("#filter-form").submit(function () {

        var input = $("#filter").val();
        if (input == "") {
            filterArticles([]);
        } else {
            filterArticles(input.split(" "));
        }
        return false;
    });


});
