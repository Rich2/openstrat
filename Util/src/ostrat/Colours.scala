/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import Colour._

class Colours(val arr: Array[Int]) extends AnyVal with ProductI1s[Colour]
{
  //override def typeStr: String = "Colours"  
  final override def newElem(intValue: Int): Colour = Colour(intValue)  
}

object Colours
{
  def apply(inp: Colour *): Colours =
  {
    val arr = new Array[Int](inp.length)
    var count = 0
    while (count < inp.length) { arr(count) = inp(count).argbValue; count += 1 }
    new Colours(arr)
  }
}

class RainbowCycle(val value: Int) extends AnyVal
{
  def apply(): Colour = rainbow(value)
  def next: RainbowCycle = ife(value == rainbow.length - 1, new RainbowCycle(0), new RainbowCycle(value + 1))
  def nextValue: Colour = next()
}

object RainbowCycle
{
  def start: RainbowCycle = new RainbowCycle(0)
}
