/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package sp1
import prid._, psq._, gPlay._

case class G1SGame(var scen: G1SqScen, guiPlayers: RArr[Player])
{
  implicit val gridSys: SqGridSys = scen.gridSys

  def endTurn(orders: SqCenStepPairArr[Player]): G1SqScen =
  { val res1 = SqCenOptStepLayer[Player]()
    orders.pairForeach { (hcst, pl) => res1.setSome(hcst.startSC, hcst.step, pl) }
    val playersNew = scen.resolve(res1)
    G1SqScen(scen.turn + 1, gridSys, playersNew)
  }
}