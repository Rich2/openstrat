# openstrat

[![Join the chat at https://gitter.im/typestrat/Lobby](https://badges.gitter.im/typestrat/Lobby.svg)](https://gitter.im/typestrat/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Cross platform scala 2d graphics, basic geometry, maps, earth maps, hex-tiling, square tiling and strategy library. It currently works on JavaFx and web page. Using canvas on both platforms. Examples of the Javascript version can be seen at [richstrat.com](https://richstrat.com), documentation at [richstrat.com/api/ostrat/index.html](https://richstrat.com/api/ostrat/index.html). 

My hope / intention is to create something accessable to complete beginners. Complete beginners in Scala certainly, but eventually even complete beginners to programming. Because of this I'm not sure whether to use sbt or Mill. If you are not experienced with Scala, you have found this site and want to experiment, you will need to install Java JDK8 and sbt. more complete documentation for getting started on Linux / Windows / Mac will come later. The basic build has been tested on Linux and Windows 7. Note unfortunately for the moment there do still seem to be some problems with openjdk and JavaFx, so on Linux you may need to use the Oracle Jdk8.

Sbt currently set to 1.2.1. Scala set to 2.12.6. Run:

```bash
sbt //From project's root folder
```

From within the sbt console run:

```sbt
~ FxPlay/reStart//To launch a ScalaFx window. The most useful command for development
~ JsPlay/fastOptJS //WebPages/the fastPlay.html will display the results in a browser
JsPlay/fullOptJS //For optimimised Javascript. WebPages/fullPlay.html will display the results in a browser
```

The tilde **~** tells sbt to rerun the command everytime you modify and save a source file. The first command will build and launch a ScalaFx window. It will rebuild and relaunch so you can immediately see the effects of your changes. Copy AppNum.txt from Misc/ to ~/AppData/Local/OpenStratData/ Change the integer to change the application. All the examples on the richstrat.com website are available plus others. The second command will also rebuild on source changes in similar manner. However unlike with the reStart command, when you make a source file edit and save it, you will have to manually refresh the browser window after the fastOptJS command has finished the rebuild. 

## The Code currently has 5 modules:

1. AnteCompono. This is only a separate module at the moment because of the macros. Just a couple of simple macros so as I can track down the source location of my debug printlns.

2. Core. The bulk of the code this is organised into the following packages:
   - **ostrat** The root package. All other packages depend on this.     
     * 32 bit Int based Colours.
     * A persistence framework. Succinct readable object notation. Save files at `~/AppData/Local/OpenStratData`.
     * Array based Int and Double product traits collection traits.
     * Other general utilities.
   - **ostrat.geom**
     * Basic  geometry.
     * A number of implementation Value classes of the Int and Double product classes defined in ostrat.
     * 2d graphical objects for an abstract canvas.     
   - **ostrat.pDisp** depends on geom
     * Abstract canvas and classes for placing objects on that abstract canvas.
     * classes for the manipulation and display of maps.
     * Mouse and other abstract controls.
   - **ostrat.pGrid** depends on geom and pDisp
     * Abstract regular tile geometry.
     * Square and hex tile grid geometry.
     * OfTile clases for the display of tiles.
   - **ostrat.pEarth** depends on geom, pDisp and pGrid
     * Earth and sphere geometry.
     * Grids of Earth terrain.
     **ostrat.pEarth.pPts** large irregular Earth terrain areas. This is mainly a development aid.
   - **ostrat.pStrat** depends on geom, pDisp and pGrid
     * Flags.
     * Odds and ends.
   - **ostrat.pGames** a number of rudimentary games and applications depending on some or all of the above packages.
     * ostrat.pGames.pWW2 A grand strategy world War 2 Game using the hex tiled world terrain map. 
     * ostrat.pGames.p1783 A grand strategy game also using the world map set in 1783.
     * ostrat.pGames.p305 A grand Strategy Game set in 305BC, using part of the world map.
     * ostrat.pGames.pZug A 20th cntury squad based strategy game.
   - **ostrat.pGames** a number of rudimentary games and applications depending on some or all of the above packages. The intention is to factor out ocmmon functionalty and classes.
     * ostrat.pGames.pWW2 A grand strategy world War 2 Game using the hex tiled world terrain map. 
     * ostrat.pGames.p1783 A grand strategy game also using the world map starting in 1783.
     * ostrat.pGames.p305 A grand Strategy Game set in 305BC, using part of the world map.
     * ostrat.pGames.pZug A 20th century squad based strategy game using hex tiles.
     * ostrat.pGames.pCiv A Civilisational development game using hex tiles.
     * ostrat.pGames.pDung A Square tile based dungeon game.
     * ostrat.pGames.pCloseOrder. Pre modern close order formation based battles, not using tiles.
     * ostrat.pGames.pSpace A solar system app.
     * ostrat.pGames.pChess. A search for an elgant implementation of Draughts and Chess.
   - **ostrat.pWeb** Code for the generation and manipulation of
     * HTML
     * Other XML
     * CSS
     * Simple Javascipt
   - **ostrat.p3d** Currently just a stub. I have included it because 3d is the preferred GUI. I have started with 2d, just because 3d development is highly time consuming and I want to focus on game play and what might might be described as the algebra of tiling. There is no "physics engine", although there is time and distance and other basic mechanics maths will probably be included later. I prefer 3d graphics, but as we are dealing with animations not a "physics engine", 2d and 3d are completely interchangable.  There will also be a command line interface. This will be the canonical interface, although it obivously won't be the normal user interface.

3. FxStrat An implementation for Canvas using ScalaFx

4. JsStrat An implementation for canvas using Scala.Js on to an Html canvas.

5. NatStrat. This is currently just a hello world stub. I'm waiting for Scala-native to get up on 2.12 before experimenting. Running a game server in native should pose no problems. However there is no easily accessable canvas for native on Windows or Linux. The abstract canvas api could be implmented on DirectX or OpenGl, but this would require significantly more work than for the ScalaFx canvas or the Html Canvas.

The code is currently organised for my own convenience. However if any of it ever gains significant traction with other developers, then I would be very happy to break it up and reorganise it, if necessary. The packages are structured with a view to future break up, although the division between geom and pDisp packages is not complelty clear and I'm not entirely comfortable with it in its current form. So think of geom and pDisp as one sub-module.
