/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer, utest._

object LongArrTest extends TestSuite
{
  //val irb0 = LongArr(4).removeIndex(0) === LongArr()
  //val irb1 = LongArr(-5, 2, 40, -89).removeIndex(2) === LongArr(-5, 2, -89)

  class MyLong1(val long1: Long) extends Long1Elem

 /* class MyLongArr extends Long1Arr[MyLong1]
  {
    type ThisT = MyLongArr

    override def newElem(long1: Long): MyLong1 = ???

    override def unsafeArray: Array[Long] = ???

    /** Reverses the order of the elements of this sequence. */
    override def reverse: MyLongArr = ???

    /** This method should rarely be needed to be used by end users, but returns a new uninitialised [[SeqSpec]] of the this [[Arr]]'s final type. */
    override def unsafeSameSize(length: Int): MyLongArr = ???


    override def fromArray(array: Array[Long]): MyLongArr = ???

    /** Utility method to append element on to an [[ArrayBuffer]][Double]. End users should rarely need to use this method. */
    override def longBufferAppend(buffer: ArrayBuffer[Long], elem: MyLong1): Unit = ???

    override def fElemStr: MyLong1 => String = ???

    /** String specifying the type of this object. */
    override def typeStr: String = ???
  }

  val a1 = LongArr(4, 3, 2, 1)
  val a1b = a1 match{
    case Arr1Tail(h, tail) => tail
    case _ => LongArr()
  }*/

  val tests = Tests {
    test("Remove") {
     // irb0 ==> true
     // irb1 ==> true
     // a1b.length ==> 3
    //  a1b(0) ==> 3
    }
  }
}

