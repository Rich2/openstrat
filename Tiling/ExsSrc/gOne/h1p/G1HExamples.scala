/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package h1p
import prid._, phex._, gPlay._

/** 1st example Turn 0 scenario state for Game One hex. */
object G1HScen1 extends G1HScen
{ override def turn: Int = 0
  implicit val gridSys: HGridReg = GSys.g1
  val counters: HCenOptLayer[Counter] = HCenOptLayer()
  counters.setSomeMut(4, 4, CounterA)
  counters.setSomesMut((4, 8, CounterB), (6, 10, CounterC))
}

/** 2nd example Turn 0 scenario state for Game One hex. */
object G1HScen2 extends G1HScen
{ override def turn: Int = 0
  implicit val gridSys: HGridReg = GSys.g2
  val counters: HCenOptLayer[Counter] = HCenOptLayer()
  counters.setSomeMut(6, 38, CounterA)
  counters.setSomesMut((4, 40, CounterB), (6, 42, CounterC), (6, 50, CounterD), (10, 34, CounterE))
}

/** 3rd example Turn 0 scenario state for Game One hex. */
object G1HScen3 extends G1HScen
{ override def turn: Int = 0
  implicit val gridSys: HGridIrr = GSys.g3
  val counters: HCenOptLayer[Counter] = HCenOptLayer()
  counters.setSomesMut((4, 4, CounterA), (10, 6, CounterB), (8, 8, CounterC))
}

/** 3rd example Turn 0 scenario state for Game One. */
object G1HScen7 extends G1HScen
{ override def turn: Int = 0
  implicit val gridSys: HGrid = HGridIrr(10, (1, 6), (2, 4), (3, 2), (2, 4), (1, 6))
  val counters: HCenOptLayer[Counter] = HCenOptLayer()
  counters.setSomesMut((4, 4, CounterA), (10, 6, CounterB), (8, 8, CounterC))
}

/** 2nd example Turn 0 scenario state for Game One. */
object G1HScen4 extends G1HScen
{ override def turn: Int = 0
  implicit val gridSys: HGridReg = HGridReg(2, 10, 4, 8)
  val counters: HCenOptLayer[Counter] = HCenOptLayer()
  counters.setSomesMut((4, 4, CounterA), (8, 4, CounterB), (6, 6, CounterC))
}

/** 8th example Turn 0 scenario state for Game One. An empty regular grid containing no hex tiles. */
object G1HScen8 extends G1HScen
{ override def turn: Int = 0
  implicit val gridSys: HGridReg = HGridReg(4, 2, 4, 2)
  val counters: HCenOptLayer[Counter] = HCenOptLayer()
}

/** 9th example Turn 0 scenario state for Game One. An empty irregular grid containing no hex tiles. */
object G1HScen9 extends G1HScen
{ override def turn: Int = 0
  implicit val gridSys: HGrid = HGridIrr(2)
  val counters: HCenOptLayer[Counter] = HCenOptLayer()
}

/** 10th example Turn 0 scenario state for Game One. Uses an [[HGridIrr]] */
object G1HScen10 extends G1HScen
{ override def turn: Int = 0
  implicit val gridSys: HGrid = HGridIrr(12, (12, 4), (4, 6), (1, 8), (4, 2), (2, 4), (1, 6))
  val counters: HCenOptLayer[Counter] = HCenOptLayer()
  counters.setSomesMut((4, 4, CounterA), (10, 6, CounterB), (8, 8, CounterC))
}