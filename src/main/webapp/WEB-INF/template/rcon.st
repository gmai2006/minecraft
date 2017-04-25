<div class="container">
	<ol class="breadcrumb">
		<li><a href="$context$index.xhtml">Home</a></li>
		<li class="active">RCON</li>
	</ol>

	<div class="row">
		<div class="col-sm-3">$menu$</div>

		<div class="col-sm-9">
	$if(action)$
	<div class="alert $cssId$" role="alert">$message$</div>
	$endif$
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3>RCON Commands</h3>
				</div>
				<div class="panel-body">
					<form action="rcon.xhtml" class="form form-inline" role="form"
						method="post">
						<input type="hidden" name="id" value="$server.id$" />


						<div class="form-group col-xs-12 col-sm-8">
							<label for="InputFieldC" class="col-xs-4">Command</label>
							<div class="col-xs-8">
								<input type="text" class="form-control" id="command"
									name="command" placeholder="Enter command here..">
							</div>
						</div>

						<div class="form-group col-xs-12 col-sm-4">
							<button type="submit"
								class="btn btn-default col-xs-8 col-xs-offset-4">Execute
							</button>
						</div>
					</form>
					$if (response)$
					<table class="table table-condensed">
						$response : { r |
						<tr>
							<td>$r$</td>
						</tr>
						}$
					</table>
					$endif$
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3>Available Commands</h3>
				</div>
				<div class="panel-body">
					<table class="table table-condensed table-bordered">
						<thead>
							<tr>
								<th>Command name</th>
								<th>Command Description</th>

							</tr>
						</thead>

						<tr>
							<td>tell [player] [message]</td>
							<td>Allows the user to privately message another player.</td>
						</tr>
						<tr>
							<td>kill</td>
							<td>Allows the player to kill themselves, returning to spawn
							</td>
						</tr>
						<tr>
							<td>me</td>
							<td>Says a message from the player's point of view: *player1
								is building a castle!</td>
						</tr>
						<tr>
							<td>help</td>
							<td>Shows a list of server commands in the console or
								in-game.</td>
						</tr>
						<tr>
							<td>kick [player]</td>
							<td>Removes a player from the server.</td>
						</tr>
						<tr>
							<td>ban [player]</td>
							<td>Bans a player from the server.</td>
						</tr>
						<tr>
							<td>banlist</td>
							<td>Shows the banned players.</td>
						</tr>
						<tr>
							<td>pardon [player]</td>
							<td>Pardons a banned player so that they can connect again.</td>
						</tr>
						<tr>
							<td>ban-ip</td>
							<td>Bans an IP address from the server.</td>
						</tr>
						<tr>
							<td>pardon-ip</td>
							<td>Pardons a banned IP address so that it can be used to
								connect again.</td>
						</tr>
						<tr>
							<td>op [player]</td>
							<td>Turns a player into a server operator.</td>
						</tr>
						<tr>
							<td>deop [player]</td>
							<td>Removes server operator status from a player.</td>
						</tr>
						<tr>
							<td>tp [player1] [player2] tp [player1] [x] [y] [z]</td>
							<td>Moves player1 to the same location as player2. Moves
								player1 to the exact coordinates.</td>
						</tr>
						<tr>
							<td>give [player] [data value] [amount] [damage]</td>
							<td>Gives player blocks/items blockID data-value. amount is
								is how many of the block/item to give (Default: 1). damage is
								the damage value of the block/item (Default: 0).</td>
						</tr>
						<tr>
							<td>stop</td>
							<td>Gracefully stops the server (i.e. allows the server to
								save the worlds and lets all the plugins shut down properly).</td>
						</tr>
						<tr>
							<td>save-all</td>
							<td>Forces a server-wide level save of the terrain.</td>
						</tr>
						<tr>
							<td>save-off</td>
							<td>Disables automatic terrain saving (useful for backup
								scripts).</td>
						</tr>
						<tr>
							<td>save-on</td>
							<td>Re-enables automatic terrain saving</td>
						</tr>
						<tr>
							<td>list</td>
							<td>Lists all currently connected players.</td>
						</tr>
						<tr>
							<td>say [message]</td>
							<td>Broadcasts a message to all players as the server.</td>
						</tr>
						<tr>
							<td>whitelist [on/off]</td>
							<td>Enable or disable whitelisting (i.e. only listed players
								may join).</td>
						</tr>
						<tr>
							<td>whitelist [add/remove] [player]</td>
							<td>Add or remove a player from the whitelist</td>
						</tr>
						<tr>
							<td>whitelist list</td>
							<td>Lists all currently whitelisted players.</td>
						</tr>
						<tr>
							<td>whitelist reload</td>
							<td>Reload the whitelist from file. Useful if you edited the
								file manually</td>
						</tr>
						<tr>
							<td>gamemode [0/1/2] [player]</td>
							<td>Change the game mode of a player. 0 = Survival mode, 1 =
								Creative mode, 2 = Adventure mode.</td>
						</tr>
						<tr>
							<td>xp [player] [amount]</td>
							<td>Gives the specified player a certain amount of
								experience.</td>
						</tr>
						<tr>
							<td>toggledownfall</td>
							<td>Turn on or off rain/snow in the current world.</td>
						</tr>
						<tr>
							<td>defaultgamemode [0/1/2/3]</td>
							<td>Change the gamemode of new players joining the server.</td>
						</tr>
						<tr>
							<td>enchant [user] [enchant number or ID] [level] [force]</td>
							<td>Enchants the item in the user's hand.</td>
						</tr>
						<tr>
							<td>weather [clear/cloudy/winter/rain]</td>
							<td>Changes the weather in-game.</td>
						</tr>
						<tr>
							<td>clear [player]</td>
							<td>Clears a user's inventory.</td>
						</tr>
						<tr>
							<td>gamerule</td>
							<td>Prints out the current game rules being applied to the
								server.</td>
						</tr>
					</table>
					<hr />
					<a href="http://wiki.bukkit.org/CraftBukkit_Commands"
						target="_blank">More commands can be found here</a>
				</div>
			</div>
		</div>
	</div>
</div>