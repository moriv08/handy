
$(document).ready(function(){


        let main = $(".main").toArray();
        let content = $("#content");

        let tv_screen = $('.tv_screen').toArray();
        let slide_img = $('.slide_img').toArray();
        let img_index = 0;

        $(slide_img[img_index]).css("display", "block");
        $(tv_screen[0]).append($(slide_img[img_index]));

        $(slide_img).click(function (){

                $(slide_img[img_index]).css("display", "none");
                if (img_index !== (slide_img.length - 1))
                        img_index++;
                else
                        img_index = 0;

                $(slide_img[img_index]).css("display", "block");
                if(($(slide_img[img_index]).width() === $(tv_screen[0]).width()) && $(slide_img[img_index]).width() > $(slide_img[img_index]).height()){
                        $(tv_screen[0]).css("background-color", "white");
                        $(slide_img[img_index]).css("margin-top", "30%");
                }

                else{
                        $(tv_screen[0]).css("background-color", "white");
                        $(slide_img[img_index]).css("margin-top", "0");
                }

                $(tv_screen[0]).append($(slide_img[img_index]));

                $(main[0]).css("width", "100%");
                $(main[0]).css("height", "100%");

                $(tv_screen[0]).css("position", "absolute");
                $(tv_screen[0]).css("top", "0");
                $(tv_screen[0]).css("left", "0");

                $(tv_screen[0]).css("width", "100%");
                $(tv_screen[0]).css("height", "100%", );

                $(main[0]).append($(tv_screen[0]));
                $(content).css("display", "none");
        });



        // $(slide_img[0]).on("swipeleft",
        //     img_index + 1,
        //     alert(img_index)
        //
        // );

        // alert("dddddd")


        // $(slide_img)[img_index].click(
        //     // { axis: "x", revert: true }
        //     $(this).css("opacity", 0.5)
        //
        // );
        // var cont_pos = $(viewport)[0].position();
        // var item_width = $(".slide_img").width();
        // var items = $($(viewport)[0] > "div.slide_img").length;
        // var item_index = 1;
        // var cont_post_temp;



    //     function bindMouseUp() {
    //         $(viewport)[0].unbind('mouseup');
    //
    //         cont_post_temp = $(viewport)[0].position().left;
    //         if (cont_pos.left > cont_post_temp && item_index != items) {
    //             // Перелистывание вправо
    //             $(viewport)[0].draggable("option", "revert", false);
    //             var moveLeft = cont_pos.left - cont_post_temp;
    //             moveLeft = Math.abs(item_width - moveLeft);
    //             $("#swipe_container").animate({
    //                 left: '-=' + moveLeft
    //             }, 500, function() {
    //                 $(viewport)[0].draggable("option", "revert", true);
    //                 cont_pos = $(viewport)[0].position();
    //                 $(viewport)[0].bind('mouseup', function() {
    //                     bindMouseUp();
    //                 });
    //             });
    //             item_index++;;
    //         } else if (cont_pos.left < cont_post_temp && item_index != 1) {
    //             // Перелистывание влево
    //             $(viewport)[0].draggable("option", "revert", false);
    //             var moveLeft = cont_post_temp - cont_pos.left;
    //             moveLeft = Math.abs(item_width - moveLeft);
    //             $(viewport)[0].animate({
    //                 left: '+=' + moveLeft
    //             }, 500, function() {
    //                 $(viewport)[0].draggable("option", "revert", true);
    //                 cont_pos = $(viewport)[0].position();
    //                 $(viewport)[0].bind('mouseup', function() {
    //                     bindMouseUp();
    //                 });
    //             });
    //             item_index = item_index - 1;
    //         } else {
    //             // В начале или в конце перелистывания страницы
    //             $(viewport)[0].draggable( "option", "revert", true );
    //             $(viewport)[0].bind('mouseup', function() {
    //                 bindMouseUp();
    //             });
    //         }
    //         alert("2222222")
    //     }
    //
    //     $(viewport)[0].mouseup(function() {
    //
    //         bindMouseUp();
    //     });
    // });

    // alert("end")


    // $(slide_img).swipe(
    //     $(this).css("opacity", 0.5)
    // );
    // if (slide_img.length > 0){
    //     $(delete_checkbox[index]).css("display", "inline-block");
    //     $(slide_img[index]).css("display", "block");
    //     $(tv_screen).append(delete_checkbox[index]);
    //     $(tv_screen).append(slide_img[index]);
    // }

	// var curent_data = $('.curent_data').toArray();
	// var hidden_input = $('.hidden_input').toArray();

	// // заменяем текущее значение из базы на новый input
	// $(curent_data).dblclick(function (){
	// 	$(this).css("display", "none");
	// 	// находим индекс нажатого элемента и выбераем соседний input
	// 	let index = $(this).index('.curent_data');
	// 	$(hidden_input[index]).css("display", "block");
	// });

	// var curent_juristic_data = $('.curent_juristic_data').toArray();
	// var hidden_juristic_input = $('.hidden_juristic_input').toArray();
	//
	// $(curent_juristic_data).dblclick(function (){
	// 	$(this).css("display", "none");
	// 	// находим индекс нажатого элемента и выбераем соседний input
	// 	let index = $(this).index('.curent_juristic_data');
	// 	$(hidden_juristic_input[index]).css("display", "block");
	// });

});