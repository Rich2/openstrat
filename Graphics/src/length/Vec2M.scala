/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A 2 dimensional vector specified in metres as units rather than pure scalar numbers. */
final class Vec2M private (val xMetresNum: Double, val yMetresNum: Double) extends Show2Dbls
{
  /** the name of the 1st element of this 2 element product. */
  override def name1: String = "x"

  /** the name of the 2nd element of this 2 element product. */
  override def name2: String = "y"

  /** Element 1 of this Show 2 element product. */
  override def show1: Double = ???

  /** Element 2 of this Show 2 element product. */
  override def show2: Double = ???

  /** the name of the type of this object. */
  override def typeStr: String = "Vec2M"
}