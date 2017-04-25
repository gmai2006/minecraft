<div class="container">
	<div class="row">
		<div class="col-sm-3">$menu$</div>
		<div class="col-sm-9">
			<div class="panel panel-default">
				<div class="panel-heading"
					style="font-family: 'Source Sans Pro'; font-size: 18px;">
					<h3>Server Status</h3>
				</div>
				<div class="panel-body">
					$if(action)$
					<div class="alert $cssId$" role="alert">$message$</div>
					$endif$
					<table class="table table-condensed">
						<tr>
							<td>Operating System</td>
							<td>$os$</td>
						</tr>
						<tr>
							<td>Time on system</td>
							<td>$uptime$</td>
						</tr>
						<tr>
							<td>Memory Usage</td>
							<td><pre>$mem$</pre></td>
						</tr>
						<tr>
							<td>CPU load average</td>
							<td>$cpuload$</td>
						</tr>
					</table>
				</div>
			</div>
			$if (servers)$
			<div class="panel panel-default">
				<div class="panel-heading"
					style="font-family: 'Source Sans Pro'; font-size: 18px;">
					<h3>Minecraft Servers Status</h3>
				</div>
				<div class="panel-body">
					<table class="table table-condensed">
						$servers: { n |
						<tr>
							<td>$n.id$</td>
							<td>$n.descr$</td>
							<td><a href="index.xhtml?action=delete&id=$n.id$"><span
									class="fa fa-trash-o"></span> [Delete $n.id$]</a></td>
						</tr>
						}$
					</table>
				</div>
			</div>
			$endif$ 
		</div>
	</div>
</div>
