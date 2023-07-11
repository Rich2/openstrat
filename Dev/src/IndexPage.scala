/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pWeb._

object IndexPage
{
  val head = HtmlHead.titleCss("Openstrat", "only")
  def topMenu: HtmlUl = SubPage.topMenu(SubPage.allPairs)
  def body = HtmlBody.elems(topMenu, XConStr(bodyStr))
  def content = HtmlPage(head, body)

  def iconStrs ="""
  |<p><a href="https://github.com/Rich2/openstrat"><svg xmlns="http://www.w3.org/2000/svg" width="92" height="20">
  |  <linearGradient id="b" x2="0" y2="100%"><stop offset="0" stop-color="#bbb" stop-opacity=".1"/><stop offset="1" stop-opacity=".1"/>
  |  </linearGradient><mask id="a"><rect width="92" height="20" rx="3" fill="#fff"/></mask><g mask="url(#a)"><path fill="#555" d="M0 0h34v20H0z"/><path
  |    fill="#46BC99" d="M34 0h58v20H34z"/><path fill="url(#b)" d="M0 0h92v20H0z"/></g>
  |    <g fill="#fff" text-anchor="middle" font-family="DejaVu Sans,Verdana,Geneva,sans-serif" font-size="11"><text x="17" y="15" fill="#010101"
  |     fill-opacity=".3">code</text><text x="17" y="14">code</text><text x="62" y="15" fill="#010101" fill-opacity=".3">on github</text><
  |     text x="62" y="14">on github</text>
  |   </g>
  |</svg></a>&nbsp;<a href="https://gitter.im/typestrat/Lobby"><svg xmlns="http://www.w3.org/2000/svg" width="92" height="20">
  |  <linearGradient id="b" x2="0" y2="100%"><stop offset="0" stop-color="#bbb" stop-opacity=".1"/><stop offset="1" stop-opacity=".1"/>
  |  </linearGradient><mask id="a"><rect width="92" height="20" rx="3" fill="#fff"/></mask><g mask="url(#a)"><path fill="#555" d="M0 0h34v20H0z"/>
  |    <path fill="#46BC99" d="M34 0h58v20H34z"/><path fill="url(#b)" d="M0 0h92v20H0z"/></g>
  |    <g fill="#fff" text-anchor="middle" font-family="DejaVu Sans,Verdana,Geneva,sans-serif" font-size="11">
  |      <text x="17" y="15" fill="#010101" fill-opacity=".3">chat</text><text x="17" y="14">chat</text>
  |      <text x="62" y="15" fill="#010101" fill-opacity=".3">on gitter</text><text x="62" y="14">on gitter</text>
  |    </g>
  |</svg></a><br /></p>
  |""".stripMargin

  val bodyStr: String = s"""<div class=main>
  |$iconStrs
  |<p>
  |<b>This project has 2 main focuses:
  |<ul>
  |  <li>A functional Geometry and Vector Graphics library with various supporting utilities</li>
  |  <li>A functional strategy game and historical education library, particularly focused on simultaneous-turn, tile-based games.</li>
  |</ul>
  |</b></p>
  |<p>
  |<a href="Documentation/developers.html"><b>Info for developers here.</b></a>
  |</p>
  |<p>This project is intended to be accessible and welcoming to programmers of all levels. Indeed it is intended as a vehicle for complete beginners to
  |  learn programming in a fun environment. To be able to begin by what for most games would be described as modding and then move down into programming
  |  as deep as they wish to go, at the pace they wish to. I want to break down the wall between modding and coding. So if you're new to programming and
  |  want to get involved, drop into the gitter channel and say hi.
  |
  |<p><b>The Code is currently organised into 7 modules.</b> Each module can be build artifacts for Jvm and JavaFx and for the JavaScript platform and
  |  the Web.
  |</p>
  |
  |<ol>
  |<li><a href="Documentation/util.html"><b>Util Module</b></a> organised into the following packages: Organised into the following folders and packages:

  |
  |  <li><a href="Documentation/geom.html"><b>Geom Module</b></a> Depends on Util, organised into the following packages Organised into the following folders and packages:      |
  |  </li>
  |
  |  <li><a href="Documentation/Tiling.html"><b>Tiling Module</b></a> Depends on UtilMacros Util and Graphic, just has the ostrat.pGrid package depends on geom and pCanv.
  |  	<ul>
  |  		<li>Abstract regular tile geometry.</li>
  |        <li>Square and hex tile grid geometry.</li>
  |        <li>OfTile classes for the display of tiles.</li>
  |    </ul>
  |  </li>
  |
  |  <li><a href="Documentation/Earth.html"><b>Earth Module</b></a> This package and module is for Earth maps.
  |    <ul>
  |
  |      <li>ostrat.pEarth depends on geom, pCanv and pGrid
  |        <ul>
  |          <li>Earth and sphere geometry.</li>
  |          <li>Grids of Earth terrain.</li>
  |        </ul>
  |      </li>
  |
  |      <li>ostrat.pEarth.pPts large irregular Earth terrain areas. This is mainly a development aid.</li>
  |
  |    </ul>
  |  </li>
  |
  |<li><a href="Documentation/EGrid.html"><b>EGrid Module</b></a>Tiling of the whole world in Hex grids, defining the changes over the course of history.
  |  This will be a data orientated module. It will also include terrain types to model terrain, both real and imagined for local maps and higher scales
  |  right up to 0.5 metres per tile However it won't generally include the data for these. The data for the real world
  |  will be organised according to a number of levels, which are likely to change over increasingly shorter historical time frames.
  |</li>
  |
  |<li><a href="Documentation/apps.html"><b>Apps Module</b></a></li>
  |
  |<li><a href="Documentation/Dev.html"><b>Dev Module</b></a> The Module as a whole Depends on all the other modules, although its constiurent parts may
  |  not. This module is for the use of developer tools and settings and for end-user applications, that may eventually end up in their own repositaries.
  |  Unlike the other modules this module has no examples sub modules. The eaxmples in the other modules, should be just that example codes to explain,
  |  illustrate provide tutorials, and to some extent test the modules core code. The examples should not include apps that have nay use in and of
  |  themsleves. Those apps belong in the Dev module.
  |  <ul>
  |    <li>User folder contains developer settings</li>
  |    <li>Developer html pages, linked to sbt target and Mill out folder artifacts.</li>
  |    <li>Documentation web pages.</li>
  |    <li> Collates the lessons in the examples folders from Util, Graphic, World and Strat. This is a number of series of lessons for beginners to
  |     Scala, complete beginners to programming and beginners in geometry, using the graphical API. These lessons are published separately as the
  |     LearnScala project.
  |    </li>
  |
  |  </ul>
  | </li>
  |</ol>
  |
  |<p>The code is organised so if it gains significant traction with other developers, then it can be broken up into separate repositories.
  |<ul>
  |<li><a href="Documentation/GitCommands.html">Useful Git commands</a></li>
  |<li><a href="Documentation/Miscellaneous.html">Miscellaneous notes</a></li>
  |</ul>
  |</p>
  |</div>
  |""".stripMargin
}