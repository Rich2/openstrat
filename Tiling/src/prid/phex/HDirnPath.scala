/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import ostrat.geom._, reflect.ClassTag

/** A path consisting of a starting [[HCen]] and a sequence of [[HDirn]]s. */
class HDirnPath(val unsafeArray: Array[Int]) extends ArrayIntBacked
{ def startR: Int = unsafeArray(0)
  def startC: Int = unsafeArray(1)
  def startCen = HCen(unsafeArray(0), unsafeArray(1))
  def length: Int = unsafeArray.length - 2

  /** Gets the first [[HDirn]] wills throw on an empty path. */
  def getHead: HDirn = HDirn.fromInt(unsafeArray(2))

  /** Gets the [[HDirn]] at the given index, will thorw if the element doesn't exist. */
  def index(index: Int): HDirn = HDirn.fromInt(unsafeArray(index + 2))

  def tail(newStart: HCen): HDirnPath =
  { val newArray = new Array[Int]((length - 1).max0 + 2)
    newArray(0) = newStart.r
    newArray(1) = newStart.c
    iUntilForeach(1, length)(i => newArray(i + 1) = unsafeArray(i + 2))
    new HDirnPath(newArray)
  }

  def segHCsForeach(f: LineSegHC => Unit)(implicit gSys: HGridSys): Unit =
  { var i: Int = 0
    var hc1: HCen = startCen
    var o2: Option[HCen] = Some(startCen)

    while (i < length & o2.nonEmpty)
    {
      o2 = gSys.findStepEnd(hc1, index(i))
      o2.foreach { hc2 =>
        val hls = LineSegHC(hc1, hc2)
        f(hls)
        i += 1
        hc1 = hc2
      }
    }
  }

  def segHCs(implicit gSys: HGridSys): LineSegHCArr =
  { val res = LineSegHCArr.uninitialised(length)
    var i = 0
    segHCsForeach{ s => res.unsafeSetElem(i, s); i += 1 }
    res
  }

  def segHCsInit(implicit gSys: HGridSys): LineSegHCArr =
  { val res = LineSegHCArr.uninitialised((length - 1).max0)
    var i = 0
    segHCsForeach { s => if (i != 0) res.unsafeSetElem(i, s); i += 1 }
    res
  }

  def segHCsMap[B, ArrB <: Arr[B]](f: LineSegHC => B)(implicit build: ArrMapBuilder[B, ArrB], grider: HGridSys): ArrB =
  { val res = build.uninitialised(length)
    var count = 0
    segHCsForeach{ s => res.unsafeSetElem(count, f(s)); count += 1 }
    res
  }

  def projLineSegs(implicit proj: HSysProjection): LineSegArr =
  { val res = LineSegArr.uninitialised(length)
    var count = 0
    segHCsForeach{ lh =>
      val ols = proj.transOptLineSeg(lh)
      ols.foreach(res.unsafeSetElem(count, _))
      count += 1
    }(proj.parent)
    res
  }
}

object HDirnPath
{
  def apply(startCen: HCen, steps: HDirn*): HDirnPath =
  { val array = new Array[Int](steps.length + 2)
    array(0) = startCen.int1
    array(1) = startCen.int2
    steps.iForeach{(i, d) => array(i + 2) = d.intValue }
    new HDirnPath(array)
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

/** Companion object for [[HDirnPathArr]] contains factory apply method. */
object HDirnPathArr
{ def apply[A2](paths: HDirnPath*): HDirnPathArr = ???
}

/** An [[PairElem]] where the first element is an [[HDirnPath]], a path consisting of a starting [[HCen]] and a sequence of [[HDirn]]s. */
class HDirnPathPair[A2](val a1ArrayInt: Array[Int], val a2: A2) extends ArrayIntBackedPair[HDirnPath, A2]
{ override def a1: HDirnPath = new HDirnPath(a1ArrayInt)
  def path: HDirnPath = new HDirnPath(a1ArrayInt)
  def tail(newStart: HCen) = path.tail(newStart)
}

object HDirnPathPair
{ /** Factory apply method with alternative name overload where start row and colum passed separately. */
  def apply[A2](a2: A2, startCen: HCen, steps: HDirn*): HDirnPathPair[A2] = apply[A2](a2,startCen.r, startCen.c, steps:_*)

  /** Factory apply method with alternative name overload where [[HCen]] passes as single parameter. */
  def apply[A2](a2: A2, startR: Int, startC: Int, steps: HDirn*): HDirnPathPair[A2] =
  { val array: Array[Int] = new Array[Int](2 + steps.length)
    array(0) = startR
    array(1) = startC
    steps.iForeach(2){ (i, dn) => array(i) = dn.intValue}
    new HDirnPathPair[A2](array, a2)
  }
}

/** A [[PairArr]] where the first element is an [[HDirnPath]], a path consisting of a starting [[HCen]] and a sequence of [[HDirn]]s. */
class HDirnPathPairArr[A2](val a1ArrayInts: Array[Array[Int]], val a2Array: Array[A2]) extends ArrayIntBackedPairArr[HDirnPath, HDirnPathArr, A2, HDirnPathPair[A2]]
{ override type ThisT = HDirnPathPairArr[A2]
  override def typeStr: String = "HDirnPathPairArr"
  inline override def a1FromArrayInt(array: Array[Int]): HDirnPath = new HDirnPath(array)
  inline override def fromArrays(array1: Array[Array[Int]], array2: Array[A2]): HDirnPathPairArr[A2] = new HDirnPathPairArr(array1, array2)
  inline override def a1Arr: HDirnPathArr = new HDirnPathArr(a1ArrayInts)
  override def fElemStr: HDirnPathPair[A2] => String = _.toString
  override def elemFromComponents(a1: Array[Int], a2: A2): HDirnPathPair[A2] = new HDirnPathPair(a1, a2)
}

object HDirnPathPairArr extends ArrayIntBackedPairArrCompanion[HDirnPath]
{ def apply[A2](elems: HDirnPathPair[A2]*)(implicit ct: ClassTag[A2]): HDirnPathPairArr[A2] = elemsToArrays(elems, new HDirnPathPairArr[A2](_, _))
}