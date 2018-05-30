# openstrat
Cross platform scala 2d graphics, basic geometry, maps, earth maps, hex-tiling and strategy library. It currently works on JavaFx and web page. Using canvas on both platforms. Examples of the Javascript version can be seen at richstrat.com.

I've currently just included a build file for the ScalaFx version. My hope / intention is to create something accessable to complete beginners. Complete beginners in Scala certainly, but eventually even complete beginners to programming. Because of this I'm not sure whether to use sbt or Mill. So I want to experiment with Mill before providing full build functionality. For the time being though, there will not be the documentation to support non scala developers, however if you are not experienced with Scala, you have found this site and want to experiment, you will need to install Java JDK8 and sbt.

I've tested the current build on Linux and Windows 7.

Sbt current set to 1.1.4, due to problem with ~ in 1.1.5.
Scala set to 2.12.6. I'm waiting for Scala-native to get up on 2.12 before experimenting. Running a game server in native should pose no problems. However there is no easily accessable canvas for native on Windows or Linux. The abstract canvas api could be implmented on DirectX or OpenGl, but this would require significantly more work than for the ScalaFx canvas or the Html Canvas.

~ FxPlay/reStart

~ PlayJs/fastOptJS


