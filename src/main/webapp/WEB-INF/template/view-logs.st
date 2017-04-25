<div class="container">
	<ol class="breadcrumb">
		<li><a href="$context$index.xhtml">Home</a></li>
		<li class="active">View Logs</li>
	</ol>

	<div class="row">
		<div class="col-sm-3">$menu$</div>

		<div class="col-sm-9">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3>Server Log</h3>
				</div>
				<div class="panel-body">
					<table class="table table-condensed">
						$logs : { s |
						<tr>
							<td>$s$</td>
						</tr>
						}$
					</table>
				</div>
			</div>
		</div>
	</div>
</div>