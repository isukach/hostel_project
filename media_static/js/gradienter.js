/**
 * jqBarGraph - jQuery plugin
 * @version: 1.0 (2010/09/11)
 * @requires jQuery v1.2.2 or later 
 * @author Ivan Lazarevic
 * Examples and documentation at: http://www.workshop.rs/projects/gradienter
 * 
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 * 
 * @param color_start: 'FFFFFF' // first color in gradient
 * @param color_end: '000000' // last color in gradient
 * @param prop: 'background-color' // on which CSS property should apply gradient

 * @example  $('div').gradienter({ color_start: '0099FF', color_end: 'F3F3F3', prob: 'color' });  
  
**/

(function($) {
	var opts = new Array;
	var count = 0;
	var defaults = {	
		color_start: 'ffffff',
		color_end: '000000',
		prop: 'background-color'
	};
	
	$.fn.Gradienter = $.fn.gradienter = function(options){
	
	init = function(el){

		opts[count] = $.extend({}, defaults, options);
		
		var r = g = b = 0;
		
		
		r = sr = parseInt(opts[count].color_start.substring(0,2),16);
		g = sg = parseInt(opts[count].color_start.substring(2,4),16);
		b = sb = parseInt(opts[count].color_start.substring(4,6),16);

		er = parseInt(opts[count].color_end.substring(0,2),16);
		eg = parseInt(opts[count].color_end.substring(2,4),16);
		eb = parseInt(opts[count].color_end.substring(4,6),16);
	
		$(el).css(opts[count].prop , 'rgb('+sr+','+sg+','+sb+')');			
	
		steps = $(el).length;
		$.each($(el), function(i,item){
			
			r -= parseInt((sr - er) / steps);
			g -= parseInt((sg - eg) / steps);
			b -= parseInt((sb - eb) / steps);
			
			if(i > 0 || steps == 1){
				$(item).css(opts[count].prop , 'rgb('+r+','+g+','+b+')');
			}			

		});

		count++;
	};
	


	init(this); 
	
};
	
})(jQuery);