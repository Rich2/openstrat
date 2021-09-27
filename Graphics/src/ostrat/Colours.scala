/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import Colour._, collection.mutable.ArrayBuffer

/** Specialist Arr, immutable sequence Array[Int] based class for [[Colour]]s. */
final class Colours(val arrayUnsafe: Array[Int]) extends AnyVal with ArrInt1s[Colour]
{ type ThisT = Colours
  override def unsafeFromArray(array: Array[Int]): Colours = new Colours(array)
  override def typeStr: String = "Colours"
  override def newElem(intValue: Int): Colour = Colour(intValue)
  override def dataElem(intValue: Int): Colour = Colour(intValue)
  override def fElemStr: Colour => String = _.str
}

/** Companion object for the [[Colours]] class, contains apply factory method and helper methods. */
object Colours
{
  def apply(inp: Colour *): Colours =
  { val arr = new Array[Int](inp.length)
    var count = 0
    while (count < inp.length) { arr(count) = inp(count).argbValue; count += 1 }
    new Colours(arr)
  }

  /** The classically designated 7 colours of the rainbow: Red, Orange, Yellow, Green, Blue, Indigo, Violet. */
  def rainbow: Colours = Colours(Red, Orange, Yellow, Green, Blue, Indigo, Violet)

  /** This class cycles through the colour of the Rainbow. */
  class RainbowCycle(val value: Int) extends AnyVal
  {
    def apply(): Colour = rainbow(value)
    def next: RainbowCycle = ife(value == rainbow.elemsNum - 1, new RainbowCycle(0), new RainbowCycle(value + 1))
    def nextValue: Colour = next()
  }

  def rainbowStart: RainbowCycle = new RainbowCycle(0)

  /** All the named colours. */
  def allNamed: Colours = strToValue.mapArr[Colour, Colours]{case (_, c) => c }
  implicit val arrFlatBuildImplicit: ArrFlatBuilder[Colours] = new ArrInt1sFlatBuilder[Colour, Colours]
  { type BuffT = ColourBuff
    override def fromIntArray(inp: Array[Int]): Colours = new Colours(inp)
    override def fromIntBuffer(inp: ArrayBuffer[Int]): ColourBuff = new ColourBuff(inp)
  }
}

/** ArrayBuffer based buffer class for Colours. */
class ColourBuff(val unsafeBuff: ArrayBuffer[Int]) extends AnyVal with BuffInt1s[Colour]
{ override def typeStr: String = "ColourBuff"
  def intToT(i1: Int): Colour = new Colour(i1)
}

sealed trait OptColour extends Opt[Colour]

class SomeColour(argbValue: Int) extends OptColour with SomeT[Colour]
{ def value: Colour = new Colour(argbValue)
  override def foreach(f: Colour => Unit): Unit = f(value)
}

object SomeColour
{
  def unapply(input: OptColour): Option[Colour] = input match
  { case sc: SomeColour => Some(sc.value)
    case NoColour => None
  }
}

case object NoColour extends OptColour with NoOpt[Colour]
{ def unapply(inp: OptColour): Boolean = inp.empty
}