/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid

/** An array of hex tile or hex centre data. */
class HcsArr[A <: AnyRef](val unsafeArr: Array[A])
{
  def length: Int = unsafeArr.length
  def apply(hc: HCen)(implicit grid: HGrid): A = unsafeArr(grid.arrIndex(hc))
}
