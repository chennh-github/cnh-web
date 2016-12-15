;
(function () {


    function menuProxy() {
        var $menu = $('.side-nav a[href]'),
            mainframe = document.getElementById('mainframe'),
            $loading = $('.frame-loading');

        function beforeLoad() {
            $loading.fadeIn(300);
        }

        function afterLoad() {
            setTimeout(function () {
                $loading.fadeOut(300);
            }, 300);
        }

        $(mainframe).on('load', afterLoad);

        $menu.each(function () {
            var $this = $(this);
            $this.data('menu-href', $this.attr('href'))
                .attr('href', 'javascript: void(0);')
                .on('click.menu', function () {
                    beforeLoad();
                    mainframe.src = $this.data('menu-href');
                });
        });

        // 默认打开第一个菜单
        setTimeout(function () {
            $menu.first().click();
        }, 1000);
    }


    $(function () {
        menuProxy();
    });

}());
