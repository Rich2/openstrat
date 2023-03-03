/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A common trait for [[Vec3]] and [[Pt3]]. Don't know if this will be useful after refactoring. */
trait Vec3Like extends ShowElemDbl3 with ApproxDbl
{ def x: Double
  def y: Double
  def z: Double
  final override def name1: String = "x"
  final override def name2: String = "y"
  final override def name3: String = "z"
  final override def show1: Double = x
  final override def show2: Double = y
  final override def show3: Double = z
}