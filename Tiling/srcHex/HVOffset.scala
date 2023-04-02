/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** [[HVert]] offset. The direction and magnitude of an [[HVAndOffset]]. An [[HCorner]] consists of 1 or 2 of these [[HVOffset]]. The [[HCorner]]
 * values are stored in an [[HCornerLayer]]. The value of the [[HVert]] can be determined by its position in [[HCornerLayer]]. */
class HVOffset(val int1: Int) extends AnyVal with Int1Elem
{ def hvDirn: HVDirnOpt = HVDirnOpt.fromInt(int1 %% 8)
  def magnitude: Int = int1 / 8
}

/** Companion object for [[HVOffset]] class, contains factory apply and fromInt methods. */
object HVOffset
{
  def apply(dirn: HVDirnOpt, magnitude: Int): Int =
  { val m2 = magnitude match {
    case m if m >= 8 => { deb("> 8"); 7 }
    case m if m < 0 => { deb("< 0"); 0 }
    case m => m
  }
    dirn.int1 + m2 * 8
  }
  def fromInt(inp: Int): HVOffset = new HVOffset(inp)
}