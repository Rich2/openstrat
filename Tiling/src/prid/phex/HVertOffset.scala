/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

class HVOffset private (val intValue: Int) extends AnyVal with ElemInt1
{ /** bits 2 - 0 */
  def dirn1: HDirnOpt = HDirnOpt.fromInt(intValue % TwoPower3)

  /** bits 6 to 3 values 0 t0 15 => -6 to 9  */
  def delta1: Int = intValue % TwoPower7 / TwoPower3 - 6
  def dirn2: HDirnOpt = HDirnOpt.fromInt(intValue % TwoPower10 / TwoPower7)
  def delta2: Int = intValue % TwoPower14 / TwoPower10
  def dirn3: HDirnOpt = HDirnOpt.fromInt(intValue % TwoPower17 / TwoPower14)
  def delta3: Int = intValue % TwoPower21 / TwoPower17
}

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
