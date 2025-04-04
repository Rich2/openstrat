/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait LengthBased extends Any
{
  /** The factor to convert the [[Length]] units of this class to metres. */
  def theseToMetres: Double
}

trait MetresBased extends Any, LengthBased
{
  override def theseToMetres: Double = 1
}