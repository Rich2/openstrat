/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pWeb._, AppPage.{ egameDir, otDir }

/** The top level HTML documentation page for the apps. */
object AppsPage extends HtmlPage
{ override def head: HtmlHead = HtmlHead.titleCss("Applications Module", "https://richstrat.com/Documentation/documentation")
  override def body: HtmlBody = HtmlBody(HtmlH1("Apps Module"), main)
  def main: HtmlDiv = HtmlDiv.classAtt("main", stratList, otherTiled, otherApps)

  def stratList: HtmlOlWithLH = HtmlOlWithLH(HtmlH2("Strategy Games using tiled world maps."),
    HtmlLi.linkAndText(egameDir + "dicelessapp.html", "DiceLess", "A simple simultaneous turn multi player game set in Europe in 1900. As the name suggests"
      -- "no random element."),
    HtmlLi.linkAndText(egameDir + "periculoapp.html", "Periculo Fundatuso", "A simple consecutive turn, world map game that has some grounding in earth" --
      "geography."),
    HtmlLi.linkAndText(egameDir + "ww2app.html", "World War II Game", "Using 460km scale."),

    HtmlLi.linkAndText(egameDir + "bc305app.html", "BC305", "A grand strategy game with a start point of 305BC. Its currently set on 80km hex scale, but I" --
      "think it would be better to start with 320km or 220km. Not sure which yet. This is the game that most interests me."),

    HtmlLi.linkAndText(egameDir + "ww1app.html", "World War I Game", "A 120km hex game."),
    HtmlLi.linkAndText(egameDir + "sorsapp.html", "Sors Imperiorum", "A game where empires appear at set times according to history. Uses 220km hex scale"),

    HtmlLi.linkAndText(egameDir + "discovapp.html", "Age of Discovery",
      "A grand strategy game with a start point of 1492. It also uses an 160km scale world map."),

    HtmlLi.linkAndText(egameDir + "indrevapp.html", "IndRev", "A grand strategy game with a start point of 1783. It also uses a 220km scale worldmap. This" --
      "is the second game that most interests me."),
  )

  def otherTiled: HtmlOlWithLH = HtmlOlWithLH(HtmlH2("Other Tiled Map Applications."),
    HtmlLi.linkAndText(otDir + "unitlocapp.html", "Unit Locator", "Locates military units and gives information for a given date and time."),
    HtmlLi.linkAndText(otDir + "zugapp.html", "Zug Fuhrer", "A Tactical strategy game with a 20 metre hex scale."),
    HtmlLi.linkAndText(otDir + "dungeonapp.html", "Dungeon Game", "A Tactical strategy game on square tiles with a 0.5 metre tile scale."),
    HtmlLi.linkAndText(otDir + "civriseapp.html", "Civ Rise", "A 4X strategy game using hexs. Its main use so far has been to develop a" --
      "generalised side terrain")
  )

  def otherApps: HtmlOlWithLH = HtmlOlWithLH(HtmlH2("Other Apps"),
    HtmlLi("Geometry and Graphics Tutorials"),
    HtmlLi.linkAndText(otDir + "planets.html", "Planets", "Mostly knocked togethor quickly some time back. I've included it next just because its different."),
    HtmlLi("Simultaneous turn, tile based tutorial games."),
    HtmlLi.linkAndText("/flags.html", "Flags", "Just some flags using the graphics module. Thanks to Rod and Stephen who did most of the work on this."),
    HtmlLi.linkAndText(otDir + "chessapp.html", "Chess", "Not completed."))
}