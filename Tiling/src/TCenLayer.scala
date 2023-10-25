/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** An Array of tile centre data of type A. An appropriate [[SqGrid]] or [[HGrid]] is required to utilise its functionality. */
trait TCenLayer[A <: AnyRef] extends Any with RefsSeqLike[A]
{ /** The mutable backing Array. End users should rarely if ever have to access this field */
  def unsafeArray: Array[A]

  /** The number of tile centres this array of data represents. */
  final def length: Int = unsafeArray.length

  /** Set all tiles to the given value. */
  final def mutSetAll(value: A): Unit = iUntilForeach(length){i => unsafeArray(i) = value }

  /** For each element in the underlying array performs the side effecting function. This method treats the [[TCenLayer]] class like a standard Arr
   *  or Array. It does not utilise the grid [[TGrid]] from which this [[TCenLayer]] was created. */
  def foreach[U](f: A => U): Unit = unsafeArray.foreach(f)

  override def ssForeach[U](f: A => U): Unit = foreach(f)

  /** Each element in the underlying array is mapped by the parameter function to an element of type B. This method treat the [[HCenArr]] class like a
   *  standard Arr or Array. It does not utilise the grid HGrid from which this HCenArr was created. */
  def map[B, BB <: Arr[B]](f: A => B)(implicit build: MapBuilderArr[B, BB]): BB =
  { val res = build.uninitialised(length)
    var count = 0
    foreach{a => res.setElemUnsafe(count, f(a)); count += 1 }
    res
  }
}