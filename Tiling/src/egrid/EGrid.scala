/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import prid._

/** A hex grid on the surface of the earth. */
trait EGrid extends HGridIrr
{
  /** The scale of the c or column coordinate in metres. */
  val cScale: Length
}

/** One of the main hex grids for the earth not a polar grid. */
trait EGridMain extends EGrid
{ /** The row offset for the longitude centre */
  def rOffset: Int

  /** The c offset for the Equator */
  def cOffset: Int
}