/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** [[HVert]] direction of offset towards [[HCen]]. These objects should not be confused with [[HStep]]s */
sealed trait HVDirnOpt extends Int1Elem
{ /** The delta in R to the [[HCen]] from an [[HCoord]]. */
  def dCenR: Int

  /** The delta in C to the [[HCen]] from an [[HCoord]]. */
  def dCenC: Int

  def dVertR: Int
  def dVertC: Int

  def int1: Int

  def opposite: HVDirnOpt

  def corner(hv: HVert): Int

  def clock(steps: Int): HVDirnOpt = if (int1 == 0) HVExact
    else{
    val r1 = int1 - 1
    val r2 = (r1 + steps)  %% 6
    val r3 = r2 + 1
    HVDirnOpt.fromInt(r3)
  }
}

object HVDirnOpt
{
  def fromInt(inp: Int): HVDirnOpt = inp match
  { case 0 => HVExact
    case 1 => HVUR
    case 2 => HVDR
    case 3 => HVDn
    case 4 => HVDL
    case 5 => HVUL
    case 6 => HVUp
    case n => excep(s"$n is an invalid Int value for an HVDirnOpt.")
  }
}

/** An offset of 0 / none to [[HVert]] hex tile vertex. */
object HVExact extends HVDirnOpt
{ def dCenR: Int = 0
  def dCenC: Int = 0
  override def int1: Int = 0
  override def opposite: HVDirnOpt = HVExact
  override def dVertR: Int = 0
  override def dVertC: Int = 0

  override def corner(hv: HVert): Int = 3
  override def toString: String = "HVExact"
}

sealed trait HVDirn extends HVDirnOpt
{ override def opposite: HVDirn

  override def clock(steps: Int): HVDirn =
  {
    val r1 = int1 - 1
    val r2 = (r1 + steps) %% 6
    val r3 = r2 + 1
    HVDirn.fromInt(r3)
  }
}

object HVDirn
{
  def fromInt(inp: Int): HVDirn = inp match
  { case 1 => HVUR
    case 2 => HVDR
    case 3 => HVDn
    case 4 => HVDL
    case 5 => HVUL
    case 6 => HVUp
    case n => excep(s"$n is an invalid Int value for an HVDirn.")
  }
}

/** Up offset to [[HVert]] hex tile vertex. */
case object HVUp extends HVDirn
{ def dCenR: Int = 1
  def dCenC: Int = 0
  override def int1: Int = 6
  override def opposite: HVDirn = HVDn
  override def dVertR: Int = 2
  override def dVertC: Int = 0
  override def corner(hv: HVert): Int = 3
  override def toString: String = "HVUp"
}

/** Up Right offset to [[HVert]] hex tile vertex. */
case object HVUR extends HVDirn
{ def dCenR: Int = 1
  def dCenC: Int = 2
  override def int1: Int = 1
  override def opposite: HVDirn = HVDL
  override def dVertR: Int = 0
  override def dVertC: Int = 2
  override def corner(hv: HVert): Int = 4
  override def toString: String = "HVUR"
}

/** Down right offset to [[HVert]] hex tile vertex. */
case object HVDR extends HVDirn
{ def dCenR: Int = -1
  def dCenC: Int = 2
  override def int1: Int = 2
  override def opposite: HVDirn = HVUL
  override def dVertR: Int = 0
  override def dVertC: Int = 2
  override def corner(hv: HVert): Int = 5
  override def toString: String = "HVDR"
}

/** Down offset to [[HVert]] hex tile vertex. */
case object HVDn extends HVDirn
{ def dCenR: Int = -1
  def dCenC: Int = 0
  override def int1: Int = 3
  override def opposite: HVDirn = HVUp
  override def dVertR: Int = -2
  override def dVertC: Int = 0
  override def corner(hv: HVert): Int = 0
  override def toString: String = "HVDn"
}

/** Down left offset to [[HVert]] hex tile vertex. */
case object HVDL extends HVDirn
{ def dCenR: Int = -1
  def dCenC: Int = -2
  override def int1: Int = 4
  override def opposite: HVDirn = HVUR
  override def dVertR: Int = 0
  override def dVertC: Int = -2
  override def corner(hv: HVert): Int = 1
  override def toString: String = "HVDL"
}

/** Up left offset to [[HVert]] hex tile vertex. */
case object HVUL extends HVDirn
{ def dCenR: Int = 1
  def dCenC: Int = -2
  override def int1: Int = 5
  override def opposite: HVDirn = HVDR
  override def dVertR: Int = 0
  override def dVertC: Int = -2
  override def corner(hv: HVert): Int = 2
  override def toString: String = "HVUL"
}

/** Down offset to [[HVert]] hex tile vertex. */
case object HVRt extends HVDirn
{ def dCenR: Int = 0
  def dCenC: Int = 4
  override def int1: Int = 6
  override def opposite: HVDirn = HVLt
  override def dVertR: Int = 0
  override def dVertC: Int = 4
  override def corner(hv: HVert): Int = 0
  override def toString: String = "HVDn"
}

/** Down offset to [[HVert]] hex tile vertex. */
case object HVLt extends HVDirn
{ def dCenR: Int = 0
  def dCenC: Int = -4
  override def int1: Int = 7
  override def opposite: HVDirn = HVUp
  override def dVertR: Int = 0
  override def dVertC: Int = -4
  override def corner(hv: HVert): Int = 0
  override def toString: String = "HVDn"
}

class HVDirnArr(val unsafeArray: Array[Int]) extends Int1Arr[HVDirnOpt]
{ override type ThisT = HVDirnArr
  override def typeStr: String = "HDirnArr"
  override def newElem(intValue: Int): HVDirnOpt = HVDirnOpt.fromInt(intValue)
  override def fromArray(array: Array[Int]): HVDirnArr = new HVDirnArr(array)
  override def fElemStr: HVDirnOpt => String = _.toString
}

object HVDirnArr extends Int1SeqLikeCompanion[HVDirnOpt, HVDirnArr]
{ override def fromArray(array: Array[Int]): HVDirnArr = new HVDirnArr(array)
}