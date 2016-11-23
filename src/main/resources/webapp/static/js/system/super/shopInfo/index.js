;(function ($, viewModel) {

	"use strict";

	$.extend(true, viewModel, {
		model: {
			search: {

			}
		}
	});


	$(function () {
		viewModel.initViewModel();
	});

}(jQuery, (window.viewModel || (window.viewModel = {}))));