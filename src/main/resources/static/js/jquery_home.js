
$(document).ready(function(){


    var leftHeader = $('#leftHeader');
    var colored_leads_table = $('.colored_leads_table');

    var comp = $('#comp');
    var mobile = $('#mobile');



    if (/Android|webOS|iPhone|iPad|iPod|BlackBerry|BB|PlayBook|IEMobile|Windows Phone|Kindle|Silk|Opera Mini/i.test(navigator.userAgent)) {

        // alert("Вы используете мобильное устройство (телефон или планшет).")

        $(comp).css("display", "none");
        $(mobile).css("display", "block");

    }
    // else
        // alert("Вы используете ПК.");

    // $(leftHeader).click(function (){
    //     $(this).css("opacity", 0.3);
    // });
    //
    // $(colored_leads_table).click(function (){
    //     $(this).css("opacity", 0.3);
    // });

});