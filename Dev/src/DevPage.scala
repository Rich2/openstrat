/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pWeb._

object DevPage extends HtmlPage
{
  override def head: HtmlHead = HtmlHead.titleCss("Dev Module", "https://richstrat.com/Documentation/documentation")

  override def body: HtmlBody = HtmlBody(HtmlH1("Dev Module"), central)

  def central: HtmlDiv = HtmlDiv.classAtt("central", list, cenStr.xCon, miscStr.xCon)

  def list: HtmlOlWithLH = HtmlOlWithLH(HtmlH2("The Dev module contains"), appSel, siteGen)

  def appSel: HtmlLi = HtmlLi.str("JavaFx application selection and developer settings for the different apps.")

  def siteGen: HtmlLi = HtmlLi.str("Generats the HTML files for the wesbsite, including this file.")

  def cenStr ="""
  |<p>It currently works on JavaFx and web page. Using canvas on both platforms. See <a href="api/index.html">Scala Docs</a> and See
  |  <a href="apiJs/index.html">Scala Docs for JavaScript target.</a>
  |</p>
  |<p>The Strategy games was the original motivation for the project, but the geometry and graphics library have far wider applicability. The geometry
  |  and graphics are far more developed, while the tiling and strategy games are still in a far more experimental stage. This is in accordance with the
  |  original vision, part of which was to explore the possibilities of an Algebra of Tiling. Out of the needs of these 2 primary focuses, 2 secondary
  |  focuses have developed. If you are new to programming then just ignore these for the time being.
  |  <ul>
  |    <li>RSON A Show, 2D-Show and Persistence Library / Framework.</li>
  |    <li>Heapless Efficient functional Array based mapping and compound deep-value type collections library. Join the Not[Boxer] Rebellion.</li>
  |  </ul>
  |</p>
  |
  |	 If you are not experienced with Scala, you have found this site and want to
  |  experiment, you will need to install Java JDK8 and sbt. more complete documentation for getting started on Linux / Windows / Mac will come later.
  |  The basic build has been tested on Linux and Windows 7. Jdk 11 preferred.</p>
  |
  |<p>However at the other end, I would welcome input from developers with greater experience and knowledge than my own. One of the goals of the project
  |  is to explore, where it is best to compose with trait / class inheritance and where to use functions. When to use mutation and when to use
  |  immutability. When to use smart, garbage collected heap based objects and when to use dumb data values. Balancing the competing priorities of
  |  elegance, succinctness, readability, run-time performance, compile time performance and accessibility for inexperienced programmers. I feel Scala
  |  is, and in particular Scala 3 will be the ideal language to explore these questions.</p>
  |
  |<p>Scala currently set to 3.3.0. Jdk 11+, 11 preferred. Scala.Js set to 1.13.2. Scala native set to 0.4.14. Sbt currently set to 1.9.1 (uses the
  | build.sbt file). Note (probably due to the JavaFx dependency). Sbt will not work running on Windows in Git Bash. Update your Mill to 0.10.7.</p>
  |
  |<p>Run <code>sbt</code> in bash from project's root folder</p>
  |
  |<p>From within the sbt console run:
  |  <ul>
  |   <li><code class="sbt">~ Dev/reStart</code> To launch a ScalaFx window. The most useful command for development</li>
  |   <li><code class="sbt">~ DicelessJs/fastOptJS</code> To rebuild a fast optimised JavaScript file. Use with Dev/DevPages/SbtFastDev.html</li>
  |   <li><code class="sbt">DicelessJs/fullOptJS</code> To build a full optimised JavaScript file. Use with Dev/DevPages/SbtFullDev.html</li>
  |   <li><code class="sbt">~ Util/test</code> Rerun tests on Util module.</li>
  |   <li><code class="sbt">~ Tiling/test</code> Rerun tests on Tiling module.</li>
  |   <li><code class="sbt">~ Dev/test</code> Rerun tests on, Dev module.</li>
  |   <li><code class="sbt">~ Util/test; Tiling/test; Dev/test</code> Rerun tests on Util module.</li>
  |   <li><code class="sbt">DocMain/doc</code> Will produce docs for all the main code in all the modules for the Jvm platform. They can be found in
  |     <code class="folder">Dev/SbtDir/DocMain/target/scala-3.3.0/api/</code>
  |    </li>
  |   <li><code class="sbt">DocJs/doc</code> Will produce docs for all the main code in all the modules for the Javascript platform. They can be found
  |      in <code class="folder">Dev/SbtDir/DocJs/target/DocMain/target/scala-3.3.0/api/</code>
  |    </li>
  |    <li><code class="sbt">bothDoc</code> will perform both the above tasks.</li>
  |   </ul>
  |  </p>
  |
  |<p>The tilde <code>~</code> tells sbt to rerun the command every time you modify and save a source file. The first command will build and launch a
  |  ScalaFx window. It will rebuild and relaunch so you can immediately see the effects of your changes. Copy the DevSettings.rson file from the
  |  Dev/Misc folder to the  Dev/User folder. Creating the directory and its parents if not already existing. Change the appStr setting in
  |  DevSettings.rson to change the application. All the examples on the richstrat.com website are available plus others. The second command will also
  |  rebuild on source changes in similar manner. However unlike with the reStart command, when you make a source file edit and save it, you will have
  |  to manually refresh the browser window after the fastOptJS command has finished the rebuild.</p>
  |
  |<p><ul>For IntellliJ useful options:
  |  <li>File => Editor => General -> Other -> tick "Show quick documentation on mouse move".</li>
  |  <li>File => "Build, Execution, Deployment" => Compiler -> "Build project automatically"</li>
  |  <li>Project-Pane => Options -> "Flatten packages"</li>
  |</ul></p>
  |""".stripMargin

