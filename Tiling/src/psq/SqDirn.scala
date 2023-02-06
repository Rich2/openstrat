/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import geom._, collection.mutable.ArrayBuffer

/** A square tile direction can take 8 values. This can be used for square grid steps or quantums. */
sealed trait SqDirn extends TDirn
{ /** The SqCen that this step would point to if it departed from SqCen(0, 0). */
  def sqCen: SqCen = SqCen(tr, tc)

  /** the step that foes in the opposite direct to this step. */
  def reverse: SqDirn

  /** Is perpendicular or non diagonal an Up / Right / Down / Left square tile direction or step */
  def isPerp: Boolean

  /** Is an UpRight / DownRight / DownLeft / UPLeft step. */
  def isDiag: Boolean

  /** The angle of inclination of this square grid direction. */
  def angle: Angle

  def int1: Int
}

object SqDirn {
  def fromInt(int1: Int): SqDirn = int1 match
  { case 1 => SqUR
    case 2 => SqRt
    case 3 => SqDR
    case 4 => SqDn
    case 5 => SqDL
    case 6 => SqLt
    case 7 => SqUL
    case 8 => SqUp
    case _ => SqRt
  }

  implicit val buildEv: Int1ArrMapBuilder[SqDirn, SqDirnArr] = new Int1ArrMapBuilder[SqDirn, SqDirnArr] {
    override type BuffT = SqDirnBuff

    override def fromIntArray(array: Array[Int]): SqDirnArr = new SqDirnArr(array)

    override def fromIntBuffer(buffer: ArrayBuffer[Int]): SqDirnBuff = new SqDirnBuff(buffer)
  }
}

/** A perpendicular or non-diagonal square tile direction or step can take 4 values. */
sealed trait SqDirnPerp extends SqDirn with TDirnSided
{ override def isPerp: Boolean = true
  override def isDiag: Boolean = false
}

/** An upward step / move addition of one square tile in a square tile grid. Increases the row coordinate by 2. */
case object SqUp extends SqDirnPerp
{ override def sr: Int = 1
  override def sc: Int = 0
  override def reverse: SqDirn = SqDn
  override def angle: Angle = 90.degs
  override def int1: Int = 8
}

/** An rightward step / move / addition of one square tile in a square tile grid. Increases the column coordinate by 2 */
case object SqRt extends SqDirnPerp
{ def sr: Int = 0
  def sc: Int = 1
  override def reverse: SqDirn = SqLt
  override def angle: Angle = 0.degs
  override def int1: Int = 2
}

/** An downward step / move / addition of one square tile in a square tile grid. */
case object SqDn extends SqDirnPerp
{ def sr: Int = -1
  def sc: Int = 0
  override def reverse: SqDirn = SqUp
  override def angle: Angle = -90.degs
  override def int1: Int = 4
}

/** An upward of one square tile in a square tile grid. */
case object SqLt extends SqDirnPerp
{ def sr: Int = 0
  def sc: Int = -1
  override def reverse: SqDirn = SqRt
  override def angle: Angle = 180.degs
  override def int1: Int = 6
}

/** A non-diagonal square tile Step can take 4 values. */
sealed trait SqDirnDiag extends SqDirn
{ override def isPerp: Boolean = false
  override def isDiag: Boolean = true
}

/** Up Right square tile step. */
case object SqUR extends SqDirnDiag
{ def tr: Int = 2
  def tc: Int = 2
  override def reverse: SqDirn = SqDR
  override def angle: Angle = 45.degs
  override def int1: Int = 1
}

/** Down Right square tile step. */
case object SqDR extends SqDirnDiag
{ def tr: Int = -2
  def tc: Int = 2
  override def reverse: SqDirn = SqUL
  override def angle: Angle = -45.degs
  override def int1: Int = 3
}

/** Down Left square tile step. */
case object SqDL extends SqDirnDiag
{ def tr: Int = -2
  def tc: Int = -2
  override def reverse: SqDirn = SqUR
  override def angle: Angle = -135.degs
  override def int1: Int = 5
}

