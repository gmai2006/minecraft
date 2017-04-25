<div class="container">
<ol class="breadcrumb">
		<li><a href="$context$index.xhtml">Home</a></li>
		<li class="active">Update Server Configuration</li>
	</ol>
	<div class="row">
		<div class="col-sm-3">$menu$</div>

	<div class="col-sm-9">
	$if(action)$
	<div class="alert $cssId$" role="alert">$message$</div>
$endif$
	
<div class="panel panel-default">
	<div class="panel-heading"
		style="font-family: 'Source Sans Pro'; font-size: 18px;">
		<h3>Change Map</h3>
	</div>
	<div class="panel-body">
		<form action="manage-map.xhtml" class="form-horizontal"
			role="form" method="post">
			<input type="hidden" name="action" value="update" /> 
			<input type="hidden" name="id" value="$server.id$" /> 
			<input type="hidden" name="type" value="map" >
			<fieldset>
				<div class="form-group">
					<label class="control-label col-xs-3" for="InputEmail">
						Map</label>
					<div class="col-xs-9">
						<select name="mapName" class="form-control">
							<option value="$server.mapName$" selected>$server.mapName$</option>
							$maps: { m |
								<option value="$m.name$">$m.name$</option>
							}$
						</select>
					</div>
				</div>
				<p>
					<label></label> <input type="submit" class="btn btn-primary pull-right" value="Update" />
				</p>
			</fieldset>
		</form>
	</div>	
	</div>
<div class="panel panel-default">
	<div class="panel-heading" style="font-family: 'Source Sans Pro';">
		<h3>Upload New Map to the Server</h3><span><small>(zip file only)</small></span>
	</div>
	<div class="panel-body text_3 color_0">

	<form id="uploadform" action="$context$upload" enctype="multipart/form-data" method="POST"
		onsubmit="postForm(this);">
		<input type="hidden" name="id" value="$server.id$" > 
		<input name="userfile" id="input-1a" type="file" class="file" data-show-preview="false">
	</form>
	
	<div class="divide10"></div>
		<!-- end of col 8 -->
		<table class="table table-condensed table-bordered">
		<thead>
			<tr>
				<th>Map Name</th>
				<th>Size</th>
				<th>Time</th>
			</tr>
		</thead>

		<tbody>
			
			$maps :{ file | 
			<tr>
				<td>$file.name$</td>
				<td>$file.size$</td>
				<td>$file.date$</td>
			</tr>
			 }$
		</tbody>
	</table>	
	</div>
</div>
	
	<div class="panel panel-default">
	<div class="panel-heading" style="font-family: 'Source Sans Pro';">
		<h3>New Map Sites</h3>
	</div>
	<div class="panel-body text_3 color_0">
	<div class="divide10"></div>
	<p>To search for plugins you can follow one of the following web sites</p>
		<ul>
		<li><a href="https://www.minecraftmaps.com/" target="_blank">https://www.minecraftmaps.com/</a></li>
		
		</ul>
		<div class="alert alert-success" role="alert">Select a map then download the 
		zip file to your local computer before uploading to the server</div>
	</div>
</div>

	</div>
	</div>
	</div>