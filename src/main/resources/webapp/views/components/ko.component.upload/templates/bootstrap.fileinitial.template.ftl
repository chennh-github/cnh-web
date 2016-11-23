<div data-bind="if: setting.fileList().length">
	<div class="file-preview">
		<div class="file-drop-zone">
			<div class="file-preview-thumbnails"
				data-bind="foreach: { data: setting.fileList(), as: 'item' }">
				<div class="file-preview-frame">
					<img class="file-preview-image" style="width:auto; height:160px;" data-bind="attr: { src: $component.getImgUrl(item) }">
					<div class="file-thumbnail-footer">
						<div class="file-footer-caption" data-bind="attr: { src: item[$component.setting.property.title] }, text: item[$component.setting.property.title]"></div>
						<div class="file-actions">
							<div class="file-footer-buttons">
								<button type="button"
									data-bind="click: $component.removeImg.bind($component, $index)"
									class="kv-file-remove btn btn-xs btn-default" title="删除文件">
									<i class="glyphicon glyphicon-trash text-danger"></i>
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
