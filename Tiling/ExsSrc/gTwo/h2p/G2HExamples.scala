/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo; package h2p
import prid._, phex._, gPlay._

/** 1st example Turn 0 scenario state for Game Three. */
object G2HScen1 extends G2HScen
{ override def turn: Int = 0
  implicit val gridSys: HGridRect = HGridRect.minMax(2, 6, 2, 16)
  val counterStates: LayerHcOptSys[CounterState] = LayerHcOptSys()
  counterStates.setSomeMut(6, 2, CounterState(CounterA, HexDR, HexDL))
  counterStates.setSomeMut(4, 16, CounterState(CounterD, HexDL, HexLt))
  counterStates.setSomesMut((4, 8, CounterState(CounterB, HexDR)), (6, 10, CounterState(CounterC, HexDL, HexDL)))
}

/** 2nd example Turn 0 scenario state for Game Three. */
object G2HScen2 extends G2HScen
{ override def turn: Int = 0
  implicit val gridSys: HGridRect = HGridRect.minMax(2, 10, 4, 8)
  val counterStates: LayerHcOptSys[CounterState] = LayerHcOptSys()
  counterStates.setSomesMut((4, 4, CounterState(CounterA)), (8, 4, CounterState(CounterB)), (6, 6, CounterState(CounterC)))
}

/** 3rd example Turn 0 scenario state for Game Three. */
object G2HScen3 extends G2HScen
{ override def turn: Int = 0
  implicit val gridSys: HGrid = HGridGen.fromTop(10, (6, 6), (4, 8), (2, 10), (4, 8), (6, 6))
  val counterStates: LayerHcOptSys[CounterState] = LayerHcOptSys()
  counterStates.setSomesMut((4, 4, CounterState(CounterA)), (10, 6, CounterState(CounterB)), (8, 8, CounterState(CounterC)))
}

object G2HScen4 extends G2HScen
{ override def turn: Int = 0
  implicit val gridSys: HGridRect = HGridRect.minMax(2, 12, 2, 60)
  val counterStates: LayerHcOptSys[CounterState] = LayerHcOptSys()
  counterStates.setSomeMut(4, 4, CounterState(CounterA))
  counterStates.setSomesMut((4, 8, CounterState(CounterB)), (6, 10, CounterState(CounterC)), (8, 32, CounterState(CounterD)))
}