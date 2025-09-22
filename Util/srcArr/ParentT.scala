/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import reflect.ClassTag

trait Parent2T[A]
{
  def child1: A
  def child2: A
}

object Parent2T
{
  extension [A](thisArr: RArr[Parent2T[A]])
  {
    def childArr(using ct: ClassTag[A]): RArr[A] =
    { val newArray: Array[A] = new Array[A](thisArr.length * 2)
      thisArr.iForeach { (i, parent) =>
        newArray(i * 2) = parent.child1
        newArray(i * 2 + 1) = parent.child2
      }
      new RArr[A](newArray)
    }
  }
}