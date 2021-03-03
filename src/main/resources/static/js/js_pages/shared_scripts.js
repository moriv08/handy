
$(document).ready(function(){

	setInterval(function(){

		var curentDate = ["янв", "фев", "март", "апр", "май", "июнь", "июль", "авг", "сент", "окт", "ноя", "дек"];
		var date = new Date();
		var month = date.getUTCMonth()
		var day = date.getDate();
		var hours = date.getHours();
		var minutes = date.getMinutes();
		var seconds = date.getSeconds();

		// Add leading zeros
		minutes = (minutes < 10 ? "0" : "") + minutes;
		seconds = (seconds < 10 ? "0" : "") + seconds;
		hours = (hours < 10 ? "0" : "") + hours;

		// Compose the string for display
		var currentTimeString = hours + ":" + minutes + ":" + seconds + '<br/>' + day + ", " + curentDate[month];
		var mobileTimeString = day + ", " + curentDate[month] + "      " + hours + ":" + minutes + ":" + seconds ;
		$(".timer").html(currentTimeString);
		$(".mobileTimer").html(mobileTimeString);

	},1000);


	var page_flyer = $('.page_flyer').toArray();
	var slide_img = $('.slide_img').toArray();
	var delete_checkbox = $('.delete_checkbox').toArray();
	const tv_screen = $('.tv_screen');
	var index = 0;

	if (slide_img.length > 0){
		$(delete_checkbox[index]).css("display", "inline-block");
		$(slide_img[index]).css("display", "block");
		$(tv_screen).append(delete_checkbox[index]);
		$(tv_screen).append(slide_img[index]);
	}

	$(page_flyer).click(function (){

		if (this === page_flyer[0]){
			if (index !== 0){
				$(page_flyer[1]).css("opacity", "0.6");
				$(delete_checkbox[index]).css("display", "none");
				$(slide_img[index]).css("display", "none");
				index--;
				$(tv_screen).append(delete_checkbox[index]);
				$(tv_screen).append(slide_img[index]);
				$(delete_checkbox[index]).css("display", "inline-block");
				$(slide_img[index]).css("display", "block");
			}
			else
				$(this).css("opacity", "0.0");
		}
		if (this === page_flyer[1]){
			if (index !== slide_img.length - 1){
				$(page_flyer[0]).css("opacity", "0.6");
				$(delete_checkbox[index]).css("display", "none");
				$(slide_img[index]).css("display", "none");
				index++;
				$(delete_checkbox[index]).css("display", "inline-block");
				$(slide_img[index]).css("display", "block");
				$(tv_screen).append(delete_checkbox[index]);
				$(tv_screen).append(slide_img[index]);
			}
			else
				$(this).css("opacity", "0.0");
		}
	});
});