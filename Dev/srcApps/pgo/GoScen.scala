/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pgo
import prid._, psq._, Colour._, pgui._

sealed trait Stone
{ def colour: Colour
}

object BStone extends Stone
{ override def colour: Colour = Black
}

object WStone extends Stone
{ override def colour: Colour = White
}

class GoScen(val grid: SqGrid, val stones: SqCenOptLayer[Stone])

object GoScen
{
  def apply() =
  { val grid = SqGrid(2, 38, 2, 38)
    new GoScen(grid, grid.newSCenOptDGrider[Stone])
  }
}

object GoLaunch extends GuiLaunchSimple("Go", (cp => GoGui(cp, GoScen()), "Go Gui"))