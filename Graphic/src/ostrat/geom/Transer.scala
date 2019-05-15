/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

trait Transer extends Any
{ def fTrans(f: Vec2 => Vec2): Transer
}

/** Not sure if implicit can be replaced by SAM. */
object Transer
{
  implicit def TransFromTranserImplicit[T <: Transer]: Trans[T] = new Trans[T] {
    override def trans(obj: T, f: Vec2 => Vec2): T = obj.fTrans(f).asInstanceOf[T]
  }
}

