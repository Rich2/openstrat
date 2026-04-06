/* Copyright 2025-6 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import reflect.ClassTag

/** A class that can create 2 objects of the specified type. */
trait Parent2T[A]
{ /** The 1st child object. */
  def child1: A

  /** The 2nd child object. */
  def child2: A
}

object Parent2T
{
  extension [A](thisSequ: Sequ[Parent2T[A]])
  { /** Produces an [[RArr]] of child objects double the length of this [[Arr]]. */
    def childArr(using ct: ClassTag[A]): RArr[A] =
    { val newArray: Array[A] = new Array[A](thisSequ.length * 2)
      thisSequ.iForeach { (i, parent) =>
        newArray(i * 2) = parent.child1
        newArray(i * 2 + 1) = parent.child2
      }
      new RArr[A](newArray)
    }
  }
}