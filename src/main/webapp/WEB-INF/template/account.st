<div class="divide10"></div>
<div class="container">
	<ol class="breadcrumb">
		<li><a href="index.xhtml">Home</a></li>
		<li class="active">Upload</li>
	</ol>
	$if(cssId)$
	<div class="alert $cssId$" role="alert">$message$</div>
	$endif$
	
	<div class="panel panel-default">
	<div class="panel-heading" style="font-family: 'Source Sans Pro';">
		<h3>File Upload</h3>
	</div>
	<div class="panel-body text_3 color_0">
	
	<div class="row">
		<div class="col-md-7">
			<form id="uploadform" action="$context$upload"
				enctype="multipart/form-data" method="POST"
				onsubmit="postForm(this);">
				<input type="hidden" name="id" value="$server.id$" /> 
				<input name="userfile" id="input-id" type="file" class="file"
					data-preview-file-type="text"><span style="float: right;">
			</form>
		</div>
	</div>
	<div class="divide10"></div>
		<!-- end of col 8 -->
		<table class="table table-condensed table-bordered">
		<thead>
			<tr>
				<th>Name</th>
				<th>Date</th>
				<th>Size</th>
			</tr>
		</thead>

		<tbody>
			
			$files:{ file | 
			<tr>
				<td><a href="$context$download?fileName=$file.name$"
					target="_blank"><img
						src="$context$images/16px/$file.imageName$" alt="" width="16"
						height="16" /> $file.name$</a></td>
				<td>$file.date$</td>
				<td>$file.size$</td>
				
			</tr>
			 }$
		</tbody>
	</table>	
	</div>
</div>
</div>