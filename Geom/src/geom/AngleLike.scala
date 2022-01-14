/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Base trait for [[Angle]], [[AngleVec]], [[Latitude]] and [[Longitude]]. */
trait AngleLike extends Any with Show with ApproxAngle with ElemDbl1
{ /** The angle expressed in thousandths of an arc second of a degree. */
  def milliSecs: Double

  @inline final override def dbl1: Double = milliSecs
  /** The value of this angle expressed in degrees. */
  @inline def degs: Double = milliSecs / MilliSecsInDeg

  /** The value of this angle expressed in arc seconds of a degree. */
  @inline final def secs: Double = milliSecs / 1000

  /** The value of the angle expressed in radians. */
  @inline final def radians: Double = milliSecs.milliSecsToRadians

  /** The sine value of this angle. */
  @inline def sin: Double = math.sin(radians)

  /** The cosine value of this angle. */
  @inline def cos: Double = math.cos(radians)

  final override def syntaxDepth: Int = 1
  final override def str: String = show(ShowStandard,  0)
}

trait ApproxAngle extends Any with Approx[AngleVec]
{ override def precisionDefault: AngleVec = 1e-10.degs
}

trait ApproxAngleT[T] extends ApproxT[AngleVec, T]
{ override def precisionDefault: AngleVec = 1e-10.degs
}