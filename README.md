# openstrat

[![Join the chat at https://gitter.im/typestrat/Lobby](https://badges.gitter.im/typestrat/Lobby.svg)](https://gitter.im/typestrat/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Cross platform scala 2d graphics, basic geometry, maps, earth maps, hex-tiling, square tiling and strategy library. It currently works on JavaFx and web page. Using canvas on both platforms. Examples of the Javascript version can be seen at [richstrat.com](https://richstrat.com), documentation at [richstrat.com/api/rich/index.html](https://richstrat.com/api/rich/index.html). There is no "physics engine", although there is time and distance and other basic mechanics maths will probably be included later. I prefer 3d graphics, but as we are dealing with animations not a "physics engine", 2d and 3d are completely interchangable. I have started with 2d, just because 3d development is highly time consuming and I want to focus on game play and what might might be described as the algebra of tiling. There will also be a command line interface. This will be the canonical interface, although it obivously won't be the normal user interface.

My hope / intention is to create something accessable to complete beginners. Complete beginners in Scala certainly, but eventually even complete beginners to programming. Because of this I'm not sure whether to use sbt or Mill. If you are not experienced with Scala, you have found this site and want to experiment, you will need to install Java JDK8 and sbt. more complete documentation for getting started on Linux / Windows / Mac will come later. The basic build has been tested on Linux and Windows 7. Note unfortunately for the moment there do still seem to be some problems with openjdk and JavaFx, so on Linux you may need to use the Oracle Jdk8.

Sbt currently set to 1.1.4. Scala set to 2.12.6. I'm waiting for Scala-native to get up on 2.12 before experimenting. Running a game server in native should pose no problems. However there is no easily accessable canvas for native on Windows or Linux. The abstract canvas api could be implmented on DirectX or OpenGl, but this would require significantly more work than for the ScalaFx canvas or the Html Canvas. Run:

```bash
sbt //From project's root folder
```

From within the sbt console run:

```sbt
~ FxPlay/reStart//To launch a ScalaFx window. The most useful command for development
~ JsPlay/fastOptJS //WebPages/the fastPlay.html will display the results in a browser
JsPlay/fullOptJS //For optimimised Javascript. WebPages/fullPlay.html will display the results in a browser
```

The tilde **~** tells sbt to rerun the command everytime you modify and save a source file. The first command will build and launch a ScalaFx window. It will rebuild and relaunch so you can immediately see the effects of your changes. Change the number in Core:rich.pPlay.Play to change the application. All the examples on the richstrat.com website are available plus others. Changing the number will also change the application for the Js builds. The second command will also rebuild on source changes in similar manner. However unlike with the reStart command, when you make a source file edit and save it, you will have to manually refresh the browser window after the fastOptJS command has finished the rebuild. 

## The Code currently has 4 modules:

1. AnteCompono. This is only a separate module at the moment because of the macros. Just a couple of simple macros so as I can track down the source location of my debug printlns.

2. Core. The bulk of the code this is organised into the following packages:
   - **rich** The root package. All other packages depend on this.
     * General utilities.
     * A persistence framework. Succinct readalble object notation. Save files at `~/AppData/Local/OpenStratData`.
     * Array based Int and Double product traits collection traits.
   - **rich.geom**
     * Basic  geometry.
     * A number of implementation Value classes of the Int and Double product classes defined in rich.
     * 2d graphical objects for an abstract canvas.     
   - **rich.pDisp** depends on geom
     * Abstract canvas and classes for placing objects on that abstract canvas.
     * classes for the manipulation and display of maps.
     * Mouse and other abstract controls.
   - **rich.pGrid** depends on geom and pDisp
     * Abstract regular tile geometry.
     * Square and hex tile grid geometry.
     * OfTile clases for the display of tiles.
   - **rich.pEarth** depends on geom, pDisp and pGrid
     * Earth and sphere geometry.
     * Earth land outlines.
     * Grids of Earth terrain.
   - **rich.pStrat** depends on geom, pDisp and pGrid
     * Flags.
     * Odds and ends.
   - **rich.pGames** a number of rudimentary games and applications depending on some or all of the above packages.

3. FxStrat An implementation for Canvas using ScalaFx

4. JsStrat An implementation for canvas using Scala.Js on to an Html canvas.

The code is currently organised for my own convenience. However if any of it ever gains significant traction with other developers, then I would be very happy to break it up and reorganise it, if necessary. The packages are structured with a view to future break up, although the division between geom and pDisp packages is not complelty clear and I'm not entirely comfortable with it in its current form. So think of geom and pDisp as one sub-module.
