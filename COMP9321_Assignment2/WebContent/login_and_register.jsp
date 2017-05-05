<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script>
	$(document)
			.ready(
					function() {
						var flag = false;
							$('#register-submit').click(
									function() {
										var user = $('#reg_username').val();
										var nickName = $('#nickName').val();
										var fname = $('#fname').val();
										var lname = $('#lname').val();
										var email = $('#email').val();
										var yob = $('#yob').val();
										var full_address = $('#full_address').val();
										var type = $('#type').val();
										var CC = $('#CC').val();
										var pwd = $('#reg_password').val();
										if (user.length == 0
												|| nickName.length == 0
												|| fname.length == 0
												|| lname.length == 0
												|| email.length == 0
												|| yob.length == 0
												|| full_address.length == 0
												|| CC.length == 0)
											flag = true;
										else
											flag = false;
										
										if (!flag) {
											$.ajax({
												type : "POST",
												url : "home",
												data : {
													"username" : user,
													"nickName" : nickName,
													"full_address" : full_address,
													"fname" : fname,
													"lname" : lname,
													"email" : email,
													"yob" : yob,
													"type" : type,
													"CC" : CC,
													"password" : pwd,
													"action" : "signup"
												},
												success : function(data) {
																if (data == 'True') {
																	$(location).attr('href','');
																	} else {
																		alert('Register Fail. Please register again!');
																		}
																}
												});
											} else {
												$('#validFrm').html('Either one of the field is empty or Entered Data is not Valid').css('color','red');
												}
										});
							$('#reg_password, #confirm-password').on('keyup', 
									function() {
										var pass = $('#reg_password').val();
										var conf = $('#confirm-password').val();
										if (pass == conf) {
											flag = false;
											$('#divCheckPasswordMatch').html('Passwords Matching').css('color','green');
											} else {
												flag = true;
												$('#divCheckPasswordMatch').html('Passwords Not Matching').css('color','red');
												}
										});
							$('#CC').on('keyup',
									function() {
										var CC = $('#CC').val();
										console.log(CC);
										if (CC.length < 16) {
											flag = true;
											$('#validCC').html('Credit Card Number Invalid').css('color','red');
											} else {
												$('#validCC').html('Credit Card Number Valid').css('color','green');
												flag = false;
												}
										});
							});
	</script>
			<div class="alert alert-warning alert-dismissable">
				<button type="button" class="close" data-dismiss="alert" aria-hidden="true">
					X
				</button>
				<h4>
					Alert!
				</h4> <strong>Warning!</strong> Best check your self, you're not looking too good. <a href="#" class="alert-link">alert link</a>
			</div>
			<div class="tabbable" id="login_and_register">
				<ul class="nav nav-tabs">
					<li class="active">
						<a href="#panel-413783" data-toggle="tab">Login</a>
					</li>
					<li>
						<a href="#panel-241973" data-toggle="tab">Register</a>
					</li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="panel-413783">
					<p>
						<form class="form-horizontal" role="form" action="routerServlet" method="post">
							<input type="hidden" name="action" value="login"/>
							<div class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">
									UserName
								</label>
								<div class="col-sm-10">
								<input type="text" class="form-control" name="user" id="user" 
																	placeholder="Username" value="">
								</div>
							</div>
							
							<div class="form-group">
								<label for="inputPassword3" class="col-sm-2 control-label">
									Password
								</label>
								<div class="col-sm-10">
									<input type="password" class="form-control" name="password" id="password"
																						placeholder="Password">
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<button type="submit" class="btn btn-default">
										Login in
									</button>
								</div>
							</div>
						</form>
		
					</div>
					<div class="tab-pane" id="panel-241973">
						<p>
						<form class="form-horizontal" role="form" role="form" action="routerServlet" method="post">
							<div class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">
									Username
								</label>
								<div class="col-sm-10">
								<input type="text" class="form-control" id="Username">
								</div>
							</div>
							<div class="form-group">
								<label for="inputPassword3" class="col-sm-2 control-label">
									Password
								</label>
								<div class="col-sm-10">
									<input type="password" class="form-control" id="Password">
								</div>
							</div>
							<div class="form-group">
								<label for="inputPassword3" class="col-sm-2 control-label">
									Confirm Password
								</label>
								<div class="col-sm-10">
									<input type="password" name="confirm-password"
											id="confirm-password" class="form-control" > 
											<!-- <span id="divCheckPasswordMatch"></span><br> 
											<span id="validCC"></span><br> 
											<span id="validFrm"></span> -->
								</div>
							</div>
							<div class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">
									Nick Name
								</label>
								<div class="col-sm-10">
								<input type="text" class="form-control" id="Nickname">
								</div>
							</div>
							<div class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">
									First Name
								</label>
								<div class="col-sm-10">
								<input type="text" class="form-control" id="First Name">
								</div>
							</div>
							<div class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">
									Last Name
								</label>
								<div class="col-sm-10">
								<input type="text" class="form-control" id="Last Name">
								</div>
							</div>
							<div class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">
									Email
								</label>
								<div class="col-sm-10">
								<input type="email" class="form-control" id="inputEmail3">
								</div>
							</div>
							<div class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">
									Address
								</label>
								<div class="col-sm-10">
								<input type="text" class="form-control" id="Address">
								</div>
							</div>
							<div class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">
									Year of Birth
								</label>
								<div class="col-sm-10">
								<input type="number" class="form-control" id="Address">
								</div>
							</div>
							<div class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">
									Register type
								</label>
								<div class="col-sm-10">
								<select name="type" id="type" class="form-control">
									<option value="1">Register as Customer</option>
									<option value="2">Register as Seller</option>
								</select>
								</div>
							</div>
							
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<div class="checkbox">
										<label>
											<input type="checkbox"> Remember me
										</label>
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<button type="submit" class="btn btn-default">
										Sign in
									</button>
								</div>
							</div>
						</form>	
					</div>
				</div>
			</div>