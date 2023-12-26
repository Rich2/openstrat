/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** The offset of a point relative an [[HVert]]. The offset is specified by the [[HVDirn]] direction to another [[HVert]] and the magnitude of that
 * offset speified in 1 sixteenth fractions. An [[HCorner]] consists of 1 or 2 of these [[HVOffsetDelta]]. The [[HCorner]] values are stored in an
 * [[HCornerLayer]]. The value of the [[HVert]] can be determined by its position in [[HCornerLayer]]. */
class HVOffsetDelta(val int1: Int) extends AnyVal with Int1Elem
{ def hvDirn: HVDirnOpt = HVDirnOpt.fromInt(int1 %% 16)
  def magnitude: Int = int1 / 16
}

/** Companion object for [[HVOffsetDelta]] class, contains factory apply and fromInt methods. */
object HVOffsetDelta
{
  def apply(dirn: HVDirnOpt, magnitude: Int): Int =
  { val m2 = magnitude match
    { case m if m > 10 => { deb("$m offset value is greater than max allowed value of 10"); 7 }
      case m if m < 0 => { deb("< 0"); 0 }
      case m => m
    }
    dirn.int1 + m2 * 16
  }
  def fromInt(inp: Int): HVOffsetDelta = new HVOffsetDelta(inp)
}