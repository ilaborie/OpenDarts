OpenDarts
=========

The main goal for this project is building an OpenSource application for Darts Training.
Currently this project is in the prototype state, be patient (or contribute).

Prototype
---------
The prototype contains:
  A scoring module for X01 game (501, 701, 170, ...)
  Support Two or more players.
  With a dumb user selection.
  A Computer player with a basic IA (does not support yet level)
  A basic statistics system (no persited)

Roadmap for V1
--------------
[BUGS] Fix all majors bugs

[TECH] Modularize application (ui, core, ui.app, core.player, core.stats, core.ia, core.x01, ui.x01, ...)
[TECH] Build system (maven tycho + Cloudbees)
[TECH] Testing
[TECH] add a console view
[TECH] Create installer for win

[FEAT] a stats view
[FEAT] add a preference system
[FEAT] basic serialization for stats
[FEAT] basic serialization for players
[FEAT] Multi Level IA
[FEAT] More flexible UI (for small screen)
[FEAT] initialize i18n: EN, FR (need contribution for others language)

Roadmap for V1.x
--------------
[BUGS] Fix all minors bugs

[TECH] Better packaging for MacOS (.app)
[TECH] Better packaging for Linux (.deb)

[FEAT] Capital scoring
[FEAT] Around the Clock scoring
[FEAT] Cricket scoring
[FEAT] OneToNine scoring
[FEAT] Add Helper view for x01 finish
[FEAT] More flexible UI
[FEAT] Add helps

Roadmap for V2.x
--------------
[TECH] move to Eclipse RCP 4.x
[TECH] client/ server mode
[TECH] Web Client
[TECH] Android client
[TECH] iOs client

[FEAT] voice
[FEAT] skins

Technicals aspects
------------------
This application is wrote in Java 6.
This is an Eclipse RCP application.
It's use the 3.7 version of Eclipse RCP. Moving to 4.x is planned.

License
-------
This application is on the Eclipse Public License (1.0)

Contributions
-------------
Free software could be better than others, you can fork or contribute:
* Give thanks
* Report Bugs
* Speaking about the project
* Improvements and new Feature suggestions
* Fix Spelling mistakes
* Writing documentations (help, ...)
* Translation (not yet available)
* Create and animate a WebSite
* Graphical contribution (icons, splash screen, ...)
* Create install package (Windows, debian .deb, osx .app)

If you are a Java/EclipseRCP developer you also could help me, fork me on github ;-) 



