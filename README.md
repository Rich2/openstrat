<html>
<head>
<link rel="stylesheet" type="text/css" href="Documentation.css">  
</head>
<body>
<h1>openstrat</h1>

[![Join the chat at https://gitter.im/typestrat/Lobby](https://badges.gitter.im/typestrat/Lobby.svg)](https://gitter.im/typestrat/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
<b>This project has 2 main focuses:</b>

<h3>A functional Geometry and Vector Graphics library</h3>
<h3>A functional strategy game and historical education library, particularly focused on simultaneous-turn, tile-based games.</h3>

We are currenlty in the process of moving documentation to <a href="https://richstrat.com/Documentation/"></a> as it gives better formatting options.

The Strategy games was the original motivation for the project, but the Geometry and Graphics library have far wider applicability. The Geometry and Graphics are far more developed, while the tiling and strategy games are still in a far more expermiental stage. This is in accordance with the oriinal vision, part of which was to explore the possiblites of an Algebra of Tiling. Out of the needs of these 2 primary focuses, 2 secondary focuses have developed:

<h3>A Show, 2D-Show and Persistence Library / Framework</h3>
<h3> Compound Value type collections library</h3>

<p>It currently works on JavaFx and web page. Using canvas on both platforms. Examples of the JavaScript version can be seen at <a href="https://richstrat.com">richstrat.com</a>, Scala Docs at <a
href="https://richstrat.com/api/ostrat/index.html"></a></p>

<p>This project is intended to be accessible and welcoming to programmers of all levels. Indeed it is intended as a vehicle for complete beginners to learn programming in a fun environment. To be able to begin by what for most games would be described as modding and then move down into programming as deep as they wish to go, at the pace they wish to. I want to break down the wall between modding and coding. So if you're new to programming and want to get involved, drop into the gitter channel and say hi. If you are not experienced with Scala, you have found this site and want to experiment, you will need to install Java JDK8 and sbt. more complete documentation for getting started on Linux / Windows / Mac will come later. The basic build has been tested on Linux and Windows 7. Note unfortunately for the moment there do still seem to be some problems with openjdk and JavaFx, so on Linux you may need to use the Oracle Jdk8.</p>

<p>However at the other end, I would welcome input from developers with greater experience and knowledge than my own. One of the goals of the project is to explore, where it is best to compose with trait / class inheritance and where to use functions. When to use mutation and when to use immutability. When to use smart, garbage collected heap based objects and when to use dumb data values. Balancing the competing priorities of elegance, succinctness, readability, run-time performance, compile time performance and accessibility for inexperienced programmers. I feel Scala is, and in particular Scala 3 will be the ideal language to explore these questions.</p>

<p>Scala currently set to 2.13.1. Scala.Js set to 0.6.29. Sbt currently set to 1.3.3 (uses the build.sbt file). Update your Mill to 0.5.2 (uses the build.sc file). Run:<br>
<code>sbt //In bash from project's root folder</code></p>

<p>From within the sbt console run:<br>
<code>~ reStart</code> To launch a ScalaFx window. The most useful command for development<br>
<code>~ JsDev/fastOptJS</code> To rebuild a fast optimised JavaScript file. Use with WebPages/SbtFastDev.html<br>
<code>JsDev/fullOptJS</code> To build a full optimised JavaScript file. Use with WebPages/SbtFullDev.html<br>
<code>~ Util/test</code> Rerun tests on Util module.<br>
<code>~ Graphic/test</code> Rerun tests on, Graphic module.<br>
<code>~ Strat/test</code> Rerun tests on, Strat module.<br>
<code>DocProj/doc</code> Will produce docs for the UtilJvm GraphicJvm and StratJvm modules. They can be found in target/DocProj/target/scala-2.12/api/. Local link PathToProject/openstrat/target/DocProj/target/scala-2.12/api/index.html</p>


<p>The tilde <code>~</code> tells sbt to rerun the command every time you modify and save a source file. The first command will build and launch a ScalaFx window. It will rebuild and relaunch so you can immediately see the effects of your changes. Copy the DevSettings.rson file from the Misc folder to the  Dev/mine folder. Creating the directory and its parents if not already existing. Change the appStr setting in DevSettings.rson to change the application. All the examples on the richstrat.com website are available plus others. The second command will also rebuild on source changes in similar manner. However unlike with the reStart command, when you make a source file edit and save it, you will have to manually refresh the browser window after the fastOptJS command has finished the rebuild.</p>

<p>So I'm experimenting with Mill from the project root folder run:
<code>mill run</code> To launch a ScalaFx window.<br>
<code>mill -w run</code> As above but mill will rebuild and relaunch the ScalaFx window when ever you save changes to source code. The most useful command for development.<br>
<code>mill test</code> To run tests.<br>
<code>mill -w test</code> To recompile and rerun the tests on source code changes.<br>
<code>mill -w Dev.runMain ostrat.pFx.App3D</code> Temp command<br>
<code>mill -w jsfast</code> To rebuild the fast optimised Js file. Use with WebPages/MillFastDev.html<br>
<code>mill jsfull</code> To build the fully optimised Js file. Use with WebPages/MillFullDev.html<br>
<code>mill mill.scalalib.GenIdea/idea</code> To create an IntelliJ IDEA project.</p>

<ul>For IntellliJ useful options:
<li>File => Editor => General -> Other -> tick "Show quick documentation on mouse move".</li>
<li>File => "Build, Execution, Deployment" => Compiler -> "Build project automatically"</li>
<li>Project-Pane => Options -> "Flatten packages"</li></ul>

<p>The Code is currently organised into 6 modules.** Each module can be build artifacts for Jvm and JavaFx and for the JavaScript platform and the Web. Modules can be built for Scala-Native, but code, notably hanging commas may break the Scala-Native build as ScalaNative is still on Scala 2.11.</p>

**1. Util** Depends on UtilMacros. Organised into the following folders and packages:
   - **ostrat** The root package. All other packages depend on this.     
     * 32 bit Int based Colours.
     * A persistence framework. Succinct readable object notation. Save files at `~/AppData/Local/OpenStratData`.
     * A functional Either based Errors framework.
     * Array based compound value collections of same length elements,to overcome the performance deficencies of the Java and JavaScript platforms, with a view to maximising common code for future Scala Native and Scala Web assembly platforms.
     * 3 simple macros, that print the source code location to help with debugging and development. They are a separate unit of compilation for sbt /mill, in the srcMacros folder.
     * Other general utilities.
   - **ostrat.parse**
     * The code for parsing RSON, Rich Succinct Object Notation.  
   - **ostrat.pWeb** Code for the generation and manipulation of
     * HTML
     * Other XML
     * CSS
     * Simple JavaScript

**2. Graphic** Depends on UtilMacros and Util, organised into the following packages:
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
     * There is no implementation for Native yet. I'm waiting for Scala-native to get up on 2.12 before experimenting. Running a game server in native should pose no problems. However there is no easily accessible canvas for native on Windows or Linux. The abstract canvas api could be implemented on DirectX or OpenGL, but this would require significantly more work than for the ScalaFx canvas or the Html Canvas.
   - **ostrat.pGrid** Depends on geom and pCanv
     * Abstract regular tile geometry.
     * Square and hex tile grid geometry.
     * OfTile classes for the display of tiles.
   - **ostrat.p3d** Currently just a stub. I have included it because 3d is the preferred GUI. I have started with 2d, just because 3d development is highly time consuming and I want to focus on game play and  what might might be described as the algebra of tiling. There is no "physics engine", although there is time and distance and other basic mechanics maths will probably be included later. I prefer 3d graphics, but as we are dealing with animations not a "physics engine", 2d and 3d are completely interchangeable.  There will also be a command line interface. This will be the canonical interface, although it obviously won't be the normal user interface.


**3. World** Depends on UtilMacros, Util and Graphic. The code for specific applications, organised into the following packages:   
   - **ostrat.pEarth** depends on geom, pCanv and pGrid
     * Earth and sphere geometry.
     * Grids of Earth terrain.
   - **ostrat.pEarth.pPts** large irregular Earth terrain areas. This is mainly a development aid.
   

**4. Strat**
   - **ostrat.pStrat** depends on geom, pCanv and pGrid and pEarth
     * Flags.
     * DemoCanvas for graphics elements.
     * Odds and ends.
   - **ostrat.pGames** A number of rudimentary games and applications depending on some or all of the above packages. The intention is to factor out common functionality and classes.
     * ostrat.pGames.pWW2 A grand strategy world War 2 game, using the hex tiled world terrain map. 
     * ostrat.pGames.p1783 A grand strategy game, also using the world map starting in 1783.
     * ostrat.pGames.p305 A grand strategy game set in 305BC, using part of the world map.
     * ostrat.pGames.pZug A 20th century squad based strategy game using hex tiles.
     * ostrat.pGames.pCiv A human history 4x development game using hex tiles.
     * ostrat.pGames.pDung A Square tile based dungeon game.
     * ostrat.pGames.pCloseOrder. Pre modern close order formation based battles, not using tiles.
     * ostrat.pGames.pSpace A solar system app.
     * ostrat.pGames.pChess. A search for an elegant implementation of Draughts and Chess.
    

**5. Learn** Depends on UtilMacros, Util, Graphic, World and Strat.
   - **pLearn** A series of lessons for beginners to Scala, complete beginners to programming and beginners in geometry, using the graphical API. These lessons are published separately as the LearnScala project.
   
**6. Dev** Depends on all the other modules. This is purely an integration module for development purposes and is not intended to contain library code.

The code is organised so if it gains significant traction with other developers, then it can be broken up into separate repositories. [Useful Git commands](https://richstrat.com/Documentation/GitCommands.html). [Miscellaneous notes](https://richstrat.com/Documentation/Miscellaneous.html)

<p><b>Road Map</b> So currently we now have a 3 month maximum time frame for Github issues, for non urgent issues left as eaily accessible for beginners. Everything will go in the road map, unitl we have time and focus to start implementing.
<ul><li>2.5D, 3D Graphics. The knowledge form inplementing 2.5 and 3d will allow clarification of the 2d Transformation of arcs.</li>
    <li>Audio: (scalajs hooks into Web API AudioContext so thats half the bases covered?). w0d won't look into jfx now until things a bit more certain that way.</li>
    <li>Array Double based generalised deep-compound value-types includind C-style type unions and single Array based collections of those types. Currently we just have Pruducts of a single type.</li>
    <li>Deep-compound value-types with repeat last members. These can not have single Array Collections.</li></ul></p>



</body>
</html>
