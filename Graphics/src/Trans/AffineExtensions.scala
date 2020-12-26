/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Extension methods for an object that can transform itself in 2d geometry via the function f: Vec2 => Vec2. There is an implicit conversion of
 *  any object that has a Trans instance. Trans instances are founded on objects that inherit the Transer trait. Eg Polygon inherits from Transer.
 *  There is a Trans[Polygon]instance and a Trans[List[Polygon]] instance. The TransDistExtension class provides similar extension methods for
 *  objects that can perform the Dist2 => Dist2 transformation. */
class AffineExtensions[T](value: T, ev: AffineTrans[T])
{
  /** General Vec2 to Vec2 transformation. */
  def trans(f: Pt2 => Pt2):  T = ev.trans(value, f)
}