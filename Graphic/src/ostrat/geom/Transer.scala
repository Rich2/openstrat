/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** An object that can transform itself in 2d geometry. This is a key trait, the object can be transformed in 2 dimensional space. Leaf classes must implement the single method fTrans(f: Vec2 => Vec2):
 *  T. The related trait TransDistable  does the same for fTrans(f: Dist2 => Dist2):  T.  */
trait Transer extends Any
{ def fTrans(f: Vec2 => Vec2): Transer
}

/** The companion object for Transer. */
object Transer
{
  implicit def TransFromTranserImplicit[T <: Transer]: Trans[T] =
    (obj, f) => obj.fTrans(f).asInstanceOf[T]
}

