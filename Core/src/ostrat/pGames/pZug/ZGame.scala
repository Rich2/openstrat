/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pZug
import pGrid._

case class ZGame(scen: ZugGrid)
{
  var turns: List[ZTurn] = Nil
}

case class ZMove(squad: Squad, coods: Coods)

case class ZTurn(moves: List[ZMove])
{
  
}