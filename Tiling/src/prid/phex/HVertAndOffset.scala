/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** [[HVert]] direction of offset towards [[HCen]]. */
sealed trait HVDirn
{ /** The delta in R to the [[HCen]] from an [[HCoord]]. */
  def deltaR: Int

  /** The delta in C to the [[HCen]] from an [[HCoord]]. */
  def deltaC: Int
}

object HVDirn
{
  def fromInt(inp: Int): HVDirn = inp match
  { case 0 => HVExact
    case 1 => HVUR
    case 2 => HVDR
    case 3 => HVDn
    case 4 => HVDL
    case 5 => HVUL
    case 6 => HVUp
    case n => excep(s"$n is an invalid Int value for an HVDirn.")
  }
}

/** Hex Vert offset of none. */
object HVExact extends HVDirn
{ def deltaR: Int = 0
  def deltaC: Int = 0
}

/** Hex Vert Up offset. */
object HVUp extends HVDirn
{ def deltaR: Int = 1
  def deltaC: Int = 0
}

/** Hex Vert Up Right offset. */
object HVUR extends HVDirn
{ def deltaR: Int = 1
  def deltaC: Int = 1
}

object HVDR extends HVDirn
{ def deltaR: Int = -1
  def deltaC: Int = 1
}

object HVDn extends HVDirn
{ def deltaR: Int = -1
  def deltaC: Int = 0
}

object HVDL extends HVDirn
{ def deltaR: Int = -1
  def deltaC: Int = -1
}

object HVUL extends HVDirn
{ def deltaR: Int = 1
  def deltaC: Int = -1
}

class HVertOffset(val int1: Int) extends AnyVal with Int1Elem
{ def hvDirn: HVDirn = HVDirn.fromInt(int1 %% 8)
  def offset: Int = int1 / 8
}

class HVertOffsetSys(val unsafeArray: Array[Int])
{
  //def apply(hCen: HCen, vertNum: Int)
}

/** Offset of an [[HVert]] measured in an offset towards a neighbouring [[HCen]]. */
class HVertAndOffset(val r: Int, val c: Int, val hvDirnInt: Int, val offset: Int)
{ /** The [[HVert]]. */
  def vert: HVert = HVert(r, c)

  def hvDirn: HVDirn = HVDirn.fromInt(hvDirnInt)
}

/*class HVOffset private (val int1: Int) extends AnyVal with Int1Elem
{ /** bits 2 - 0 */
  def dirn1: HDirnOpt = HDirnOpt.fromInt(int1 % TwoPower3)

  /** bits 6 to 3 values 0 t0 15 => -6 to 9  */
  def delta1: Int = int1 % TwoPower7 / TwoPower3 - 6
  def dirn2: HDirnOpt = HDirnOpt.fromInt(int1 % TwoPower10 / TwoPower7)
  def delta2: Int = int1 % TwoPower14 / TwoPower10
  def dirn3: HDirnOpt = HDirnOpt.fromInt(int1 % TwoPower17 / TwoPower14)
  def delta3: Int = int1 % TwoPower21 / TwoPower17
}*/

//trait HVOffset0 extends HVOffset

/*
object HVNoOffset extends HVOptOffset
{ override def intValue: Int = 0
  override def delta: Int = 0
}

sealed abstract class HVSomeOffset(deltaIn: Int) extends HVOptOffset
{ def fullR: Int
  def fullC: Int
  def dInt: Int
  val delta: Int = deltaIn match {
    case i if i >= 4 => 4
    case i if i <= -2 => -2
    case i => i
  }
  override def intValue: Int = dInt + delta + 2
}

class HVOffsetUp(delta: Int) extends HVSomeOffset(delta)
{ def fullR: Int = 1
  def fullC: Int = 0
  override def dInt: Int = 8
}

class HVOffsetUR(delta: Int) extends HVSomeOffset(delta)
{ def fullR: Int = 1
  def fullC: Int = 2
  override def dInt: Int = 16
}

class HVOffsetDR(delta: Int) extends HVSomeOffset(delta)
{ def fullR: Int = -1
  def fullC: Int = 2
  override def dInt: Int = 24
}

class HVOffsetDn(delta: Int) extends HVSomeOffset(delta)
{ def fullR: Int = -1
  def fullC: Int = 0
  override def dInt: Int = 32
}

class HVOffsetDL(delta: Int) extends HVSomeOffset(delta)
{ def fullR: Int = -1
  def fullC: Int = -2
  override def dInt: Int = 40
}

class HVOffsetUL(delta: Int) extends HVSomeOffset(delta)
{ def fullR: Int = 1
  def fullC: Int = -2
  override def dInt: Int = 48
}

class HOptOffsetArr(val unsafeArray: Array[Int]) extends Int1Arr[HVOptOffset]
{ override type ThisT = HOptOffsetArr
  override def typeStr: String = "OptOffsetArr"
  override def fromArray(array: Array[Int]): HOptOffsetArr = new HOptOffsetArr(array)
  override def fElemStr: HVOptOffset => String = el => "HVOpt" + el.delta

  override def dataElem(intValue: Int): HVOptOffset =
  { val t = intValue / 8
    val d = (intValue % 8) - 2
    t match {
      case 1 => new HVOffsetUp(d)
      case 2 => new HVOffsetUR(d)
      case 3 => new HVOffsetDR(d)
      case 4 => new HVOffsetDn(d)
      case 5 => new HVOffsetDL(d)
      case 6 => new HVOffsetUL(d)
      case _ => HVNoOffset
    }
  }
}*/
