/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pZug
import pGrid._

class ZGameOld(scenInit: ZugGridOld, val players: Refs[ZPlayer])
{
  private [this] var scen: ZugGridOld = scenInit
  def getScen(player: ZPlayer): ZugGridOld =
  {
    val newScen = scen.minCopy
    scen.foreachTilesCoodAll{cood =>
      val oldTile = scen.getTile(cood)
      val newUnits = oldTile.lunits.filter(oldUnit => player.polities.contains(oldUnit.polity) | !oldTile.terr.conceal)
      val newTile = oldTile.copy(lunits = newUnits)
      newScen.setTile(cood, newTile)

    }
    scen.foreachSidesCoodAll {cood => newScen.setSide(cood, scen.getSide(cood)) }
    newScen
  }
  def makeMove(id: Int, coods: Coods): ZugGridOld = scen
}

case class ZPlayer(polities: Refs[Polity])

object PlayBritain extends ZPlayer (Refs(Britain))
object PlayGermany extends ZPlayer (Refs(Germany))
object PlayGermanyBritain extends ZPlayer (Refs(Germany, Britain))
object PlayGermanyFrance extends ZPlayer (Refs(Germany, France))

case class ZMove(squad: SquadOld, coods: Coods)

case class ZTurn(moves: Refs[ZMove])