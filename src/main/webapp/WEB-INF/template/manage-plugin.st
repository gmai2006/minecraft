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
	<div class="panel-heading" style="font-family: 'Source Sans Pro';">
		<h3>Upload New Plugin to the Server</h3><span><small>(jar file only)</small></span>
	</div>
	<div class="panel-body text_3 color_0">

	<form id="uploadform" action="$context$upload" enctype="multipart/form-data" method="POST"
		onsubmit="postForm(this);">
		<input type="hidden" name="id" value="$server.id$" >
		<input type="hidden" name="type" value="plugins" > 
		<input name="userfile" id="input-1a" type="file" class="file" data-show-preview="false">
	</form>
	
	<div class="divide10"></div>
		<!-- end of col 8 -->
		<table class="table table-condensed table-bordered">
		<thead>
			<tr>
				<th>Plugin Name</th>
				<th>Size</th>
				<th>Time</th>
			</tr>
		</thead>

		<tbody>
			
			$plugins :{ file | 
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
		<h3>New Plugins Sites</h3>
	</div>
	<div class="panel-body text_3 color_0">
	<div class="divide10"></div>
	<p>To search for plugins you can follow one of the following web sites</p>
		<ul>
		<li><a href="http://dev.bukkit.org/bukkit-plugins/" target="_blank">http://dev.bukkit.org/bukkit-plugins/</a></li>
		<li><a href="http://www.curse.com/bukkit-plugins/minecraft/downloads" target="_blank">http://www.curse.com/bukkit-plugins/minecraft/downloads</a></li>
		<li><a href="https://www.spigotmc.org/resources/categories/bukkit.4/" target="_blank">https://www.spigotmc.org/resources/categories/bukkit.4/</a></li>
		</ul>
		<div class="alert alert-success" role="alert">Select a plugin then download the 
		jar file to your local computer before uploading to the server</div>
	</div>
</div>
	
	
	</div>
	</div>
	</div>