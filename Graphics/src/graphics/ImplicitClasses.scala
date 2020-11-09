/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

class IntGeomImplicit(thisInt: Int)
{ /** Succinct syntax for creating 2 dimensional vectors, Vec2s from 2 numbers. Note the low precedence of this method relative to most numerical
      operators. */
  @inline def pp(y: Double): Pt2 = Pt2(thisInt, y)
  @inline def vv(y: Double): Vec2 = Vec2(thisInt, y)
  def ° : Angle = Angle(thisInt)
  def km: Dist = Dist(thisInt * 1000)
  def metre: Dist = Dist(thisInt)
  @inline def miles: Dist = Dist(thisInt * 1609.344)
  @inline def millionMiles: Dist = thisInt.miles * 1000000
  def * (operator: Dist): Dist = Dist(thisInt * operator.metres)
  def ll (longDegs: Double): LatLong = LatLong.degs(thisInt, longDegs)
  def east = Longitude.degs(thisInt)
  def west = Longitude.degs(-thisInt)
  def north = Latitude.apply(thisInt)
  def south = Latitude.apply(-thisInt)
}

class DoubleImplicitGeom(thisDouble: Double)
{ /** Succinct syntax for creating 2 dimensional vectors, Vec2s from 2 numbers. Note the low precedence of this method relative to most numerical
      operators. */
  @inline def pp(y: Double): Pt2 = Pt2(thisDouble, y)
  @inline def vv(y: Double): Vec2 = Vec2(thisDouble, y)
  def km: Dist = Dist(thisDouble * 1000)
  def metre: Dist = Dist(thisDouble)
  def * (operator: Dist): Dist = Dist(thisDouble * operator.metres)
  @inline def miles: Dist = Dist(thisDouble * 1609.344)
  @inline def millionMiles: Dist = thisDouble.miles * 1000000
  def radians: Angle = Angle.radians(thisDouble)
  def degs: Angle = Angle(thisDouble)
  def ll (longDegs: Double): LatLong = LatLong.degs(thisDouble, longDegs)
  def east = Longitude.degs(thisDouble)
  def west = Longitude.degs(-thisDouble)
  def north = Latitude.apply(thisDouble)
  def south = Latitude.apply(-thisDouble)
  def * (operand: Pt2): Pt2 = new Pt2(thisDouble * operand.x, thisDouble * operand.y)
  def * (operand: Vec2): Vec2 = new Vec2(thisDouble * operand.x, thisDouble * operand.y)
  def metres: Dist = new Dist(thisDouble)
}