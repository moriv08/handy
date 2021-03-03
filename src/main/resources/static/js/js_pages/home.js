

/******************************************************************************************/
/******************				 make keepers fade  			***************************/
	var isFaded = new Array(true, true);
	{
		let blockHeader = document.getElementsByClassName("blockHeader");
		let keepers = document.getElementsByClassName("keepers");

		for(let i = 0; i < blockHeader.length; i++){
			blockHeader[i].onclick = function(){
				if (isFaded[i]){
					keepers[i].style.opacity = "0.2";
					keepers[i].style.boxShadow = "0.0em 0.0em 0px rgba(122,122,122,0.0)";
					blockHeader[i].style.boxShadow = "0.4em 0.4em 11px rgba(122,122,122,0.7)";
					isFaded[i] = false;
				}
				else{
					keepers[i].style.opacity = "1.0";
					keepers[i].style.boxShadow = "0.4em 0.4em 11px rgba(122,122,122,0.7)";
					blockHeader[i].style.boxShadow = "0.0em 0.0em 0px rgba(122,122,122,0.0)";
					isFaded[i] = true;
				}
			}
		}
	}

