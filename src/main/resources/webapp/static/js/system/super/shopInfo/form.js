;(function ($, viewModel) {

	"use strict";

	$.extend(true, viewModel, {
		model: {
			entity: {
                id: id, 
                cityId: "", 
                areaId: "", 
                shopTypeId: "", 
                shopChildTypeId: "", 
                bigLogo: "", 
                logo: "", 
                shopName: "", 
                mobile: "", 
                introduction: "", 
                address: "", 
                longitude: "", 
                latitude: "", 
                certificatePhoto: "", 
                isIntegrity: "", 
                isRecommend: "", 
                isAuthentication: "", 
                isMuslim: "", 
                isPush: "", 
                status: "", 
                createTime: "", 
                browseCount: "", 
                consumptionPerPerson: "", 
                content: "", 
                oldShopType: "", 
                money: "", 
                lowestFee: "", 
                couponFee: "", 
                couponDesc: "", 
                isUserFind: "", 
                alipayAccount: "", 
                source: ""
			}
		}
	});


	$(function () {
		viewModel.initViewModel();
	});

} (jQuery, (window.viewModel || (window.viewModel = {}))));