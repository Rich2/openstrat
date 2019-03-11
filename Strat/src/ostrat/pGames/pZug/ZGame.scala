/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pZug
import pGrid._

class ZGame(scenInit: ZugGrid, val players: List[ZPlayer])
{
  private [this] var scen: ZugGrid = scenInit
  def getScen(player: ZPlayer): ZugGrid =
  {
    val newScen = scen.minCopy
    scen.foreachTileCood{cood =>
      val oldTile = scen.getTile(cood)
      val newUnits = oldTile.lunits.filter(oldUnit => player.polities.contains(oldUnit.polity) | !oldTile.terr.conceal)
      val newTile = oldTile.copy(lunits = newUnits)
      newScen.setTile(cood, newTile)
      
    }
    scen.sideCoodForeach{cood => newScen.setSide(cood, scen.getSide(cood)) }
    newScen
  }
  def makeMove(id: Int, coods: Coods): ZugGrid = scen
}

object ZGame1 extends ZGame(Zug1, List(PlayBritain, PlayGermany))
object ZGame2 extends ZGame(Zug2, PlayGermanyFrance :: Nil)

case class ZPlayer(polities: List[Polity])

object PlayBritain extends ZPlayer (List(Britain))
object PlayGermany extends ZPlayer (List(Germany))
object PlayGermanyBritain extends ZPlayer (List(Germany, Britain))
object PlayGermanyFrance extends ZPlayer (List(Germany, France))


case class ZMove(squad: Squad, coods: Coods)

case class ZTurn(moves: List[ZMove])
{
  
}