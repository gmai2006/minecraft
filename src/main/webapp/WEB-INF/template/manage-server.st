<div class="container">
<ol class="breadcrumb">
		<li><a href="$context$index.xhtml">Home</a></li>
		<li class="active">Manage Server</li>
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
		<h3>Minecraft Server - $server.id$</h3>
	</div>
	<div class="panel-body">

		<form action="manage-server.xhtml" class="form-horizontal"
			role="form" method="post">
			<input type="hidden" name="action" value="$server.action$" />
			<input type="hidden" name="id" value="$server.id$" />  
			<fieldset>
			<table class="table table-condensed">
					<tr>
						<td>$server.descr$</td>
						<td><input type="submit" class="btn btn-primary pull-right" value="$server.action$ Minecraft Server" /></td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
	</div>
	
	<div class="panel panel-default">
	<div class="panel-heading"
		style="font-family: 'Source Sans Pro'; font-size: 18px;">
		<h3>Current Configuration</h3>
	</div>
	<div class="panel-body">

			<table class="table table-condensed">
						<tr>
							<td>Server Uptime</td>
							<td>$uptime$ (min)</td>
						</tr>
						<tr>
							<td>World Map</td>
							<td>$server.mapName$</td>
						</tr>
						<tr>
							<td>Server Type</td>
							<td>$server.motd$</td>
						</tr>
						<tr>
							<td>Server Port</td>
							<td>$server.serverPort$</td>
						</tr>
						<tr>
							<td>White list</td>
							<td>$server.whitelist$</td>
						</tr>
						<tr>
							<td>Game Mode</td>
							<td>$server.gameModeDescr$</td>
						</tr>
						<tr>
							<td>Difficulty</td>
							<td>$server.difficultyDescr$</td>
						</tr>
					</table>
	</div>
	</div>

	<!-- 
	<div class="panel panel-default">
	<div class="panel-heading"
		style="font-family: 'Source Sans Pro'; font-size: 18px;">
		<h3>Add Users To White list and Ops </h3>
	</div>
	<div class="panel-body">
		<form action="manage-server.xhtml" class="form-horizontal"
			role="form" method="post">
			<input type="hidden" name="action" value="addUser" />
			<input type="hidden" name="id" value="$server.id$" />  
			<fieldset>
			<div class="form-group">
					<label class="control-label col-xs-3" for="InputEmail">
						User Id</label>
					<div class="col-xs-9">
						<input name="userId" class="form-control" value="">
					</div>
				</div>
				
				<div class="form-group">
					<label class="control-label col-xs-3" for="InputEmail">
						User Type</label>
					<div class="col-xs-9">
						<select name="userType" class="form-control">
							<option value="">normal</option>
							<option value="op">Operator</option>
						</select>
					</div>
				</div>
				<p>
					<label></label>
					<input type="submit" class="btn btn-primary pull-right" value="Add User" />
				</p>
			</fieldset>
		</form>
	</div>
	</div>
	 -->
	</div>
	</div>
	</div>