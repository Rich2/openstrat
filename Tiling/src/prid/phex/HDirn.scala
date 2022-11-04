/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, collection.mutable.ArrayBuffer

sealed trait HDirnOpt

object HDirnOpt
{
  def fromInt(inp: Int): HDirnOpt = inp match
  {
    case 1 => HexUR
    case 2 => HexRt
    case 3 => HexDR
    case 4 => HexDL
    case 5 => HStepLt
    case 6 => HStepUL
    case n => HDirnNone
  }
}

case object HDirnNone extends HDirnOpt

/** A step on a hex tile grid [[HGrid]] can take 6 values: upright right, downright, downleft, left and upleft. */
sealed trait HDirn extends TDirnSided with ElemInt1 with HDirnOpt
{ /** The delta [[HCen]] of this step inside a hex grid. */
  def hCenDelta: HCen = HCen(tr, tc)
  def intValue: Int
  def reverse: HDirn
  def canEqual(a: Any): Boolean = a.isInstanceOf[HDirn]
}

object HDirn
{
  def fromInt(inp: Int): HDirn = inp match
  { case 1 => HexUR
    case 2 => HexRt
    case 3 => HexDR
    case 4 => HexDL
    case 5 => HStepLt
    case 6 => HStepUL
    case n => excep(s"$n is not a valid HStep")
  }

  def full: HDirnArr = HDirnArr(HexUR, HexRt, HexDR, HexDL, HStepLt, HStepUL)

  implicit val buildEv: Int1ArrMapBuilder[HDirn, HDirnArr] = new Int1ArrMapBuilder[HDirn, HDirnArr]
  { override type BuffT = HStepBuff
    override def fromIntArray(array: Array[Int]): HDirnArr = new HDirnArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HStepBuff = new HStepBuff(buffer)
  }
}

/** A step upright on a hex tile grid [[HGrid]]. */
case object HexUR extends HDirn
{ def sr: Int = 1
  def sc: Int = 1
  def intValue = 1
  override def reverse: HDirn = HexDL
}

/** A step right on a hex tile grid [[HGrid]]. */
case object HexRt extends HDirn
{ def sr: Int = 0
  def sc: Int = 2
  def intValue = 2
  override def reverse: HDirn = HStepLt
}

/** A step downright on a hex tile grid [[HGrid]]. */
case object HexDR extends HDirn
{ def sr: Int = -1
  def sc: Int = 1
  def intValue = 3
  override def reverse: HDirn = HStepUL
}

/** A step downleft on a hex tile grid [[HGrid]]. */
case object HexDL extends HDirn
{ def sr: Int = -1
  def sc: Int = -1
  def intValue = 4
  override def reverse: HDirn = HexUR
}

/** A step left on a hex tile grid [[HGrid]]. */
case object HStepLt extends HDirn
{ def sr: Int = 0
  def sc: Int = -2
  def intValue = 5
  override def reverse: HDirn = HexRt
}

/** A step upleft on a hex tile grid [[HGrid]]. */
case object HStepUL extends HDirn
{ def sr: Int = 1
  def sc: Int = -1
  def intValue = 6
  override def reverse: HDirn = HexDR
}

/** An Arr of hex step directions. */
class HDirnArr(val unsafeArray: Array[Int]) extends AnyVal with Int1Arr[HDirn]
{ override type ThisT = HDirnArr
  override def typeStr: String = "HSteps"
  override def newElem(intValue: Int): HDirn = HDirn.fromInt(intValue)
  override def fromArray(array: Array[Int]): HDirnArr = new HDirnArr(array)
  override def fElemStr: HDirn => String = _.toString

  def segsNum: Int = unsafeArray.length

  def oldSegsForeach[U](start: HCen)(f: LineSeg => U)(implicit grider: HGridSys): Unit = oldSegsForeach(start.r, start.c)(f)

