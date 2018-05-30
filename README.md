# openstrat
Cross platform scala 2d graphics, basic geometry, maps, earth maps, hex-tiling and strategy library. It currently works on JavaFx and web page. Using canvas on both platforms. Examples of the Javascript version can be seen at richstrat.com.

I've currently just included a build file for the ScalaFx version. My hope / intention is to create something accessable to complete beginners. Complete beginners in Scala certainly, but eventually even complete beginners to programming. Because of this I'm not sure whether to use sbt or Mill. S For the time being though, there will not be the documentation to support non scala developers, however if you are not experienced with Scala, you have found this site and want to experiment, you will need to install Java JDK8 and sbt.

I test on Linux and from time to time on Windows 7.

Sbt current set to 1.1.4, due to problem with ~ in 1.1.5 and 1.1.6.
Scala set to 2.12.6. I'm waiting for Scala-native to get up on 2.12 before experimenting. Running a game server in native should pose no problems. However there is no easily accessable canvas for native on Windows or Linux. The abstract canvas api could be implmented on DirectX or OpenGl, but this would require significantly more work than for the ScalaFx canvas or the Html Canvas.

Run sbt. The most useful command is:

~ FxPlay/reStart

This will build and launch a ScalaFx window. It will rebuild and relaunch everytime you modify and save a source file. So you can immediatly see the effects of your changes. Change the number in Core:rich.pPlay.Play to change the application. All the examples on the richstrat.com website are available plus others.

Changing the number will also change the application for the Js build. that can be rebuilt in similar manner with the command:

~ PlayJs/fastOptJS

The file

WebPages/fastPlay.html

will display the results. Unlike with the reStart command, when you make a source file edit and save it, you will have to manually refresh the browser window after the fastOptJS command has finsihed the rebuild. To get an optomised Javascript executable run:

PlayJs/fullOptJS

The result can be seen in 

WebPages/fullPlay.html

after the build has been completed.


