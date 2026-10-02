/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package strat
import geom.*

trait StatePolity
{
  def startDate: TimeDay
}

class StatePolityCurr(val startDate: TimeDay) extends StatePolity

class StatePolityHist(val startDate: TimeDay, endDate: TimeDay) extends StatePolity

object GermanyPru extends StatePolityHist(TimeDay(1525, 4, 10), TimeDay(1945, 5, 23))