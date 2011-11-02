<#setting url_escaping_charset='UTF-8'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${set.name}</title>
	<link rel="stylesheet" href="opendarts.css" type="text/css">
</head>
<body>
	<h1>${set.name}</h1>
	<div class="nav">
		<a href="#status">Status</a>
		<a href="#stats">Statistics</a>
		<#if option.exportChart>
		<a href="#charts">Charts</a>
		</#if>
		<a href="#detail">Detail</a>
	</div>
	<a class="parent" href="../${set.parent.fileName?url}.html">${set.parent.name}</a>
	<ol>
		<#list set.games as game>
		<li><a href="./${game.rootName?url}/${game.fileName?url}.html">${game.name}</a></li>
		</#list>
	</ol>
	
	<h2><a name="status"/>Status</h2>
	<div id="status">
		<label for="setStartAt">Started at:</label><input type="text" name="sesStartAt" value="${set.start}"/><br/>
		<#if set.end??>
		<label for="setEndAt">Ended at:</label><input type="text" name="sesEndAt" value="${set.end}"/><br/>
		</#if>
		<label for="setWinner">Winner:</label><input type="text" name="sesWinner" value="${set.winner}"/><br/>
		<label for="setStartingScore">Starting Score:</label><input type="text" name="sesStartingScore" value="${set.startingScore}"/><br/>
		<label for="setNbGameToWin">Nb. Games to Win:</label><input type="text" name="sesNbGameToWin" value="${set.nbGameToWin}"/><br/>
	</div>
	
	<h2><a name="stats"/>Statistics</h2>
	<div id="stats">
		<#list set.stats as st>
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
		<#list set.charts as chart>
		<h3>${chart.name}</h3>
		<#if option.chartPng>
		<img src="${chart.name}.png"/>
		<#else>
		<img src="${chart.name}.jpg"/>
		</#if>
		</#list>
	</div>
	</#if>
	
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
				<#list set.games as game>
				<tr>
					<td>${game.index}</td>
					<td>${game.players}</td>
					<td>${game.winner}</td>
					<td>${game.result}</td>
				</tr>
				</#list>
			</tbody>
		</table>
	</div>
</body>
</html>
