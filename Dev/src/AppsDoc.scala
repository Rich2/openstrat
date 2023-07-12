/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pWeb._

/** The top level HTML documentation page for the apps. */
object AppsDoc extends HtmlPage
{
  override def head: HtmlHead = HtmlHead.titleCss("Applications Module", "https://richstrat.com/Documentation/documentation")
  override def body: HtmlBody = HtmlBody(HtmlH1("Apps Module"), main)
  def main: HtmlDiv = HtmlDiv.classAtt("main", list, XConStr(bodyStr))
  def list = HtmlOl(RArr(uloc, dless))
  val uloc = HtmlLi.linkAndText("../unitlocapp.html", "Unit Locator", "Locates military units and gives information for a given date and time.")
  val dless = HtmlLi.linkAndText("../dicelessapp.html", "DiceLess",
    "A simple simultaneous turn multi player game set in Europe in 1900. As the name suggests no random element.")

  def bodyStr: String =
    """
      |<li>A number of rudimentary games and applications depending on some or all of the above packages. The intention is to factor out common
      |      functionality and classes.
      |      <ul>
      |        <li>ostrat.pWW2 A grand strategy world War 2 game, using the hex tiled world terrain map.</li>
      |        <li>ostrat.p1783 A grand strategy game, also using the world map starting in 1783.</li>
      |        <li>ostrat.p305 A grand strategy game set in 305BC, using part of the world map.</li>
      |        <li>ostrat.pZug A 20th century squad based strategy game using hex tiles.</li>
      |        <li>ostrat.pGames.pCiv A human history 4x development game using hex tiles.</li>
      |        <li>ostrat.pGames.pDung A Square tile based dungeon game.</li>
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
      |      <li>ostrat.pSpace A solar system app.</li>
      |
      |      <li>ostratpChess. A search for an elegant implementation of Draughts and Chess.</li>
      |
      |    </div>
      |""".stripMargin
}
