<#setting url_escaping_charset='UTF-8'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${session.name}</title>
	<link rel="stylesheet" href="opendarts.css" type="text/css">
</head>
<body>
	<h1>${session.name}</h1>
	<div class="nav">
		<a href="#status">Status</a>
		<a href="#stats">Statistics</a>
		<#if option.exportChart>
		<a href="#charts">Charts</a>
		</#if>
		<a href="#detail">Detail</a>
	</div>
	
	<ol>
		<#list session.sets as set>
		<li><a href="./${set.rootName?url}/${set.fileName?url}.html">${set.name}</a></li>
		</#list>
	</ol>
	
	<h2><a name="status"/>Status</h2>
	<div id="status">
		<label for="sesStartAt">Started at:</label><input type="text" name="sesStartAt" value="${session.start}"/><br/>
		<#if session.end??>
		<label for="sesEndAt">Ended at:</label><input type="text" name="sesEndAt" value="${session.end}"/><br/>
		</#if>
		<label for="sesWinner">Winner:</label><input type="text" name="sesWinner" value="${session.winner}"/><br/>
	</div>
	
	<h2><a name="detail"/>Detail</h2>
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
	
	<h2><a name="stats"/>Statistics</h2>
	<div id="stats">
		<#list session.stats as st>
			<table>
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
						<td>${v}</td>	
						</#list>
					</tr>
					</#list>
				</tbody>
			</table>
		</#list>
	</div>
	
	<#if option.exportChart>
	<h2><a name="charts"/>Charts</h2>
	<div id="charts">
		<#list session.charts as chart>
		<h3>${chart.name}</h3>
		<#if option.chartPng>
		<img src="${chart.name}.png"/>
		<#else>
		<img src="${chart.name}.jpg"/>
		</#if>
		</#list>
	</div>
	</#if>
</body>
</html>
