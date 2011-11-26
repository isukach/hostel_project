

(function($){

	$.fn.twirlie = function(options) {
	
		var defaults = {
			transtime     : 500,
			nextSel       : ".next",
			prevSel       : ".prev"
		};
		
		var options = $.extend(defaults, options);
	
		this.each(function() {
			var obj = $(this);
			var next = options.nextSel;
			var prev = options.prevSel;
			var current = 0;
			var total = $('#slider ul.sliderelement li').length;
			var width = parseInt(($(obj).css("width")).substring(0, ($(obj).css("width")).length-2));
			
			$(obj).data("current", current);
			$(obj).data("width", width);
			$(obj).data("transtime", options.transtime);
		
			$(next).click(function() {
				current = $(obj).data("current");
				current += 1;
				if (current == total) {
					current = 0;
				}
				obj.gotoSlide(current+1);
				return false;
			});
	
			$(prev).click(function() {
				current = $(obj).data("current");
				current -= 1;
				if (current < 0) current = total-1;
				
				obj.gotoSlide(current+1);
				return false;
			});
			
	   });
	   return this;
	}
	
	$.fn.gotoSlide = function(slide) {
		var width = parseInt($(this).data("width"));
//        var width = 500;
		var transtime = $(this).data("transtime");
      	$('ul', this).stop().animate({
   			left: (slide-1)*(0-width) + "px"
      	}, transtime);
      	$(this).data("current", slide-1);
	}

    $.fn.changeRoom = function(roomAttr){
    }

   $.fn.positionOn = function(element, align) {
       return this.each(function() {
             var target   = $(this);
             var position = element.position();

             var x      = position.left;
             var y      = position.top;

             if(align == 'right') {
               x -= (target.outerWidth() - element.outerWidth());
             } else if(align == 'center') {
               x -= target.outerWidth() / 2 - element.outerWidth() / 2;
               y += target.outerHeight() / 2 + element.outerHeight() /2;
             }

             target.css({
               position: 'absolute',
               zIndex:   5000,
               top:      y,
               left:     x
             });
       });
   };

	
})(jQuery); 