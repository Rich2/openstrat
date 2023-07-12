/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pWeb._

/** The top level HTML documentation page for the apps. */
object AppsDoc extends HtmlPage
{
  override def head: HtmlHead = HtmlHead.titleCss("Applications Module", "https://richstrat.com/Documentation/documentation")
  override def body: HtmlBody = HtmlBody(HtmlH1("Apps Module"), main)
  def main: HtmlDiv = HtmlDiv.classAtt("main", list, XConStr(bodyStr))

  def list: HtmlOl = HtmlOl(RArr(uloc, HtmlH2("Strategy Games using tiled world maps."), dless, ww2, bc305, y1783, HtmlH2("Other Tiled Map Applications."),
    zug, dung, HtmlH2("Other Applications."), planets))

  val uloc = HtmlLi.linkAndText("../unitlocapp.html", "Unit Locator", "Locates military units and gives information for a given date and time.")

  val dless = HtmlLi.linkAndText("../dicelessapp.html", "DiceLess",
    "A simple simultaneous turn multi player game set in Europe in 1900. As the name suggests no random element.")
  
  val ww2: HtmlLi = HtmlLi.linkAndText("../ww2.html", "World War II Game", "This was chosen as the third priority application as it seemed like" --
    "the best choice for a game that could use the 320km scale tiled world map.")

  val bc305: HtmlLi = HtmlLi.linkAndText("../bc305.html", "BC305", "A grand strategy game with a start point of 305BC. It uses an 80km scale world"
    -- "map. This is the game that most interests me.")

  val y1783: HtmlLi = HtmlLi.linkAndText("../y1783.html", "AD1783", "A grand strategy game with a start point of 1783. It also uses an 80km scale world"
    -- "map. This is the second game that most interests me.")

  val zug: HtmlLi = HtmlLi.linkAndText("../zug.html", "Zug Fuhrer", "A Tactical strategy game with a 20 metre hex scale.")
  val dung: HtmlLi = HtmlLi.linkAndText("../dungeon.html", "Dungeon Game", "A Tactical strategy game on square tiles with a 0.5 metre tile scale.")

  val planets = HtmlLi.linkAndText("../planets.html", "Planets", "Mostly knocked togethor quickly some time back. I've included it next just because its different.")

  def bodyStr: String =
    """
      |<li>A number of rudimentary games and applications depending on some or all of the above packages. The intention is to factor out common
      |      functionality and classes.
      |      <ul>
      |        <li>ostrat.pGames.pCiv A human history 4x development game using hex tiles.</li>
      |      </ul>
      |    </li>
      |
      |      <li>ostrat.pStrat depends on geom, pCanv and pGrid and pEarth.
      |        <ul>
      |          <li>Flags</li>
      |          <li>DemoCanvas for graphics elements.</li>
      |          <li>Odds and ends.</li>
      |        </ul>
      |      </li>
      |
      |      <li>ostrat.pCloseOrder. Pre modern close order formation based battles, not using tiles.</li>
      |
      |      <li>ostratpChess. A search for an elegant implementation of Draughts and Chess.</li>
      |
      |    </div>
      |""".stripMargin
}