  def oldSegsForeach[U](startR: Int, startC: Int)(f: LineSeg => U)(implicit grider: HGridSys): Unit =
  { var count = 0
    var r1 = startR
    var c1 = startC
    var r2: Int = 0
    var c2: Int = 0

    while (count < segsNum)
    { val step =  HDirn.fromInt(unsafeArray(count))
      r2 = r1 + step.tr
      c2 = c1 + step.tc
      val hls = LineSegHC(r1, c1, r2, c2)
      f(hls.oldLineSeg)
      count += 1
      r1 = r2
      c1 = c2
    }
  }

  def segsForeach[U](start: HCen, trans: LineSegHC => Option[LineSeg])(f: LineSeg => U): Unit = segsForeach(start.r, start.c, trans)(f)

  def segsForeach[U](startR: Int, startC: Int, trans: LineSegHC => Option[LineSeg])(f: LineSeg => U): Unit = {
    var count = 0
    var r1 = startR
    var c1 = startC
    var r2: Int = 0
    var c2: Int = 0

    while (count < segsNum) {
      val step = HDirn.fromInt(unsafeArray(count))
      r2 = r1 + step.tr
      c2 = c1 + step.tc
      val hls = LineSegHC(r1, c1, r2, c2)
      trans(hls).foreach(f)
      count += 1
      r1 = r2
      c1 = c2
    }
  }

  def oldSegsMap[B, ArrB <: Arr[B]](start: HCen)(f: LineSeg => B)(implicit build: ArrMapBuilder[B, ArrB], gridSys: HGridSys): ArrB =
    oldSegsMap(start.r, start.c)(f)(build, gridSys)

  def oldSegsMap[B, ArrB <: Arr[B]](startR: Int, startC: Int)(f: LineSeg => B)(implicit build: ArrMapBuilder[B, ArrB], grider: HGridSys): ArrB =
  { val res = build.arrUninitialised(segsNum)
    var count = 0
    oldSegsForeach(startR, startC){ s =>
      res.unsafeSetElem(count, f(s))
      count += 1
    }
    res
  }

  def segsMap[B, ArrB <: Arr[B]](start: HCen, trans: LineSegHC => Option[LineSeg])(f: LineSeg => B)(implicit build: ArrMapBuilder[B, ArrB]): ArrB =
    segsMap(start.r, start.c, trans)(f)(build)

  def segsMap[B, ArrB <: Arr[B]](startR: Int, startC: Int, trans: LineSegHC => Option[LineSeg])(f: LineSeg => B)(implicit build: ArrMapBuilder[B, ArrB]): ArrB =
  { val res = build.arrUninitialised(segsNum)
    var count = 0
    segsForeach(startR, startC, trans) { s =>
      res.unsafeSetElem(count, f(s))
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
      val optHC: Option[HCen] = grider.findStepEnd(currHC, apply(i))
      optHC.fold[Unit]{ continue = false}{hc2 =>
        buff.grow(hc2)
        currHC = hc2
        i += 1
      }
    }
    buff.toLinePath
  }
}

object HDirnArr extends Int1SeqLikeCompanion[HDirn, HDirnArr]
{ override def fromArray(array: Array[Int]): HDirnArr = new HDirnArr(array)

  implicit val flatBuilder: ArrFlatBuilder[HDirnArr] = new Int1ArrFlatBuilder[HDirnArr]
  { override type BuffT = HStepBuff
    override def fromIntArray(array: Array[Int]): HDirnArr = new HDirnArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HStepBuff = new HStepBuff(buffer)
  }
}

/** ArrayBuffer based buffer class for Colours. */
class HStepBuff(val unsafeBuffer: ArrayBuffer[Int]) extends AnyVal with Int1Buff[HDirn]
{ override def typeStr: String = "HStepBuff"
  def intToT(i1: Int): HDirn = HDirn.fromInt(i1)
}

object HStepBuff
{ def apply(initLen: Int = 4): HStepBuff = new HStepBuff(new ArrayBuffer[Int](initLen))
}