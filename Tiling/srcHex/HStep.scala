/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, collection.mutable.ArrayBuffer, reflect.ClassTag

/** Common trait for [[HStep]] and [[HStepStay]]. */
sealed trait HStepLike extends TStepLike
{
  /** Returns a [[Some]] of the result of the function if this is an [[HStep]] else returns [[None]]. */
  def mapOpt[B, ArrB <: Arr[B]](f: HStep => B): Option[B] = this match
  { case hs: HStep => Some(f(hs))
    case _ => None
  }
}

object HStepLike
{ /** Constructs [[HStepLike]] from its int1 value. */
  def fromInt(inp: Int): HStepLike = inp match
  { case 0 => HStepStay
    case 1 => HexUR
    case 2 => HexRt
    case 3 => HexDR
    case 4 => HexDL
    case 5 => HexLt
    case 6 => HexUL
    case _ => HStepStay
  }

  implicit val defaultValueEv: DefaultValue[HStepLike] = new DefaultValue[HStepLike] { override def default: HStepLike = HStepStay }
}

/** The no step value of [[HStepLike]] */
case object HStepStay extends HStepLike
{ override val int1: Int = 0
  override def tr: Int = 0
  override def tc: Int = 0
}

/** A step on a hex tile grid [[HGrid]] can take 6 values: upright right, downright, downleft, left and upleft. These should not be confused with
 * [[HVDirnOpt]]s which fo from an [[HVert]] to an [[HCen]]. */
sealed trait HStep extends TStepSided with HStepLike
{ /** The delta [[HCen]] of this step inside a hex grid. */
  def hCenDelta: HCen = HCen(tr, tc)
  def int1: Int
  def reverse: HStep
  def canEqual(a: Any): Boolean = a.isInstanceOf[HStep]
}

object HStep
{ /** Constructs [[HStep]] from its int1 value. */
  def fromInt(inp: Int): HStep = inp match
  { case 1 => HexUR
    case 2 => HexRt
    case 3 => HexDR
    case 4 => HexDL
    case 5 => HexLt
    case 6 => HexUL
    case n => excep(s"$n is not a valid HStep")
  }

  def full: HStepArr = HStepArr(HexUR, HexRt, HexDR, HexDL, HexLt, HexUL)

  implicit val arrMapBuildEv: BuilderMapArrInt1[HStep, HStepArr] = new BuilderMapArrInt1[HStep, HStepArr]
  { override type BuffT = HStepBuff
    override def fromIntArray(array: Array[Int]): HStepArr = new HStepArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HStepBuff = new HStepBuff(buffer)
  }

  implicit def pairArrMapBuilder[B2](implicit ct: ClassTag[B2]): HStepPairArrMapBuilder[B2] = new HStepPairArrMapBuilder[B2]
}

/** A step upright on a hex tile grid [[HGrid]]. */
case object HexUR extends HStep
{ def sr: Int = 1
  def sc: Int = 1
  def int1 = 1
  override def reverse: HStep = HexDL
}

/** A step right on a hex tile grid [[HGrid]]. */
case object HexRt extends HStep
{ def sr: Int = 0
  def sc: Int = 2
  def int1 = 2
  override def reverse: HStep = HexLt
}

/** A step downright on a hex tile grid [[HGrid]]. */
case object HexDR extends HStep
{ def sr: Int = -1
  def sc: Int = 1
  def int1 = 3
  override def reverse: HStep = HexUL
}

/** A step downleft on a hex tile grid [[HGrid]]. */
case object HexDL extends HStep
{ def sr: Int = -1
  def sc: Int = -1
  def int1 = 4
  override def reverse: HStep = HexUR
}

/** A step left on a hex tile grid [[HGrid]]. */
case object HexLt extends HStep
{ def sr: Int = 0
  def sc: Int = -2
  def int1 = 5
  override def reverse: HStep = HexRt
}

/** A step upleft on a hex tile grid [[HGrid]]. */
case object HexUL extends HStep
{ def sr: Int = 1
  def sc: Int = -1
  def int1 = 6
  override def reverse: HStep = HexDR
}
/** An Arr of hex step directions. */
class HStepLikeArr(val arrayUnsafe: Array[Int]) extends AnyVal with ArrInt1[HStepLike] {
  override type ThisT = HStepLikeArr

  override def typeStr: String = "HStepLikeArr"

  override def elemFromInt(intValue: Int): HStepLike = HStepLike.fromInt(intValue)

  override def fromArray(array: Array[Int]): HStepLikeArr = new HStepLikeArr(array)

  override def fElemStr: HStepLike => String = _.toString
}

object HStepLikeArr extends CompanionSlInt1[HStepLike, HStepLikeArr]
{ override def fromArray(array: Array[Int]): HStepLikeArr = new HStepLikeArr(array)

  implicit val flatBuilder: BuilderArrFlat[HStepLikeArr] = new BuilderFlatArrIn1[HStepLikeArr]
  { override type BuffT = HStepLikeBuff
    override def fromIntArray(array: Array[Int]): HStepLikeArr = new HStepLikeArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HStepLikeBuff = new HStepLikeBuff(buffer)
  }
}

/** ArrayBuffer based buffer class for [[HStepLike]]s. */
class HStepLikeBuff(val bufferUnsafe: ArrayBuffer[Int]) extends AnyVal with BuffInt1[HStepLike]
{ override def typeStr: String = "HStepLikeBuff"
  def elemFromInt(i1: Int): HStepLike = HStepLike.fromInt(i1)
}

object HStepLikeBuff
{ def apply(initLen: Int = 4): HStepLikeBuff = new HStepLikeBuff(new ArrayBuffer[Int](initLen))
}

