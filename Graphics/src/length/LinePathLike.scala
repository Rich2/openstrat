/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait LinePathLike[A <: ElemValueN] extends Any with DataValueNs[A]
{
  def map[B <: ElemValueN, BB <: LinePathLike[B]](f: A => B)(implicit build: LinePathBuilder[B, BB]): BB =
  { val res = build.newArr(elemsNum)
    dataIForeach((p, i) => res.unsafeSetElem(i, f(p)))
    res
  }
}

trait LinePathValueNsData[A <: ElemValueN] extends Any with LinePathLike[A]
trait LinePathDblNs[A <: ElemDblN] extends  Any with LinePathLike[A] with DataDblNs[A]
trait LinePathDbl2s[A <: ElemDbl2] extends Any with LinePathDblNs[A] with DataDbl2s[A]
trait LinePathDbl3s[A <: ElemDbl3] extends Any with LinePathDblNs[A] with DataDbl3s[A]