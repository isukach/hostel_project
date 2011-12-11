/*
POPUP call function:
popup({popup: '#tablePopup',show: true,centralize: true,overlay: true,overlayID: '#overlay'});
*/

jQuery(document).ready(function() {
	
	tabs(); // payment system tabs switcher

    jQuery('#slider').twirlie({
        transtime: 300
    });

    jQuery('.floorNumbers a').live( 'click', function(){
        // attr  floornumber is added for defining floor
        var floor = jQuery(this).attr('floornumber');
        jQuery('#slider').gotoSlide(floor);
    });

    jQuery('#slider').changeRoom({
        number : 'room'
    })

    jQuery(".room").live("mouseover mouseout", function(event) {
        var roomTextEl =  jQuery('.roomNumber');
        var floorNumberText = jQuery(this).attr('floor');
        var roomNumberText = jQuery(this).attr('room');
        var calculatedRoomNumber = parseInt(floorNumberText)*100 + parseInt(roomNumberText);

        if ( event.type == "mouseover" ) {
               jQuery('.roomNumber span').text(calculatedRoomNumber);
               roomTextEl.positionOn(jQuery(this), 'center');
               roomTextEl.show();
        } else {
            roomTextEl.hide();
        }
        return false;
    });
    jQuery('.goodMining').gradienter({ color_start: '56cb56', color_end: '165c16' });
    jQuery('.badMining').gradienter({ color_start: 'cb5656', color_end: '5c1616' });

    jQuery('.goodNetwork').gradienter({ color_start: '56cb56', color_end: '165c16' });
    jQuery('.badNetwork').gradienter({ color_start: 'cb5656', color_end: '5c1616' });

    jQuery('.goodDuty').gradienter({ color_start: '56cb56', color_end: '165c16' });
    jQuery('.badDuty').gradienter({ color_start: 'cb5656', color_end: '5c1616' });

});

function tabs() {
	jQuery('.tabContent li').click(function(e) {
        console.log('ok');
		e.preventDefault();
		jQuery(this).siblings().removeClass('current');
		jQuery(this).addClass('current');
		var tab_name = jQuery('.tabContent li.current a').attr('href');
		jQuery('.tabText').children("'"+tab_name+"'").show().siblings().hide();
	});
}