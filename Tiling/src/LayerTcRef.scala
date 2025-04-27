/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** An Array of tile centre data of type A. An appropriate [[SqGrid]] or [[HGrid]] is required to utilise its functionality. */
trait LayerTcRef[A <: AnyRef] extends Any
{ /** The mutable backing Array. End users should rarely if ever have to access this field */
  def arrayUnsafe: Array[A]

  /** The number of tile centres this array of data represents. */
  final def length: Int = arrayUnsafe.length

  /** Set all tiles to the given value. */
  final def mutSetAll(value: A): Unit = iUntilForeach(length){i => arrayUnsafe(i) = value }

  /** For each element in the underlying array performs the side effecting function. This method treats the [[LayerTcRef]] class like a standard Arr
   *  or Array. It does not utilise the grid [[TGrid]] from which this [[LayerTcRef]] was created. */
  def foreach[U](f: A => U): Unit = arrayUnsafe.foreach(f)

  /** Each element in the underlying array is mapped by the parameter function to an element of type B. This method treat the [[HCenArr]] class like a
   *  standard Arr or Array. It does not utilise the grid HGrid from which this HCenArr was created. */
  def map[B, BB <: Arr[B]](f: A => B)(implicit build: BuilderArrMap[B, BB]): BB =
  { val res = build.uninitialised(length)
    var count = 0
    foreach{a => res.setElemUnsafe(count, f(a)); count += 1 }
    res
  }
}