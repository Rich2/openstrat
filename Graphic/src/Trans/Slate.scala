/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

trait Slate[T]
{ def slate(obj: T, offset: Vec2): T
}

object Slate
{
  implicit def transAlignerImplicit[T <: TransAligner]: Slate[T] = (obj, offset) => obj.slateOld(offset).asInstanceOf[T]
}
