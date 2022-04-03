/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pgo
import prid._, psq._, Colour._

sealed trait Stone
{ def colour: Colour
}

object BStone extends Stone
{ override def colour: Colour = Black
}

object WStone extends Stone
{ override def colour: Colour = White
}

class GoScen
{
  def grid: SqGrid = SqGrid(2, 38, 2, 38)
  def stones: SqCenOptDGrid[Stone] = grid.newSCenOptDGrider[Stone]
}