/** An Arr of hex step directions. */
class HStepArr(val arrayUnsafe: Array[Int]) extends AnyVal with ArrInt1[HStep]
{ override type ThisT = HStepArr
  override def typeStr: String = "HSteps"
  override def elemFromInt(intValue: Int): HStep = HStep.fromInt(intValue)
  override def fromArray(array: Array[Int]): HStepArr = new HStepArr(array)
  override def fElemStr: HStep => String = _.toString
  def segsNum: Int = arrayUnsafe.length

  def segHCsForeach(start: HCen)(f: LineSegHC => Unit): Unit = segHCsForeach(start.r, start.c)(f)

  def segHCsForeach(startR: Int, startC: Int)(f: LineSegHC => Unit): Unit =
  { var count = 0
    var r1 = startR
    var c1 = startC
    var r2: Int = 0
    var c2: Int = 0

    while (count < segsNum)
    { val step = HStep.fromInt(arrayUnsafe(count))
      r2 = r1 + step.tr
      c2 = c1 + step.tc
      val hls = LineSegHC(r1, c1, r2, c2)
      f(hls)
      count += 1
      r1 = r2
      c1 = c2
    }
  }

  def segHCsMap[B, ArrB <: Arr[B]](start: HCen)(f: LineSegHC => B)(implicit build: BuilderArrMap[B, ArrB], gridSys: HGridSys): ArrB =
    segHCsMap(start.r, start.c)(f)(build, gridSys)

  def segHCsMap[B, ArrB <: Arr[B]](startR: Int, startC: Int)(f: LineSegHC => B)(implicit build: BuilderArrMap[B, ArrB], grider: HGridSys): ArrB = {
    val res = build.uninitialised(segsNum)
    var count = 0
    segHCsForeach(startR, startC) { s =>
      res.setElemUnsafe(count, f(s))
      count += 1
    }
    res
  }

  def projLineSegs(startCen: HCen, proj: HSysProjection): LSeg2Arr = projLineSegs(startCen. r, startCen.c, proj)

  def projLineSegs(startR: Int, startC: Int, proj: HSysProjection): LSeg2Arr =
  { val res = LSeg2Arr.uninitialised(segsNum)
    var count = 0
    segHCsForeach(startR, startC) { lh =>
      val ols = proj.transOptLineSeg(lh)
      ols.foreach(res.setElemUnsafe(count, _))
      count += 1
    }
    res
  }

  def pathHC(startHC: HCen)(implicit grider: HGridSys): LinePathHC = {
    val buff: HCoordBuff = HCoordBuff(startHC)
    var i = 0
    var continue = true
    var currHC: HCen = startHC
    while(i < length & continue == true) {
      val optHC: Option[HCen] = grider.stepEndFind(currHC, apply(i))
      optHC.fold[Unit]{ continue = false}{hc2 =>
        buff.grow(hc2)
        currHC = hc2
        i += 1
      }
    }
    buff.toLinePath
  }
}

object HStepArr extends CompanionSlInt1[HStep, HStepArr]
{ override def fromArray(array: Array[Int]): HStepArr = new HStepArr(array)

  implicit val flatBuilder: BuilderArrFlat[HStepArr] = new BuilderFlatArrIn1[HStepArr]
  { override type BuffT = HStepBuff
    override def fromIntArray(array: Array[Int]): HStepArr = new HStepArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HStepBuff = new HStepBuff(buffer)
  }
}

/** ArrayBuffer based buffer class for Colours. */
class HStepBuff(val bufferUnsafe: ArrayBuffer[Int]) extends AnyVal with BuffInt1[HStep]
{ override def typeStr: String = "HStepBuff"
  def elemFromInt(i1: Int): HStep = HStep.fromInt(i1)
}

object HStepBuff
{ def apply(initLen: Int = 4): HStepBuff = new HStepBuff(new ArrayBuffer[Int](initLen))
}

class HStepArrArr(val arrayOfArraysUnsafe: Array[Array[Int]]) extends ArrArrayInt[HStepArr]
{ override type ThisT = HStepArrArr
  override def typeStr: String = "HStepArrArr"
  override def elemFromArray(array: Array[Int]): HStepArr = new HStepArr(array)
  override def fromArrayArray(array: Array[Array[Int]]): HStepArrArr = new HStepArrArr(array)
  override def fElemStr: HStepArr => String = _.toString
}

class HStepArrPair[A2](val a1ArrayInt: Array[Int], val a2: A2) extends ArrayIntBackedPair[HStepArr, A2]
{ override def a1: HStepArr = new HStepArr(a1ArrayInt)
}

class HStepArrPairArr[A2](val a1ArrayArrayInts: Array[Array[Int]], val a2Array: Array[A2]) extends ArrayIntBackedPairArr[HStepArr, HStepArrArr, A2, HStepArrPair[A2]]
{ override type ThisT = HStepArrPairArr[A2]
  override def typeStr: String = "HStepArrPairArr"
  override def a1FromArrayArrayInt(array: Array[Int]): HStepArr = new HStepArr(array)
  override def newFromArrays(array1: Array[Array[Int]], array2: Array[A2]): HStepArrPairArr[A2] = new HStepArrPairArr[A2](array1, array2)
  override def elemFromComponents(a1: Array[Int], a2: A2): HStepArrPair[A2] = new HStepArrPair[A2](a1, a2)
  override def a1Arr: HStepArrArr = new HStepArrArr(a1ArrayArrayInts)
  override def fElemStr: HStepArrPair[A2] => String = _.toString
}