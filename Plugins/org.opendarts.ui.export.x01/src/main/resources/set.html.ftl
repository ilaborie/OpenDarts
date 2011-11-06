<#setting url_escaping_charset='UTF-8'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${set.name}</title>
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
				<#list set.charts as chart>
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
				<h1>${set.name}</h1>
				<a class="parent" href="../${set.parent.fileName?url}.html">${set.parent.name}</a>
				<ol>
					<#list set.games as game>
					<li><a href="./${game.rootName?url}/${game.fileName?url}.html">${game.name}</a></li>
					</#list>
				</ol>
			</div>
			<#-- Status -->
			<div class="block">
				<a name="stats"></a>
				<h2>Status</h2>
				<div id="status">
					<label for="setStartAt">Started at:</label><input type="text" name="sesStartAt" value="${set.start}"/><br/>
					<#if set.end??>
					<label for="setEndAt">Ended at:</label><input type="text" name="sesEndAt" value="${set.end}"/><br/>
					</#if>
					<label for="setWinner">Winner:</label><input type="text" name="sesWinner" value="${set.winner}"/><br/>
					<label for="setStartingScore">Starting Score:</label><input type="text" name="sesStartingScore" value="${set.startingScore}"/><br/>
					<label for="setNbGameToWin">Nb. Games to Win:</label><input type="text" name="sesNbGameToWin" value="${set.nbGameToWin}"/><br/>
				</div>
			</div>
			<#-- Statistics -->
			<div class="block">
				<a name="stats"></a>
				<h2>Statistics</h2>
				<div id="stats">
				<#list set.stats as st>
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
									<#if v.best>
								<td class="best">${v}</td>
									<#else>
								<td>${v}</td>
									</#if>
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
					<#list set.charts as chart>
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
								<th>Game</th>
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
			</div>
		</div>
	</div>
</body>
</html>
