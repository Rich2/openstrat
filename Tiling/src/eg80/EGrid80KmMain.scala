/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import egrid._

trait EGrid80Km extends EGrid
{
  /** The scale of the c or column coordinate in metres. */
  val cScale: Length = 20000.metres * Sqrt3
}

/** A main non-polar gird with a hex pan of 80Km */
trait EGrid80KmMain extends EGridMain with EGrid80Km
{
  override def rOffset: Int = 300
}