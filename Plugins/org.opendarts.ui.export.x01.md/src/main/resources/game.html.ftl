<#setting url_escaping_charset='UTF-8'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${game.name}</title>
	<link rel="stylesheet" href="opendarts.css" type="text/css">
</head>
<body>
	<h1>${game.name}</h1>
	
	<h2><a name="status"/>Status</h2>
	<div id="status">
		<label for="gamStartAt">Started at:</label><input type="text" name="gamStartAt" value="${game.start}"/><br/>
		<#if game.end??>
		<label for="gamEndAt">Ended at:</label><input type="text" name="gamEndAt" value="${game.end}"/><br/>
		</#if>
		<label for="gamWinner">Winner:</label><input type="text" name="gamWinner" value="${game.winner}"/><br/>
		<label for="gamStartingScore">Starting Score:</label><input type="text" name="gamStartingScore" value="${game.startingScore}"/><br/>
		<label for="gamNbGameToWin">Nb. Games to Win:</label><input type="text" name="gamNbGameToWin" value="${game.nbGameToWin}"/><br/>
	</div>
	
	<h2><a name="stats"/>Statistics</h2>
	<div id="stats">
		<#list game.stats as st>
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
	
	<#if option.exportChart>
	<h2><a name="charts"/>Charts</h2>
	<div id="charts">
		<#list game.charts as chart>
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
					<th></th>
					<#list game.playerList as p>
					<th>${p}</th>	
					</#list>
				<tr>
			</thead>
			<tbody>
				<#list game.entries as e>
				<tr>
					<td>${e.label}</td>
					<#list e.scores as s>
					<td>${s}</td>
					</#list>
				</tr>
				</#list>
			</tbody>
		</table>
	</div>
</body>
</html>
