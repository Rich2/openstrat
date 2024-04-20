/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import impunits._

/** Extension methods class for [[Double]], for the geom package. */
class GeomDoubleExtensions(thisDouble: Double)
{
  /** Method to be removed, prefer kMetres. */
  def km: Metres = new Metres(thisDouble * 1000)

  /** Returns this [[Double]] value in [[Metres]]. */
  @inline def metres: Metres = new Metres(thisDouble)

  /** Extension methods multiplies this scalar [[Double]] by the operand in metres */
  @inline def * (operator: Metres): Metres = new Metres(thisDouble * operator.metresNum)

  /** Returns this [[Int]] value in [[Metres]] kilometres. */
  @inline def kMetres: Metres = Metres(thisDouble * 1000)

  /** Returns this [[Double]] value in [[Metres]] or millions of kilometres. */
  @inline def gMetres: Metres = new Metres(thisDouble * 1000000000)

  /** Returns this [[Double]] value in [[Miles]]. */
  @inline def miles: Miles = new Miles(thisDouble)

  /** Returns this [[Double]] value of [[MegaMiles]] millions of miles. */
  @inline def megaMiles: MegaMiles = new MegaMiles(thisDouble)

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