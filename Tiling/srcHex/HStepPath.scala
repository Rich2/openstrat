/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import annotation._, geom._, reflect.ClassTag

/** A path consisting of a starting [[HCen]] and a sequence of [[HStep]]s. */
class HStepPath(val unsafeArray: Array[Int]) extends ArrayIntBacked
{ def startR: Int = unsafeArray(0)
  def startC: Int = unsafeArray(1)
  def startCen = HCen(unsafeArray(0), unsafeArray(1))
  def length: Int = unsafeArray.length - 2

  /** Gets the first [[HStep]] wills throw on an empty path. */
  def getHead: HStep = HStep.fromInt(unsafeArray(2))

  /** Gets the [[HStep]] at the given index, will throw if the element doesn't exist. */
  def index(index: Int): HStep = HStep.fromInt(unsafeArray(index + 2))

  def tail(newStart: HCen): HStepPath =
  { val newArray = new Array[Int]((length - 1).max0 + 2)
    newArray(0) = newStart.r
    newArray(1) = newStart.c
    iUntilForeach(1, length)(i => newArray(i + 1) = unsafeArray(i + 2))
    new HStepPath(newArray)
  }

  def segHCsForeach(f: LineSegHC => Unit)(implicit gSys: HGridSys): Unit =
  { var i: Int = 0
    var hc1: HCen = startCen
    var o2: Option[HCen] = Some(startCen)

    while (i < length & o2.nonEmpty)
    {
      o2 = gSys.stepEndFind(hc1, index(i))
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
    segHCsForeach{ s => res.setElemUnsafe(i, s); i += 1 }
    res
  }

  def segHCsInit(implicit gSys: HGridSys): LineSegHCArr =
  { val res = LineSegHCArr.uninitialised((length - 1).max0)
    var i = 0
    segHCsForeach { s => if (i != 0) res.setElemUnsafe(i, s); i += 1 }
    res
  }

  //def SegHCLast: Option[LineSegHC] = if (length >= 0) Some(index(length - 1)) else None

  def segHCsMap[B, ArrB <: Arr[B]](f: LineSegHC => B)(implicit build: BuilderArrMap[B, ArrB], grider: HGridSys): ArrB =
  { val res = build.uninitialised(length)
    var count = 0
    segHCsForeach{ s => res.setElemUnsafe(count, f(s)); count += 1 }
    res
  }

  def projLineSegs(implicit proj: HSysProjection): LineSegArr =
  { val res = LineSegArr.uninitialised(length)
    var count = 0
    segHCsForeach{ lh =>
      val ols = proj.transOptLineSeg(lh)
      ols.foreach(res.setElemUnsafe(count, _))
      count += 1
    }(proj.parent)
    res
  }
}

object HStepPath
{
  def apply(startCen: HCen, steps: HStep*): HStepPath =
  { val array = new Array[Int](steps.length + 2)
    array(0) = startCen.int1
    array(1) = startCen.int2
    steps.iForeach{(i, d) => array(i + 2) = d.int1 }
    new HStepPath(array)
  }

  //implicit val implicitArrMapBuilder: ArrMapBuilder[HStepPath, HStepPathArr] = new
}

/** An [[Arr]] of paths consisting of a starting [[HCen]] and a sequence of [[HStep]]s. */
class HStepPathArr(val unsafeArrayOfArrays: Array[Array[Int]]) extends ArrayIntBackedArr[HStepPath]
{ override type ThisT = HStepPathArr
  override def typeStr: String = "HDirnPathArr"
  override def unsafeFromArrayArray(array: Array[Array[Int]]): HStepPathArr = new HStepPathArr(array)
  override def apply(index: Int): HStepPath = new HStepPath(unsafeArrayOfArrays(index))
  override def fElemStr: HStepPath => String = _.toString
}

/** Companion object for [[HStepPathArr]] contains factory apply method. */
object HStepPathArr
{ def apply[A2](paths: HStepPath*): HStepPathArr = ???
}

/** An [[PairNoA1ParamElem]] where the first element is an [[HStepPath]], a path consisting of a starting [[HCen]] and a sequence of [[HStep]]s. */
class HDirnPathPair[A2](val a1ArrayInt: Array[Int], val a2: A2) extends ArrayIntBackedPair[HStepPath, A2]
{ override def a1: HStepPath = new HStepPath(a1ArrayInt)
  def path: HStepPath = new HStepPath(a1ArrayInt)
  def tail(newStart: HCen) = path.tail(newStart)
}

object HDirnPathPair
{ /** Factory apply method with alternative name overload where start row and colum passed separately. */
  def apply[A2](a2: A2, startCen: HCen, steps: HStep*): HDirnPathPair[A2] = apply[A2](a2,startCen.r, startCen.c, steps:_*)

  /** Factory apply method with alternative name overload where [[HCen]] passes as single parameter. */
  def apply[A2](a2: A2, startR: Int, startC: Int, steps: HStep*): HDirnPathPair[A2] =
  { val array: Array[Int] = new Array[Int](2 + steps.length)
    array(0) = startR
    array(1) = startC
    steps.iForeach(2){ (i, dn) => array(i) = dn.int1}
    new HDirnPathPair[A2](array, a2)
  }

  /** Factory apply method with alternative name overload where [[HCen]] passes as single parameter. */
  def apply[A2](a2: A2, hCen: HCen, steps: HStepArr): HDirnPathPair[A2] =
  { val array: Array[Int] = new Array[Int](2 + steps.length)
    array(0) = hCen.r
    array(1) = hCen.c
    steps.iForeach(2) { (i, dn) => array(i) = dn.int1 }
    new HDirnPathPair[A2](array, a2)
  }
}

/** A [[PairNoA1PramArr]] where the first element is an [[HStepPath]], a path consisting of a starting [[HCen]] and a sequence of [[HStep]]s. */
class HStepPathPairArr[A2](val a1ArrayArrayInts: Array[Array[Int]], val a2Array: Array[A2]) extends ArrayIntBackedPairArr[HStepPath, HStepPathArr, A2, HDirnPathPair[A2]]
{ override type ThisT = HStepPathPairArr[A2]
  override def typeStr: String = "HDirnPathPairArr"
  inline override def a1FromArrayArrayInt(array: Array[Int]): HStepPath = new HStepPath(array)
  inline override def newFromArrays(array1: Array[Array[Int]], array2: Array[A2]): HStepPathPairArr[A2] = new HStepPathPairArr(array1, array2)
  inline override def a1Arr: HStepPathArr = new HStepPathArr(a1ArrayArrayInts)
  override def fElemStr: HDirnPathPair[A2] => String = _.toString
  override def elemFromComponents(a1: Array[Int], a2: A2): HDirnPathPair[A2] = new HDirnPathPair(a1, a2)
}

object HStepPathPairArr extends ArrayIntBackedPairArrCompanion[HStepPath]
{ def apply[A2](elems: HDirnPathPair[A2]*)(implicit ct: ClassTag[A2]): HStepPathPairArr[A2] = elemsToArrays(elems, new HStepPathPairArr[A2](_, _))
}