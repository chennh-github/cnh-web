function changeModal(a,b){var c=["default","primary","info","success","warning","danger","inverse"];for(var d in c)$(a).removeClass("modal-"+c[d]);$(a).addClass("modal-"+b)}$(document).ready(function(){$("#demo-settings-toggler").click(function(){return $("#demo-settings").toggleClass("open"),!1}),$(window).scroll(function(){$(window).scrollTop()>0?$("#right-notifier").addClass("right-notifier-sticky"):$("#right-notifier").removeClass("right-notifier-sticky")}),$(".right-notifier-content").slimScroll({height:"100%",railOpacity:.4,wheelStep:12}),$("#right-notifier-toggler").click(function(){$("#right-notifier").toggleClass("right-notifier-open")}),$("#right-notifier-toggler-2").click(function(){$("#right-notifier").hasClass("right-notifier-open")||$("#right-notifier").addClass("right-notifier-open")}),$("#right-notifier-toggler-3").click(function(){$("#right-notifier").hasClass("right-notifier-open")||$("#right-notifier").addClass("right-notifier-open")}),$("#profile-toggler").click(function(){$("#right-notifier").removeClass("right-notifier-open")}),$(".container-navbar-fixer-nav").click(function(){$("#wrapper").toggleClass("container-navbar-fixed"),$(this).toggleClass("bold")}),$(".container-footer-fixer-nav").click(function(){$("#wrapper").toggleClass("container-footer-fixed"),$("#wrapper").removeClass("boxed"),$(this).toggleClass("bold")}),$(".sidebar-collapser-nav").click(function(){$("#wrapper").toggleClass("mini-sidebar"),$(this).toggleClass("bold")}),$(".boxed-selector-nav").click(function(){$("#wrapper").toggleClass("boxed"),$("#wrapper").removeClass("container-footer-fixed"),$(this).toggleClass("bold")}),$(".sidebar-fixed-nav").click(function(){$("#wrapper").toggleClass("fixed-sidebar"),$(this).toggleClass("bold")}),$(".container-navbar-fixer").click(function(){$("#wrapper").toggleClass("container-navbar-fixed")}),$(".container-footer-fixer").click(function(){$("#wrapper").toggleClass("container-footer-fixed"),$("#wrapper").removeClass("boxed"),$(".boxed-selector").hasClass("checked")&&$(".boxed-selector").removeClass("checked")}),$(".sidebar-collapser").click(function(){$("#wrapper").toggleClass("mini-sidebar")}),$(".boxed-selector").click(function(){$("#wrapper").toggleClass("boxed"),$("#wrapper").removeClass("container-footer-fixed"),$(".container-footer-fixer").hasClass("checked")&&$(".container-footer-fixer").removeClass("checked")}),$(".sidebar-fixed").click(function(){$("#wrapper").toggleClass("fixed-sidebar"),$("#wrapper").hasClass("fixed-sidebar")&&!$("#wrapper").hasClass("mini-sidebar")?($(".sidebar-wrapper").slimScroll({width:"200px",height:"100%",railOpacity:.3,wheelStep:12}),$(".sidebar-wrapper").css("min-height","")):($(".sidebar-wrapper").slimscroll({destroy:!0}),$(".sidebar-wrapper").attr("style","")),$("#wrapper").hasClass("fixed-sidebar")||resizeWrapper()}),$(".theme-selector").click(function(){$("body").attr("data-theme",$(this).attr("theme"))}),$(".background-selector").click(function(){$("#sidebar-wrapper").attr("data-background",$(this).attr("background"))}),$(".sidebar-minimalizer").click(function(){var a=$("#onoff-collapse-sidebar");a.toggleClass("checked")})});