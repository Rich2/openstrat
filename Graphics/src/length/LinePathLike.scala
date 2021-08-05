/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait LinePathLike[A] extends Any
{
  def map[B, BB <: LinePathLike[B]](implicit build: LinePathBuilder[B, BB]): BB =
  {
    val res = build.newArr(5)
    res
  }
}

trait ValueNsDataLinePath[A <: ValueNElem] extends Any with LinePathLike[A]
trait DblNsLinePath[A <: DblNElem] extends  Any with LinePathLike[A] with DblNsData[A]
trait Dbl2sLinePath[A <: Dbl2Elem] extends Any with DblNsLinePath[A] with Dbl2sData[A]
trait Dbl3sLinePath[A <: Dbl3Elem] extends Any with DblNsLinePath[A] with Dbl3sData[A]