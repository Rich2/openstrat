/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package h1p
import prid._, phex._, gPlay._

/** Simple Game manager for [[G1HScen]]. Contains the Scenario and sequence of counters controlled by the single GUI player. */
case class G1HGame(var scen: G1HScen, guiCounters: RArr[Counter])
{ implicit def gridSys: HGridSys = scen.gridSys
  var history: RArr[G1HScen] = RArr(scen)

  /** Resolves turn. Takes an [[HCenStepArr]]. The directives are passed in as relative moves. This is in accordance with the principle in more complex
   *  games that the entity issuing the command may not know its real location. */
  def endTurn(directives: HCenStepArr): G1HScen =
  { val intensions: HCenOptStepLayer = HCenOptStepLayer(scen.gridSys)
    directives.foreach { hcst =>
      val oc: Option[Counter] = scen.counters(hcst.startHC)
      oc.foreach { ct => if (guiCounters.contains(ct)) intensions.setSome(hcst.startHC, hcst.step) }
    }
    val countersNew: HCenOptLayer[Counter] = scen.resolve(intensions)
    val newScen: G1HScen = G1HScen(scen.turn + 1, scen.gridSys, countersNew)
    scen = newScen
    history +%= scen
    scen
  }
}