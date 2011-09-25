/*
POPUP call function:
popup({popup: '#tablePopup',show: true,centralize: true,overlay: true,overlayID: '#overlay'});
*/

$(document).ready(function() {	
	
	tabs(); // payment system tabs switcher

    $('#slider').twirlie({
        transtime: 300
    });

    $('.floorNumbers a').live( 'click', function(){
        // attr  floornumber is added for defining floor
        var floor = $(this).attr('floornumber');
        $('#slider').gotoSlide(floor);
    });

    $('#slider').changeRoom({
        number : 'room'
    })

});

function tabs() {
	$('.tabContent li').click(function(e) {
        console.log('ok');
		e.preventDefault();
		$(this).siblings().removeClass('current');
		$(this).addClass('current');
		var tab_name = $('.tabContent li.current a').attr('href');
		$('.tabText').children("'"+tab_name+"'").show().siblings().hide();
	});
}