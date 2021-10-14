/*控制導覽列隱藏or顯示or上色or透明*/
var doc = $(document);
var win = $(window);
var nav = $(".navbar");
var navFont = $(".navbar-light .navbar-nav .nav-link");
var navLogin = $("#Login");
var preScroll = window.pageYOffset;

doc.ready(function () {
    win.on("scroll", function () {
        var curScroll = window.pageYOffset;
        if (preScroll > curScroll) {
            nav.removeClass("navHide").addClass("navColor");
            navFont.addClass("dark");
            navLogin.removeClass("btn-outline-light").addClass("btn-outline-dark");
        } else {
            nav.addClass("navHide");
        }
        if (curScroll === 0) {
            nav.removeClass("navHide navColor");
            navFont.removeClass("dark");
            navLogin.removeClass("btn-outline-dark").addClass("btn-outline-light");
        }
        preScroll = curScroll;
    })
});