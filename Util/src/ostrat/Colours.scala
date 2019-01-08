/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

class Colours(val arr: Array[Int]) extends AnyVal
{
  def length: Int = arr.length
  def apply(index: Int): Colour = Colour(arr(index))
  def find(colour: Colour): Option[Colour] =
  {
    var count = 0
    var acc: Option[Colour] = None
    var continue = true
    while (continue == true & count < length)
    {
      if (colour.argbValue == arr(count))
      {
        acc = Some(Colour(arr(count)))
        continue = false
      }
      count += 1
    }
    acc
  }
  def findIndex(colour: Colour): Opt[Int] =
  {
    var count = 0
    var acc: Opt[Int] = NoInt
    var continue = true
    while (continue == true & count < length)
    {
      if (colour.argbValue == arr(count))
      {
        acc = SomeInt(count)
        continue = false
      }
      count += 1
    }
    acc
  }
  
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