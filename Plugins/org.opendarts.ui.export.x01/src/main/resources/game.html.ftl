<#setting url_escaping_charset='UTF-8'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${game.name?html}</title>
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
				<#list game.charts as chart>
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
				<h1>${game.name}</h1>
				<a class="parent" href="../${game.parent.fileName?url}.html">${game.parent.name}</a>
			</div>
			<#-- Status -->
			<div class="block">
				<a name="stats"></a>
				<h2>Status</h2>
				<div id="status">
					<label for="gamStartAt">Started at:</label><input type="text" name="gamStartAt" value="${game.start}"/><br/>
					<#if game.end??>
					<label for="gamEndAt">Ended at:</label><input type="text" name="gamEndAt" value="${game.end}"/><br/>
					</#if>
					<label for="gamWinner">Winner:</label><input type="text" name="gamWinner" value="${game.winner}"/><br/>
					<label for="gamStartingScore">Starting Score:</label><input type="text" name="gamStartingScore" value="${game.startingScore}"/><br/>
					<label for="gamNbGameToWin">Nb. Games to Win:</label><input type="text" name="gamNbGameToWin" value="${game.nbGameToWin}"/><br/>
				</div>
			</div>
			<#-- Statistics -->
			<div class="block">
				<a name="stats"></a>
				<h2>Statistics</h2>
				<div id="stats">
				<#list game.stats as st>
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
					<#list game.charts as chart>
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
			</div>
		</div>
	</div>
</body>
</html>
