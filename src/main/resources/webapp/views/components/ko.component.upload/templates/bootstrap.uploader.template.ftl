<div>
	<!-- file inital -->
	<div class="form-group" style="display: none;"
		data-bind="
				visible: setting.fileList().length,
				component: {
					name: 'x-fileinitial',
					params: {
						fileList: setting.fileList
					}
				}">
	</div>

	<div class="form-group" data-bind="if: setting.fileList().length < setting.upload.maxFileCount">
		<div data-bind="if: setting.fileList().length">
			<div class="alert alert-warning">
				<strong>注意：</strong>删除图片前请确认保存了已上传的图片
				<i class="glyphicon glyphicon-chevron-up pull-right" style="cursor: pointer;" data-bind="click: toggleDetailTip"></i>
				<p class="help-block" style="display: none;">
					1、由于浏览器安全性限制，程序只能保留对文件的最后一次操作信息。多次选择的文件在组件刷新后只会保留最后一次所选择的文件。<br />
					2、原文件的删除会刷新上传组件，请注意处理好未保存的文件。
				</p>
			</div>		
		</div>
		<br />
		<div class="btn-groups">
			<button class="btn btn-primary" data-bind="click: saveUploadedFile">
				<i class="glyphicon glyphicon-floppy-saved"></i>&nbsp;<span>保存已上传图片</span>
			</button>
			<div class="alert alert-warning"  style="display: none; margin: 5px 0 0;">
				 <button type="button" class="close" data-bind="click: hideSaveWarning"><span aria-hidden="true">&times;</span></button>
				<p class="help-block"><strong>请上传已选择的图片后保存</strong></p>
			</div>
		</div>
	</div>

	<!--file-upload-->
	<div class="form-group" style="display: none;"
		data-bind="
				visible: setting.fileList().length <= setting.upload.maxFileCount,
				component: {
					name: 'x-fileupload',
					params: {
						fileList: setting.fileList,
						uploadedFileList: setting.uploadedFileList,
						data: setting.data
					}
				}">
	</div>
</div>