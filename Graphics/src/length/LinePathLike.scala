/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait LinePathLike[A <: ElemValueN] extends Any with ValueNsData[A]
{
  def map[B <: ElemValueN, BB <: LinePathLike[B]](f: A => B)(implicit build: LinePathBuilder[B, BB]): BB =
  { val res = build.newArr(elemsNum)
    dataIForeach((p, i) => res.unsafeSetElem(i, f(p)))
    res
  }
}

trait LinePathValueNsData[A <: ElemValueN] extends Any with LinePathLike[A]
trait LinePathDblNs[A <: ElemDblN] extends  Any with LinePathLike[A] with DblNsData[A]
trait LinePathDbl2s[A <: Dbl2Elem] extends Any with LinePathDblNs[A] with Dbl2sData[A]
trait LinePathDbl3s[A <: Dbl3Elem] extends Any with LinePathDblNs[A] with Dbl3sData[A]