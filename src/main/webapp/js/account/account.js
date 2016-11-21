$(function () {

    $("#addBtn").click(function () {
        $.post("account/staticpage.html", function (data) {
            console.warn(data);
            console.log(data);
            console.count("执行次数");
            console.time("array");
            var a = 0;
            for (var i = 0; i < 100000; i++) {
                a += i;
            }
            console.timeEnd("array");
            $("#addBtn").val(data);

        });
    })


});