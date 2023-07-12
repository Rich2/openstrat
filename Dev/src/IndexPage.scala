/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pWeb._

/** The home page for the project, currently displayed at richstrat.com. */
object IndexPage
{ val head = HtmlHead.titleCss("Openstrat", "Documentation/documentation")
  def topMenu: HtmlUl = appPage.topMenu(appPage.allPairs)
  def body = HtmlBody(topMenu, main)
  def content = HtmlPage(head, body)

  def main: HtmlDiv = HtmlDiv.classAtt("main", XConStr(iconStrs), XConStr(mainStr))

  val mainStr: String = s"""<p>
  |<b>This project has 3 main focuses:
  |<ul>
  |  <li>Historical strategy games, particularly focused on simultaneous-turn, tile-based games.</li>
  |  <li>Graphics for the display of historical information.</li>
  |  <li>A functional Geometry and Vector Graphics library with various supporting utilities</li>
  |</ul>
  |</b></p>
  |<p>
  |<a href="Documentation/developers.html"><b>Info for developers here.</b></a>
  |</p>
  |<p>This project is intended to be accessible and welcoming to programmers of all levels. Indeed it is intended as a vehicle for complete beginners to
  |  learn programming in a fun environment. To be able to begin by what for most games would be described as modding and then move down into programming
  |  as deep as they wish to go, at the pace they wish to.
  |
  |<p><b>The Code is currently organised into 7 modules.</b> Each module can be build artifacts for Jvm and JavaFx and for the JavaScript platform and
  |  the Web.
  |</p>
  |
  |<ol>
  |  <li><a href="Documentation/util.html"><b>Util Module</b></a> Contains a number of utilities. This includes a parsing and persistence system.
  |
  |  <li><a href="Documentation/geom.html"><b>Geom Module</b></a> Depends on Util. Basic 2D and 3D geometry, functional Graphics.</li>
  |
  |  <li><a href="Documentation/Tiling.html"><b>Tiling Module</b></a> Depends on Util and Geom modules. Encodes tile Grids, both square and hexagonal,
  |   as well as systems of grids.</li>
  |
  |  <li><a href="Documentation/Earth.html"><b>Earth Module</b></a> This module is for Earth maps. Allows the maniupation of latitude and longitude
  |    allowing free conversion betwen them and 2D and 3D coordinates.</li>
  |
  |  <li><a href="Documentation/EGrid.html"><b>EGrid Module</b></a>Tiling of the whole world in Hex grids, defining the changes over the course of history.
  |    This will be a data orientated module. It will also include terrain types to model terrain, both real and imagined for local maps and higher scales
  |    right up to 0.5 metres per tile However it won't generally include the data for these. The data for the real world
  |    will be organised according to a number of levels, which are likely to change over increasingly shorter historical time frames.
  |  </li>
  |
  |<li><a href="Documentation/apps.html"><b>Apps Module</b></a> This module for end-user applications, that may eventually end up in their own
  | repositories.</li>
  |
  |<li><a href="Documentation/Dev.html"><b>Dev Module</b></a> Depends on all the other modules. This module is for the use of developer tools and
  | settings and   |  illustrate provide tutorials, and to some extent test the modules core code.</li>
  |</ol>
  |
  |<ul>
  |<li><a href="Documentation/GitCommands.html">Useful Git commands</a></li>
  |<li><a href="Documentation/Miscellaneous.html">Miscellaneous notes</a></li>
  |</ul>
  |""".stripMargin

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

}