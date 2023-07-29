/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pWeb._

/** The home page for the project, currently displayed at richstrat.com. */
object IndexPage extends HtmlPage
{ val head = HtmlHead.titleCss("Openstrat", "Documentation/documentation")
  def topMenu: HtmlUl = AppPage.topMenu(AppPage.allTopPairs)
  def body: HtmlBody = HtmlBody(topMenu, central)

  def central: HtmlDiv = HtmlDiv.classAtt("main", iconsHtml, focus, devInfo, HtmlBr, begInfo, menu)

  def focus: HtmlOlWithLH = HtmlOlWithLH.h2("This project has 3 main focuses", strat, hist, geom1)
  def strat: HtmlLi = HtmlLi("Historical strategy games, particularly focused on simultaneous-turn, tile-based games.")
  def hist: HtmlLi = HtmlLi("Graphics for the display of historical information.")
  def geom1: HtmlLi = HtmlLi("A functional Geometry and Vector Graphics library with various supporting utilities.")

  def devInfo = HtmlA("Documentation/dev.html", "Info for developers here.")
  def begInfo = HtmlA("Documentation/newdevs.html", "Info for new developers here.")

  def menu: HtmlOlWithLH = HtmlOlWithLH(intro.xCon, util, geom, tile, earth, egrid, apps, dev)

  def intro: String = "<b>The Code is currently organised into 7 modules.</b> Each module can build artifacts for Jvm and JavaFx and for the" +
    " JavaScript platform and the Web."

  def util: HtmlLi = HtmlLi.linkAndText("Documentation/util.html", "Util", "Contains a number of utilities. This includes a parsing and persistence" +
    " system.")

  def geom: HtmlLi = HtmlLi.linkAndText("Documentation/geom.html", "Geom Module", "Depends on Util. Basic 2D and 3D geometry, functional Graphics.")

  def tile: HtmlLi = HtmlLi.linkAndText("Documentation/tiling.html", "Tiling Module", "Depends on Util and Geom modules. Encodes tile Grids, both" +
    " square and hexagonal, as well as systems of grids.")

  def earth: HtmlLi = HtmlLi.linkAndText("Documentation/earth.html", "Earth Module", "This module is for Earth maps. Allows the manipulation of" +
    " latitude and longitude allowing free conversion between them and 2D and 3D coordinates.")

  def egrid: HtmlLi = HtmlLi.linkAndText("Documentation/egrid.html", "EGrid Module", "Tiling of the whole world in Hex grids, defining the changes" +
    " over the course of history. This will be a data orientated module. It will also include terrain types to model terrain, both real and imagined" +
    " for local maps and higher scales right up to 0.5 metres per tile However it won't generally include the data for these. The data for the real" +
    " world will be organised according to a number of levels, which are likely to change over increasingly shorter historical time frames.")

  def apps: HtmlLi = HtmlLi.linkAndText("Documentation/apps.html", "Apps Module", "This module for end-user applications, that may eventually end up" +
    " in their own repositories")

  def dev: HtmlLi = HtmlLi.linkAndText("Documentation/dev.html", "Dev Module", "Depends on all the other modules. This module is for the use of" +
    " developer tools and settings and illustrate provide tutorials, and to some extent test the modules core code.")

  def iconsHtml: XmlAsString ="""
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
      |""".stripMargin.xmlAsString
}
