package ostrat
import Colour._, collection.mutable.ArrayBuffer

/** Specialist Array[Int] based class for [[Colour]]s. */
final class Colours(val arrayUnsafe: Array[Int]) extends AnyVal with ArrProdInt1[Colour]
{ type ThisT = Colours
  override def unsafeFromArray(array: Array[Int]): Colours = new Colours(array)
  override def typeStr: String = "Colours"
  final override def newElem(intValue: Int): Colour = Colour(intValue)
  override def toString: String = "Colours" + map(_.str).mkString("; ").enParenth
}

object Colours
{
  def apply(inp: Colour *): Colours =
  { val arr = new Array[Int](inp.length)
    var count = 0
    while (count < inp.length) { arr(count) = inp(count).argbValue; count += 1 }
    new Colours(arr)
  }

  /** This class cycles through the colour of the Rainbow. */
  class RainbowCycle(val value: Int) extends AnyVal
  {
    def apply(): Colour = rainbow(value)
    def next: RainbowCycle = ife(value == rainbow.length - 1, new RainbowCycle(0), new RainbowCycle(value + 1))
    def nextValue: Colour = next()
  }

  def rainbowStart: RainbowCycle = new RainbowCycle(0)
}

/** ArrayBuffer based buffer class for Colours. */
class ColourBuff(val buffer: ArrayBuffer[Int]) extends AnyVal with BuffProdInt1[Colour, Colours]
{ def intToT(i1: Int): Colour = new Colour(i1)
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
