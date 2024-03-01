/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Extension methods class for [[Int]], for the geom package. */
class IntGeomImplicit(thisInt: Int)
{
  def ° : Angle = Angle(thisInt)

  /** Succinct syntax for creating 2 dimensional points [[Pt2]]s, from 2 numbers. Note the low precedence of this method relative to most numerical
   * operators. A third number as example {{{3.1 pp 4 pp -7.25}}} can be used to create a [[Pt3]]. */
  @inline infix def pp(y: Double): Pt2 = Pt2(thisInt, y)

  /** Succinct syntax for creating 2 dimensional vectors, [[Vec2]]s from 2 numbers. Note the low precedence of this method relative to most numerical
      operators. A third number as example {{{3.1 vv 4 vv -7.25}}} can be used to create a [[Vec3]]. */
  @inline def vv(y: Double): Vec2 = Vec2(thisInt, y)

  /** Converts this Int into an absolute angle of the given degrees from 0 until 360 degrees. */
  def degs: Angle = Angle(thisInt)

  /** Degrees rotation anti clockwise. Converts this Int into an [[AngleVec]] a positive angle of rotation. Can return values greater than 360 degrees
   *  and less than -360 degrees. */
  def degsVec: AngleVec = AngleVec(thisInt)

  /** Degrees rotation clockwise. Converts this Int into an [[AngleVec]] a negative angle of rotation. Can return values greater than 360 degrees and
   *  less than -360 degrees. */
  def degsClk: AngleVec = AngleVec(-thisInt)

  /** Multiplication of a 2 dimensional vector by this scalar [[Int]]. */
  def *(operand: Vec2): Vec2 = new Vec2(thisInt * operand.x, thisInt * operand.y)
}

/** Extension methods class for [[Double]], for the geom package. */
class DoubleImplicitGeom(thisDouble: Double)
{
  /** Succinct syntax for creating 2 dimensional points [[Pt2]]s, from 2 numbers. Note the low precedence of this method relative to most numerical
   *  operators. A third number as example {{{3.1 pp 4 pp -7.25}}} can be used to create a [Pt3]. */
  @inline infix def pp(y: Double): Pt2 = Pt2(thisDouble, y)

  /** Succinct syntax for creating 2 dimensional vectors, [[Vec2]]s from 2 numbers. Note the low precedence of this method relative to most numerical
   *  operators. A third number as example {{{3.1 pp 4 pp -7.25}}} can be used to create a [Pt3]. */
  @inline infix def vv(y: Double): Vec2 = Vec2(thisDouble, y)

  def radians: Angle = Angle.radians(thisDouble)

  /** Converts this Double into an absolute angle of the given degrees from 0 until 360 degrees. */
  def degs: Angle = Angle(thisDouble)

  /** Degrees rotation anti clockwise. Converts this Double into an [[AngleVec]] a positive angle of rotation. Can return values greater than 360
   *  degrees and less than -360 degrees. */
  def degsVec: AngleVec = AngleVec(thisDouble)

  /** Degrees rotation clockwise. Converts this Double into an [[AngleVec]] a negative angle of rotation. Can return values greater than 360 degrees
   * and less than -360 degrees. */
  def degsClk: AngleVec = AngleVec(-thisDouble)

  /** Multiplication of a 2 dimensional vector by this scalar [[Double]]. */
  def * (operand: Vec2): Vec2 = new Vec2(thisDouble * operand.x, thisDouble * operand.y)
}