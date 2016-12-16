;
( function( $, win ){
/**
 * zTreeProxy - zTree包装对象，基于zTree3.5
 *
 * 约定了zTree的基本配置项和处理函数
 */
function zTreeProxy( $obj, setting ){
	return this.init( $obj, setting );
}
//win.zTreeProxy = zTreeProxy;
$.extend( true, zTreeProxy, {

	treeArray: null,						// 树对象数组，存储所有的树对象

	defaults: {
		css : {
			treeContainer: {				// 树菜单容器的样式
				"width"		: 200,
				"height"	: "100%",
				"float"		: "left",
				"overflow"	: "auto",
				"min-height": 510,
				"position"	: "relative"
			},

			tree: {							// 树菜单样式，通常直接给对象加[tree zTree]样式即可
				"color"			: "#333",
				"padding"		: "5px",
				"font-family"	: "Verdana, Arial, Helvetica, AppleGothic, sans-serif",
				"font-size"		: "12px",
				"margin"		: "0px"
			},

			treeError: {					// 错误提示的样式
				"color"		: "red",
				"display"	: "none",
				"fontSize"	: 12
			},

			splitCon: {						// 分割线的样式
				"width"			: 6,
				"height"		: "100%",
				"border"		: "1px solid RGB(14, 149, 208)",
				"float"			: "left",
				"cursor"		: "pointer",
				"background"	: "RGB(186, 226, 241)",
				"position"		: "relative"
			},

			splitImg: {						// 分割线图片的样式
				"position"	: "relative",
				"width"		: 6
			}

		},

		time: {								// 统一的动画时间
			show			: 300,
			hide			: 300
		}
	},

	path: {									// 该对象下的所有路径在对象初始化时均会自动补全为绝对路径
		fullPath	: "",					// 项目上下文路径，对象初始化时会自动获取id=full_path的文本框的值
		zTree		: "jquery.ztree-2.6.min.js",	// zTree2.6的js文件路径，已放弃使用
		btnOpen		: "img/btn_open.gif",			// 分割线的开启状态图片
		btnClose	: "img/btn_close.gif",			// 分割线的关闭状态图片

		zTreeStyleHasCheck	: "zTreeStyle/zTreeStyleHasCheck.css",		// zTree3.5对应的外部样式表
		zTreeJs35		: ["js3.5/jquery.ztree.all.min.js"]		// zTree3.5的外部js路径
	},

	index: 0,		// 控制自增的类索引

	hasInit: false,	// 标志当前的类变量是否已经初始化

	specialName: {	// 标志性名称，用于对象存储或者jQuery选择器使用
		zTreeProxy		: "jQuery_zTreeProxy",
		zTreeContainer	: "zTreeContainer",
		zTree			: "zTree",
		zTreeError		: "zTreeError",
		splitCon		: "splitCon",
		splitImg		: "splitImg"
	},

	util: {
		/**
		 * 添加外部js文件，同步等待文件加载完成
		 * @param url - js文件路径
		 */
		addScript: function( url ){
			$.ajaxSetup( { async: false, cache: false, global: false } );
			if( $.isArray( url ) ){
				for( var i = 0, l = url.length; i < l; i++ ){
					$.getScript( url[i] );
				}
			} else if( typeof url === "string" ){
				$.getScript( url );
			}
	        $.ajaxSetup( { async: true, global: true } );
		},
		/**
		 * 添加外部css文件
		 * @param url - css文件路径
		 */
		addCssByLink: function( url ){
			var doc=document;
			var link=doc.createElement( "link" );
			link.setAttribute( "rel", "stylesheet" );
		    link.setAttribute( "type", "text/css" );
		    link.setAttribute( "href", url );

			var heads = doc.getElementsByTagName( "head" );
			if( heads.length )
				heads[0].insertBefore( link, heads[0].firstChild );
			else
				doc.documentElement.appendChild( link );
		},
		/**
		 * 创建标签名为tagName的jQuery对象
		 * @param tagName - 标签名
		 */
		dom: function( tagName ){
			var singleTag = ["img", "hr", "br", "input"];
			tagName = tagName || "div";
			if( ("|" + singleTag.join("|") + "|").indexOf( "|" + tagName + "|" ) >= 0 ){
				return $( "<" + tagName + " />" );
			} else {
				return $( "<" + tagName + "></" + tagName + ">" );
			}
		},
		/**
		 * 获取$treeContainer的子元素中属性值为specialName的jQuery对象
		 * @param $treeContainer - 任意的容器jQuery对象
		 * @param specialName - 获取内部jQuery对象的属性值
		 */
		get: function( $treeContainer, specialName ){
			return $treeContainer.find( "[" + zTreeProxy.specialName.zTreeProxy + "='" + specialName + "']" );
		},
		/**
		 * 创建一个树菜单jQuery对象
		 * @param zTreeProxySetting - setting.zTreeProxy
		 */
		createContainer: function( zTreeProxySetting ){
			var index = zTreeProxy.index++,
				$treeContainer 	= zTreeProxy.util.dom( "div" 	).css( zTreeProxySetting.css.treeContainer 	).attr( zTreeProxy.specialName.zTreeProxy, zTreeProxy.specialName.zTreeContainer ).attr( "id", zTreeProxy.specialName.zTreeContainer + "_" + index 	),
				$splitCon 		= zTreeProxy.util.dom( "div" 	).css( zTreeProxySetting.css.splitCon	 	).attr( zTreeProxy.specialName.zTreeProxy, zTreeProxy.specialName.splitCon 		 ).attr( "id", zTreeProxy.specialName.splitCon + "_" + index 		),
				$ul				= zTreeProxy.util.dom( "ul" 	)/*.css( zTreeProxySetting.css.tree 	  )*/.attr( zTreeProxy.specialName.zTreeProxy, zTreeProxy.specialName.zTree 		 ).attr( "id", zTreeProxy.specialName.zTree + "_" + index 			),
				$font			= zTreeProxy.util.dom( "font" 	).css( zTreeProxySetting.css.treeError 		).attr( zTreeProxy.specialName.zTreeProxy, zTreeProxy.specialName.zTreeError 	 ).attr( "id", zTreeProxy.specialName.zTreeError + "_" + index 		),
				$splitImg		= zTreeProxy.util.dom( "img" 	).css( zTreeProxySetting.css.splitImg 		).attr( zTreeProxy.specialName.zTreeProxy, zTreeProxy.specialName.splitImg 		 ).attr( "id", zTreeProxy.specialName.splitImg + "_" + index 		),
				$div 			= zTreeProxy.util.dom( "div" 	).css( "position", "relative" );
			$treeContainer.append( $ul.addClass( "tree" ).addClass( "zTree" ).addClass( "ztree" ) )
			  			  .append( $font.html( "获取数据失败" ) );
			$splitCon.append( $splitImg.attr( "width", zTreeProxySetting.css.splitImg.width ) ).css( "height", $( win ).height() );
			return 	zTreeProxySetting.splitPosition === "right" ?
					$div.append( $treeContainer ).append( $splitCon ) :
					$div.append( $splitCon ).append( $treeContainer ) ;
		},
		/**
		 * debug输出
		 * @param msg - 输出值
		 */
		debug: function( msg ){
			alert( msg );
			return zTreeProxy;
		}
	},
	/**
	 * 全局对象初始化执行函数，所有的zTreeProxy全局对象和预操作在此操作
	 * @param isDebug - 是否开启debug模式
	 */
	initPrev: function( isDebug ){
		var fullPath = $( "#full_path" ).val() || window['fullPath'] || "",
			relativePath = (function () {
				var scripts = document.scripts;
				var src = scripts[scripts.length - 1].src;
				var minifyIndex = src.indexOf("/minify/");
				return minifyIndex > -1 && window.fullPath ? window.fullPath + "static/js/asserts/jquery-zTree/v3.5/js/" :
					src.substring( 0, scripts[scripts.length - 1].src.lastIndexOf( "/" ) + 1 );
			}());

		zTreeProxy.treeArray = [];
		$.extend( true, zTreeProxy.path, {
			fullPath	: fullPath,
			zTree		: relativePath + zTreeProxy.path.zTree,
			btnOpen		: relativePath + zTreeProxy.path.btnOpen,
			btnClose	: relativePath + zTreeProxy.path.btnClose,

			zTreeStyleHasCheck	: relativePath + zTreeProxy.path.zTreeStyleHasCheck,
			zTreeJs35	: (function () {
				var ret = [];
				$.each(zTreeProxy.path.zTreeJs35, function (i, path) {
					ret.push(relativePath + path);
				});
				return ret;
			}())
		} );

		if (!$.fn.zTree) {
			zTreeProxy.util.addCssByLink( zTreeProxy.path.zTreeStyleHasCheck );
			zTreeProxy.util.addScript( zTreeProxy.path.zTreeJs35 );
		}

		zTreeProxy.hasInit = true;
	}

} );

$.extend( true, zTreeProxy.prototype, {

	setting: {
		zTree: {								// zTree对象初始化的基本配置
			data : {
				keep		: {
					parent	: true,
					leaf	:true
				},
				simpleData 	: {
					enable	: true,
					idKey 	: "id",
					pIdKey 	: "pid",
					name 	: "name",
					rootPId	: -999999
				},
				key	: {
					name 	: "name",
					children: "nodes",
					title	: "name",
					checked	: "checked"
				}
			},
			check: {
				enable		: false,
				chkStyle	: "checkbox",
				chkboxType	: { "Y": "ps", "N": "ps" }
			}
		},
		zTreeProxy: {							// zTreeProxy对象的一些配置量
			splitPosition	: "right",			// 标志分割线在树菜单右边
			contentId		: "content",		// 隐藏/显示树菜单时需要改变宽度的dom对象的id
			dataUrl			: "",				// 获取树菜单数据的URL
			data			: {},				// 访问url带的参数
			css				: zTreeProxy.defaults.css,	// 树菜单的样式表，一般不需要配置此项
			isDebug			: false,			// 是否开启DEBUG模式
			showLevel		: 2,				// 显示级别，从0开始计数，也可以配置为任意对象，只需要自定义reload中的handleShowLevel函数自行处理
			responseLevel	: 2,				// 响应级别，从0开始计数，也可以配置为任意对象，只需要自定义reload中的handleResponseLevel函数自行处理
			removeAloneParents	: false,		// 移除不包含子节点的父节点，暂未提供该功能，建议使用reload中的dataFilter函数，按照数据特征过滤
			removeAloneChildren	: true,			// 移除没有挂载父节点的子节点，暂未提供该功能，建议使用reload中的dataFilter函数，按照数据特征过滤
			rootId			: 0,				// 树的根结点ID，可不需配置
			level			: null				// 默认的显示级别
		},
		reload: {
			dataFilter			: null,			// 数据过滤函数，如果配置了该函数在获取数据后会执行该函数对数据过滤而跳过其他过滤函数
			handleShowLevel		: null,			// 显示级别处理函数，控制树菜单的显示级别
			handleResponseLevel	: null			// 响应级别的处理函数，控制树菜单的响应级别
		},
		callback: {
			click: null,						// 树菜单的单击事件，需要达到响应级别才会执行该函数
			afterTreeBuild: null
		}
	},

	storage : {									// 对象内部存储数据使用
			$obj: null,							// 需要创建树菜单的jQuery对象
			tree: null							// zTree对象
		},
	/**
	 * 对象初始化执行的函数
	 * @param $obj jQuery对象
	 * @param setting 常用配置
	 * @returns {___anonymous7103_15659}
	 */
	init: function( $obj, setting ){
		if( zTreeProxy.hasInit === false )	zTreeProxy.initPrev();
		this.setSetting( $obj, setting )
			.initUI()
			.initEvt();
		return this;
	},
	/**
	 * 初始化setting
	 * @param $obj jQuery对象
	 * @param setting
	 * @returns {___anonymous7103_15774}
	 */
	setSetting: function( $obj, setting ){
		this.storage = $.extend( true, {}, this.storage, { $obj: $obj } );		// 此处必须改变storage对象的引用，避免对象共享
		this.setting = $.extend( true, {}, this.setting, setting );				// 此处如上
		this.setCallback();
		return this;
	},
	/**
	 * 更新setting
	 * @param setting
	 * @returns {___anonymous7328_17068}
	 */
	updateSetting: function( setting ){
		this.setting = $.extend( true, this.setting, setting );
		return this;
	},
	/**
	 * 设置树对象的回掉函数，所有开放的回掉函数都需要在此处设置
	 * @returns {___anonymous7103_15925}
	 */
	setCallback: function(){
		var self = this,
			callback = {
				onClick: function( event, treeId, treeNode ){
					self.handleZTreeClick.call( self, event, treeId, treeNode );
				}
			};
		this.setting.zTree.callback = $.extend(true, {}, this.setting.zTree.callback, callback);
		return this;
	},
	/**
	 * 初始化UI
	 * @returns {___anonymous7103_16007}
	 */
	initUI: function(){
		var $treeContainer = zTreeProxy.util.createContainer( this.setting.zTreeProxy );
		this.storage.$obj.append( $treeContainer.show() );
		return this;
	},
	/**
	 * 初始化事件绑定
	 * @returns {___anonymous7103_16066}
	 */
	initEvt: function(){
		var $treeContainer 	= this.storage.$obj,
			$splitCon 		= zTreeProxy.util.get( $treeContainer, zTreeProxy.specialName.splitCon ),
			$splitImg		= zTreeProxy.util.get( $treeContainer, zTreeProxy.specialName.splitImg );

		this.evt.splitImgEvt( $splitImg );
		this.evt.splitConEvt( $splitCon, $treeContainer,
							  this.setting.zTreeProxy.splitPosition, $( "#" + this.setting.zTreeProxy.contentId ) );
		return this;
	},
	/**
	 * 定义了树菜单内部的一些事件
	 */
	evt: {
		/**
		 * 分割线的图片随滚动条移动
		 * @param $splitImg
		 */
		splitImgEvt: function( $splitImg ){
			var $win = $( window );
			$win.scroll( function(){
				$splitImg.animate( {
					top : ( $win.height() -$splitImg.height() )/2 + $win.scrollTop()
				}, 1 );
			} ).scroll();
		},
		/**
		 * 分割线点击事件，隐藏/显示树菜单
		 * @param $splitCon
		 * @param $div
		 * @param position
		 * @param $content
		 */
		splitConEvt: function( $splitCon, $div, position, $content ){
			var $treeContainer = zTreeProxy.util.get( $div, zTreeProxy.specialName.zTreeContainer ),
				$splitImg = zTreeProxy.util.get( $div, zTreeProxy.specialName.splitImg ),
				btnOpen, btnClose, treeOuterWidth = $treeContainer.outerWidth() + $splitCon.outerWidth();
			// 分隔栏显示在右边
			if( position === "right" ){
				btnOpen 	= zTreeProxy.path.btnOpen;
				btnClose 	= zTreeProxy.path.btnClose;
				$treeContainer.css( "float", "left" );
				$splitCon.css( "float", "left" );
				$splitImg.attr( "src", btnClose );
			} else {
				btnOpen 	= zTreeProxy.path.btnClose;
				btnClose 	= zTreeProxy.path.btnOpen;
				$treeContainer.css( "float", "right" );
				$splitCon.css( "float", "right" );
				$splitImg.attr( "src", btnOpen );
			}
			$splitCon.off( "click" ).on( "click", function(){
				if( $treeContainer.is(":visible") ){
					$treeContainer.hide( zTreeProxy.defaults.time.hide, function(){
						$splitImg.attr( "src", btnOpen );
					} );
					if( $content && $content.length > 0 ){
						$content.animate( {
							width: "+=" + treeOuterWidth + "px"
						}, zTreeProxy.defaults.time.hide );
					}
				} else {
					$treeContainer.show( zTreeProxy.defaults.time.show, function(){
						$splitImg.attr( "src", btnClose );
					} );
					if( $content && $content.length > 0 ){
						$content.animate( {
							width: "-=" + treeOuterWidth + "px"
						}, zTreeProxy.defaults.time.show );
					}
				}
			} );
		}

	},
	/**
	 * 执行获取数据，获取成功后会自动执行创建树菜单
	 * @returns {___anonymous7103_16329}
	 */
	getData: function(){
		var self = this;
			var $treeContainer 	= zTreeProxy.util.get( self.storage.$obj, zTreeProxy.specialName.zTreeContainer ),
				$tree			= zTreeProxy.util.get( self.storage.$obj, zTreeProxy.specialName.zTree ),
				$treeError 		= zTreeProxy.util.get( self.storage.$obj, zTreeProxy.specialName.zTreeError ),
				treeContainerId = $treeContainer.attr( "id" ) || $treeContainer.attr( "id", zTreeProxy.specialName.zTreeProxy + "_" + zTreeProxy.index++ ).attr( "id" );
			$tree.empty();
			$treeError.hide();
			jQuery.support.cors = true;
			Ajax.ajax( {
						  url		: self.setting.zTreeProxy.dataUrl,
						  method	: "POST",
						  data		: self.setting.zTreeProxy.data,
						  dataType	: "json",
					  } ).success( function( resJson ){
						  var 	data	= resJson.entity;
						  if( $.isFunction( self.setting.reload.dataFilter ) ){
							  data = self.setting.reload.dataFilter.call( null, resJson );
						  }
						  if( $.isFunction( self.setting.reload.handleShowLevel ) ){
							  data = self.setting.reload.handleShowLevel.call( null, data, self.setting.zTreeProxy.showLevel );
						  } else {
							  data = self.handleShowLevel( data );
						  }
						  self.buildTree( data );
					  } ).error( function( err ){
						  $treeError.show();
					  } );
		return this;
	},
	/**
	 * 创建树菜单
	 * @param zNodes
	 * @returns {___anonymous7103_16405}
	 */
	buildTree: function( zNodes ){
		var zTree, $tree = zTreeProxy.util.get( this.storage.$obj, zTreeProxy.specialName.zTree );
		if( $.fn.zTree.init ){		// 高版本的zTree构造函数
			zTree = $.fn.zTree.init( $tree, this.setting.zTree, zNodes );
		} else {					// 低版本的zTree构造函数
			zTree = $tree.zTree(this.setting.zTree, zNodes);
		}
		this.storage.tree = zTree;
		if ($.isFunction (this.setting.callback.afterTreeBuild)) {
			this.setting.callback.afterTreeBuild(zTree);
		}
		return this;
	},
	// =================== 处理显示层 begin ========================
	/**
	 * 此处的数据必须是根据showLevel正序排序的，即根节点在前，子节点在后
	 */
	handleShowLevel: function( data, showLevel ){
		var showLevel;
		return data;
	},

	handleShowLevelByLevel: function( data, responseLevel ){
		var resArr = [];
		for( var i = 0, l = data.length; i < l; i++ ){
			if( parseInt( data[i][this.setting.zTreeProxy.level] ) <= this.setting.zTreeProxy.showLevel ){
				resArr.push( data[i] );
			}
		}
		delete data;
		return resArr;
	},

	handleShowLevelById: function( data ){
		return this;
	},

	handleAloneParents: function( data ){
		return this;
	},

	handleAloneChildren: function( data ){
		return this;
	},
	// =================== 处理显示层 end ========================

	handleZTreeClick: function( event, treeId, treeNode ){
		if( $.isFunction( this.setting.reload.handleResponseLevel ) ){
			this.setting.reload.handleResponseLevel.call( null, event, treeId, treeNode, this.setting.zTreeProxy.responseLevel, this.setting.callback.click );
		} else {
			this.handleResponseLevel( event, treeId, treeNode );
		}
		return this;
	},

	handleResponseLevel: function(){
		return this;
	},
	/**
	 * 树菜单自适应页面高度，提供外部调用
	 * @param $obj
	 * @returns {___anonymous7103_16483}
	 */
	autoSize: function( $obj ){
		$obj = $obj || $( window );
		zTreeProxy.util.get( this.storage.$obj, zTreeProxy.specialName.zTreeContainer ).css( "height", $obj.outerHeight() );
		zTreeProxy.util.get( this.storage.$obj, zTreeProxy.specialName.zTree		  ).css( "height", $obj.outerHeight() );
		zTreeProxy.util.get( this.storage.$obj, zTreeProxy.specialName.splitCon		  ).css( "height", $obj.outerHeight() );
		return this;
	},
	/**
	 * 执行生成树菜单，提供外部调用
	 * @returns {___anonymous7103_16574}
	 */
	exec: function(){
		this.getData();
		return this;
	}
} );

$.extend( $.fn, {
	zTreeProxy: function( setting ){
		var $this;
		return this.each( function(){
			$this = $( this );
			if( !$this.data( zTreeProxy.specialName.zTreeProxy ) ){
				$this.data( zTreeProxy.specialName.zTreeProxy, new zTreeProxy( $this, setting ) );
				if( setting.expand && typeof setting.expand == "string" ){
					$.extend( true, eval( setting.expand ), $.fn.zTreeProxy );
				}
			} else {
				$this.data( zTreeProxy.specialName.zTreeProxy ).exec();
			}
		} );
		return this;
	}
} );
/**
 * $.fn.zTreeProxy上所有的方法都将自动作为扩展对象的方法使用
 */
$.extend( true, $.fn.zTreeProxy, {
	/**
	 * 执行获取数据，并生成树
	 * @param $obj
	 */
	exec: function( $obj ){
		var zp = $obj && $obj.data( zTreeProxy.specialName.zTreeProxy ) || null;
		if( zp )	zp.exec();
	},
	/**
	 * 更新setting
	 * @param $obj
	 * @param setting
	 */
	updateSetting: function( $obj, setting ){
		var zp = $obj && $obj.data( zTreeProxy.specialName.zTreeProxy ) || null;
		if( zp )	zp.updateSetting( setting );
		return $.fn.zTreeProxy;
	},
	/**
	 * 获取该$obj对象的zTree对象
	 * @param $obj
	 * @returns
	 */
	getZTreeObj: function( $obj ){
		var zp = $obj && $obj.data( zTreeProxy.specialName.zTreeProxy ) || null;
		return zp && zp.storage.tree;
	},
	/**
	 * 获取zTreeProxy对象
	 * @param $obj
	 * @returns {Boolean}
	 */
	getZTreeProxy: function( $obj ){
		return $obj && $obj.data( zTreeProxy.specialName.zTreeProxy ) || null;
	},
	/**
	 * 调整树菜单的尺寸
	 * @param $obj
	 * @returns {Boolean}
	 */
	autoSize: function( $obj ){
		var zp = $obj && $obj.data( zTreeProxy.specialName.zTreeProxy ) || null;
		return zp && zp.autoSize( $obj );
	}
} );


} )( jQuery, window );


