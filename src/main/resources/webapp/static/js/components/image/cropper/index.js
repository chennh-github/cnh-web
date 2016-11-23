;
(function () {

    'use strict';

    var service = {
        crop: function (data) {
            return $.ajax({
                url: fullPath + 'component/image/cropper/crop',
                type: 'POST',
                dataType: 'json',
                contentType: 'application/json',
                data: JSON.stringify(data)
            });
        }
    };

    var viewModel = {
        model: {
            imgUrl: imgUrl,
            crop: {
                data: {
                    x: 0,
                    y: 0,
                    height: 0,
                    width: 0,
                    rotate: 0,
                    scaleX: 1,
                    scaleY: 1,
                    zoom: 1
                }
            }
        },
        initViewModel: function () {
            this.model = ko.mapping.fromJS(this.model);

            this.initImage(imgUrl);
            ko.applyBindings(this, $('#cropperContainer')[0]);
        },
        initImage: function (imgUrl) {
            var img = new Image(), that = this;
            img.onload = function () {
                $('#image').attr('src', imgUrl);
                that.initCropper();
                img.onload = null;
                img.onerror = null;
            }
            img.onerror = function () {
                master.sweet.warning({
                    title: '图片加载失败',
                    text: 'URL' + imgUrl
                });
            }
            img.src = imgUrl;
        },
        initCropper: function () {
            var options = {
                    aspectRatio: 16 / 9,
                    preview: '.img-preview',
                    crop: function (e) {
                        var zoom = that.model.crop.data.zoom.peek();
                        master.ko.fromJS(that.model.crop.data, {
                            x: Math.round(e.x*zoom),
                            y: Math.round(e.y*zoom),
                            height: Math.round(e.height*zoom),
                            width: Math.round(e.width*zoom),
                            rotate: e.rotate,
                            scaleX: e.scaleX,
                            scaleY: e.scaleY
                        }, false);
                    },
                    zoom: function (e) {
                        that.model.crop.data.zoom(e.ratio);
                    },
                    built: function (e) {
                        $image.cropper('zoom', 0);
                    }
                },
                that = this,
                $image = $('#image'),
                $download = $('#download');

            // Cropper
            $image.cropper(options);

            // Buttons
            if (!$.isFunction(document.createElement('canvas').getContext)) {
                $('button[data-method="getCroppedCanvas"]').prop('disabled', true);
            }

            if (typeof document.createElement('cropper').style.transition === 'undefined') {
                $('button[data-method="rotate"]').prop('disabled', true);
                $('button[data-method="scale"]').prop('disabled', true);
            }

            // Download
            if (typeof $download[0].download === 'undefined') {
                $download.addClass('disabled').text('浏览器不支持下载');
            }


            // Options
            $('.docs-toggles').on('change', 'input', function () {
                var $this = $(this);
                var name = $this.attr('name');
                var type = $this.prop('type');
                var cropBoxData;
                var canvasData;

                if (!$image.data('cropper')) {
                    return;
                }

                if (type === 'checkbox') {
                    options[name] = $this.prop('checked');
                    cropBoxData = $image.cropper('getCropBoxData');
                    canvasData = $image.cropper('getCanvasData');

                    options.built = function () {
                        $image.cropper('setCropBoxData', cropBoxData);
                        $image.cropper('setCanvasData', canvasData);
                    };
                } else if (type === 'radio') {
                    options[name] = $this.val();
                }

                $image.cropper('destroy').cropper(options);
            });


            // Methods
            $('.docs-buttons').on('click', '[data-method]', function () {
                var $this = $(this);
                var data = $this.data();
                var $target;
                var result;

                if ($this.prop('disabled') || $this.hasClass('disabled')) {
                    return;
                }

                if ($image.data('cropper') && data.method) {
                    data = $.extend({}, data); // Clone a new one

                    if (typeof data.target !== 'undefined') {
                        $target = $(data.target);

                        if (typeof data.option === 'undefined') {
                            try {
                                data.option = JSON.parse($target.val());
                            } catch (e) {
                                console.log(e.message);
                            }
                        }
                    }

                    if (data.method === 'rotate') {
                        $image.cropper('clear');
                    }

                    result = $image.cropper(data.method, data.option, data.secondoption);

                    if (data.method === 'rotate') {
                        $image.cropper('crop');
                    }

                    switch (data.method) {
                        case 'scaleX':
                        case 'scaleY':
                            $(this).data('option', -data.option);
                            break;

                        case 'getCroppedCanvas':
                            if (result) {

                                // Bootstrap's Modal
                                $('#getCroppedCanvasModal').modal().find('.modal-body').html(result);

                                if (!$download.hasClass('disabled')) {
                                    $download.attr('href', result.toDataURL('image/jpeg'));
                                }
                            }

                            break;
                    }

                    if ($.isPlainObject(result) && $target) {
                        try {
                            $target.val(JSON.stringify(result));
                        } catch (e) {
                            console.log(e.message);
                        }
                    }
                }
            });

            // Keyboard
            $(document.body).on('keydown', function (e) {

                if (!$image.data('cropper') || this.scrollTop > 300) {
                    return;
                }

                switch (e.which) {
                    case 37:
                        e.preventDefault();
                        $image.cropper('move', -1, 0);
                        break;

                    case 38:
                        e.preventDefault();
                        $image.cropper('move', 0, -1);
                        break;

                    case 39:
                        e.preventDefault();
                        $image.cropper('move', 1, 0);
                        break;

                    case 40:
                        e.preventDefault();
                        $image.cropper('move', 0, 1);
                        break;
                }

            });
        },
        submitCrop: function () {
            var data = ko.mapping.toJS(this.model.crop.data), that = this,
                imgUrl = this.model.imgUrl.peek();
            if (imgUrl.indexOf('.') < imgUrl.indexOf('?')) {
                imgUrl = imgUrl.replace(/\?[^.]*$/, '');
            }
            data.imgUrl = imgUrl;
            service.crop(data).then(function (data) {
                if ($.isFunction(parent['callback'])) {
                    parent['callback'](data);
                }
            });
        }
    };

    $(function () {
        // Tooltip
        $('[data-toggle="tooltip"]').tooltip();

        viewModel.initViewModel();
    });

}());