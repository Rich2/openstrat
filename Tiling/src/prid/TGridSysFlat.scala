/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._

/** A system of tile grids using a flat 2D geometry. This includes all single tile grids which are considered flat and some systems of multiple tile grids. */
trait TGridSysFlat extends Any with TGridSys
{

  def fullDisplayScale(dispWidth: Double, dispHeight: Double, padding: Double = 20): Double =
  {
    def adj(inp : Double): Double = inp match
    { case n if n > 1000 => inp - padding
      case n if n > 500 => inp - padding * inp / 1000.0
      case n if n > 10 => n
      case _ => 10
    }
    (adj(dispWidth) / adj(width).max(1)).min(adj(dispHeight) / height.max(1))
  }
}