( function( $, win, undefined ){

var orgTreeDefaults = {
	showLevel : {
			0: "all",
			1: "00",
			2: 2,
			3: 4
	},
	showLevelDefault: 3,
	responseLevel : {
			all			: false,				// 配置了true，则优先判断，优先级1
			country		: false,				// 优先级3
			province	: false,				// 优先级3
			city		: true,					// 优先级3
			navi		: false,				// 配置了true，则优先判断，优先级2
			leaf		: false					// 配置了true，则优先判断，优先级2
	},
	code : "CODE"
};

var orgTree = {
		setting: {
			expand: "$.fn.orgTree",
			zTree: {
				data 				: {
					simpleData 	: {
						idKey 	: "ORGID",
						pIdKey 	: "ORGPID",
						rootPId	: 0
					},
					key	: {
						name 	: "CODEANDNAME",
						title	: "CODEANDNAME"
					}
				},
				check : {
					enable	: false
				}
			},
			zTreeProxy: {
				dataUrl			: "http://prodics-cas.icity.com:8904/icity-portal-console/orgTree/treeOrg?menuFormId=1",
				removeAloneParents	: false,
				removeAloneChildren	: false,
				showLevel		: 3,
				responseLevel	: {
					city	: true,
					province: false
				}
			},
			reload: {
				dataFilter		: function( data ){
					if( orgTree.setting.zTreeProxy.removeAloneParents === true ){
						data = removeAloneParents( data );
					}
					if( orgTree.setting.zTreeProxy.removeAloneChildren === true ){
						data = removeAloneChildren( data );
					}
					return data;
				},
				handleShowLevel	: function( data, showLevel ){
					var level = orgTreeDefaults.showLevel[showLevel] || orgTreeDefaults.showLevel[orgTreeDefaults.showLevelDefault],
					resArr = [], code;
					if( level === "all" )	return data;
					for( var i = 0, l = data.length; i < l; i++ ){
						code = data[i][orgTreeDefaults.code];
						if( code === level ){				// 处理显示级别为中国
							resArr.push( data[i] );
							break;							// 中国节点只有一个，得到后结束循环
						} else if( code.length <= level ){	// 处理其他显示级别
							resArr.push( data[i] );
						}
					}
					return resArr;
				},
				handleResponseLevel: function( event, treeId, treeNode, responseLevel, func ){
					var responseLevel = $.extend( true, {}, orgTreeDefaults.responseLevel, responseLevel );
						all 		= responseLevel["all"],
						country		= responseLevel["country"],
						province	= responseLevel["province"],
						city		= responseLevel["city"],
						navi		= responseLevel["navi"],
						leaf		= responseLevel["leaf"],
						code 		= treeNode[orgTreeDefaults.code],
						isParent	= treeNode.isParent, hasHandle = false;;
					if( $.isFunction( func ) ){
						if( all ){
							func.call( null, event, treeId, treeNode );
						} else {
							if( navi ){
								if( isParent === true ){
									hasHandle = true;
									func.call( null, event, treeId, treeNode );
								}
							}
							if( leaf ){
								if( isParent === false ){
									hasHandle = true;
									func.call( null, event, treeId, treeNode );
								}
							}
							if( hasHandle === false ){
								if( code === "00" ){
									if( country ){
										func.call( null, event, treeId, treeNode );
									}
								} else {
									if( code.length === 2 ){
										if( province ){
											func.call( null, event, treeId, treeNode );
										}
									} else if( code.length === 4 ){
										if( city ){
											func.call( null, event, treeId, treeNode );
										}
									}
								}
							}
						}
					}
				}
			}
		}
};


function removeAloneParents( data ){
	return data;
}
function removeAloneChildren( data ){
	return data;
}

$.fn.orgTree = function( setting ){
	setting = $.extend( true, {}, orgTree.setting, setting );
	return $( this ).zTreeProxy( setting );
}


} )( jQuery, window );
( function( $, win, undefined ){

	var menuTree = {
			setting: {
				expand: "$.fn.menuTree",
				zTree: {
					data 				: {
						simpleData 	: {
							idKey 	: "ID",
							pIdKey 	: "PARENT_ID",
							rootPId	: 0
						},
						key	: {
							name 	: "MENU_NAME",
							title	: "MENU_NAME"
						}
					},
					check : {
						enable	: false
					}
				},
				zTreeProxy: {
					dataUrl			: "http://prodics-cas.icity.com:8904/menu/getObjectByPage",
					data			: {
						ORG_CODE 	: "",
						pageNo 		: 1,
						STATUS 		: 101,
						pageSize 	: 1000000
					},
					removeAloneParents	: false,
					removeAloneChildren	: false,
					showLevel		: 0,
					responseLevel	: 0
				},
				reload: {
					dataFilter		: function( data ){
						if( menuTree.setting.zTreeProxy.removeAloneParents === true ){
							data = removeAloneParents( data );
						}
						if( menuTree.setting.zTreeProxy.removeAloneChildren === true ){
							data = removeAloneChildren( data );
						}
						data.push({
							ID 			: 0,
							PARENT_ID 	: -1,
							MENU_NAME 	: "栏目",
							isParent 	: true
						});
						return data;
					},
					handleShowLevel	: function( data, showLevel ){
						if ( showLevel === 0 ) 	return data;
						var resArr = [];
						for ( var i = 0, l = data.length; i < l; i++ ) {
							if ( data[i].DEPTH_FLAG && data[i].DEPTH_FLAG <= showLevel ) {
								resArr.push( data[i] );
							}
						}
						return resArr;
					},
					handleResponseLevel: function( event, treeId, treeNode, responseLevel, func ){
						if ( responseLevel === 0 ){
							func.call( null, event, treeId, treeNode );
						} else {
							if ( treeNode.DEPTH_FLAG && treeNode.DEPTH_FLAG <= responseLevel ) {
								func.call( null, event, treeId, treeNode );
							}
						}
					}
				}
			}
	};


	function removeAloneParents( data ){
		return data;
	}
	function removeAloneChildren( data ){
		return data;
	}

	$.fn.menuTree = function( setting ){
		setting = $.extend( true, {}, menuTree.setting, setting );
		return $( this ).zTreeProxy( setting );
	}


} )( jQuery, window );