/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package sp1
import prid._, psq._, gPlay._

/** Simple Game manager for [[G2HScen]]. Contains the Scenario and sequence of counters controlled by the single GUI player */
case class G1SGame(var scen: G1SScen, guiCounters: RArr[Counter])
{
  implicit val gridSys: SqGridSys = scen.gridSys

  def endTurn(directives: SqCenStepPairArr[Counter]): G1SScen =
  { val res1 = SqCenOptStepLayer[Counter]()
    directives.pairForeach { (hcst, pl) =>if (guiCounters.contains(pl)) res1.setSome(hcst.startSC, hcst.step, pl) }
    val countersNew = scen.resolve(res1)
    val newScen: G1SScen = G1SScen(scen.turn + 1, gridSys, countersNew)
    scen = newScen
    newScen
  }
}