/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scala.collection.mutable.ArrayBuffer

/** An example of a class that is based on the product of 2 [[Double]]s. This class, [[MyDbl2s]] and their companion objects show you the boiler
 *  plate necessary to create and use custom efficient flat Array based immutable collection classes. */
case class MyDbl2(a: Double, b: Double) extends ElemDbl2
{ override def dbl1: Double = a
  override def dbl2: Double = b
  override def canEqual(that: Any): Boolean = that match
  { case MyDbl2(_, _) => true
    case _ => false
  }
}

object MyDbl2
{
  implicit val arrBuilderImplicit: ArrDbl2sBuilder[MyDbl2, MyDbl2s] = new ArrDbl2sBuilder[MyDbl2, MyDbl2s]
  { type BuffT = MinesBuff
    override def fromDblArray(array: Array[Double]): MyDbl2s = new MyDbl2s(array)
    def fromDblBuffer(inp: ArrayBuffer[Double]): MinesBuff = new MinesBuff(inp)
  }
}

final class MyDbl2s(val unsafeArray: Array[Double]) extends AnyVal with ArrDbl2s[MyDbl2]
{ type ThisT = MyDbl2s
  def typeStr = "Mines"
  def unsafeFromArray(array: Array[Double]): MyDbl2s = new MyDbl2s(array)
  override def seqDefElem(d1: Double, d2: Double): MyDbl2 = MyDbl2(d1, d2)
  override def fElemStr: MyDbl2 => String = _.toString
}

object MyDbl2s extends DataDbl2sCompanion[MyDbl2, MyDbl2s]
{
  implicit val flatImplicit: ArrFlatBuilder[MyDbl2s] = new ArrDbl2sFlatBuilder[MyDbl2, MyDbl2s]
  { type BuffT = MinesBuff
    override def fromDblArray(array: Array[Double]): MyDbl2s = new MyDbl2s(array)
    def fromDblBuffer(inp: ArrayBuffer[Double]): MinesBuff = new MinesBuff(inp)
  }

  override def fromArrayDbl(array: Array[Double]): MyDbl2s = new MyDbl2s(array)

  implicit val persistImplicit: DataDbl2sPersist[MyDbl2, MyDbl2s] = new DataDbl2sPersist[MyDbl2, MyDbl2s]("Mines")
  { override def fromArray(value: Array[Double]): MyDbl2s = new MyDbl2s(value)
  }
}

class MinesBuff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with Dbl2Buff[MyDbl2]
{ override def typeStr: String = "MinesBuff"
  def dblsToT(d1: Double, d2: Double): MyDbl2 = MyDbl2(d1, d2)
}