// Copyright 2010 htmldrive.net Inc.
/**
 * @projectHomepage: http://www.htmldrive.net/go/to/canvasSlideshow
 * @projectDescription: Html5 canvas image slideshow jQuery plugin.
 * @author htmldrive.net
 * More script and css style : htmldrive.net
 * @version 1.0
 * @license http://www.apache.org/licenses/LICENSE-2.0
 */
(function(a){
    a.fn.webwidget_slideshow_html5_simple=function(p){
        var p=p||{};

        var ste=p&&p.slideshow_transition_effect?p.slideshow_transition_effect:"";
        var m=p&&p.slideshow_time_interval?p.slideshow_time_interval:"2000";
        var q=p&&p.slideshow_window_width?p.slideshow_window_width:"500";
        var r=p&&p.slideshow_window_height?p.slideshow_window_height:"400";
        var sbgc=p&&p.slideshow_background_color?p.slideshow_background_color:"#FFF";
        var sb = p&&p.slideshow_border?p.slideshow_border:"#999 soild 2px";
        q = parseInt(q);
        r = parseInt(r);
        m = parseInt(m);
        var d;
        var e;
        var v;
        var w=-1;
        var x=a(this);
        var cu;
        var stea = ste.split(",");
        var images_array = new Array();
        var href_array = new Array();
        var target_array = new Array();
        var video_array = new Array();
        var title_array = new Array();
        var masker_count = 0;
        var canvas;
        var canvas_mask;
        var canvas_text;
        var mosaic_arr = new Array();
        var t_r = p&&p.text_effect?p.text_effect:"text_nothing";
        var y=x.find("ul:first").children("li").length;
        if(x.find("ul").length==0||x.find("li").length==0){
            x.append("Require content");
            return null
        }


        
        init();
        next();
        thumbrun();
        function init(){
            x.css("border",sb);
            x.append('<div id="webwidget_window"></div>');
            x.find("#webwidget_window").append('<a></a>');
            x.find("#webwidget_window").children("a:first").append('<canvas id="webwidget_canvas" width="'+q+'" height="'+r+'" style="position:absolute"></canvas>');
            x.find("#webwidget_window").children("a:first").append('<canvas id="webwidget_canvas_mask" width="'+q+'" height="'+r+'" style="position:absolute"></canvas>');
            x.find("#webwidget_window").children("a:first").append('<canvas id="text_canvas" width="'+q+'" height="'+r+'" style="position:absolute"></canvas>');
            x.find("#webwidget_window").css("width",q+"px").css("height",r+"px").css("background-color",sbgc);
            x.css("width",q+"px");
            x.children("ul").children("li").each(function(i){
                href_array[i] = $(this).children(a).attr("href");
                target_array[i] = $(this).children(a).attr("target");
                images_array[i] = $(this).find("img").attr("src");
                video_array[i] = $(this).find("video");
                title_array[i] = $(this).find("img").attr("alt");
            });
            y = images_array.length;

            canvas = document.getElementById('webwidget_canvas').getContext('2d');
            canvas_mask = document.getElementById('webwidget_canvas_mask').getContext('2d');
            canvas_text = document.getElementById('text_canvas').getContext('2d');
            x.children("ul").remove();
            x.append('<div id="webwidget_tool"></div>');
            x.children("#webwidget_tool").append('<ul id="webwidget_thumb_box"></ul>');
            for(var i in images_array){
                x.children("#webwidget_tool").children("#webwidget_thumb_box").append('<li><img src="'+images_array[i]+'" /></li>');
            }
            x.find("#webwidget_thumb_box li").css("float","left").css("list-style","none").css("margin-right","2px").css("width","70px").css("height","55px");
            x.find("#webwidget_thumb_box li img").css("width","70px").css("height","55px").css("cursor","pointer");
            x.find("#webwidget_thumb_box").css("margin","0px").css("padding","0px").width(y*(70+2)).css("position","absolute");
            x.children("#webwidget_tool").css("margin-top","2px").css("height","55px").css("overflow","hidden").width(q).css("position","relative");

            x.find("#webwidget_thumb_box li").click(
                function(){
                    var picindex = $(this).parent().children("li").index($(this));
                    showpic(picindex);
                }
                );
            $("#nextbtn").click(
                function(){
                    next();
                }
                );
            $("#prevbtn").click(
                function(){
                    prev();
                }
                );
            $("#stopbtn").click(
                function(){
                    stop();
                }
                );
        }
        function addEvent(node, type, listener) {
                if(node.addEventListener) {
                        node.addEventListener(type, listener, false);
                        return true;
                } else if(node.attachEvent) {
                        node['e' + type + listener] = listener;
                        node[type + listener] = function() {
                                node['e' + type + listener](window.event);
                        };
                        node.attachEvent('on' + type, node[type + listener]);
                        return true;
                }
                return false;
        }

        function thumbrun(){
            var o = document.getElementById('webwidget_tool');
            var bai = x.find("#webwidget_tool").width()/(x.find("#webwidget_thumb_box").width()-x.find("#webwidget_tool").width());
            addEvent(o, 'mousemove', function(ev){
                var mouse_x = ev.clientX - x.find("#webwidget_tool").offset().left;
                x.find("#webwidget_thumb_box").css("left","-"+(mouse_x/bai)+"px");
            });
        }

        function showpic(who){
            mosaic_arr.length = 0;
            clearInterval(e);
            clearTimeout(v);
            canvas_mask.clearRect(0,0,q,r);
            cu = w;
            w = who;

            switch(t_r){
                case 'text_nothing' :
                    text_nothing();
                    break;
                case 'text_color' :
                    text_color();
                    break;
                case 'text_fade' :
                    text_fade();
                    break;
                case 'text_jump' :
                    text_jump();
                    break;
                case 'text_rebound' :
                    text_rebound();
                    break;
                case 'text_typewriter' :
                    text_typewriter();
                    break;
                case 'text_whirl' :
                    text_whirl();
                    break;
                case 'text_run' :
                    text_run();
                    break;
                case 'text_runin' :
                    text_runin();
                    break;
            }
            masker_count = 0;
            e = setInterval(eval(stea[w]),30);
        }
        function prev(){
            mosaic_arr.length = 0;
            clearInterval(e);
            clearTimeout(v);
            canvas_mask.clearRect(0,0,q,r);
            cu = w;
            if(w <= 0){
                w = y - 1;
            }else{
                w --;
            }
            switch(t_r){
                case 'text_nothing' :
                    text_nothing();
                    break;
                case 'text_color' :
                    text_color();
                    break;
                case 'text_fade' :
                    text_fade();
                    break;
                case 'text_jump' :
                    text_jump();
                    break;
                case 'text_rebound' :
                    text_rebound();
                    break;
                case 'text_typewriter' :
                    text_typewriter();
                    break;
                case 'text_whirl' :
                    text_whirl();
                    break;
                case 'text_run' :
                    text_run();
                    break;
                case 'text_runin' :
                    text_runin();
                    break;
            }
            masker_count = 0;
            e = setInterval(eval(stea[w]),30);
        }
        function next(){
            mosaic_arr.length = 0;
            clearInterval(e);
            clearTimeout(v);
            canvas_mask.clearRect(0,0,q,r);
            cu = w;
            if(w>=(y-1)){
                w = 0;
            }else{
                w ++;
            }
            switch(t_r){
                case 'text_nothing' :
                    text_nothing();
                    break;
                case 'text_color' :
                    text_color();
                    break;
                case 'text_fade' :
                    text_fade();
                    break;
                case 'text_jump' :
                    text_jump();
                    break;
                case 'text_rebound' :
                    text_rebound();
                    break;
                case 'text_typewriter' :
                    text_typewriter();
                    break;
                case 'text_whirl' :
                    text_whirl();
                    break;
                case 'text_run' :
                    text_run();
                    break;
                case 'text_runin' :
                    text_runin();
                    break;
            }
            masker_count = 0;
            e = setInterval(eval(stea[w]),30);
        }
        function Wipe_from_horizontal_blind(){
            if(cu == -1){
                canvas_mask.fillStyle = sbgc;
                canvas_mask.fillRect (0,0,q,r);
            }else{
                var img_pre = new Image();
                img_pre.src = images_array[cu];
                canvas_mask.drawImage(img_pre,0,0,q,r);
            }
            var img = new Image();
            img.src = images_array[w];
            canvas.drawImage(img,0,0,q,r);
            var bar_width = r/30;
            for(var i=0;i<=30;i++){
                canvas_mask.clearRect(0,bar_width*i,q,bar_width/30*masker_count);
            }
            if(((q/30)*masker_count) >= q){
                canvas_mask.clearRect(0,0,q,r);
                tran_end();
            }
            masker_count ++;
        }
        function Wipe_from_vertical_blind(){
            if(cu == -1){
                canvas_mask.fillStyle = sbgc;
                canvas_mask.fillRect (0,0,q,r);
            }else{
                var img_pre = new Image();
                img_pre.src = images_array[cu];
                canvas_mask.drawImage(img_pre,0,0,q,r);
            }
            var img = new Image();
            img.src = images_array[w];
            canvas.drawImage(img,0,0,q,r);
            var bar_width = q/30;
            for(var i=0;i<=30;i++){
                canvas_mask.clearRect(bar_width*i,0,bar_width/30*masker_count,r);
            }
            if(((q/30)*masker_count) >= q){
                canvas_mask.clearRect(0,0,q,r);
                tran_end();
            }
            masker_count ++;
        }
        function Wipe_from_vertical_center(){
            if(cu == -1){
                canvas_mask.fillStyle = sbgc;
                canvas_mask.fillRect (0,0,q,r);
            }else{
                var img_pre = new Image();
                img_pre.src = images_array[cu];
                canvas_mask.drawImage(img_pre,0,0,q,r);
            }
            var img = new Image();
            img.src = images_array[w];
            canvas.drawImage(img,0,0,q,r);
            canvas_mask.clearRect((q-(q/30)*masker_count)/2,0,(q/30)*masker_count,r);
            if(((q/30)*masker_count) >= q){
                tran_end();
            }
            masker_count ++;
        }
        function Wipe_from_horizontal_center(){
            if(cu == -1){
                canvas_mask.fillStyle = sbgc;
                canvas_mask.fillRect (0,0,q,r);
            }else{
                var img_pre = new Image();
                img_pre.src = images_array[cu];
                canvas_mask.drawImage(img_pre,0,0,q,r);
            }
            var img = new Image();
            img.src = images_array[w];
            canvas.drawImage(img,0,0,q,r);
            canvas_mask.clearRect(0,(r-(r/30)*masker_count)/2,q,(r/30)*masker_count);
            if(((q/30)*masker_count) >= q){
                tran_end();
            }
            masker_count ++;
        }
        function Wipe_to_horizontal_center(){
            if(cu == -1){
                canvas_mask.fillStyle = sbgc;
                canvas_mask.fillRect (0,0,q,r);
            }else{
                var img_pre = new Image();
                img_pre.src = images_array[cu];
                canvas_mask.drawImage(img_pre,0,0,q,r);
            }
            var img = new Image();
            img.src = images_array[w];
            canvas.drawImage(img,0,0,q,r);
            canvas_mask.clearRect(0,0,q,(r/2/30)*masker_count);
            canvas_mask.clearRect(0,r-(r/2/30)*masker_count,q,(r/2/30)*masker_count);
            if(((q/30)*masker_count) >= q){
                tran_end();
            }
            masker_count ++;
        }
        function Wipe_to_vertical_center(){
            if(cu == -1){
                canvas_mask.fillStyle = sbgc;
                canvas_mask.fillRect (0,0,q,r);
            }else{
                var img_pre = new Image();
                img_pre.src = images_array[cu];
                canvas_mask.drawImage(img_pre,0,0,q,r);
            }
            var img = new Image();
            img.src = images_array[w];
            canvas.drawImage(img,0,0,q,r);
            canvas_mask.clearRect(0,0,(q/2/30)*masker_count,r);
            canvas_mask.clearRect(q-(q/2/30)*masker_count,0,(q/2/30)*masker_count,r);
            if(((q/30)*masker_count) >= q){
                tran_end();
            }
            masker_count ++;
        }
        function Wipe_from_bottom_to_top(){
            if(cu == -1){
                canvas_mask.fillStyle = sbgc;
                canvas_mask.fillRect (0,0,q,r);
            }else{
                var img_pre = new Image();
                img_pre.src = images_array[cu];
                canvas_mask.drawImage(img_pre,0,0,q,r);
            }
            var img = new Image();
            img.src = images_array[w];
            canvas.drawImage(img,0,0,q,r);
            canvas_mask.clearRect(0,r-(r/30)*masker_count,q,(r/30)*masker_count);
            if(((q/30)*masker_count) >= q){
                tran_end();
            }
            masker_count ++;
        }
        function Wipe_from_top_to_bottom(){
            if(cu == -1){
                canvas_mask.fillStyle = sbgc;
                canvas_mask.fillRect (0,0,q,r);
            }else{
                var img_pre = new Image();
                img_pre.src = images_array[cu];
                canvas_mask.drawImage(img_pre,0,0,q,r);
            }
            var img = new Image();
            img.src = images_array[w];
            canvas.drawImage(img,0,0,q,r);
            canvas_mask.clearRect(0,0,q,(r/30)*masker_count);
            if(((q/30)*masker_count) >= q){
                tran_end();
            }
            masker_count ++;
        }
        function Wipe_from_right_to_left(){
            if(cu == -1){
                canvas_mask.fillStyle = sbgc;
                canvas_mask.fillRect (0,0,q,r);
            }else{
                var img_pre = new Image();
                img_pre.src = images_array[cu];
                canvas_mask.drawImage(img_pre,0,0,q,r);
            }
            var img = new Image();
            img.src = images_array[w];
            canvas.drawImage(img,0,0,q,r);
            canvas_mask.clearRect(q-(q/30)*masker_count,0,(q/30)*masker_count,r);
            if(((q/30)*masker_count) >= q){
                tran_end();
            }
            masker_count ++;
        }
        function Wipe_from_left_to_right(){
            if(cu == -1){
                canvas_mask.fillStyle = sbgc;
                canvas_mask.fillRect (0,0,q,r);
            }else{
                var img_pre = new Image();
                img_pre.src = images_array[cu];
                canvas_mask.drawImage(img_pre,0,0,q,r);
            }
            var img = new Image();
            img.src = images_array[w];
            canvas.drawImage(img,0,0,q,r);
            canvas_mask.clearRect(0,0,(q/30)*masker_count,r);
            if(((q/30)*masker_count) >= q){
                tran_end();
            }
            masker_count ++;
        }
        function inArray(arr,value){
            var i;
            for (i=0; i < arr.length; i++) {
                if (arr[i] === value) {
                    return true;
                }
            }
            return false;
        }
        function Mosaic(){
            if(cu == -1){
                canvas_mask.fillStyle = sbgc;
                canvas_mask.fillRect (0,0,q,r);
            }else{
                var img_pre = new Image();
                img_pre.src = images_array[cu];
                canvas_mask.drawImage(img_pre,0,0,q,r);
            }
            var img = new Image();
            img.src = images_array[w];
            canvas.drawImage(img,0,0,q,r);
            var block_count = 10;
            var block_width = q/block_count;
            var block_height = r/block_count;
            
            for (var i in mosaic_arr){
                canvas_mask.clearRect(((mosaic_arr[i]-1)%block_count)*block_width,Math.floor((mosaic_arr[i]-1)/block_count)*block_height,block_width,block_height);
            }
            while(true) {
                var rand = Math.floor(Math.random()*(block_count*block_count)+1);
                if(!inArray(mosaic_arr,rand)){
                    mosaic_arr.push(rand);
                    canvas_mask.clearRect(((rand-1)%block_count)*block_width,Math.floor((rand-1)/block_count)*block_height,block_width,block_height);
                    break;
                }

            }
            if(mosaic_arr.length >= (block_count*block_count)){
                tran_end();
            }
        }
        function Wipe_rectangle(){
            if(cu == -1){
                canvas_mask.fillStyle = sbgc;
                canvas_mask.fillRect (0,0,q,r);
            }else{
                var img_pre = new Image();
                img_pre.src = images_array[cu];
                canvas_mask.drawImage(img_pre,0,0,q,r);
            }
            var img = new Image();
            img.src = images_array[w];
            canvas.drawImage(img,0,0,q,r);
            canvas_mask.clearRect((q-(q/30)*masker_count)/2,(r-(r/30)*masker_count)/2,(q/30)*masker_count,(r/30)*masker_count);
            if(((q/30)*masker_count) >= q){
                tran_end();
            }
            masker_count ++;
        }
        function Wipe_from_bottom_right_corner(){
            if(cu == -1){
                canvas_mask.fillStyle = sbgc;
                canvas_mask.fillRect (0,0,q,r);
            }else{
                var img_pre = new Image();
                img_pre.src = images_array[cu];
                canvas_mask.drawImage(img_pre,0,0,q,r);
            }
            var img = new Image();
            img.src = images_array[w];
            canvas.drawImage(img,0,0,q,r);
            canvas_mask.clearRect(q-(q/30)*masker_count,r-(r/30)*masker_count,(q/30)*masker_count,(r/30)*masker_count);
            if(((q/30)*masker_count) >= q){
                tran_end();
            }
            masker_count ++;
        }
        function Wipe_from_bottom_left_corner(){
            if(cu == -1){
                canvas_mask.fillStyle = sbgc;
                canvas_mask.fillRect (0,0,q,r);
            }else{
                var img_pre = new Image();
                img_pre.src = images_array[cu];
                canvas_mask.drawImage(img_pre,0,0,q,r);
            }
            var img = new Image();
            img.src = images_array[w];
            canvas.drawImage(img,0,0,q,r);
            canvas_mask.clearRect(0,r-(r/30)*masker_count,(q/30)*masker_count,(r/30)*masker_count);
            if(((q/30)*masker_count) >= q){
                tran_end();
            }
            masker_count ++;
        }
        function Wipe_from_top_right_corner(){
            if(cu == -1){
                canvas_mask.fillStyle = sbgc;
                canvas_mask.fillRect (0,0,q,r);
            }else{
                var img_pre = new Image();
                img_pre.src = images_array[cu];
                canvas_mask.drawImage(img_pre,0,0,q,r);
            }
            var img = new Image();
            img.src = images_array[w];
            
            canvas.drawImage(img,0,0,q,r);
            
            canvas_mask.clearRect(q-(q/30)*masker_count,0,(q/30)*masker_count,(r/30)*masker_count);
            if(((q/30)*masker_count) >= q){
                tran_end();
            }
            masker_count ++;
        }
        function Wipe_from_top_left_corner(){
            if(cu == -1){
                canvas_mask.fillStyle = sbgc;
                canvas_mask.fillRect (0,0,q,r);
            }else{
                var img_pre = new Image();
                img_pre.src = images_array[cu];
                canvas_mask.drawImage(img_pre,0,0,q,r);
            }
            var img = new Image();
            img.src = images_array[w];
            canvas.drawImage(img,0,0,q,r);
            canvas_mask.clearRect(0,0,(q/30)*masker_count,(r/30)*masker_count);
            if(((q/30)*masker_count) >= q){
                tran_end();
            }
            masker_count ++;
        }
        function Stretch_from_top_left_corner(){
            var img = new Image();
            img.src = images_array[w];
            canvas.drawImage(img,0,0,(q/30)*masker_count,(r/30)*masker_count);
            if(((q/30)*masker_count) >= q){
                tran_end();
            }
            masker_count ++;
        }
        function Stretch_from_bottom_left_corner(){
            var img = new Image();
            img.src = images_array[w];
            canvas.drawImage(img,0,r-(r/30)*masker_count,(q/30)*masker_count,(r/30)*masker_count);
            if(((q/30)*masker_count) >= q){
                tran_end();
            }
            masker_count ++;
        }
        function Stretch_from_top_right_corner(){
            var img = new Image();
            img.src = images_array[w];
            canvas.drawImage(img,q-(q/30)*masker_count,0,(q/30)*masker_count,(r/30)*masker_count);
            if(((q/30)*masker_count) >= q){
                tran_end();
            }
            masker_count ++;
        }
        function Stretch_from_bottom_right_corner(){
            var img = new Image();
            img.src = images_array[w];
            canvas.drawImage(img,q-(q/30)*masker_count,r-(r/30)*masker_count,(q/30)*masker_count,(r/30)*masker_count);
            if(((q/30)*masker_count) >= q){
                tran_end();
            }
            masker_count ++;
        }
        function Stretch_from_left_to_right(){
            var img = new Image();
            img.src = images_array[w];
            
            canvas.drawImage(img,0,0,(q/30)*masker_count,r);
            if(((q/30)*masker_count) >= q){
                tran_end();
            }
            masker_count ++;
        }
        function Stretch_from_top_to_bottom(){
            var img = new Image();
            img.src = images_array[w];
            canvas.drawImage(img,0,0,q,(r/30)*masker_count);
            if(((r/30)*masker_count) >= r){
                tran_end();
            }
            masker_count ++;
        }
        function Stretch_from_right_to_left(){
            var img = new Image();
            img.src = images_array[w];
            
            canvas.drawImage(img,q-(q/30)*masker_count,0,(q/30)*masker_count,r);
            if(((q/30)*masker_count) >= q){
                tran_end();
            }
            masker_count ++;
        }
        function Stretch_from_bottom_to_top(){
            var img = new Image();
            img.src = images_array[w];
            canvas.drawImage(img,0,r-(r/30)*masker_count,q,(r/30)*masker_count);
            if(((r/30)*masker_count) >= r){
                tran_end();
            }
            masker_count ++;
        }
        function Stretch_from_center(){
            var img = new Image();
            img.src = images_array[w];
            canvas.drawImage(img,(q-(q/30)*masker_count)/2,(r-(r/30)*masker_count)/2,(q/30)*masker_count,(r/30)*masker_count);
            if(((r/30)*masker_count) >= r){
                tran_end();
            }
            masker_count ++;
        }
        function Stretch_from_horizontal_center(){
            var img = new Image();
            img.src = images_array[w];
            canvas.drawImage(img,0,(r-(r/30)*masker_count)/2,q,(r/30)*masker_count);
            if(((r/30)*masker_count) >= r){
                tran_end();
            }
            masker_count ++;
        }
        function Stretch_from_vertical_center(){
            var img = new Image();
            img.src = images_array[w];
            canvas.drawImage(img,(q-(q/30)*masker_count)/2,0,(q/30)*masker_count,r);
            if(((q/30)*masker_count) >= q){
                tran_end();
            }
            masker_count ++;
        }
        function stop(){
            clearTimeout(v);
        }
        function text_nothing(){
            canvas_text.clearRect(0,0,q,r);
            canvas_text.fillStyle = sbgc;
            canvas_text.shadowOffsetX = 2;
            canvas_text.shadowOffsetY = 2;
            canvas_text.shadowBlur = 2;
            canvas_text.shadowColor = "rgba(0, 0, 0, 0.5)";
            canvas_text.font = "30px Times New Roman";
            var text = title_array[w];
            canvas_text.fillText(text,5, 30);
        }
        function text_typewriter(){
            canvas_text.clearRect(0,0,q,r);
            canvas_text.shadowOffsetX = 2;
            canvas_text.shadowOffsetY = 2;
            canvas_text.shadowBlur = 2;
            canvas_text.shadowColor = "rgba(0, 0, 0, 0.5)";
            canvas_text.font = "30px Times New Roman";
            canvas_text.fillStyle = sbgc;
            var text = title_array[w];
            var text_length = text.length;
            var tw_timer = setInterval(typewriter,150);
            var tw_current = 0;
            function typewriter(){
                canvas_text.fillText(text[tw_current], 20*tw_current, 25);
                if(tw_current >= text_length-1){
                    clearInterval(tw_timer);
                }
                tw_current ++
            }
        }
        function text_fade(){
            canvas_text.clearRect(0,0,q,r);
            canvas_text.font = "30px Times New Roman";
            var text = title_array[w];
            
            var tw_current = 0;
            
            
            
            var tw_timer = setInterval(fadein,50);
            function fadein(){
                canvas_text.clearRect(0,0,q,r);
                canvas_text.fillStyle = "rgba(255,255,255,"+(tw_current/20)+" )";
                canvas_text.fillText(text,5,30);
                tw_current ++;
                if(tw_current > 20){
                    clearInterval(tw_timer);
                }
            }
        }
        function text_color(){
            var my_w = w;
            canvas_text.clearRect(0,0,q,r);
            canvas_text.shadowOffsetX = 2;
            canvas_text.shadowOffsetY = 2;
            canvas_text.shadowBlur = 2;
            canvas_text.shadowColor = "rgba(0, 0, 0, 0.5)";
            canvas_text.font = "30px Times New Roman";
            canvas_text.fillStyle = sbgc;
            var text = title_array[w];
            var i, n = 0,
            components = [34, 45, 23],
            operations = [1, 2, 3];


            var tw_timer = setInterval(color,10);
            function color(){
                canvas_text.clearRect(0,0,q,r);
                canvas_text.fillStyle = "rgb("+components.join(',')+")";
                canvas_text.fillText(text,5,30);
                for(i = 0; i < 3; i++) {
                    components[i] += operations[i];
                    if ((components[i] >= r) || (components[i] <= 0)) operations[i] *= -1;
                }
                if(my_w != w){
                    clearInterval(tw_timer);
                }
            }
        }
        function text_rebound(){
            var my_w = w;
            canvas_text.clearRect(0,0,q,r);
            canvas_text.shadowOffsetX = 2;
            canvas_text.shadowOffsetY = 2;
            canvas_text.shadowBlur = 2;
            canvas_text.shadowColor = "rgba(0, 0, 0, 0.5)";
            canvas_text.font = "30px Times New Roman";
            canvas_text.textBaseline = 'middle';
            canvas_text.fillStyle = sbgc;
            canvas_text.translate(0, 0);
            var text = title_array[w];
            var i, n = 0,
            components = [0, 0, 0],
            operations = [1, 2, 3];


            var tw_timer = setInterval(rebound,10);
            function rebound(){
                canvas_text.clearRect(0,0,q,r);
                canvas_text.fillText(text,5,components[1]);
                for(i = 0; i < 3; i++) {
                    components[i] += operations[i];
                    if ((components[i] >= r) || (components[i] <= 0)) operations[i] *= -1;
                }
                if(my_w != w){
                    clearInterval(tw_timer);
                }
            }
        }
        function text_runin(){
            var my_w = w;
            var text = title_array[w];
            canvas_text.clearRect(0,0,q,r);
            canvas_text.shadowOffsetX = 2;
            canvas_text.shadowOffsetY = 2;
            canvas_text.shadowBlur = 2;
            canvas_text.shadowColor = "rgba(0, 0, 0, 0.5)";
            canvas_text.fillStyle = sbgc;
            canvas_text.font = "30px Times New Roman";
            canvas_text.textBaseline = 'middle';
            var tw_timer = setInterval(runin,20);
            var tw_current = q;
            function runin(){
                canvas_text.clearRect(0,0,q,r);
                if(my_w != w){
                    clearInterval(tw_timer);
                }
                canvas_text.fillText(text, tw_current, 20);
                if(tw_current <= 10){
                    canvas_text.clearRect(0,0,q,r);
                    canvas_text.fillText(text, tw_current, 20);
                    clearInterval(tw_timer);
                }else{
                    tw_current -= 15 ;
                }
            }
        }
        function text_run(){
            var my_w = w;
            var text = title_array[w];
            canvas_text.clearRect(0,0,q,r);
            canvas_text.shadowOffsetX = 2;
            canvas_text.shadowOffsetY = 2;
            canvas_text.shadowBlur = 2;
            canvas_text.shadowColor = "rgba(0, 0, 0, 0.5)";
            canvas_text.fillStyle = "white";
            canvas_text.font = "30px Times New Roman";
            canvas_text.textAlign = 'center';
            canvas_text.textBaseline = 'middle';
            var tw_timer = setInterval(run,20);
            var tw_current = q;
            function run(){
                canvas_text.clearRect(0,0,q,r);
                if(my_w != w){
                    clearInterval(tw_timer);
                }
                canvas_text.fillText(text, tw_current, 20);

                if(tw_current <= 0){
                    tw_current = q;
                }else{
                    tw_current -= 2 ;
                }
            }
        }
        function text_whirl(){
            var my_w = w;
            var text = title_array[w];
            var n = 0;
            canvas_text.clearRect(0,0,q,r);
            canvas_text.shadowOffsetX = 2;
            canvas_text.shadowOffsetY = 2;
            canvas_text.shadowBlur = 2;
            canvas_text.shadowColor = "rgba(0, 0, 0, 0.5)";
            canvas_text.fillStyle = sbgc;
            canvas_text.font = "30px Times New Roman";
            canvas_text.textAlign = 'center';
            canvas_text.textBaseline = 'middle';
            var tw_timer = setInterval(jump,20);
            function jump(){
                canvas_text.clearRect(0, 0, q, r);
                canvas_text.save();
                canvas_text.translate(q/2, 20);
                canvas_text.rotate(0.03*n++);
                canvas_text.fillText(text, 0, 0);
                canvas_text.restore();
                if(my_w != w){
                    clearInterval(tw_timer);
                }
            }
        }
        function text_jump(){
            var my_w = w;
            var text = title_array[w];
            var text_length = text.length;
            var tw_timer = setInterval(jump,200);
            var tw_current = 0;         
            function jump(){
                if(my_w != w){
                    clearInterval(tw_timer);
                }
                canvas_text.clearRect(0,0,q,r);
                canvas_text.shadowOffsetX = 2;
                canvas_text.shadowOffsetY = 2;
                canvas_text.shadowBlur = 2;
                canvas_text.shadowColor = "rgba(0, 0, 0, 0.5)";
                canvas_text.fillStyle = sbgc;
                for(var i=0;i<=(text_length-1);i++){
                    if(i == tw_current){
                        canvas_text.font = "30px Times New Roman";
                    }else{
                        canvas_text.font = "20px Times New Roman";
                    }
                    canvas_text.fillText(text[i], 15*i, 20);
                }
                if(tw_current >= text_length-1){
                    tw_current = 0;
                }else{
                    tw_current ++
                }
            }
        }
        function tran_end(){
            clearInterval(e);
            if(href_array[w] == undefined){
                href_array[w] = '';
            }
            if(target_array[w] == undefined){
                target_array[w] = '';
            }
            x.children("#webwidget_window").children("a:first").attr("href",href_array[w]);
            x.children("#webwidget_window").children("a:first").attr("target",target_array[w]);
            if(video_array[w] == 'null'){
                v = setTimeout(next,m);
            }else{
                if(video_array[w].html() != null){
                    x.children("#webwidget_canvas_mask").after('<video controls="true" autobuffer="true">'+video_array[w].html()+'</video>');
                    x.children("video").attr("width",q).attr("height",r).css("position","absolute").attr("id","webwidget_video");
                    var vd = document.getElementById("webwidget_video");
                    vd.play();
                    vd.addEventListener("ended", function() {
                        x.children("video").remove();
                        next();
                    }, true);
                }else{
                    v = setTimeout(next,m);
                }
            }
        }
    }
})(jQuery);