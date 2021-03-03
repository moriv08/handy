
// {
// 	/******* make appropriate os space ******/
// 	function auto_height(elem) {
// 		elem.style.height = "1px";
// 		elem.style.height = (elem.scrollHeight)+"px";
// 	}
// }

$(document).ready(function(){

	var curent_data = $('.curent_data').toArray();
	var hidden_input = $('.hidden_input').toArray();

	// заменяем текущее значение из базы на новый input
	$(curent_data).dblclick(function (){
		$(this).css("display", "none");
		// находим индекс нажатого элемента и выбераем соседний input
		let index = $(this).index('.curent_data');
		$(hidden_input[index]).css("display", "block");
	});

	var curent_juristic_data = $('.curent_juristic_data').toArray();
	var hidden_juristic_input = $('.hidden_juristic_input').toArray();

	$(curent_juristic_data).dblclick(function (){
		$(this).css("display", "none");
		// находим индекс нажатого элемента и выбераем соседний input
		let index = $(this).index('.curent_juristic_data');
		$(hidden_juristic_input[index]).css("display", "block");
	});

});