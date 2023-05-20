/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo; package s2p
import prid._, psq._, gPlay._

object G2SScen1 extends G2SqScenStart
{ implicit val gridSys: SqGrid = SqGrid(2, 6, 2, 8)
  val counterStates: SqCenOptLayer[CounterState] = gridSys.newSCenOptDGrider
//  counters.unsafeSetSome(4, 4, CounterA)
  //counters.unsafeSetSomes((4, 6, CounterB), (6, 8, CounterC))
}

object G2SScen2 extends G2SqScenStart
{ implicit val gridSys: SqGrid = SqGrid(2, 14, 2, 22)
  val counterStates: SqCenOptLayer[CounterState] = gridSys.newSCenOptDGrider
  //counters.unsafeSetSome(14, 18, CounterA)
  //counters.unsafeSetSomes((10, 18, CounterB), (10, 22, CounterC), (12, 22, CounterD), (6, 6, CounterE))
}

object G2SScen3 extends G2SqScenStart
{ implicit val gridSys: SqGrid = SqGrid(2, 32, 2, 40)
  val counterStates: SqCenOptLayer[CounterState] = gridSys.newSCenOptDGrider
  //counters.unsafeSetSome(4, 4, CounterA)
 // counters.unsafeSetSomes((4, 6, CounterB), (6, 8, CounterC))
}