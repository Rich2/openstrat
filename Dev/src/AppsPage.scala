/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pWeb._

/** The top level HTML documentation page for the apps. */
object AppsPage extends HtmlPage
{
  override def head: HtmlHead = HtmlHead.titleCss("Applications Module", "https://richstrat.com/Documentation/documentation")
  override def body: HtmlBody = HtmlBody(HtmlH1("Apps Module"), main)
  def main: HtmlDiv = HtmlDiv.classAtt("main", list)

  def list: HtmlOl = HtmlOl(RArr(uloc, HtmlH2("Strategy Games using tiled world maps."), dless, peri, ww2, bc305, ww1, sors, y1492, y1783,
    HtmlH2("Other Tiled Map Applications."), zug, dung, civ, HtmlH2("Other Applications."), geomTut, planets, flags, chess))

  val uloc = HtmlLi.linkAndText("../unitlocapp.html", "Unit Locator", "Locates military units and gives information for a given date and time.")

  val dless = HtmlLi.linkAndText("../dicelessapp.html", "DiceLess",
    "A simple simultaneous turn multi player game set in Europe in 1900. As the name suggests no random element.")

  val peri = HtmlLi.linkAndText("../periculoapp.html", "Periculo Fundatuso",
    "A simple consecutive turn, world map game that has some grounding in earth geography.")
  
  val ww2: HtmlLi = HtmlLi.linkAndText("../ww2.html", "World War II Game", "This was going to be the third priority application, but it was" ---
    "decided to use the 220km scale tiled world map, as this scale is required to resolve Belgium and the Netherlands as separate countries.")

  val bc305: HtmlLi = HtmlLi.linkAndText("../bc305.html", "BC305", "A grand strategy game with a start point of 305BC. It uses an 80km scale world"
    -- "map. This is the game that most interests me.")

  val ww1: HtmlLi = HtmlLi.linkAndText("../ww1.html", "World War I Game", "A 120km hex game.")

  val sors = HtmlLi.linkAndText("../sors.html", "Sors Imperiorum", "A game where empires appear at set times according to history. Uses 220km hex scale")

  val y1492: HtmlLi = HtmlLi.linkAndText("../y1492.html", "AD1492", "A grand strategy game with a start point of 1492. It also uses an 160km scale world map.")

  val y1783: HtmlLi = HtmlLi.linkAndText("../y1783.html", "AD1783", "A grand strategy game with a start point of 1783. It also uses an 80km scale world"
    -- "map. This is the second game that most interests me.")

  val zug: HtmlLi = HtmlLi.linkAndText("../zug.html", "Zug Fuhrer", "A Tactical strategy game with a 20 metre hex scale.")
  val dung: HtmlLi = HtmlLi.linkAndText("../dungeon.html", "Dungeon Game", "A Tactical strategy game on square tiles with a 0.5 metre tile scale.")

  val civ: HtmlLi = HtmlLi.linkAndText("../civrise.html", "Civ Rise", "A 4X strategy game using hexs. Its main use so far has been to develop a" --
    "generalised side terrain")

  val geomTut: HtmlLi = HtmlLi("Geometry and Graphics Tutorials")

  val gameTut: HtmlLi = HtmlLi("Simultaneous turn, tile based tutorial games.")

  val planets: HtmlLi = HtmlLi.linkAndText("../planets.html", "Planets", "Mostly knocked togethor quickly some time back. I've included it next just" --
    "because its different.")

  val flags: HtmlLi = HtmlLi.linkAndText("../flags.html", "Flags", "Just some flags using the graphics module. Thanks to Rod and Stephen who did" --
    "most of the work on this.")

  val chess: HtmlLi = HtmlLi.linkAndText("../chess.html", "Chess", "Not completed.")
}