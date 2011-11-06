<#setting url_escaping_charset='UTF-8'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${game.name}</title>
	<link rel="stylesheet" href="opendarts.css" type="text/css">
</head>
<body>
	<div class="main">
		<#-- Left Menu -->
		<div class="nav">
			<a href="#status">Status</a><br />
			<a href="#stats">Statistics</a><br />
			<a href="#gameDetail">Game Detail</a><br />
			<#list game.playersGameDetail as detail>
			<a href="#${detail.player}Detail" class="sub">${detail.player} Detail</a><br />
			</#list>
			<#if option.exportChart>
			<a href="#charts">Charts</a><br />
				<#list game.charts as chart>
				<a href="#${chart.name}" class="sub">${chart.name}</a><br />
				</#list>
			</#if>
			<a href="#score">Score</a><br />
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
			<#-- Game Detail -->
			<div class="block">
				<a name="gameDetail"></a>
				<h2>Game Detail</h2>
				<div id="gameDetail">
					<label for="gamTimeTarget">Target Time:</label><input type="text" name="gamTimeTarget" value="${game.targetTime}"/><br/>
					<label for="gamTotalTime">Total time:</label><input type="text" name="gamTotalTime" value="${game.gameDetail.totalTime}"/><br/>
					<label for="gamNbThrows">Nb Throws:</label><input type="text" name="gamNbThrows" value="${game.gameDetail.nbThrows}"/><br/>
					<label for="gamNbDarts">Nb Darts:</label><input type="text" name="gamNbDarts" value="${game.gameDetail.nbDarts}"/><br/>
					<label for="gamPointBySeconds">Point by Seconds:</label><input type="text" name="gamPointBySeconds" value="${game.gameDetail.pointsBySecond}"/><br/>
					<label for="gamSecondsByThrow">Seconds by Throw:</label><input type="text" name="gamSecondsByThrow" value="${game.gameDetail.secondsByThrow}"/><br/>
					<label for="gamAvgScore">Average score:</label><input type="text" name="gamAvgScore" value="${game.gameDetail.averageScore}"/><br/>
				</div>
			</div>
			<#-- Player Detail -->
			<#list game.playersGameDetail as detail>
			<div class="block">
				<a name="${detail.player}Detail"></a>
				<h3>${detail.player} Detail</h3>
				<div id="${detail.player}Detail">
					<label for="${detail.player}TotalTime">Total time:</label><input type="text" name="${detail.player}TotalTime" value="${detail.totalTime}"/><br/>
					<label for="${detail.player}PointBySeconds">Point by Seconds:</label><input type="text" name="${detail.player}PointBySeconds" value="${detail.pointsBySecond}"/><br/>
					<label for="${detail.player}SecondsByThrow">Seconds by Throw:</label><input type="text" name="${detail.player}SecondsByThrow" value="${detail.secondsByThrow}"/><br/>
					<label for="${detail.player}AvgScore">Average score:</label><input type="text" name="${detail.player}AvgScore" value="${detail.averageScore}"/><br/>
					<label for="${detail.player}NbThrows">Nb Throws:</label><input type="text" name="${detail.player}NbThrows" value="${detail.nbThrows}"/><br/>
					<label for="${detail.player}NbDarts">Nb Darts:</label><input type="text" name="${detail.player}NbDarts" value="${detail.nbDarts}"/><br/>
				</div>
			</div>
			</#list>
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
			<#-- Score -->
			<div class="block">
				<a name="score"></a>
				<h3>Score</h3>
				<div id="score">
					<table id="score" summary="Scores">
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
								<th>${e.label}</th>
								<#list e.scores as s>
									<#if (s == 180)>
								<td class="_180">${s}</td>
									<#elseif (s >= 100)>
								<td class="_100">${s}</td>
									<#elseif (s >= 60)>
								<td class="_60">${s}</td>
									<#elseif (s == -1)>
								<td>-</td>
									<#else>
								<td>${s}</td>
									</#if>
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
