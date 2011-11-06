<#setting url_escaping_charset='UTF-8'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${session.name}</title>
	<link rel="stylesheet" href="opendarts.css" type="text/css">
</head>
<body>
	<div class="main">
		<#-- Left Menu -->
		<div class="nav">
			<a href="#status">Status</a><br />
			<a href="#stats">Statistics</a><br />
			<#if option.exportChart>
			<a href="#charts">Charts</a><br />
				<#list session.charts as chart>
				<a href="#${chart.name}" class="sub">${chart.name}</a><br />
				</#list>
			</#if>
			<a href="#detail">Detail</a><br />
		</div>
		<div class="container">
			<#-- Header -->
			<div class="header">
				<div id="headerTitle">OpenDarts</div>
			</div>
			<#-- Title -->
			<div class="title">
				<h1>${session.name}</h1>
				<ol>
					<#list session.sets as set>
					<li><a href="./${set.rootName?url}/${set.fileName?url}.html">${set.name}</a></li>
					</#list>
				</ol>
			</div>
			<#-- Status -->
			<div class="block">
				<a name="stats"></a>
				<h2>Status</h2>
				<div id="status">
					<label for="sesStartAt">Started at:</label><input type="text" name="sesStartAt" value="${session.start}"/><br/>
					<#if session.end??>
					<label for="sesEndAt">Ended at:</label><input type="text" name="sesEndAt" value="${session.end}"/><br/>
					</#if>
					<label for="sesWinner">Winner:</label><input type="text" name="sesWinner" value="${session.winner}"/><br/>
				</div>
			</div>
			<#-- Statistics -->
			<div class="block">
				<a name="stats"></a>
				<h2>Statistics</h2>
				<div id="stats">
				<#list session.stats as st>
					<table summary="Statistics">
						<thead>
							<tr>
								<th></th>
								<#list st.players as p>
								<th>${p}</th>
								</#list>
							</tr>
						</thead>
						<tbody>
							<#list st.entries as e>
							<tr>
								<th>${e.label}</th>
								<#list e.playersValues as v>
								<#if v??>
								<td>${v}</td>
								<#else>
								<td></td>
								</#if>
								</#list>
							</tr>
							</#list>
						</tbody>
					</table>
				</#list>
				</div>
			</div>
			<#-- Charts -->
			<#if option.exportChart>
			<div class="block">
				<a name="charts"></a>
				<h2>Charts</h2>
				<div id="charts">
					<#list session.charts as chart>
					<a name="${chart.name}"></a>
					<h3>${chart.name}</h3>
					<div class="chart">
					<#if option.chartPng>
						<img src="${chart.name}.png"/>
					<#else>
						<img src="${chart.name}.jpg"/>
					</#if>
					</div>
					</#list>
				</div>
			</div>
			</#if>
			<#-- Detail -->
			<div class="block">
				<a name="detail"></a>
				<h2>Detail</h2>
				<div id="detail">
					<table id="tblDetail">
						<thead>
							<tr>
								<th>Set</th>
								<th>Players</th>
								<th>Winner</th>
								<th>Result</th>
							<tr>
						</thead>
						<tbody>
							<#list session.sets as set>
							<tr>
								<td>${set.index}</td>
								<td>${set.players}</td>
								<td>${set.winner}</td>
								<td>${set.result}</td>
							</tr>
							</#list>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
