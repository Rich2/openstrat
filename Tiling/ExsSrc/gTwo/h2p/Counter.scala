/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo; package h2p
import prid._, phex._, gPlay._

/** The [[Counter]] and its intentions. I'm thinking that a game entity and its state should generally be stored in the same layer. */
case class CounterState(counter: Counter, steps: HStepArr)
{ /** Returns the [[CounterState]] with the tail of the steps." */
  def takeStep: CounterState = CounterState(counter, steps.tail)
}

object CounterState
{ def apply(counter: Counter, steps: HStep*): CounterState = CounterState(counter, HStepArr(steps: _*))
}

/** Class may not be needed. A class identifying a [[Counter]] and an [[HCen]] hex coordinate position. */
case class HCounter(hc: HCen, value: Counter) extends HexMemShow[Counter]
{ override def typeStr: String = "HCounter"
  override def name2: String = "counter"
  override implicit def show2: Show[Counter] = Counter.showTEv
  override def syntaxDepth: Int = 2
}