/** Up Left square tile step. */
case object SqUL extends SqDirnDiag
{ def tr: Int = 2
  def tc: Int = -2
  override def reverse: SqDirn = SqDR
  override def angle: Angle = 135.degs
  override def int1: Int = 7
}

case class SqAndStep(r1: Int, c1: Int, step: SqDirn)
{ def sc1: SqCen = SqCen(r1, c1)
  def sc2: SqCen = SqCen(r1 + step.tr, c1 + step.tc)
}

/** An Arr of hex step directions. */
class SqDirnArr(val unsafeArray: Array[Int]) extends AnyVal with Int1Arr[SqDirn]
{ override type ThisT = SqDirnArr
  override def typeStr: String = "SqSteps"
  override def newElem(intValue: Int): SqDirn = SqDirn.fromInt(intValue)
  override def fromArray(array: Array[Int]): SqDirnArr = new SqDirnArr(array)
  override def fElemStr: SqDirn => String = _.toString

  def segsNum: Int = unsafeArray.length

  def segSqCsForeach(start: SqCen)(f: LineSegSC => Unit): Unit = segSqCsForeach(start.r, start.c)(f)

  def segSqCsForeach(startR: Int, startC: Int)(f: LineSegSC => Unit): Unit =
  { var count = 0
    var r1 = startR
    var c1 = startC
    var r2: Int = 0
    var c2: Int = 0

    while (count < segsNum)
    { val step = SqDirn.fromInt(unsafeArray(count))
      r2 = r1 + step.tr
      c2 = c1 + step.tc
      val hls = LineSegSC(r1, c1, r2, c2)
      f(hls)
      count += 1
      r1 = r2
      c1 = c2
    }
  }

  def segSqCsMap[B, ArrB <: Arr[B]](start: SqCen)(f: LineSegSC => B)(implicit build: ArrMapBuilder[B, ArrB], gridSys: SqGridSys): ArrB =
    segSqCsMap(start.r, start.c)(f)(build, gridSys)

  def segSqCsMap[B, ArrB <: Arr[B]](startR: Int, startC: Int)(f: LineSegSC => B)(implicit build: ArrMapBuilder[B, ArrB], grider: SqGridSys): ArrB = {
    val res = build.uninitialised(segsNum)
    var count = 0
    segSqCsForeach(startR, startC) { s =>
      res.unsafeSetElem(count, f(s))
      count += 1
    }
    res
  }

  def projLineSegs(startCen: SqCen, proj: SqSysProjection): LineSegArr = projLineSegs(startCen. r, startCen.c, proj)

  def projLineSegs(startR: Int, startC: Int, proj: SqSysProjection): LineSegArr =
  { val res = LineSegArr.uninitialised(segsNum)
    var count = 0
    segSqCsForeach(startR, startC) { lh =>
      val ols = proj.transOptLineSeg(lh)
      ols.foreach(res.unsafeSetElem(count, _))
      count += 1
    }
    res
  }

  /*def pathSqC(startSqC: SqCen)(implicit grider: SqGridSys): LinePathSqC = {
    val buff: SqCoordBuff = SqCoordBuff(startSqC)
    var i = 0
    var continue = true
    var currSqC: SqCen = startSqC
    while(i < length & continue == true) {
      val optSqC: Option[SqCen] = grider.findStepEnd(currSqC, apply(i))
      optSqC.fold[Unit]{ continue = false}{hc2 =>
        buff.grow(hc2)
        currSqC = hc2
        i += 1
      }
    }
    buff.toLinePath
  }*/
}


/** ArrayBuffer based buffer class for Colours. */
class SqDirnBuff(val unsafeBuffer: ArrayBuffer[Int]) extends AnyVal with Int1Buff[SqDirn]
{ override def typeStr: String = "SqDirnBuff"
  def newElem(i1: Int): SqDirn = SqDirn.fromInt(i1)
}

object SqDirnBuff
{ def apply(initLen: Int = 4): SqDirnBuff = new SqDirnBuff(new ArrayBuffer[Int](initLen))
}