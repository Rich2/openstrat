# openstrat

[![Join the chat at https://gitter.im/typestrat/Lobby](https://badges.gitter.im/typestrat/Lobby.svg)](https://gitter.im/typestrat/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Cross platform Scala 2d graphics, basic geometry, maps, earth maps, hex-tiling, square tiling and strategy library. It currently works on JavaFx and web page. Using canvas on both platforms. Examples of the Javascript version can be seen at [richstrat.com](https://richstrat.com), documentation at [richstrat.com/api/ostrat/index.html](https://richstrat.com/api/ostrat/index.html). 

This project is intended to be accessible and welcoming to programmers of all levels. Indeed it is intended as a vehicle for complete beginners to learn programming in a fun environment. To be able to begin by what for most games would be described as modding and then move down into programming as deep as they wish to go, at the pace they wish to. I want to break down the wall between modding and coding. So if you're new to programming and want to get involved, drop into the gitter channel and say hi. If you are not experienced with Scala, you have found this site and want to experiment, you will need to install Java JDK8 and sbt. more complete documentation for getting started on Linux / Windows / Mac will come later. The basic build has been tested on Linux and Windows 7. Note unfortunately for the moment there do still seem to be some problems with openjdk and JavaFx, so on Linux you may need to use the Oracle Jdk8.

However at the other end, I would welcome input from developers with greater experience and knowledge than my own. One of the goals of the project is to explore, where it is best to compose with trait / class inheritance and where to use functions. When to use mutation and when to use immutability. When to use smart, garbage collected heap based objects and when to use dumb data values. Balancing the competing priorities of elegance, succinctness, readability, run-time performance, compile time performance and accessibility for inexperienced programmers. I feel Scala is, and in particular Scala 3 will be the ideal language to explore these questions.

Scala currently set to 2.13.0, except for Scala-native build set to 2.11.12. Sbt currently set to 1.2.8. Update your Mill to 0.5.0. Run:

```
sbt //In bash from project's root folder
```
From within the sbt console run:
```
~ reStart //To launch a ScalaFx window. The most useful command for development
~ JsDev/fastOptJS //To rebuild a fast optimised JavaScript file. Use with WebPages/SbtFastDev.html
JsDev/fullOptJS //To build a full optimised JavaScript file. Use with WebPages/SbtFullDev.html
~ Util/test //Rerun tests on Util module.
~ Graphic/test //Rerun tests on, Graphic module.
~ Strat/test //Rerun tests on, Strat module.
DocProj/doc //Will produce docs for the UtilJvm GraphicJvm and StratJvm modules.
  They can be found in target/DocProj/target/scala-2.12/api/.
  Local link PathToProject/openstrat/target/DocProj/target/scala-2.12/api/index.html
```

The tilde **~** tells sbt to rerun the command every time you modify and save a source file. The first command will build and launch a ScalaFx window. It will rebuild and relaunch so you can immediately see the effects of your changes. Change the appNum on line 32 of Core/src/ostrat/pDev/Apps.scala to change the application. All the examples on the richstrat.com website are available plus others. The second command will also rebuild on source changes in similar manner. However unlike with the reStart command, when you make a source file edit and save it, you will have to manually refresh the browser window after the fastOptJS command has finished the rebuild.

So I'm experimenting with Mill from the project root folder run:

```
mill run //To launch a ScalaFx window.
mill -w run //As above but mill will rebuild and relaunch the ScalaFx window when ever you save changes to source code. The most useful command for development
mill test //To run tests
mill -w //To recompile and rerun the tests on source code changes.
mill -w jsfast //To rebuild the fast optimised Js file. Use with WebPages/MillFastDev.html
mill jsfull //To build the fully optimised Js file. Use with WebPages/MillFullDev.html
mill mill.scalalib.GenIdea/idea //To create an IntelliJ IDEA project.
```
For IntellliJ useful options:
* File => Editor => General -> Other -> tick "Show quick documentation on mouse move".
* File => "Build, Execution, Deployment" => Compiler -> "Build project automatically"
* Project-Pane => Options -> "Flatten packages"

**The Code currently has 5 modules.** Each module can be built for Jvm and JavaFx and for the Javascript platform and the Web. Modules can be built for Scala-Native, but code, notably hanging commas may break the Scala-Native build as ScalaNative is still on Scala 2.11:

**1. UtilMacros** They are a separate unit of compilation for sbt /mill. Just a couple of simple macros so as one can track down the source location of one's debug printlns.

**2. Util** No module dependancies. Organised into the following folders and packages.
   - 
   - **ostrat** The root package. All other packages depend on this.     
     * 32 bit Int based Colours.
     * A persistence framework. Succinct readable object notation. Save files at `~/AppData/Local/OpenStratData`.
     * A functional Either based Errors framework.
     * Array based compound value collections of same length elements.
     * Other general utilities.
   - **ostrat.parse**
     * Basic  geometry.
     * A number of implementation Value classes of the Int and Double product classes defined in ostrat.
     * 2d graphical objects for generalised use. They are of particular use for the generic canvas based classes defined in pCanv but can be used in any display framework and for printing.   
   - **ostrat.pWeb** Code for the generation and manipulation of
     * HTML
     * Other XML
     * CSS
     * Simple Javascipt

**3. Graphic** depends on Macros and Util, organised into the following packages.  
   - **ostrat.geom** A pure or near pure functional package.
     * Basic  geometry.
     * A number of implementation Value classes of the Int and Double product classes defined in ostrat.
     * 2d graphical objects for generalised use. They are of particular use for the generic canvas based classes defined in pCanv but can be used in any display framework and for printing.     
   - **ostrat.pCanv** depends on geom. This could be made into a separate module, but I don't see any great advantage.
     * Abstract canvas and classes for placing objects on that abstract canvas.
     * classes for the manipulation and display of maps.
     * Mouse and other abstract controls.
     * An implementation of Canvas for Jvm using JavaFx.
     * An implementation of Canvas for Html Canvas using JavaScript.
     * There is no implementation for Native yet. I'm waiting for Scala-native to get up on 2.12 before experimenting. Running a game server in native should pose no problems. However there is no easily accessible canvas for native on Windows or Linux. The abstract canvas api could be implemented on DirectX or OpenGl, but this would require significantly more work than for the ScalaFx canvas or the Html Canvas.
   - **ostrat.p3d** Currently just a stub. I have included it because 3d is the preferred GUI. I have started with 2d, just because 3d development is highly time consuming and I want to focus on game play and what might might be described as the algebra of tiling. There is no "physics engine", although there is time and distance and other basic mechanics maths will probably be included later. I prefer 3d graphics, but as we are dealing with animations not a "physics engine", 2d and 3d are completely interchangeable.  There will also be a command line interface. This will be the canonical interface, although it obviously won't be the normal user interface.

**4. Strat** The code for specific applications, organised into the following packages:   
   - **ostrat.pGrid** depends on geom and pCanv
     * Abstract regular tile geometry.
     * Square and hex tile grid geometry.
     * OfTile classes for the display of tiles.
   - **ostrat.pEarth** depends on geom, pCanv and pGrid
     * Earth and sphere geometry.
     * Grids of Earth terrain.
   - **ostrat.pEarth.pPts** large irregular Earth terrain areas. This is mainly a development aid.
   - **ostrat.pStrat** depends on geom, pCanv and pGrid
     * Flags.
     * DemoCanvas for graphics elements.
     * Odds and ends.
   

   - **ostrat.pGames** a number of rudimentary games and applications depending on some or all of the above packages. The intention is to factor out common functionality and classes.
     * ostrat.pGames.pWW2 A grand strategy world War 2 game, using the hex tiled world terrain map. 
     * ostrat.pGames.p1783 A grand strategy game, also using the world map starting in 1783.
     * ostrat.pGames.p305 A grand Strategy Game set in 305BC, using part of the world map.
     * ostrat.pGames.pZug A 20th century squad based strategy game using hex tiles.
     * ostrat.pGames.pCiv A civilisational development game using hex tiles.
     * ostrat.pGames.pDung A Square tile based dungeon game.
     * ostrat.pGames.pCloseOrder. Pre modern close order formation based battles, not using tiles.
     * ostrat.pGames.pSpace A solar system app.
     * ostrat.pGames.pChess. A search for an elegant implementation of Draughts and Chess.

  **5. Dev**
    -**pLearn** A series of lessons for beginners to Scala, complete beginners to programming and beginners in geometry, using the graphical API. These lessons are published separately as the LearnScala project.
   

The code is organised so if it gains significant traction with other developers, then it can be broken up into separate repositories.