/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** [[HVert]] direction of offset towards [[HCen]]. These objects should not be confused with [[HStep]]s. Stored as a 4 bit number between 0 and 15
 *  although only values 0 to 8 currently used. */
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
  /** Values 0 to 8 are valid. Normally storeda as 4bit 0-15 value. */
  def fromInt(inp: Int): HVDirnOpt = inp match
  { case 0 => HVExact
    case 1 => HVUR
    case 2 => HVDR
    case 3 => HVDn
    case 4 => HVDL
    case 5 => HVUL
    case 6 => HVUp
    case 7 => HVRt
    case 8 => HVLt
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
  { val r1 = int1 - 1
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
    case 7 => HVRt
    case 8 => HVLt
    case n => excep(s"$n is an invalid Int value for an HVDirn.")
  }

  def inFromVertIndex(inp: Int): HVDirn = inp %% 6 match {
    case 0 => HVDn
    case 1 => HVDL
    case 2 => HVUL
    case 3 => HVUp
    case 4 => HVUR
    case 5 => HVDR
  }
}

sealed trait HVDirnPrimary extends HVDirn

/** Up offset to [[HVert]] hex tile vertex. */
case object HVUp extends HVDirnPrimary
{ def dCenR: Int = 1
  def dCenC: Int = 0
  override def int1: Int = 6
  override def opposite: HVDirnPrimary = HVDn
  override def dVertR: Int = 2
  override def dVertC: Int = 0
  override def corner(hv: HVert): Int = 3
  override def toString: String = "HVUp"
}

/** Up Right offset to [[HVert]] hex tile vertex. */
case object HVUR extends HVDirnPrimary
{ def dCenR: Int = 1
  def dCenC: Int = 2
  override def int1: Int = 1
  override def opposite: HVDirnPrimary = HVDL
  override def dVertR: Int = 0
  override def dVertC: Int = 2
  override def corner(hv: HVert): Int = 4
  override def toString: String = "HVUR"
}

/** Down right offset to [[HVert]] hex tile vertex. */
case object HVDR extends HVDirnPrimary
{ def dCenR: Int = -1
  def dCenC: Int = 2
  override def int1: Int = 2
  override def opposite: HVDirnPrimary = HVUL
  override def dVertR: Int = 0
  override def dVertC: Int = 2
  override def corner(hv: HVert): Int = 5
  override def toString: String = "HVDR"
}

/** Down offset to [[HVert]] hex tile vertex. */
case object HVDn extends HVDirnPrimary
{ def dCenR: Int = -1
  def dCenC: Int = 0
  override def int1: Int = 3
  override def opposite: HVDirnPrimary = HVUp
  override def dVertR: Int = -2
  override def dVertC: Int = 0
  override def corner(hv: HVert): Int = 0
  override def toString: String = "HVDn"
}

/** Down left offset to [[HVert]] hex tile vertex. */
case object HVDL extends HVDirnPrimary
{ def dCenR: Int = -1
  def dCenC: Int = -2
  override def int1: Int = 4
  override def opposite: HVDirnPrimary = HVUR
  override def dVertR: Int = 0
  override def dVertC: Int = -2
  override def corner(hv: HVert): Int = 1
  override def toString: String = "HVDL"
}

/** Up left offset to [[HVert]] hex tile vertex. */
case object HVUL extends HVDirnPrimary
{ def dCenR: Int = 1
  def dCenC: Int = -2
  override def int1: Int = 5
  override def opposite: HVDirnPrimary = HVDR
  override def dVertR: Int = 0
  override def dVertC: Int = -2
  override def corner(hv: HVert): Int = 2
  override def toString: String = "HVUL"
}

/** Down offset to [[HVert]] hex tile vertex. */
case object HVRt extends HVDirn
{ def dCenR: Int = 0
  def dCenC: Int = 4
  override def int1: Int = 7
  override def opposite: HVDirn = HVLt
  override def dVertR: Int = 0
  override def dVertC: Int = 4
  override def corner(hv: HVert): Int = 0
  override def toString: String = "HVRt"
}

/** Down offset to [[HVert]] hex tile vertex. */
case object HVLt extends HVDirn
{ def dCenR: Int = 0
  def dCenC: Int = -4
  override def int1: Int = 8
  override def opposite: HVDirn = HVUp
  override def dVertR: Int = 0
  override def dVertC: Int = -4
  override def corner(hv: HVert): Int = 0
  override def toString: String = "HVLt"
}

class HVDirnArr(val arrayUnsafe: Array[Int]) extends ArrInt1[HVDirnOpt]
{ override type ThisT = HVDirnArr
  override def typeStr: String = "HDirnArr"
  override def elemFromInt(intValue: Int): HVDirnOpt = HVDirnOpt.fromInt(intValue)
  override def fromArray(array: Array[Int]): HVDirnArr = new HVDirnArr(array)
  override def fElemStr: HVDirnOpt => String = _.toString
}

object HVDirnArr extends CompanionSlInt1[HVDirnOpt, HVDirnArr]
{ override def fromArray(array: Array[Int]): HVDirnArr = new HVDirnArr(array)
}