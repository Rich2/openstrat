# openstrat

[![Join the chat at https://gitter.im/typestrat/Lobby](https://badges.gitter.im/typestrat/Lobby.svg)](https://gitter.im/typestrat/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Cross platform scala 2d graphics, basic geometry, maps, earth maps, hex-tiling and strategy library. It currently works on JavaFx and web page. Using canvas on both platforms. Examples of the Javascript version can be seen at [richstrat.com](richstrat.com), documentation at [richstrat.com/api/rich/index.htlm](richstrat.com/api/rich/index.htlm).

My hope / intention is to create something accessable to complete beginners. Complete beginners in Scala certainly, but eventually even complete beginners to programming. Because of this I'm not sure whether to use sbt or Mill. For the time being though, there will not be the documentation to support non scala developers, however if you are not experienced with Scala, you have found this site and want to experiment, you will need to install Java JDK8 and sbt. The basic build has been tested on Linux and Windows 7. Note unfortunately for the moment there do still seem to be some problems with openjdk and JavaFx, so on Linux you may need to use the Oracle Jdk8.

Sbt currently set to 1.1.6. Scala set to 2.12.6. I'm waiting for Scala-native to get up on 2.12 before experimenting. Running a game server in native should pose no problems. However there is no easily accessable canvas for native on Windows or Linux. The abstract canvas api could be implmented on DirectX or OpenGl, but this would require significantly more work than for the ScalaFx canvas or the Html Canvas.

Run sbt. The most useful command is:

~ FxPlay/reStart

This will build and launch a ScalaFx window. It will rebuild and relaunch everytime you modify and save a source file. So you can immediatly see the effects of your changes. Change the number in Core:rich.pPlay.Play to change the application. All the examples on the richstrat.com website are available plus others.

Changing the number will also change the application for the Js build. that can be rebuilt in similar manner with the command:

~ JsPlay/fastOptJS

WebPages/fastPlay.html

will display the results. Unlike with the reStart command, when you make a source file edit and save it, you will have to manually refresh the browser window after the fastOptJS command has finsihed the rebuild. To get an optomised Javascript executable run:

JsPlay/fullOptJS

WebPages/fullPlay.html

will display the results after the build has been completed.

## The Code currently has 4 modules:

1. AnteCompono. This is only a seperate module at the moment because of the macros. just a a couple of simple macros so as I can track down the source location of my debug printlns. As well as some general utilities stuff. It includes my persistence framework and the array based int and Double product collection traits.

2. Core. The bulk of the code including:
   * Basic  geometry.
   * Abstract canvas and classes for placing objects on that abstract canvas
   * classes for the manipulation and display of maps
   * Square and hex tile grid geometry.
   * Earth geometry and terrain
   * A number of rudimentary games and applications using varing amounts of the above

3. FxStrat An implementation for Canvas using ScalaFx

4. JsStrat An implementation for canvas using Scala.Js on to an Html canvas.

The code is currently organised for my own convenience. However if any of it ever gains significant traction with other developers, then I would be very happy to break it up and reorganise it, if necessary. The packages are structured with a view to future break up, although the division between geom and pDisp packages is not complelty clear and I'm not entirely comfortable with it in its current form. So think of geom and pDisp as one sub-module.