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
		<h3>Change Configuration</h3>
	</div>
	<div class="panel-body">
		<form action="update-server-configuration.xhtml" class="form-horizontal"
			role="form" method="post">
			<input type="hidden" name="action" value="update" /> 
			<input type="hidden" name="id" value="$server.id$" /> 
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
				
				
				<div class="form-group">
					<label class="control-label col-xs-3" for="InputEmail">
						Game Mode</label>
					<div class="col-xs-9">
						<select name="gameMode" class="form-control">
							<option value="$server.gameMode$" selected>$server.gameModeDescr$</option>
							<option value="0">Survival</option>
							<option value="1">Creative</option>
							<option value="2">Adventure</option>
							<option value="3">Spectator</option>
						</select>
					</div>
				</div>
				
				<div class="form-group">
					<label class="control-label col-xs-3" for="InputEmail">
						Difficulty</label>
					<div class="col-xs-9">
						<select name="difficulty" class="form-control">
							<option value="$server.difficulty$" selected>$server.difficultyDescr$</option>
							<option value="0">Peaceful</option>
							<option value="1">Easy</option>
							<option value="2">Normal</option>
							<option value="3">Hard</option>
						</select>
					</div>
				</div>
				
				<div class="form-group">
					<label class="control-label col-xs-3" for="InputEmail">
						Map Type</label>
					<div class="col-xs-9">
						<select name="levelType" class="form-control">
							<option value="$server.levelType$" selected>$server.levelType$</option>
							<option value="DEFAULT">DEFAULT</option>
							<option value="FLAT">FLAT</option>
							<option value="LARGEBIOMES">LARGEBIOMES</option>
							<option value="AMPLIFIED">AMPLIFIED</option>
						</select>
					</div>
				</div>
				
				<div class="form-group">
					<label class="control-label col-xs-3" for="InputEmail">
						Maximum # of Players</label>
					<div class="col-xs-9">
						<input name="maxPlayers" class="form-control" value="$server.maxPlayers$">
					</div>
				</div>
				
				<div class="form-group">
					<label class="control-label col-xs-3" for="InputEmail">
						White List</label>
					<div class="col-xs-9">
						<select name="whitelist" class="form-control">
							<option value="$server.whitelist$" selected>$server.whitelist$</option>
							<option value="true">true</option>
							<option value="false">false</option>
						</select>
					</div>
				</div>
				
				<div class="form-group">
					<label class="control-label col-xs-3" for="InputEmail">
						Spawn Monsters</label>
					<div class="col-xs-9">
						<select name="spawnMonsters" class="form-control">
							<option value="$server.spawnMonsters$" selected>$server.spawnMonsters$</option>
							<option value="true">true</option>
							<option value="false">false</option>
						</select>
					</div>
				</div>
				
				<div class="form-group">
					<label class="control-label col-xs-3" for="InputEmail">
						Spawn Animals</label>
					<div class="col-xs-9">
						<select name="spawnAnimal" class="form-control">
							<option value="$server.spawnAnimal$" selected>$server.spawnAnimal$</option>
							<option value="true">true</option>
							<option value="false">false</option>
						</select>
					</div>
				</div>
				
				<div class="form-group">
					<label class="control-label col-xs-3" for="InputEmail">
						View Distance</label>
					<div class="col-xs-9">
						<input name="viewDistance" class="form-control" value="$server.viewDistance$" />
					</div>
				</div>
				
				<div class="form-group">
					<label class="control-label col-xs-3" for="InputEmail">
						Resource Pack</label>
					<div class="col-xs-9">
						<input type="text" name="resourcePack" class="form-control" value="$server.resourcePack$" />
						<span>To clear the resource please type <b>clear</b> (no single quote)</span>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-xs-3" for="InputEmail">
						Command Block</label>
					<div class="col-xs-9">
						<select name="commandBlock" class="form-control">
							<option value="$server.commandBlock$" selected>$server.commandBlock$</option>
							<option value="true">true</option>
							<option value="false">false</option>
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
	
	
	</div>
	</div>
	</div>