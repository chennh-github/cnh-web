/*!
 * Bootstrap-select v1.7.3 (http://silviomoreto.github.io/bootstrap-select)
 *
 * Copyright 2013-2015 bootstrap-select
 * Licensed under MIT (https://github.com/silviomoreto/bootstrap-select/blob/master/LICENSE)
 */
!function(a,b){"function"==typeof define&&define.amd?define(["jquery"],function(a){return b(a)}):"object"==typeof exports?module.exports=b(require("jquery")):b(jQuery)}(this,function(){!function(a){"use strict";function b(b){var c=[{re:/[\xC0-\xC6]/g,ch:"A"},{re:/[\xE0-\xE6]/g,ch:"a"},{re:/[\xC8-\xCB]/g,ch:"E"},{re:/[\xE8-\xEB]/g,ch:"e"},{re:/[\xCC-\xCF]/g,ch:"I"},{re:/[\xEC-\xEF]/g,ch:"i"},{re:/[\xD2-\xD6]/g,ch:"O"},{re:/[\xF2-\xF6]/g,ch:"o"},{re:/[\xD9-\xDC]/g,ch:"U"},{re:/[\xF9-\xFC]/g,ch:"u"},{re:/[\xC7-\xE7]/g,ch:"c"},{re:/[\xD1]/g,ch:"N"},{re:/[\xF1]/g,ch:"n"}];return a.each(c,function(){b=b.replace(this.re,this.ch)}),b}function c(a){var b={"&":"&amp;","<":"&lt;",">":"&gt;",'"':"&quot;","'":"&#x27;","`":"&#x60;"},c="(?:"+Object.keys(b).join("|")+")",d=new RegExp(c),e=new RegExp(c,"g"),f=null==a?"":""+a;return d.test(f)?f.replace(e,function(a){return b[a]}):f}function d(b,c){var d=arguments,f=b,g=c;[].shift.apply(d);var h,i=this.each(function(){var b=a(this);if(b.is("select")){var c=b.data("selectpicker"),i="object"==typeof f&&f;if(c){if(i)for(var j in i)i.hasOwnProperty(j)&&(c.options[j]=i[j])}else{var k=a.extend({},e.DEFAULTS,a.fn.selectpicker.defaults||{},b.data(),i);b.data("selectpicker",c=new e(this,k,g))}"string"==typeof f&&(h=c[f]instanceof Function?c[f].apply(c,d):c.options[f])}});return"undefined"!=typeof h?h:i}String.prototype.includes||!function(){var a={}.toString,b=function(){try{var a={},b=Object.defineProperty,c=b(a,a,a)&&b}catch(d){}return c}(),c="".indexOf,d=function(b){if(null==this)throw TypeError();var d=String(this);if(b&&"[object RegExp]"==a.call(b))throw TypeError();var e=d.length,f=String(b),g=f.length,h=arguments.length>1?arguments[1]:void 0,i=h?Number(h):0;i!=i&&(i=0);var j=Math.min(Math.max(i,0),e);return g+j>e?!1:-1!=c.call(d,f,i)};b?b(String.prototype,"includes",{value:d,configurable:!0,writable:!0}):String.prototype.includes=d}(),String.prototype.startsWith||!function(){var a=function(){try{var a={},b=Object.defineProperty,c=b(a,a,a)&&b}catch(d){}return c}(),b={}.toString,c=function(a){if(null==this)throw TypeError();var c=String(this);if(a&&"[object RegExp]"==b.call(a))throw TypeError();var d=c.length,e=String(a),f=e.length,g=arguments.length>1?arguments[1]:void 0,h=g?Number(g):0;h!=h&&(h=0);var i=Math.min(Math.max(h,0),d);if(f+i>d)return!1;for(var j=-1;++j<f;)if(c.charCodeAt(i+j)!=e.charCodeAt(j))return!1;return!0};a?a(String.prototype,"startsWith",{value:c,configurable:!0,writable:!0}):String.prototype.startsWith=c}(),Object.keys||(Object.keys=function(a,b,c){c=[];for(b in a)c.hasOwnProperty.call(a,b)&&c.push(b);return c}),a.fn.triggerNative=function(a){var b,c=this[0];c.dispatchEvent?("function"==typeof Event?b=new Event(a,{bubbles:!0}):(b=document.createEvent("Event"),b.initEvent(a,!0,!1)),c.dispatchEvent(b)):(c.fireEvent&&(b=document.createEventObject(),b.eventType=a,c.fireEvent("on"+a,b)),this.trigger(a))},a.expr[":"].icontains=function(b,c,d){var e=a(b),f=(e.data("tokens")||e.text()).toUpperCase();return f.includes(d[3].toUpperCase())},a.expr[":"].ibegins=function(b,c,d){var e=a(b),f=(e.data("tokens")||e.text()).toUpperCase();return f.startsWith(d[3].toUpperCase())},a.expr[":"].aicontains=function(b,c,d){var e=a(b),f=(e.data("tokens")||e.data("normalizedText")||e.text()).toUpperCase();return f.includes(d[3].toUpperCase())},a.expr[":"].aibegins=function(b,c,d){var e=a(b),f=(e.data("tokens")||e.data("normalizedText")||e.text()).toUpperCase();return f.startsWith(d[3].toUpperCase())};var e=function(b,c,d){d&&(d.stopPropagation(),d.preventDefault()),this.$element=a(b),this.$newElement=null,this.$button=null,this.$menu=null,this.$lis=null,this.options=c,null===this.options.title&&(this.options.title=this.$element.attr("title")),this.val=e.prototype.val,this.render=e.prototype.render,this.refresh=e.prototype.refresh,this.setStyle=e.prototype.setStyle,this.selectAll=e.prototype.selectAll,this.deselectAll=e.prototype.deselectAll,this.destroy=e.prototype.remove,this.remove=e.prototype.remove,this.show=e.prototype.show,this.hide=e.prototype.hide,this.init()};e.VERSION="1.7.2",e.DEFAULTS={noneSelectedText:"Nothing selected",noneResultsText:"No results matched {0}",countSelectedText:function(a,b){return 1==a?"{0} item selected":"{0} items selected"},maxOptionsText:function(a,b){return[1==a?"Limit reached ({n} item max)":"Limit reached ({n} items max)",1==b?"Group limit reached ({n} item max)":"Group limit reached ({n} items max)"]},selectAllText:"Select All",deselectAllText:"Deselect All",doneButton:!1,doneButtonText:"Close",multipleSeparator:", ",styleBase:"btn",style:"btn-default",size:"auto",title:null,selectedTextFormat:"values",width:!1,container:!1,hideDisabled:!1,showSubtext:!1,showIcon:!0,showContent:!0,dropupAuto:!0,header:!1,liveSearch:!1,liveSearchPlaceholder:null,liveSearchNormalize:!1,liveSearchStyle:"contains",actionsBox:!1,iconBase:"glyphicon",tickIcon:"glyphicon-ok",maxOptions:!1,mobile:!1,selectOnTab:!1,dropdownAlignRight:!1},e.prototype={constructor:e,init:function(){var b=this,c=this.$element.attr("id");this.$element.addClass("bs-select-hidden"),this.liObj={},this.multiple=this.$element.prop("multiple"),this.autofocus=this.$element.prop("autofocus"),this.$newElement=this.createView(),this.$element.after(this.$newElement),this.$button=this.$newElement.children("button"),this.$menu=this.$newElement.children(".dropdown-menu"),this.$menuInner=this.$menu.children(".inner"),this.$searchbox=this.$menu.find("input"),this.options.dropdownAlignRight&&this.$menu.addClass("dropdown-menu-right"),"undefined"!=typeof c&&(this.$button.attr("data-id",c),a('label[for="'+c+'"]').click(function(a){a.preventDefault(),b.$button.focus()})),this.checkDisabled(),this.clickListener(),this.options.liveSearch&&this.liveSearchListener(),this.render(),this.setStyle(),this.setWidth(),this.options.container&&this.selectPosition(),this.$menu.data("this",this),this.$newElement.data("this",this),this.options.mobile&&this.mobile(),this.$newElement.on("hide.bs.dropdown",function(a){b.$element.trigger("hide.bs.select",a)}),this.$newElement.on("hidden.bs.dropdown",function(a){b.$element.trigger("hidden.bs.select",a)}),this.$newElement.on("show.bs.dropdown",function(a){b.$element.trigger("show.bs.select",a)}),this.$newElement.on("shown.bs.dropdown",function(a){b.$element.trigger("shown.bs.select",a)}),setTimeout(function(){b.$element.trigger("loaded.bs.select")})},createDropdown:function(){var b=this.multiple?" show-tick":"",d=this.$element.parent().hasClass("input-group")?" input-group-btn":"",e=this.autofocus?" autofocus":"",f=this.options.header?'<div class="popover-title"><button type="button" class="close" aria-hidden="true">&times;</button>'+this.options.header+"</div>":"",g=this.options.liveSearch?'<div class="bs-searchbox"><input type="text" class="form-control" autocomplete="off"'+(null===this.options.liveSearchPlaceholder?"":' placeholder="'+c(this.options.liveSearchPlaceholder)+'"')+"></div>":"",h=this.multiple&&this.options.actionsBox?'<div class="bs-actionsbox"><div class="btn-group btn-group-sm btn-block"><button type="button" class="actions-btn bs-select-all btn btn-default">'+this.options.selectAllText+'</button><button type="button" class="actions-btn bs-deselect-all btn btn-default">'+this.options.deselectAllText+"</button></div></div>":"",i=this.multiple&&this.options.doneButton?'<div class="bs-donebutton"><div class="btn-group btn-block"><button type="button" class="btn btn-sm btn-default">'+this.options.doneButtonText+"</button></div></div>":"",j='<div class="btn-group bootstrap-select'+b+d+'"><button type="button" class="'+this.options.styleBase+' dropdown-toggle" data-toggle="dropdown"'+e+'><span class="filter-option pull-left"></span>&nbsp;<span class="caret"></span></button><div class="dropdown-menu open">'+f+g+h+'<ul class="dropdown-menu inner" role="menu"></ul>'+i+"</div></div>";return a(j)},createView:function(){var a=this.createDropdown(),b=this.createLi();return a.find("ul")[0].innerHTML=b,a},reloadLi:function(){this.destroyLi();var a=this.createLi();this.$menuInner[0].innerHTML=a},destroyLi:function(){this.$menu.find("li").remove()},createLi:function(){var d=this,e=[],f=0,g=document.createElement("option"),h=-1,i=function(a,b,c,d){return"<li"+("undefined"!=typeof c&""!==c?' class="'+c+'"':"")+("undefined"!=typeof b&null!==b?' data-original-index="'+b+'"':"")+("undefined"!=typeof d&null!==d?'data-optgroup="'+d+'"':"")+">"+a+"</li>"},j=function(a,e,f,g){return'<a tabindex="0"'+("undefined"!=typeof e?' class="'+e+'"':"")+("undefined"!=typeof f?' style="'+f+'"':"")+(d.options.liveSearchNormalize?' data-normalized-text="'+b(c(a))+'"':"")+("undefined"!=typeof g||null!==g?' data-tokens="'+g+'"':"")+">"+a+'<span class="'+d.options.iconBase+" "+d.options.tickIcon+' check-mark"></span></a>'};if(this.options.title&&!this.multiple&&(h--,!this.$element.find(".bs-title-option").length)){var k=this.$element[0];g.className="bs-title-option",g.appendChild(document.createTextNode(this.options.title)),g.value="",k.insertBefore(g,k.firstChild),void 0===a(k.options[k.selectedIndex]).attr("selected")&&(g.selected=!0)}return this.$element.find("option").each(function(b){var c=a(this);if(h++,!c.hasClass("bs-title-option")){var g=this.className||"",k=this.style.cssText,l=c.data("content")?c.data("content"):c.html(),m=c.data("tokens")?c.data("tokens"):null,n="undefined"!=typeof c.data("subtext")?'<small class="text-muted">'+c.data("subtext")+"</small>":"",o="undefined"!=typeof c.data("icon")?'<span class="'+d.options.iconBase+" "+c.data("icon")+'"></span> ':"",p=this.disabled||"OPTGROUP"===this.parentElement.tagName&&this.parentElement.disabled;if(""!==o&&p&&(o="<span>"+o+"</span>"),d.options.hideDisabled&&p)return void h--;if(c.data("content")||(l=o+'<span class="text">'+l+n+"</span>"),"OPTGROUP"===this.parentElement.tagName&&c.data("divider")!==!0){var q=" "+this.parentElement.className||"";if(0===c.index()){f+=1;var r=this.parentElement.label,s="undefined"!=typeof c.parent().data("subtext")?'<small class="text-muted">'+c.parent().data("subtext")+"</small>":"",t=c.parent().data("icon")?'<span class="'+d.options.iconBase+" "+c.parent().data("icon")+'"></span> ':"";r=t+'<span class="text">'+r+s+"</span>",0!==b&&e.length>0&&(h++,e.push(i("",null,"divider",f+"div"))),h++,e.push(i(r,null,"dropdown-header"+q,f))}e.push(i(j(l,"opt "+g+q,k,m),b,"",f))}else c.data("divider")===!0?e.push(i("",b,"divider")):c.data("hidden")===!0?e.push(i(j(l,g,k,m),b,"hidden is-hidden")):(this.previousElementSibling&&"OPTGROUP"===this.previousElementSibling.tagName&&(h++,e.push(i("",null,"divider",f+"div"))),e.push(i(j(l,g,k,m),b)));d.liObj[b]=h}}),this.multiple||0!==this.$element.find("option:selected").length||this.options.title||this.$element.find("option").eq(0).prop("selected",!0).attr("selected","selected"),e.join("")},findLis:function(){return null==this.$lis&&(this.$lis=this.$menu.find("li")),this.$lis},render:function(b){var c,d=this;b!==!1&&this.$element.find("option").each(function(a){var b=d.findLis().eq(d.liObj[a]);d.setDisabled(a,this.disabled||"OPTGROUP"===this.parentElement.tagName&&this.parentElement.disabled,b),d.setSelected(a,this.selected,b)}),this.tabIndex();var e=this.$element.find("option").map(function(){if(this.selected){if(d.options.hideDisabled&&(this.disabled||"OPTGROUP"===this.parentElement.tagName&&this.parentElement.disabled))return!1;var b,c=a(this),e=c.data("icon")&&d.options.showIcon?'<i class="'+d.options.iconBase+" "+c.data("icon")+'"></i> ':"";return b=d.options.showSubtext&&c.data("subtext")&&!d.multiple?' <small class="text-muted">'+c.data("subtext")+"</small>":"","undefined"!=typeof c.attr("title")?c.attr("title"):c.data("content")&&d.options.showContent?c.data("content"):e+c.html()+b}}).toArray(),f=this.multiple?e.join(this.options.multipleSeparator):e[0];if(this.multiple&&this.options.selectedTextFormat.indexOf("count")>-1){var g=this.options.selectedTextFormat.split(">");if(g.length>1&&e.length>g[1]||1==g.length&&e.length>=2){c=this.options.hideDisabled?", [disabled]":"";var h=this.$element.find("option").not('[data-divider="true"], [data-hidden="true"]'+c).length,i="function"==typeof this.options.countSelectedText?this.options.countSelectedText(e.length,h):this.options.countSelectedText;f=i.replace("{0}",e.length.toString()).replace("{v4.3.0}",h.toString())}}void 0==this.options.title&&(this.options.title=this.$element.attr("title")),"static"==this.options.selectedTextFormat&&(f=this.options.title),f||(f="undefined"!=typeof this.options.title?this.options.title:this.options.noneSelectedText),this.$button.attr("title",a.trim(f.replace(/<[^>]*>?/g,""))),this.$button.children(".filter-option").html(f),this.$element.trigger("rendered.bs.select")},setStyle:function(a,b){this.$element.attr("class")&&this.$newElement.addClass(this.$element.attr("class").replace(/selectpicker|mobile-device|bs-select-hidden|validate\[.*\]/gi,""));var c=a?a:this.options.style;"add"==b?this.$button.addClass(c):"remove"==b?this.$button.removeClass(c):(this.$button.removeClass(this.options.style),this.$button.addClass(c))},liHeight:function(b){if(b||this.options.size!==!1&&!this.sizeInfo){var c=document.createElement("div"),d=document.createElement("div"),e=document.createElement("ul"),f=document.createElement("li"),g=document.createElement("li"),h=document.createElement("a"),i=document.createElement("span"),j=this.options.header?this.$menu.find(".popover-title")[0].cloneNode(!0):null,k=this.options.liveSearch?document.createElement("div"):null,l=this.options.actionsBox&&this.multiple?this.$menu.find(".bs-actionsbox")[0].cloneNode(!0):null,m=this.options.doneButton&&this.multiple?this.$menu.find(".bs-donebutton")[0].cloneNode(!0):null;if(i.className="text",c.className=this.$menu[0].parentNode.className+" open",d.className="dropdown-menu open",e.className="dropdown-menu inner",f.className="divider",i.appendChild(document.createTextNode("Inner text")),h.appendChild(i),g.appendChild(h),e.appendChild(g),e.appendChild(f),j&&d.appendChild(j),k){var n=document.createElement("span");k.className="bs-searchbox",n.className="form-control",k.appendChild(n),d.appendChild(k)}l&&d.appendChild(l),d.appendChild(e),m&&d.appendChild(m),c.appendChild(d),document.body.appendChild(c);var o=h.offsetHeight,p=j?j.offsetHeight:0,q=k?k.offsetHeight:0,r=l?l.offsetHeight:0,s=m?m.offsetHeight:0,t=a(f).outerHeight(!0),u="function"==typeof getComputedStyle?getComputedStyle(d):!1,v=u?null:a(d),w=parseInt(u?u.paddingTop:v.css("paddingTop"))+parseInt(u?u.paddingBottom:v.css("paddingBottom"))+parseInt(u?u.borderTopWidth:v.css("borderTopWidth"))+parseInt(u?u.borderBottomWidth:v.css("borderBottomWidth")),x=w+parseInt(u?u.marginTop:v.css("marginTop"))+parseInt(u?u.marginBottom:v.css("marginBottom"))+2;document.body.removeChild(c),this.sizeInfo={liHeight:o,headerHeight:p,searchHeight:q,actionsHeight:r,doneButtonHeight:s,dividerHeight:t,menuPadding:w,menuExtras:x}}},setSize:function(){if(this.findLis(),this.liHeight(),this.options.header&&this.$menu.css("padding-top",0),this.options.size!==!1){var b,c,d,e,f=this,g=this.$menu,h=this.$menuInner,i=a(window),j=this.$newElement[0].offsetHeight,k=this.sizeInfo.liHeight,l=this.sizeInfo.headerHeight,m=this.sizeInfo.searchHeight,n=this.sizeInfo.actionsHeight,o=this.sizeInfo.doneButtonHeight,p=this.sizeInfo.dividerHeight,q=this.sizeInfo.menuPadding,r=this.sizeInfo.menuExtras,s=this.options.hideDisabled?".disabled":"",t=function(){d=f.$newElement.offset().top-i.scrollTop(),e=i.height()-d-j};if(t(),"auto"===this.options.size){var u=function(){var i,j=function(b,c){return function(d){return c?d.classList?d.classList.contains(b):a(d).hasClass(b):!(d.classList?d.classList.contains(b):a(d).hasClass(b))}},p=f.$menuInner[0].getElementsByTagName("li"),s=Array.prototype.filter?Array.prototype.filter.call(p,j("hidden",!1)):f.$lis.not(".hidden"),u=Array.prototype.filter?Array.prototype.filter.call(s,j("dropdown-header",!0)):s.filter(".dropdown-header");t(),b=e-r,f.options.container?(g.data("height")||g.data("height",g.height()),c=g.data("height")):c=g.height(),f.options.dropupAuto&&f.$newElement.toggleClass("dropup",d>e&&c>b-r),f.$newElement.hasClass("dropup")&&(b=d-r),i=s.length+u.length>3?3*k+r-2:0,g.css({"max-height":b+"px",overflow:"hidden","min-height":i+l+m+n+o+"px"}),h.css({"max-height":b-l-m-n-o-q+"px","overflow-y":"auto","min-height":Math.max(i-q,0)+"px"})};u(),this.$searchbox.off("input.getSize propertychange.getSize").on("input.getSize propertychange.getSize",u),i.off("resize.getSize scroll.getSize").on("resize.getSize scroll.getSize",u)}else if(this.options.size&&"auto"!=this.options.size&&this.$lis.not(s).length>this.options.size){var v=this.$lis.not(".divider").not(s).children().slice(0,this.options.size).last().parent().index(),w=this.$lis.slice(0,v+1).filter(".divider").length;b=k*this.options.size+w*p+q,f.options.container?(g.data("height")||g.data("height",g.height()),c=g.data("height")):c=g.height(),f.options.dropupAuto&&this.$newElement.toggleClass("dropup",d>e&&c>b-r),g.css({"max-height":b+l+m+n+o+"px",overflow:"hidden","min-height":""}),h.css({"max-height":b-q+"px","overflow-y":"auto","min-height":""})}}},setWidth:function(){if("auto"===this.options.width){this.$menu.css("css-width","0");var a=this.$menu.parent().clone().appendTo("body"),b=this.options.container?this.$newElement.clone().appendTo("body"):a,c=a.children(".dropdown-menu").outerWidth(),d=b.css("width","auto").children("button").outerWidth();a.remove(),b.remove(),this.$newElement.css("width",Math.max(c,d)+"px")}else"fit"===this.options.width?(this.$menu.css("css-width",""),this.$newElement.css("width","").addClass("fit-width")):this.options.width?(this.$menu.css("css-width",""),this.$newElement.css("width",this.options.width)):(this.$menu.css("css-width",""),this.$newElement.css("width",""));this.$newElement.hasClass("fit-width")&&"fit"!==this.options.width&&this.$newElement.removeClass("fit-width")},selectPosition:function(){var b,c,d=this,e="<div />",f=a(e),g=function(a){f.addClass(a.attr("class").replace(/form-control|fit-width/gi,"")).toggleClass("dropup",a.hasClass("dropup")),b=a.offset(),c=a.hasClass("dropup")?0:a[0].offsetHeight,f.css({top:b.top+c,left:b.left,width:a[0].offsetWidth,position:"absolute"})};this.$newElement.on("click",function(){d.isDisabled()||(g(a(this)),f.appendTo(d.options.container),f.toggleClass("open",!a(this).hasClass("open")),f.append(d.$menu))}),a(window).on("resize scroll",function(){g(d.$newElement)}),this.$element.on("hide.bs.select",function(){d.$menu.data("height",d.$menu.height()),f.detach()})},setSelected:function(a,b,c){if(!c)var c=this.findLis().eq(this.liObj[a]);c.toggleClass("selected",b)},setDisabled:function(a,b,c){if(!c)var c=this.findLis().eq(this.liObj[a]);b?c.addClass("disabled").children("a").attr("href","#").attr("tabindex",-1):c.removeClass("disabled").children("a").removeAttr("href").attr("tabindex",0)},isDisabled:function(){return this.$element[0].disabled},checkDisabled:function(){var a=this;this.isDisabled()?(this.$newElement.addClass("disabled"),this.$button.addClass("disabled").attr("tabindex",-1)):(this.$button.hasClass("disabled")&&(this.$newElement.removeClass("disabled"),this.$button.removeClass("disabled")),-1!=this.$button.attr("tabindex")||this.$element.data("tabindex")||this.$button.removeAttr("tabindex")),this.$button.click(function(){return!a.isDisabled()})},tabIndex:function(){this.$element.is("[tabindex]")&&(this.$element.data("tabindex",this.$element.attr("tabindex")),this.$button.attr("tabindex",this.$element.data("tabindex")))},clickListener:function(){var b=this,c=a(document);this.$newElement.on("touchstart.dropdown",".dropdown-menu",function(a){a.stopPropagation()}),c.data("spaceSelect",!1),this.$button.on("keyup",function(a){/(32)/.test(a.keyCode.toString(10))&&c.data("spaceSelect")&&(a.preventDefault(),c.data("spaceSelect",!1))}),this.$newElement.on("click",function(){b.setSize(),b.$element.on("shown.bs.select",function(){if(b.options.liveSearch||b.multiple){if(!b.multiple){var a=b.liObj[b.$element[0].selectedIndex];if("number"!=typeof a||b.options.size===!1)return;var c=b.$lis.eq(a)[0].offsetTop-b.$menuInner[0].offsetTop;c=c-b.$menuInner[0].offsetHeight/2+b.sizeInfo.liHeight/2,b.$menuInner[0].scrollTop=c}}else b.$menu.find(".selected a").focus()})}),this.$menu.on("click","li a",function(c){var d=a(this),e=d.parent().attr("data-original-index"),f=b.$element.val(),g=b.$element.prop("selectedIndex");if(b.multiple&&c.stopPropagation(),c.preventDefault(),!b.isDisabled()&&!d.parent().hasClass("disabled")){var h=b.$element.find("option"),i=h.eq(e),j=i.prop("selected"),k=i.parent("optgroup"),l=b.options.maxOptions,m=k.data("maxOptions")||!1;if(b.multiple){if(i.prop("selected",!j),b.setSelected(e,!j),d.blur(),l!==!1||m!==!1){var n=l<h.filter(":selected").length,o=m<k.find("option:selected").length;if(l&&n||m&&o)if(l&&1==l)h.prop("selected",!1),i.prop("selected",!0),b.$menu.find(".selected").removeClass("selected"),b.setSelected(e,!0);else if(m&&1==m){k.find("option:selected").prop("selected",!1),i.prop("selected",!0);var p=d.parent().data("optgroup");b.$menu.find('[data-optgroup="'+p+'"]').removeClass("selected"),b.setSelected(e,!0)}else{var q="function"==typeof b.options.maxOptionsText?b.options.maxOptionsText(l,m):b.options.maxOptionsText,r=q[0].replace("{n}",l),s=q[1].replace("{n}",m),t=a('<div class="notify"></div>');q[2]&&(r=r.replace("{var}",q[2][l>1?0:1]),s=s.replace("{var}",q[2][m>1?0:1])),i.prop("selected",!1),b.$menu.append(t),l&&n&&(t.append(a("<div>"+r+"</div>")),b.$element.trigger("maxReached.bs.select")),m&&o&&(t.append(a("<div>"+s+"</div>")),b.$element.trigger("maxReachedGrp.bs.select")),setTimeout(function(){b.setSelected(e,!1)},10),t.delay(750).fadeOut(300,function(){a(this).remove()})}}}else h.prop("selected",!1),i.prop("selected",!0),b.$menu.find(".selected").removeClass("selected"),b.setSelected(e,!0);b.multiple?b.options.liveSearch&&b.$searchbox.focus():b.$button.focus(),(f!=b.$element.val()&&b.multiple||g!=b.$element.prop("selectedIndex")&&!b.multiple)&&(b.$element.triggerNative("change"),b.$element.trigger("changed.bs.select",[e,i.prop("selected"),j]))}}),this.$menu.on("click","li.disabled a, .popover-title, .popover-title :not(.close)",function(c){c.currentTarget==this&&(c.preventDefault(),c.stopPropagation(),b.options.liveSearch&&!a(c.target).hasClass("close")?b.$searchbox.focus():b.$button.focus())}),this.$menu.on("click","li.divider, li.dropdown-header",function(a){a.preventDefault(),a.stopPropagation(),b.options.liveSearch?b.$searchbox.focus():b.$button.focus()}),this.$menu.on("click",".popover-title .close",function(){b.$button.click()}),this.$searchbox.on("click",function(a){a.stopPropagation()}),this.$menu.on("click",".actions-btn",function(c){b.options.liveSearch?b.$searchbox.focus():b.$button.focus(),c.preventDefault(),c.stopPropagation(),a(this).hasClass("bs-select-all")?b.selectAll():b.deselectAll(),b.$element.triggerNative("change")}),this.$element.change(function(){b.render(!1)})},liveSearchListener:function(){var d=this,e=a('<li class="no-results"></li>');this.$newElement.on("click.dropdown.data-api touchstart.dropdown.data-api",function(){d.$menuInner.find(".active").removeClass("active"),d.$searchbox.val()&&(d.$searchbox.val(""),d.$lis.not(".is-hidden").removeClass("hidden"),e.parent().length&&e.remove()),d.multiple||d.$menuInner.find(".selected").addClass("active"),setTimeout(function(){d.$searchbox.focus()},10)}),this.$searchbox.on("click.dropdown.data-api focus.dropdown.data-api touchend.dropdown.data-api",function(a){a.stopPropagation()}),this.$searchbox.on("input propertychange",function(){if(d.$searchbox.val()){var f=d.$lis.not(".is-hidden").removeClass("hidden").children("a");f=d.options.liveSearchNormalize?f.not(":a"+d._searchStyle()+'("'+b(d.$searchbox.val())+'")'):f.not(":"+d._searchStyle()+'("'+d.$searchbox.val()+'")'),f.parent().addClass("hidden"),d.$lis.filter(".dropdown-header").each(function(){var b=a(this),c=b.data("optgroup");0===d.$lis.filter("[data-optgroup="+c+"]").not(b).not(".hidden").length&&(b.addClass("hidden"),d.$lis.filter("[data-optgroup="+c+"div]").addClass("hidden"))});var g=d.$lis.not(".hidden");g.each(function(b){var c=a(this);c.hasClass("divider")&&(c.index()===g.eq(0).index()||c.index()===g.last().index()||g.eq(b+1).hasClass("divider"))&&c.addClass("hidden")}),d.$lis.not(".hidden, .no-results").length?e.parent().length&&e.remove():(e.parent().length&&e.remove(),e.html(d.options.noneResultsText.replace("{0}",'"'+c(d.$searchbox.val())+'"')).show(),d.$menuInner.append(e))}else d.$lis.not(".is-hidden").removeClass("hidden"),e.parent().length&&e.remove();d.$lis.filter(".active").removeClass("active"),d.$searchbox.val()&&d.$lis.not(".hidden, .divider, .dropdown-header").eq(0).addClass("active").children("a").focus(),a(this).focus()})},_searchStyle:function(){var a="icontains";switch(this.options.liveSearchStyle){case"begins":case"startsWith":a="ibegins";break;case"contains":}return a},val:function(a){return"undefined"!=typeof a?(this.$element.val(a),this.render(),this.$element):this.$element.val()},selectAll:function(){this.findLis(),this.$element.find("option:enabled").not("[data-divider], [data-hidden]").prop("selected",!0),this.$lis.not(".divider, .dropdown-header, .disabled, .hidden").addClass("selected"),this.render(!1)},deselectAll:function(){this.findLis(),this.$element.find("option:enabled").not("[data-divider], [data-hidden]").prop("selected",!1),this.$lis.not(".divider, .dropdown-header, .disabled, .hidden").removeClass("selected"),this.render(!1)},keydown:function(c){var d,e,f,g,h,i,j,k,l,m=a(this),n=m.is("input")?m.parent().parent():m.parent(),o=n.data("this"),p=":not(.disabled, .hidden, .dropdown-header, .divider)",q={32:" ",48:"0",49:"1",50:"2",51:"3",52:"4",53:"5",54:"6",55:"7",56:"8",57:"9",59:";",65:"a",66:"b",67:"c",68:"d",69:"e",70:"f",71:"g",72:"h",73:"i",74:"j",75:"k",76:"l",77:"m",78:"n",79:"o",80:"p",81:"q",82:"r",83:"s",84:"t",85:"u",86:"v",87:"w",88:"x",89:"y",90:"z",96:"0",97:"1",98:"2",99:"3",100:"4",101:"5",102:"6",103:"7",104:"8",105:"9"};if(o.options.liveSearch&&(n=m.parent().parent()),o.options.container&&(n=o.$menu),d=a("[role=menu] li a",n),l=o.$menu.parent().hasClass("open"),!l&&(c.keyCode>=48&&c.keyCode<=57||c.keyCode>=65&&c.keyCode<=90)&&(o.options.container?o.$newElement.trigger("click"):(o.setSize(),o.$menu.parent().addClass("open"),l=!0),o.$searchbox.focus()),o.options.liveSearch&&(/(^9$|27)/.test(c.keyCode.toString(10))&&l&&0===o.$menu.find(".active").length&&(c.preventDefault(),o.$menu.parent().removeClass("open"),o.options.container&&o.$newElement.removeClass("open"),o.$button.focus()),d=a("[role=menu] li:not(.disabled, .hidden, .dropdown-header, .divider)",n),m.val()||/(38|40)/.test(c.keyCode.toString(10))||0===d.filter(".active").length&&(d=o.$newElement.find("li"),d=o.options.liveSearchNormalize?d.filter(":a"+o._searchStyle()+"("+b(q[c.keyCode])+")"):d.filter(":"+o._searchStyle()+"("+q[c.keyCode]+")"))),d.length){if(/(38|40)/.test(c.keyCode.toString(10)))e=d.index(d.filter(":focus")),g=d.parent(p).first().data("originalIndex"),h=d.parent(p).last().data("originalIndex"),f=d.eq(e).parent().nextAll(p).eq(0).data("originalIndex"),i=d.eq(e).parent().prevAll(p).eq(0).data("originalIndex"),j=d.eq(f).parent().prevAll(p).eq(0).data("originalIndex"),o.options.liveSearch&&(d.each(function(b){a(this).hasClass("disabled")||a(this).data("index",b)}),e=d.index(d.filter(".active")),g=d.first().data("index"),h=d.last().data("index"),f=d.eq(e).nextAll().eq(0).data("index"),i=d.eq(e).prevAll().eq(0).data("index"),j=d.eq(f).prevAll().eq(0).data("index")),k=m.data("prevIndex"),38==c.keyCode?(o.options.liveSearch&&(e-=1),e!=j&&e>i&&(e=i),g>e&&(e=g),e==k&&(e=h)):40==c.keyCode&&(o.options.liveSearch&&(e+=1),-1==e&&(e=0),e!=j&&f>e&&(e=f),e>h&&(e=h),e==k&&(e=g)),m.data("prevIndex",e),o.options.liveSearch?(c.preventDefault(),m.hasClass("dropdown-toggle")||(d.removeClass("active").eq(e).addClass("active").children("a").focus(),m.focus())):d.eq(e).focus();else if(!m.is("input")){var r,s,t=[];d.each(function(){a(this).parent().hasClass("disabled")||a.trim(a(this).text().toLowerCase()).substring(0,1)==q[c.keyCode]&&t.push(a(this).parent().index())}),r=a(document).data("keycount"),r++,a(document).data("keycount",r),s=a.trim(a(":focus").text().toLowerCase()).substring(0,1),s!=q[c.keyCode]?(r=1,a(document).data("keycount",r)):r>=t.length&&(a(document).data("keycount",0),r>t.length&&(r=1)),d.eq(t[r-1]).focus()}if((/(13|32)/.test(c.keyCode.toString(10))||/(^9$)/.test(c.keyCode.toString(10))&&o.options.selectOnTab)&&l){if(/(32)/.test(c.keyCode.toString(10))||c.preventDefault(),o.options.liveSearch)/(32)/.test(c.keyCode.toString(10))||(o.$menu.find(".active a").click(),m.focus());else{var u=a(":focus");u.click(),u.focus(),c.preventDefault(),a(document).data("spaceSelect",!0)}a(document).data("keycount",0)}(/(^9$|27)/.test(c.keyCode.toString(10))&&l&&(o.multiple||o.options.liveSearch)||/(27)/.test(c.keyCode.toString(10))&&!l)&&(o.$menu.parent().removeClass("open"),o.options.container&&o.$newElement.removeClass("open"),o.$button.focus())}},mobile:function(){this.$element.addClass("mobile-device").appendTo(this.$newElement),this.options.container&&this.$menu.hide()},refresh:function(){this.$lis=null,this.liObj={},this.reloadLi(),this.render(),this.checkDisabled(),this.liHeight(!0),this.setStyle(),this.setWidth(),this.$lis&&this.$searchbox.trigger("propertychange"),this.$element.trigger("refreshed.bs.select")},hide:function(){this.$newElement.hide()},show:function(){this.$newElement.show()},remove:function(){this.$newElement.remove(),this.$element.remove()}};var f=a.fn.selectpicker;a.fn.selectpicker=d,a.fn.selectpicker.Constructor=e,a.fn.selectpicker.noConflict=function(){return a.fn.selectpicker=f,this},a(document).data("keycount",0).on("keydown",'.bootstrap-select [data-toggle=dropdown], .bootstrap-select [role="menu"], .bs-searchbox input',e.prototype.keydown).on("focusin.modal",'.bootstrap-select [data-toggle=dropdown], .bootstrap-select [role="menu"], .bs-searchbox input',function(a){a.stopPropagation()}),a(window).on("load.bs.select.data-api",function(){a(".selectpicker").each(function(){var b=a(this);d.call(b,b.data())})})}(jQuery)});
//# sourceMappingURL=bootstrap-select.js.map