/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** [[HVert]] direction of offset towards [[HCen]]. These objects should not be confused with [[HStep]]s */
sealed trait HVDirn
{ /** The delta in R to the [[HCen]] from an [[HCoord]]. */
  def dCenR: Int

  /** The delta in C to the [[HCen]] from an [[HCoord]]. */
  def dCenC: Int

  def dVertR: Int
  def dVertC: Int

  def intValue: Int

  def opposite: HVDirn
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
{ def dCenR: Int = 0
  def dCenC: Int = 0
  override def intValue: Int = 0
  override def opposite: HVDirn = HVExact
  override def dVertR: Int = 0
  override def dVertC: Int = 0
}

/** Hex Vert Up offset. */
object HVUp extends HVDirn
{ def dCenR: Int = 1
  def dCenC: Int = 0
  override def intValue: Int = 6
  override def opposite: HVDirn = HVDn
  override def dVertR: Int = 2
  override def dVertC: Int = 0
}

/** Hex Vert Up Right offset. */
object HVUR extends HVDirn
{ def dCenR: Int = 1
  def dCenC: Int = 2
  override def intValue: Int = 1
  override def opposite: HVDirn = HVDL
  override def dVertR: Int = 0
  override def dVertC: Int = 2
}

object HVDR extends HVDirn
{ def dCenR: Int = -1
  def dCenC: Int = 2
  override def intValue: Int = 2
  override def opposite: HVDirn = HVUL
  override def dVertR: Int = 0
  override def dVertC: Int = 2
}

object HVDn extends HVDirn
{ def dCenR: Int = -1
  def dCenC: Int = 0
  override def intValue: Int = 3
  override def opposite: HVDirn = HVUp
  override def dVertR: Int = -2
  override def dVertC: Int = 0
}

object HVDL extends HVDirn
{ def dCenR: Int = -1
  def dCenC: Int = -2
  override def intValue: Int = 4
  override def opposite: HVDirn = HVUR
  override def dVertR: Int = 0
  override def dVertC: Int = -2
}

object HVUL extends HVDirn
{ def dCenR: Int = 1
  def dCenC: Int = -2
  override def intValue: Int = 5
  override def opposite: HVDirn = HVDR
  override def dVertR: Int = 0
  override def dVertC: Int = -2
}