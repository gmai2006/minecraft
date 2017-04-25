<div class="container">
<ol class="breadcrumb">
		<li><a href="$context$index.xhtml">Home</a></li>
		<li class="active">Create New Server</li>
	</ol>
	<div class="row">
		<div class="col-sm-3">$menu$</div>

	<div class="col-sm-9">
<div class="panel panel-default">
	<div class="panel-heading"
		style="font-family: 'Source Sans Pro'; font-size: 18px;">
		<h3>Create New Minecraft / Spigot / Pixelmon / FTP Server</h3>
	</div>
	<div class="panel-body">
$if(action)$
	<div class="alert $cssId$" role="alert">$message$</div>
$endif$
		<form action="create-server.xhtml" class="form-horizontal"
			role="form" method="post">
			<input type="hidden" name="action" value="yes" /> 
			<fieldset>
				<div class="form-group">
					<label class="control-label col-xs-3" for="InputEmail">
						Server Type</label>
					<div class="col-xs-9">
						<select name="serverType" class="form-control">
							<option value="">Select Server Type....</option>
							$types: { s |
							<option value="$s.name$">$s.name$</option>
							}$
							
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-xs-3" for="InputEmail">
						Memory</label>
					<div class="col-xs-9">
						<select name="memory" class="form-control">
							<option value="">Select Memory....</option>
							<option value="512M">512MB</option>
							<option value="1024M">1024MB</option>
							<option value="2048M">2048MB</option>
							<option value="4196M">4196MB</option>
						</select>
					</div>
				</div>

				<p>
					<label><br /></label> <input type="submit" class="btn btn-primary pull-right" value="Create Minecraft Server" />
				</p>
			</fieldset>
		</form>
	</div>
	</div>
	</div>
</div>
</div>