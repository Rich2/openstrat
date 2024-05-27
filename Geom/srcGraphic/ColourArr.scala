/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import Colour._, collection.mutable.ArrayBuffer

/** Specialist Arr, immutable sequence Array[Int] based class for [[Colour]]s. */
final class ColourArr(val arrayUnsafe: Array[Int]) extends AnyVal with ArrInt1[Colour]
{ type ThisT = ColourArr
  override def fromArray(array: Array[Int]): ColourArr = new ColourArr(array)
  override def typeStr: String = "Colours"
  override def newElem(intValue: Int): Colour = Colour(intValue)
  override def fElemStr: Colour => String = _.str
}

/** Companion object for the [[ColourArr]] class, contains apply factory method and helper methods. */
object ColourArr
{ /** Factory apply method for [[ColourArr]] from repeat parameters. */
  def apply(inp: Colour *): ColourArr =
  { val arr = new Array[Int](inp.length)
    var count = 0
    while (count < inp.length) { arr(count) = inp(count).argbValue; count += 1 }
    new ColourArr(arr)
  }

  /** The classically designated 7 colours of the rainbow: Red, Orange, Yellow, Green, Blue, Indigo, Violet. */
  def rainbow: ColourArr = ColourArr(Red, Orange, Yellow, Green, Blue, Indigo, Violet)

  /** This class cycles through the colour of the Rainbow. */
  class RainbowCycle(val value: Int) extends AnyVal
  {
    def apply(): Colour = rainbow(value)
    def next: RainbowCycle = ife(value == rainbow.length - 1, new RainbowCycle(0), new RainbowCycle(value + 1))
    def nextValue: Colour = next()
  }

  def rainbowStart: RainbowCycle = new RainbowCycle(0)

  /** All the named colours. */
  def allNamed: ColourArr = strValueKeys.mapArr[Colour, ColourArr]{case (_, c) => c }
  implicit val arrFlatBuildImplicit: BuilderArrFlat[ColourArr] = new BuilderArrIn1Flat[ColourArr]
  { type BuffT = ColourBuff
    override def fromIntArray(array: Array[Int]): ColourArr = new ColourArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): ColourBuff = new ColourBuff(buffer)
  }
}

/** ArrayBuffer based buffer class for Colours. */
class ColourBuff(val unsafeBuffer: ArrayBuffer[Int]) extends AnyVal with BuffInt1[Colour]
{ override def typeStr: String = "ColourBuff"
  def newElem(i1: Int): Colour = new Colour(i1)
}

object ColourBuff {
  def apply(initLen: Int = 4): ColourBuff = new ColourBuff(new ArrayBuffer[Int](initLen))
}