/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scala.collection.mutable.ArrayBuffer

/** An example of a class that is based on the product of 2 [[Double]]s. This class, [[MyDbl2s]] and their companion objects show you the boiler
 *  plate necessary to create and use custom efficient flat Array based immutable collection classes. */
case class MyDbl2Elem(a: Double, b: Double) extends Dbl2Elem
{ override def dbl1: Double = a
  override def dbl2: Double = b
  override def canEqual(that: Any): Boolean = that match
  { case MyDbl2Elem(_, _) => true
    case _ => false
  }
}

object MyDbl2Elem
{
  implicit val arrBuilderImplicit: Dbl2ArrMapBuilder[MyDbl2Elem, MyDbl2s] = new Dbl2ArrMapBuilder[MyDbl2Elem, MyDbl2s]
  { type BuffT = MinesBuff
    override def fromDblArray(array: Array[Double]): MyDbl2s = new MyDbl2s(array)
    def buffFromBufferDbl(buffer: ArrayBuffer[Double]): MinesBuff = new MinesBuff(buffer)
  }
}

final class MyDbl2s(val unsafeArray: Array[Double]) extends AnyVal with Dbl2Arr[MyDbl2Elem]
{ type ThisT = MyDbl2s
  def typeStr = "Mines"
  def fromArray(array: Array[Double]): MyDbl2s = new MyDbl2s(array)
  override def seqDefElem(d1: Double, d2: Double): MyDbl2Elem = MyDbl2Elem(d1, d2)
  override def fElemStr: MyDbl2Elem => String = _.toString
}

object MyDbl2s extends Dbl2SeqLikeCompanion[MyDbl2Elem, MyDbl2s]
{
  implicit val flatImplicit: ArrFlatBuilder[MyDbl2s] = new Dbl2ArrFlatBuilder[MyDbl2s]
  { type BuffT = MinesBuff
    override def fromDblArray(array: Array[Double]): MyDbl2s = new MyDbl2s(array)
    def buffFromBufferDbl(inp: ArrayBuffer[Double]): MinesBuff = new MinesBuff(inp)
  }

  override def fromArray(array: Array[Double]): MyDbl2s = new MyDbl2s(array)

  implicit val persistImplicit: Dbl2SeqDefPersist[MyDbl2Elem, MyDbl2s] = new Dbl2SeqDefPersist[MyDbl2Elem, MyDbl2s]("Mines")
  { override def fromArray(array: Array[Double]): MyDbl2s = new MyDbl2s(array)
  }
}

class MinesBuff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with Dbl2Buff[MyDbl2Elem]
{ override def typeStr: String = "MinesBuff"
  def newElem(d1: Double, d2: Double): MyDbl2Elem = MyDbl2Elem(d1, d2)
}