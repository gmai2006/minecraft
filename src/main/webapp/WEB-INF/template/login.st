<div class="container">
	<ol class="breadcrumb">
		<li><a href="index.xhtml">Home</a></li>
		<li class="active">Sign in</li>
	</ol>
	<div class="row">
	$if(message)$
	<div class="alert $cssId$" role="alert">$message$</div>
	$endif$
		<div class="col-md-7">
			<h4>Sign In</h4>
			<hr />
			<form class="form-horizontal" role="form" name="loginform"
				action="login" method="post">
				<div class="form-group">
					<label class="control-label col-xs-3" for="InputEmail">Your
						UserID</label>
					<div class="col-xs-9">
						<input type="email" class="form-control" id="InputEmail"
							name="user" placeholder="Enter Email" required>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-xs-3" for="InputEmail">Password</label>
					<div class="col-xs-9">
						<input type="password" class="form-control" id="InputEmail"
							name="pass" placeholder="Enter Password" required> <span
							style="float: right;"><small><a
								href="request-password.xhtml">Forgot your password?</a></small></span>
					</div>
				</div>
				<input type="submit" name="submit" id="submit" value="Sign in"
					class="btn btn-primary pull-right">
			</form>
		</div>
		<!-- end of col 8 -->
	</div>
	<br />
</div>

<script>
	document.loginform.user.focus();
</script>