  def miscStr: String ="""
      |<p>This is just a place to put various notes, so as stuff doesn't get lost. It can be sorted into proper documentation later.</p>
      |
      |<p>So its been one of my aspirations to reduce if not remove dependence on scala.Any. Any often leads to extra boxing. Single
      |boxing of AnyRef classes but double boxing of primitives such Int, Double and Boolean. I created an Opt class as a replacement
      |for Option and started replacing scala.Either[+A, +B] with a lower head Error Monad replacement. However this creates similar
      |but possibly worse method signature complication than can scala.collection.generic.CanBuildFrom.</p>
      |
      |<p>The Opt class certainly works for the A* pathfinding algorithm where it doesn't into the user interface.</p>
      |
      |<p>So at least recent versions of Kubuntu the java command on the path, is at /usr/bin/java. It is a link to /etc/alternatives/java.
      |This is also a link. To install a different java, install the JDK root folder in usr/lib/jvm. It doesn't have to be here,
      |but it makes it easier to go with convention. Run <br>
      |sudo update-alternatives --config java<br>
      |In my example this gives<br>
      | Selection    Path                                            Priority   Status
      |------------------------------------------------------------
      |* 0            /usr/lib/jvm/java-11-openjdk-amd64/bin/java      1111      auto mode
      |  1            /usr/lib/jvm/java-11-openjdk-amd64/bin/java      1111      manual mode
      |  2            /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java   1081      manual mode
      |So leave the number as it is, then add to alternatives. I put the number 3 at then end because in my case slots 0 to 2 are
      |already taken.<br>
      |sudo update-alternatives --install /usr/bin/java java /usr/lib/jvm/jdk1.8.0_212/bin/java 3<br>
      |then repeat sudo update-alternatives --config java
      |
      |<p>ScalaRx might prove useful. Consider -Xfatal-warnings.</p>
      |<p>Consider extending hexadecimal to 32 values for succinct tile coordinate notation. Uses all the letters except i and o. 2 digits can encode 1024 vales instead of 100. 3 digits can encode 32768 values instead of a thousand.
      |
      |0123456789ABCDEF //Hexadecimal
      |
      |0123456789ABCDEFGHJKLMNPQRSTUVWXYZ</p>
      |
      |<h3>Credits</h3>
      |<ul>
      |<li><a href="https://lampwww.epfl.ch/~doeraene/thesis/">Sébastien Doeraene, Ph.D. thesis</a> for Scala.js</li>
      |  <li><a href="https://www.patreon.com/lihaoyi">Li Haoyi</a> for Mill and uTest.</li>
      |</ul>
      |""".stripMargin
}