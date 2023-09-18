/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package dless
import geom._, prid._, phex._, pgui._, egrid._

case class DLessGame(var scen: DLessScen, guiNations: RArr[Nation])
{ implicit def gridSys: EGridSys = scen.gridSys
  var history: RArr[DLessScen] = RArr(scen)

  def endTurn(directives: HCenStepPairArr[Army]): DLessScen ={

    scen.resolve(directives)
  }
}