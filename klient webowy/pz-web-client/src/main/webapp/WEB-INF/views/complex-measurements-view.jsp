<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>

<head>
<title>Web client</title>

	<spring:url value="/resources/css/style.css" var="styleCss" />
	<link href="${styleCss}" rel="stylesheet" />

	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
		integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7"
		crossorigin="anonymous">
	<!-- Optional theme -->
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css"
		integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r"
		crossorigin="anonymous">

	<!-- Tables css -->
	<link rel="stylesheet" href="https://cdn.datatables.net/1.10.12/css/dataTables.bootstrap.min.css">

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
		integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
		crossorigin="anonymous"></script>

	<!-- Tables js -->
	<script
		src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
	<script
		src="https://cdn.datatables.net/1.10.12/js/dataTables.bootstrap.min.js"></script>

</head>



<body>

	<div id="sidebar-black" class="sidebar-nav">
		<nav id="navbar-black" class="navbar navbar-default">
			<div class="navbar-header">
				<span>Web client</span>
			</div>

			<div class="navbar-collapse collapse sidebar-navbar-collapse">
				<ul class="nav navbar-nav">

					<li><a href="/pz-web-client/resources"><i></i> Resources </a></li>
					<li><a href="/pz-web-client/metrics"><i></i> Metrics </a></li>
					<li><a href="/pz-web-client/measurements"><i></i> Simply
							measurements </a></li>
					<li><a href="/pz-web-client/complex-measurements"><i></i> Complex measurements </a></li>

					<li><a href="index.html"><i
							class="glyphicon glyphicon-user"></i> Profile </a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->

		</nav>
		<!--/.navbar -->
	</div>
	<!--/.sidebar-nav -->

	<main class="page-wrapper">
	<div class="col-md-10">

		<div class="panel panel-primary">
			<div class="panel-heading">
				<h4>Complex Measurements</h4>
			</div>
			<div class="panel-body">

				<h4>Latest complex measurements:</h4>

				<table id="example" class="table table-bordered table-hover table-responsive"
					cellspacing="0" width="100%">
					<thead>
						<tr>
							<th>#</th>
							<th>Creation time</th>
							<th>Resource</th>
							<th>Metric</th>
							<th>Value</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${measurements}" var="measurement"
							varStatus="loopCounter">

							<tr class="link">
								<td>${loopCounter.index + 1}</td>
								<td>${measurement.creationTimestamp}</td>
								<td><a
									href="/pz-web-client/resources/${resources[loopCounter.index].id}">${resources[loopCounter.index].name}</a></td>
								<td><a
									href="/pz-web-client/metrics/${metrics[loopCounter.index].id}">${metrics[loopCounter.index].name}</a></td>
								<%-- 									<td>	${measurement.value}	</td> --%>
								<td><fmt:formatNumber type="number" maxFractionDigits="4"
										value="${measurement.value}" /></td>
							</tr>

						</c:forEach>
					</tbody>
				</table>

				<script type="text/javascript">
					$(document).ready(function() {
						$('#example').DataTable();
					});
				</script>

			</div>
		</div>

	</div>
	</main>

</body>
</html>
