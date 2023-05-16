/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package sp1
import prid._, psq._, gPlay._

object G1SScen1 extends G1SqScenStart
{ implicit val gridSys: SqGrid = SqGrid(2, 6, 2, 8)
  val counters: SqCenOptLayer[Counter] = gridSys.newSCenOptDGrider
  counters.unsafeSetSome(4, 4, CounterA)
  counters.unsafeSetSomes((4, 6, CounterB), (6, 8, CounterC))
}

object G1SScen2 extends G1SqScenStart
{ implicit val gridSys: SqGrid = SqGrid(2, 14, 2, 22)
  val counters: SqCenOptLayer[Counter] = gridSys.newSCenOptDGrider
  counters.unsafeSetSome(14, 18, CounterA)
  counters.unsafeSetSomes((10, 18, CounterB), (10, 22, CounterC), (12, 22, CounterD))
}

object G1SScen3 extends G1SqScenStart
{ implicit val gridSys: SqGrid = SqGrid(2, 32, 2, 40)
  val counters: SqCenOptLayer[Counter] = gridSys.newSCenOptDGrider
  counters.unsafeSetSome(4, 4, CounterA)
  counters.unsafeSetSomes((4, 6, CounterB), (6, 8, CounterC))
}