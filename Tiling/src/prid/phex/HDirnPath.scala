/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import ostrat.geom._
//import collection.mutable.ArrayBuffer

/** A path consisting of a starting [[HCen]] and a sequence of [[HDirn]]s. */
class HDirnPath(val unsafeArray: Array[Int]) extends ArrayIntBacked
{
  def startR: Int = unsafeArray(0)
  def startC: Int = unsafeArray(1)
  def startCen = HCen(unsafeArray(0), unsafeArray(1))
  def segsNum: Int = unsafeArray.length - 2

  def segHCsForeach(f: LineSegHC => Unit): Unit =
  { var count = 0
    var r1 = startR
    var c1 = startC
    var r2: Int = 0
    var c2: Int = 0

    while (count < segsNum)
    { val step = HDirn.fromInt(unsafeArray(count + 2))
      r2 = r1 + step.tr
      c2 = c1 + step.tc
      val hls = LineSegHC(r1, c1, r2, c2)
      f(hls)
      count += 1
      r1 = r2
      c1 = c2
    }
  }

  def segHCsMap[B, ArrB <: Arr[B]](f: LineSegHC => B)(implicit build: ArrMapBuilder[B, ArrB], grider: HGridSys): ArrB =
  { val res = build.uninitialised(segsNum)
    var count = 0
    segHCsForeach{ s => res.unsafeSetElem(count, f(s)); count += 1 }
    res
  }

  def projLineSegs(implicit proj: HSysProjection): LineSegArr =
  { val res = LineSegArr.uninitialised(segsNum)
    var count = 0
    segHCsForeach{ lh =>
      val ols = proj.transOptLineSeg(lh)
      ols.foreach(res.unsafeSetElem(count, _))
      count += 1
    }
    res
  }
}

/** An [[Arr]] of paths consisting of a starting [[HCen]] and a sequence of [[HDirn]]s. */
class HDirnPathArr(val unsafeArrayOfArrays: Array[Array[Int]]) extends ArrayIntBackedArr[HDirnPath]
{ override type ThisT = HDirnPathArr
  override def typeStr: String = "HDirnPathArr"
  override def unsafeFromArrayArray(array: Array[Array[Int]]): HDirnPathArr = new HDirnPathArr(array)
  override def apply(index: Int): HDirnPath = new HDirnPath(unsafeArrayOfArrays(index))
  override def fElemStr: HDirnPath => String = _.toString
}

/** An [[ElemPair]] where the first element is an [[HDirnPath]], a path consisting of a starting [[HCen]] and a sequence of [[HDirn]]s. */
class HDirnPathPair[A2](val a1ArrayInt: Array[Int], val a2: A2) extends ArrayIntBackedPair[HDirnPath, A2]
{ override def a1: HDirnPath = new HDirnPath(a1ArrayInt)
}

/** A [[PairArr]] where the first element is an [[HDirnPath]], a path consisting of a starting [[HCen]] and a sequence of [[HDirn]]s. */
class HDirnPathPairArr[A2](val a1Arrays: Array[Array[Int]], val a2Array: Array[A2]) extends ArrayIntBackedPairArr[HDirnPath, HDirnPathArr, A2, HDirnPathPair[A2]]
{ override type ThisT = HDirnPathPairArr[A2]
  override def typeStr: String = "HDirnPathPairArr"
  inline override def a1FromArrayInt(array: Array[Int]): HDirnPath = new HDirnPath(array)
  inline override def fromArrays(array1: Array[Array[Int]], array2: Array[A2]): HDirnPathPairArr[A2] = new HDirnPathPairArr(array1, array2)
  inline override def a1Arr: HDirnPathArr = new HDirnPathArr(a1Arrays)
  override def fElemStr: HDirnPathPair[A2] => String = _.toString
  override def elemFromComponents(a1: Array[Int], a2: A2): HDirnPathPair[A2] = new HDirnPathPair(a1, a2)
}