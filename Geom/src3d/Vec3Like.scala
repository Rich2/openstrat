/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A common trait for [[Vec3]] and [[Pt3]]. Don't know if this will be useful after refactoring. */
trait Vec3Like extends Dbl3Elem with ApproxDbl
{ def x: Double
  def y: Double
  def z: Double
  inline final override def dbl1: Double = x
  inline final override def dbl2: Double = y
  inline final override def dbl3: Double = z
}